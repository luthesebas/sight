package de.ka.sl.sight.rest;

/**
 *
 */
class RecipeNotFoundException extends Exception {

    RecipeNotFoundException(long id) {
        super(String.format("Could not find recipe with id '%s'.", id));
    }

}
