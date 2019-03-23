package de.ka.sl.sight.config;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
public class Endpoint {

    private Endpoint() {

    }

    //TODO Extract values from a config file
    public final static String RECIPES = "/recipes";

    public final static String ID = "/{id:[0-9]+}";

}
