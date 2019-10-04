package de.ka.sl.sight.rest.resource.recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import de.ka.sl.sight.rest.resource.recipe.model.CreateRecipe;
import de.ka.sl.sight.rest.resource.recipe.model.UpdateRecipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerITest {

    @Autowired
    private MockMvc mvc;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void createRecipe () throws Exception {
        String title = "Title";
        String description = "Description";
        mvc.perform(
            post("/recipes")
                .content(asJsonString(createRecipe(title, description)))
                .contentType(MediaType.APPLICATION_JSON))
           //.andDo(print())
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$").exists())
           .andExpect(jsonPath("$.title").value(title))
           .andExpect(jsonPath("$.description").value(description));
    }
    
    @Transactional
    @Test
    public void updateExistingRecipe () throws Exception {
        RecipeEntity anyExistingRecipe = new RecipeEntity();
        anyExistingRecipe.setTitle("Title Before");
        anyExistingRecipe.setDescription("Description Before");
        entityManager.persist(anyExistingRecipe);
        entityManager.flush();

        long recipesIdToUpdate = 1;
        String title = "Title After";
        String description = "Description After";
        mvc.perform(put("/recipes/" + recipesIdToUpdate)
            .content(asJsonString(updateRecipe(title, description)))
            .contentType(MediaType.APPLICATION_JSON))
           //.andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").exists())
           .andExpect(jsonPath("$.id").value(recipesIdToUpdate))
           .andExpect(jsonPath("$.title").value(title))
           .andExpect(jsonPath("$.description").value(description));
    }

    @Test
    public void updateNonExistingRecipe () throws Exception {
        String title = "Title 404";
        String description = "Description 404";
        mvc.perform(put("/recipes/404")
            .content(asJsonString(updateRecipe(title, description)))
            .contentType(MediaType.APPLICATION_JSON))
           //.andDo(print())
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$").exists())
           .andExpect(jsonPath("$.title").value(title))
           .andExpect(jsonPath("$.description").value(description));
    }

    private static UpdateRecipe updateRecipe (String title, String description) {
        UpdateRecipe recipe = new UpdateRecipe();
        recipe.setTitle(title);
        recipe.setDescription(description);
        return recipe;
    }

    private static CreateRecipe createRecipe (String title, String description) {
        CreateRecipe recipe = new CreateRecipe();
        recipe.setTitle(title);
        recipe.setDescription(description);
        return recipe;
    }

    private static String asJsonString (final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}