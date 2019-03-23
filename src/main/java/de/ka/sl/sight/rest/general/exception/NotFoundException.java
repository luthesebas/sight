package de.ka.sl.sight.rest.general.exception;

import de.ka.sl.sight.persistence.recipe.RecipeEntity;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
public class NotFoundException extends AppException {

    public NotFoundException(Class clazz, long id) {
        super(String.format(
                "Could not find %s with id '%s'.",
                clazz.getSimpleName().toLowerCase(), id
        ));
    }

    public NotFoundException(Class clazz) {
        super(String.format(
                "Could not find any %s",
                clazz.getSimpleName().toLowerCase())
        );
    }
}
