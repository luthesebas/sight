package de.ka.sl.sight.rest;

import de.ka.sl.sight.persistence.Recipe;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *
 */
@Component
public class RecipeMapper implements ResourceAssembler<Recipe, Resource<Recipe>> {

    @Override
    public Resource<Recipe> toResource(Recipe recipe) {
        Resource<Recipe> resource = new Resource<>(recipe);
        resource.add(linkTo(RecipeController.class).slash(recipe.getId()).withSelfRel());
        resource.add(linkTo(RecipeController.class).withRel("recipes"));
        return resource;
    }

}
