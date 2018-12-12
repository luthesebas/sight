package de.ka.sl.sight.rest.resource.instruction;

import de.ka.sl.sight.rest.general.AppException;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
public final class InstructionNotFoundException extends AppException {

    public InstructionNotFoundException(long id) {
        super(String.format("Could not find instruction with id '%s'.", id));
    }

}
