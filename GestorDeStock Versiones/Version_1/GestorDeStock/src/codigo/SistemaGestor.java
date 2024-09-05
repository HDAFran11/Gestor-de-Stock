package codigo;

import java.io.*;
import java.util.ArrayList;

public class SistemaGestor implements Serializable {
    private ListaProductos listaProductos;
    private ListaVentas listaVentas;

    public SistemaGestor() {
        listaProductos = new ListaProductos();
        listaVentas = new ListaVentas();
    }

    public ListaProductos getListaProductos() {
        return listaProductos;
    }

    public ListaVentas getListaVentas() {
        return listaVentas;
    }

    // Método para añadir un producto
    public void añadirProducto(Producto producto) {
        listaProductos.añadirProducto(producto);
    }

    // Método para eliminar un producto por código
    public void eliminarProducto(int codigo) {
        listaProductos.eliminarProducto(codigo);
    }

    // Método para modificar el precio de un producto por código
    public void modificarPrecio(int codigo, double nuevoPrecio) {
        listaProductos.modificarPrecio(codigo, nuevoPrecio);
    }

    // Método para modificar el stock de un producto por código
    public void modificarStock(int codigo, int nuevoStock) {
        listaProductos.modificarStock(codigo, nuevoStock);
    }

    // Método para añadir una venta
    public void registrarVenta(Venta venta) {
        listaVentas.añadirVenta(venta);
        for (Producto producto : venta.getProductos()) {
            Producto p = listaProductos.buscarProducto(producto.getCodigo());
            if (p != null) {
                p.setStock(p.getStock() - 1); // Descuento del stock
            }
        }
    }

    // Método para eliminar una venta por número de venta
    public void eliminarVenta(int numeroVenta) {
        Venta venta = listaVentas.buscarVentaPorNumero(numeroVenta);
        if (venta != null) {
            listaVentas.eliminarVenta(numeroVenta);
            for (Producto producto : venta.getProductos()) {
                Producto p = listaProductos.buscarProducto(producto.getCodigo());
                if (p != null) {
                    p.setStock(p.getStock() + 1); // Devolución del stock
                }
            }
        }
    }
    
    public boolean guardarSistema() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sistemaGestor.dat"))) {
            oos.writeObject(this);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static SistemaGestor cargarSistema() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("sistemaGestor.dat"))) {
            return (SistemaGestor) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new SistemaGestor(); // Retorna un nuevo sistema si no se puede cargar
        }
    }
}
