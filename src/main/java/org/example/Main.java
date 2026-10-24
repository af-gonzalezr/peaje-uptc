package org.example;

import org.example.controller.ICasetaController;
import org.example.controller.ICasetaControllerImpl;
import org.example.controller.IOrdenController;
import org.example.controller.IOrdenControllerImpl;
import org.example.service.CasetaService;
import org.example.service.ICasetaService;
import org.example.service.IOrdenService;
import org.example.service.OrdenServiceImpl;
import org.example.view.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        ICasetaService casetaService = new CasetaService();
        IOrdenService ordenService = new OrdenServiceImpl(casetaService);

        ICasetaController casetaController = new ICasetaControllerImpl(casetaService);
        IOrdenController ordenController = new IOrdenControllerImpl(ordenService);

        IVistaCaseta vistaCaseta = new VistaCaseta(casetaController);
        IVistaOrden vistaOrden = new VistaOrden(ordenController);

        MenuPrincipal menu = new MenuPrincipal(vistaCaseta, vistaOrden);
        menu.iniciar();

    }
}
