package de.ka.sl.sight.rest.resource.recipe;

import de.ka.sl.sight.rest.general.AppException;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
public final class RecipeNotFoundException extends AppException {

    public RecipeNotFoundException(long id) {
        super(String.format("Could not find recipe with id '%s'.", id));
    }

}