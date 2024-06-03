/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controladores;

import Entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author danie
 */
public class ValidarLogin extends HttpServlet {

    private static final int MAX_INTENTOS = 4;
    private static final long BLOQUEO_TIEMPO_MS = 10000; // 10 segundos
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
        Conexion.Conexion conBD = new Conexion.Conexion();
        Connection conn = conBD.Conexion();
        PreparedStatement ps;
        ResultSet rs;

        String usuario = request.getParameter("txtUsuario");
        String contraseña = request.getParameter("contra");

        String contraseñaEncriptada = EncriptadorAES.encriptarAES(contraseña);

        HttpSession session = request.getSession();
        Integer intentosFallidos = (Integer) session.getAttribute("intentosFallidos_" + usuario);
        Long tiempoBloqueo = (Long) session.getAttribute("tiempoBloqueo_" + usuario);

        if (intentosFallidos == null) {
            intentosFallidos = 0;
        }

        long ahora = System.currentTimeMillis();
        if (tiempoBloqueo != null && ahora < tiempoBloqueo) {
            response.sendRedirect("Login.jsp?error=bloqueado");
            return;
        }

        try {
            String sql = "SELECT * FROM t_usuario WHERE IdUsuario=? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);

            rs = ps.executeQuery();

            if (rs.next()) {
                String contraseñaAlmacenadaEncriptada  = rs.getString("Passwd");
                String estadoUsuario = rs.getString("Estado");

                if ("activo".equalsIgnoreCase(estadoUsuario)) {
                    String contraseñaAlmacenada = EncriptadorAES.desencriptarAES(contraseñaAlmacenadaEncriptada);
                    if (contraseñaAlmacenada.equals(contraseña)) {
                        // Autenticación exitosa
                        Usuario nuser = new Usuario(usuario, contraseña);
                        session.setAttribute("user", nuser);
                        session.setAttribute("IdUsuario", usuario); // Asegúrate de que el ID del usuario se configura en la sesión
                        session.removeAttribute("intentosFallidos_" + usuario);
                        session.removeAttribute("tiempoBloqueo_" + usuario);                      
                        
                        //session.setMaxInactiveInterval(5); // se le da 10 segundos, sino al hacer una accion se cierra, le di 5 segundos mas para q no haya problemas al contar y espere que llegue a 0 el contador de js                     
                        
                        //request.getRequestDispatcher("MenuClientes.jsp").forward(request, response);
                        response.sendRedirect("ControlerCliente?Op=Listar");
                    } else {
                        // Contraseña incorrecta
                        intentosFallidos++;
                        session.setAttribute("intentosFallidos_" + usuario, intentosFallidos);
                        if (intentosFallidos >= MAX_INTENTOS) {
                            session.setAttribute("tiempoBloqueo_" + usuario, ahora + BLOQUEO_TIEMPO_MS);
                            response.sendRedirect("Login.jsp?error=bloqueado");
                        } else {
                            response.sendRedirect("Login.jsp?error=incorrecto");
                        }
                    }
                } else {
                    // Usuario inactivo
                    response.sendRedirect("Login.jsp?error=inactivo");
                }
            } else {
                // Usuario no encontrado
                response.sendRedirect("Login.jsp?error=incorrecto");
            }
        } catch (SQLException ex) {
            System.out.println("Error de SQL..." + ex.getMessage());
        }
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
