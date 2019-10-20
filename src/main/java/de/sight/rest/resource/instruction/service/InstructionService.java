package de.sight.rest.resource.instruction.service;

import de.sight.persistence.instruction.InstructionDAO;
import de.sight.persistence.instruction.InstructionEntity;
import de.sight.persistence.recipe.RecipeEntity;
import de.sight.rest.config.InstructionConfig;
import de.sight.rest.exception.NotFoundException;
import de.sight.rest.exception.UnprocessableException;
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
public class InstructionService {

    private final InstructionDAO instructionDAO;
    private final InstructionMapper instructionMapper;
    private final InstructionValidator instructionValidator;

    @Transactional(readOnly = true)
    public boolean exists (long instructionId) {
        return instructionDAO.existsById(instructionId);
    }

    @Transactional
    public InstructionEntity create (InstructionEntity data, RecipeEntity recipeEntity) throws UnprocessableException {
        if (instructionValidator.isValid(data)) {
            InstructionEntity instruction = save(data);
            instruction.setRecipe(recipeEntity);
            return instruction;
        } else {
            throw new UnprocessableException();
        }
    }

    @Transactional
    private InstructionEntity save (InstructionEntity entity) {
        return instructionDAO.save(entity);
    }

    @Transactional
    public InstructionEntity update (long instructionId, InstructionEntity data, RecipeEntity recipe) {
        return read(instructionId, recipe.getId()).map(instruction -> {
            update(instruction, data);
            return save(instruction);
        }).orElseGet(() -> {
            // data.setId(id);
            data.setRecipe(recipe);
            return save(data);
        });
    }

    @Transactional(propagation = Propagation.MANDATORY) // An opened transaction must already exist
    private void update (InstructionEntity target, InstructionEntity source) {
        target.setStep(source.getStep());
        target.setDescription(source.getDescription());
        target.setDurationInSeconds(source.getDurationInSeconds());
    }

    @Transactional(readOnly = true)
    public List<InstructionEntity> read (long recipeId) {
        return instructionDAO.findAllByRecipeId(recipeId);
    }

    @Transactional(readOnly = true)
    public Optional<InstructionEntity> read (long instructionId, long recipeId) {
        return instructionDAO.findByIdAndRecipeId(instructionId, recipeId);
    }

    @Transactional
    public void delete (long instructionId, long recipeId) throws NotFoundException {
        if (exists(instructionId)) {
            instructionDAO.deleteByIdAndRecipeId(instructionId, recipeId);
        } else {
            throw new NotFoundException(InstructionConfig.NAME, instructionId);
        }
    }

}
