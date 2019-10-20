package de.sight.rest.resource.instruction.service;

import de.sight.persistence.instruction.InstructionEntity;
import de.sight.rest.resource.IModelMapper;
import de.sight.rest.resource.instruction.model.Instruction;
import org.springframework.stereotype.Component;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Component
public final class InstructionMapper implements IModelMapper<InstructionEntity, Instruction> {

    @Override
    public Instruction mapToModel (final InstructionEntity entity) {
        Instruction instruction = new Instruction();
        instruction.setId(entity.getId());

        instruction.setStep(entity.getStep());
        instruction.setDescription(entity.getDescription());
        instruction.setDurationInSeconds(entity.getDurationInSeconds());

        instruction.setModifiedDate(entity.getModifiedDate());
        instruction.setCreatedBy(entity.getCreatedBy());
        instruction.setCreatedDate(entity.getCreatedDate());
        return instruction;
    }

}
