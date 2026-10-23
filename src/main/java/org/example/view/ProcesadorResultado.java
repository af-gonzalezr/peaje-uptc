package org.example.view;

import org.example.service.Result;

public class ProcesadorResultado {

    public static <T> void procesar(Result<T> result) {
        if (result == null) {
            System.err.println("Error: El resultado obtenido es nulo.");
            return;
        }

        if (result.status()) {
            System.out.println("✔ [ÉXITO] " + result.message());
            if (result.element() != null) {
                System.out.println("   Detalle: " + result.element());
            }
        } else {
            System.err.println("✖ [ERROR] " + result.message());
        }
    }
}