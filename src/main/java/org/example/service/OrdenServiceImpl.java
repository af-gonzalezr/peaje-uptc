package org.example.service;

import org.example.Modelo.Caseta;
import org.example.Modelo.Orden;
import org.example.Modelo.TipoPago;
import org.example.Modelo.TipoVehiculo;
import org.example.exception.MedioPagoNoSoportadoException;
import org.example.exception.OrdenNoEncontradaException;
import org.example.exception.PeajeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdenServiceImpl implements IOrdenService {


    private final ArrayList<Orden> ordenes = new ArrayList<>();
    private final ICasetaService casetaService;

    public OrdenServiceImpl (ICasetaService casetaService) {
        this.casetaService = casetaService;
    }

    @Override
    public Orden registrarOrden(String idOrden, double monto, TipoPago pago,
                                TipoVehiculo vehiculo, String idCaseta, LocalDateTime fechaHora) {


        if (idOrden == null || idOrden.isBlank()) {
            throw new PeajeException("El ID de la orden no puede estar vacío.");
        }
        if (monto <= 0) {
            throw new PeajeException("El monto debe ser mayor a 0.");
        }
        if (pago == null || vehiculo == null || fechaHora == null) {
            throw new PeajeException("El tipo de pago, el tipo de vehículo y la fecha son obligatorios.");
        }
        if (ordenes.stream().anyMatch(o -> o.getIdOrden().equals(idOrden))) {
            throw new PeajeException("Ya existe una orden con el ID '" + idOrden + "'.");
        }

        Caseta caseta = casetaService.buscarPorId(idCaseta);


        if (!casetaService.obtenerMediosPagoSoportados(idCaseta).contains(pago)) {
            throw new MedioPagoNoSoportadoException(idCaseta, pago);
        }

        Orden orden = new Orden(idOrden, monto, pago, vehiculo, idCaseta, fechaHora);
        ordenes.add(orden);
        caseta.agregarOrden(orden);

        return orden;
    }

    @Override
    public Orden buscarPorId(String idOrden) {
        return ordenes.stream()
                .filter(o -> o.getIdOrden().equals(idOrden))
                .findFirst()
                .orElseThrow(() -> new OrdenNoEncontradaException(idOrden));
    }

    @Override
    public List<Orden> consultarPorFechaYCaseta(String idCaseta, LocalDate fecha) {
        Caseta caseta = casetaService.buscarPorId(idCaseta); // valida que exista

        return caseta.getOrdenes().stream()
                .filter(o -> o.getFechaHora().toLocalDate().equals(fecha))
                .toList();
    }

    @Override
    public List<Orden> consultarPorFechaGeneral(LocalDate fecha) {
        return ordenes.stream()
                .filter(o -> o.getFechaHora().toLocalDate().equals(fecha))
                .toList();
    }

    @Override
    public List<Orden> listarTodas() {
        return new ArrayList<>(ordenes);
    }
}