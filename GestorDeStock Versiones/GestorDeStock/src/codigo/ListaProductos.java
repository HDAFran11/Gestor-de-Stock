package codigo;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaProductos implements Serializable {
    private ArrayList<Producto> productos;

    public ListaProductos() {
        productos = new ArrayList<>();
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    // Método para añadir un producto
    public void añadirProducto(Producto producto) {
        productos.add(producto);
    }

    // Método para eliminar un producto por código
    public void eliminarProducto(int codigo) {
        Producto producto = buscarProducto(codigo);
        if (producto != null) {
            productos.remove(producto);
        }
    }

    // Método para modificar el precio de un producto por código
    public void modificarPrecio(int codigo, double nuevoPrecio) {
        Producto producto = buscarProducto(codigo);
        if (producto != null) {
            producto.setPrecio(nuevoPrecio);
        }
    }

    // Método para modificar el stock de un producto por código
    public void modificarStock(int codigo, int nuevoStock) {
        Producto producto = buscarProducto(codigo);
        if (producto != null) {
            producto.setStock(nuevoStock);
        }
    }

    // Método para modificar todos los precios de los productos
    public void modificarTodosLosPrecios(double porcentaje) {
        for (Producto producto : productos) {
            double nuevoPrecio = producto.getPrecio() * (1 + porcentaje / 100);
            producto.setPrecio(nuevoPrecio);
        }
    }

    // Método para buscar un producto por código
    public Producto buscarProducto(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null; // Devuelve null si no se encuentra el producto
    }
    
    public ArrayList<Producto> buscarProductosPorNombre(String nombre) {
        ArrayList<Producto> resultados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getDescripcion().toLowerCase().contains(nombre.toLowerCase())) {
                resultados.add(producto);
            }
        }
        return resultados;
    }
    
    public boolean productoExiste(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return true;
            }
        }
        return false;
    }

    
}