package ru.simbirsoft.training.exceptions;

public class ConnectionNotFoundException extends RuntimeException {

    public ConnectionNotFoundException(Long id) {
        super("No connection found with id = " + id);
    }

}
