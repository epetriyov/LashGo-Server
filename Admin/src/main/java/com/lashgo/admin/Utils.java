package com.lashgo.admin;

import org.primefaces.model.UploadedFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Eugene on 21.06.2015.
 */
public class Utils {

    public static void savePhoto(UploadedFile file) {
        try {
            InputStream inputStream = file.getInputstream();
            if (inputStream != null) {
                BufferedImage src = null;
                try {
                    src = ImageIO.read(inputStream);
                } catch (IOException e) {
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                    }
                }
                StringBuilder photoDestinationBuilder = new StringBuilder(CheckConstants.PHOTOS_FOLDER);
                String photoPath = photoDestinationBuilder.append(file.getFileName()).toString();
                File destination = new File(photoPath);
                if (src != null) {
                    try {
                        ImageIO.write(src, "jpg", destination);
                    } catch (IOException e) {
                        //                    throw new PhotoWriteException();
                    }
                }
            } else {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
