package de.ka.sl.sight.rest.general.exception;

import lombok.Data;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
@Data
public class ExceptionMessage {

    private final String message;

    //--------------------------------------
    // Constructors
    //--------------------------------------

    public ExceptionMessage(String message) {
        this.message = message;
    }

}
