package org.example.exception;

import org.example.Modelo.tipoPago;

public class MedioPagoNoSoportadoException extends PeajeException {
    public MedioPagoNoSoportadoException(String idCaseta, tipoPago pago) {
        super("El medio de pago " + pago + " no está soportado en la caseta " + idCaseta + ".");
    }
}