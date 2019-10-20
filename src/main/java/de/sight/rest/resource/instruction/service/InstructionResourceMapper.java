package de.sight.rest.resource.instruction.service;

import de.sight.persistence.instruction.InstructionEntity;
import de.sight.rest.config.InstructionConfig;
import de.sight.rest.resource.IResourceMapper;
import de.sight.rest.resource.instruction.InstructionController;
import de.sight.rest.resource.instruction.model.Instruction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/** @author Sebastian Luther (@url(https://github.com/luthesebas)) */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InstructionResourceMapper implements IResourceMapper<InstructionEntity, Instruction> {

    private final InstructionMapper instructionMapper;

    @Override
    public Resource<Instruction> toResource (InstructionEntity instructionEntity) {
        Instruction model = instructionMapper.mapToModel(instructionEntity);
        Resource<Instruction> resource = new Resource<>(model);
        if (instructionEntity.getRecipe() != null) {
            resource.add(linkTo(InstructionController.class, instructionEntity.getRecipe().getId())
                .slash(instructionEntity.getId())
                .withSelfRel());
            resource.add(linkTo(InstructionController.class, instructionEntity.getRecipe().getId()).withRel(
                InstructionConfig.RESOURCE));
        }
        return resource;
    }

}
