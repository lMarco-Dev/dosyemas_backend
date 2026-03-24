package com.dosyemas.almacen.exception;

// Cuando algo no existe en la base de datos
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
