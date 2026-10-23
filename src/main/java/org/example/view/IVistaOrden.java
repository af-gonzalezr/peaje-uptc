package org.example.view;

public interface IVistaOrden {
    void registrarOrden(
            String idOrden,
            String tipoPagoStr,
            String tipoVehiculoStr,
            String idCaseta
    );
    void buscarOrdenPorId(String idOrden);
    void consultarOrdenesPorFechaYCaseta(String idCaseta, String fechaStr);
    void consultarOrdenesPorFechaGeneral(String fechaStr);
}