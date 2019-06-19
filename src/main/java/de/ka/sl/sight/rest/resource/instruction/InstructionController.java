package de.ka.sl.sight.rest.resource.instruction;

import de.ka.sl.sight.persistence.instruction.InstructionEntity;
import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.rest.general.exception.AppException;
import de.ka.sl.sight.rest.general.exception.NotFoundException;
import de.ka.sl.sight.rest.resource.config.InstructionConfig;
import de.ka.sl.sight.rest.resource.config.RecipeConfig;
import de.ka.sl.sight.rest.resource.config.ResourceConfig;
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
@RequestMapping(RecipeConfig.ROOT + RecipeConfig.ID_PATH + InstructionConfig.ROOT)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class InstructionController {

   private final RecipeService recipeService;
   private final InstructionService instructionService;
   private final InstructionResourceMapper instructionMapper;

   private static <T> URI uriOf (Resource<T> resource) throws URISyntaxException {
      return new URI(resource.getId().expand().getHref());
   }

   @PostMapping()
   public ResponseEntity<?> create (
      @PathVariable(RecipeConfig.ID_NAME) long recipeId, @RequestBody InstructionEntity data
   ) throws AppException, URISyntaxException {
      Optional<RecipeEntity> recipe = recipeService.read(recipeId);
      if (recipe.isPresent()) {
         InstructionEntity instruction = instructionService.create(data, recipe.get());
         Resource<InstructionEntity> resource = instructionMapper.toResource(instruction);
         return ResponseEntity.created(uriOf(resource)).body(resource);
      } else {
         throw new NotFoundException(RecipeEntity.class, recipeId);
      }
   }

   @PutMapping(ResourceConfig.ID)
   public ResponseEntity<?> update (
      @PathVariable(RecipeConfig.ID_NAME) long recipeId,
      @PathVariable(ResourceConfig.ID_NAME) long instructionId,
      @RequestBody InstructionEntity data
   ) throws AppException, URISyntaxException {
      Optional<RecipeEntity> optional = recipeService.read(recipeId);
      if (optional.isPresent()) {
         InstructionEntity instruction = instructionService.update(instructionId, data, optional.get());
         Resource<InstructionEntity> resource = instructionMapper.toResource(instruction);
         return ResponseEntity.created(uriOf(resource)).body(resource);
      } else {
         throw new NotFoundException(RecipeEntity.class, recipeId);
      }
   }

   @GetMapping()
   public Resources<Resource<InstructionEntity>> read (
      @PathVariable(RecipeConfig.ID_NAME) long recipeId
   ) throws NotFoundException {
      List<InstructionEntity> instructions = instructionService.read(recipeId);
      if (instructions != null && !instructions.isEmpty()) {
         return instructionMapper.toResource(instructions, InstructionController.class, recipeId);
      } else {
         throw new NotFoundException(InstructionEntity.class);
      }
   }

   @GetMapping(ResourceConfig.ID)
   public Resource<InstructionEntity> read (
      @PathVariable(RecipeConfig.ID_NAME) long recipeId, @PathVariable(ResourceConfig.ID_NAME) long instructionId
   ) throws AppException {
      Optional<InstructionEntity> optional = instructionService.read(instructionId, recipeId);
      if (optional.isPresent()) {
         return instructionMapper.toResource(optional.get());
      } else {
         throw new NotFoundException(Instruction.class, instructionId);
      }
   }

   @DeleteMapping(ResourceConfig.ID)
   public ResponseEntity<?> delete (
      @PathVariable(RecipeConfig.ID_NAME) long recipeId, @PathVariable(ResourceConfig.ID_NAME) long instructionId
   ) {
      instructionService.delete(instructionId, recipeId);
      return ResponseEntity.noContent().build();
   }

}
