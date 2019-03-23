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

    public Resources<Resource<RecipeEntity>> asResource(List<RecipeEntity> entities, Class controller) {
        List<Resource<RecipeEntity>> resources = entities.stream()
                .map(recipeMapper::toResource)
                .collect(Collectors.toList());
        return new Resources<>(resources, linkTo(controller).withSelfRel());
    }


    public boolean exists(long recipeId) {
        return recipeDAO.existsById(recipeId);
    }

    //--------------------------------------
    // CRUD
    //--------------------------------------

    public RecipeEntity create(RecipeEntity data) {
        return recipeDAO.save(data);
    }

    public RecipeEntity update(long recipeId, RecipeEntity data) {
        return read(recipeId)
                .map(recipe -> {
                    recipe.updateFrom(data);
                    return create(recipe);
                })
                .orElseGet(() -> {
                    //data.setId(id);
                    return create(data);
                });
    }

    public List<RecipeEntity> read() {
        return recipeDAO.findAll();
    }

    public Optional<RecipeEntity> read(long recipeId) {
        return recipeDAO.findById(recipeId);
    }

    public void delete(long recipeId) {
        if (exists(recipeId)) {
            recipeDAO.deleteById(recipeId);
        }
    }

}
