package de.sight.rest.resource.instruction;

import de.sight.exception.AppException;
import de.sight.exception.NotFoundException;
import de.sight.persistence.instruction.InstructionEntity;
import de.sight.persistence.recipe.RecipeEntity;
import de.sight.rest.config.InstructionConfig;
import de.sight.rest.config.RecipeConfig;
import de.sight.rest.resource.URIFactory;
import de.sight.rest.resource.instruction.model.Instruction;
import de.sight.rest.resource.instruction.service.InstructionResourceMapper;
import de.sight.rest.resource.instruction.service.InstructionService;
import de.sight.rest.resource.recipe.service.RecipeService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Api(
    tags = InstructionConfig.RESOURCE,
    description = "Operations pertaining to instructions"
)
@RestController
@RequestMapping(RecipeConfig.ROOT + RecipeConfig.ONE + InstructionConfig.ROOT)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class InstructionController {

    private final RecipeService recipeService;
    private final InstructionService instructionService;
    private final InstructionResourceMapper instructionMapper;

    @ApiOperation(value = "Create a new instruction")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully created a new instruction"),
        @ApiResponse(code = 400, message = "Invalid arguments supplied"),
        @ApiResponse(code = 401, message = "You are not authorized to create a instruction")
    })
    @PostMapping
    public ResponseEntity<Resource<Instruction>> create (
        @ApiParam(value = "Id of the corresponding recipe", required = true, example = "1")
        @PathVariable(RecipeConfig.ID_NAME) long recipeId,
        @RequestBody InstructionEntity data
    ) throws AppException, URISyntaxException {
        Optional<RecipeEntity> recipe = recipeService.read(recipeId);
        if (recipe.isPresent()) {
            InstructionEntity instruction = instructionService.create(data, recipe.get());
            Resource<Instruction> resource = instructionMapper.toResource(instruction);
            return ResponseEntity.created(URIFactory.of(resource)).body(resource);
        } else {
            throw new NotFoundException(RecipeConfig.NAME, recipeId);
        }
    }

    @ApiOperation(value = "Update a instruction")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully updated the instruction"),
        @ApiResponse(code = 400, message = "Invalid arguments supplied"),
        @ApiResponse(code = 401, message = "You are not authorized to update the instruction"),
        @ApiResponse(code = 403, message = "Accessing the instruction you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The instruction you were trying to reach is not found")
    })
    @PutMapping(InstructionConfig.ONE)
    public ResponseEntity<Resource<Instruction>> update (
        @ApiParam(value = "Id of the corresponding recipe", required = true, example = "1")
        @PathVariable(RecipeConfig.ID_NAME) long recipeId,
        @ApiParam(value = "Id of the instruction to update", required = true, example = "1")
        @PathVariable(InstructionConfig.ID_NAME) long instructionId,
        @RequestBody InstructionEntity data
    ) throws AppException, URISyntaxException {
        Optional<RecipeEntity> optional = recipeService.read(recipeId);
        if (optional.isPresent()) {
            InstructionEntity instruction = instructionService.update(instructionId, data, optional.get());
            Resource<Instruction> resource = instructionMapper.toResource(instruction);
            return ResponseEntity.created(URIFactory.of(resource)).body(resource);
        } else {
            throw new NotFoundException(RecipeConfig.NAME, recipeId);
        }
    }

    @ApiOperation(value = "Read instructions")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved instructions"),
        @ApiResponse(code = 401, message = "You are not authorized to read the instructions"),
        @ApiResponse(code = 403, message = "Accessing the instructions you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The corresponding recipe you were trying to reach is not found")
    })
    @GetMapping
    public Resources<Resource<Instruction>> read (
        @ApiParam(value = "Id of the corresponding recipe", required = true, example = "1")
        @PathVariable(RecipeConfig.ID_NAME) long recipeId
    ) throws AppException {
        List<InstructionEntity> instructions = instructionService.read(recipeId);
        if (instructions != null && !instructions.isEmpty()) {
            return instructionMapper.toResource(instructions, InstructionController.class, recipeId);
        } else {
            throw new NotFoundException(RecipeConfig.NAME, recipeId);
        }
    }

    @ApiOperation(value = "Read a specific instruction")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved the instruction"),
        @ApiResponse(code = 401, message = "You are not authorized to read the instruction"),
        @ApiResponse(code = 403, message = "Accessing the instruction you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The instruction you were trying to reach is not found"),
    })
    @GetMapping(InstructionConfig.ONE)
    public Resource<Instruction> read (
        @ApiParam(value = "Id of the corresponding recipe", required = true, example = "1")
        @PathVariable(RecipeConfig.ID_NAME) long recipeId,
        @ApiParam(value = "Id of the instruction to read", required = true, example = "1")
        @PathVariable(InstructionConfig.ID_NAME) long instructionId
    ) throws AppException {
        Optional<InstructionEntity> optional = instructionService.read(instructionId, recipeId);
        if (optional.isPresent()) {
            return instructionMapper.toResource(optional.get());
        } else {
            throw new NotFoundException(InstructionConfig.NAME, instructionId);
        }
    }

    @ApiOperation(value = "Delete a specific instruction")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully deleted the instruction"),
        @ApiResponse(code = 401, message = "You are not authorized to delete the instruction"),
        @ApiResponse(code = 403, message = "Accessing the instruction you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The instruction you were trying to reach is not found")
    })
    @DeleteMapping(InstructionConfig.ONE)
    public ResponseEntity delete (
        @ApiParam(value = "Id of the corresponding recipe", required = true, example = "1")
        @PathVariable(RecipeConfig.ID_NAME) long recipeId,
        @ApiParam(value = "Id of the instruction to delete", required = true, example = "1")
        @PathVariable(InstructionConfig.ID_NAME) long instructionId
    ) throws AppException {
        instructionService.delete(instructionId, recipeId);
        return ResponseEntity.noContent().build();
    }

}
