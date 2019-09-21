package de.ka.sl.sight.rest.general.pattern;

/** @author Sebastian Luther (@url(https://github.com/luthesebas)) */
public abstract class RecipePattern {

    public static final String NAME = "recipe";
    public static final String NAME_PLURAL = "recipes";
    public static final String RESOURCE = "recipes";

    public static final String ID_NAME = "recipeId";
    public static final String ID_PATTERN = "[0-9]+";

    /*--------------------
    PATHS
     -------------------*/

    public static final String ROOT = "/" + RESOURCE;
    public static final String ONE = "/{" + ID_NAME + ":" + ID_PATTERN + "}";

}
