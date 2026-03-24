package com.dosyemas.almacen.exception;

public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(String mensaje){
        super(mensaje);
    }
}
