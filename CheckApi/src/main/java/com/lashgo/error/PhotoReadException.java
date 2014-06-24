package com.lashgo.error;

import com.lashgo.model.ErrorCodes;

import java.io.IOException;

/**
 * Created by Eugene on 28.04.2014.
 */
public class PhotoReadException extends LashgoRuntimeError {

    public PhotoReadException() {
        super(ErrorCodes.PHOTO_READ_ERROR);
    }
}
