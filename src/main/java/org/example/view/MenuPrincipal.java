package org.example.view;

import java.util.Scanner;

public class MenuPrincipal {
    private final IVistaCaseta vistaCaseta;
    private final IVistaOrden vistaOrden;
    private final Scanner scanner;

    public MenuPrincipal(IVistaCaseta vistaCaseta, IVistaOrden vistaOrden) {
        this.vistaCaseta = vistaCaseta;
        this.vistaOrden = vistaOrden;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = -1;
        do {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE PEAJES ===");
            System.out.println("1. Registrar Caseta");
            System.out.println("2. Asociar Medio de Pago a Caseta");
            System.out.println("3. Listar Casetas");
            System.out.println("4. Registrar/Pagar Orden Vehicular");
            System.out.println("5. Buscar Orden por ID");
            System.out.println("6. Consultar Órdenes por Fecha y Caseta");
            System.out.println("7. Consultar Órdenes por Fecha (General)");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
                ejecutarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.err.println("✖ Error: Ingrese un número válido.");
            }
        } while (opcion != 0);
    }

    private void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> {
                System.out.print("ID Caseta: ");
                String id = scanner.nextLine();
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
                vistaCaseta.registrarCaseta(id, nombre);
            }
            case 2 -> {
                System.out.print("ID Caseta: ");
                String id = scanner.nextLine();
                System.out.print("Medio de Pago (EFECTIVO / COLPASS): ");
                String pago = scanner.nextLine();
                vistaCaseta.asociarMedioPago(id, pago);
            }
            case 3 -> vistaCaseta.listarCasetas();
            case 4 -> {
                System.out.print("ID Orden: ");
                String idOrden = scanner.nextLine();
                System.out.print("Tipo Pago (EFECTIVO / COLPASS): ");
                String pago = scanner.nextLine();
                System.out.print("Tipo Vehículo (PESADO / LIGERO / MOTO): ");
                String vehiculo = scanner.nextLine();
                System.out.print("ID Caseta: ");
                String idCaseta = scanner.nextLine();
                vistaOrden.registrarOrden(idOrden, pago, vehiculo, idCaseta);
            }
            case 5 -> {
                System.out.print("ID Orden: ");
                String id = scanner.nextLine();
                vistaOrden.buscarOrdenPorId(id);
            }
            case 6 -> {
                System.out.print("ID Caseta: ");
                String idCaseta = scanner.nextLine();
                System.out.print("Fecha (yyyy-MM-dd): ");
                String fecha = scanner.nextLine();
                vistaOrden.consultarOrdenesPorFechaYCaseta(idCaseta, fecha);
            }
            case 7 -> {
                System.out.print("Fecha (yyyy-MM-dd): ");
                String fecha = scanner.nextLine();
                vistaOrden.consultarOrdenesPorFechaGeneral(fecha);
            }
            case 0 -> System.out.println("Saliendo del sistema...");
            default -> System.err.println("Opción no válida.");
        }
    }
}