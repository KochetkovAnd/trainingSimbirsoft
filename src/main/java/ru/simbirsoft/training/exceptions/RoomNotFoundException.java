package ru.simbirsoft.training.exceptions;

public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException(Long id) {
        super("No room found with id = " + id);
    }
}
