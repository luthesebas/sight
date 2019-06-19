package de.ka.sl.sight.persistence.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Repository
public interface RecipeDAO extends JpaRepository<RecipeEntity, Long> {}
