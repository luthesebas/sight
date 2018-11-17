package de.ka.sl.sight.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 */
public interface InstructionDAO extends JpaRepository<Instruction, Long> {

    List<Instruction> findAllByRecipeId(long recipeId);

}
