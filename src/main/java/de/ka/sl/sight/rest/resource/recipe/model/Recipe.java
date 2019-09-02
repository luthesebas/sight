package de.ka.sl.sight.rest.resource.recipe.model;

import de.ka.sl.sight.rest.resource.instruction.model.Instruction;
import lombok.Data;

import java.util.Date;
import java.util.List;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Data
public class Recipe {

    private long id;

    private String title;
    private String description;
    private List<Instruction> instructions;

    private Date modifiedDate;
    private String createdBy;
    private Date createdDate;

}
