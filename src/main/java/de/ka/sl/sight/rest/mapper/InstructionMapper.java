package de.ka.sl.sight.rest.mapper;

import de.ka.sl.sight.persistence.Instruction;
import de.ka.sl.sight.rest.InstructionController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
@Component
public final class InstructionMapper implements ResourceAssembler<Instruction, Resource<Instruction>> {

    @Override
    public Resource<Instruction> toResource(Instruction instruction) {
        Resource<Instruction> resource = new Resource<>(instruction);
        resource.add(linkTo(InstructionController.class, instruction.getRecipe().getId())
                .slash(instruction.getId()).withSelfRel());
        resource.add(linkTo(InstructionController.class, instruction.getRecipe().getId())
                .withRel("instructions"));
        return resource;
    }

}
