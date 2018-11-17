package de.ka.sl.sight.rest.mapper;

import de.ka.sl.sight.persistence.Recipe;
import de.ka.sl.sight.rest.InstructionController;
import de.ka.sl.sight.rest.RecipeController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
@Component
public final class RecipeMapper implements ResourceAssembler<Recipe, Resource<Recipe>> {

    @Override
    public Resource<Recipe> toResource(Recipe recipe) {
        Resource<Recipe> resource = new Resource<>(recipe);
        resource.add(linkTo(RecipeController.class).slash(recipe.getId()).withSelfRel());
        resource.add(linkTo(InstructionController.class, recipe.getId()).withRel("instructions"));
        resource.add(linkTo(RecipeController.class).withRel("recipes"));
        return resource;
    }

}
