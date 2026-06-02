package model;

public class PedidoItem {
    private int id;
    private int pedidoId;
    private int productoId;
    private int cantidad;
    private double precioUnitario;

    public PedidoItem() {}

    public PedidoItem(int productoId, int cantidad, double precioUnitario) {
        this.productoId = productoId; this.cantidad = cantidad; this.precioUnitario = precioUnitario;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPedidoId() { return pedidoId; }
    public void setPedidoId(int pedidoId) { this.pedidoId = pedidoId; }
    public int getProductoId() { return productoId; }
    public void setProductoId(int productoId) { this.productoId = productoId; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
    public double getSubtotal() { return precioUnitario * cantidad; }
}
