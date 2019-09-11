package de.ka.sl.sight.rest.resource.instruction.service;

import de.ka.sl.sight.persistence.instruction.InstructionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InstructionValidator {

    public boolean isValid (InstructionEntity entity) {
        //TODO Implement isValid
        return true;
    }

}
