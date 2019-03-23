package de.ka.sl.sight.rest.resource;

import org.springframework.hateoas.Resource;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
public class UriFactory {

    private UriFactory() {

    }

    //--------------------------------------
    // Methods
    //--------------------------------------

    public static <T> URI uriOf(Resource<T> resource) throws URISyntaxException {
        return new URI(resource.getId().expand().getHref());
    }

}
