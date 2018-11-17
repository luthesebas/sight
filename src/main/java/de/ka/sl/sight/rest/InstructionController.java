package de.ka.sl.sight.rest;

import de.ka.sl.sight.persistence.Instruction;
import de.ka.sl.sight.persistence.InstructionDAO;
import de.ka.sl.sight.rest.mapper.InstructionMapper;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 *
 */
@RestController
@RequestMapping(value = "/recipes/{recipeId:[0-9]+}/instructions")
public class InstructionController {

    private InstructionDAO instructionDAO;
    private InstructionMapper instructionMapper;

    //--------------------------------------
    // Constructors
    //--------------------------------------

    public InstructionController(InstructionDAO instructionDAO, InstructionMapper instructionMapper) {
        this.instructionDAO = instructionDAO;
        this.instructionMapper = instructionMapper;
    }

    //--------------------------------------
    // Methods
    //--------------------------------------

    @GetMapping()
    public Resources<Resource<Instruction>> all(@PathVariable(name = "recipeId") long recipeId) {
        List<Resource<Instruction>> instructions = instructionDAO.findAllByRecipeId(recipeId).stream()
                .map(instructionMapper::toResource)
                .collect(Collectors.toList());
        return new Resources<>(instructions, linkTo(InstructionController.class, recipeId).withSelfRel());
    }

}
