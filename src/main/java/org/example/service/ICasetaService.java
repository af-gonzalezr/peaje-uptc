package org.example.service;

import org.example.Modelo.Caseta;
import org.example.Modelo.tipoPago;
import org.example.exception.CasetaNoEncontradaException;
import org.example.exception.PeajeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CasetaService implements ICasetaService {

    private final Map<String, Caseta> casetas = new HashMap<>();
    private final Map<String, Set<tipoPago>> mediosPorCaseta = new HashMap<>();

    @Override
    public Caseta registrarCaseta(String idCaseta, String nombre) {
        if (idCaseta == null || idCaseta.isBlank()) {
            throw new PeajeException("El ID de la caseta no puede estar vacío.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new PeajeException("El nombre de la caseta no puede estar vacío.");
        }
        if (casetas.containsKey(idCaseta)) {
            throw new PeajeException("Ya existe una caseta con ID '" + idCaseta + "'.");
        }
        Caseta caseta = new Caseta(idCaseta, nombre);
        casetas.put(idCaseta, caseta);
        mediosPorCaseta.put(idCaseta, EnumSet.noneOf(tipoPago.class));
        return caseta;
    }

    @Override
    public Caseta buscarPorId(String idCaseta) {
        Caseta caseta = casetas.get(idCaseta);
        if (caseta == null) {
            throw new CasetaNoEncontradaException(idCaseta);
        }
        return caseta;
    }

    @Override
    public List<Caseta> listarTodas() {
        return new ArrayList<>(casetas.values());
    }

    @Override
    public void asociarMedioPago(String idCaseta, tipoPago medioPago) {
        buscarPorId(idCaseta);
        if (medioPago == null) {
            throw new PeajeException("El medio de pago no puede ser nulo.");
        }
        mediosPorCaseta.get(idCaseta).add(medioPago);
    }

    @Override
    public Set<tipoPago> obtenerMediosPagoSoportados(String idCaseta) {
        buscarPorId(idCaseta);
        return Collections.unmodifiableSet(mediosPorCaseta.get(idCaseta));
    }
}