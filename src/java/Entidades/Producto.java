package Entidades;

/**
 *
 * @author maria
 */
public class Producto {

    private String idProd;
    private String descripcion;
    private Double costo;
    private Double precio;
    private Double cantidad;

    public Producto() {
    }

    public Producto(String idProd, String descripcion, Double costo, Double precio, Double cantidad) {
        this.idProd = idProd;
        this.descripcion = descripcion;
        this.costo = costo;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getIdProd() {
        return idProd;
    }

    public void setIdProd(String idProd) {
        this.idProd = idProd;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

}
