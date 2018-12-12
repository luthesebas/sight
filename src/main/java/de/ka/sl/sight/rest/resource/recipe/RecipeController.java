package de.ka.sl.sight.rest.resource.recipe;

import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.persistence.recipe.RecipeDAO;
import de.ka.sl.sight.rest.general.AppException;
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
@RequestMapping(value = "/recipes")
public final class RecipeController {

    private RecipeDAO recipeDAO;
    private RecipeMapper recipeMapper;

    //--------------------------------------
    // Constructors
    //--------------------------------------

    public RecipeController(RecipeDAO recipeDAO, RecipeMapper recipeMapper) {
        this.recipeDAO = recipeDAO;
        this.recipeMapper = recipeMapper;
    }

    //--------------------------------------
    // Methods
    //--------------------------------------

    @GetMapping
    public Resources<Resource<RecipeEntity>> all() {
        List<Resource<RecipeEntity>> recipes = recipeDAO.findAll().stream()
                .map(recipeMapper::toResource)
                .collect(Collectors.toList());
        return new Resources<>(recipes, linkTo(RecipeController.class).withSelfRel());
    }

    @GetMapping("/{id:[0-9]+}")
    public Resource<RecipeEntity> read(@PathVariable long id) throws AppException {
        return recipeMapper.toResource(
                recipeDAO.findById(id).orElseThrow(() -> new RecipeNotFoundException(id)));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RecipeEntity recipeEntity) throws URISyntaxException {
        Resource<RecipeEntity> resource = recipeMapper.toResource(recipeDAO.save(recipeEntity));
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<?> update(@RequestBody RecipeEntity newRecipeEntity, @PathVariable long id) throws URISyntaxException {
        RecipeEntity updatedRecipeEntity = recipeDAO.findById(id)
                .map(recipe -> {
                    recipe.updateFrom(newRecipeEntity);
                    return recipeDAO.save(recipe);
                })
                .orElseGet(() -> {
                    //newRecipeEntity.setId(id);
                    return recipeDAO.save(newRecipeEntity);
                });
        Resource<RecipeEntity> resource = recipeMapper.toResource(updatedRecipeEntity);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (recipeDAO.existsById(id))
            recipeDAO.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
