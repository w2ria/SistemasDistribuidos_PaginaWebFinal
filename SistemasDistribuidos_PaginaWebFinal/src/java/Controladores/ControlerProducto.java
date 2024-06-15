/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import Entidades.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author danie
 */
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
                String opcion = request.getParameter("Op");
        ArrayList<Producto> Lista = new ArrayList<>();
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs;
        String sql;
        
        
        switch (opcion) {
            case "Listar":
                HttpSession session = request.getSession();
                String id = (String) session.getAttribute("IdUsuario");
                String nombre = (String) session.getAttribute("Nombre");
                System.out.println("EL NOMBRE que llega al servlet es ES:" +nombre);
                System.out.println("EL id traido es 500: "+id);
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
                request.setAttribute("Id_Usuario", id);
                request.setAttribute("Nombre", nombre);
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
                String image = request.getParameter("imagen");
                String costo = request.getParameter("costo");
                String precio = request.getParameter("precio");
                String cantidad = request.getParameter("cantidad");
                
                Producto producto = new Producto();

                producto.setIdProd(Id);
                producto.setDescripcion(descrip);
                producto.setImagen(image);
                producto.setCosto(Double.parseDouble(costo));
                producto.setPrecio(Double.parseDouble(precio));
                producto.setCantidad(Integer.parseInt(cantidad));
                

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
                String imag = request.getParameter("imagen");
                String cost = request.getParameter("costo");
                String preci = request.getParameter("precio");
                String cantida = request.getParameter("cantidad");
                String estado = "activo";
                
                

                sql = "INSERT INTO t_producto (Id_Prod, Descripcion, imagen, costo, precio, cantidad, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";

                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, idProd);
                    ps.setString(2, descri);
                    ps.setString(3, imag);
                    ps.setString(4, cost);
                    ps.setString(5, preci);
                    ps.setString(6, cantida);
                    ps.setString(7, estado);
                    

                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        //response.sendRedirect("MenuClientes.jsp");
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
