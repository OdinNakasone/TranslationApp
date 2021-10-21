package com.example.translationapp.encoders;

import java.util.Base64;
import java.util.Base64.*;

public class PasswordEncoder {
    public static void encodePassword(String password) {
        Encoder encoder = Base64.getEncoder();
        encoder.encodeToString(password.getBytes());
    }


    public static String decodePassword(String password) {
        Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(password);
        return new String(bytes);
    }
}
