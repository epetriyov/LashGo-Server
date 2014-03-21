package main.java.com.check.utils;

import main.java.com.check.rest.error.ValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * Created by Eugene on 14.02.14.
 */
public final class CheckUtils {

    private CheckUtils() {

    }

    public static void handleBindingResult(BindingResult result) throws ValidationException {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            ObjectError error = errors.get(0);
            String[] codes = error.getCodes();
            throw new ValidationException(codes[0].toString());
        }
    }

    public static String generateSession(String uuid, int userId) {
        return md5(uuid + String.valueOf(userId) + new Date().getTime());
    }

    public static String md5(final String s) {
        if (s != null) {
            try {
                // Create MD5 Hash
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.update(s.getBytes());
                byte messageDigest[] = digest.digest();

                // Create Hex String
                StringBuilder hexString = new StringBuilder();
                for (int i = 0; i < messageDigest.length; i++) {
                    String h = Integer.toHexString(0xFF & messageDigest[i]);
                    while (h.length() < 2)
                        h = "0" + h;
                    hexString.append(h);
                }
                return hexString.toString();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    public static String convertStreamToString(InputStream is) {
        ByteArrayOutputStream oas = new ByteArrayOutputStream();
        copyStream(is, oas);
        String t = oas.toString();
        try {
            oas.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    private static void copyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }
}
