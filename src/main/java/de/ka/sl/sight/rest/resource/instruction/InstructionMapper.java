package de.ka.sl.sight.rest.resource.instruction;

import de.ka.sl.sight.persistence.instruction.InstructionEntity;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
@Component
public final class InstructionMapper implements ResourceAssembler<InstructionEntity, Resource<InstructionEntity>> {

    @Override
    public Resource<InstructionEntity> toResource(InstructionEntity instructionEntity) {
        Resource<InstructionEntity> resource = new Resource<>(instructionEntity);
        resource.add(linkTo(InstructionController.class, instructionEntity.getRecipe().getId())
                .slash(instructionEntity.getId()).withSelfRel());
        resource.add(linkTo(InstructionController.class, instructionEntity.getRecipe().getId())
                .withRel("instructions"));
        return resource;
    }

}
