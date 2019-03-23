package de.ka.sl.sight.rest.resource;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.Resources;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
public interface IResourceMapper<M, N> extends ResourceAssembler<M, Resource<N>> {

    Resource<N> toResource(M model);

    default Resources<Resource<N>> toResource(List<M> models, Class controller) {
        List<Resource<N>> resources = models.stream()
                .map(this::toResource)
                .collect(Collectors.toList());
        return new Resources<>(resources, linkTo(controller.getClass()).withSelfRel());
    }

}
