package de.ka.sl.sight.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
@Repository
public interface InstructionDAO extends JpaRepository<Instruction, Long> {

    List<Instruction> findAllByRecipeId(long recipeId);

    Optional<Instruction> findByIdAndRecipeId(long id, long recipeId);

}
