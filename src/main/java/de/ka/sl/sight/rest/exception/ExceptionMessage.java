package de.ka.sl.sight.rest.exception;

import lombok.Data;

/** @author Sebastian Luther (@url(https://github.com/luthesebas)) */
@Data
public class ExceptionMessage {

    private final String error;

    public ExceptionMessage (String error) {
        this.error = error;
    }

}
