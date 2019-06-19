package de.ka.sl.sight.rest.resource;

import de.ka.sl.sight.persistence.IEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
public interface IMapper<M extends IEntity, N> {

   N mapToModel (M model);

   default List<N> mapToModel (List<M> models) {
      return models.stream().map(this::mapToModel).collect(Collectors.toList());
   }

}
