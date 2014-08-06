package com.lashgo.error;

import com.lashgo.model.dto.ErrorDto;
import com.lashgo.model.dto.ResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

    private static Logger logger = LoggerFactory.getLogger("FILE");

    @Autowired
    private MessageSource messageSource;

    private ResponseObject buildErrorResponse(LashgoRuntimeError exception) {
        logger.info(exception.getErrorCode());
//        logger.info(messageSource.getMessage(exception.getMessage(), new Object[]{}, Locale.ENGLISH));
        logger.info("SUCCESS!");
        ResponseObject responseObject = new ResponseObject();
        responseObject.setError(new ErrorDto(exception.getErrorCode(), null));
        return responseObject;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ResponseObject processValidationError(ValidationException exception) {
        return buildErrorResponse(exception);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnautharizedException.class)
    @ResponseBody
    public ResponseObject processUnautharizedError(UnautharizedException exception) {
        return buildErrorResponse(exception);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    public ResponseObject processDataAccessError() {
        return buildErrorResponse(new InternalServerError());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PhotoReadException.class)
    @ResponseBody
    public ResponseObject processPhotoReadError(PhotoReadException exception) {
        return buildErrorResponse(exception);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PhotoWriteException.class)
    @ResponseBody
    public ResponseObject processPhotoWriteError(PhotoWriteException exception) {
        return buildErrorResponse(exception);
    }
}
