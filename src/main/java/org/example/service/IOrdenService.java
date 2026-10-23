package org.example.service;

import org.example.Modelo.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IOrdenService {
    Orden registrarOrden(
            String idOrden,
            double monto,
            TipoPago pago,
            TipoVehiculo vehiculo,
            String idCaseta,
            LocalDateTime fechaHora
    );

    Orden buscarPorId(String idOrden);
    List<Orden> consultarPorFechaYCaseta(String idCaseta, LocalDate fecha);
    List<Orden> consultarPorFechaGeneral(LocalDate fecha);
    List<Orden> listarTodas();
}