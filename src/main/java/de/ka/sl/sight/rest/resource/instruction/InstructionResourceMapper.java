package de.ka.sl.sight.rest.resource.instruction;

import de.ka.sl.sight.persistence.instruction.InstructionEntity;
import de.ka.sl.sight.rest.resource.IResourceMapper;
import de.ka.sl.sight.rest.resource.config.InstructionConfig;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/** @author Sebastian Luther (@url(https://github.com/luthesebas)) */
@Component
public class InstructionResourceMapper implements IResourceMapper<InstructionEntity, InstructionEntity> {

    @Override
    public Resource<InstructionEntity> toResource (InstructionEntity instructionEntity) {
        Resource<InstructionEntity> resource = new Resource<>(instructionEntity);
        if (instructionEntity.getRecipe() != null) {
            resource.add(linkTo(InstructionController.class, instructionEntity.getRecipe().getId())
                .slash(instructionEntity.getId())
                .withSelfRel());
            resource.add(linkTo(InstructionController.class, instructionEntity.getRecipe().getId()).withRel(
                InstructionConfig.NAME));
        }
        return resource;
    }
}
