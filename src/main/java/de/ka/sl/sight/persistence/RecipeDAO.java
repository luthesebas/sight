package de.ka.sl.sight.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
public interface RecipeDAO extends JpaRepository<Recipe, Long> {

}
