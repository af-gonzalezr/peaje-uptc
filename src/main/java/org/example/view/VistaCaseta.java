package org.example.view;

import org.example.Modelo.Caseta;
import org.example.controller.ICasetaController;
import org.example.service.Result;

import java.util.List;

public class VistaCaseta implements IVistaCaseta {

    private final ICasetaController casetaController;

    public VistaCaseta(ICasetaController casetaController) {
        this.casetaController = casetaController;
    }

    @Override
    public void registrarCaseta(String idCaseta, String nombre) {
        ProcesadorResultado.procesar(casetaController.registrarCaseta(idCaseta, nombre));
    }

    @Override
    public void asociarMedioPago(String idCaseta, String tipoPagoStr) {
        ProcesadorResultado.procesar(casetaController.asociarMedioPago(idCaseta, tipoPagoStr));
    }

    @Override
    public void listarCasetas() {
        Result<List<Caseta>> resultado = casetaController.listarTodas();


        if (!resultado.status()) {
            ProcesadorResultado.procesar(resultado);
            return;
        }

        List<Caseta> casetas = resultado.element();
        if (casetas == null || casetas.isEmpty()) {
            System.out.println("No hay casetas registradas.");
            return;
        }

        System.out.println("--- Casetas registradas ---");
        for (Caseta caseta : casetas) {
            System.out.println(caseta.getIdCaseta() + " - " + caseta.getNombre());
        }
    }

    @Override
    public void consultarMediosPagoSoportados(String idCaseta) {
        ProcesadorResultado.procesar(casetaController.obtenerMediosPagoSoportados(idCaseta));
    }
}