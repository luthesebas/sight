package de.ka.sl.sight.rest.resource.recipe;

import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.rest.general.exception.AppException;
import de.ka.sl.sight.rest.general.exception.NotFoundException;
import de.ka.sl.sight.rest.general.Endpoint;
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

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
@RestController
@RequestMapping(value = Endpoint.RECIPES)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class RecipeController {

    private final RecipeService recipeService;

    //--------------------------------------
    // Methods
    //--------------------------------------

    private static <T> URI uriOf(Resource<T> resource) throws URISyntaxException {
        return new URI(resource.getId().expand().getHref());
    }

    @GetMapping
    public Resources<Resource<RecipeEntity>> all() {
        List<RecipeEntity> recipes = recipeService.all();
        return recipeService.asResource(recipes, RecipeController.class);
    }

    @GetMapping(Endpoint.ID)
    public Resource<RecipeEntity> read(@PathVariable long id) throws AppException {
        Optional<RecipeEntity> entity = recipeService.get(id);
        if (entity.isPresent()) {
            return recipeService.asResource(entity.get());
        } else {
            throw new NotFoundException(RecipeEntity.class, id);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RecipeEntity recipeEntity) throws URISyntaxException {
        RecipeEntity entity = recipeService.save(recipeEntity);
        Resource<RecipeEntity> resource = recipeService.asResource(entity);
        return ResponseEntity.created(uriOf(resource)).body(resource);
    }

    @PutMapping(Endpoint.ID)
    public ResponseEntity<?> update(
            @PathVariable long id,
            @RequestBody RecipeEntity newRecipeEntity
    ) throws URISyntaxException {
        RecipeEntity updatedRecipeEntity = recipeService.update(id, newRecipeEntity);
        Resource<RecipeEntity> resource = recipeService.asResource(updatedRecipeEntity);
        return ResponseEntity.created(uriOf(resource)).body(resource);
    }

    @DeleteMapping(Endpoint.ID)
    public ResponseEntity<?> delete(@PathVariable long id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
