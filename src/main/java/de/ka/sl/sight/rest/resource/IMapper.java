package de.ka.sl.sight.rest.resource;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
public interface IMapper<M, N> extends ResourceAssembler<M, Resource<N>> {

    Resource<N> toResource(M model);

    N mapToModel(M model);

    default List<N> mapToModel(List<M> models) {
        return models.stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

}
