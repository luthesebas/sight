package de.ka.sl.sight.rest.general.exception;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
public class NotFoundException extends AppException {

    public NotFoundException(Class clazz, long id) {
        super(String.format(
                "Could not find %s with id '%s'.",
                clazz.getSimpleName().toLowerCase(), id
        ));
    }

}
