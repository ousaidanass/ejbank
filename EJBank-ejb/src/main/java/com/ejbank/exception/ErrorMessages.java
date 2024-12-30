package com.ejbank.exception;

import java.util.HashMap;

public class ErrorMessages {

    private static final HashMap<ErrorIdentifier, String> messages = new HashMap<>();

    static {
        messages.put(ErrorIdentifier.USER_NOT_FOUND, "Utilisateur non trouvé");
        messages.put(ErrorIdentifier.USER_IS_NOT_A_CUSTOMER, "Utilisateur n'est pas un client");
    }

    /**
     * It will return the error object corresponding to the error code.
     * @param errorIdentifier the error identifier
     * @return Error the error corresponding to the identifier
     */
    public static String getErrorMessage(ErrorIdentifier errorIdentifier) {
        return messages.get(errorIdentifier);
    }
}
