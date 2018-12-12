package de.ka.sl.sight.rest.general;

import de.ka.sl.sight.rest.resource.instruction.InstructionNotFoundException;
import de.ka.sl.sight.rest.resource.recipe.RecipeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
@ControllerAdvice
public final class AppExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handel(RecipeNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InstructionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handel(InstructionNotFoundException ex) {
        return ex.getMessage();
    }

}
