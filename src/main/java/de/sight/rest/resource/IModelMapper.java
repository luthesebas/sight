package de.sight.rest.resource;

import de.sight.persistence.IEntity;

import java.util.List;
import java.util.stream.Collectors;

/** @author Sebastian Luther (https://github.com/luthesebas) */
public interface IModelMapper<E extends IEntity, M> {

    M mapToModel (E entity);

    default List<M> mapToModel (List<E> entities) {
        return entities.stream().map(this::mapToModel).collect(Collectors.toList());
    }

}
