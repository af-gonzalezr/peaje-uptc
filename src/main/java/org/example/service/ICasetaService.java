package org.example.service;

import org.example.Modelo.Caseta;
import org.example.Modelo.TipoPago;
import org.example.exception.CasetaNoEncontradaException;

import java.util.List;
import java.util.Set;

public interface ICasetaService {
    void registrarCaseta(Caseta caseta);
    Caseta buscarPorId(String idCaseta);
    List<Caseta> listarTodas();
    void asociarMedioPago(String idCaseta, TipoPago medioPago);
    Set<TipoPago> obtenerMediosPagoSoportados(String idCaseta);
}