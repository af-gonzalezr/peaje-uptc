package org.example.view;

public interface IVistaOrden {
    void registrarOrden(
            String idOrden,
            String montoStr,
            String tipoPagoStr,
            String tipoVehiculoStr,
            String idCaseta,
            String fechaHoraStr
    );
    void buscarOrdenPorId(String idOrden);
    void consultarOrdenesPorFechaYCaseta(String idCaseta, String fechaStr);
    void consultarOrdenesPorFechaGeneral(String fechaStr);
}