package de.ka.sl.sight.rest.resource.recipe.service;

import de.ka.sl.sight.persistence.recipe.RecipeDAO;
import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.rest.general.exception.UnprocessableException;
import de.ka.sl.sight.rest.resource.recipe.model.CreateRecipe;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/** @author Sebastian Luther (@url(https://github.com/luthesebas)) */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecipeService {

    private final RecipeDAO recipeDAO;
    private final RecipeMapper recipeMapper;

    @Transactional(readOnly = true)
    public boolean exists (long recipeId) {
        return recipeDAO.existsById(recipeId);
    }

    public boolean isValid (CreateRecipe data) {
        //TODO Implement isValid
        return true;
    }

    @Transactional
    public RecipeEntity create (CreateRecipe data) throws UnprocessableException {
        if (isValid(data)) {
            RecipeEntity recipe = recipeMapper.map(data);
            return save(recipe);
        } else {
            throw new UnprocessableException();
        }
    }

    @Transactional
    private RecipeEntity save (RecipeEntity entity) {
        return recipeDAO.save(entity);
    }

    @Transactional
    public RecipeEntity update (long recipeId, RecipeEntity data) {
        return read(recipeId).map(recipe -> {
            update(recipe, data);
            return save(recipe);
        }).orElseGet(() -> {
            // data.setId(id);
            return save(data);
        });
    }

    @Transactional(propagation = Propagation.MANDATORY) // An opened transaction must already exist
    private void update (RecipeEntity target, RecipeEntity source) {
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
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
