package de.ka.sl.sight.rest.resource.recipe.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Data
public class CreateRecipe {

    @NotNull(message = "Title may not be null")
    @NotEmpty(message = "Title may not be empty")
    private String title;
    private String description;

}
