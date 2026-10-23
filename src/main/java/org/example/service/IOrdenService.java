package org.example.service;

import org.example.Modelo.Orden;
import org.example.Modelo.TipoPago;
import org.example.Modelo.TipoVehiculo;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IOrdenService {
    Orden registrarOrden(String idOrden,
                         double monto,
                         TipoPago pago,
                         TipoVehiculo vehiculo,
                         String idCaseta,
                         LocalDateTime fechaHora);
    void registrarOrden(Orden orden);
    Orden buscarPorId(String idOrden);
    List<Orden> consultarPorFechaYCaseta(String idCaseta, LocalDate fecha);
    List<Orden> consultarPorFechaGeneral(LocalDate fecha);
    List<Orden> listarTodas();
}