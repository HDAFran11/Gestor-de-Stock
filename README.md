# 📦 Gestor de Stock

Aplicación de escritorio desarrollada en **Java** para la administración eficiente de inventarios y registro de ventas en pequeños comercios. El sistema permite controlar el stock de productos, realizar ventas y generar reportes diarios de actividad.

## 🚀 Características Principales

* **Gestión de Productos:** Alta, baja y modificación de productos con control de stock y precios.
* **Registro de Ventas:** Procesamiento de ventas calculando totales automáticamente y descontando stock en tiempo real.
* **Persistencia de Datos:** El sistema guarda automáticamente todo el inventario y el historial de ventas en un archivo binario (`sistemaGestor.dat`), asegurando que no se pierdan datos al cerrar la aplicación.
* **Reportes Automáticos:** Generación de un archivo de texto (`informeDelDia.txt`) con el resumen de las operaciones diarias para control de caja.
* **Interfaz Gráfica:** GUI intuitiva (`VentanaPrincipal`) para facilitar la operación sin necesidad de consola.

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java
* **Interfaz:** Java Swing / AWT (Ventanas y componentes gráficos)
* **Almacenamiento:** Serialización de objetos (Archivos `.dat`)

## 📂 Estructura del Proyecto

El proyecto ha evolucionado a través de 3 versiones, siendo la **Versión 3** la más estable y completa:

```text
GestorDeStock_V3/
├── bin/                  # Archivos compilados (.class)
├── src/codigo/           # Código fuente
│   ├── Producto.java     # Clase entidad Producto
│   ├── Venta.java        # Clase entidad Venta
│   ├── SistemaGestor.java# Lógica de negocio (Controlador)
│   └── VentanaPrincipal.java # Interfaz gráfica de usuario
├── sistemaGestor.dat     # Base de datos local (Persistencia)
└── informeDelDia.txt     # Reporte de salida
```
## 🔧 Instalación y Ejecución
Requisitos: Tener instalado Java (JRE/JDK).

Ejecutar:

 * Puedes correr directamente el archivo JAR ejecutable:

```text
java -jar GestorDeStock_v3.jar
```
 * O compilar y correr desde el código fuente (src/codigo/VentanaPrincipal.java).

## 📄 Notas de Versión
* Versión 1: Prototipo inicial con consola.

* Versión 2: Mejoras en la estructura de clases.

* Versión 3: Implementación completa de GUI, persistencia robusta y generación de informes.
