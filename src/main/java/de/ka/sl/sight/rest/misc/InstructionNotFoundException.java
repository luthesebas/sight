package de.ka.sl.sight.rest.misc;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
public class InstructionNotFoundException extends Exception {

    public InstructionNotFoundException(long id) {
        super(String.format("Could not find instruction with id '%s'.", id));
    }

}
