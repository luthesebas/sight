package de.sight.rest.resource.recipe.service;

import de.sight.rest.resource.recipe.model.CreateRecipe;
import de.sight.rest.resource.recipe.model.UpdateRecipe;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecipeValidator {

    public boolean isValid (CreateRecipe data) {
        //TODO Implement isValid
        return true;
    }

    public boolean isValid (UpdateRecipe data) {
        //TODO Implement isValid
        return true;
    }

}
