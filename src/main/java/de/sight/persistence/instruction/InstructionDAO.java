package de.sight.persistence.instruction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Repository
public interface InstructionDAO extends JpaRepository<InstructionEntity, Long> {

    List<InstructionEntity> findAllByRecipeId (long recipeId);

    Optional<InstructionEntity> findByIdAndRecipeId (long id, long recipeId);

    void deleteByIdAndRecipeId (long id, long recipeId);

}
