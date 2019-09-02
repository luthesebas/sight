package de.ka.sl.sight.rest.resource.config;

/** @author Sebastian Luther (@url(https://github.com/luthesebas)) */
public class InstructionConfig extends ResourceConfig {

    public static final String NAME = "instructions";
    public static final String ROOT = "/" + NAME;

    public static final String ID_NAME = "instructionId";
    public static final String ID_PATH = "/{" + ID_NAME + ":" + ID_PATTERN + "}";

}
