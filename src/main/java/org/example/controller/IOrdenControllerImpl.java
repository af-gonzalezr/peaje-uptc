package org.example.controller;

import org.example.Modelo.Orden;
import org.example.Modelo.TipoPago;
import org.example.Modelo.TipoVehiculo;
import org.example.service.IOrdenService;
import org.example.service.Result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class IOrdenControllerImpl implements IOrdenController {

    private final IOrdenService ordenService;

    public IOrdenControllerImpl(IOrdenService ordenService) {
        this.ordenService = ordenService;
    }


    @Override
    public Result<Orden> crearYRegistrarOrden(String idOrden, String tipoPagoStr, String tipoVehiculoStr, String idCaseta, String fechaHoraStr) {
        if (idOrden == null || idOrden.isBlank()) {
            return Result.failed("El identificador no puede estar vacío.");
        }
        if (idCaseta == null || idCaseta.isBlank()) {
            return Result.failed("Se debe ingresar el ID de la caseta.");
        }

        TipoVehiculo tipoVehiculo;

        try {
            tipoVehiculo = TipoVehiculo.valueOf(tipoVehiculoStr);
        }catch (IllegalArgumentException e) {
            return Result.failed("El tipo de vehiculo no es valido.");
        }

        double monto = montoPorVehiculo(tipoVehiculo);

        TipoPago tipoPago;
        try {
            tipoPago = TipoPago.valueOf(tipoPagoStr);
        }catch (IllegalArgumentException e) {
            return Result.failed("El tipo de pago no es valido.");
        }

        LocalDateTime fechaHora;
        try {
            fechaHora = LocalDateTime.parse(fechaHoraStr);
        }catch (NullPointerException | DateTimeParseException e) {
            return Result.failed("La fecha y hora no es valido.");
        }

        try {
            Orden nuevaOrden = ordenService.registrarOrden(
                    idOrden.trim(),
                    monto,
                    tipoPago,
                    tipoVehiculo,
                    idCaseta.trim(),
                    fechaHora
            );
            return Result.success("Orden registrada exitosamente", nuevaOrden);
        }catch (RuntimeException e) {
            return Result.failed("Error al registrar el orden: "+e.getMessage());
        }
    }

    @Override
    public Result<Orden> buscarPorId(String idOrden) {
        if (idOrden == null || idOrden.isBlank()) {
            return Result.failed("El identificador no puede estar vacío.");
        }
        try {
            Orden orden = ordenService.buscarPorId(idOrden);
            return Result.success("Orden buscada exitosamente", orden);
        }catch (RuntimeException e) {
            return Result.failed("Error al buscar el orden: "+e.getMessage());
        }
    }

    @Override
    public Result<List<Orden>> consultarPorFechaYCaseta(String idCaseta, String fechaStr) {
        if  (idCaseta == null || idCaseta.isBlank()) {
            return Result.failed("Se debe ingresar el ID de la caseta.");
        }

        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaStr);
        }catch (NullPointerException | DateTimeParseException e) {
            return Result.failed("La fecha y hora no es valido.");
        }

        try {
            List<Orden> ordenes = ordenService.consultarPorFechaYCaseta(idCaseta.trim(), fecha);
            if (ordenes.isEmpty()) {
                return Result.success("No hay ordenes registradas para la caseta en esa fecha",ordenes);
            }
            return Result.success("Se encontraron "+ordenes.size()+" ordenes",ordenes);
        }catch (RuntimeException e) {
            return Result.failed("Error al consultar el orden: "+e.getMessage());
        }
    }

    @Override
    public Result<List<Orden>> consultarPorFechaGeneral(String fechaStr) {
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaStr);
        }catch (NullPointerException | DateTimeParseException e) {
            return Result.failed("La fecha no es valida.");
        }

        try {
            List<Orden> ordenes = ordenService.consultarPorFechaGeneral(fecha);
            if (ordenes.isEmpty()) {
                return Result.success("No se encontraron ordenes para la fecha",ordenes);
            }
            return Result.success("Se encontraron "+ordenes.size()+" ordenes",ordenes);
        }catch (RuntimeException e) {
            return Result.failed("Error al consultar el orden: "+e.getMessage());
        }

    }

    @Override
    public Result<List<Orden>> listarTodas() {
        try {
            List<Orden> ordenes = ordenService.listarTodas();
            if (ordenes.isEmpty()) {
                return Result.success("No hay ordenes registradas en el sistema",ordenes);
            }
            return Result.success("Todas las ordenes",ordenes);
        }catch (RuntimeException e) {
            return Result.failed("Error al listar el ordenes: "+e.getMessage());
        }
    }


    private double montoPorVehiculo(TipoVehiculo tipoVehiculo) {
        return switch (tipoVehiculo){
            case MOTO -> 0;
            case LIGERO -> 15000;
            case PESADO -> 30000;
        };
    }
}
