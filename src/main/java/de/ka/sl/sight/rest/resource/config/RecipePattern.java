package de.ka.sl.sight.rest.resource.config;

/** @author Sebastian Luther (@url(https://github.com/luthesebas)) */
public class RecipePattern extends ResourcePattern {

    public static final String NAME = "recipe";
    public static final String NAME_PLURAL = "recipes";

    public static final String RESOURCE_NAME = NAME_PLURAL;
    public static final String ROOT = "/" + RESOURCE_NAME;

    public static final String ID_NAME = "recipeId";
    public static final String ID_PATH = "/{" + ID_NAME + ":" + ID_PATTERN + "}";

}
