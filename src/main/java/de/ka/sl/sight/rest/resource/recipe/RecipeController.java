package de.ka.sl.sight.rest.resource.recipe;

import de.ka.sl.sight.config.Endpoint;
import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.rest.general.exception.AppException;
import de.ka.sl.sight.rest.general.exception.NotFoundException;
import de.ka.sl.sight.rest.resource.UriFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
@RestController
@RequestMapping(value = Endpoint.RECIPES)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class RecipeController {

    private final RecipeService recipeService;
    private final RecipeResourceMapper mapper;

    //--------------------------------------
    // Methods
    //--------------------------------------

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody RecipeEntity data
    ) throws URISyntaxException {
        RecipeEntity recipe = recipeService.create(data);
        Resource<RecipeEntity> resource = mapper.toResource(recipe);
        return ResponseEntity.created(UriFactory.of(resource)).body(resource);
    }

    @PutMapping(Endpoint.ID)
    public ResponseEntity<?> update(
            @PathVariable(Endpoint.ID_VAR_NAME) long recipeId,
            @RequestBody RecipeEntity data
    ) throws URISyntaxException {
        RecipeEntity recipe = recipeService.update(recipeId, data);
        Resource<RecipeEntity> resource = mapper.toResource(recipe);
        return ResponseEntity.created(UriFactory.of(resource)).body(resource);
    }

    @GetMapping
    public Resources<Resource<RecipeEntity>> read() throws NotFoundException {
        List<RecipeEntity> recipes = recipeService.read();
        if (recipes != null && !recipes.isEmpty()) {
            return mapper.toResource(recipes, RecipeController.class);
        } else {
            throw new NotFoundException(RecipeEntity.class);
        }
    }

    @GetMapping(Endpoint.ID)
    public Resource<RecipeEntity> read(
            @PathVariable(Endpoint.ID_VAR_NAME) long recipeId
    ) throws AppException {
        Optional<RecipeEntity> recipe = recipeService.read(recipeId);
        if (recipe.isPresent()) {
            return mapper.toResource(recipe.get());
        } else {
            throw new NotFoundException(RecipeEntity.class, recipeId);
        }
    }

    @DeleteMapping(Endpoint.ID)
    public ResponseEntity<?> delete(
            @PathVariable(Endpoint.ID_VAR_NAME) long recipeId
    ) {
        recipeService.delete(recipeId);
        return ResponseEntity.noContent().build();
    }

}
