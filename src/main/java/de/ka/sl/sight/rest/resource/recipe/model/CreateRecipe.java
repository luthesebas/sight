package de.ka.sl.sight.rest.resource.recipe.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Data
public class CreateRecipe {

    @ApiModelProperty(value = "The recipe title", required = true)
    @NotNull(message = "Title may not be null")
    @NotEmpty(message = "Title may not be empty")
    private String title;
    @ApiModelProperty(value = "The recipe description", required = true)
    private String description;

}
