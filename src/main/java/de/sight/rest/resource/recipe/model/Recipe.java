package de.sight.rest.resource.recipe.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Data
public class Recipe {

    @ApiModelProperty("The unique recipe id")
    private long id;

    @ApiModelProperty("The recipe title")
    private String title;
    @ApiModelProperty("The recipe description")
    private String description;

    @ApiModelProperty("The modification date of the recipe")
    private LocalDateTime modifiedDate;
    @ApiModelProperty("The name of the recipe author")
    private String createdBy;
    @ApiModelProperty("The creation date of the recipe")
    private LocalDateTime createdDate;

}
