package com.lashgo.error;

import com.lashgo.model.ErrorCodes;

import java.io.IOException;

/**
 * Created by Eugene on 28.04.2014.
 */
public class PhotoWriteException extends LashgoRuntimeError {
    public PhotoWriteException() {
        super(ErrorCodes.PHOTO_WRITE_ERROR);
    }
}
