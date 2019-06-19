package de.ka.sl.sight.persistence;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Sebastian Luther (@url(https://github.com/luthesebas))
 */
@Data
@EqualsAndHashCode(of = "id")
@MappedSuperclass
public abstract class IEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

}
