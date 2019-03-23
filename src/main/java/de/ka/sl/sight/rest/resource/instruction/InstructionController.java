package de.ka.sl.sight.rest.resource.instruction;

import de.ka.sl.sight.persistence.instruction.InstructionDAO;
import de.ka.sl.sight.persistence.instruction.InstructionEntity;
import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.config.Endpoint;
import de.ka.sl.sight.rest.general.exception.AppException;
import de.ka.sl.sight.rest.general.exception.NotFoundException;
import de.ka.sl.sight.rest.resource.recipe.RecipeService;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.generic.Instruction;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = Endpoint.RECIPES + "/{recipeId:[0-9]+}/instructions")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class InstructionController {

    private final InstructionDAO instructionDAO;
    private final InstructionMapper instructionMapper;

    private final RecipeService recipeService;

    //--------------------------------------
    // Methods
    //--------------------------------------

    @GetMapping()
    public Resources<Resource<InstructionEntity>> all(@PathVariable(name = "recipeId") long recipeId) {
        List<Resource<InstructionEntity>> instructions = instructionDAO
                .findAllByRecipeId(recipeId).stream()
                .map(instructionMapper::toResource)
                .collect(Collectors.toList());
        return new Resources<>(instructions, linkTo(InstructionController.class, recipeId).withSelfRel());
    }

    @GetMapping(Endpoint.ID)
    public Resource<InstructionEntity> read(@PathVariable long id) throws AppException {
        Optional<InstructionEntity> optional = instructionDAO.findById(id);
        if (optional.isPresent()) {
            return instructionMapper.toResource(optional.get());
        } else {
            throw new NotFoundException(Instruction.class, id);
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody InstructionEntity instructionEntity,
                                    @PathVariable(name = "recipeId") long recipeId)
            throws AppException, URISyntaxException {
        Optional<RecipeEntity> optional = recipeService.get(recipeId);
        if (optional.isPresent()) {
            instructionEntity = instructionDAO.save(instructionEntity);
            instructionEntity.setRecipe(optional.get());
            Resource<InstructionEntity> resource = instructionMapper.toResource(instructionDAO.save(instructionEntity));
            return ResponseEntity
                    .created(new URI(resource.getId().expand().getHref()))
                    .body(resource);
        } else {
            throw new NotFoundException(RecipeEntity.class, recipeId);
        }
    }

    @PutMapping(Endpoint.ID)
    public ResponseEntity<?> update(@RequestBody InstructionEntity newInstructionEntity,
                                    @PathVariable(name = "recipeId") long recipeId,
                                    @PathVariable(name = "id") long id)
            throws AppException, URISyntaxException {
        Optional<RecipeEntity> optional = recipeService.get(recipeId);
        if (optional.isPresent()) {
            InstructionEntity updatedInstructionEntity = instructionDAO
                    .findByIdAndRecipeId(id, recipeId)
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
            throw new NotFoundException(RecipeEntity.class, recipeId);
        }
    }

    @DeleteMapping(Endpoint.ID)
    public ResponseEntity<?> delete(@PathVariable long recipeId, @PathVariable long id) {
        if (instructionDAO.existsById(id)) {
            instructionDAO.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }

}
