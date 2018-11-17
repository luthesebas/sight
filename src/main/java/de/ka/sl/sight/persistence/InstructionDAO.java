package de.ka.sl.sight.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface InstructionDAO extends JpaRepository<Instruction, Long> {

    List<Instruction> findAllByRecipeId(long recipeId);

    Optional<Instruction> findByIdAndRecipeId(long id, long recipeId);

}
