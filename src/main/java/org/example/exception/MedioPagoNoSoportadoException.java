package org.example.exception;

import org.example.Modelo.TipoPago;

public class MedioPagoNoSoportadoException extends PeajeException {
    public MedioPagoNoSoportadoException(String idCaseta, TipoPago pago) {
        super("El medio de pago " + pago + " no está soportado en la caseta " + idCaseta + ".");
    }
}