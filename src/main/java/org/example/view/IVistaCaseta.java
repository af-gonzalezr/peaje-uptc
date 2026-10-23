package org.example.view;

public interface IVistaCaseta {
    void registrarCaseta(String idCaseta, String nombre);
    void asociarMedioPago(String idCaseta, String tipoPagoStr);
    void listarCasetas();
    void consultarMediosPagoSoportados(String idCaseta);
}