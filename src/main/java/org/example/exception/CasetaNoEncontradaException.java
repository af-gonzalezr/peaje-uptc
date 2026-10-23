package org.example.exception;

public class CasetaNoEncontradaException extends PeajeException {
    public CasetaNoEncontradaException(String idCaseta) {
        super("La caseta con ID '" + idCaseta + "' no fue encontrada.");
    }
}