package de.sight.exception;

/** @author Sebastian Luther (https://github.com/luthesebas) */
public class UnprocessableException extends AppException {

    public UnprocessableException () {
        super("Action could not be proceeded due to semantically erroneous data");
    }

}
