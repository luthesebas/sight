package de.sight.persistence.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.sight.persistence.IEntity;
import de.sight.persistence.instruction.InstructionEntity;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Setter
@Getter
@Entity
@Table(name = "recipe")
public final class RecipeEntity extends IEntity {

    private String title;
    private String description;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private List<InstructionEntity> instructions;

    public RecipeEntity () {
        this.instructions = new LinkedList<>();
    }

}
