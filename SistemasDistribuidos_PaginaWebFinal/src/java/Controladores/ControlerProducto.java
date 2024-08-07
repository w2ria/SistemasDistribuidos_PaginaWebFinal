/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import Entidades.Producto;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Types;
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
public class ControlerProducto extends HttpServlet {

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
        ArrayList<Producto> Lista = new ArrayList<>();
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
//                System.out.println("EL id traido es 500: " + id);

                HttpSession session = request.getSession();
                String id = (String) session.getAttribute("Id_Usuario");
                String nombre = (String) session.getAttribute("Nombre");
                request.setAttribute("Id_Usuario", id);
                request.setAttribute("Nombre", nombre);
                try {
                    sql = "SELECT * FROM t_producto";
                    ps = conn.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        Producto producto = new Producto();
                        producto.setIdProd(rs.getString("Id_Prod"));
                        producto.setDescripcion(rs.getString("Descripcion"));
                        producto.setImagen(rs.getString("imagen"));
                        producto.setCosto(rs.getDouble("costo"));
                        producto.setPrecio(rs.getDouble("precio"));
                        producto.setCantidad(rs.getInt("cantidad"));
                        producto.setEstado(rs.getString("estado"));
                        Lista.add(producto);
                    }
//                    request.setAttribute("Id_Usuario", id);
//                    request.setAttribute("Nombre", nombre);
                    request.setAttribute("Lista", Lista);

                    request.getRequestDispatcher("MenuProductos.jsp").forward(request, response);
                } catch (SQLException ex) {
                    System.out.println("Error de SQL..." + ex.getMessage());
                } finally {
                    conBD.Discconet();
                }

                break;
            case "Eliminar":
                try {
                String Id = request.getParameter("Id");
                String sqlGetEstado = "SELECT estado FROM t_producto WHERE Id_Prod = ?";
                String sqlUpdateEstado = "";

                conn = conBD.Conexion();

                // Obtener el estado actual del cliente
                ps = conn.prepareStatement(sqlGetEstado);
                ps.setString(1, Id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String estadoActual = rs.getString("estado");

                    // Si el estado actual es 'activo', cambia a 'inactivo'; si es 'inactivo', cambia a 'activo'
                    if (estadoActual == null || estadoActual.isEmpty() || estadoActual.equals("activo")) {
                        sqlUpdateEstado = "UPDATE t_producto SET estado = 'inactivo' WHERE Id_Prod = ?";
                    } else if (estadoActual.equals("inactivo")) {
                        sqlUpdateEstado = "UPDATE t_producto SET estado = 'activo' WHERE Id_Prod = ?";
                    }

                    // Ejecutar la actualización de estado
                    ps = conn.prepareStatement(sqlUpdateEstado);
                    ps.setString(1, Id);
                    ps.executeUpdate();
                }

                response.sendRedirect("ControlerProducto?Op=Listar");

            } catch (SQLException ex) {
                System.out.println("Error de SQL..." + ex.getMessage());
            } finally {
                conBD.Discconet();
            }
            break;

            case "Actualizar":
                String Id = request.getParameter("codProd");
                String descrip = request.getParameter("descripcion");
                Part imagenPart = request.getPart("imagen");
                String imagenActual = request.getParameter("imagenActual");
                String costo = request.getParameter("costo");
                String precio = request.getParameter("precio");
                String cantidad = request.getParameter("cantidad");
                String imagenNombre = getFilename(imagenPart);

                Producto producto = new Producto();

                producto.setIdProd(Id);
                producto.setDescripcion(descrip);
                producto.setCosto(Double.parseDouble(costo));
                producto.setPrecio(Double.parseDouble(precio));
                producto.setCantidad(Integer.parseInt(cantidad));
                if (imagenNombre != null && !imagenNombre.isEmpty()) {
                    // Se ha cargado una nueva imagen
                    String nuevoNombreImagen = generarNuevoNombreImagen(obtenerUltimoNumeroImagen());
                    String extensionImagen = obtenerExtensionImagen(imagenPart);
                    String rutaImagen = guardarImagen(imagenPart, nuevoNombreImagen, extensionImagen);
                    producto.setImagen(nuevoNombreImagen + extensionImagen);
                } else {
                    // No se ha cargado una nueva imagen, mantener la imagen actual
                    producto.setImagen(imagenActual);
                }

                sql = "update t_producto set Descripcion=?, imagen=?, costo=?, precio=?, cantidad=? where Id_Prod=?";

                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, producto.getDescripcion());
                    ps.setString(2, producto.getImagen());
                    ps.setDouble(3, producto.getCosto());
                    ps.setDouble(4, producto.getPrecio());
                    ps.setInt(5, producto.getCantidad());
                    ps.setString(6, producto.getIdProd());
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("Error actualizando tabla..." + ex.getMessage());
                } finally {
                    conBD.Discconet();
                }
                //response.sendRedirect("MenuClientes.jsp");   
                response.sendRedirect("ControlerProducto?Op=Listar");

                break;

            case "Guardar":
                String idProd = GenerarnuevoId();
                String descri = request.getParameter("descripcion");
                imagenPart = request.getPart("imagen");
                imagenNombre = getFilename(imagenPart);
                String cost = request.getParameter("costo");
                String preci = request.getParameter("precio");
                String cantida = request.getParameter("cantidad");
                String estado = "activo";
                String imag = null;

                if (imagenNombre != null && !imagenNombre.isEmpty()) {
                    // Se ha cargado una nueva imagen
                    String nuevoNombreImagen = generarNuevoNombreImagen(obtenerUltimoNumeroImagen());
                    String extensionImagen = obtenerExtensionImagen(imagenPart);
                    String rutaImagen = guardarImagen(imagenPart, nuevoNombreImagen, extensionImagen);
                    imag = nuevoNombreImagen + extensionImagen;
                }

                sql = "INSERT INTO t_producto (Id_Prod, Descripcion, imagen, costo, precio, cantidad, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";

                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, idProd);
                    ps.setString(2, descri);
                    if (imagenNombre != null && !imagenNombre.isEmpty()) {
                        ps.setString(3, imag);
                    } else {
                        ps.setNull(3, Types.VARCHAR);
                    }
                    ps.setString(4, cost);
                    ps.setString(5, preci);
                    ps.setString(6, cantida);
                    ps.setString(7, estado);

                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        //response.sendRedirect("MenuClientes.jsp");
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ControlerProducto.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        response.sendRedirect("ControlerProducto?Op=Listar");

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

            case "BuscarProducto":
                String nombreProducto = request.getParameter("nombreProducto");
                try {
                    sql = "SELECT Id_Prod, precio, cantidad FROM t_producto WHERE Descripcion = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, nombreProducto);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        String codigoProducto = rs.getString("Id_Prod");
                        String precioProducto = rs.getString("precio");
                        String stockProducto = rs.getString("cantidad");
                        request.setAttribute("nombreProducto", nombreProducto);
                        request.setAttribute("codigoProducto", codigoProducto);
                        request.setAttribute("precioProducto", precioProducto);
                        request.setAttribute("stockProducto", stockProducto);
                    } else {
                        request.setAttribute("codigoProducto", "");
                        request.setAttribute("precioProducto", "");
                        request.setAttribute("stockProducto", "");
                    }

                    request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
                } catch (SQLException ex) {
                    System.out.println("Error de SQL..." + ex.getMessage());
                } finally {
                    conBD.Discconet();
                }
                break;

            default:

        }
    }

    private String GenerarnuevoId() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sqlLastId = "SELECT Id_Prod FROM t_producto ORDER BY Id_Prod DESC LIMIT 1";
            ps = conn.prepareStatement(sqlLastId);
            rs = ps.executeQuery();

            if (rs.next()) {
                String lastIdWithPrefix = rs.getString("Id_Prod");
                int num = Integer.parseInt(lastIdWithPrefix.substring(1));
                String nextId = String.format("%05d", num + 1);
                return "P" + nextId;
            } else {
                return "P00001";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Error";
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                conBD.Discconet();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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
        return "imagen" + (ultimoNumeroImagen + 1);
    }

    public int obtenerUltimoNumeroImagen() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT MAX(CAST(SUBSTRING(imagen, 7, LENGTH(imagen) - 10) AS UNSIGNED)) AS ultimoNumero FROM t_producto WHERE imagen LIKE 'imagen%'";
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
