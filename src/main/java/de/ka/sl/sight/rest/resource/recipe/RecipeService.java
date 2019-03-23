package de.ka.sl.sight.rest.resource.recipe;

import de.ka.sl.sight.persistence.recipe.RecipeDAO;
import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecipeService {

    private final RecipeDAO recipeDAO;
    private final RecipeMapper recipeMapper;

    //--------------------------------------
    // Methods
    //--------------------------------------

    public Resource<RecipeEntity> asResource(RecipeEntity entity) {
        return recipeMapper.toResource(entity);
    }

    public List<Resource<RecipeEntity>> asResource(List<RecipeEntity> entities) {
        return entities.stream()
                .map(recipeMapper::toResource)
                .collect(Collectors.toList());
    }

    public Resources<Resource<RecipeEntity>> asResource(List<RecipeEntity> entities, Class controller) {
        List<Resource<RecipeEntity>> resources = entities.stream()
                .map(recipeMapper::toResource)
                .collect(Collectors.toList());
        return new Resources<>(resources, linkTo(controller).withSelfRel());
    }


    public List<RecipeEntity> all() {
        return recipeDAO.findAll();
    }

    public boolean exists(long id) {
        return recipeDAO.existsById(id);
    }

    //--------------------------------------
    // CRUD
    //--------------------------------------

    public RecipeEntity create(RecipeEntity recipeEntity) {
        return recipeDAO.save(recipeEntity);
    }

    public RecipeEntity update(long recipeId, RecipeEntity newRecipeEntity) {
        return read(recipeId)
                .map(recipe -> {
                    recipe.updateFrom(newRecipeEntity);
                    return create(recipe);
                })
                .orElseGet(() -> {
                    //newRecipeEntity.setId(id);
                    return create(newRecipeEntity);
                });
    }

    public Optional<RecipeEntity> read(long id) {
        return recipeDAO.findById(id);
    }

    public void delete(long id) {
        if (exists(id)) {
            recipeDAO.deleteById(id);
        }
    }

}
