package org.intentor.samples.store.controllers.advices;

import org.intentor.samples.store.exceptions.DataNotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Advices for common exceptions that occur in controllers.
 */
@ControllerAdvice
@RequestMapping(produces = "application/vnd.error")
public class CommonControllerAdvice {
    @ResponseBody
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors DataNotFoundExceptionHandler(DataNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
