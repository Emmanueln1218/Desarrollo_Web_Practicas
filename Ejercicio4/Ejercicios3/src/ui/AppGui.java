package ui;

import dao.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import model.*;

public class AppGui extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color DARK_BG = new Color(236, 240, 241);
    private static final Color CARD_BG = Color.WHITE;

    private final RepositoryFactory factory = RepositoryFactory.getInstance();
    private final ClienteDAO clienteDao = factory.getClienteDAO();
    private final ProductoDAO productoDao = factory.getProductoDAO();
    private final MamiferoDAO mascotaDao = factory.getMamiferoDAO();
    private final PedidoDAO pedidoDao = factory.getPedidoDAO();
    private final CaptDatos cap = CaptDatos.getInstance();

    private final DefaultTableModel clientesModel = new DefaultTableModel(new String[]{"Id","Nombre","Email","Telefono","Direccion"}, 0);
    private final DefaultTableModel productosModel = new DefaultTableModel(new String[]{"Id","Nombre","Precio","Stock"}, 0);
    private final DefaultTableModel mascotasModel = new DefaultTableModel(new String[]{"Id","Nombre","Raza","Tipo","Fecha","Peso","Lugar","Altura"}, 0);
    private final DefaultTableModel pedidosModel = new DefaultTableModel(new String[]{"Id","ClienteId","Total","Fecha"}, 0);

    public AppGui() {
        setLookAndFeel();
        setTitle("Sistema de Tienda y Mascotas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(DARK_BG);
        mainPanel.add(createHeader(), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabs.add("Clientes", buildClientesPanel());
        tabs.add("Productos", buildProductosPanel());
        tabs.add("Mascotas", buildMascotasPanel());
        tabs.add("Pedidos", buildPedidosPanel());

        mainPanel.add(tabs, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_COLOR);
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Sistema de Gestion - Tienda y Mascotas");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        header.add(title, BorderLayout.WEST);
        return header;
    }

    private JPanel buildClientesPanel() {
        return buildDataPanel(clientesModel, () -> refreshClientes(), () -> addCliente(), 
            (table) -> editCliente(table), (table) -> deleteCliente(table));
    }

    private JPanel buildProductosPanel() {
        return buildDataPanel(productosModel, () -> refreshProductos(), () -> addProducto(), 
            (table) -> editProducto(table), (table) -> deleteProducto(table));
    }

    private JPanel buildMascotasPanel() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(DARK_BG);
        p.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTable table = createStyledTable(mascotasModel);
        JScrollPane scroll = new JScrollPane(table);
        p.add(scroll, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttons.setBackground(CARD_BG);
        buttons.add(createStyledButton("Refrescar", SUCCESS_COLOR, () -> refreshMascotas()));
        buttons.add(createStyledButton("Agregar Perro", SECONDARY_COLOR, () -> addPerro()));
        buttons.add(createStyledButton("Agregar Gato", SECONDARY_COLOR, () -> addGato()));
        buttons.add(createStyledButton("Eliminar", DANGER_COLOR, () -> deleteMascota(table)));

        p.add(buttons, BorderLayout.SOUTH);
        refreshMascotas();
        return p;
    }

    private JPanel buildPedidosPanel() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(DARK_BG);
        p.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTable table = createStyledTable(pedidosModel);
        JScrollPane scroll = new JScrollPane(table);
        p.add(scroll, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttons.setBackground(CARD_BG);
        buttons.add(createStyledButton("Refrescar", SUCCESS_COLOR, () -> refreshPedidos()));
        buttons.add(createStyledButton("Crear Pedido", PRIMARY_COLOR, () -> createPedido()));

        p.add(buttons, BorderLayout.SOUTH);
        refreshPedidos();
        return p;
    }

    private JPanel buildDataPanel(DefaultTableModel model, Runnable refresh, Runnable add,
                                   java.util.function.Consumer<JTable> edit,
                                   java.util.function.Consumer<JTable> delete) {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(DARK_BG);
        p.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTable table = createStyledTable(model);
        JScrollPane scroll = new JScrollPane(table);
        p.add(scroll, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttons.setBackground(CARD_BG);
        buttons.add(createStyledButton("Refrescar", SUCCESS_COLOR, refresh));
        buttons.add(createStyledButton("Agregar", PRIMARY_COLOR, add));
        buttons.add(createStyledButton("Editar", SECONDARY_COLOR, () -> edit.accept(table)));
        buttons.add(createStyledButton("Eliminar", DANGER_COLOR, () -> delete.accept(table)));

        p.add(buttons, BorderLayout.SOUTH);
        refresh.run();
        return p;
    }

    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        table.setRowHeight(28);
        table.setShowGrid(true);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        return table;
    }

    private JButton createStyledButton(String text, Color bgColor, Runnable action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> action.run());
        return btn;
    }

    private void refreshClientes() {
        try {
            List<Cliente> list = clienteDao.listAll();
            clientesModel.setRowCount(0);
            for (Cliente c : list) clientesModel.addRow(new Object[]{c.getId(), c.getNombre(), c.getEmail(), c.getTelefono(), c.getDireccion()});
        } catch (Exception e) { showErr(e); }
    }

    private void addCliente() {
        try {
            String nombre = cap.readString("Nombre: ", 100);
            String email = cap.readString("Email: ", 100);
            String tel = cap.readString("Telefono: ", 20);
            String dir = cap.readString("Direccion: ", 200);
            Cliente c = new Cliente(nombre, email, tel, dir);
            clienteDao.insert(c);
            refreshClientes();
            JOptionPane.showMessageDialog(this, "Cliente agregado");
        } catch (Exception e) { showErr(e); }
    }

    private void editCliente(JTable table) {
        int r = table.getSelectedRow();
        if (r < 0) { JOptionPane.showMessageDialog(this, "Seleccione una fila"); return; }
        int id = (int)clientesModel.getValueAt(r, 0);
        try {
            Cliente c = clienteDao.findById(id);
            c.setNombre(cap.readString("Nuevo nombre: ", 100));
            c.setEmail(cap.readString("Nuevo email: ", 100));
            c.setTelefono(cap.readString("Nuevo telefono: ", 20));
            c.setDireccion(cap.readString("Nueva direccion: ", 200));
            clienteDao.update(c);
            refreshClientes();
        } catch(Exception e) { showErr(e); }
    }

    private void deleteCliente(JTable table) {
        int r = table.getSelectedRow();
        if (r < 0) { JOptionPane.showMessageDialog(this, "Seleccione una fila"); return; }
        int id = (int)clientesModel.getValueAt(r, 0);
        try { clienteDao.delete(id); refreshClientes(); } catch(Exception e) { showErr(e); }
    }

    private void refreshProductos() {
        try {
            List<Producto> list = productoDao.listAll();
            productosModel.setRowCount(0);
            for(Producto p: list) productosModel.addRow(new Object[]{p.getId(), p.getNombre(), "$"+String.format("%.2f", p.getPrecio()), p.getStock()});
        } catch(Exception e) { showErr(e); }
    }

    private void addProducto() {
        try {
            String nombre = cap.readString("Nombre: ", 100);
            String desc = cap.readString("Descripcion: ", 500);
            double precio = cap.readDouble("Precio: ");
            int stock = (int)cap.readFloat("Stock: ");
            Producto p = new Producto(nombre, desc, precio, stock);
            productoDao.insert(p);
            refreshProductos();
        } catch(Exception e) { showErr(e); }
    }

    private void editProducto(JTable table) {
        int r = table.getSelectedRow();
        if(r < 0) { JOptionPane.showMessageDialog(this, "Seleccione fila"); return; }
        int id = (int)productosModel.getValueAt(r, 0);
        try {
            Producto p = productoDao.findById(id);
            p.setNombre(cap.readString("Nuevo nombre: ", 100));
            p.setDescripcion(cap.readString("Nueva descripcion: ", 500));
            p.setPrecio(cap.readDouble("Nuevo precio: "));
            p.setStock((int)cap.readFloat("Nuevo stock: "));
            productoDao.update(p);
            refreshProductos();
        } catch(Exception e) { showErr(e); }
    }

    private void deleteProducto(JTable table) {
        int r = table.getSelectedRow();
        if(r < 0) { JOptionPane.showMessageDialog(this, "Seleccione fila"); return; }
        int id = (int)productosModel.getValueAt(r, 0);
        try { productoDao.delete(id); refreshProductos(); } catch(Exception e) { showErr(e); }
    }

    private void refreshMascotas() {
        try {
            List<Mamifero> list = mascotaDao.listAll();
            mascotasModel.setRowCount(0);
            for(Mamifero m: list) {
                if(m instanceof perro) {
                    perro p = (perro)m;
                    mascotasModel.addRow(new Object[]{p.getId(), p.getNombre(), p.getRaza(), p.getTipoAnimal(), p.getfNacimiento(), p.getPeso(), p.getLgEntrenamiento(), "-"});
                } else if(m instanceof gato) {
                    gato g = (gato)m;
                    mascotasModel.addRow(new Object[]{g.getId(), g.getNombre(), g.getRaza(), g.getTipoAnimal(), g.getfNacimiento(), g.getPeso(), "-", g.gethSalto()});
                }
            }
        } catch(Exception e) { showErr(e); }
    }

    private void addPerro() {
        try {
            perro p = cap.capturaPerro();
            mascotaDao.insert(p);
            refreshMascotas();
        } catch(Exception e) { showErr(e); }
    }

    private void addGato() {
        try {
            gato g = cap.capturaGato();
            mascotaDao.insert(g);
            refreshMascotas();
        } catch(Exception e) { showErr(e); }
    }

    private void deleteMascota(JTable table) {
        int r = table.getSelectedRow();
        if(r < 0) { JOptionPane.showMessageDialog(this, "Seleccione fila"); return; }
        int id = (int)mascotasModel.getValueAt(r, 0);
        try { mascotaDao.delete(id); refreshMascotas(); } catch(Exception e) { showErr(e); }
    }

    private void refreshPedidos() {
        try {
            List<Pedido> list = pedidoDao.listAll();
            pedidosModel.setRowCount(0);
            for(Pedido p: list) pedidosModel.addRow(new Object[]{p.getId(), p.getClienteId(), "$"+String.format("%.2f", p.getTotal()), p.getFechaCreacion()});
        } catch(Exception e) { showErr(e); }
    }

    private void createPedido() {
        try {
            List<Cliente> clientes = clienteDao.listAll();
            if (clientes.isEmpty()) { JOptionPane.showMessageDialog(this, "No hay clientes"); return; }
            String[] clienteNames = clientes.stream().map(c -> c.getId() + ": " + c.getNombre()).toArray(String[]::new);
            String sel = (String) JOptionPane.showInputDialog(this, "Seleccione cliente:", "Cliente", JOptionPane.PLAIN_MESSAGE, null, clienteNames, clienteNames[0]);
            if (sel == null) return;
            int clienteId = Integer.parseInt(sel.split(":")[0]);

            Pedido pedido = new Pedido(clienteId);
            pedido.setFechaCreacion(new Timestamp(System.currentTimeMillis()));

            while (true) {
                List<Producto> productos = productoDao.listAll();
                String[] prodNames = productos.stream().map(p -> p.getId() + ": " + p.getNombre() + " (P:$" + p.getPrecio() + ", S:" + p.getStock() + ")").toArray(String[]::new);
                String psel = (String) JOptionPane.showInputDialog(this, "Seleccione producto:", "Producto", JOptionPane.PLAIN_MESSAGE, null, prodNames, prodNames.length>0?prodNames[0]:null);
                if (psel == null) break;
                int prodId = Integer.parseInt(psel.split(":")[0]);
                Producto prod = productoDao.findById(prodId);
                String qStr = JOptionPane.showInputDialog(this, "Cantidad:", "1");
                if (qStr == null) break;
                int qty;
                try { qty = Integer.parseInt(qStr); if (qty <= 0) continue; } catch (NumberFormatException ex) { continue; }
                PedidoItem it = new PedidoItem(prodId, qty, prod.getPrecio());
                pedido.addItem(it);
            }
            if (pedido.getItems().isEmpty()) { JOptionPane.showMessageDialog(this, "Pedido vacio"); return; }

            SwingWorker<Integer,Void> worker = new SwingWorker<Integer,Void>() {
                @Override protected Integer doInBackground() throws Exception {
                    return pedidoDao.insert(pedido);
                }
                @Override protected void done() {
                    try { int id = get(); JOptionPane.showMessageDialog(AppGui.this, "Pedido creado: " + id); refreshPedidos(); refreshProductos(); } catch (Exception ex) { showErr(ex); }
                }
            };
            worker.execute();
        } catch (Exception e) { showErr(e); }
    }

    private void showErr(Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        e.printStackTrace();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppGui g = new AppGui();
            g.setVisible(true);
        });
    }
}
