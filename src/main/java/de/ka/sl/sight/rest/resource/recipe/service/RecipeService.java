package de.ka.sl.sight.rest.resource.recipe.service;

import de.ka.sl.sight.persistence.recipe.RecipeDAO;
import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.rest.general.exception.UnprocessableException;
import de.ka.sl.sight.rest.resource.recipe.model.CreateRecipe;
import de.ka.sl.sight.rest.resource.recipe.model.UpdateRecipe;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/** @author Sebastian Luther (@url(https://github.com/luthesebas)) */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecipeService {

    private final RecipeDAO recipeDAO;
    private final RecipeMapper recipeMapper;
    private final RecipeValidator recipeValidator;

    @Transactional(readOnly = true)
    public boolean exists (long recipeId) {
        return recipeDAO.existsById(recipeId);
    }

    @Transactional
    public RecipeEntity create (CreateRecipe data) throws UnprocessableException {
        if (recipeValidator.isValid(data)) {
            RecipeEntity recipeToCreate = recipeMapper.map(data);
            return save(recipeToCreate);
        } else {
            throw new UnprocessableException();
        }
    }

    @Transactional
    public RecipeEntity update (long recipeId, UpdateRecipe data) throws UnprocessableException {
        if (recipeValidator.isValid(data)) {
            RecipeEntity recipeToUpdate = recipeMapper.map(data);
            recipeToUpdate.setId(recipeId);
            return save(recipeToUpdate);
        } else {
            throw new UnprocessableException();
        }
    }

    @Transactional
    private RecipeEntity save (RecipeEntity entity) {
        return recipeDAO.save(entity);
    }

    @Transactional(readOnly = true)
    public List<RecipeEntity> read () {
        return recipeDAO.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<RecipeEntity> read (long recipeId) {
        return recipeDAO.findById(recipeId);
    }

    @Transactional
    public void delete (long recipeId) {
        if (exists(recipeId)) {
            recipeDAO.deleteById(recipeId);
        }
    }

}
