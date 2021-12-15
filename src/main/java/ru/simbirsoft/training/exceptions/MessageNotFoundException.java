package ru.simbirsoft.training.exceptions;

public class MessageNotFoundException extends RuntimeException {

    public MessageNotFoundException(Long id) {
        super("No message found with id = " + id);
    }

}
