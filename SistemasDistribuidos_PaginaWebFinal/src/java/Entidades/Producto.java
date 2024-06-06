package Entidades;

/**
 *
 * @author maria
 */
public class Producto {

    private String idProd;
    private String descripcion;
    private String imagen;
    private Double costo;
    private Double precio;
    private int cantidad;
    private String estado;

    public Producto() {
    }

    public Producto(String idProd, String descripcion, String imagen, Double costo, Double precio, int cantidad, String estado) {
        this.idProd = idProd;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.costo = costo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.estado = estado;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    

}
