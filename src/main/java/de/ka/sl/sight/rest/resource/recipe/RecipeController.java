package de.ka.sl.sight.rest.resource.recipe;

import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.rest.general.exception.AppException;
import de.ka.sl.sight.rest.general.exception.NotFoundException;
import de.ka.sl.sight.rest.general.exception.UnprocessableException;
import de.ka.sl.sight.rest.resource.UriFactory;
import de.ka.sl.sight.rest.resource.config.RecipeConfig;
import de.ka.sl.sight.rest.resource.config.ResourceConfig;
import de.ka.sl.sight.rest.resource.recipe.model.CreateRecipe;
import de.ka.sl.sight.rest.resource.recipe.model.UpdateRecipe;
import de.ka.sl.sight.rest.resource.recipe.service.RecipeResourceMapper;
import de.ka.sl.sight.rest.resource.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@RestController
@RequestMapping(value = RecipeConfig.ROOT)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class RecipeController {

    private final RecipeService recipeService;
    private final RecipeResourceMapper mapper;

    @PostMapping
    public ResponseEntity<?> create (
        @Valid @RequestBody CreateRecipe data
    ) throws URISyntaxException, UnprocessableException {
        RecipeEntity recipe = recipeService.create(data);
        Resource<RecipeEntity> resource = mapper.toResource(recipe);
        return ResponseEntity.created(UriFactory.of(resource)).body(resource);
    }

    @PutMapping(ResourceConfig.ID)
    public ResponseEntity<?> update (
        @PathVariable(ResourceConfig.ID_NAME) long recipeId,
        @RequestBody UpdateRecipe data
    ) throws URISyntaxException, UnprocessableException {
        RecipeEntity recipe = recipeService.update(recipeId, data);
        Resource<RecipeEntity> resource = mapper.toResource(recipe);
        if (recipe.getId() != recipeId) {
            return ResponseEntity.created(UriFactory.of(resource)).body(resource);
        } else {
            return ResponseEntity.ok(resource);
        }
    }

    @GetMapping
    public ResponseEntity<?> read () throws NotFoundException {
        List<RecipeEntity> recipes = recipeService.read();
        if (recipes != null && !recipes.isEmpty()) {
            return ResponseEntity.ok(mapper.toResource(recipes, RecipeController.class));
        } else {
            throw new NotFoundException(RecipeEntity.class);
        }
    }

    @GetMapping(ResourceConfig.ID)
    public ResponseEntity<?> read (
        @PathVariable(ResourceConfig.ID_NAME) long recipeId
    ) throws AppException {
        Optional<RecipeEntity> recipe = recipeService.read(recipeId);
        if (recipe.isPresent()) {
            return ResponseEntity.ok(mapper.toResource(recipe.get()));
        } else {
            throw new NotFoundException(RecipeEntity.class, recipeId);
        }
    }

    @DeleteMapping(ResourceConfig.ID)
    public ResponseEntity<?> delete (
        @PathVariable(ResourceConfig.ID_NAME) long recipeId
    ) {
        recipeService.delete(recipeId);
        return ResponseEntity.noContent().build();
    }

}
