package de.ka.sl.sight.rest.resource.recipe;

import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.rest.resource.IResourceMapper;
import de.ka.sl.sight.rest.resource.config.InstructionConfig;
import de.ka.sl.sight.rest.resource.config.RecipeConfig;
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
                .withRel(InstructionConfig.NAME));
        resource.add(linkTo(RecipeController.class)
                .withRel(RecipeConfig.NAME));
        return resource;
    }

}
