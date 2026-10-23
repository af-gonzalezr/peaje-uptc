package org.example.service;


import org.example.Modelo.Caseta;
import org.example.Modelo.TipoPago;

import java.util.List;
import java.util.Set;

public interface ICasetaService {
    Caseta registrarCaseta(String idCaseta, String nombre);
    Caseta buscarPorId(String idCaseta);
    List<Caseta> listarTodas();
    void asociarMedioPago(String idCaseta, TipoPago medioPago);
    Set<TipoPago> obtenerMediosPagoSoportados(String idCaseta);
}