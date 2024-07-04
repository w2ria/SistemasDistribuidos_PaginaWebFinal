/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import Entidades.Producto;
import Entidades.ProductosDemandados;
import Entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author dayanna
 */
@WebServlet(name = "ControlerDemanda", urlPatterns = {"/ControlerDemanda"})
public class ControlerDemanda extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String opcion = request.getParameter("Op");
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql;
        HttpSession session = request.getSession();

        List<ProductosDemandados> productosCeroVentas = (List<ProductosDemandados>) session.getAttribute("productosCeroVentas");
        if (productosCeroVentas == null) {
            productosCeroVentas = new ArrayList<>();
        }

        switch (opcion) {
            case "VerPagina":
                String id = request.getParameter("Id_Usuario");
                String nom = request.getParameter("Nombre");
                request.setAttribute("Nombre", nom);
                request.setAttribute("Id_Usuario", id);

                request.getRequestDispatcher("MenuDemandas.jsp").forward(request, response);
                break;

            case "generarGraficos":
                generarGraficos(request, response, conn);
                break;
            case "obtenerProductosCeroVentas":
                obtenerProductosCeroVentas(request, response, conn);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Opción no válida");
                break;

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

    private JSONArray obtenerProductosMasComprados(String fechaInicio, String fechaFin, int cantidadProductos, Connection conn) {
        JSONArray productosMasComprados = new JSONArray();
        String sql = "SELECT p.Id_Prod, p.Descripcion AS NombreProducto, SUM(dp.cantidad) AS CantidadComprada "
                + "FROM t_pedido pe "
                + "INNER JOIN t_detalle_pedido dp ON pe.Id_Pedido = dp.Id_Pedido "
                + "INNER JOIN t_producto p ON dp.Id_Prod = p.Id_Prod "
                + "WHERE pe.Fecha BETWEEN ? AND ? "
                + "GROUP BY p.Id_Prod, p.Descripcion "
                + "ORDER BY CantidadComprada DESC "
                + "LIMIT ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            ps.setInt(3, cantidadProductos);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    JSONObject producto = new JSONObject();
                    producto.put("idProducto", rs.getString("Id_Prod"));
                    producto.put("nombreProducto", rs.getString("NombreProducto"));
                    producto.put("cantidadComprada", rs.getInt("CantidadComprada"));
                    productosMasComprados.put(producto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productosMasComprados;
    }

    private JSONArray obtenerProductosMenosComprados(String fechaInicio, String fechaFin, int cantidadProductos, Connection conn) {
        JSONArray productosMenosComprados = new JSONArray();
        String sql = "SELECT p.Id_Prod, p.Descripcion AS NombreProducto, SUM(dp.cantidad) AS CantidadComprada "
                + "FROM t_pedido pe "
                + "INNER JOIN t_detalle_pedido dp ON pe.Id_Pedido = dp.Id_Pedido "
                + "INNER JOIN t_producto p ON dp.Id_Prod = p.Id_Prod "
                + "WHERE pe.Fecha BETWEEN ? AND ? "
                + "GROUP BY p.Id_Prod, p.Descripcion "
                + "ORDER BY CantidadComprada ASC "
                + // Ordenar en orden ascendente
                "LIMIT ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            ps.setInt(3, cantidadProductos);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    JSONObject producto = new JSONObject();
                    producto.put("idProducto", rs.getString("Id_Prod"));
                    producto.put("nombreProducto", rs.getString("NombreProducto"));
                    producto.put("cantidadComprada", rs.getInt("CantidadComprada"));
                    productosMenosComprados.put(producto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productosMenosComprados;
    }

    private void generarGraficos(HttpServletRequest request, HttpServletResponse response, Connection conn) throws IOException {
        String fechaInicio = request.getParameter("fechaInicio");
        String fechaFin = request.getParameter("fechaFin");
        int cantidadProductos = Integer.parseInt(request.getParameter("cantidadProductos"));

        JSONArray productosMasComprados = obtenerProductosMasComprados(fechaInicio, fechaFin, cantidadProductos, conn);
        JSONArray productosMenosComprados = obtenerProductosMenosComprados(fechaInicio, fechaFin, cantidadProductos, conn);

        JSONObject resultados = new JSONObject();
        resultados.put("masVendidos", productosMasComprados);
        resultados.put("menosVendidos", productosMenosComprados);

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            out.print(resultados.toString());
        }

    }

    private void obtenerProductosCeroVentas(HttpServletRequest request, HttpServletResponse response, Connection conn) throws ServletException, IOException {
        List<ProductosDemandados> productosCeroVentas = new ArrayList<>();
        String sql = "SELECT t_producto.Id_Prod, t_producto.Descripcion, t_producto.cantidad "
                + "FROM t_producto LEFT JOIN t_detalle_pedido ON t_producto.Id_Prod = t_detalle_pedido.Id_Prod "
                + "WHERE t_detalle_pedido.Id_Prod IS NULL OR t_detalle_pedido.cantidad = 0";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProductosDemandados producto = new ProductosDemandados();
                producto.setIdProducto(rs.getString("Id_Prod"));
                producto.setNombreProducto(rs.getString("Descripcion"));
                producto.setTotalCantidad(rs.getInt("cantidad"));
                productosCeroVentas.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Guardar la lista en sesión
        HttpSession session = request.getSession();
        session.setAttribute("productosCeroVentas", productosCeroVentas);

        // Redirigir a la página de demandas
        String id = request.getParameter("Id_Usuario");
        String nom = request.getParameter("Nombre");
        request.setAttribute("Nombre", nom);
        request.setAttribute("Id_Usuario", id);
        request.getRequestDispatcher("MenuDemandas.jsp").forward(request, response);
    }

}
