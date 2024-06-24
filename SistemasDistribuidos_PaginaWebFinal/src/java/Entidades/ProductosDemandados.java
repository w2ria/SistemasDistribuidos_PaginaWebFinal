package Entidades;

public class ProductosDemandados {
    private String idProducto;
    private String nombreProducto;
    private int cantidadComprada;
    private int totalCantidad;
    
    public ProductosDemandados() {
    }

    public ProductosDemandados(String idProducto, String nombreProducto, int cantidadComprada, int totalCantidad) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidadComprada = cantidadComprada;
        this.totalCantidad = totalCantidad;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

   

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidadComprada() {
        return cantidadComprada;
    }

    public void setCantidadComprada(int cantidadComprada) {
        this.cantidadComprada = cantidadComprada;
    }

    public int getTotalCantidad() {
        return totalCantidad;
    }

    public void setTotalCantidad(int totalCantidad) {
        this.totalCantidad = totalCantidad;
    }
    
    
}
