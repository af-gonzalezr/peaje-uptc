package org.example.exception;

public class OrdenNoEncontradaException extends PeajeException {
    public OrdenNoEncontradaException(String idOrden) {
        super("La orden con ID '" + idOrden + "' no fue encontrada.");
    }
}