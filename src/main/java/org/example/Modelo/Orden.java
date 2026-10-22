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
    
    
    
}
