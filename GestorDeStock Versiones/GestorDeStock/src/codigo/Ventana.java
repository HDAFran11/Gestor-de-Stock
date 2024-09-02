package codigo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class Ventana extends JFrame {
    private SistemaGestor sistemaGestor;
    private JTable tablaProductos;
    private JTable tablaVentas;
    private DefaultTableModel modeloProductos;
    private DefaultTableModel modeloVentas;

    public Ventana(SistemaGestor sistemaGestor) {
        this.sistemaGestor = sistemaGestor;
        setTitle("Sistema Gestor de Stock");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Paneles para contenido
        JPanel panelProductos = new JPanel(new BorderLayout());
        JPanel panelVentas = new JPanel(new BorderLayout());
        JPanel panelBotones = new JPanel();
        
        // Configuración de la tabla de productos
        modeloProductos = new DefaultTableModel(new String[]{"Código", "Descripción", "Precio", "Stock"}, 0);
        tablaProductos = new JTable(modeloProductos);
        JScrollPane scrollProductos = new JScrollPane(tablaProductos);
        panelProductos.add(scrollProductos, BorderLayout.CENTER);
        panelProductos.setBorder(BorderFactory.createTitledBorder("Productos"));

        // Configuración de la tabla de ventas
        modeloVentas = new DefaultTableModel(new String[]{"Número", "Productos", "Fecha", "Total"}, 0);
        tablaVentas = new JTable(modeloVentas);
        JScrollPane scrollVentas = new JScrollPane(tablaVentas);
        panelVentas.add(scrollVentas, BorderLayout.CENTER);
        panelVentas.setBorder(BorderFactory.createTitledBorder("Ventas"));

        // Configuración de los botones
        panelBotones.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Botones
        JButton btnAñadirProducto = new JButton("Añadir Producto");
        JButton btnEliminarProducto = new JButton("Eliminar Producto");
        JButton btnModificarPrecio = new JButton("Modificar Precio");
        JButton btnModificarStock = new JButton("Modificar Stock");
        JButton btnRegistrarVenta = new JButton("Registrar Venta");
        JButton btnModificarTodos = new JButton("Modificar Todos");
        JButton btnGuardarSistema = new JButton("Guardar Sistema");
        JButton btnBuscarProducto = new JButton("Buscar Producto");
        JButton btnCerrar = new JButton("Cerrar Programa");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelBotones.add(btnAñadirProducto, gbc);

        gbc.gridx = 1;
        panelBotones.add(btnEliminarProducto, gbc);

        gbc.gridx = 2;
        panelBotones.add(btnModificarPrecio, gbc);

        gbc.gridx = 3;
        panelBotones.add(btnModificarStock, gbc);

        gbc.gridx = 4;
        panelBotones.add(btnRegistrarVenta, gbc);

        gbc.gridx = 5;
        panelBotones.add(btnModificarTodos, gbc);

        gbc.gridx = 6;
        panelBotones.add(btnGuardarSistema, gbc);

        gbc.gridx = 7;
        panelBotones.add(btnBuscarProducto, gbc);

        gbc.gridx = 8;
        panelBotones.add(btnCerrar, gbc);

        // Añadir componentes al frame
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Productos", panelProductos);
        tabbedPane.addTab("Ventas", panelVentas);

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos de los botones
        btnAñadirProducto.addActionListener(e -> añadirProducto());
        btnEliminarProducto.addActionListener(e -> eliminarProducto());
        btnModificarPrecio.addActionListener(e -> modificarPrecio());
        btnModificarStock.addActionListener(e -> modificarStock());
        btnRegistrarVenta.addActionListener(e -> registrarVenta());
        btnModificarTodos.addActionListener(e -> modificarTodos());
        btnGuardarSistema.addActionListener(e -> guardarSistema());
        btnBuscarProducto.addActionListener(e -> buscarProductoPorNombre());
        btnCerrar.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea cerrar el programa y guardar el estado?", "Confirmar cierre", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                serializarSistema();
                System.exit(0);
            }
        });

        // Añadir el WindowListener para manejar el cierre de la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int respuesta = JOptionPane.showConfirmDialog(Ventana.this, "¿Está seguro de que desea cerrar el programa y guardar el estado?", "Confirmar cierre", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    serializarSistema();
                    System.exit(0);
                }
            }
        });

        // Cargar datos iniciales en las tablas
        cargarProductos();
        cargarVentas();

        setVisible(true);
    }

    private void cargarProductos() {
        modeloProductos.setRowCount(0); // Limpiar tabla
        for (Producto producto : sistemaGestor.getListaProductos().getProductos()) {
            modeloProductos.addRow(new Object[]{producto.getCodigo(), producto.getDescripcion(), producto.getPrecio(), producto.getStock()});
        }
    }

    private void cargarVentas() {
        modeloVentas.setRowCount(0); // Limpiar tabla
        for (Venta venta : sistemaGestor.getListaVentas().getVentas()) {
            modeloVentas.addRow(new Object[]{
                venta.getNumero(),
                obtenerDescripcionProductos(venta.getProductos()),
                venta.getFecha(),
                venta.getTotal()
            });
        }
    }

    private void añadirProducto() {
        // Diálogo para ingresar datos del nuevo producto
        String codigoStr = JOptionPane.showInputDialog(this, "Ingrese el código del producto:");
        if (codigoStr == null) return; // Cancelado
        int codigo = Integer.parseInt(codigoStr);

        // Verificar si el código ya existe
        if (sistemaGestor.getListaProductos().productoExiste(codigo)) {
            JOptionPane.showMessageDialog(this, "El código del producto ya existe. Ingrese un código único.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String descripcion = JOptionPane.showInputDialog(this, "Ingrese la descripción del producto:");
        if (descripcion == null) return; // Cancelado

        String precioStr = JOptionPane.showInputDialog(this, "Ingrese el precio del producto:");
        if (precioStr == null) return; // Cancelado
        double precio = Double.parseDouble(precioStr);

        String stockStr = JOptionPane.showInputDialog(this, "Ingrese el stock del producto:");
        if (stockStr == null) return; // Cancelado
        int stock = Integer.parseInt(stockStr);

        // Crear el nuevo producto y añadirlo a la lista
        Producto producto = new Producto(codigo, descripcion, precio, stock);
        sistemaGestor.getListaProductos().añadirProducto(producto);
        cargarProductos(); // Actualiza la tabla de productos
        JOptionPane.showMessageDialog(this, "Producto añadido con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarProducto() {
        String codigoStr = JOptionPane.showInputDialog(this, "Ingrese el código del producto a eliminar:");
        if (codigoStr == null) return; // Cancelado
        int codigo = Integer.parseInt(codigoStr);

        sistemaGestor.getListaProductos().eliminarProducto(codigo);
        cargarProductos(); // Actualiza la tabla de productos
        JOptionPane.showMessageDialog(this, "Producto eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void modificarPrecio() {
        String codigoStr = JOptionPane.showInputDialog(this, "Ingrese el código del producto:");
        if (codigoStr == null) return; // Cancelado
        int codigo = Integer.parseInt(codigoStr);

        String precioStr = JOptionPane.showInputDialog(this, "Ingrese el nuevo precio del producto:");
        if (precioStr == null) return; // Cancelado
        double precio = Double.parseDouble(precioStr);

        sistemaGestor.getListaProductos().modificarPrecio(codigo, precio);
        cargarProductos(); // Actualiza la tabla de productos
        JOptionPane.showMessageDialog(this, "Precio modificado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void modificarStock() {
        String codigoStr = JOptionPane.showInputDialog(this, "Ingrese el código del producto:");
        if (codigoStr == null) return; // Cancelado
        int codigo = Integer.parseInt(codigoStr);

        String stockStr = JOptionPane.showInputDialog(this, "Ingrese el nuevo stock del producto:");
        if (stockStr == null) return; // Cancelado
        int stock = Integer.parseInt(stockStr);

        sistemaGestor.getListaProductos().modificarStock(codigo, stock);
        cargarProductos(); // Actualiza la tabla de productos
        JOptionPane.showMessageDialog(this, "Stock modificado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void registrarVenta() {
        ArrayList<Producto> productosVenta = new ArrayList<>();
        double totalVenta = 0;

        while (true) {
            // Diálogo para ingresar el código del producto
            String codigoStr = JOptionPane.showInputDialog(this, "Ingrese el código del producto (o cancele para terminar):");
            if (codigoStr == null) break; // Cancelado
            int codigo = Integer.parseInt(codigoStr);

            Producto producto = sistemaGestor.getListaProductos().buscarProducto(codigo);
            if (producto == null) {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            if (producto.getStock() <= 0) {
                JOptionPane.showMessageDialog(this, "El stock del producto es 0.", "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            // Agregar el producto a la venta
            productosVenta.add(producto);
            totalVenta += producto.getPrecio();

            // Disminuir el stock
            sistemaGestor.getListaProductos().modificarStock(codigo, producto.getStock() - 1);
        }

        if (productosVenta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se registraron productos para la venta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear la venta y añadirla a la lista
        Venta venta = new Venta(sistemaGestor.getListaVentas().generarNumeroVenta(), productosVenta, new java.util.Date(), totalVenta);
        sistemaGestor.getListaVentas().añadirVenta(venta);
        cargarVentas(); // Actualiza la tabla de ventas
        JOptionPane.showMessageDialog(this, "Venta registrada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void modificarTodos() {
        String nuevoPrecioStr = JOptionPane.showInputDialog(this, "Ingrese el nuevo precio para todos los productos:");
        if (nuevoPrecioStr == null) return; // Cancelado
        double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);

        sistemaGestor.getListaProductos().modificarTodosLosPrecios(nuevoPrecio);
        cargarProductos(); // Actualiza la tabla de productos
        JOptionPane.showMessageDialog(this, "Precios modificados con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void buscarProductoPorNombre() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre o parte del nombre del producto a buscar:");
        if (nombre != null) {
            ArrayList<Producto> resultados = sistemaGestor.getListaProductos().buscarProductosPorNombre(nombre);
            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron productos con ese nombre.", "Resultado de la búsqueda", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder("Resultados de la búsqueda:\n");
                for (Producto p : resultados) {
                    sb.append("Código: ").append(p.getCodigo()).append(", Descripción: ").append(p.getDescripcion()).append("\n");
                }
                JOptionPane.showMessageDialog(this, sb.toString(), "Resultado de la búsqueda", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    private void guardarSistema() {
        serializarSistema();
        JOptionPane.showMessageDialog(this, "Sistema guardado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void serializarSistema() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sistemaGestor.dat"))) {
            oos.writeObject(sistemaGestor);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerDescripcionProductos(ArrayList<Producto> productos) {
        StringBuilder descripcion = new StringBuilder();
        for (Producto producto : productos) {
            if (descripcion.length() > 0) {
                descripcion.append(", ");
            }
            descripcion.append(producto.getDescripcion());
        }
        return descripcion.toString();
    }
}
