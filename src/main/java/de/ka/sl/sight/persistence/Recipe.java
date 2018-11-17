package de.ka.sl.sight.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
@Data
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "recipe")
public final class Recipe {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Instruction> instructions;

    //--------------------------------------
    // Constructors
    //--------------------------------------

    public Recipe() {}

    public Recipe(String title, String description) {
        this.title = title;
        this.description = description;
        this.instructions = new LinkedList<>();
    }

    //--------------------------------------
    // Methods
    //--------------------------------------

    public void updateFrom(Recipe recipe) {
        this.title = recipe.title;
        this.description = recipe.description;
    }

    public void add(Instruction instruction) {
        instructions.add(instruction);
    }

    public void remove(Instruction instruction) {
        instructions.remove(instruction);
    }

    //--------------------------------------
    // General
    //--------------------------------------

}
