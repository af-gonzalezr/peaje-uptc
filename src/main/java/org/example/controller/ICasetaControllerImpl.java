package org.example.controller;

import org.example.Modelo.Caseta;
import org.example.Modelo.TipoPago;
import org.example.service.ICasetaService;
import org.example.service.Result;

import java.util.List;
import java.util.Set;

public class ICasetaControllerImpl implements ICasetaController {

    private final ICasetaService casetaService;

    public ICasetaControllerImpl(ICasetaService casetaService) {
        this.casetaService = casetaService;
    }

    @Override
    public Result<Caseta> registrarCaseta(String idCaseta, String nombre) {
        if (idCaseta == null || idCaseta.isBlank()) {
            return Result.failed("El identificador de la caseta no puede estar vacío.");
        }
        if (nombre == null || nombre.isBlank()) {
            return Result.failed("El nombre de la caseta no puede estar vacío.");
        }

        try {
            Caseta caseta = casetaService.registrarCaseta(idCaseta.trim(), nombre.trim());
            return Result.success("Caseta registrada exitosamente", caseta);
        } catch (RuntimeException e) {
            return Result.failed("Error al registrar la caseta: " + e.getMessage());
        }
    }

    @Override
    public Result<Caseta> buscarPorId(String idCaseta) {
        if (idCaseta == null || idCaseta.isBlank()) {
            return Result.failed("El identificador de la caseta no puede estar vacío.");
        }

        try {
            Caseta caseta = casetaService.buscarPorId(idCaseta.trim());
            return Result.success("Caseta encontrada exitosamente", caseta);
        } catch (RuntimeException e) {
            return Result.failed("Error al buscar la caseta: " + e.getMessage());
        }
    }

    @Override
    public Result<List<Caseta>> listarTodas() {
        try {
            List<Caseta> casetas = casetaService.listarTodas();
            if (casetas.isEmpty()) {
                return Result.success("No hay casetas registradas en el sistema", casetas);
            }
            return Result.success("Se encontraron " + casetas.size() + " casetas", casetas);
        } catch (RuntimeException e) {
            return Result.failed("Error al listar las casetas: " + e.getMessage());
        }
    }

    @Override
    public Result<Boolean> asociarMedioPago(String idCaseta, String tipoPagoStr) {
        if (idCaseta == null || idCaseta.isBlank()) {
            return Result.failed("El identificador de la caseta no puede estar vacío.");
        }
        if (tipoPagoStr == null || tipoPagoStr.isBlank()) {
            return Result.failed("El tipo de pago no puede estar vacío.");
        }

        TipoPago tipoPago;
        try {
            tipoPago = TipoPago.valueOf(tipoPagoStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return Result.failed("El tipo de pago no es valido.");
        }

        try {
            casetaService.asociarMedioPago(idCaseta.trim(), tipoPago);
            return Result.success("Medio de pago asociado exitosamente", true);
        } catch (RuntimeException e) {
            return Result.failed("Error al asociar el medio de pago: " + e.getMessage());
        }
    }

    @Override
    public Result<Set<TipoPago>> obtenerMediosPagoSoportados(String idCaseta) {
        if (idCaseta == null || idCaseta.isBlank()) {
            return Result.failed("El identificador de la caseta no puede estar vacío.");
        }

        try {
            Set<TipoPago> medios = casetaService.obtenerMediosPagoSoportados(idCaseta.trim());
            if (medios.isEmpty()) {
                return Result.success("La caseta no tiene medios de pago asociados", medios);
            }
            return Result.success("Medios de pago soportados por la caseta", medios);
        } catch (RuntimeException e) {
            return Result.failed("Error al obtener los medios de pago: " + e.getMessage());
        }
    }
}