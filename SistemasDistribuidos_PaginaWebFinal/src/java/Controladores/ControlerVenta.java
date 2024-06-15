
package Controladores;

import Entidades.Venta;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import Entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author user
 */
@WebServlet(name = "ControlerVenta", urlPatterns = {"/ControlerVenta"})
public class ControlerVenta extends HttpServlet {

     private int detallePedidoCounter = 0;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        String opcion = request.getParameter("Op");
        ArrayList<Venta> listaVentas = new ArrayList<>();
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs;
        String sql;

        switch (opcion) {
                case "VerPagina":
                        String id = request.getParameter("id");
                        String nom = request.getParameter("nom");
                        request.setAttribute("Nombre", nom);
                        request.setAttribute("Id_Usuario", id);
                        
                        request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
                break;
                case "BuscarCliente":
                    String dniCliente = request.getParameter("dni");
                    try {
                        sql = "SELECT Id_Cliente, Nombres, Apellidos FROM t_cliente WHERE DNI = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, dniCliente);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            String idCliente = rs.getString("Id_Cliente");
                            String nombreCliente = rs.getString("Nombres");
                            String apellidosCliente = rs.getString("Apellidos");

                            HttpSession session = request.getSession();
                            session.setAttribute("idCliente", idCliente);
                            session.setAttribute("dniCliente", dniCliente);
                            session.setAttribute("nombreCliente", nombreCliente);
                            session.setAttribute("apellidosCliente", apellidosCliente);
                            
                        } else {
                            request.setAttribute("mensajeClienteNoRegistrado", "Cliente no encontrado con DNI: " + dniCliente);
                            // Limpiar datos del cliente de la sesión en caso de no encontrarlo
                            HttpSession session = request.getSession();
                            session.removeAttribute("idCliente");
                            session.removeAttribute("dniCliente");
                            session.removeAttribute("nombreCliente");
                            session.removeAttribute("apellidosCliente");
                            
                        }
                        
                        request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
                    } catch (SQLException ex) {
                        System.out.println("Error de SQL..." + ex.getMessage());
                    } finally {
                        conBD.Discconet();
                    }
                break;


                
                case "BuscarProducto":
                String nombreProducto = request.getParameter("nombreProducto");

                try {
                    
                    conn.createStatement().execute("SET CHARACTER SET utf8");

                    // el LOWER() es para las mayusculas y minuscls
                    sql = "SELECT Id_Prod, precio, cantidad FROM t_producto WHERE LOWER(Descripcion) = ?";
                    ps = conn.prepareStatement(sql);

                  
                    ps.setString(1, nombreProducto.toLowerCase());
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
                        request.setAttribute("mensajeProductoNoRegistrado", "Producto no encontrado" );
                        
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
   
            case "AgregarProducto":
                String codigoProducto = request.getParameter("codigoProducto");
                String nombreProductoAgregar = request.getParameter("nombreProducto");
                double precioProducto = Double.parseDouble(request.getParameter("precioProducto"));
                int cantidadProducto = Integer.parseInt(request.getParameter("cantidadProducto"));

                HttpSession session = request.getSession();
                listaVentas = (ArrayList<Venta>) session.getAttribute("listaVentas");
                if (listaVentas == null) {
                    listaVentas = new ArrayList<>();
                }

                boolean productoExiste = false;

                // Buscar si el producto ya existe en la lista
                for (Venta venta : listaVentas) {
                    if (venta.getCodigoProducto().equals(codigoProducto)) {
                        // Producto ya existe, incrementar cantidad
                        int nuevaCantidad = venta.getCantidadProducto() + cantidadProducto;
                        venta.setCantidadProducto(nuevaCantidad);
                        double nuevoSubtotal = nuevaCantidad * venta.getPrecioProducto();
                        venta.setSubtotal(nuevoSubtotal);
                        productoExiste = true;
                        break;
                    }
                }

                // Si el producto no existe en la lista, agregarlo como una nueva entrada
                if (!productoExiste) {
                    Venta venta = new Venta();
                    venta.setCodigoProducto(codigoProducto);
                    venta.setNombreProducto(nombreProductoAgregar);
                    venta.setPrecioProducto(precioProducto);
                    venta.setCantidadProducto(cantidadProducto);
                    double subtotal = precioProducto * cantidadProducto;
                    venta.setSubtotal(subtotal);
                    venta.setIndex(listaVentas.size() + 1); // Set the index for display
                    listaVentas.add(venta);
                }

                session.setAttribute("listaVentas", listaVentas);

                double totalCompra = calcularTotal(listaVentas);
                request.setAttribute("totalCompra", totalCompra);

                request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
            break;


            case "Listar":
                session = request.getSession();
                listaVentas = (ArrayList<Venta>) session.getAttribute("listaVentas");
                request.setAttribute("listaVentas", listaVentas);
                
                
                
                request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
                break;
            
                
            case "EditarProducto":
            String indexParameter = request.getParameter("index");
            if (indexParameter != null && !indexParameter.isEmpty()) {
                int indexEditar = Integer.parseInt(indexParameter);

                
                HttpSession sessionEditar = request.getSession();
                listaVentas = (ArrayList<Venta>) sessionEditar.getAttribute("listaVentas");

                
                if (indexEditar > 0 && indexEditar <= listaVentas.size()) {
                    Venta ventaEditar = listaVentas.get(indexEditar - 1); // El índice -1 porque el índice comienza en 0 en Java
                    request.setAttribute("indexEditar", indexEditar); 

                    
                    request.setAttribute("codigoProductoEditar", ventaEditar.getCodigoProducto());
                    request.setAttribute("nombreProductoEditar", ventaEditar.getNombreProducto());
                    request.setAttribute("precioProductoEditar", ventaEditar.getPrecioProducto());
                    request.setAttribute("cantidadProductoEditar", ventaEditar.getCantidadProducto());
                    
                    request.setAttribute("stockProductoEditar", ventaEditar.getStockProducto()); 

                    request.getRequestDispatcher("EditarProductoVenta.jsp").forward(request, response); 
                } else {
                   
                }
            } else {
                
            }
            break;

        case "ActualizarProducto":
    String indexActualizarParameter = request.getParameter("indexEditar");
    if (indexActualizarParameter != null && !indexActualizarParameter.isEmpty()) {
        int indexActualizar = Integer.parseInt(indexActualizarParameter);
        HttpSession sessionActualizar = request.getSession();
        listaVentas = (ArrayList<Venta>) sessionActualizar.getAttribute("listaVentas");

        double precioProductoActualizar = Double.parseDouble(request.getParameter("precioProducto"));
        int cantidadProductoActualizar = Integer.parseInt(request.getParameter("cantidadProducto"));
        int stockProductoActualizar = Integer.parseInt(request.getParameter("stockProducto")); // Aquí obtienes el stock actualizado

        if (indexActualizar > 0 && indexActualizar <= listaVentas.size()) {
            Venta ventaActualizar = listaVentas.get(indexActualizar - 1);
            ventaActualizar.setPrecioProducto(precioProductoActualizar);
            ventaActualizar.setCantidadProducto(cantidadProductoActualizar);
            // No necesitas setear el stockProducto en el objeto Venta, solo usarlo para actualizar en la base de datos

            // Actualizar el stock del producto en la base de datos
            // (Implementación de actualizarStockProductos() debería usar stockProductoActualizar)
            actualizarStockProductos(listaVentas, conn);

            double subtotalActualizar = precioProductoActualizar * cantidadProductoActualizar;
            ventaActualizar.setSubtotal(subtotalActualizar);

            sessionActualizar.setAttribute("listaVentas", listaVentas);

            double totalCompraActualizar = calcularTotal(listaVentas);
            request.setAttribute("totalCompra", totalCompraActualizar);

            request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
        } else {
            // Manejo de error si el índice no es válido
        }
    } else {
        // Manejo de error si no se proporciona indexEditar
    }
    break;


            case "EliminarProducto":
                int indexEliminar = Integer.parseInt(request.getParameter("index"));

               
                HttpSession sessionEliminar = request.getSession();
                listaVentas = (ArrayList<Venta>) sessionEliminar.getAttribute("listaVentas");

                
                // Suponiendo que indexEliminar es el índice que obtienes desde la solicitud para eliminar
                listaVentas.remove(indexEliminar - 1); // Elimina el elemento en base al índice de usuario

                // Actualizar índices después de eliminar
                for (int i = 0; i < listaVentas.size(); i++) {
                    listaVentas.get(i).setIndex(i + 1); // Actualiza el índice para que comience desde 1
                }
 
                sessionEliminar.setAttribute("listaVentas", listaVentas);
   
                double totalCompraEliminar = calcularTotal(listaVentas);
                request.setAttribute("totalCompra", totalCompraEliminar);

                request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
                break;
                
            case "GenerarVenta":
                detallePedidoCounter = 0;
                generarVenta(request, response);
            break;
                
            case "CancelarVenta":
                cancelarVenta(request, response);
            break;
            
            case "LimpiarCliente":
                 request.getSession().removeAttribute("dniCliente");
                request.getSession().removeAttribute("nombreCliente");
                request.getSession().removeAttribute("apellidosCliente");
                response.getWriter().write("Datos del cliente limpiados");
                
            break;
            

                default:
                    break;
            }
        }
    
    private void generarVenta(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<Venta> listaVentas = (ArrayList<Venta>) session.getAttribute("listaVentas");
   
        
    // Validar si se ha buscado previamente al cliente
    String idCliente = (String) session.getAttribute("idCliente");
    String dniCliente = (String) session.getAttribute("dniCliente");
    String nombreCliente = (String) session.getAttribute("nombreCliente");
    String apellidosCliente = (String) session.getAttribute("apellidosCliente");

    if (idCliente == null || dniCliente == null || nombreCliente == null || apellidosCliente == null) {
        // Mostrar un mensaje de error indicando que es necesario seleccionar un cliente
        request.setAttribute("errorMessage", "Es necesario seleccionar un cliente.");
        request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
        return;
        }
        
       if (listaVentas == null || listaVentas.isEmpty()) {
            request.setAttribute("mensajeSinProductos", "No hay productos en la venta.");
            request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
            return;
        }


        Connection conn = null;
        PreparedStatement psPedido = null;
        PreparedStatement psDetalle = null;

        try {
            Conexion.Conexion conBD = new Conexion.Conexion();
            conn = conBD.Conexion();
            conn.setAutoCommit(false); // Deshabilitar el modo de autocommit

            String idPedido = generarNuevoIdPedido();
            idCliente = (String) session.getAttribute("idCliente"); // Obtener ID del cliente desde la sesión
            String idUsuario = request.getParameter("idUsuario");
            double totalCompra = calcularTotal(listaVentas);

            String sqlPedido = "INSERT INTO t_pedido (Id_Pedido, Id_Cliente, Id_Usuario, Fecha, SubTotal, TotalVenta) VALUES (?, ?, ?, NOW(), ?, ?)";
            psPedido = conn.prepareStatement(sqlPedido);
            psPedido.setString(1, idPedido);
            psPedido.setString(2, idCliente);
            psPedido.setString(3, idUsuario);
            psPedido.setDouble(4, totalCompra);
            psPedido.setDouble(5, totalCompra);
            psPedido.executeUpdate();

            String sqlDetalle = "INSERT INTO t_detalle_pedido (Id_DetallePedido, Id_Pedido, Id_Prod, cantidad, precio, TotalDeta) VALUES (?, ?, ?, ?, ?, ?)";
            psDetalle = conn.prepareStatement(sqlDetalle);

            for (Venta venta : listaVentas) {
                String idDetallePedido = generarIdDetallePedido();
                psDetalle.setString(1, idDetallePedido);
                psDetalle.setString(2, idPedido);
                psDetalle.setString(3, venta.getCodigoProducto());
                psDetalle.setInt(4, venta.getCantidadProducto());
                psDetalle.setDouble(5, venta.getPrecioProducto());
                psDetalle.setDouble(6, venta.getSubtotal());
                psDetalle.addBatch();
            }

            psDetalle.executeBatch();

             // Actualizar el stock de productos vendidos
            actualizarStockProductos(listaVentas, conn);


            conn.commit();
            
            request.getSession().setAttribute("idPedido", idPedido);

            // Limpiar datos del cliente de la sesión
            session.removeAttribute("idCliente");
            session.removeAttribute("dniCliente");
            session.removeAttribute("nombreCliente");
            session.removeAttribute("apellidosCliente");

            session.removeAttribute("listaVentas"); // Limpiar la lista de ventas
            response.sendRedirect("MenuVentas.jsp?ventaGuardada=true"); // Redirigir con el parámetro

        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }

            ex.printStackTrace();
            response.getWriter().println("Error en la aplicación: " + ex.getMessage());

        } finally {
            try {
                if (psPedido != null) {
                    psPedido.close();
                }
                if (psDetalle != null) {
                    psDetalle.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    } 


    private String generarNuevoIdPedido() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conexion = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sqlLastId = "SELECT Id_Pedido FROM t_pedido ORDER BY Id_Pedido DESC LIMIT 1";
            ps = conexion.prepareStatement(sqlLastId);
            rs = ps.executeQuery();
            if (rs.next()) {
                String lastIdWithPrefix = rs.getString("Id_Pedido");
                int num = Integer.parseInt(lastIdWithPrefix.substring(1));
                String nextId = String.format("%05d", num + 1);
                return "V" + nextId;
            } else {
                return "V00001";
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


    private String generarIdDetallePedido() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conexion = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sqlLastId = "SELECT Id_DetallePedido FROM t_detalle_pedido ORDER BY Id_DetallePedido DESC LIMIT 1";
            ps = conexion.prepareStatement(sqlLastId);
            rs = ps.executeQuery();
            if (rs.next()) {
                String lastIdWithPrefix = rs.getString("Id_DetallePedido");
                int num = Integer.parseInt(lastIdWithPrefix.substring(1)) + ++detallePedidoCounter;
                String nextId = String.format("%05d", num);
                return "D" + nextId;
            } else {
                return "D00001";
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
    
    private void actualizarStockProductos(ArrayList<Venta> listaVentas, Connection conn) throws SQLException {
        PreparedStatement psStock = null;
        String sqlStock = "UPDATE t_producto SET cantidad = cantidad - ? WHERE Id_Prod = ?";

        try {
            psStock = conn.prepareStatement(sqlStock);

            for (Venta venta : listaVentas) {
                psStock.setInt(1, venta.getCantidadProducto());
                psStock.setString(2, venta.getCodigoProducto());
                psStock.executeUpdate();
            }
        } finally {
            if (psStock != null) {
                psStock.close();
            }
        }
    }
    
    private void cancelarVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        // Limpiar los datos de la sesión relacionados con la venta
        session.removeAttribute("listaVentas");
        session.removeAttribute("idCliente");
        session.removeAttribute("dniCliente");
        session.removeAttribute("nombreCliente");
        session.removeAttribute("apellidosCliente");
        session.removeAttribute("codigoProducto");
        session.removeAttribute("nombreProducto");
        session.removeAttribute("precioProducto");
        session.removeAttribute("stockProducto");

        // Redirigir a la página de ventas o la página inicial
        response.sendRedirect("MenuVentas.jsp");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         try {
             processRequest(request, response);
         } catch (SQLException ex) {
             Logger.getLogger(ControlerVenta.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         try {
             processRequest(request, response);
         } catch (SQLException ex) {
             Logger.getLogger(ControlerVenta.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private double calcularTotal(ArrayList<Venta> listaVentas) {
        double total = 0.0;
        for (Venta venta : listaVentas) {
            total += venta.getSubtotal();
        }
        return total;
    }
}
