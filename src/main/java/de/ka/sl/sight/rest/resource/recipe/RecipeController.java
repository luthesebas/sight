package de.ka.sl.sight.rest.resource.recipe;

import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.rest.exception.AppException;
import de.ka.sl.sight.rest.exception.NotFoundException;
import de.ka.sl.sight.rest.exception.UnprocessableException;
import de.ka.sl.sight.rest.config.RecipeConfig;
import de.ka.sl.sight.rest.resource.URIFactory;
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
@Api(
    tags = RecipeConfig.RESOURCE,
    description = "Operations pertaining to recipes"
)
@RestController
@RequestMapping(value = RecipeConfig.ROOT)
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
        return ResponseEntity.created(URIFactory.of(resource)).body(resource);
    }

    @ApiOperation(value = "Update a recipe")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully updated the recipe"),
        @ApiResponse(code = 400, message = "Invalid arguments supplied"),
        @ApiResponse(code = 401, message = "You are not authorized to update the recipe"),
        @ApiResponse(code = 403, message = "Accessing the recipe you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The recipe you were trying to reach is not found")
    })
    @PutMapping(RecipeConfig.ONE)
    public ResponseEntity<Resource<Recipe>> update (
        @ApiParam(value = "Id of the recipe to update", required = true, example = "1")
        @PathVariable(RecipeConfig.ID_NAME) long recipeId,
        @ApiParam(value = "Details to update of the recipe", required = true)
        @RequestBody UpdateRecipe data
    ) throws URISyntaxException, UnprocessableException {
        RecipeEntity recipe = recipeService.update(recipeId, data);
        Resource<Recipe> resource = resourceMapper.toResource(recipe);
        if (recipe.getId() != recipeId) {
            return ResponseEntity.created(URIFactory.of(resource)).body(resource);
        } else {
            return ResponseEntity.ok(resource);
        }
    }

    @ApiOperation(value = "Read recipes")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved recipes"),
        @ApiResponse(code = 401, message = "You are not authorized to read recipes"),
        @ApiResponse(code = 403, message = "Accessing the recipes you were trying to reach is forbidden")
    })
    @GetMapping
    public ResponseEntity<Resources<Resource<Recipe>>> read () throws NotFoundException {
        List<RecipeEntity> recipes = recipeService.read();
        if (recipes != null && !recipes.isEmpty()) {
            return ResponseEntity.ok(resourceMapper.toResource(recipes, RecipeController.class));
        } else {
            throw new NotFoundException(RecipeConfig.RESOURCE);
        }
    }

    @ApiOperation(value = "Read a specific recipe")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved the recipe"),
        @ApiResponse(code = 401, message = "You are not authorized to read the recipe"),
        @ApiResponse(code = 403, message = "Accessing the recipe you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The recipe you were trying to reach is not found")
    })
    @GetMapping(RecipeConfig.ONE)
    public ResponseEntity<Resource<Recipe>> read (
        @ApiParam(value = "Id of the recipe to read", required = true, example = "1")
        @PathVariable(RecipeConfig.ID_NAME) long recipeId
    ) throws AppException {
        Optional<RecipeEntity> recipe = recipeService.read(recipeId);
        if (recipe.isPresent()) {
            return ResponseEntity.ok(resourceMapper.toResource(recipe.get()));
        } else {
            throw new NotFoundException(RecipeConfig.NAME, recipeId);
        }
    }

    @ApiOperation(value = "Delete a specific recipe")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully deleted the recipe"),
        @ApiResponse(code = 401, message = "You are not authorized to delete the recipe"),
        @ApiResponse(code = 403, message = "Accessing the recipe you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The recipe you were trying to reach is not found")
    })
    @DeleteMapping(RecipeConfig.ONE)
    public ResponseEntity delete (
        @ApiParam(value = "Id of the recipe to delete", required = true, example = "1")
        @PathVariable(RecipeConfig.ID_NAME) long recipeId
    ) throws AppException {
        recipeService.delete(recipeId);
        return ResponseEntity.noContent().build();
    }

}
