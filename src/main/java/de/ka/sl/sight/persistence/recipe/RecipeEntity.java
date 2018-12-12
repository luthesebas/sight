package de.ka.sl.sight.persistence.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.ka.sl.sight.persistence.instruction.InstructionEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
@Data
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "recipe")
public final class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private List<InstructionEntity> instructions;

    //--------------------------------------
    // Constructors
    //--------------------------------------

    public RecipeEntity() {
        this.instructions = new LinkedList<>();
    }

    public RecipeEntity(String title, String description) {
        this();
        this.title = title;
        this.description = description;
    }

    //--------------------------------------
    // Methods
    //--------------------------------------

    public void updateFrom(RecipeEntity recipeEntity) {
        title = recipeEntity.title;
        description = recipeEntity.description;
    }

    public void add(InstructionEntity instructionEntity) {
        instructions.add(instructionEntity);
    }

    public void remove(InstructionEntity instructionEntity) {
        instructions.remove(instructionEntity);
    }

}
