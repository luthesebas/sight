package de.ka.sl.sight.rest.resource.recipe;

import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.rest.resource.IMapper;
import de.ka.sl.sight.rest.resource.instruction.InstructionController;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
@Component
public final class RecipeMapper implements IMapper<RecipeEntity, RecipeEntity> {

    @Override
    public Resource<RecipeEntity> toResource(RecipeEntity recipeEntity) {
        Resource<RecipeEntity> resource = new Resource<>(recipeEntity);
        resource.add(linkTo(RecipeController.class).slash(recipeEntity.getId()).withSelfRel());
        resource.add(linkTo(InstructionController.class, recipeEntity.getId()).withRel("instructions"));
        resource.add(linkTo(RecipeController.class).withRel("recipes"));
        return resource;
    }

    @Override
    public RecipeEntity mapToModel(RecipeEntity model) {
        return model;
    }

}
