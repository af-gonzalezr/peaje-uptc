package org.example.controller;

import org.example.Modelo.Caseta;
import org.example.Modelo.TipoPago;
import org.example.service.Result;

import java.util.List;
import java.util.Set;

public interface ICasetaController {
    Result<Caseta> registrarCaseta(String idCaseta, String nombre);
    Result<Caseta> buscarPorId(String idCaseta);
    Result<List<Caseta>> listarTodas();
    Result<Boolean> asociarMedioPago(String idCaseta, String tipoPagoStr);
    Result<Set<TipoPago>> obtenerMediosPagoSoportados(String idCaseta);
}