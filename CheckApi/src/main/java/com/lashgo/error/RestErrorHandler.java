package com.lashgo.error;

import com.lashgo.model.dto.ErrorDto;
import com.lashgo.model.dto.ResponseObject;
import org.springframework.dao.DataAccessException;
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
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ResponseObject processValidationError(ValidationException exception) {
        return new ResponseObject(new ErrorDto(exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnautharizedException.class)
    @ResponseBody
    public ResponseObject processUnautharizedError(UnautharizedException exception) {
        return new ResponseObject(new ErrorDto(exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    public ResponseObject processDataAccessError(DataAccessException exception) {
        return new ResponseObject(new ErrorDto(exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PhotoReadException.class)
    @ResponseBody
    public ResponseObject processPhotoReadError(PhotoReadException exception) {
        return new ResponseObject(new ErrorDto(exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PhotoWriteException.class)
    @ResponseBody
    public ResponseObject processPhotoWriteError(PhotoWriteException exception) {
        return new ResponseObject(new ErrorDto(exception.getMessage()));
    }
}
