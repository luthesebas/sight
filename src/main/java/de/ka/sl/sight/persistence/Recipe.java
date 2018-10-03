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
@Table(name = "recipe")
public class Recipe {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;

    //--------------------------------------
    // Constructors
    //--------------------------------------

    public Recipe() {}

    public Recipe(String title, String description) {
        this.title = title;
        this.description = description;
    }

    //--------------------------------------
    // Methods
    //--------------------------------------



    //--------------------------------------
    // General
    //--------------------------------------

}
