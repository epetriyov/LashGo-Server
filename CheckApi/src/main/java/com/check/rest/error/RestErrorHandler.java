package main.java.com.check.rest.error;

import com.check.model.dto.ErrorDto;
import com.check.model.dto.Response;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.xml.crypto.Data;

/**
 * Created by Eugene on 13.02.14.
 */
@ControllerAdvice
public class RestErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public Response processValidationError(ValidationException exception) {
        return new Response(new ErrorDto(exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnautharizedException.class)
    @ResponseBody
    public Response processUnautharizedError(UnautharizedException exception) {
        return new Response(new ErrorDto(exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GcmSendException.class)
    @ResponseBody
    public Response processGcmSendError(GcmSendException exception) {
        return new Response(new ErrorDto(exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    public Response processDataAccessError(DataAccessException exception) {
        return new Response(new ErrorDto(exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PhotoReadException.class)
    @ResponseBody
    public Response processPhotoReadError(PhotoReadException exception) {
        return new Response(new ErrorDto(exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PhotoWriteException.class)
    @ResponseBody
    public Response processPhotoWriteError(PhotoWriteException exception) {
        return new Response(new ErrorDto(exception.getMessage()));
    }
}
