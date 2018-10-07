package de.ka.sl.sight.persistence;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 *
 */
@Data
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "instruction")
public final class Instruction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;
    private int durationInSeconds;

    //--------------------------------------
    // Constructors
    //--------------------------------------

    public Instruction() {}

    public Instruction(String description, int durationInSeconds) {
        this.description = description;
        this.durationInSeconds = durationInSeconds;
    }

    //--------------------------------------
    // Methods
    //--------------------------------------

    //--------------------------------------
    // General
    //--------------------------------------

}
