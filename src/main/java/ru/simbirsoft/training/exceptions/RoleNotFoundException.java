package ru.simbirsoft.training.exceptions;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(Long id) {
        super("No role found with id = " + id);
    }

}
