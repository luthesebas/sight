package de.ka.sl.sight.rest.resource.config;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
public class InstructionConfig extends ResourceConfig {

    public final static String NAME = "instructions";
    public final static String ROOT = "/" + NAME;

    public final static String ID_NAME = "instructionId";
    public final static String ID_PATH = "/{" + ID_NAME + ":" + ID_PATTERN + "}";

}
