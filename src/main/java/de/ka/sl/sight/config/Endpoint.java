package de.ka.sl.sight.config;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
public class Endpoint {

    //TODO Extract values like 'recipes' and 'instructions' from a config file

    private Endpoint() {

    }

    public final static String RECIPES = "/recipes";
    public final static String RECIPES_ID = RECIPES + "/{recipeId:[0-9]+}";

    public final static String INSTRUCTIONS = "/instructions";

    public final static String ID_VAR_NAME = "id";
    public final static String ID = "/{id:[0-9]+}";

}
