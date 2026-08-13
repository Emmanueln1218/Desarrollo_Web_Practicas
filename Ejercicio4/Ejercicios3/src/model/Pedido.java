package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private int clienteId;
    private double total;
    private Timestamp fechaCreacion;
    private List<PedidoItem> items = new ArrayList<>();

    public Pedido() {}

    public Pedido(int clienteId) { this.clienteId = clienteId; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public Timestamp getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Timestamp fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public List<PedidoItem> getItems() { return items; }
    public void setItems(List<PedidoItem> items) { this.items = items; }
    public void addItem(PedidoItem it) { items.add(it); total += it.getSubtotal(); }
}
