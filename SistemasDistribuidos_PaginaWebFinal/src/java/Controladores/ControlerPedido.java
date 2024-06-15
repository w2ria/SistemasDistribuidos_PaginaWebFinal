package Controladores;

import Entidades.Pedido;
import Entidades.DetallePedido;
import Conexion.Conexion;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ControlerPedido", urlPatterns = {"/ControlerPedido"})
public class ControlerPedido extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ArrayList<Pedido> pedidos = new ArrayList<>();
        ArrayList<DetallePedido> detalles = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.Conexion();

        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM t_pedido";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getString("Id_Pedido"));
                pedido.setIdCliente(rs.getString("Id_Cliente"));
                pedido.setIdUsuario(rs.getString("Id_Usuario"));
                pedido.setFecha(rs.getDate("Fecha"));
                pedido.setSubTotal(rs.getDouble("SubTotal"));
                pedido.setTotalVenta(rs.getDouble("TotalVenta"));
                pedidos.add(pedido);
            }
            rs.close();

            
            sql = "SELECT * FROM t_detalle_pedido";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                DetallePedido detalle = new DetallePedido();
                detalle.setIdDetallePedido(rs.getString("Id_DetallePedido"));
                detalle.setIdPedido(rs.getString("Id_Pedido"));
                detalle.setIdProd(rs.getString("Id_Prod"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setPrecio(rs.getDouble("precio"));
                detalle.setTotalDeta(rs.getDouble("TotalDeta"));
                detalles.add(detalle);
            }
            rs.close();

            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Número de pedidos recuperados: " + pedidos.size());

        request.setAttribute("pedidos", pedidos);
        request.setAttribute("detalles", detalles);

        String operation = request.getParameter("Op");
        if ("Exportar".equals(operation)) {
            exportarPDF(response, pedidos);
        } else {
            request.getRequestDispatcher("MenuPedidos.jsp").forward(request, response);
        }
    }

    private void exportarPDF(HttpServletResponse response, ArrayList<Pedido> pedidos) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=pedidos.pdf");

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            document.add(new Paragraph("Lista de Pedidos"));

            PdfPTable table = new PdfPTable(6); // Número de columnas
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Encabezados de la tabla
            String[] headers = {"Id_Pedido", "Id_Cliente", "Id_Usuario", "Fecha", "SubTotal", "TotalVenta"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            // Datos de la tabla
            for (Pedido pedido : pedidos) {
                table.addCell(pedido.getIdPedido());
                table.addCell(pedido.getIdCliente());
                table.addCell(pedido.getIdUsuario());
                table.addCell(pedido.getFecha().toString());
                table.addCell(String.valueOf(pedido.getSubTotal()));
                table.addCell(String.valueOf(pedido.getTotalVenta()));
            }

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
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
        return "Servlet que lista pedidos y muestra detalles de pedidos";
    }
}
