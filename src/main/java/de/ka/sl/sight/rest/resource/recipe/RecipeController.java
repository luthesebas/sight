package de.ka.sl.sight.rest.resource.recipe;

import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.rest.general.exception.AppException;
import de.ka.sl.sight.rest.general.exception.NotFoundException;
import de.ka.sl.sight.rest.general.exception.UnprocessableException;
import de.ka.sl.sight.rest.general.pattern.RecipePattern;
import de.ka.sl.sight.rest.resource.UriFactory;
import de.ka.sl.sight.rest.resource.recipe.model.CreateRecipe;
import de.ka.sl.sight.rest.resource.recipe.model.Recipe;
import de.ka.sl.sight.rest.resource.recipe.model.UpdateRecipe;
import de.ka.sl.sight.rest.resource.recipe.service.RecipeResourceMapper;
import de.ka.sl.sight.rest.resource.recipe.service.RecipeService;
import io.swagger.annotations.*;
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
@Api(tags = RecipePattern.NAME_PLURAL, description = "Operations pertaining to recipes")
@RestController
@RequestMapping(value = RecipePattern.ROOT)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class RecipeController {

    private final RecipeService recipeService;
    private final RecipeResourceMapper resourceMapper;

    @ApiOperation(value = "Create a new recipe")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully created a new recipe"),
        @ApiResponse(code = 400, message = "Invalid arguments supplied"),
        @ApiResponse(code = 401, message = "You are not authorized to create a recipe")
    })
    @PostMapping
    public ResponseEntity<Resource<Recipe>> create (
        @ApiParam(value = "Details to create a recipe", required = true)
        @Valid @RequestBody CreateRecipe data
    ) throws URISyntaxException, UnprocessableException {
        RecipeEntity recipe = recipeService.create(data);
        Resource<Recipe> resource = resourceMapper.toResource(recipe);
        return ResponseEntity.created(UriFactory.of(resource)).body(resource);
    }

    @ApiOperation(value = "Update a recipe")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully updated the recipe"),
        @ApiResponse(code = 401, message = "You are not authorized to update the recipe"),
        @ApiResponse(code = 403, message = "Accessing the recipe you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The recipe you were trying to reach is not found")
    })
    @PutMapping(RecipePattern.ONE)
    public ResponseEntity<Resource<Recipe>> update (
        @ApiParam(value = "Id of the recipe to update", required = true, example = "1")
        @PathVariable(RecipePattern.ID_NAME) long recipeId,
        @ApiParam(value = "Details to update of the recipe", required = true)
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

    @ApiOperation(value = "Read a list of recipes")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved a list of recipes"),
        @ApiResponse(code = 401, message = "You are not authorized to read recipes"),
        @ApiResponse(code = 403, message = "Accessing the recipes you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The recipes you were trying to reach are not found")
    })
    @GetMapping
    public ResponseEntity<Resources<Resource<Recipe>>> read () throws NotFoundException {
        List<RecipeEntity> recipes = recipeService.read();
        if (recipes != null && !recipes.isEmpty()) {
            return ResponseEntity.ok(resourceMapper.toResource(recipes, RecipeController.class));
        } else {
            throw new NotFoundException(RecipePattern.NAME_PLURAL);
        }
    }

    @ApiOperation(value = "Read a specific recipe")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved the recipe"),
        @ApiResponse(code = 401, message = "You are not authorized to read the recipe"),
        @ApiResponse(code = 403, message = "Accessing the recipe you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The recipe you were trying to reach is not found")
    })
    @GetMapping(RecipePattern.ONE)
    public ResponseEntity<Resource<Recipe>> read (
        @ApiParam(value = "Id of the recipe to read", required = true, example = "1")
        @PathVariable(RecipePattern.ID_NAME) long recipeId
    ) throws AppException {
        Optional<RecipeEntity> recipe = recipeService.read(recipeId);
        if (recipe.isPresent()) {
            return ResponseEntity.ok(resourceMapper.toResource(recipe.get()));
        } else {
            throw new NotFoundException(RecipePattern.NAME, recipeId);
        }
    }

    @ApiOperation(value = "Delete a specific recipe")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully deleted the recipe"),
        @ApiResponse(code = 401, message = "You are not authorized to delete the recipe"),
        @ApiResponse(code = 403, message = "Accessing the recipe you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The recipe you were trying to reach is not found")
    })
    @DeleteMapping(RecipePattern.ONE)
    public ResponseEntity delete (
        @ApiParam(value = "Id of the recipe to delete", required = true, example = "1")
        @PathVariable(RecipePattern.ID_NAME) long recipeId
    ) {
        recipeService.delete(recipeId);
        return ResponseEntity.noContent().build();
    }

}
