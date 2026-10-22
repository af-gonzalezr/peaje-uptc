package org.example.Modelo;

import java.time.LocalDateTime;

public class Orden {
    private String idOrden;
    private double monto;
    private tipoPago tipoPago;
    private tipoVehiculo tipoVehiculo;
    private String idCaseta;
    private LocalDateTime fechaHora;

    public Orden(String idOrden, double monto, org.example.Modelo.tipoPago tipoPago, org.example.Modelo.tipoVehiculo tipoVehiculo,
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

    public tipoPago getTipoPago() {
        return tipoPago;
    }

    public tipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public String getIdCaseta() {
        return idCaseta;
    }
    
    
    
}
