package de.ka.sl.sight.rest.resource.instruction;

import de.ka.sl.sight.config.Endpoint;
import de.ka.sl.sight.persistence.instruction.InstructionEntity;
import de.ka.sl.sight.persistence.recipe.RecipeEntity;
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

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
@RestController
@RequestMapping(Endpoint.RECIPES_ID + Endpoint.INSTRUCTIONS)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class InstructionController {

    private final RecipeService recipeService;
    private final InstructionService instructionService;
    private final InstructionResourceMapper mapper;

    //--------------------------------------
    // Methods
    //--------------------------------------

    private static <T> URI uriOf(Resource<T> resource) throws URISyntaxException {
        return new URI(resource.getId().expand().getHref());
    }

    //--------------------------------------
    // CRUD
    //--------------------------------------

    @PostMapping()
    public ResponseEntity<?> create(
            @PathVariable(Endpoint.RECIPES_ID_VAR_NAME) long recipeId,
            @RequestBody InstructionEntity data
    ) throws AppException, URISyntaxException {
        Optional<RecipeEntity> recipe = recipeService.read(recipeId);
        if (recipe.isPresent()) {
            InstructionEntity instruction = instructionService.create(data, recipe.get());
            Resource<InstructionEntity> resource = mapper.toResource(instruction);
            return ResponseEntity.created(uriOf(resource)).body(resource);
        } else {
            throw new NotFoundException(RecipeEntity.class, recipeId);
        }
    }

    @PutMapping(Endpoint.ID)
    public ResponseEntity<?> update(
            @PathVariable(Endpoint.RECIPES_ID_VAR_NAME) long recipeId,
            @PathVariable(Endpoint.ID_VAR_NAME) long instructionId,
            @RequestBody InstructionEntity data
    ) throws AppException, URISyntaxException {
        Optional<RecipeEntity> optional = recipeService.read(recipeId);
        if (optional.isPresent()) {
            InstructionEntity instruction = instructionService.update(instructionId, data, optional.get());
            Resource<InstructionEntity> resource = mapper.toResource(instruction);
            return ResponseEntity.created(uriOf(resource)).body(resource);
        } else {
            throw new NotFoundException(RecipeEntity.class, recipeId);
        }
    }

    @GetMapping()
    public Resources<Resource<InstructionEntity>> read(
            @PathVariable(Endpoint.RECIPES_ID_VAR_NAME) long recipeId
    ) throws NotFoundException {
        List<InstructionEntity> instructions = instructionService.read(recipeId);
        if (instructions != null && !instructions.isEmpty()) {
            return mapper.toResource(instructions, InstructionController.class, recipeId);
        } else {
            throw new NotFoundException(InstructionEntity.class);
        }
    }

    @GetMapping(Endpoint.ID)
    public Resource<InstructionEntity> read(
            @PathVariable(Endpoint.RECIPES_ID_VAR_NAME) long recipeId,
            @PathVariable(Endpoint.ID_VAR_NAME) long instructionId
    ) throws AppException {
        Optional<InstructionEntity> optional = instructionService.read(instructionId, recipeId);
        if (optional.isPresent()) {
            return mapper.toResource(optional.get());
        } else {
            throw new NotFoundException(Instruction.class, instructionId);
        }
    }

    @DeleteMapping(Endpoint.ID)
    public ResponseEntity<?> delete(
            @PathVariable(Endpoint.RECIPES_ID_VAR_NAME) long recipeId,
            @PathVariable(Endpoint.ID_VAR_NAME) long instructionId
    ) {
        instructionService.delete(instructionId, recipeId);
        return ResponseEntity.noContent().build();
    }

}
