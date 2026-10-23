package org.example.service;

import org.example.Modelo.Caseta;
import org.example.Modelo.TipoPago;
import org.example.exception.CasetaNoEncontradaException;
import org.example.exception.PeajeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CasetaService implements ICasetaService {

    // idCaseta -> Caseta
    private final Map<String, Caseta> casetas = new LinkedHashMap<>();

    // idCaseta -> medios de pago que acepta esa caseta
    private final Map<String, Set<TipoPago>> mediosPagoPorCaseta = new HashMap<>();

    @Override
    public void registrarCaseta(Caseta caseta) {
        if (caseta == null) {
            throw new PeajeException("La caseta no puede ser nula.");
        }
        validarTexto(caseta.getIdCaseta(), "El ID de la caseta no puede estar vacío.");
        validarTexto(caseta.getNombre(), "El nombre de la caseta no puede estar vacío.");

        String idCaseta = caseta.getIdCaseta();
        if (casetas.containsKey(idCaseta)) {
            throw new PeajeException("Ya existe una caseta con ID '" + idCaseta + "'.");
        }

        casetas.put(idCaseta, caseta);
        mediosPagoPorCaseta.put(idCaseta, EnumSet.noneOf(TipoPago.class));
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
    public void asociarMedioPago(String idCaseta, TipoPago medioPago) {
        if (medioPago == null) {
            throw new PeajeException("El medio de pago no puede ser nulo.");
        }
        buscarPorId(idCaseta); // lanza CasetaNoEncontradaException si no existe
        mediosPagoPorCaseta.get(idCaseta).add(medioPago);
    }

    @Override
    public Set<TipoPago> obtenerMediosPagoSoportados(String idCaseta) {
        buscarPorId(idCaseta); // lanza CasetaNoEncontradaException si no existe
        return Collections.unmodifiableSet(mediosPagoPorCaseta.get(idCaseta));
    }

    private void validarTexto(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new PeajeException(mensaje);
        }
    }
}