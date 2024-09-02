package codigo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ListaVentas implements Serializable {
    private ArrayList<Venta> ventas;
    private static int siguienteNumero = 1; // Contador estático para el número de venta

    public ListaVentas() {
        ventas = new ArrayList<>();
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }

    // Método para añadir una venta
    public void añadirVenta(Venta venta) {
        ventas.add(venta);
    }

    // Método para eliminar una venta por número
    public void eliminarVenta(int numero) {
        Venta venta = buscarVentaPorNumero(numero);
        if (venta != null) {
            ventas.remove(venta);
        }
    }

    // Método para buscar una venta por número
    public Venta buscarVentaPorNumero(int numero) {
        for (Venta venta : ventas) {
            if (venta.getNumero() == numero) {
                return venta;
            }
        }
        return null; // Devuelve null si no se encuentra la venta
    }
    
    public int generarNumeroVenta() {
        return siguienteNumero++;
    }
}
