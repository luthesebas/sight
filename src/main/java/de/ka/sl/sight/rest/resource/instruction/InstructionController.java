package de.ka.sl.sight.rest.resource.instruction;

import de.ka.sl.sight.persistence.instruction.InstructionEntity;
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
    public Resources<Resource<InstructionEntity>> all(@PathVariable(name = "recipeId") long recipeId) {
        List<Resource<InstructionEntity>> instructions = instructionDAO.findAllByRecipeId(recipeId).stream()
                .map(instructionMapper::toResource)
                .collect(Collectors.toList());
        return new Resources<>(instructions, linkTo(InstructionController.class, recipeId).withSelfRel());
    }

    @GetMapping("/{id:[0-9]+}")
    public Resource<InstructionEntity> read(@PathVariable long id) throws AppException {
        return instructionMapper.toResource(
                instructionDAO.findById(id).orElseThrow(() -> new InstructionNotFoundException(id)));
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody InstructionEntity instructionEntity,
                                    @PathVariable(name = "recipeId") long recipeId)
            throws AppException, URISyntaxException {
        Optional<RecipeEntity> optional = recipeDAO.findById(recipeId);
        if (optional.isPresent()) {
            instructionEntity = instructionDAO.save(instructionEntity);
            instructionEntity.setRecipe(optional.get());
            Resource<InstructionEntity> resource = instructionMapper.toResource(instructionDAO.save(instructionEntity));
            return ResponseEntity
                    .created(new URI(resource.getId().expand().getHref()))
                    .body(resource);
        } else {
            throw new RecipeNotFoundException(recipeId);
        }
    }

    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<?> update(@RequestBody InstructionEntity newInstructionEntity,
                                    @PathVariable(name = "recipeId") long recipeId,
                                    @PathVariable(name = "id") long id)
            throws AppException, URISyntaxException {
        Optional<RecipeEntity> optional = recipeDAO.findById(recipeId);
        if (optional.isPresent()) {
            InstructionEntity updatedInstructionEntity = instructionDAO.findByIdAndRecipeId(id, recipeId)
                    .map(instruction -> {
                        instruction.updateFrom(newInstructionEntity);
                        return instructionDAO.save(instruction);
                    })
                    .orElseGet(() -> {
                        //newInstructionEntity.setId(id);
                        newInstructionEntity.setRecipe(optional.get());
                        return instructionDAO.save(newInstructionEntity);
                    });
            Resource<InstructionEntity> resource = instructionMapper.toResource(updatedInstructionEntity);
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
