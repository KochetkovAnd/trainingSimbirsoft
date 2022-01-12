package ru.simbirsoft.training.exceptions;

public class ResourceNotFoundException extends AbstractException {

    public ResourceNotFoundException(String message, String techInfo) {
        super(message, techInfo);
    }
}
