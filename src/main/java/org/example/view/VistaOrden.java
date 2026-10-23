package org.example.view;

import org.example.Modelo.Orden;
import org.example.controller.IOrdenController;
import org.example.service.Result;

import java.util.List;

public class VistaOrden implements IVistaOrden {

    private final IOrdenController controller;

    public VistaOrden(IOrdenController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("El controlador de órdenes no puede ser nulo.");
        }
        this.controller = controller;
    }

    @Override
    public void registrarOrden(
            String idOrden,
            String tipoPagoStr,
            String tipoVehiculoStr,
            String idCaseta) {
        System.out.println("\nRegistrando orden...");

        // Se llama al controlador sin enviar montoStr ni fechaHoraStr
        Result<Orden> resultado = controller.crearYRegistrarOrden(
                idOrden,
                tipoPagoStr,
                tipoVehiculoStr,
                idCaseta);

        ProcesadorResultado.procesar(resultado);

        if (resultado != null && resultado.status() && resultado.element() != null) {
            imprimirDetalleOrden(resultado.element());
        }
    }

    @Override
    public void buscarOrdenPorId(String idOrden) {
        System.out.println("\nBuscando orden: " + idOrden);
        Result<Orden> resultado = controller.buscarPorId(idOrden);

        ProcesadorResultado.procesar(resultado);

        if (resultado != null && resultado.status() && resultado.element() != null) {
            imprimirDetalleOrden(resultado.element());
        }
    }

    @Override
    public void consultarOrdenesPorFechaYCaseta(String idCaseta, String fechaStr) {
        System.out.println("\nConsultando órdenes de la caseta " + idCaseta + " para la fecha: " + fechaStr);
        Result<List<Orden>> resultado = controller.consultarPorFechaYCaseta(idCaseta, fechaStr);

        ProcesadorResultado.procesar(resultado);

        if (resultado != null && resultado.status() && resultado.element() != null) {
            imprimirListadoOrdenes(resultado.element(), "Órdenes Caseta " + idCaseta + " (" + fechaStr + ")");
        }
    }

    @Override
    public void consultarOrdenesPorFechaGeneral(String fechaStr) {
        System.out.println("\nConsultando reporte general para la fecha: " + fechaStr);
        Result<List<Orden>> resultado = controller.consultarPorFechaGeneral(fechaStr);

        ProcesadorResultado.procesar(resultado);

        if (resultado != null && resultado.status() && resultado.element() != null) {
            imprimirListadoOrdenes(resultado.element(), "Reporte General de Órdenes (" + fechaStr + ")");
        }
    }

    private void imprimirDetalleOrden(Orden orden) {
        System.out.println("\n---------------- DETALLE DE LA ORDEN ----------------");
        System.out.println("ID Orden:        " + orden.getIdOrden());
        System.out.println("Caseta:          " + orden.getIdCaseta());
        System.out.println("Fecha y Hora:    " + orden.getFechaHora());
        System.out.println("Tipo Vehículo:   " + orden.getTipoVehiculo());
        System.out.println("Medio de Pago:   " + orden.getTipoPago());
        System.out.println("Monto:           $" + orden.getMonto());
        System.out.println("-----------------------------------------------------");
    }

    private void imprimirListadoOrdenes(List<Orden> ordenes, String titulo) {
        if (ordenes.isEmpty()) {
            System.out.println("No se encontraron órdenes.");
            return;
        }

        System.out.println("\n=== " + titulo + " ===");
        double total = 0;
        for (Orden o : ordenes) {
            System.out.println("- ID: " + o.getIdOrden() +
                    " | Caseta: " + o.getIdCaseta() +
                    " | Fecha: " + o.getFechaHora() +
                    " | Vehículo: " + o.getTipoVehiculo() +
                    " | Pago: " + o.getTipoPago() +
                    " | Monto: $" + o.getMonto());
            total += o.getMonto();
        }
        System.out.println("Total de órdenes: " + ordenes.size());
        System.out.println("Total recaudado: $" + total);
        System.out.println("=====================================================");
    }
}