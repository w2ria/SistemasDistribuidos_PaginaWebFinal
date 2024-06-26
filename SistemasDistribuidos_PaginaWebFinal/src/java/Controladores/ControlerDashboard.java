package Controladores;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
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
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps = null;
        ResultSet rs;
        String sql;
        Map<String, Object> result = new HashMap<>();

        switch (opcion) {
            case "Listar":
                HttpSession session = request.getSession();
                String id = (String) session.getAttribute("IdUsuario");
                String nombre = (String) session.getAttribute("Nombre");
                request.setAttribute("Id_Usuario", id);
                request.setAttribute("Nombre", nombre);

                Map<String, Object> ventasData = obtenerVentas();
                System.out.println("ventasData: " + ventasData);
                Gson gson = new Gson();
                String ventasDataJson = gson.toJson(ventasData);
                System.out.println("ventasDataJson: " + ventasDataJson);
                request.setAttribute("ventasData", ventasDataJson);

                Map<String, Object> gananciasData = obtenerGanancias();
                System.out.println("gananciasData: " + gananciasData);
                gson = new Gson();
                String gananciasDataJson = gson.toJson(gananciasData);
                System.out.println("gananciasDataJson: " + gananciasDataJson);
                request.setAttribute("gananciasData", gananciasDataJson);

                Map<String, Object> pedidosData = obtenerPedidos();
                System.out.println("pedidosData: " + pedidosData);
                gson = new Gson();
                String pedidosDataJson = gson.toJson(pedidosData);
                System.out.println("pedidosDataJson: " + pedidosDataJson);
                request.setAttribute("pedidosData", pedidosDataJson);

                Map<String, Object> empleadosData = obtenerEmpleados();
                System.out.println("empleadosData: " + empleadosData);
                gson = new Gson();
                String empleadosDataJson = gson.toJson(empleadosData);
                System.out.println("empleadosDataJson: " + empleadosDataJson);
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
        data7.put("data", new int[]{100, 70, 90, 70, 85, 60, 75});
        data7.put("label", new String[]{"LUN", "MAR", "MIÉR", "JUE", "VIE", "SÁB", "DOM"});
        data.put("7", data7);

        Map<String, Object> data30 = new HashMap<>();
        data30.put("data", new int[]{100, 70, 90, 70, 85, 60, 75, 60, 90, 80, 110, 100, 130, 50, 100, 70, 90, 70, 85, 60, 75, 60, 90, 80, 110, 100, 130, 50, 100, 70, 90});
        data30.put("label", java.util.stream.IntStream.rangeClosed(1, 30).mapToObj(Integer::toString).toArray(String[]::new));
        data.put("30", data30);

        Map<String, Object> data365 = new HashMap<>();
        data365.put("data", new int[]{100, 70, 90, 70, 85, 60, 75, 80, 90, 100, 110, 120});
        data365.put("label", new String[]{"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"});
        data.put("365", data365);

        return data;
    }

    private Map<String, Object> obtenerGanancias() {
        Map<String, Object> data = new HashMap<>();

        Map<String, Object> data7 = new HashMap<>();
        data7.put("data", new int[]{100, 0, 90, 70, 85, 60, 75});
        data7.put("label", new String[]{"LUN", "MAR", "MIÉR", "JUE", "VIE", "SÁB", "DOM"});
        data.put("7", data7);

        Map<String, Object> data30 = new HashMap<>();
        data30.put("data", new int[]{100, 70, 0, 70, 85, 60, 75, 60, 90, 80, 110, 100, 130, 50, 100, 70, 90, 70, 85, 60, 75, 60, 90, 80, 110, 100, 130, 50, 100, 70, 90});
        data30.put("label", java.util.stream.IntStream.rangeClosed(1, 30).mapToObj(Integer::toString).toArray(String[]::new));
        data.put("30", data30);

        Map<String, Object> data365 = new HashMap<>();
        data365.put("data", new int[]{100, 70, 90, 0, 85, 60, 75, 80, 90, 100, 110, 120});
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
        data7.put("datasets", crearDatasets(new String[]{"Empleado 1", "Empleado 3", "Empleado 3", "Empleado 4", "Empleado 5"},
                new int[][]{
                    {100, 70, 90, 70, 85, 60, 75},
                    {60, 75, 90, 80, 110, 100, 130},
                    {50, 100, 70, 90, 70, 85, 60},
                    {75, 60, 90, 80, 110, 100, 130},
                    {10, 50, 30, 50, 85, 70, 65}
                }));
        data.put("7", data7);

        Map<String, Object> data30 = new HashMap<>();
        data30.put("labels", java.util.stream.IntStream.rangeClosed(1, 30).mapToObj(Integer::toString).toArray(String[]::new));
        data30.put("datasets", crearDatasets(new String[]{"Empleado 11", "Empleado 3", "Empleado 3", "Empleado 4", "Empleado 5"},
                new int[][]{
                    {100, 70, 90, 70, 85, 60, 75, 60, 90, 80, 110, 100, 130, 50, 100, 70, 90, 70, 85, 60, 75, 60, 90, 80, 110, 100, 130, 50, 100, 70, 90},
                    {70, 90, 70, 85, 60, 75, 60, 90, 80, 110, 100, 130, 50, 100, 70, 90, 70, 85, 60, 75, 60, 90, 80, 110, 100, 130, 50, 100, 70, 90},
                    {100, 130, 50, 100, 70, 90, 70, 85, 60, 75, 60, 90, 80, 110, 100, 130, 50, 100, 70, 90, 70, 85, 60, 75, 60, 90, 80, 110, 100, 130},
                    {70, 85, 60, 75, 60, 90, 80, 110, 100, 130, 50, 100, 70, 90, 70, 85, 60, 75, 60, 90, 80, 110, 100, 130, 50, 100, 70, 90, 70, 85},
                    {60, 75, 60, 90, 80, 110, 100, 130, 50, 100, 70, 90, 70, 85, 60, 75, 60, 90, 80, 110, 100, 130, 50, 100, 70, 90, 70, 85, 60, 75}
                }));
        data.put("30", data30);

        Map<String, Object> data365 = new HashMap<>();
        data365.put("labels", new String[]{"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"});
        data365.put("datasets", crearDatasets(new String[]{"Empleado 111", "Empleado 3", "Empleado 3", "Empleado 4", "Empleado 5"},
                new int[][]{
                    {100, 70, 90, 70, 85, 60, 75, 80, 90, 100, 110, 120},
                    {70, 90, 100, 110, 120, 70, 85, 60, 75, 80, 90, 100},
                    {110, 120, 100, 130, 50, 100, 70, 90, 70, 85, 60, 75},
                    {80, 90, 100, 110, 120, 100, 130, 50, 100, 70, 90, 70},
                    {85, 60, 75, 80, 90, 100, 110, 120, 100, 130, 50, 100}
                }));
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

}
