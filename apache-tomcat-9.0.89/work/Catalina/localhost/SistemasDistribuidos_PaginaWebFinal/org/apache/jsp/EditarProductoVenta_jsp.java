/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.89
 * Generated at: 2024-06-23 02:04:32 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class EditarProductoVenta_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(4);
    _jspx_dependants.put("/WEB-INF/lib/jstl-1.2.jar", Long.valueOf(1717378218116L));
    _jspx_dependants.put("/ModalSesionExpirada.jsp", Long.valueOf(1717456573208L));
    _jspx_dependants.put("jar:file:/D:/maria/Descargas/SistemasDistribuidos_PaginaWebFinal/SistemasDistribuidos_PaginaWebFinal/build/web/WEB-INF/lib/jstl-1.2.jar!/META-INF/c.tld", Long.valueOf(1153403082000L));
    _jspx_dependants.put("/NavBar.jsp", Long.valueOf(1719106741936L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.LinkedHashSet<>(3);
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("    <title>Ventas</title>\r\n");
      out.write("    <script src=\"https://kit.fontawesome.com/26a3cc7edf.js\" crossorigin=\"anonymous\"></script>\r\n");
      out.write("    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\" crossorigin=\"anonymous\">\r\n");
      out.write("    <style>\r\n");
      out.write("        * {\r\n");
      out.write("            margin: 0;\r\n");
      out.write("            padding: 0;\r\n");
      out.write("        }\r\n");
      out.write("        body, html {\r\n");
      out.write("            height: 100%;\r\n");
      out.write("        }\r\n");
      out.write("        .btnDesplegable {\r\n");
      out.write("            height: 100vh;\r\n");
      out.write("            width: 6%;\r\n");
      out.write("            position: fixed;\r\n");
      out.write("            transition: transform 0.5s ease;\r\n");
      out.write("            z-index: 1;\r\n");
      out.write("        }\r\n");
      out.write("        .cajas {\r\n");
      out.write("            height: 12vh;\r\n");
      out.write("            display: flex;\r\n");
      out.write("            justify-content: center;\r\n");
      out.write("            align-items: center;\r\n");
      out.write("            cursor: pointer;\r\n");
      out.write("        }\r\n");
      out.write("        .cajas:hover {\r\n");
      out.write("            background-color: #87ceeb;\r\n");
      out.write("        }\r\n");
      out.write("        .cajas:nth-child(n+2) {\r\n");
      out.write("            flex-direction: column;\r\n");
      out.write("        }\r\n");
      out.write("        .cajas:last-child {\r\n");
      out.write("            width: 100%;\r\n");
      out.write("            position: absolute;\r\n");
      out.write("            bottom: 0;\r\n");
      out.write("            margin-bottom: 2vh;\r\n");
      out.write("        }\r\n");
      out.write("        .cajas i {\r\n");
      out.write("            font-size: 5vh;\r\n");
      out.write("            padding: 0.2vh;\r\n");
      out.write("        }\r\n");
      out.write("        .cajas h1 {\r\n");
      out.write("            font-size: 2.5vh;\r\n");
      out.write("            padding: 0.2vh;\r\n");
      out.write("            text-align: center;\r\n");
      out.write("        }\r\n");
      out.write("        .navMasContenido {\r\n");
      out.write("            width: 100%;\r\n");
      out.write("        }\r\n");
      out.write("        .navegador {\r\n");
      out.write("            height: 12vh;\r\n");
      out.write("            display: flex;\r\n");
      out.write("            justify-content: space-around;\r\n");
      out.write("        }\r\n");
      out.write("        .imagen {\r\n");
      out.write("            width: 12wh;\r\n");
      out.write("            height: 100%;\r\n");
      out.write("        }\r\n");
      out.write("        .imagen img {\r\n");
      out.write("            width: 100%;\r\n");
      out.write("            height: 100%;\r\n");
      out.write("        }\r\n");
      out.write("        .datos {\r\n");
      out.write("            display: flex;\r\n");
      out.write("            align-items: center;\r\n");
      out.write("        }\r\n");
      out.write("        .Contenido {\r\n");
      out.write("            display: flex;\r\n");
      out.write("            justify-content: center;\r\n");
      out.write("            width: 100%;\r\n");
      out.write("            background-color: #87ceeb;\r\n");
      out.write("            min-height: 100vh;\r\n");
      out.write("        }\r\n");
      out.write("        .tabla {\r\n");
      out.write("            width: 70%;\r\n");
      out.write("            margin: 20px 0; \r\n");
      out.write("        }\r\n");
      out.write("        .titulo {\r\n");
      out.write("            font-size: 10vh;\r\n");
      out.write("            text-align: center;\r\n");
      out.write("            margin-bottom: 20px; \r\n");
      out.write("        }\r\n");
      out.write("        .content-container {\r\n");
      out.write("            display: flex;\r\n");
      out.write("            justify-content: space-around;\r\n");
      out.write("            width: 100%;\r\n");
      out.write("        }\r\n");
      out.write("        .card {\r\n");
      out.write("            margin-bottom: 20px;\r\n");
      out.write("        }\r\n");
      out.write("        .card-footer {\r\n");
      out.write("            display: flex;\r\n");
      out.write("            justify-content: space-between;\r\n");
      out.write("            align-items: center;\r\n");
      out.write("        }\r\n");
      out.write("        \r\n");
      out.write("        .table td{\r\n");
      out.write("            padding: 15px; \r\n");
      out.write("        }\r\n");
      out.write("        \r\n");
      out.write("        .container {\r\n");
      out.write("            display: flex;\r\n");
      out.write("            justify-content: center;\r\n");
      out.write("            align-items: center;\r\n");
      out.write("            height: 100vh;\r\n");
      out.write("        }\r\n");
      out.write("        .form-container {\r\n");
      out.write("            width: 400px;\r\n");
      out.write("            padding: 20px;\r\n");
      out.write("            background-color: #ffffff;\r\n");
      out.write("            border-radius: 8px;\r\n");
      out.write("            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\r\n");
      out.write("        }\r\n");
      out.write("        .form-container h1 {\r\n");
      out.write("            text-align: center;\r\n");
      out.write("            margin-bottom: 20px;\r\n");
      out.write("        }\r\n");
      out.write("        .form-group {\r\n");
      out.write("            margin-bottom: 20px;\r\n");
      out.write("        }\r\n");
      out.write("        .btn-primary {\r\n");
      out.write("            background-color: #000000;\r\n");
      out.write("            border-color: #000000;\r\n");
      out.write("            margin-top: 10px;\r\n");
      out.write("        }\r\n");
      out.write("        .btn-primary:hover {\r\n");
      out.write("            background-color: #333333;\r\n");
      out.write("            border-color: #333333;\r\n");
      out.write("        }\r\n");
      out.write("        .btn-cancelar {\r\n");
      out.write("            background-color: #000000;\r\n");
      out.write("            border-color: #000000;\r\n");
      out.write("            color: #ffffff;\r\n");
      out.write("            margin-top: 10px;\r\n");
      out.write("        }\r\n");
      out.write("        .btn-cancelar:hover {\r\n");
      out.write("            background-color: #333333;\r\n");
      out.write("            border-color: #333333;\r\n");
      out.write("        }\r\n");
      out.write("    </style>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("    ");

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    if (session.getAttribute("user") == null) {
        response.sendRedirect("Login.jsp");
    }

    String XD = (String) request.getAttribute("Nombre");
    String idUsuario = (String) request.getAttribute("Id_Usuario");

    System.out.println("El nombre en jsp es: " + XD);
    System.out.println("El ID USUARIOS ES==================================="+idUsuario);

    // Verificar si el usuario es administrador
    boolean esAdmin = idUsuario != null && idUsuario.startsWith("A");

    // Determinar la pÃÂ¡gina activa
    String currentPage = request.getRequestURI();
    boolean isClientes = currentPage.contains("MenuClientes.jsp");
    boolean isProductos = currentPage.contains("MenuProductos.jsp");
    boolean isPedidos = currentPage.contains("MenuPedidos.jsp");
    boolean isVentas = currentPage.contains("MenuVentas.jsp");
    boolean isUsuarios = currentPage.contains("MenuUsuarios.jsp");
    

      out.write("\r\n");
      out.write("<body style=\"display: flex\"> \r\n");
      out.write("    <div id=\"sidebar1\" class=\"btnDesplegable\" style=\"background-color: #f0f0f0\">\r\n");
      out.write("        <div class=\"cajas\" onclick=\"toggleSidebar()\" style=\"cursor: pointer;\">\r\n");
      out.write("            <i class=\"fa-solid fa-bars\"></i>\r\n");
      out.write("        </div>\r\n");
      out.write("        <a class=\"cajas\" href=\"ControlerCliente?Op=Listar\" style=\"text-decoration: none; color: black; ");
      out.print( isClientes ? "background-color: #87ceeb" : "");
      out.write("\">\r\n");
      out.write("            <i class=\"fa-solid fa-clipboard\"></i>\r\n");
      out.write("            <h1>Clientes</h1>\r\n");
      out.write("        </a>\r\n");
      out.write("        <a class=\"cajas\" href=\"ControlerProducto?Op=Listar\" style=\"text-decoration: none; color: black; ");
      out.print( isProductos ? "background-color: #87ceeb" : "");
      out.write("\">\r\n");
      out.write("            <i class=\"fa-solid fa-bottle-water\"></i>\r\n");
      out.write("            <h1>Productos</h1>\r\n");
      out.write("        </a>\r\n");
      out.write("        <a class=\"cajas\" href=\"ControlerPedido?Op=Listar\" style=\"text-decoration: none; color: black; ");
      out.print( isPedidos ? "background-color: #87ceeb" : "");
      out.write("\">\r\n");
      out.write("            <i class=\"fa-solid fa-cart-shopping\"></i>\r\n");
      out.write("            <h1>Pedidos</h1>\r\n");
      out.write("        </a>\r\n");
      out.write("        <a class=\"cajas\" href=\"ControlerVenta?Op=VerPagina\" style=\"text-decoration: none; color: black; ");
      out.print( isVentas ? "background-color: #87ceeb" : "");
      out.write("\">\r\n");
      out.write("            <i class=\"fa-solid fa-clipboard\"></i>\r\n");
      out.write("            <h1>Ventas</h1>\r\n");
      out.write("        </a>\r\n");
      out.write("        ");
 if (esAdmin) {
      out.write("\r\n");
      out.write("        <a class=\"cajas\" href=\"ControlerUsuario?Op=Listar\" style=\"text-decoration: none; color: black; ");
      out.print( isUsuarios ? "background-color: #87ceeb" : "");
      out.write("\">\r\n");
      out.write("            <i class=\"fa-solid fa-user\"></i>\r\n");
      out.write("            <h1>Usuarios</h1>\r\n");
      out.write("        </a>\r\n");
      out.write("        ");
 } 
      out.write("\r\n");
      out.write("        <a class=\"cajas\" href=\"CerrarSesion\" style=\"text-decoration: none; color: black;\">\r\n");
      out.write("            <i class=\"fa-solid fa-power-off\"></i>\r\n");
      out.write("            <h1>Cerrar Sesion</h1>\r\n");
      out.write("        </a>            \r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("    <div id=\"sidebar2\" class=\"btnDesplegable\" style=\"transform: translateX(-100%); width: 12%; background-color: #f0f0f0\">\r\n");
      out.write("        <div class=\"cajas\" onclick=\"toggleSidebar()\" style=\"cursor: pointer;\">\r\n");
      out.write("            <i class=\"fa-solid fa-bars\"></i>\r\n");
      out.write("        </div>\r\n");
      out.write("        <a class=\"cajas\" href=\"ControlerCliente?Op=Listar\" style=\"flex-direction: row; text-decoration: none; color: black; ");
      out.print( isClientes ? "background-color: #87ceeb" : "");
      out.write("\">\r\n");
      out.write("            <i class=\"fa-solid fa-clipboard\"></i>\r\n");
      out.write("            <h1 style=\"font-size: 3.5vh;\">Clientes</h1>\r\n");
      out.write("        </a>\r\n");
      out.write("        <a class=\"cajas\" href=\"ControlerProducto?Op=Listar\" style=\"flex-direction: row; text-decoration: none; color: black; ");
      out.print( isProductos ? "background-color: #87ceeb" : "");
      out.write("\">\r\n");
      out.write("            <i class=\"fa-solid fa-bottle-water\"></i>\r\n");
      out.write("            <h1 style=\"font-size: 3.5vh;\">Productos</h1>\r\n");
      out.write("        </a>\r\n");
      out.write("        <a class=\"cajas\" href=\"ControlerPedido?Op=Listar\" style=\"flex-direction: row; text-decoration: none; color: black; ");
      out.print( isPedidos ? "background-color: #87ceeb" : "");
      out.write("\">\r\n");
      out.write("            <i class=\"fa-solid fa-cart-shopping\"></i>\r\n");
      out.write("            <h1 style=\"font-size: 3.5vh;\">Pedidos</h1>\r\n");
      out.write("        </a>\r\n");
      out.write("        <a class=\"cajas\" href=\"ControlerVenta?Op=VerPagina\" style=\"flex-direction: row; text-decoration: none; color: black; ");
      out.print( isVentas ? "background-color: #87ceeb" : "");
      out.write("\">\r\n");
      out.write("            <i class=\"fa-solid fa-clipboard\"></i>\r\n");
      out.write("            <h1 style=\"font-size: 3.5vh;\">Ventas</h1>\r\n");
      out.write("        </a>\r\n");
      out.write("        ");
 if (esAdmin) {
      out.write("\r\n");
      out.write("        <a class=\"cajas\" href=\"ControlerUsuario?Op=Listar\" style=\"flex-direction: row; text-decoration: none; color: black; ");
      out.print( isUsuarios ? "background-color: #87ceeb" : "");
      out.write("\">\r\n");
      out.write("            <i class=\"fa-solid fa-user\"></i>\r\n");
      out.write("            <h1 style=\"font-size: 3.5vh;\">Usuarios</h1>\r\n");
      out.write("        </a>\r\n");
      out.write("        ");
 } 
      out.write("\r\n");
      out.write("        <a class=\"cajas\" href=\"CerrarSesion\" style=\"flex-direction: row; text-decoration: none; color: black;\">\r\n");
      out.write("            <i class=\"fa-solid fa-power-off\"></i>\r\n");
      out.write("            <h1 style=\"font-size: 3vh;\">Cerrar Sesion</h1>\r\n");
      out.write("        </a>\r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("    <div class=\"navMasContenido\">\r\n");
      out.write("        <nav class=\"navegador\" style=\"padding-left: 6%; background-color: #ffd700;\">\r\n");
      out.write("            <div class=\"imagen\">\r\n");
      out.write("                <img src=\"https://www.logogenio.es/icons/preview/11175\" alt=\"Logo\">\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"datos\">\r\n");
      out.write("                <h2>Bienvenido </h2>\r\n");
      out.write("                <h1>");
      out.print( XD );
      out.write("</h1>\r\n");
      out.write("            </div>\r\n");
      out.write("        </nav>\r\n");
      out.write("\r\n");
      out.write("               \r\n");
      out.write("   <div class=\"container\">\r\n");
      out.write("        <div class=\"form-container\">\r\n");
      out.write("            <h1>Editar Venta</h1>\r\n");
      out.write("            <form id=\"editVentaForm\" action=\"ControlerVenta\" method=\"post\" onsubmit=\"return validateCantidad()\">\r\n");
      out.write("                <input type=\"hidden\" name=\"Op\" value=\"ActualizarProducto\">\r\n");
      out.write("                <input type=\"hidden\" name=\"indexEditar\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${indexEditar}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\">\r\n");
      out.write("                <div class=\"form-group\">\r\n");
      out.write("                    <label>Código del Producto</label>\r\n");
      out.write("                    <input type=\"text\" name=\"codigoProducto\" class=\"form-control\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${codigoProductoEditar}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" readonly>\r\n");
      out.write("                </div>\r\n");
      out.write("                <div class=\"form-group\">\r\n");
      out.write("                    <label>Nombre del Producto</label>\r\n");
      out.write("                    <input type=\"text\" name=\"nombreProducto\" class=\"form-control\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${nombreProductoEditar}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" readonly>\r\n");
      out.write("                </div>\r\n");
      out.write("                <div class=\"form-group\">\r\n");
      out.write("                    <label>Precio del Producto</label>\r\n");
      out.write("                    <input type=\"text\" name=\"precioProducto\" class=\"form-control\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${precioProductoEditar}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" readonly>\r\n");
      out.write("                </div>\r\n");
      out.write("                <div class=\"form-group\">\r\n");
      out.write("                    <label>Cantidad</label>\r\n");
      out.write("                    <input type=\"number\" name=\"cantidadProducto\" class=\"form-control\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${cantidadProductoEditar}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" required>\r\n");
      out.write("                </div>\r\n");
      out.write("               <div class=\"form-group\">\r\n");
      out.write("                    <label>Stock del Producto</label>\r\n");
      out.write("                    <input type=\"text\" name=\"stockProducto\" class=\"form-control\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${stockProductoEditar}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" readonly>\r\n");
      out.write("                </div>\r\n");
      out.write("\r\n");
      out.write("                <div class=\"d-flex justify-content-between\">\r\n");
      out.write("                    <button type=\"submit\" class=\"btn btn-primary btn-block\">Guardar Cambios</button>\r\n");
      out.write("                    <a href=\"MenuVentas.jsp\" class=\"btn btn-cancelar\">Cancelar</a>\r\n");
      out.write("                </div>\r\n");
      out.write("            </form>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("    \r\n");
      out.write("    <script>\r\n");
      out.write("        function validateCantidad() {\r\n");
      out.write("            var cantidadInput = document.getElementById('cantidadInput');\r\n");
      out.write("            var stockProducto = ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${stockProductoEditar}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("; // Obtén el valor de stock desde tu servidor (usando JSP)\r\n");
      out.write("\r\n");
      out.write("            if (parseInt(cantidadInput.value) > parseInt(stockProducto)) {\r\n");
      out.write("                alert('La cantidad no puede ser mayor al stock disponible.');\r\n");
      out.write("                return false; // Evita que se envíe el formulario\r\n");
      out.write("            }\r\n");
      out.write("            return true; // Permite que se envíe el formulario\r\n");
      out.write("        }\r\n");
      out.write("    </script>\r\n");
      out.write("    ");
      out.write("<div id=\"myModal\" class=\"modal\">\r\n");
      out.write("    <div class=\"modal-content\">\r\n");
      out.write("        <p>Tu sesión expirará en <span id=\"countdown\"></span> segundos.</p>\r\n");
      out.write("    </div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<style>\r\n");
      out.write("    .modal {\r\n");
      out.write("        display: none;\r\n");
      out.write("        position: fixed;\r\n");
      out.write("        z-index: 1;\r\n");
      out.write("        left: 0;\r\n");
      out.write("        top: 0;\r\n");
      out.write("        width: 100%;\r\n");
      out.write("        height: 100%;\r\n");
      out.write("        overflow: auto;\r\n");
      out.write("        background-color: rgb(0,0,0);\r\n");
      out.write("        background-color: rgba(0,0,0,0.4);\r\n");
      out.write("        padding-top: 60px;\r\n");
      out.write("    }\r\n");
      out.write("    .modal-content {\r\n");
      out.write("        background-color: #fefefe;\r\n");
      out.write("        margin: 5% auto;\r\n");
      out.write("        padding: 20px;\r\n");
      out.write("        border: 1px solid #888;\r\n");
      out.write("        width: 80%;\r\n");
      out.write("        text-align: center;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    span{\r\n");
      out.write("        font-size: 3.5vh;\r\n");
      out.write("        margin: 0.5vh;\r\n");
      out.write("    }\r\n");
      out.write("</style>\r\n");
      out.write("\r\n");
      out.write("<script>\r\n");
      out.write("    var totalInactiveTime = 100000; // 10 segundos\r\n");
      out.write("    var warningTime = 5; // 5 segundos antes del logout\r\n");
      out.write("    var time, warningTimer;\r\n");
      out.write("\r\n");
      out.write("    window.onload = function () {\r\n");
      out.write("        resetTimer();\r\n");
      out.write("        document.addEventListener('mousemove', resetTimer, false);\r\n");
      out.write("        document.addEventListener('keypress', resetTimer, false);\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    function startLogoutWarning() {\r\n");
      out.write("        var countdown = warningTime;\r\n");
      out.write("        var modal = document.getElementById(\"myModal\");\r\n");
      out.write("        var countdownElement = document.getElementById(\"countdown\");\r\n");
      out.write("        modal.style.display = \"block\";\r\n");
      out.write("        countdownElement.innerText = countdown;\r\n");
      out.write("\r\n");
      out.write("        warningTimer = setInterval(function () {\r\n");
      out.write("            countdown--;\r\n");
      out.write("            countdownElement.innerText = countdown;\r\n");
      out.write("            if (countdown < 0) {\r\n");
      out.write("                clearInterval(warningTimer);\r\n");
      out.write("                logout();\r\n");
      out.write("            }\r\n");
      out.write("        }, 1000);\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    function logout() {\r\n");
      out.write("        alert(\"Tu sesión ha expirado.\");\r\n");
      out.write("        window.location.href = 'CerrarSesion'; /*Es el controlador que hice para que se cierre */\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    function resetTimer() {\r\n");
      out.write("        clearTimeout(time);\r\n");
      out.write("        clearInterval(warningTimer); // Detener el contador de advertencia si está en curso\r\n");
      out.write("        var modal = document.getElementById(\"myModal\");\r\n");
      out.write("        modal.style.display = \"none\"; // Ocultar el modal si se muestra\r\n");
      out.write("        time = setTimeout(startLogoutWarning, (totalInactiveTime - warningTime) * 1000);\r\n");
      out.write("    }\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    <script src=\"https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js\" integrity=\"sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r\" crossorigin=\"anonymous\"></script>\r\n");
      out.write("    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js\" integrity=\"sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy\" crossorigin=\"anonymous\"></script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
