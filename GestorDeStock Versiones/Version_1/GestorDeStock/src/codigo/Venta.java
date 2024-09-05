package codigo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Venta implements Serializable {
    private int numero;
    private ArrayList<Producto> productos;
    private Date fecha;
    private double total;

    public Venta(int numero, ArrayList<Producto> productos, Date fecha, double total) {
        this.numero = numero;
        this.productos = productos;
        this.fecha = fecha;
        this.total = total;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "numero=" + numero +
                ", productos=" + productos +
                ", fecha=" + fecha +
                ", total=" + total +
                '}';
    }
}
