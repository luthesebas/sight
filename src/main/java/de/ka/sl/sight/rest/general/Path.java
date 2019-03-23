package de.ka.sl.sight.rest.general;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
public class Path {

    private Path() {

    }

    //TODO Extract values from a config file
    public final static String RECIPES = "/recipes";

    public final static String ID = "/{id:[0-9]+}";

}
