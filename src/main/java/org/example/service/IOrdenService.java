package org.example.service;

import org.example.Modelo.Orden;
import org.example.exception.CasetaNoEncontradaException;
import org.example.exception.MedioPagoNoSoportadoException;
import org.example.exception.OrdenNoEncontradaException;

import java.time.LocalDate;
import java.util.List;

public interface IOrdenService {
    void registrarOrden(Orden orden);
    Orden buscarPorId(String idOrden);
    List<Orden> consultarPorFechaYCaseta(String idCaseta, LocalDate fecha);
    List<Orden> consultarPorFechaGeneral(LocalDate fecha);
    List<Orden> listarTodas();
}