package de.ka.sl.sight.rest.general;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import de.ka.sl.sight.rest.general.exception.AppException;
import de.ka.sl.sight.rest.general.exception.ExceptionMessage;
import de.ka.sl.sight.rest.general.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@ControllerAdvice
public final class AppExceptionAdvice {

    private ExceptionMessage mapToMessage (Exception ex) {
        // ex.printStackTrace();
        return new ExceptionMessage(ex.getMessage());
    }

    private ExceptionMessage mapToMessage (String message) {
        return new ExceptionMessage(message);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessage handel (NotFoundException ex) {
        return mapToMessage(ex);
    }

    @ExceptionHandler(JsonMappingException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handel (JsonMappingException ex) {
        return mapToMessage(ex);
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handel (JsonProcessingException ex) {
        return mapToMessage(ex);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handel (HttpMessageNotReadableException ex) {
        return mapToMessage(ex);
    }

    @ExceptionHandler(AppException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage handel (AppException ex) {
        return mapToMessage(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage handel (Exception ex, WebRequest request) {
        ex.printStackTrace();
        return mapToMessage("Oops... something went wrong on our end. Sorry!");
    }

}
