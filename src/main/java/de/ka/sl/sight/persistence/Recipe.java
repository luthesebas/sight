package de.ka.sl.sight.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public final class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private List<Instruction> instructions;

    //--------------------------------------
    // Constructors
    //--------------------------------------

    public Recipe() {
        this.instructions = new LinkedList<>();
    }

    public Recipe(String title, String description) {
        this();
        this.title = title;
        this.description = description;
    }

    //--------------------------------------
    // Methods
    //--------------------------------------

    public void updateFrom(Recipe recipe) {
        title = recipe.title;
        description = recipe.description;
    }

    public void add(Instruction instruction) {
        instructions.add(instruction);
    }

    public void remove(Instruction instruction) {
        instructions.remove(instruction);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
