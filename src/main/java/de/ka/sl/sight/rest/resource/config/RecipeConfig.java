package de.ka.sl.sight.rest.resource.config;

/** @author Sebastian Luther (@url(https://github.com/luthesebas)) */
public class RecipeConfig extends ResourceConfig {

    public static final String NAME = "recipes";
    public static final String ROOT = "/" + NAME;

    public static final String ID_NAME = "recipeId";
    public static final String ID_PATH = "/{" + ID_NAME + ":" + ID_PATTERN + "}";

}
