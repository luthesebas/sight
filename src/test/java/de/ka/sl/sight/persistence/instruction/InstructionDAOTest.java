package de.ka.sl.sight.persistence.instruction;

import de.ka.sl.sight.persistence.Auditor;
import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

import static org.mockito.Mockito.when;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@RunWith(SpringRunner.class)
@EnableTransactionManagement
@EnableJpaAuditing
@DataJpaTest
public class InstructionDAOTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private InstructionDAO instructionDAO;

    @MockBean
    private Auditor auditor;

    @Before
    public void setUp () {
        when(auditor.getCurrentAuditor()).thenReturn(Optional.of("editor"));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void expectExceptionAtStepDuplication () {
        RecipeEntity recipe = new RecipeEntity();
        recipe.setTitle("Recipe Title");
        recipe.setDescription("Recipe Description");
        entityManager.persistAndFlush(recipe);

        instructionDAO.save(aInstruction(recipe, 1)); // valid
        instructionDAO.save(aInstruction(recipe, 1)); // Exception expected
    }

    private InstructionEntity aInstruction (RecipeEntity recipe, int step) {
        InstructionEntity instruction = new InstructionEntity();
        instruction.setRecipe(recipe);
        instruction.setStep(step);
        instruction.setDescription("Step Description");
        instruction.setDurationInSeconds(120);
        return instruction;
    }

}