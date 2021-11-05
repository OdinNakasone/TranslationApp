package com.example.translationapp.encoders;

import java.util.Base64;
import java.util.Base64.*;

public class PasswordEncoder {
    public static String encodePassword(String password) {
        Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(password.getBytes());
    }


    public static String decodePassword(String password) {
        Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(password);
        return new String(bytes);
    }
}
