package com.bookwise.sembackend.service;

import java.util.HashMap;
import java.util.Random;

public class VerificationUtils {
    private static final HashMap<String, String> verificationPool;

    static {
        verificationPool = new HashMap<>();
    }

    private static String generateRandomCode() {
        // Define the range of possible digits
        int min = 100000;
        int max = 999999;

        // Generate a random number within the specified range
        int randomCode = new Random().nextInt((max - min) + 1) + min;

        // Convert the random number to a 6-digit string
        return String.format("%06d", randomCode);
    }

    public static String populate(String email) {
        String code = generateRandomCode();
        verificationPool.put(email, code);
        return code;
    }

    public static boolean verify(String email, String code) {
        String _code = verificationPool.get(email);
        if (_code == null) return false;
        boolean ret = _code.equals(code);
        if (ret) verificationPool.remove(email);
        return ret;
    }
}
