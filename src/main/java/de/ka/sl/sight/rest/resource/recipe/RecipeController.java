package de.ka.sl.sight.rest.resource.recipe;

import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.rest.general.exception.AppException;
import de.ka.sl.sight.rest.general.exception.NotFoundException;
import de.ka.sl.sight.rest.general.exception.UnprocessableException;
import de.ka.sl.sight.rest.resource.UriFactory;
import de.ka.sl.sight.rest.resource.config.RecipePattern;
import de.ka.sl.sight.rest.resource.recipe.model.CreateRecipe;
import de.ka.sl.sight.rest.resource.recipe.model.Recipe;
import de.ka.sl.sight.rest.resource.recipe.model.UpdateRecipe;
import de.ka.sl.sight.rest.resource.recipe.service.RecipeResourceMapper;
import de.ka.sl.sight.rest.resource.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@RestController
@RequestMapping(value = RecipePattern.ROOT)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class RecipeController {

    private final RecipeService recipeService;
    private final RecipeResourceMapper resourceMapper;

    @PostMapping
    public ResponseEntity<Resource<Recipe>> create (
        @Valid @RequestBody CreateRecipe data
    ) throws URISyntaxException, UnprocessableException {
        RecipeEntity recipe = recipeService.create(data);
        Resource<Recipe> resource = resourceMapper.toResource(recipe);
        return ResponseEntity.created(UriFactory.of(resource)).body(resource);
    }

    @PutMapping(RecipePattern.ONE)
    public ResponseEntity<Resource<Recipe>> update (
        @PathVariable(RecipePattern.ID_NAME) long recipeId,
        @RequestBody UpdateRecipe data
    ) throws URISyntaxException, UnprocessableException {
        RecipeEntity recipe = recipeService.update(recipeId, data);
        Resource<Recipe> resource = resourceMapper.toResource(recipe);
        if (recipe.getId() != recipeId) {
            return ResponseEntity.created(UriFactory.of(resource)).body(resource);
        } else {
            return ResponseEntity.ok(resource);
        }
    }

    @GetMapping
    public ResponseEntity<Resources<Resource<Recipe>>> read () throws NotFoundException {
        List<RecipeEntity> recipes = recipeService.read();
        if (recipes != null && !recipes.isEmpty()) {
            return ResponseEntity.ok(resourceMapper.toResource(recipes, RecipeController.class));
        } else {
            throw new NotFoundException(RecipePattern.NAME_PLURAL);
        }
    }

    @GetMapping(RecipePattern.ONE)
    public ResponseEntity<Resource<Recipe>> read (
        @PathVariable(RecipePattern.ID_NAME) long recipeId
    ) throws AppException {
        Optional<RecipeEntity> recipe = recipeService.read(recipeId);
        if (recipe.isPresent()) {
            return ResponseEntity.ok(resourceMapper.toResource(recipe.get()));
        } else {
            throw new NotFoundException(RecipePattern.NAME, recipeId);
        }
    }

    @DeleteMapping(RecipePattern.ONE)
    public ResponseEntity delete (
        @PathVariable(RecipePattern.ID_NAME) long recipeId
    ) {
        recipeService.delete(recipeId);
        return ResponseEntity.noContent().build();
    }

}
