package org.example.controller;

import org.example.Modelo.Orden;
import org.example.service.Result;

import java.util.List;

public interface IOrdenController {
    Result<Orden> crearYRegistrarOrden(
            String idOrden,
            String tipoPagoStr,
            String tipoVehiculoStr,
            String idCaseta,
            String fechaHoraStr
    );
    Result<Orden> buscarPorId(String idOrden);
    Result<List<Orden>> consultarPorFechaYCaseta(String idCaseta, String fechaStr);
    Result<List<Orden>> consultarPorFechaGeneral(String fechaStr);
    Result<List<Orden>> listarTodas();
}