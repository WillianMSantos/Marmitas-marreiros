package com.delivery.marmitamarreiros.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("Usuario não encontrado");
    }

}
