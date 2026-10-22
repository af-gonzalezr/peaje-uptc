package org.example.Modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Caseta {
    private String idCaseta;
    private String nombre;
    private ArrayList<Orden> ordenes;

    public Caseta(String idCaseta, String nombre) {
        this.idCaseta = idCaseta;
        this.nombre = nombre;
        this.ordenes = new ArrayList<>();
    }

    public String getIdCaseta() {
        return idCaseta;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Orden> getOrdenes() {
        return ordenes;
    }
    
    public void agregarOrden(Orden orden) {
        ordenes.add(orden);
    }

    public List <Orden> getFechasOrdenes(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        
       return ordenes.stream()
                .filter(orden -> orden.getFechaHora().isAfter(fechaInicio) && orden.getFechaHora().isBefore(fechaFin))
                .toList();
    }

    

}
