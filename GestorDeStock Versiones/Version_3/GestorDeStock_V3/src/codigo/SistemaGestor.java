package codigo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SistemaGestor implements Serializable {
   private static final long serialVersionUID = 1L;
   private List<Producto> listaProductos = new ArrayList<>();
   private List<Venta> listaVentas = new ArrayList<>();
   private int contadorVentas = 1;

   public void añadirProducto(Producto producto) {
      this.listaProductos.add(producto);
   }

   public boolean eliminarProducto(int id) {
      Iterator<Producto> iterator = this.listaProductos.iterator();
      while (iterator.hasNext()) {
         Producto producto = iterator.next();
         if (producto.getId() == id) {
            iterator.remove();
            return true;
         }
      }
      return false;
   }

   public boolean modificarStock(int id, int nuevoStock) {
      for (Producto producto : this.listaProductos) {
         if (producto.getId() == id) {
            producto.setStock(nuevoStock + producto.getStock());
            return true;
         }
      }
      return false;
   }

   public boolean modificarPrecio(int id, double nuevoPrecio) {
      for (Producto producto : this.listaProductos) {
         if (producto.getId() == id) {
            producto.setPrecio(nuevoPrecio);
            return true;
         }
      }
      return false;
   }

   public void modificarTodosLosPrecios(double porcentaje) {
      for (Producto producto : this.listaProductos) {
         double nuevoPrecio = producto.getPrecio() * (1.0 + porcentaje / 100.0);
         int precioRedondeado = (int) Math.round(nuevoPrecio);
         producto.setPrecio((double) precioRedondeado);
      }
   }

   public boolean registrarVenta(Venta venta) {
      for (Producto producto : venta.getProductos()) {
         Producto p = this.obtenerProductoPorId(producto.getId());
         if (p == null || producto.getStock() > p.getStock()) {
            return false;
         }
      }

      for (Producto producto : venta.getProductos()) {
         Producto p = this.obtenerProductoPorId(producto.getId());
         if (p != null) {
            p.setStock(p.getStock() - producto.getStock());
         }
      }

      this.listaVentas.add(venta);
      return true;
   }

   public int generarIdVenta() {
      return this.contadorVentas++;
   }

   public boolean esIdRepetido(int id) {
      for (Producto producto : this.listaProductos) {
         if (producto.getId() == id) {
            return true;
         }
      }
      return false;
   }

   public Producto obtenerProductoPorId(int id) {
      for (Producto producto : this.listaProductos) {
         if (producto.getId() == id) {
            return producto;
         }
      }
      return null;
   }

   public List<Producto> getListaProductos() {
      return this.listaProductos;
   }

   public List<Venta> getListaVentas() {
      return this.listaVentas;
   }

   public void guardarEstado(String nombreArchivo) throws IOException {
      try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
         oos.writeObject(this);
      }
   }

   public static SistemaGestor cargarEstado(String nombreArchivo) throws IOException, ClassNotFoundException {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
         return (SistemaGestor) ois.readObject();
      }
   }
}
