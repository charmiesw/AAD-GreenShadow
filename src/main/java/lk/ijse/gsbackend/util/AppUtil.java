package lk.ijse.gsbackend.util;

import java.util.Base64;

public class AppUtil {
    public static String toBase64image(byte[] image){
        return Base64.getEncoder().encodeToString(image);
    }
}
