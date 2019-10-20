package de.sight.persistence.instruction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.sight.persistence.IEntity;
import de.sight.persistence.recipe.RecipeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Data
@ToString(callSuper = true, exclude = {"recipe"})
@EqualsAndHashCode(callSuper = true, exclude = {"recipe"})
@Entity
@Table(
    name = "instruction",
    uniqueConstraints = { @UniqueConstraint(columnNames = {"recipe_id", "step"}) }
)
public final class InstructionEntity extends IEntity {

    private int step;
    private String description;
    private int durationInSeconds;

    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;

    public InstructionEntity () {}

}
