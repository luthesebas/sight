package de.ka.sl.sight.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private int step;
    private String description;
    private int durationInSeconds;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    //--------------------------------------
    // Constructors
    //--------------------------------------

    public Instruction() {}

    public Instruction(int step, String description, int durationInSeconds) {
        this.step = step;
        this.description = description;
        this.durationInSeconds = durationInSeconds;
    }

    //--------------------------------------
    // Methods
    //--------------------------------------

    @Override
    public String toString() {
        return "Instruction{" +
                "id=" + id +
                ", step=" + step +
                ", description='" + description + '\'' +
                ", durationInSeconds=" + durationInSeconds +
                ", recipe=" + (recipe != null ? recipe.getId() : "?") +
                '}';
    }

}
