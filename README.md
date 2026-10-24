# Peaje - Sistema de Gestión de Peajes

[![Java](https://img.shields.io/badge/Java-25%2B-blue.svg)](https://www.oracle.com/java/technologies/downloads/)
[![Maven](https://img.shields.io/badge/Maven-3.x-red.svg)](https://maven.apache.org/)

Sistema de gestión para estaciones de peaje, desarrollado en Java utilizando una arquitectura **MVC (Modelo-Vista-Controlador)**. Este proyecto permite automatizar el registro de casetas y el procesamiento de órdenes de pago de peajes vehiculares.

## 📋 Funcionalidades Principales

El sistema ofrece una interfaz interactiva de consola para realizar las siguientes operaciones:

*   **Gestión de Casetas**:
    *   Registrar nuevas estaciones de peaje.
    *   Asociar medios de pago permitidos (`EFECTIVO`, `COLPASS`) a cada caseta.
    *   Listar todas las casetas registradas en el sistema.
*   **Gestión de Órdenes**:
    *   **Procesar pago**: Registrar el pago de peaje validando el tipo de vehículo (`PESADO`, `LIGERO`, `MOTO`) y el medio de pago.
    *   **Consulta rápida**: Buscar una orden específica por su identificador (ID).
    *   **Reportes**:
        *   Consultar órdenes filtradas por fecha y estación específica.
        *   Ver el historial general de transacciones filtrado por fecha.

## 🛠️ Requisitos Técnicos

*   **Lenguaje**: Java 27 o superior.
*   **Herramienta de Construcción**: Maven.

## 📁 Estructura del Proyecto

El código está organizado siguiendo los principios MVC para garantizar la separación de responsabilidades:

```text
src/main/java/org/example/
├── controller/  # Controladores (Lógica de flujo)
├── exception/   # Manejo de excepciones personalizadas
├── Modelo/      # Entidades de dominio (Caseta, Orden, Enums)
├── service/     # Lógica de negocio (Servicios)
└── view/        # Interfaz de usuario (Consola)
```

## 🚀 Cómo Ejecutar

1.  Asegúrate de tener instalado **Java 27+** y **Maven**.
2.  Clona el repositorio.
3.  Navega a la carpeta del proyecto.
4.  Ejecuta el proyecto utilizando Maven:
    ```bash
    mvn clean compile exec:java -Dexec.mainClass="org.example.Main"
    ```

---
*Desarrollado como proyecto de gestión de sistemas de peaje.*
