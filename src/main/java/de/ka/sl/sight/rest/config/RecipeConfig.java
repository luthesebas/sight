package de.ka.sl.sight.rest.config;

/** @author Sebastian Luther (@url(https://github.com/luthesebas)) */
public abstract class RecipeConfig {

    public static final String NAME = "recipe";
    public static final String RESOURCE = "recipes";

    public static final String ID_NAME = "recipeId";
    public static final String ID_PATTERN = "[0-9]+";

    /*--------------------
    PATHS
     -------------------*/

    public static final String ROOT = "/" + RESOURCE;
    public static final String ONE = "/{" + ID_NAME + ":" + ID_PATTERN + "}";

}
