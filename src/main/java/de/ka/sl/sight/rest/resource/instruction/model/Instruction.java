package de.ka.sl.sight.rest.resource.instruction.model;

import lombok.Data;

import java.util.Date;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Data
public class Instruction {

    private long id;

    private int step;
    private String description;
    private int durationInSeconds;

    private Date modifiedDate;
    private String createdBy;
    private Date createdDate;

}
