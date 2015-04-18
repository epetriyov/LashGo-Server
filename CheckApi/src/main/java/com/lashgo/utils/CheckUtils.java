package com.lashgo.utils;

import com.lashgo.error.ValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Eugene on 14.02.14.
 */
public final class CheckUtils {

    private CheckUtils() {

    }

    public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(rsmd.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }

    public static void handleBindingResult(BindingResult result) throws ValidationException {
        if (result != null && result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            ObjectError error = errors.get(0);
            String[] codes = error.getCodes();
            throw new ValidationException(codes[0].toString());
        }
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

    public static String buildNewPhotoName(int checkId, int userId) {
        StringBuilder photoNameBuilder = new StringBuilder("check_");
        photoNameBuilder.append(checkId);
        photoNameBuilder.append("_user_");
        photoNameBuilder.append(userId);
        photoNameBuilder.append(".jpg");
        return photoNameBuilder.toString();
    }

}
