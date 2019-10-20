package de.sight.rest.resource;

import org.springframework.hateoas.Resource;

import java.net.URI;
import java.net.URISyntaxException;

/** @author Sebastian Luther (@url(https://github.com/luthesebas)) */
public class URIFactory {

    private URIFactory () {}

    public static <T> URI of (Resource<T> resource) throws URISyntaxException {
        return new URI(resource.getId().expand().getHref());
    }

}
