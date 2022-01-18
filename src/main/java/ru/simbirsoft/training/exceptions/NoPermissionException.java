package ru.simbirsoft.training.exceptions;

public class NoPermissionException extends AbstractException {

    public NoPermissionException(String message, String techInfo) {
        super(message, techInfo);
    }
}
