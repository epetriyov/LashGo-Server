package main.java.com.check.rest.error;

import com.check.model.dto.ErrorDto;
import com.check.model.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Eugene on 13.02.14.
 */
@ControllerAdvice
public class RestErrorHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoginException.class)
    @ResponseBody
    public Response processLoginError(LoginException exception) {
        exception.printStackTrace();
        return new Response(new ErrorDto(exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RegisterException.class)
    @ResponseBody
    public Response processRegisterError(RegisterException exception) {
        exception.printStackTrace();
        return new Response(new ErrorDto(exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public Response processValidationError(ValidationException exception) {
        exception.printStackTrace();
        return new Response(new ErrorDto(exception.getMessage()));
    }
}
