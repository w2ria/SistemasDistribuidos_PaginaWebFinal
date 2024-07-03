package Controladores;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author maria
 */
public class ControlerDashboard extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String opcion = request.getParameter("Op");

        switch (opcion) {
            case "Listar":
                HttpSession session = request.getSession();
                String id = (String) session.getAttribute("IdUsuario");
                String nombre = (String) session.getAttribute("Nombre");
                request.setAttribute("Id_Usuario", id);
                request.setAttribute("Nombre", nombre);
                request.setAttribute("jsonDatos", obtenerDatosComoJSON());
                System.out.println("jsonDatos: " + obtenerDatosComoJSON());

                Map<String, Object> ventasData = obtenerVentas();
                //System.out.println("ventasData: " + ventasData);
                Gson gson = new Gson();
                String ventasDataJson = gson.toJson(ventasData);
                //System.out.println("ventasDataJson: " + ventasDataJson);
                request.setAttribute("ventasData", ventasDataJson);

                Map<String, Object> gananciasData = obtenerGanancias();
                //System.out.println("gananciasData: " + gananciasData);
                gson = new Gson();
                String gananciasDataJson = gson.toJson(gananciasData);
                //System.out.println("gananciasDataJson: " + gananciasDataJson);
                request.setAttribute("gananciasData", gananciasDataJson);

                Map<String, Object> pedidosData = obtenerPedidos();
                //System.out.println("pedidosData: " + pedidosData);
                gson = new Gson();
                String pedidosDataJson = gson.toJson(pedidosData);
                //System.out.println("pedidosDataJson: " + pedidosDataJson);
                request.setAttribute("pedidosData", pedidosDataJson);

                Map<String, Object> empleadosData = obtenerEmpleados();
                //System.out.println("empleadosData: " + empleadosData);
                gson = new Gson();
                String empleadosDataJson = gson.toJson(empleadosData);
                //System.out.println("empleadosDataJson: " + empleadosDataJson);
                request.setAttribute("empleadosData", empleadosDataJson);

                request.getRequestDispatcher("MenuDashboard.jsp").forward(request, response);
                break;

            default:
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

    private Map<String, Object> obtenerVentas() {
        Map<String, Object> data = new HashMap<>();

        Map<String, Object> data7 = new HashMap<>();
        double[] sumaTotalVentaSemanal = obtenerSumaTotalVentaSemanal();
        data7.put("data", sumaTotalVentaSemanal);
        data7.put("label", new String[]{"LUN", "MAR", "MIÉR", "JUE", "VIE", "SÁB", "DOM"});
        data.put("7", data7);

        Map<String, Object> data30 = new HashMap<>();
        Map<String, Object> resultadoMensual = obtenerSumaTotalVentaMensual();
        String[] fechasVentaMensual = (String[]) resultadoMensual.get("fechas");
        double[] sumaTotalVentaMensual = (double[]) resultadoMensual.get("ventas");
        data30.put("data", sumaTotalVentaMensual);
        data30.put("label", fechasVentaMensual);
        data.put("30", data30);

        Map<String, Object> data365 = new HashMap<>();
        double[] sumaTotalVentaAnual = obtenerSumaTotalVentaAnual();
        data365.put("data", sumaTotalVentaAnual);
        data365.put("label", new String[]{"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"});
        data.put("365", data365);

        return data;
    }

    private Map<String, Object> obtenerGanancias() {
        Map<String, Object> data = new HashMap<>();

        Map<String, Object> data7 = new HashMap<>();
        double[] gananciasSemanales = obtenerGananciasSemanales();
        data7.put("data", gananciasSemanales);
        data7.put("label", new String[]{"LUN", "MAR", "MIÉR", "JUE", "VIE", "SÁB", "DOM"});
        data.put("7", data7);

        Map<String, Object> data30 = new HashMap<>();
        Map<String, Object> resultadoMensual = obtenerGananciasMensuales();
        double[] gananciasMensuales = (double[]) resultadoMensual.get("ganancias");
        String[] fechasVentaMensual = (String[]) resultadoMensual.get("fechas");
        data30.put("data", gananciasMensuales);
        data30.put("label", fechasVentaMensual);
        data.put("30", data30);

        Map<String, Object> data365 = new HashMap<>();
        double[] gananciasAnuales = obtenerGananciasAnuales();
        data365.put("data", gananciasAnuales);
        data365.put("label", new String[]{"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"});
        data.put("365", data365);

        return data;
    }

    private Map<String, Object> obtenerPedidos() {
        Map<String, Object> data = new HashMap<>();
        data.put("data", new int[]{100, 90, 70});
        data.put("label", new String[]{"Pendiente", "En proceso", "Completado"});
        data.put("backgroundColor", new String[]{"rgb(255, 99, 132)", "rgb(54, 162, 235)", "rgb(255, 205, 86)"});

        return data;
    }

    private Map<String, Object> obtenerEmpleados() {
        Map<String, Object> data = new HashMap<>();

        Map<String, Object> data7 = new HashMap<>();
        data7.put("labels", new String[]{"LUN", "MAR", "MIÉR", "JUE", "VIE", "SÁB", "DOM"});
        Map<String, double[]> ventasSemanalesPorEmpleado = obtenerSumaTotalVentaSemanalPorEmpleado();
        List<Map<String, Object>> datasets = new ArrayList<>();
        for (Map.Entry<String, double[]> entry : ventasSemanalesPorEmpleado.entrySet()) {
            String empleado = entry.getKey();
            double[] ventas = entry.getValue();

            Map<String, Object> dataset = new HashMap<>();
            dataset.put("label", empleado);
            dataset.put("data", ventas);

            datasets.add(dataset);
        }
        data7.put("datasets", datasets);
        data.put("7", data7);

        Map<String, Object> data30 = new HashMap<>();
        data30.put("labels", java.util.stream.IntStream.rangeClosed(1, 30).mapToObj(Integer::toString).toArray(String[]::new));
        Map<String, double[]> ventasMensualesPorEmpleado = obtenerSumaTotalVentaMensualPorEmpleado();
        List<Map<String, Object>> datasets30 = new ArrayList<>();
        for (Map.Entry<String, double[]> entry : ventasMensualesPorEmpleado.entrySet()) {
            String empleado = entry.getKey();
            double[] ventas = entry.getValue();
            Map<String, Object> dataset = new HashMap<>();
            dataset.put("label", "Empleado " + empleado);
            dataset.put("data", ventas);
            datasets30.add(dataset);
        }
        data30.put("datasets", datasets30);
        data.put("30", data30);

        Map<String, Object> data365 = new HashMap<>();
        data365.put("labels", new String[]{"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"});
        Map<String, double[]> ventasAnualesPorEmpleado = obtenerSumaTotalVentaAnualPorEmpleado();
        List<Map<String, Object>> datasets365 = new ArrayList<>();
        for (Map.Entry<String, double[]> entry : ventasAnualesPorEmpleado.entrySet()) {
            String empleado = entry.getKey();
            double[] ventas = entry.getValue();
            Map<String, Object> dataset = new HashMap<>();
            dataset.put("label", "Empleado " + empleado);
            dataset.put("data", ventas);
            datasets365.add(dataset);
        }
        data365.put("datasets", datasets365);
        data.put("365", data365);

        return data;
    }

    // Método auxiliar para crear los datasets
    private List<Map<String, Object>> crearDatasets(String[] labels, int[][] data) {
        List<Map<String, Object>> datasets = new ArrayList<>();
        for (int i = 0; i < labels.length; i++) {
            datasets.add(createDataset(labels[i], data[i]));
        }
        return datasets;
    }

    // Método auxiliar para crear un dataset
    private Map<String, Object> createDataset(String label, int[] data) {
        Map<String, Object> dataset = new HashMap<>();
        dataset.put("label", label);
        dataset.put("data", data);
        return dataset;
    }

    private double[] obtenerSumaTotalVentaSemanal() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        double[] sumaTotalVentaSemana = new double[7];

        try {
            String sql = "SELECT DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL (WEEKDAY(CURDATE()) + 7) % 7 DAY) + INTERVAL (n.n) DAY, '%e-%b') AS FechaPedido, "
                    + "       COALESCE(SUM(p.TotalVenta), 0) AS SumaTotalVenta "
                    + "FROM ( "
                    + "    SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 "
                    + "    UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 "
                    + ") n "
                    + "LEFT JOIN ( "
                    + "    SELECT * "
                    + "    FROM t_pedido "
                    + "    WHERE Fecha >= DATE_SUB(CURDATE(), INTERVAL (WEEKDAY(CURDATE()) + 7) % 7 DAY) "
                    + "      AND Fecha <= CURDATE() "
                    + ") p ON DATE(p.Fecha) = DATE_SUB(CURDATE(), INTERVAL (WEEKDAY(CURDATE()) + 7) % 7 DAY) + INTERVAL (n.n) DAY "
                    + "GROUP BY FechaPedido "
                    + "ORDER BY DATE_SUB(CURDATE(), INTERVAL (WEEKDAY(CURDATE()) + 7) % 7 DAY) + INTERVAL (n.n) DAY ASC";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            int i = 0;
            while (rs.next() && i < 7) {
                sumaTotalVentaSemana[i] = rs.getDouble("SumaTotalVenta");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return sumaTotalVentaSemana;
    }

    private Map<String, Object> obtenerSumaTotalVentaMensual() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String[] fechasVentaMes = new String[31];
        double[] sumaTotalVentaMes = new double[31];

        try {
            String setSql1 = "SET @primer_dia_mes_actual := CURDATE() - INTERVAL (DAY(CURDATE()) - 1) DAY;";
            String setSql2 = "SET @ultimo_dia_mes_actual := LAST_DAY(CURDATE());";
            String sql = "SELECT DATE_FORMAT(cal.fecha, '%e') AS Fecha,\n"
                    + "       COALESCE(SUM(p.TotalVenta), 0) AS SumaTotalVenta\n"
                    + "FROM (\n"
                    + "    SELECT @primer_dia_mes_actual + INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY AS fecha\n"
                    + "    FROM (\n"
                    + "        SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL\n"
                    + "        SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9\n"
                    + "    ) a \n"
                    + "    CROSS JOIN (\n"
                    + "        SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL\n"
                    + "        SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9\n"
                    + "    ) b \n"
                    + "    CROSS JOIN (\n"
                    + "        SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL\n"
                    + "        SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9\n"
                    + "    ) c \n"
                    + "    WHERE @primer_dia_mes_actual + INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY <= @ultimo_dia_mes_actual\n"
                    + ") cal\n"
                    + "LEFT JOIN (\n"
                    + "    SELECT DATE(Fecha) AS Fecha, SUM(TotalVenta) AS TotalVenta\n"
                    + "    FROM t_pedido\n"
                    + "    WHERE Fecha >= @primer_dia_mes_actual\n"
                    + "      AND Fecha <= @ultimo_dia_mes_actual\n"
                    + "    GROUP BY DATE(Fecha)\n"
                    + ") p ON cal.fecha = p.Fecha\n"
                    + "GROUP BY cal.fecha\n"
                    + "ORDER BY cal.fecha;";

            // Execute the SET commands first
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(setSql1);
                stmt.execute(setSql2);
            }

            // Execute the SELECT query
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            int i = 0;
            while (rs.next() && i < 31) {
                fechasVentaMes[i] = rs.getString("Fecha");
                sumaTotalVentaMes[i] = rs.getDouble("SumaTotalVenta");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("fechas", fechasVentaMes);
        resultado.put("ventas", sumaTotalVentaMes);

        return resultado;
    }

    private double[] obtenerSumaTotalVentaAnual() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        double[] sumaTotalVentaAnual = new double[12];

        try {
            String sql = "SELECT MONTHNAME(Fecha) AS Mes, "
                    + "COALESCE(SUM(TotalVenta), 0) AS SumaTotalVenta "
                    + "FROM ( "
                    + "    SELECT * "
                    + "    FROM t_pedido "
                    + "    WHERE YEAR(Fecha) = YEAR(CURDATE()) "
                    + ") AS pedidos_2024 "
                    + "RIGHT JOIN ( "
                    + "    SELECT 1 AS mes UNION ALL SELECT 2 AS mes UNION ALL SELECT 3 AS mes "
                    + "    UNION ALL SELECT 4 AS mes UNION ALL SELECT 5 AS mes UNION ALL SELECT 6 AS mes "
                    + "    UNION ALL SELECT 7 AS mes UNION ALL SELECT 8 AS mes UNION ALL SELECT 9 AS mes "
                    + "    UNION ALL SELECT 10 AS mes UNION ALL SELECT 11 AS mes UNION ALL SELECT 12 AS mes "
                    + ") AS todos_meses ON MONTH(Fecha) = todos_meses.mes "
                    + "GROUP BY todos_meses.mes "
                    + "ORDER BY todos_meses.mes;";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            int i = 0;
            while (rs.next() && i < 12) {
                sumaTotalVentaAnual[i] = rs.getDouble("SumaTotalVenta");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return sumaTotalVentaAnual;
    }

    private double[] obtenerGananciasSemanales() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        double[] gananciasSemanales = new double[7];

        try {
            String sql = "SELECT DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL (WEEKDAY(CURDATE()) + 7) % 7 DAY) + INTERVAL (n.n) DAY, '%e-%b') AS FechaPedido, "
                    + "       COALESCE(SUM(dp.cantidad * (prod.precio - prod.costo)), 0) AS GananciasSemanales "
                    + "FROM ( "
                    + "    SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 "
                    + "    UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 "
                    + ") n "
                    + "LEFT JOIN ( "
                    + "    SELECT dp.Id_Pedido, dp.Id_Prod, dp.cantidad, t_pedido.Fecha, t_producto.precio, t_producto.costo "
                    + "    FROM t_detalle_pedido dp "
                    + "    INNER JOIN t_pedido ON dp.Id_Pedido = t_pedido.Id_Pedido "
                    + "    INNER JOIN t_producto ON dp.Id_Prod = t_producto.Id_Prod "
                    + "    WHERE t_pedido.Fecha >= DATE_SUB(CURDATE(), INTERVAL (WEEKDAY(CURDATE()) + 7) % 7 DAY) "
                    + "      AND t_pedido.Fecha <= DATE_ADD(CURDATE(), INTERVAL (6 - WEEKDAY(CURDATE())) DAY) "
                    + ") dp ON DATE(dp.Fecha) = DATE_SUB(CURDATE(), INTERVAL (WEEKDAY(CURDATE()) + 7) % 7 DAY) + INTERVAL (n.n) DAY "
                    + "LEFT JOIN t_producto prod ON dp.Id_Prod = prod.Id_Prod "
                    + "GROUP BY FechaPedido "
                    + "ORDER BY DATE_SUB(CURDATE(), INTERVAL (WEEKDAY(CURDATE()) + 7) % 7 DAY) + INTERVAL (n.n) DAY ASC";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            int i = 0;
            while (rs.next() && i < 7) {
                gananciasSemanales[i] = rs.getDouble("GananciasSemanales");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return gananciasSemanales;
    }

    private Map<String, Object> obtenerGananciasMensuales() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String[] fechasVentaMes = new String[31];
        double[] gananciasMensuales = new double[31];

        try {
            String setSql1 = "SET @primer_dia_mes_actual := CURDATE() - INTERVAL (DAY(CURDATE()) - 1) DAY;";
            String setSql2 = "SET @ultimo_dia_mes_actual := LAST_DAY(CURDATE());";
            String sql = "SELECT DATE_FORMAT(cal.fecha, '%e') AS Fecha,\n"
                    + "       COALESCE(SUM(dp.cantidad * (prod.precio - prod.costo)), 0) AS GananciasMensuales\n"
                    + "FROM (\n"
                    + "    SELECT @primer_dia_mes_actual + INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY AS fecha\n"
                    + "    FROM (\n"
                    + "        SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL\n"
                    + "        SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9\n"
                    + "    ) a \n"
                    + "    CROSS JOIN (\n"
                    + "        SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL\n"
                    + "        SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9\n"
                    + "    ) b \n"
                    + "    CROSS JOIN (\n"
                    + "        SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL\n"
                    + "        SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9\n"
                    + "    ) c \n"
                    + "    WHERE @primer_dia_mes_actual + INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY <= @ultimo_dia_mes_actual\n"
                    + ") cal\n"
                    + "LEFT JOIN (\n"
                    + "    SELECT dp.Id_Pedido, dp.Id_Prod, dp.cantidad, t_pedido.Fecha, t_producto.precio, t_producto.costo\n"
                    + "    FROM t_detalle_pedido dp\n"
                    + "    INNER JOIN t_pedido ON dp.Id_Pedido = t_pedido.Id_Pedido\n"
                    + "    INNER JOIN t_producto ON dp.Id_Prod = t_producto.Id_Prod\n"
                    + "    WHERE t_pedido.Fecha >= @primer_dia_mes_actual\n"
                    + "      AND t_pedido.Fecha <= @ultimo_dia_mes_actual\n"
                    + ") dp ON cal.fecha = DATE(dp.Fecha)\n"
                    + "LEFT JOIN t_producto prod ON dp.Id_Prod = prod.Id_Prod\n"
                    + "GROUP BY cal.fecha\n"
                    + "ORDER BY cal.fecha;";

            // Execute the SET commands first
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(setSql1);
                stmt.execute(setSql2);
            }

            // Execute the SELECT query
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            int i = 0;
            while (rs.next() && i < 31) {
                fechasVentaMes[i] = rs.getString("Fecha");
                gananciasMensuales[i] = rs.getDouble("GananciasMensuales");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("fechas", fechasVentaMes);
        resultado.put("ganancias", gananciasMensuales);

        return resultado;
    }

    private double[] obtenerGananciasAnuales() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        double[] gananciasAnuales = new double[12];

        try {
            String sql = "SELECT MONTHNAME(dp.Fecha) AS Mes, "
                    + "COALESCE(SUM(dp.cantidad * (prod.precio - prod.costo)), 0) AS GananciasAnuales "
                    + "FROM ( "
                    + "    SELECT dp.Id_Pedido, dp.Id_Prod, dp.cantidad, t_pedido.Fecha, t_producto.precio, t_producto.costo "
                    + "    FROM t_detalle_pedido dp "
                    + "    INNER JOIN t_pedido ON dp.Id_Pedido = t_pedido.Id_Pedido "
                    + "    INNER JOIN t_producto ON dp.Id_Prod = t_producto.Id_Prod "
                    + "    WHERE YEAR(t_pedido.Fecha) = YEAR(CURDATE()) "
                    + ") dp "
                    + "LEFT JOIN t_producto prod ON dp.Id_Prod = prod.Id_Prod "
                    + "RIGHT JOIN ( "
                    + "    SELECT 1 AS mes UNION ALL SELECT 2 AS mes UNION ALL SELECT 3 AS mes "
                    + "    UNION ALL SELECT 4 AS mes UNION ALL SELECT 5 AS mes UNION ALL SELECT 6 AS mes "
                    + "    UNION ALL SELECT 7 AS mes UNION ALL SELECT 8 AS mes UNION ALL SELECT 9 AS mes "
                    + "    UNION ALL SELECT 10 AS mes UNION ALL SELECT 11 AS mes UNION ALL SELECT 12 AS mes "
                    + ") todos_meses ON MONTH(dp.Fecha) = todos_meses.mes "
                    + "GROUP BY todos_meses.mes "
                    + "ORDER BY todos_meses.mes;";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            int i = 0;
            while (rs.next() && i < 12) {
                gananciasAnuales[i] = rs.getDouble("GananciasAnuales");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return gananciasAnuales;
    }

    private Map<String, double[]> obtenerSumaTotalVentaSemanalPorEmpleado() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, double[]> ventasPorEmpleado = new HashMap<>();

        try {
            String sql = "WITH TopEmpleados AS ( "
                    + "    SELECT "
                    + "        u.Id_Usuario, "
                    + "        CONCAT(u.Nombres, ' ', u.Apellidos) AS NombreCompleto, "
                    + "        SUM(COALESCE(p.TotalVenta, 0)) AS TotalVentasSemanales "
                    + "    FROM t_usuario u "
                    + "    LEFT JOIN t_pedido p ON u.Id_Usuario = p.Id_Usuario "
                    + "        AND p.Fecha >= DATE_SUB(CURDATE(), INTERVAL (WEEKDAY(CURDATE()) + 7) % 7 DAY) "
                    + "        AND p.Fecha <= CURDATE() "
                    + "    GROUP BY u.Id_Usuario "
                    + "    ORDER BY TotalVentasSemanales DESC "
                    + "    LIMIT 5 "
                    + "), "
                    + "DiasSemana AS ( "
                    + "    SELECT DATE_SUB(CURDATE(), INTERVAL (WEEKDAY(CURDATE()) + 7) % 7 DAY) + INTERVAL n.n DAY AS Fecha "
                    + "    FROM ( "
                    + "        SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 "
                    + "        UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 "
                    + "    ) n "
                    + ") "
                    + "SELECT "
                    + "    te.NombreCompleto AS Empleado, "
                    + "    DATE_FORMAT(ds.Fecha, '%e-%b') AS FechaPedido, "
                    + "    COALESCE(SUM(p.TotalVenta), 0) AS TotalVentaDiaria "
                    + "FROM TopEmpleados te "
                    + "CROSS JOIN DiasSemana ds "
                    + "LEFT JOIN t_pedido p ON te.Id_Usuario = p.Id_Usuario AND DATE(p.Fecha) = ds.Fecha "
                    + "GROUP BY te.NombreCompleto, FechaPedido "
                    + "ORDER BY te.NombreCompleto, FechaPedido ASC";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String empleado = rs.getString("Empleado");
                double totalVentaDiaria = rs.getDouble("TotalVentaDiaria");

                if (!ventasPorEmpleado.containsKey(empleado)) {
                    ventasPorEmpleado.put(empleado, new double[7]);
                }

                String fechaPedido = rs.getString("FechaPedido");
                int index = determinarIndiceFecha(fechaPedido);
                if (index != -1) {
                    ventasPorEmpleado.get(empleado)[index] += totalVentaDiaria;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ventasPorEmpleado;
    }

    private int determinarIndiceFecha(String fechaPedido) {
        // Convertir la fecha del formato 'd-MMM' al índice del día de la semana (0-6)
        SimpleDateFormat sdf = new SimpleDateFormat("d-MMM", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(fechaPedido));
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            // Calendar.DAY_OF_WEEK devuelve 1 (domingo) a 7 (sábado)
            // Convertimos a índice 0 (lunes) a 6 (domingo)
            return (dayOfWeek + 5) % 7;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private Map<String, double[]> obtenerSumaTotalVentaMensualPorEmpleado() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, double[]> ventasPorEmpleado = new HashMap<>();

        try {
            String sql = "WITH TopEmpleados AS ( "
                    + "    SELECT "
                    + "        u.Id_Usuario, "
                    + "        CONCAT(u.Nombres, ' ', u.Apellidos) AS NombreCompleto, "
                    + "        SUM(COALESCE(p.TotalVenta, 0)) AS TotalVentasMes "
                    + "    FROM t_usuario u "
                    + "    LEFT JOIN t_pedido p ON u.Id_Usuario = p.Id_Usuario "
                    + "        AND p.Fecha >= DATE_SUB(CURDATE(), INTERVAL DAYOFMONTH(CURDATE()) - 1 DAY) "
                    + "        AND p.Fecha <= LAST_DAY(CURDATE()) "
                    + "    GROUP BY u.Id_Usuario "
                    + "    ORDER BY TotalVentasMes DESC "
                    + "    LIMIT 5 "
                    + "), "
                    + "DiasMes AS ( "
                    + "    SELECT DATE_SUB(CURDATE(), INTERVAL DAYOFMONTH(CURDATE()) - 1 DAY) + INTERVAL n.n DAY AS Fecha "
                    + "    FROM ( "
                    + "        SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 "
                    + "        UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 "
                    + "        UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 "
                    + "        UNION ALL SELECT 12 UNION ALL SELECT 13 UNION ALL SELECT 14 UNION ALL SELECT 15 "
                    + "        UNION ALL SELECT 16 UNION ALL SELECT 17 UNION ALL SELECT 18 UNION ALL SELECT 19 "
                    + "        UNION ALL SELECT 20 UNION ALL SELECT 21 UNION ALL SELECT 22 UNION ALL SELECT 23 "
                    + "        UNION ALL SELECT 24 UNION ALL SELECT 25 UNION ALL SELECT 26 UNION ALL SELECT 27 "
                    + "        UNION ALL SELECT 28 UNION ALL SELECT 29 UNION ALL SELECT 30 UNION ALL SELECT 31 "
                    + "    ) n "
                    + "    WHERE n.n < DAY(LAST_DAY(CURDATE())) "
                    + ") "
                    + "SELECT "
                    + "    te.NombreCompleto AS Empleado, "
                    + "    DATE_FORMAT(dm.Fecha, '%e-%b') AS FechaPedido, "
                    + "    COALESCE(SUM(p.TotalVenta), 0) AS TotalVentaDiaria "
                    + "FROM TopEmpleados te "
                    + "CROSS JOIN DiasMes dm "
                    + "LEFT JOIN t_pedido p ON te.Id_Usuario = p.Id_Usuario AND DATE(p.Fecha) = dm.Fecha "
                    + "GROUP BY te.NombreCompleto, dm.Fecha "
                    + "ORDER BY "
                    + "    FIND_IN_SET(te.Id_Usuario, (SELECT GROUP_CONCAT(Id_Usuario ORDER BY TotalVentasMes DESC) FROM TopEmpleados)), "
                    + "    te.NombreCompleto, "
                    + "    dm.Fecha ASC";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String empleado = rs.getString("Empleado");
                double totalVentaDiaria = rs.getDouble("TotalVentaDiaria");

                if (!ventasPorEmpleado.containsKey(empleado)) {
                    ventasPorEmpleado.put(empleado, new double[obtenerEtiquetasMes().size()]);
                }

                String fechaPedido = rs.getString("FechaPedido");
                int index = determinarIndiceFechaMensual(fechaPedido);
                if (index != -1) {
                    ventasPorEmpleado.get(empleado)[index] += totalVentaDiaria;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ventasPorEmpleado;
    }

    private int determinarIndiceFechaMensual(String fechaPedido) {
        // Implementa la lógica para determinar el índice de fecha según tu necesidad
        // Este es un ejemplo hipotético
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("d-MMM", Locale.ENGLISH); // Cambiado Locale.getDefault() a Locale.ENGLISH
        try {
            cal.setTime(sdf.parse(fechaPedido));
            return cal.get(Calendar.DAY_OF_MONTH) - 1; // Índice basado en el día del mes
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private List<String> obtenerEtiquetasMes() {
        List<String> labels = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);

        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("d-MMM", Locale.getDefault());

        for (int i = 1; i <= lastDay; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i);
            String label = sdf.format(cal.getTime());
            labels.add(label);
        }

        return labels;
    }

    private Map<String, double[]> obtenerSumaTotalVentaAnualPorEmpleado() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, double[]> ventasPorEmpleado = new HashMap<>();

        try {
            String sql = "WITH TopEmpleados AS ( "
                    + "    SELECT "
                    + "        u.Id_Usuario, "
                    + "        CONCAT(u.Nombres, ' ', u.Apellidos) AS NombreCompleto, "
                    + "        SUM(COALESCE(p.TotalVenta, 0)) AS TotalVentasAnuales "
                    + "    FROM t_usuario u "
                    + "    LEFT JOIN t_pedido p ON u.Id_Usuario = p.Id_Usuario "
                    + "        AND YEAR(p.Fecha) = YEAR(CURDATE()) "
                    + "    GROUP BY u.Id_Usuario "
                    + "    ORDER BY TotalVentasAnuales DESC "
                    + "    LIMIT 5 "
                    + "), "
                    + "MesesAnio AS ( "
                    + "    SELECT 1 AS Mes "
                    + "    UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 "
                    + "    UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 "
                    + "    UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10 "
                    + "    UNION ALL SELECT 11 UNION ALL SELECT 12 "
                    + ") "
                    + "SELECT "
                    + "    te.NombreCompleto, "
                    + "    MONTHNAME(DATE_ADD('2024-01-01', INTERVAL MA.Mes - 1 MONTH)) AS Mes, "
                    + "    COALESCE(SUM(p.TotalVenta), 0) AS TotalVentaMensual "
                    + "FROM TopEmpleados te "
                    + "CROSS JOIN MesesAnio MA "
                    + "LEFT JOIN t_pedido p ON te.Id_Usuario = p.Id_Usuario AND MONTH(p.Fecha) = MA.Mes "
                    + "GROUP BY te.Id_Usuario, te.NombreCompleto, Mes "
                    + "ORDER BY "
                    + "    FIND_IN_SET(te.Id_Usuario, (SELECT GROUP_CONCAT(Id_Usuario ORDER BY TotalVentasAnuales DESC) FROM TopEmpleados)), "
                    + "    te.NombreCompleto, "
                    + "    MA.Mes ASC";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String empleado = rs.getString("NombreCompleto");
                String mes = rs.getString("Mes");
                double totalVentaMensual = rs.getDouble("TotalVentaMensual");

                if (!ventasPorEmpleado.containsKey(empleado)) {
                    ventasPorEmpleado.put(empleado, new double[12]);
                }

                int index = determinarIndiceMes(mes);
                if (index != -1) {
                    ventasPorEmpleado.get(empleado)[index] += totalVentaMensual;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ventasPorEmpleado;
    }

    private int determinarIndiceMes(String mes) {
        // Convertir el nombre del mes en un índice del 0 al 11
        try {
            Date date = new SimpleDateFormat("MMMM", Locale.ENGLISH).parse(mes);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String obtenerDatosComoJSON() {
        Map<String, Object> datos = new HashMap<>();
        datos.put("ventas7Valor", obtenerVentasSemanalesValor());
        datos.put("ventas30Valor", obtenerVentasMensualesValor());
        datos.put("ventas365Valor", obtenerVentasAnualesValor());
        datos.put("ganancias7Valor", obtenerGananciasSemanalesValor());
        datos.put("ganancias30Valor", obtenerGananciasMensualesValor());
        datos.put("ganancias365Valor", obtenerGananciasAnualesValor());
        datos.put("txtPedidosValor", obtenerPedidosValor());
        datos.put("txtPedidosPromedioValor", obtenerPedidosPromedioValor());
        datos.put("txtProductosValor", obtenerProductosValor());
        datos.put("txtProductosVendidosValor", obtenerProductosVendidosValor());
        datos.put("txtClientesValor", obtenerClientesValor());
        datos.put("txtClientesActivosValor", obtenerClientesActivosValor());
        datos.put("txtEmpleadosValor", obtenerEmpleadosValor());
        datos.put("txtEmpleadosActivosValor", obtenerEmpleadosActivosValor());

        Gson gson = new Gson();
        return gson.toJson(datos);
    }

    public double obtenerVentasSemanalesValor() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        double sumaVentasSemanales = 0;

        try {
            String sql = "SELECT COALESCE(SUM(p.TotalVenta), 0) AS SumaTotalVentasSemanales "
                    + "FROM t_pedido p "
                    + "WHERE p.Fecha >= DATE_SUB(CURDATE(), INTERVAL (WEEKDAY(CURDATE()) + 7) % 7 DAY) "
                    + "AND p.Fecha <= CURDATE()";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                sumaVentasSemanales = rs.getDouble("SumaTotalVentasSemanales");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return sumaVentasSemanales;
    }

    public double obtenerVentasMensualesValor() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        double sumaVentasMensuales = 0;

        try {
            String sql = "SELECT COALESCE(SUM(p.TotalVenta), 0) AS SumaTotalVentasMensuales "
                    + "FROM t_pedido p "
                    + "WHERE p.Fecha >= DATE_SUB(CURDATE(), INTERVAL DAYOFMONTH(CURDATE()) - 1 DAY) "
                    + "AND p.Fecha <= LAST_DAY(CURDATE())";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                sumaVentasMensuales = rs.getDouble("SumaTotalVentasMensuales");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return sumaVentasMensuales;
    }

    public double obtenerVentasAnualesValor() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        double sumaVentasAnuales = 0;

        try {
            String sql = "SELECT COALESCE(SUM(p.TotalVenta), 0) AS SumaTotalVentasAnuales "
                    + "FROM t_pedido p "
                    + "WHERE p.Fecha >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) "
                    + "AND p.Fecha <= CURDATE()";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                sumaVentasAnuales = rs.getDouble("SumaTotalVentasAnuales");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return sumaVentasAnuales;
    }

    public double obtenerGananciasSemanalesValor() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        double gananciasSemanales = 0;

        try {
            String sql = "SELECT COALESCE(SUM(dp.cantidad * (prod.precio - prod.costo)), 0) AS GananciasSemanales "
                    + "FROM ( "
                    + "    SELECT dp.Id_Pedido, dp.Id_Prod, dp.cantidad, t_producto.precio, t_producto.costo "
                    + "    FROM t_detalle_pedido dp "
                    + "    INNER JOIN t_pedido ON dp.Id_Pedido = t_pedido.Id_Pedido "
                    + "    INNER JOIN t_producto ON dp.Id_Prod = t_producto.Id_Prod "
                    + "    WHERE t_pedido.Fecha >= DATE_SUB(CURDATE(), INTERVAL (WEEKDAY(CURDATE()) + 7) % 7 DAY) "
                    + "      AND t_pedido.Fecha <= CURDATE() "
                    + ") dp "
                    + "LEFT JOIN t_producto prod ON dp.Id_Prod = prod.Id_Prod";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                gananciasSemanales = rs.getDouble("GananciasSemanales");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return gananciasSemanales;
    }

    public double obtenerGananciasMensualesValor() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        double gananciasMensuales = 0;

        try {
            String sql = "SELECT COALESCE(SUM(dp.cantidad * (prod.precio - prod.costo)), 0) AS GananciasMensuales "
                    + "FROM t_detalle_pedido dp "
                    + "INNER JOIN t_pedido ON dp.Id_Pedido = t_pedido.Id_Pedido "
                    + "INNER JOIN t_producto prod ON dp.Id_Prod = prod.Id_Prod "
                    + "WHERE t_pedido.Fecha >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH) "
                    + "  AND t_pedido.Fecha <= LAST_DAY(CURDATE())";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                gananciasMensuales = rs.getDouble("GananciasMensuales");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return gananciasMensuales;
    }

    public double obtenerGananciasAnualesValor() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        double gananciasAnuales = 0;

        try {
            String sql = "SELECT COALESCE(SUM(dp.cantidad * (prod.precio - prod.costo)), 0) AS GananciasAnuales "
                    + "FROM t_detalle_pedido dp "
                    + "INNER JOIN t_pedido ON dp.Id_Pedido = t_pedido.Id_Pedido "
                    + "INNER JOIN t_producto prod ON dp.Id_Prod = prod.Id_Prod "
                    + "WHERE t_pedido.Fecha >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) "
                    + "  AND t_pedido.Fecha <= CURDATE()";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                gananciasAnuales = rs.getDouble("GananciasAnuales");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return gananciasAnuales;
    }

    public int obtenerPedidosValor() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int totalPedidos = 0;

        try {
            String sql = "SELECT COUNT(*) AS TotalPedidos FROM t_pedido";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                totalPedidos = rs.getInt("TotalPedidos");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalPedidos;
    }

    public double obtenerPedidosPromedioValor() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        double promedioPedidosDiarios = 0;

        try {
            String sql = "SELECT AVG(TotalPedidos) AS PromedioPedidosDiarios "
                    + "FROM ( "
                    + "    SELECT COUNT(*) AS TotalPedidos "
                    + "    FROM t_pedido "
                    + "    GROUP BY Fecha "
                    + ") AS PedidosDiarios";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                promedioPedidosDiarios = rs.getDouble("PromedioPedidosDiarios");
            } else {
                System.out.println("No se encontraron datos para calcular el promedio de pedidos diarios.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return promedioPedidosDiarios;
    }

    public int obtenerProductosValor() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int totalProductos = 0;

        try {
            String sql = "SELECT COUNT(*) AS TotalProductos FROM t_producto";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                totalProductos = rs.getInt("TotalProductos");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalProductos;
    }

    public int obtenerProductosVendidosValor() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int totalUnidadesProductosVendidos = 0;

        try {
            String sql = "SELECT SUM(dp.cantidad) AS TotalUnidadesProductosVendidos FROM t_detalle_pedido dp";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                totalUnidadesProductosVendidos = rs.getInt("TotalUnidadesProductosVendidos");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalUnidadesProductosVendidos;
    }

    public int obtenerClientesValor() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int totalClientes = 0;

        try {
            String sql = "SELECT COUNT(*) AS TotalClientes FROM t_cliente";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                totalClientes = rs.getInt("TotalClientes");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalClientes;
    }

    public int obtenerClientesActivosValor() {
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int clientesActivos = 0;

        try {
            String sql = "SELECT COUNT(*) AS ClientesActivos FROM t_cliente WHERE estado = 'Activo'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                clientesActivos = rs.getInt("ClientesActivos");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return clientesActivos;
    }

    public int obtenerEmpleadosValor() {
        int totalEmpleados = 0;
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT COUNT(*) AS TotalEmpleados FROM t_usuario WHERE Id_Usuario LIKE 'u%'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                totalEmpleados = rs.getInt("TotalEmpleados");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalEmpleados;
    }

    public int obtenerEmpleadosActivosValor() {
        int totalEmpleadosActivos = 0;
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT COUNT(*) AS TotalEmpleadosActivos FROM t_usuario WHERE Id_Usuario LIKE 'u%' AND Estado = 'Activo'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                totalEmpleadosActivos = rs.getInt("TotalEmpleadosActivos");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalEmpleadosActivos;
    }

}
