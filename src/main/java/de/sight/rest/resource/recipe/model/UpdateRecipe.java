package de.sight.rest.resource.recipe.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Data
public class UpdateRecipe {

    @ApiModelProperty(value = "The unique recipe id", required = true)
    private long id;

    @ApiModelProperty("The recipe title")
    private String title;
    @ApiModelProperty("The recipe description")
    private String description;

}
