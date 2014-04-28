package main.java.com.check.rest.error;

import java.io.IOException;

/**
 * Created by Eugene on 28.04.2014.
 */
public class PhotoWriteException extends RuntimeException {
    public PhotoWriteException(IOException e) {
        super(ErrorCodes.PHOTO_WRITE_ERROR);
    }
}
