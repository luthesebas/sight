package de.ka.sl.sight.rest.general;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

/**
 * @author Sebastian Luther (https://github.com/luthesebas)
 */
@ControllerAdvice
public final class AppExceptionAdvice {

    private ExceptionMessage mapToModel(Exception ex) {
        return new ExceptionMessage(ex.getMessage());
    }

    private ExceptionMessage mapToModel(String message) {
        return new ExceptionMessage(message);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessage handel(NotFoundException ex) {
        return mapToModel(ex);
    }

    @ExceptionHandler(JsonMappingException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handel(JsonMappingException ex) {
        return mapToModel(ex);
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handel(JsonProcessingException ex) {
        return mapToModel(ex);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handel(HttpMessageNotReadableException ex) {
        return mapToModel(ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage handel(Exception ex, WebRequest request) {
        return mapToModel("Oops... something went wrong on our end. Sorry!");
    }

}
