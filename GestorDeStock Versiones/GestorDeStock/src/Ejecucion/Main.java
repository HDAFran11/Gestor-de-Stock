package Ejecucion;

import javax.swing.SwingUtilities;

import codigo.*;

public class Main {
    public static void main(String[] args) {
        // Crear instancias de las clases necesarias
        ListaProductos listaProductos = new ListaProductos();
        ListaVentas listaVentas = new ListaVentas();
        SistemaGestor sistemaGestor = new SistemaGestor();

        // Crear y mostrar la ventana
        SwingUtilities.invokeLater(() -> {
            Ventana ventana = new Ventana(sistemaGestor);
            ventana.setVisible(true);
        });
    }
}
