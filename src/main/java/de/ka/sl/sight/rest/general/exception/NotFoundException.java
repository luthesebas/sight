package de.ka.sl.sight.rest.general.exception;

/** @author Sebastian Luther (@url(https://github.com/luthesebas)) */
public class NotFoundException extends AppException {

    public NotFoundException (Class clazz, long id) {
        super("Could not find a " + clazz.getSimpleName().toLowerCase() + " with id " + id);
    }

    public NotFoundException (Class clazz) {
        super("Could not find any " + clazz.getSimpleName().toLowerCase());
    }

}
