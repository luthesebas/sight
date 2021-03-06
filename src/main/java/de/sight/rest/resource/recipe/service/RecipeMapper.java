package de.sight.rest.resource.recipe.service;

import de.sight.persistence.recipe.RecipeEntity;
import de.sight.rest.resource.IModelMapper;
import de.sight.rest.resource.recipe.model.CreateRecipe;
import de.sight.rest.resource.recipe.model.Recipe;
import de.sight.rest.resource.recipe.model.UpdateRecipe;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class RecipeMapper implements IModelMapper<RecipeEntity, Recipe> {

    @Override
    public Recipe mapToModel (final RecipeEntity entity) {
        Recipe recipe = new Recipe();
        recipe.setId(entity.getId());
        recipe.setTitle(entity.getTitle());
        recipe.setDescription(entity.getDescription());
        recipe.setModifiedDate(entity.getModifiedDate());
        recipe.setCreatedBy(entity.getCreatedBy());
        recipe.setCreatedDate(entity.getCreatedDate());
        return recipe;
    }

    public RecipeEntity map (CreateRecipe data) {
        RecipeEntity recipe = new RecipeEntity();
        recipe.setTitle(data.getTitle());
        recipe.setDescription(data.getDescription());
        return recipe;
    }

    public RecipeEntity map (UpdateRecipe data) {
        RecipeEntity recipe = new RecipeEntity();
        recipe.setId(data.getId());
        recipe.setTitle(data.getTitle());
        recipe.setDescription(data.getDescription());
        return recipe;
    }

}
