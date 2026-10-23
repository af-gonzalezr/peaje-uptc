package org.example.service;

import org.example.Modelo.Caseta;
import org.example.Modelo.TipoPago;
import org.example.exception.CasetaNoEncontradaException;
import org.example.exception.PeajeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CasetaService implements ICasetaService {

    // Lista de casetas registradas
    private final List<Caseta> casetas = new ArrayList<>();

    // Lista con los medios de pago de cada caseta (se relaciona por idCaseta)
    private final List<MediosPagoCaseta> mediosPago = new ArrayList<>();

    @Override
    public Caseta registrarCaseta(String idCaseta, String nombre) {
        validarTexto(idCaseta, "El ID de la caseta no puede estar vacío.");
        validarTexto(nombre, "El nombre de la caseta no puede estar vacío.");

        String idLimpio = idCaseta.trim();
        String nombreLimpio = nombre.trim();

        for (Caseta c : casetas) {
            if (c.getIdCaseta().equalsIgnoreCase(idLimpio)) {
                throw new PeajeException("Ya existe una caseta con ID '" + idLimpio + "'.");
            }
        }

        Caseta nuevaCaseta = new Caseta(idLimpio, nombreLimpio);
        casetas.add(nuevaCaseta);
        mediosPago.add(new MediosPagoCaseta(idLimpio));

        return nuevaCaseta;
    }

    @Override
    public Caseta buscarPorId(String idCaseta) {
        if (idCaseta == null || idCaseta.isBlank()) {
            throw new PeajeException("El ID de la caseta no puede estar vacío.");
        }

        String idLimpio = idCaseta.trim();
        for (Caseta c : casetas) {
            if (c.getIdCaseta().equalsIgnoreCase(idLimpio)) {
                return c;
            }
        }
        throw new CasetaNoEncontradaException(idLimpio);
    }

    @Override
    public List<Caseta> listarTodas() {
        return new ArrayList<>(casetas);
    }

    @Override
    public void asociarMedioPago(String idCaseta, TipoPago medioPago) {
        if (medioPago == null) {
            throw new PeajeException("El medio de pago no puede ser nulo.");
        }

        Caseta caseta = buscarPorId(idCaseta); // Lanza CasetaNoEncontradaException si no existe

        List<TipoPago> medios = buscarMedios(caseta.getIdCaseta()).medios;
        if (!medios.contains(medioPago)) {
            medios.add(medioPago);
        }
    }

    @Override
    public Set<TipoPago> obtenerMediosPagoSoportados(String idCaseta) {
        Caseta caseta = buscarPorId(idCaseta); // Lanza CasetaNoEncontradaException si no existe

        List<TipoPago> medios = buscarMedios(caseta.getIdCaseta()).medios;
        return Collections.unmodifiableSet(new LinkedHashSet<>(medios));
    }

    private MediosPagoCaseta buscarMedios(String idCaseta) {
        for (MediosPagoCaseta m : mediosPago) {
            if (m.idCaseta.equalsIgnoreCase(idCaseta)) {
                return m;
            }
        }
        throw new CasetaNoEncontradaException(idCaseta);
    }

    private void validarTexto(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new PeajeException(mensaje);
        }
    }

    // Guarda los medios de pago de una caseta usando una lista
    private static class MediosPagoCaseta {
        private final String idCaseta;
        private final List<TipoPago> medios = new ArrayList<>();

        private MediosPagoCaseta(String idCaseta) {
            this.idCaseta = idCaseta;
        }
    }
}