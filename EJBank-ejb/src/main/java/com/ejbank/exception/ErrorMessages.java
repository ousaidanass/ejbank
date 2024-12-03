package com.ejbank.exception;

import java.util.HashMap;

public class ErrorMessages {

    private static final HashMap<Integer, String> messages = new HashMap<>();

    static {
        messages.put(1, "Utilisateur non trouvé");
    }

    public static String getErrorMessage(int errorCode) {
        return messages.get(errorCode);
    }
}
