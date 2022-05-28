package com.delivery.marmitamarreiros.exception;

public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException() {
        super("Senha incorreta");
    }
}
