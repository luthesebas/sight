package de.sight.rest.config;

/** @author Sebastian Luther (@url(https://github.com/luthesebas)) */
public abstract class InstructionConfig {
    //TODO Extract to application.properties

    public static final String NAME = "instruction";
    public static final String RESOURCE = "instructions";

    public static final String ID_NAME = "instructionId";
    public static final String ID_PATTERN = "[0-9]+";

    /*--------------------
    PATHS
     -------------------*/

    public static final String ROOT = "/" + RESOURCE;
    public static final String ONE = "/{" + ID_NAME + ":" + ID_PATTERN + "}";

}
