/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import Entidades.Usuario;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Types;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author danie
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ControlerUsuario extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String opcion = request.getParameter("Op");
        ArrayList<Usuario> Lista = new ArrayList<>();
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs;
        String sql;
        switch (opcion) {
            case "Listar":
//                HttpSession session = request.getSession();
//                String id = (String) session.getAttribute("IdUsuario");
//                String nombre = (String) session.getAttribute("Nombre");
//                System.out.println("EL NOMBRE que llega al servlet es ES:" + nombre);
//                System.out.println("EL id traido es: " + id);
                HttpSession session = request.getSession();
                String id = (String) session.getAttribute("Id_Usuario");
                String nombre = (String) session.getAttribute("Nombre");
                request.setAttribute("Id_Usuario", id);
                request.setAttribute("Nombre", nombre);
                try {
                    sql = "SELECT * FROM t_usuario";
                    ps = conn.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        Usuario usuario = new Usuario();
                        usuario.setId_usuario(rs.getString("Id_Usuario"));
                        usuario.setContraseña(rs.getString("Passwd"));
                        usuario.setApellidos(rs.getString("Apellidos"));
                        usuario.setNombres(rs.getString("Nombres"));
                        usuario.setImagen(rs.getString("imagen"));
                        usuario.setDireccion(rs.getString("Direccion"));
                        usuario.setDNI(rs.getString("DNI"));
                        usuario.setTelefono(rs.getString("Telefono"));
                        usuario.setMovil(rs.getString("Movil"));
                        usuario.setEnLinea(rs.getString("EnLinea"));
                        usuario.setEstado(rs.getString("Estado"));

                        Lista.add(usuario);
                    }

                    request.setAttribute("Id_Usuario", id);
                    request.setAttribute("Nombre", nombre);

                    request.setAttribute("Lista", Lista);
                    request.getRequestDispatcher("MenuUsuarios.jsp").forward(request, response);
                } catch (SQLException ex) {
                    System.out.println("Error de SQL..." + ex.getMessage());
                } finally {
                    conBD.Discconet();
                }

                break;
            case "Eliminar":
                try {
                String Id = request.getParameter("Id");
                String sqlGetEstado = "SELECT Estado FROM t_usuario WHERE Id_Usuario = ?";
                String sqlUpdateEstado = "";

                conn = conBD.Conexion();

                // Obtener el estado actual del cliente
                ps = conn.prepareStatement(sqlGetEstado);
                ps.setString(1, Id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String estadoActual = rs.getString("Estado");

                    // Si el estado actual es 'activo', cambia a 'inactivo'; si es 'inactivo', cambia a 'activo'
                    if (estadoActual == null || estadoActual.isEmpty() || estadoActual.equals("activo")) {
                        sqlUpdateEstado = "UPDATE t_usuario SET Estado = 'inactivo' WHERE Id_Usuario = ?";
                    } else if (estadoActual.equals("inactivo")) {
                        sqlUpdateEstado = "UPDATE t_usuario SET Estado = 'activo' WHERE Id_Usuario = ?";
                    }

                    // Ejecutar la actualización de estado
                    ps = conn.prepareStatement(sqlUpdateEstado);
                    ps.setString(1, Id);
                    ps.executeUpdate();
                }

                response.sendRedirect("ControlerUsuario?Op=Listar");

            } catch (SQLException ex) {
                System.out.println("Error de SQL..." + ex.getMessage());
            } finally {
                conBD.Discconet();
            }
            break;
            case "Actualizar":
                String idUsuari = request.getParameter("cod");
                String contraseñ = request.getParameter("contra");

                String passwordEncriptado = EncriptadorAES.encriptarAES(contraseñ);

                String contrena = "";

                try {
                    PreparedStatement ps1 = null;
                    ResultSet rs1;
                    String sql1;
                    sql1 = "SELECT * FROM t_usuario where Id_Usuario='" + idUsuari + "'";
                    ps1 = conn.prepareStatement(sql1);
                    rs1 = ps1.executeQuery();
                    id = null;
                    while (rs1.next()) {
                        Usuario usuario = new Usuario();
                        usuario.setId_usuario(rs1.getString("Passwd"));
                        id = usuario.getId_usuario();
                    }

                    if (id.equals(contraseñ)) {
                        contrena = contraseñ;

                        System.out.println("Contra venida de jsp: " + contraseñ);
                        System.out.println("Contra venida de de BD: " + id);
                        System.out.println("La contra es iwal q la BD");
                        System.out.println("Contra encriptada: " + passwordEncriptado);

                        System.out.println("Contra que se enviará a BD: " + contrena);
                    } else {
                        contrena = passwordEncriptado;

                        System.out.println("Contra venida de jsp: " + contraseñ);
                        System.out.println("Contra venida de de BD: " + id);
                        System.out.println("La contra NOOOO es iwal q la BD");
                        System.out.println("Contra encriptada: " + passwordEncriptado);

                        System.out.println("Contra que se enviará a BD: " + contrena);
                    }
                } catch (SQLException ex) {
                    System.out.println("Error de SQL..." + ex.getMessage());
                }

                String apellido = request.getParameter("apellidos");
                nombre = request.getParameter("nombres");
                Part imagenPart = request.getPart("imagen");
                String imagenActual = request.getParameter("imagenActual");
                String direccio = request.getParameter("direccion");
                String dn = request.getParameter("dni");
                String telefon = request.getParameter("telefono");
                String movi = request.getParameter("movil");
                String imagenNombre = getFilename(imagenPart);
                Usuario usuario = new Usuario();

                usuario.setId_usuario(idUsuari);
                usuario.setContraseña(contrena);
                usuario.setApellidos(apellido);
                usuario.setNombres(nombre);
                usuario.setDireccion(direccio);
                usuario.setDNI(dn);
                usuario.setTelefono(telefon);
                usuario.setMovil(movi);

                if (imagenNombre != null && !imagenNombre.isEmpty()) {
                    // Se ha cargado una nueva imagen
                    String nuevoNombreImagen = generarNuevoNombreImagen(obtenerUltimoNumeroImagen());
                    String extensionImagen = obtenerExtensionImagen(imagenPart);
                    String rutaImagen = guardarImagen(imagenPart, nuevoNombreImagen, extensionImagen);
                    usuario.setImagen(nuevoNombreImagen + extensionImagen);
                } else {
                    // No se ha cargado una nueva imagen, mantener la imagen actual
                    usuario.setImagen(imagenActual);
                }

                sql = "update t_usuario set Passwd=?, apellidos=?, nombres=?, imagen=?, direccion=?, DNI=?, telefono=?, movil=? where Id_Usuario=?";

                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, usuario.getContraseña());
                    ps.setString(2, usuario.getApellidos());
                    ps.setString(3, usuario.getNombres());
                    ps.setString(4, usuario.getImagen());
                    ps.setString(5, usuario.getDireccion());
                    ps.setString(6, usuario.getDNI());
                    ps.setString(7, usuario.getTelefono());
                    ps.setString(8, usuario.getMovil());
                    ps.setString(9, usuario.getId_usuario());
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("Error actualizando tabla..." + ex.getMessage());
                } finally {
                    conBD.Discconet();
                }
                //response.sendRedirect("MenuClientes.jsp");   
                response.sendRedirect("ControlerUsuario?Op=Listar");

                break;

            case "Guardar":
                String idUsuario = request.getParameter("cod");
                String contraseña = request.getParameter("contra");
                String passwordEncriptad = EncriptadorAES.encriptarAES(contraseña);
                String apellidos = request.getParameter("apellidos");
                String nombres = request.getParameter("nombres");
                String img = request.getParameter("imagen");
                String direccion = request.getParameter("direccion");
                String dni = request.getParameter("dni");
                String telefono = request.getParameter("telefono");
                String movil = request.getParameter("movil");
                String estado = "activo";
                String imag = null;

                if (img != null) {
                    Part imagenPartGuardar = request.getPart("imagen");
                    String imagenNombreGuardar = getFilename(imagenPartGuardar);
                    String extensionImagen = obtenerExtensionImagen(imagenPartGuardar);
                    String nuevoNombreImagen = generarNuevoNombreImagen(obtenerUltimoNumeroImagen());
                    String rutaImagen = guardarImagen(imagenPartGuardar, nuevoNombreImagen, extensionImagen);
                    nuevoNombreImagen = generarNuevoNombreImagen(obtenerUltimoNumeroImagen());
                    extensionImagen = obtenerExtensionImagen(imagenPartGuardar);
                    rutaImagen = guardarImagen(imagenPartGuardar, nuevoNombreImagen, extensionImagen);
                    imag = nuevoNombreImagen + extensionImagen;
                }

                sql = "INSERT INTO t_usuario (Id_Usuario, Passwd, Apellidos, Nombres,imagen, Direccion, DNI, Telefono, Movil, Estado, EnLinea) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";

                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, idUsuario);
                    ps.setString(2, passwordEncriptad);
                    ps.setString(3, apellidos);
                    ps.setString(4, nombres);
                    if (imag != null) {
                        ps.setString(5, imag);
                    } else {
                        ps.setNull(5, Types.VARCHAR);
                    }
                    ps.setString(6, direccion);
                    ps.setString(7, dni);
                    ps.setString(8, telefono);
                    ps.setString(9, movil);
                    ps.setString(10, estado);
                    ps.setInt(11, 0);

                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        //response.sendRedirect("MenuClientes.jsp");
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ControlerUsuario.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        response.sendRedirect("ControlerUsuario?Op=Listar");

                    } else {
                        response.sendRedirect("Error.jsp");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    response.sendRedirect("Error.jsp");
                } finally {
                    try {
                        if (ps != null) {
                            ps.close();
                        }
                        conBD.Discconet();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                break;

            default:

        }

    }

    private String getFilename(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String cd : contentDisposition.split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private String obtenerExtensionImagen(Part part) {
        String fileName = getFilename(part);
        if (fileName != null) {
            return fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
        }
        return null;
    }

    private String guardarImagen(Part part, String nuevoNombreImagen, String extension) throws IOException {
        String directory = getServletContext().getRealPath("/resources/img/");
        Files.createDirectories(Paths.get(directory));
        Path filePath = Paths.get(directory, nuevoNombreImagen + extension);
        try (InputStream input = part.getInputStream()) {
            Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        return filePath.toString();
    }

    private String generarNuevoNombreImagen(int ultimoNumeroImagen) {
        return "usuario" + (ultimoNumeroImagen + 1);
    }

    public int obtenerUltimoNumeroImagen() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT MAX(CAST(SUBSTRING(imagen, 7, LENGTH(imagen) - 10) AS UNSIGNED)) AS ultimoNumero FROM t_usuario WHERE imagen LIKE 'usuario%'";
        int ultimoNumero = 0;

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                ultimoNumero = rs.getInt("ultimoNumero");
            }
        } catch (Exception e) {
            System.out.println("ERROR en obtenerUltimoNumeroImagen: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println("ERROR cerrando recursos: " + e.getMessage());
            }
        }
        return ultimoNumero;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
