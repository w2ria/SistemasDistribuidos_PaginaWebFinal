package Entidades;

public class Cliente {    
    private String Id;
    private String apellidos;
    private String nombres;
    private String direccion;
    private String DNI;
    private String telefono;
    private String movil;
    private String estado; 
    private String enLinea;

    public Cliente(){}

    public Cliente(String Id, String apellidos, String nombres, String direccion, String DNI, String telefono, String movil, String estado, String enLinea) {
        this.Id = Id;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.direccion = direccion;
        this.DNI = DNI;
        this.telefono = telefono;
        this.movil = movil;
        this.estado = estado;
        this.enLinea = enLinea;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEnLinea() {
        return enLinea;
    }

    public void setEnLinea(String enLinea) {
        this.enLinea = enLinea;
    }

   
}
