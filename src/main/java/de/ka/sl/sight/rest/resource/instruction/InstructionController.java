package de.ka.sl.sight.rest.resource.instruction;

import de.ka.sl.sight.persistence.instruction.Instruction;
import de.ka.sl.sight.persistence.instruction.InstructionDAO;
import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.persistence.recipe.RecipeDAO;
import de.ka.sl.sight.rest.general.AppException;
import de.ka.sl.sight.rest.resource.recipe.RecipeNotFoundException;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
@RestController
@RequestMapping(value = "/recipes/{recipeId:[0-9]+}/instructions")
public final class InstructionController {

    private InstructionDAO instructionDAO;
    private InstructionMapper instructionMapper;

    private RecipeDAO recipeDAO;

    //--------------------------------------
    // Constructors
    //--------------------------------------

    public InstructionController(InstructionDAO instructionDAO, InstructionMapper instructionMapper,
                                 RecipeDAO recipeDAO) {
        this.instructionDAO = instructionDAO;
        this.instructionMapper = instructionMapper;
        this.recipeDAO = recipeDAO;
    }

    //--------------------------------------
    // Methods
    //--------------------------------------

    @GetMapping()
    public Resources<Resource<Instruction>> all(@PathVariable(name = "recipeId") long recipeId) {
        List<Resource<Instruction>> instructions = instructionDAO.findAllByRecipeId(recipeId).stream()
                .map(instructionMapper::toResource)
                .collect(Collectors.toList());
        return new Resources<>(instructions, linkTo(InstructionController.class, recipeId).withSelfRel());
    }

    @GetMapping("/{id:[0-9]+}")
    public Resource<Instruction> read(@PathVariable long id) throws AppException {
        return instructionMapper.toResource(
                instructionDAO.findById(id).orElseThrow(() -> new InstructionNotFoundException(id)));
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Instruction instruction,
                                    @PathVariable(name = "recipeId") long recipeId)
            throws AppException, URISyntaxException {
        Optional<RecipeEntity> optional = recipeDAO.findById(recipeId);
        if (optional.isPresent()) {
            instruction = instructionDAO.save(instruction);
            instruction.setRecipe(optional.get());
            Resource<Instruction> resource = instructionMapper.toResource(instructionDAO.save(instruction));
            return ResponseEntity
                    .created(new URI(resource.getId().expand().getHref()))
                    .body(resource);
        } else {
            throw new RecipeNotFoundException(recipeId);
        }
    }

    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<?> update(@RequestBody Instruction newInstruction,
                                    @PathVariable(name = "recipeId") long recipeId,
                                    @PathVariable(name = "id") long id)
            throws AppException, URISyntaxException {
        Optional<RecipeEntity> optional = recipeDAO.findById(recipeId);
        if (optional.isPresent()) {
            Instruction updatedInstruction = instructionDAO.findByIdAndRecipeId(id, recipeId)
                    .map(instruction -> {
                        instruction.updateFrom(newInstruction);
                        return instructionDAO.save(instruction);
                    })
                    .orElseGet(() -> {
                        //newInstruction.setId(id);
                        newInstruction.setRecipe(optional.get());
                        return instructionDAO.save(newInstruction);
                    });
            Resource<Instruction> resource = instructionMapper.toResource(updatedInstruction);
            return ResponseEntity
                    .created(new URI(resource.getId().expand().getHref()))
                    .body(resource);
        } else {
            throw new RecipeNotFoundException(recipeId);
        }
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable long recipeId, @PathVariable long id) {
        if (instructionDAO.existsById(id)) {
            instructionDAO.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }

}
