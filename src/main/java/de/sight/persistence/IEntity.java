package de.sight.persistence;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/** @author Sebastian Luther (@url(https://github.com/luthesebas)) */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, of = "id")
@MappedSuperclass
public abstract class IEntity extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

}
