package de.ka.sl.sight.rest.resource.recipe;

import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.rest.resource.IMapper;
import org.springframework.stereotype.Component;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
@Component
public final class RecipeMapper implements IMapper<RecipeEntity, RecipeEntity> {

   @Override
   public RecipeEntity mapToModel (RecipeEntity model) {
      return model;
   }

}
