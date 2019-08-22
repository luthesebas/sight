package de.ka.sl.sight.rest.resource.recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void createRecipe() throws Exception {
        String title = "Title";
        String description = "Description";
        mvc.perform(
            post("/recipes")
                .content(asJsonString(aRecipe(title, description)))
                .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$").exists())
           .andExpect(jsonPath("$.title").value(title))
           .andExpect(jsonPath("$.description").value(description));
    }

    private static RecipeEntity aRecipe (String title, String description) {
        RecipeEntity recipe = new RecipeEntity();
        recipe.setTitle(title);
        recipe.setDescription(description);
        return recipe;
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}