package de.ka.sl.sight.rest;

import de.ka.sl.sight.persistence.Recipe;
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
 *
 */
@RestController
@RequestMapping(value = "/recipes")
public class RecipeController {

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
    public Resources<Resource<Recipe>> all() {
        List<Resource<Recipe>> recipes = recipeDAO.findAll().stream()
                .map(recipeMapper::toResource)
                .collect(Collectors.toList());
        return new Resources<>(recipes, linkTo(RecipeController.class).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Recipe recipe) throws URISyntaxException {
        Resource<Recipe> resource = recipeMapper.toResource(recipeDAO.save(recipe));
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping("/{id}")
    public Resource<Recipe> read(@PathVariable long id) throws RecipeNotFoundException {
        return recipeMapper.toResource(
                recipeDAO.findById(id).orElseThrow(() -> new RecipeNotFoundException(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Recipe newRecipe, @PathVariable long id) throws URISyntaxException {
        Recipe updatedRecipe = recipeDAO.findById(id)
                .map(recipe -> {
                    recipe.updateFrom(newRecipe);
                    return recipeDAO.save(recipe);
                })
                .orElseGet(() -> {
                    //newRecipe.setId(id);
                    return recipeDAO.save(newRecipe);
                });
        Resource<Recipe> resource = recipeMapper.toResource(updatedRecipe);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }



}
