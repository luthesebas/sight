package de.ka.sl.sight.rest;

import de.ka.sl.sight.persistence.Instruction;
import de.ka.sl.sight.persistence.InstructionDAO;
import de.ka.sl.sight.persistence.Recipe;
import de.ka.sl.sight.persistence.RecipeDAO;
import de.ka.sl.sight.rest.mapper.InstructionMapper;
import de.ka.sl.sight.rest.misc.instruction.InstructionNotFoundException;
import de.ka.sl.sight.rest.misc.recipe.RecipeNotFoundException;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
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

    private RecipeDAO recipeDAO;

    //--------------------------------------
    // Constructors
    //--------------------------------------

    public InstructionController(InstructionDAO instructionDAO, InstructionMapper instructionMapper,
                                 RecipeDAO recipeDAO) {
        this.instructionDAO = instructionDAO;
        this.instructionMapper = instructionMapper;
        this.recipeDAO = recipeDAO;
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

    @GetMapping("/{id:[0-9]+}")
    public Resource<Instruction> read(@PathVariable long id) throws InstructionNotFoundException {
        return instructionMapper.toResource(
                instructionDAO.findById(id).orElseThrow(() -> new InstructionNotFoundException(id)));
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Instruction instruction,
                                    @PathVariable(name = "recipeId") long recipeId) throws Exception {
        Optional<Recipe> optional = recipeDAO.findById(recipeId);
        if (optional.isPresent()) {
            instruction = instructionDAO.save(instruction);
            instruction.setRecipe(optional.get());
            Resource<Instruction> resource = instructionMapper.toResource(instructionDAO.save(instruction));
            return ResponseEntity
                    .created(new URI(resource.getId().expand().getHref()))
                    .body(resource);
        } else {
            throw new RecipeNotFoundException(recipeId);
        }
    }

}
