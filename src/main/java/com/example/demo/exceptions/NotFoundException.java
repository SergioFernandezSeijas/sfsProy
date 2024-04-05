package com.example.demo.exceptions;

public class NotFoundException extends Exception{
    public NotFoundException (String msg) {
        super(msg);
    }
    public NotFoundException () {
        super("Elemento no encontrado");
    }
}
