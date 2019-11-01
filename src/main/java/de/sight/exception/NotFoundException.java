package de.sight.exception;

/** @author Sebastian Luther (@url(https://github.com/luthesebas)) */
public class NotFoundException extends AppException {

    public NotFoundException (String resourceName, long id) {
        super("Could not find a " + resourceName + " with id " + id);
    }

    public NotFoundException (String resourceName) {
        super("Could not find any " + resourceName);
    }

}
