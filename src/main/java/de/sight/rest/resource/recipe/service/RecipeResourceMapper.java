package de.sight.rest.resource.recipe.service;

import de.sight.persistence.recipe.RecipeEntity;
import de.sight.rest.config.InstructionConfig;
import de.sight.rest.config.RecipeConfig;
import de.sight.rest.resource.IResourceMapper;
import de.sight.rest.resource.instruction.InstructionController;
import de.sight.rest.resource.recipe.RecipeController;
import de.sight.rest.resource.recipe.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/** @author Sebastian Luther (@url(https://github.com/luthesebas)) */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecipeResourceMapper implements IResourceMapper<RecipeEntity, Recipe> {

    private final RecipeMapper recipeMapper;

    @Override
    public Resource<Recipe> toResource (RecipeEntity recipeEntity) {
        Recipe model = recipeMapper.mapToModel(recipeEntity);
        Resource<Recipe> resource = new Resource<>(model);
        resource.add(linkTo(RecipeController.class).slash(recipeEntity.getId()).withSelfRel());
        resource.add(linkTo(InstructionController.class, recipeEntity.getId()).withRel(InstructionConfig.RESOURCE));
        resource.add(linkTo(RecipeController.class).withRel(RecipeConfig.RESOURCE));
        return resource;
    }

}
