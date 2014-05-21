package main.java.com.lashgo.error;

import java.io.IOException;

/**
 * Created by Eugene on 28.04.2014.
 */
public class PhotoReadException extends RuntimeException {

    public PhotoReadException(IOException e) {
        super(ErrorCodes.PHOTO_READ_ERROR);
    }
}
