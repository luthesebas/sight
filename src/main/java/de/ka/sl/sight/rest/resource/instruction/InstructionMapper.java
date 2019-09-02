package de.ka.sl.sight.rest.resource.instruction;

import de.ka.sl.sight.persistence.instruction.InstructionEntity;
import de.ka.sl.sight.rest.resource.IModelMapper;
import org.springframework.stereotype.Component;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Component
public final class InstructionMapper implements IModelMapper<InstructionEntity, InstructionEntity> {

    @Override
    public InstructionEntity mapToModel (InstructionEntity model) {
        return model;
    }

}
