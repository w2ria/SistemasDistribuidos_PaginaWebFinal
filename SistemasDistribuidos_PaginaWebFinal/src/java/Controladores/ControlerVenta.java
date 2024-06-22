package Controladores;

import Entidades.Venta;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import Entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONArray;
import org.json.JSONObject;
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
import java.text.DecimalFormat;

/**
 *
 * @author user
 */
@WebServlet(name = "ControlerVenta", urlPatterns = {"/ControlerVenta"})
public class ControlerVenta extends HttpServlet {
    
    private String formatearDecimal(double valor) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(valor);
    }
    
    private double redondearDecimal(double valor) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.valueOf(df.format(valor));
    }
    
    private int detallePedidoCounter = 0;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        String opcion = request.getParameter("Op");
        ArrayList<Venta> listaVentas = new ArrayList<>();
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql;
        
        HttpSession session = request.getSession();
        listaVentas = (ArrayList<Venta>) session.getAttribute("listaVentas");
        if (listaVentas == null) {
            listaVentas = new ArrayList<>();
        }
        
        double subtotal = calcularSubtotal(listaVentas);
        double igv = calcularIGV(subtotal);
        double totalCompra = calcularTotal(subtotal, igv);

        request.setAttribute("subtotal", formatearDecimal(subtotal));
        request.setAttribute("igv", formatearDecimal(igv));
        request.setAttribute("totalCompra", formatearDecimal(totalCompra));

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

                            session.setAttribute("idCliente", idCliente);
                            session.setAttribute("dniCliente", dniCliente);
                            session.setAttribute("nombreCliente", nombreCliente);
                            session.setAttribute("apellidosCliente", apellidosCliente);
                            
                        } else {
                            request.setAttribute("mensajeClienteNoRegistrado", "Cliente no encontrado con DNI: " + dniCliente);
                            // Limpiar datos del cliente de la sesión en caso de no encontrarlo
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

                        sql = "SELECT Id_Prod, precio, cantidad FROM t_producto WHERE LOWER(Descripcion) = ?";
                        ps = conn.prepareStatement(sql);

                        ps.setString(1, nombreProducto.toLowerCase());
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            String codigoProducto = rs.getString("Id_Prod");
                            String precioProducto = rs.getString("precio");
                            int stockProducto = rs.getInt("cantidad");

                            Integer stockTemporal = (Integer) session.getAttribute("stock_" + codigoProducto);
                            if (stockTemporal != null) {
                                stockProducto = stockTemporal;
                            }

                            request.setAttribute("nombreProducto", nombreProducto);
                            request.setAttribute("codigoProducto", codigoProducto);
                            request.setAttribute("precioProducto", precioProducto);
                            request.setAttribute("stockProducto", stockProducto);
                        } else {
                            request.setAttribute("mensajeProductoNoRegistrado", "Producto no encontrado");
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
                        double nuevototalDeta = nuevaCantidad * venta.getPrecioProducto();
                        venta.setTotalDeta(nuevototalDeta); // Recalcular el importe
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
                    double totalDeta = precioProducto * cantidadProducto;
                    venta.setTotalDeta(totalDeta);
                    venta.setIndex(listaVentas.size() + 1);
                    listaVentas.add(venta);
                }

                session.setAttribute("listaVentas", listaVentas);

                // Guardar el stock original si no está guardado ya
                if (session.getAttribute("stockOriginal_" + codigoProducto) == null) {
                    int stockProducto = Integer.parseInt(request.getParameter("stockProducto"));
                    session.setAttribute("stockOriginal_" + codigoProducto, stockProducto);
                }

                // Actualizar el stock temporalmente en la sesión
                Integer stockActual = (Integer) session.getAttribute("stock_" + codigoProducto);
                if (stockActual == null) {
                    stockActual = Integer.parseInt(request.getParameter("stockProducto"));
                }
                stockActual -= cantidadProducto;
                session.setAttribute("stock_" + codigoProducto, stockActual);

                subtotal = calcularSubtotal(listaVentas);
                igv = calcularIGV(subtotal);
                totalCompra = calcularTotal(subtotal, igv);

                request.setAttribute("subtotal", formatearDecimal(subtotal));
                request.setAttribute("igv", formatearDecimal(igv));
                request.setAttribute("totalCompra", formatearDecimal(totalCompra));

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

                    if (listaVentas != null && indexEditar > 0 && indexEditar <= listaVentas.size()) {
                        Venta ventaEditar = listaVentas.get(indexEditar - 1);

                        try {
                            // Consulta SQL para obtener información del producto
                            String sqlEditar = "SELECT Id_Prod FROM t_producto WHERE Id_Prod = ?";
                            ps = conn.prepareStatement(sqlEditar);
                            ps.setString(1, ventaEditar.getCodigoProducto());
                            rs = ps.executeQuery();

                            if (rs.next()) {
                                // Obtener el stock temporalmente disminuido de la sesión
                                Integer stockTemporal = (Integer) sessionEditar.getAttribute("stock_" + ventaEditar.getCodigoProducto());
                                if (stockTemporal != null) {
                                    // Establecer atributos en el request para la página de edición
                                    request.setAttribute("indexEditar", indexEditar);
                                    request.setAttribute("codigoProductoEditar", ventaEditar.getCodigoProducto());
                                    request.setAttribute("nombreProductoEditar", ventaEditar.getNombreProducto());
                                    request.setAttribute("precioProductoEditar", ventaEditar.getPrecioProducto());
                                    request.setAttribute("cantidadProductoEditar", ventaEditar.getCantidadProducto());
                                    request.setAttribute("stockProductoEditar", stockTemporal); // Usar el stock temporal

                                    // Redirigir a la página de edición
                                    request.getRequestDispatcher("EditarProductoVenta.jsp").forward(request, response);
                                } else {
                                    // Manejar caso donde no se encuentra el stock temporal
                                    request.setAttribute("mensajeStockNoEncontrado", "Stock no encontrado temporalmente.");
                                    request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
                                }
                            } else {
                                // Manejar caso de no encontrar el producto en la base de datos
                                request.setAttribute("mensajeProductoNoEncontrado", "Producto no encontrado.");
                                request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
                            }

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            request.setAttribute("errorSQL", "Error al consultar producto: " + ex.getMessage());
                            request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
                        } finally {
                            try {
                                if (rs != null) {
                                    rs.close();
                                }
                                if (ps != null) {
                                    ps.close();
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }

                    } else {
                        // Manejar caso de índice fuera de rango o listaVentas es null
                        request.setAttribute("mensajeIndexInvalido", "Índice de producto no válido.");
                        request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
                    }
                } else {
                    // Manejar caso de indexParameter vacío o null
                    request.setAttribute("mensajeIndexVacio", "Índice de producto vacío.");
                    request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
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
                    int stockProductoOriginal = Integer.parseInt(request.getParameter("stockProducto"));

                    if (indexActualizar > 0 && indexActualizar <= listaVentas.size()) {
                        Venta ventaActualizar = listaVentas.get(indexActualizar - 1);

                        // Obtener stock temporal disminuido
                        Integer stockTemporal = (Integer) sessionActualizar.getAttribute("stock_" + ventaActualizar.getCodigoProducto());
                        if (stockTemporal == null) {
                            stockTemporal = stockProductoOriginal; // Usar stock original si no hay temporal
                        }

                        // Restaurar el stock original antes de actualizar
                        int cantidadAnterior = ventaActualizar.getCantidadProducto();
                        int cantidadDiferencia = cantidadAnterior - cantidadProductoActualizar;
                        stockTemporal += cantidadDiferencia;
                        sessionActualizar.setAttribute("stock_" + ventaActualizar.getCodigoProducto(), stockTemporal);

                        // Actualizar la cantidad y precio del producto
                        ventaActualizar.setPrecioProducto(precioProductoActualizar);
                        ventaActualizar.setCantidadProducto(cantidadProductoActualizar);

                        // Recalcular el importe para el producto actualizado
                        double totalDetaActualizar = precioProductoActualizar * cantidadProductoActualizar;
                        ventaActualizar.setTotalDeta(totalDetaActualizar);

                        // Actualizar la lista de ventas en la sesión
                        sessionActualizar.setAttribute("listaVentas", listaVentas);

                        // Recalcular el subtotal, IGV y total de la compra
                        double subtotalActualizar = calcularSubtotal(listaVentas);
                        double igvActualizar = calcularIGV(subtotalActualizar);
                        double totalCompraActualizar = calcularTotal(subtotalActualizar, igvActualizar);

                        // Agregar estos valores como atributos de la solicitud para la vista
                        request.setAttribute("subtotal", formatearDecimal(subtotalActualizar));
                        request.setAttribute("igv", formatearDecimal(igvActualizar));
                        request.setAttribute("totalCompra", formatearDecimal(totalCompraActualizar));

                        request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
                    } else {
                        // Manejo de error si el índice no es válido
                        request.setAttribute("errorMessage", "Índice de producto no válido.");
                        request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
                    }
                } else {
                    // Manejo de error si no se proporciona indexEditar
                    request.setAttribute("errorMessage", "Índice de producto no proporcionado.");
                    request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
                }
            break;




            case "EliminarProducto":
    int indexEliminar = Integer.parseInt(request.getParameter("index"));

    HttpSession sessionEliminar = request.getSession();
    listaVentas = (ArrayList<Venta>) sessionEliminar.getAttribute("listaVentas");

    if (listaVentas != null && indexEliminar > 0 && indexEliminar <= listaVentas.size()) {
        Venta ventaEliminar = listaVentas.remove(indexEliminar - 1); // Elimina el elemento en base al índice de usuario

        // Restaurar el stock al valor original guardado en la sesión
        String codigoProductoEliminar = ventaEliminar.getCodigoProducto();
        Integer stockOriginal = (Integer) sessionEliminar.getAttribute("stockOriginal_" + codigoProductoEliminar);
        if (stockOriginal != null) {
            sessionEliminar.setAttribute("stock_" + codigoProductoEliminar, stockOriginal);
        }

        // Actualizar índices después de eliminar
        for (int i = 0; i < listaVentas.size(); i++) {
            listaVentas.get(i).setIndex(i + 1); // Actualiza el índice para que comience desde 1
        }

        sessionEliminar.setAttribute("listaVentas", listaVentas);

        // Recalcular subtotal, IGV y total de la compra
        double subtotalActualizar = calcularSubtotal(listaVentas);
        double igvActualizar = calcularIGV(subtotalActualizar);
        double totalCompraActualizar = calcularTotal(subtotalActualizar, igvActualizar);

        // Agregar estos valores como atributos de la solicitud para la vista
        request.setAttribute("subtotal", formatearDecimal(subtotalActualizar));
        request.setAttribute("igv", formatearDecimal(igvActualizar));
        request.setAttribute("totalCompra", formatearDecimal(totalCompraActualizar));

        // Redirigir a la página de ventas con los nuevos cálculos
        request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
    } else {
        request.setAttribute("errorMessage", "Índice de producto no válido.");
        request.getRequestDispatcher("MenuVentas.jsp").forward(request, response);
    }
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
            
            
            
            case "BuscarProductosAutocompletado":
                String term = request.getParameter("term");
                JSONArray productos = buscarProductosPorNombre(term, conn);
                response.setContentType("application/json");
                response.getWriter().write(productos.toString());
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
            
             // Calcular subtotal y IGV
            double subtotal = calcularSubtotal(listaVentas);
            double igv = calcularIGV(subtotal);
            double totalCompra = calcularTotal(subtotal, igv);

            String sqlPedido = "INSERT INTO t_pedido (Id_Pedido, Id_Cliente, Id_Usuario, Fecha, SubTotal, TotalVenta) VALUES (?, ?, ?, NOW(), ?, ?)";
            psPedido = conn.prepareStatement(sqlPedido);
            psPedido.setString(1, idPedido);
            psPedido.setString(2, idCliente);
            psPedido.setString(3, idUsuario);
            psPedido.setDouble(4, subtotal); //corregir por SubTotal sin IGV
            psPedido.setDouble(5, totalCompra); //corregir por Total con IGV
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
                psDetalle.setDouble(6, venta.getTotalDeta());
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

    private double calcularSubtotal(ArrayList<Venta> listaVentas) {
        double subtotal = 0.0;
        for (Venta venta : listaVentas) {
            subtotal += venta.getTotalDeta();
        }
        return redondearDecimal(subtotal);
    }

    private double calcularIGV(double subtotal) {
        double igv = subtotal * 0.18; // 18% de IGV
        return redondearDecimal(igv);
    }

    private double calcularTotal(double subtotal, double igv) {
        double total = subtotal + igv;
        return redondearDecimal(total);
    }

     // Funcionalidad para mostrar las coincidencias de los productos 
    private JSONArray buscarProductosPorNombre(String term, Connection conn) {
        JSONArray productos = new JSONArray();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT Descripcion FROM t_producto WHERE Descripcion LIKE ? LIMIT 10";   //se limita a solo buscar 10 datos

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + term + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                String nombreProducto = rs.getString("Descripcion");
                productos.put(nombreProducto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return productos;
    }


}
