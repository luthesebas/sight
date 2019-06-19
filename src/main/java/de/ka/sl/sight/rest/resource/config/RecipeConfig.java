package de.ka.sl.sight.rest.resource.config;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
public class RecipeConfig extends ResourceConfig {

    public final static String NAME = "recipes";
    public final static String ROOT = "/" + NAME;

    public final static String ID_NAME = "recipeId";
    public final static String ID_PATH = "/{" + ID_NAME + ":" + ID_PATTERN + "}";

}
