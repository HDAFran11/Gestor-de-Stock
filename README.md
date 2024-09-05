# Sistema Gestor de Stock para Kiosco - Versiones y Ejecutables

Este repositorio contiene las diferentes versiones del desarrollo del **Sistema Gestor de Stock para un Kiosco**. El proyecto ha evolucionado a lo largo del tiempo, y cada versión incluye mejoras y nuevas funcionalidades. Además, se proporcionan los ejecutables correspondientes para que puedas probar el sistema sin necesidad de compilar el código.

## Descripción del Proyecto

El **Sistema Gestor de Stock para Kiosco** es una aplicación de escritorio en Java que permite gestionar productos y registrar ventas de manera eficiente. A lo largo de las distintas versiones del proyecto, se han añadido características como:

- Gestión de productos (añadir, eliminar, modificar).
- Registro de ventas, con la posibilidad de descontar el stock automáticamente.
- Notificaciones cuando el stock llega a 0 o no es suficiente para una venta.
- Serialización de datos para evitar la pérdida de información entre sesiones.

## Estructura del Repositorio

Este repositorio está organizado en carpetas, cada una representando una versión diferente del proyecto.
Cada versión incluye:

1. **Código fuente**: El código Java desarrollado para esa versión.
2. **Ejecutable (.jar)**: Un archivo ejecutable que puedes correr en cualquier entorno que soporte Java, sin necesidad de compilar el código.

## Requisitos del Sistema

- **Java 8** o superior
- Librerías de Java Swing para la interfaz gráfica (incluidas por defecto en JDK)

## Cómo Ejecutar los Ejecutables

Para probar una versión del proyecto:

1. Descarga el archivo `.jar` correspondiente desde la carpeta de la versión deseada.
2. Abre una terminal o línea de comandos y ejecuta el archivo `.jar` con el siguiente comando:
   "java -jar nombre-del-archivo.jar"
