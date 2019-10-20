package de.ka.sl.sight.rest.resource.instruction;

import de.ka.sl.sight.persistence.instruction.InstructionEntity;
import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.rest.general.exception.AppException;
import de.ka.sl.sight.rest.general.exception.NotFoundException;
import de.ka.sl.sight.rest.general.pattern.InstructionPattern;
import de.ka.sl.sight.rest.general.pattern.RecipePattern;
import de.ka.sl.sight.rest.resource.instruction.model.Instruction;
import de.ka.sl.sight.rest.resource.instruction.service.InstructionResourceMapper;
import de.ka.sl.sight.rest.resource.instruction.service.InstructionService;
import de.ka.sl.sight.rest.resource.recipe.service.RecipeService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@RestController
@RequestMapping(RecipePattern.ROOT + RecipePattern.ONE + InstructionPattern.ROOT)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class InstructionController {

    private final RecipeService recipeService;
    private final InstructionService instructionService;
    private final InstructionResourceMapper instructionMapper;

    private static <T> URI uriOf (Resource<T> resource) throws URISyntaxException {
        return new URI(resource.getId().expand().getHref());
    }

    @PostMapping()
    public ResponseEntity<Resource<Instruction>> create (
        @ApiParam(value = "Id of the corresponding recipe", required = true, example = "1")
        @PathVariable(RecipePattern.ID_NAME) long recipeId,
        @RequestBody InstructionEntity data
    ) throws AppException, URISyntaxException {
        Optional<RecipeEntity> recipe = recipeService.read(recipeId);
        if (recipe.isPresent()) {
            InstructionEntity instruction = instructionService.create(data, recipe.get());
            Resource<Instruction> resource = instructionMapper.toResource(instruction);
            return ResponseEntity.created(uriOf(resource)).body(resource);
        } else {
            throw new NotFoundException(RecipePattern.NAME, recipeId);
        }
    }

    @PutMapping(InstructionPattern.ONE)
    public ResponseEntity<Resource<Instruction>> update (
        @ApiParam(value = "Id of the corresponding recipe", required = true, example = "1")
        @PathVariable(RecipePattern.ID_NAME) long recipeId,
        @ApiParam(value = "Id of the instruction to update", required = true, example = "1")
        @PathVariable(InstructionPattern.ID_NAME) long instructionId,
        @RequestBody InstructionEntity data
    ) throws AppException, URISyntaxException {
        Optional<RecipeEntity> optional = recipeService.read(recipeId);
        if (optional.isPresent()) {
            InstructionEntity instruction = instructionService.update(instructionId, data, optional.get());
            Resource<Instruction> resource = instructionMapper.toResource(instruction);
            return ResponseEntity.created(uriOf(resource)).body(resource);
        } else {
            throw new NotFoundException(RecipePattern.NAME, recipeId);
        }
    }

    @GetMapping()
    public Resources<Resource<Instruction>> read (
        @ApiParam(value = "Id of the corresponding recipe", required = true, example = "1")
        @PathVariable(RecipePattern.ID_NAME) long recipeId
    ) throws NotFoundException {
        List<InstructionEntity> instructions = instructionService.read(recipeId);
        if (instructions != null && !instructions.isEmpty()) {
            return instructionMapper.toResource(instructions, InstructionController.class, recipeId);
        } else {
            throw new NotFoundException(InstructionPattern.NAME_PLURAL);
        }
    }

    @GetMapping(InstructionPattern.ONE)
    public Resource<Instruction> read (
        @ApiParam(value = "Id of the corresponding recipe", required = true, example = "1")
        @PathVariable(RecipePattern.ID_NAME) long recipeId,
        @ApiParam(value = "Id of the instruction to read", required = true, example = "1")
        @PathVariable(InstructionPattern.ID_NAME) long instructionId
    ) throws AppException {
        Optional<InstructionEntity> optional = instructionService.read(instructionId, recipeId);
        if (optional.isPresent()) {
            return instructionMapper.toResource(optional.get());
        } else {
            throw new NotFoundException(InstructionPattern.NAME, instructionId);
        }
    }

    @DeleteMapping(InstructionPattern.ONE)
    public ResponseEntity delete (
        @ApiParam(value = "Id of the corresponding recipe", required = true, example = "1")
        @PathVariable(RecipePattern.ID_NAME) long recipeId,
        @ApiParam(value = "Id of the instruction to delete", required = true, example = "1")
        @PathVariable(InstructionPattern.ID_NAME) long instructionId
    ) {
        instructionService.delete(instructionId, recipeId);
        return ResponseEntity.noContent().build();
    }

}
