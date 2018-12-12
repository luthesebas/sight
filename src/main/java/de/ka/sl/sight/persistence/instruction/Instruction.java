package de.ka.sl.sight.persistence.instruction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.ka.sl.sight.persistence.recipe.RecipeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RecipeEntity recipe;

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

}
