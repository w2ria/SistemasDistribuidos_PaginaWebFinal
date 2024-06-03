/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author danie
 */
public class Usuario {
    private String id_usuario;
    private String contraseña;
    private String apellidos;
    private String nombres;
    private String direccion;
    private String DNI;
    private String telefono;
    private String movil;
    private String enLinea;
    private String estado;
    
    public Usuario(){
        
    }
    
    // Constructor que acepta solo usuario y contraseña
    public Usuario(String id_usuario, String contraseña) {
        this.id_usuario = id_usuario;
        this.contraseña = contraseña;
    }

    public Usuario(String id_usuario, String contraseña, String apellidos, String nombres, String direccion, String DNI, String telefono, String movil, String enLinea, String estado) {
        this.id_usuario = id_usuario;
        this.contraseña = contraseña;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.direccion = direccion;
        this.DNI = DNI;
        this.telefono = telefono;
        this.movil = movil;
        this.enLinea = enLinea;
        this.estado = estado;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String Id_usuario) {
        this.id_usuario = Id_usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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

    public String getEnLinea() {
        return enLinea;
    }

    public void setEnlinea(String enLinea) {
        this.enLinea = enLinea;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
    
    
}
