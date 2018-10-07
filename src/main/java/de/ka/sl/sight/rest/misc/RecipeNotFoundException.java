package de.ka.sl.sight.rest.misc;

/**
 *
 */
public class RecipeNotFoundException extends Exception {

    public RecipeNotFoundException(long id) {
        super(String.format("Could not find recipe with id '%s'.", id));
    }

}
