package com.ejbank.exception;

import java.util.HashMap;

public class ErrorMessages {

    private static final HashMap<ErrorIdentifier, String> messages = new HashMap<>();

    static {
        messages.put(ErrorIdentifier.USER_NOT_FOUND, "Utilisateur non trouvé");
        messages.put(ErrorIdentifier.ACCOUNT_NOT_FOUND, "Compte non trouvé");
        messages.put(ErrorIdentifier.CUSTOMER_NOT_FOUND, "Client non trouvé");
        messages.put(ErrorIdentifier.TRANSACTION_NOT_FOUND, "Transaction non trouvé");
        messages.put(ErrorIdentifier.TRANSACTION_REFUSED, "Transaction refuser");
        messages.put(ErrorIdentifier.AUTHOR_IS_NOT_CORRECT, "L'auteur n'est pas correct");
        messages.put(ErrorIdentifier.TECHNICAL_ERROR, "Erreur technique");
        messages.put(ErrorIdentifier.SOURCE_ACCOUNT_NOT_FOUND, "Le compte source non trouvé");
        messages.put(ErrorIdentifier.DESTINATION_ACCOUNT_NOT_FOUND, "Le compte destinataire non trouvé");
        messages.put(ErrorIdentifier.USER_IS_NOT_A_CUSTOMER, "Utilisateur n'est pas un client");
        messages.put(ErrorIdentifier.ACCOUNT_NOT_ASSIGNED_TO_ADVISOR, "Ce compte n'est pas assigné à ce conseillers");
        messages.put(ErrorIdentifier.ACCOUNT_NOT_ASSIGNED_TO_CUSTOMER, "Ce compte n'est pas assigné à ce client");
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
