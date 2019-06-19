package de.ka.sl.sight.persistence.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.ka.sl.sight.persistence.IEntity;
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
@ToString(exclude = {"instructions"})
@EqualsAndHashCode(callSuper = true, exclude = {"instructions"})
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

   public RecipeEntity (String title, String description) {
      this();
      this.title = title;
      this.description = description;
   }

   public void updateFrom (RecipeEntity recipeEntity) {
      title = recipeEntity.title;
      description = recipeEntity.description;
   }

   public void add (InstructionEntity instructionEntity) {
      instructions.add(instructionEntity);
   }

   public void remove (InstructionEntity instructionEntity) {
      instructions.remove(instructionEntity);
   }

}
