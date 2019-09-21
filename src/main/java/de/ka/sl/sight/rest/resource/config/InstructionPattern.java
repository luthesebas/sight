package de.ka.sl.sight.rest.resource.config;

/** @author Sebastian Luther (@url(https://github.com/luthesebas)) */
public class InstructionPattern extends ResourcePattern {

    private InstructionPattern () {}

    public static final String NAME = "instruction";
    public static final String NAME_PLURAL = "instructions";

    public static final String RESOURCE_NAME = NAME_PLURAL;
    public static final String ROOT = "/" + RESOURCE_NAME;

    public static final String ID_NAME = "instructionId";
    public static final String ID_PATH = "/{" + ID_NAME + ":" + ID_PATTERN + "}";

}
