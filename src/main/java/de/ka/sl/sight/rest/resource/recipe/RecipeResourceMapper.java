package de.ka.sl.sight.rest.resource.recipe;

import de.ka.sl.sight.config.Endpoint;
import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.rest.resource.IResourceMapper;
import de.ka.sl.sight.rest.resource.instruction.InstructionController;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
@Component
public class RecipeResourceMapper implements IResourceMapper<RecipeEntity, RecipeEntity> {

    @Override
    public Resource<RecipeEntity> toResource(RecipeEntity recipeEntity) {
        Resource<RecipeEntity> resource = new Resource<>(recipeEntity);
        resource.add(linkTo(RecipeController.class).slash(recipeEntity.getId()).withSelfRel());
        resource.add(linkTo(InstructionController.class, recipeEntity.getId())
                .withRel(Endpoint.INSTRUCTIONS_RESOURCE_NAME));
        resource.add(linkTo(RecipeController.class)
                .withRel(Endpoint.RECIPES_RESOURCE_NAME));
        return resource;
    }

}
