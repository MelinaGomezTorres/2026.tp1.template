# Documentación de Diseño – BiblioTech

## 👤 Alumno

* Nombre: *Melina Abril Gomez Torres*
* Carrera: *Ingeniería en Informática*
* Legajo: *63211*

---

## 1. Decisiones generales de arquitectura

El sistema se diseñó aplicando una arquitectura en capas para lograr separación de responsabilidades:

* `model`: entidades del dominio
* `repository`: acceso a datos (implementación en memoria)
* `service`: lógica de negocio
* `exception`: manejo de errores del dominio
* `Main`: capa de presentación (CLI)

Esto permite que cada capa tenga una única responsabilidad, facilitando mantenimiento y escalabilidad.

---

## 2. Uso de interfaces y desacoplamiento

Se utilizó el patrón **Repository + Interfaces**:

* `Repository<T, ID>` define operaciones genéricas
* `InMemoryRepository` implementa almacenamiento en memoria

Beneficio:

* desacoplamiento entre lógica de negocio y almacenamiento
* posibilidad de cambiar la persistencia (ej:base de datos) sin modificar servicios

---

## 3. Servicios como núcleo del sistema

Los servicios (`PrestamoService`, `LibroService`, `SocioService`) concentran la lógica de negocio.

Se aplicó:

* **Dependency Injection** por constructor
* separación entre validación, lógica y persistencia

Esto mejora testabilidad y evita lógica duplicada.

---

## 4. Uso de excepciones personalizadas

Se implementó una jerarquía de excepciones propias:

* `LibroNoExisteException`
* `LibroNoDisponibleException`
* `LimitePrestamoException`
* `SocioNoExisteException`
* `PrestamoNoEncontradoException`
* etc.

Beneficios:

* errores más descriptivos
* mejor control del flujo
* evita uso de `RuntimeException` genérica

---

## 5. Uso de Optional

Se utilizó `Optional` en los repositorios para evitar `null` en búsquedas:

```java
Optional<Libro> buscarPorId(String id);
```

Beneficio:

* evita NullPointerException
* obliga a manejar ausencia de datos explícitamente

---

## 6. Modelo de dominio

Se utilizaron:

* `Socio` como clase abstracta
* `Estudiante` y `Docente` como especializaciones
* `Libro` como entidad principal
* `Historial` para auditoría de operaciones
* `Prestamo` para control de ciclo de vida

Esto permite aplicar polimorfismo y extender el sistema fácilmente.

---

## 7. Historial y trazabilidad

Cada operación de préstamo o devolución se registra en `Historial`.

Permite:

* auditoría de acciones
* seguimiento de operaciones
* trazabilidad del sistema

---

## 8. Diseño orientado a pruebas

Él `Main` funciona como simulador del sistema:

* carga de datos inicial
* pruebas de flujo normal
* pruebas de errores

Esto facilita validación sin necesidad de UI.

---

## Conclusión

El sistema fue diseñado siguiendo principios de:

* SOLID (especialmente SRP y DIP)
* programación orientada a objetos
* separación en capas
* manejo seguro de errores
