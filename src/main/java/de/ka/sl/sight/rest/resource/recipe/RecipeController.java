package de.ka.sl.sight.rest.resource.recipe;

import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.rest.general.AppException;
import de.ka.sl.sight.rest.general.Path;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
@RestController
@RequestMapping(value = Path.RECIPES)
public final class RecipeController {

    private RecipeService recipeService;
    private RecipeMapper recipeMapper;

    //--------------------------------------
    // Constructors
    //--------------------------------------

    public RecipeController(RecipeService recipeService, RecipeMapper recipeMapper) {
        this.recipeService = recipeService;
        this.recipeMapper = recipeMapper;
    }

    //--------------------------------------
    // Methods
    //--------------------------------------

    @GetMapping
    public Resources<Resource<RecipeEntity>> all() {
        List<Resource<RecipeEntity>> recipes = recipeService.all().stream()
                .map(recipeMapper::toResource)
                .collect(Collectors.toList());
        return new Resources<>(recipes, linkTo(RecipeController.class).withSelfRel());
    }

    @GetMapping(Path.ID)
    public Resource<RecipeEntity> read(@PathVariable long id) throws AppException {
        return recipeMapper.toResource(
                recipeService.get(id).orElseThrow(() -> new RecipeNotFoundException(id)));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RecipeEntity recipeEntity) throws URISyntaxException {
        Resource<RecipeEntity> resource = recipeMapper.toResource(recipeService.save(recipeEntity));
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping(Path.ID)
    public ResponseEntity<?> update(@RequestBody RecipeEntity newRecipeEntity, @PathVariable long id) throws URISyntaxException {
        RecipeEntity updatedRecipeEntity = recipeService.get(id)
                .map(recipe -> {
                    recipe.updateFrom(newRecipeEntity);
                    return recipeService.save(recipe);
                })
                .orElseGet(() -> {
                    //newRecipeEntity.setId(id);
                    return recipeService.save(newRecipeEntity);
                });
        Resource<RecipeEntity> resource = recipeMapper.toResource(updatedRecipeEntity);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping(Path.ID)
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (recipeService.exists(id))
            recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
