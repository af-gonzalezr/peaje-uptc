# Guía de Desarrollo: Sistema de Gestión de Peajes (Java Puro / N-Capas)

Este documento define la arquitectura, convenciones, interfaces y casos de uso para la implementación del sistema de peaje en Java utilizando un enfoque en **N-Capas**, persistencia en memoria y separación estricta de responsabilidades.

---

## 1. Arquitectura del Sistema (N-Capas)

El proyecto sigue una estructura jerárquica de 4 capas para mantener un desacoplamiento efectivo y facilitar el trabajo colaborativo:

1. **Capa de Presentación (`org.example.vista`)**:
    - Menú interactivo de consola.
    - Captura la entrada del usuario (`Scanner`) y muestra resultados o mensajes de error.
    - **No realiza conversiones avanzadas de tipos ni lógica de negocio.**

2. **Capa del Controlador (`org.example.controller`)**:
    - Funciona como puente entre la Vista y los Servicios.
    - **Responsabilidad principal:** Recibir `Strings` o entradas primitivas de la vista, **parsear/convertir** los datos a tipos adecuados (`LocalDateTime`, `enums`, etc.), capturar posibles errores de formato y delegar la operación al servicio correspondiente.
    - Expone sus funciones mediante **interfaces**.

3. **Capa de Servicio / Lógica de Negocio (`org.example.service`)**:
    - Recibe **datos ya parseados** desde el controlador.
    - Implementa las reglas de negocio (ej. validación de tipos de pago soportados por caseta, cálculo de tarifas según tipo de vehículo, verificación de duplicados).
    - Manipula la persistencia mediante repositorios/listas.
    - Retorna objetos `Result<T>` o lanza excepciones del dominio (`TollException`).

4. **Capa de Persistencia / Datos (`org.example.repository`)**:
    - Almacenamiento en memoria mediante colecciones (`List<T>`, `Map<K,V>`).
    - Abstraída tras interfaces DAO/Repository.

5. **Capa de Modelo / Dominio (`org.example.modelo`)**:
    - Clases POJO (`Caseta`, `Orden`, `TipoPago`, `TipoVehiculo`).

---

## 2. Definición de Excepciones Personales

Para un manejo de errores robusto, definimos una jerarquía de excepciones dentro del paquete `org.example.exception`:

```
TollException (RuntimeException / Exception)
 ├── CasetaNotFoundException
 ├── OrdenNotFoundException
 ├── TipoPagoNoSoportadoException
 └── FormatoInvalidoException
```

---

## 3. Casos de Uso

| ID | Caso de Uso | Descripción |
| :--- | :--- | :--- |
| **CU01** | Registrar Caseta | Permite crear una caseta configurando sus tipos de pago permitidos. |
| **CU02** | Consultar Casetas | Lista todas las casetas registradas en el sistema. |
| **CU03** | Crear / Pagar Orden | Genera una orden de pago asociada a un vehículo en una caseta específica validando el tipo de pago. |
| **CU04** | Buscar Ordenes por Fecha | Filtra y consulta todas las órdenes registradas en una fecha o rango específico para una caseta o en general. |
| **CU05** | Reporte diario de recaudo | Genera un consolidado del total recaudado por caseta en un día dado. |

---

## 4. Contratos e Interfaces del Proyecto

### 4.1 Interfaces del Controlador (`org.example.controller`)

El controlador se define con interfaces para permitir que el equipo de UI desarrolle la vista sin depender de la implementación final del controlador.

```java
package org.example.controller;

import java.util.List;

public interface ICasetaController {
    /**
     * Parsea los datos de entrada y delega la creación de la caseta.
     * @param idCaseta Identificador único.
     * @param nombre Nombre descriptivo de la caseta.
     * @param tiposPagoPermitidos Cadenas separadas por comas (ej. "EFECTIVO,COLPASS").
     * @return Result con mensaje de éxito o falla.
     */
    Result<String> registrarCaseta(String idCaseta, String nombre, String tiposPagoPermitidos);

    /**
     * Obtiene la lista formatada o estructurada de las casetas.
     */
    Result<List<String>> listarCasetas();
}
```

```java
package org.example.controller;

import java.util.List;

public interface IOrdenController {
    /**
     * Parsea datos, valida formato de fechas/enums y registra el pago de la orden.
     * @param idOrden ID de la orden.
     * @param monto Cadena a parsear a double.
     * @param tipoPago Cadena a convertir a enum tipoPago.
     * @param tipoVehiculo Cadena a convertir a enum tipoVehiculo.
     * @param idCaseta ID de la caseta donde transita.
     * @param fechaHora Cadena de fecha en formato ISO o personalizado ("yyyy-MM-dd HH:mm").
     */
    Result<String> crearYPagarOrden(String idOrden, String monto, String tipoPago, 
                                    String tipoVehiculo, String idCaseta, String fechaHora);

    /**
     * Parsea la fecha enviada desde el menú y busca las órdenes de ese día.
     * @param idCaseta ID de la caseta a consultar.
     * @param fechaTexto Cadena en formato "yyyy-MM-dd".
     */
    Result<List<String>> buscarOrdenesPorFecha(String idCaseta, String fechaTexto);
}
```

---

### 4.2 Interfaces del Servicio (`org.example.service`)

Los servicios trabajan directamente con **tipos fuertemente tipados** (`enums`, `LocalDateTime`, `double`).

```java
package org.example.service;

import org.example.Modelo.Caseta;
import org.example.Modelo.TipoPago;
import org.example.Modelo.tipoPago;

import java.util.List;

public interface ICasetaService {
   void registrarCaseta(Caseta caseta, List<TipoPago> pagosPermitidos);

   Caseta buscarPorId(String idCaseta);

   List<Caseta> listarTodas();
}
```

```java
package org.example.service;

import org.example.Modelo.Orden;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IOrdenService {
    void registrarOrden(Orden orden);
    List<Orden> buscarPorCasetaYFecha(String idCaseta, LocalDate fecha);
    List<Orden> buscarPorRangoFechas(String idCaseta, LocalDateTime inicio, LocalDateTime fin);
}
```

---

## 5. Ajustes Recomendados al Modelo Existente

1. **Soporte de Tipos de Pago en `Caseta`**:
   Actualmente la clase `Caseta` solo tiene lista de órdenes. Debe incluir la lista o conjunto de `TipoPago` que soporta:
   ```java
   private List<tipoPago> tiposPagoSoportados;
   ```

2. **Validación de Pago en Dominio/Servicio**:
   Antes de agregar la orden a una caseta, la caseta (o el servicio) debe validar si el `TipoPago` de la orden está dentro de `tiposPagoSoportados`.

3. **Convenciones Naming Java**:
   Renombrar los enums siguiendo la convención PascalCase: `TipoPago` -> `TipoPago` y `TipoVehiculo` -> `TipoVehiculo`.

---

## 6. Flujo de Datos Típico (Ejemplo CU03)

1. **Vista**: Pide al usuario datos por teclado como texto. Llama a `ordenController.crearYPagarOrden(...)`.
2. **Controller**:
    - Parsea `monto` -> `double`.
    - Parsea `TipoPago` -> `TipoPago.valueOf(...)`.
    - Parsea `fechaHora` -> `LocalDateTime.parse(...)`.
    - Maneja `FormatoInvalidoException` si el parseo falla.
    - Construye la entidad `Orden` y la envía a `ordenService.registrarOrden(orden)`.
3. **Service**:
    - Verifica que la caseta exista.
    - Verifica que la caseta acepte ese `TipoPago`.
    - Agrega la orden a la lista/repositorio de memoria.
    - Retorna un `Result.success(...)`.