package de.ka.sl.sight.rest;

import de.ka.sl.sight.persistence.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface RecipeDAO extends JpaRepository<Recipe, Long> {

}
