package de.ka.sl.sight.persistence.instruction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.ka.sl.sight.persistence.recipe.Recipe;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
@Data
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "instruction")
public final class Instruction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int step;
    private String description;
    private int durationInSeconds;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Recipe recipe;

    //--------------------------------------
    // Constructors
    //--------------------------------------

    public Instruction() {
    }

    public Instruction(int step, String description, int durationInSeconds) {
        this.step = step;
        this.description = description;
        this.durationInSeconds = durationInSeconds;
    }

    //--------------------------------------
    // Methods
    //--------------------------------------

    public void updateFrom(Instruction newInstruction) {
        step = newInstruction.step;
        description = newInstruction.description;
        durationInSeconds = newInstruction.durationInSeconds;
    }

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
