package Entidades;

public class DetallePedido {
    private String idDetallePedido;
    private String idPedido;
    private String idProd;
    private int cantidad;
    private double precio;
    private double totalDeta;

    // Getters y Setters

    public String getIdDetallePedido() {
        return idDetallePedido;
    }

    public void setIdDetallePedido(String idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdProd() {
        return idProd;
    }

    public void setIdProd(String idProd) {
        this.idProd = idProd;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getTotalDeta() {
        return totalDeta;
    }

    public void setTotalDeta(double totalDeta) {
        this.totalDeta = totalDeta;
    }
}
