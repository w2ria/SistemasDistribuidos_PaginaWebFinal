/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import Entidades.Cliente;
import java.io.IOException;
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

public class ControlerCliente extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String opcion = request.getParameter("Op");
        ArrayList<Cliente> Lista = new ArrayList<>();
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
                    sql = "SELECT * FROM t_cliente";
                    ps = conn.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        Cliente client = new Cliente();
                        client.setId(rs.getString("Id_Cliente"));
                        client.setApellidos(rs.getString("Apellidos"));
                        client.setNombres(rs.getString("Nombres"));
                        client.setNumeroDocumento(rs.getString("NumeroDocumento"));
                        client.setTipoDocumento(rs.getString("TipoDocumento"));
                        client.setDireccion(rs.getString("Direccion"));
                        client.setTelefono(rs.getString("Telefono"));
                        client.setMovil(rs.getString("Movil"));
                        client.setEstado(rs.getString("Estado"));
                        client.setEnLinea(rs.getString("EnLinea"));
                        Lista.add(client);

                    }
//                    request.setAttribute("Nombre", nombre);
//                    request.setAttribute("Id_Usuario", id);
                    request.setAttribute("Lista", Lista);
                    request.getRequestDispatcher("MenuClientes.jsp").forward(request, response);
                } catch (SQLException ex) {
                    System.out.println("Error de SQL..." + ex.getMessage());
                } finally {
                    conBD.Discconet();
                }

                break;
            case "Eliminar":
                try {
                String Id = request.getParameter("Id");
                String sqlGetEstado = "SELECT estado FROM t_cliente WHERE Id_Cliente = ?";
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
                        sqlUpdateEstado = "UPDATE t_cliente SET estado = 'inactivo' WHERE Id_Cliente = ?";
                    } else if (estadoActual.equals("inactivo")) {
                        sqlUpdateEstado = "UPDATE t_cliente SET estado = 'activo' WHERE Id_Cliente = ?";
                    }

                    // Ejecutar la actualización de estado
                    ps = conn.prepareStatement(sqlUpdateEstado);
                    ps.setString(1, Id);
                    ps.executeUpdate();
                }

                response.sendRedirect("ControlerCliente?Op=Listar");

            } catch (SQLException ex) {
                System.out.println("Error de SQL..." + ex.getMessage());
            } finally {
                conBD.Discconet();
            }
            break;
            case "Actualizar":
                String Id = request.getParameter("cod");
                String Apellidos = request.getParameter("apellidos");
                String Nombres = request.getParameter("nombres");
                String Direccion = request.getParameter("direccion");
                String Numero = request.getParameter("numeroDocumento");
                String Tipo = request.getParameter("tipoDocumento");
                String Telefono = request.getParameter("telefono");
                String Movil = request.getParameter("movil");
                Cliente client = new Cliente();

                client.setId(Id);
                client.setApellidos(Apellidos);
                client.setNombres(Nombres);
                client.setNumeroDocumento(Numero);
                client.setTipoDocumento(Tipo);
                client.setDireccion(Direccion);
                client.setTelefono(Telefono);
                client.setMovil(Movil);

                sql = "update t_cliente set apellidos=?, nombres=?, Direccion=?, NumeroDocumento=?, TipoDocumento=?, direccion=?, telefono=?, movil=? where Id_Cliente=?";

                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, client.getApellidos());
                    ps.setString(2, client.getNombres());
                    ps.setString(3, client.getDireccion());
                    ps.setString(4, client.getNumeroDocumento());
                    ps.setString(5, client.getTipoDocumento());
                    ps.setString(6, client.getDireccion());
                    ps.setString(7, client.getTelefono());
                    ps.setString(8, client.getMovil());
                    ps.setString(9, client.getId());
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("Error actualizando tabla..." + ex.getMessage());
                } finally {
                    conBD.Discconet();
                }
                //response.sendRedirect("MenuClientes.jsp");   
                response.sendRedirect("ControlerCliente?Op=Listar");

                break;

            case "Guardar":
                String idCliente = GenerarnuevoId();
                String apellidos = request.getParameter("apellidos");
                String nombres = request.getParameter("nombres");
                String direccion = request.getParameter("direccion");
                String numeroDocumento = request.getParameter("numeroDocumento");
                String tipoDocumento = request.getParameter("tipoDocumento");
                String telefono = request.getParameter("telefono");
                String movil = request.getParameter("movil");
                String estado = "activo";

                sql = "INSERT INTO t_cliente (Id_Cliente, Apellidos, Nombres, Direccion, NumeroDocumento, TipoDocumento, Telefono, Movil, Estado, EnLinea) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, idCliente);
                    ps.setString(2, apellidos);
                    ps.setString(3, nombres);
                    ps.setString(4, direccion);
                    ps.setString(5, numeroDocumento);
                    ps.setString(6, tipoDocumento);
                    ps.setString(7, telefono);
                    ps.setString(8, movil);
                    ps.setString(9, estado);
                    ps.setInt(10, 0);

                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        //response.sendRedirect("MenuClientes.jsp");
                        response.sendRedirect("ControlerCliente?Op=Listar");

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

            case "BuscarCliente":
                String dniCliente = request.getParameter("dni");
                try {
                    sql = "SELECT Nombres, Apellidos FROM t_cliente WHERE NumeroDocumento = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, dniCliente);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        String nombreCliente = rs.getString("Nombres");
                        String apellidosCliente = rs.getString("Apellidos");
                        request.setAttribute("nombreCliente", nombreCliente);
                        request.setAttribute("apellidosCliente", apellidosCliente);
                    } else {
                        request.setAttribute("nombreCliente", "");
                        request.setAttribute("apellidosCliente", "");
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
            String sqlLastId = "SELECT Id_Cliente FROM t_cliente ORDER BY Id_Cliente DESC LIMIT 1";
            ps = conn.prepareStatement(sqlLastId);
            rs = ps.executeQuery();

            if (rs.next()) {
                String lastIdWithPrefix = rs.getString("Id_Cliente");
                int num = Integer.parseInt(lastIdWithPrefix.substring(1));
                String nextId = String.format("%05d", num + 1);
                return "C" + nextId;
            } else {
                return "C00001";
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
