package de.sight.rest.resource.instruction.model;

import lombok.Data;

import java.time.LocalDateTime;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Data
public class Instruction {

    private long id;

    private int step;
    private String description;
    private int durationInSeconds;

    private LocalDateTime modifiedDate;
    private String createdBy;
    private LocalDateTime createdDate;

}
