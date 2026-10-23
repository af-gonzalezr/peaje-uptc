package org.example.Modelo;

import java.time.LocalDateTime;

public class Orden {
    private final String idOrden;
    private final double monto;
    private final TipoPago tipoPago;
    private final TipoVehiculo tipoVehiculo;
    private final String idCaseta;
    private final LocalDateTime fechaHora;

    public Orden(String idOrden, double monto, TipoPago tipoPago, TipoVehiculo tipoVehiculo,
                 String idCaseta, LocalDateTime fechaHora) {
        this.idOrden = idOrden;
        this.monto = monto;
        this.tipoPago = tipoPago;
        this.tipoVehiculo = tipoVehiculo;
        this.idCaseta = idCaseta;
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "Orden [idOrden=" + idOrden + ", monto=" + monto + ", tipoPago=" + tipoPago + ", tipoVehiculo="
                + tipoVehiculo + ", idCaseta=" + idCaseta + ", fechaHora=" + fechaHora + "]";
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getIdOrden() {
        return idOrden;
    }

    public double getMonto() {
        return monto;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public String getIdCaseta() {
        return idCaseta;
    }
    
    
    
}
