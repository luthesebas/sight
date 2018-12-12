package de.ka.sl.sight.rest.resource.recipe;

import de.ka.sl.sight.persistence.recipe.RecipeDAO;
import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
@Service
public class RecipeService {

    private RecipeDAO recipeDAO;

    //--------------------------------------
    // Constructors
    //--------------------------------------

    @Autowired
    public RecipeService(RecipeDAO recipeDAO) {
        this.recipeDAO = recipeDAO;
    }

    //--------------------------------------
    // Methods
    //--------------------------------------

    public RecipeEntity save(RecipeEntity recipeEntity) {
        return recipeDAO.save(recipeEntity);
    }

    public Optional<RecipeEntity> get(long id) {
        return recipeDAO.findById(id);
    }

    public List<RecipeEntity> all() {
        return recipeDAO.findAll();
    }

    public boolean exists(long id) {
        return recipeDAO.existsById(id);
    }

    public void delete(long id) {
        recipeDAO.deleteById(id);
    }

}
