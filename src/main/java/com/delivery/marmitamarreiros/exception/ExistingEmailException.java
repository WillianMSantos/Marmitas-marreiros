package com.delivery.marmitamarreiros.exception;

public class ExistingEmailException extends RuntimeException{
    public ExistingEmailException(){ super("Email já cadastrado");}
}
