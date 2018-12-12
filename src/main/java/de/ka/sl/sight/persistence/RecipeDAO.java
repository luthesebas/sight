package de.ka.sl.sight.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
@Repository
public interface RecipeDAO extends JpaRepository<Recipe, Long> {

}
