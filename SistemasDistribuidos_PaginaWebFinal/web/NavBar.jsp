<%
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

    // Determinar la página activa
    String currentPage = request.getRequestURI();
    boolean isClientes = currentPage.contains("MenuClientes.jsp");
    boolean isProductos = currentPage.contains("MenuProductos.jsp");
    boolean isPedidos = currentPage.contains("MenuPedidos.jsp");
    boolean isUsuarios = currentPage.contains("MenuUsuarios.jsp");
    boolean isVentas = currentPage.contains("MenuVentas.jsp");
%>
<body style="display: flex"> 
    <div id="sidebar1" class="btnDesplegable" style="background-color: #f0f0f0">
        <div class="cajas" onclick="toggleSidebar()" style="cursor: pointer;">
            <i class="fa-solid fa-bars"></i>
        </div>
        <a class="cajas" href="ControlerCliente?Op=Listar" style="text-decoration: none; color: black; <%= isClientes ? "background-color: #87ceeb" : ""%>">
            <i class="fa-solid fa-clipboard"></i>
            <h1>Clientes</h1>
        </a>
        <a class="cajas" href="ControlerProducto?Op=Listar" style="text-decoration: none; color: black; <%= isProductos ? "background-color: #87ceeb" : ""%>">
            <i class="fa-solid fa-bottle-water"></i>
            <h1>Productos</h1>
        </a>
        <a class="cajas" href="ControlerPedido?Op=Listar" style="text-decoration: none; color: black; <%= isPedidos ? "background-color: #87ceeb" : ""%>">
            <i class="fa-solid fa-cart-shopping"></i>
            <h1>Pedidos</h1>
        </a>
        <a class="cajas" href="MenuVentas.jsp" style="text-decoration: none; color: black; <%= isVentas ? "background-color: #87ceeb" : ""%>">
            <i class="fa-solid fa-clipboard"></i>
            <h1>Ventas</h1>
        </a>
        <% if (esAdmin) {%>
        <a class="cajas" href="ControlerUsuario?Op=Listar" style="text-decoration: none; color: black; <%= isUsuarios ? "background-color: #87ceeb" : ""%>">
            <i class="fa-solid fa-user"></i>
            <h1>Usuarios</h1>
        </a>
        <% } %>
        <a class="cajas" href="CerrarSesion" style="text-decoration: none; color: black;">
            <i class="fa-solid fa-power-off"></i>
            <h1>Cerrar Sesion</h1>
        </a>            
    </div>

    <div id="sidebar2" class="btnDesplegable" style="transform: translateX(-100%); width: 12%; background-color: #f0f0f0">
        <div class="cajas" onclick="toggleSidebar()" style="cursor: pointer;">
            <i class="fa-solid fa-bars"></i>
        </div>
        <a class="cajas" href="ControlerCliente?Op=Listar" style="flex-direction: row; text-decoration: none; color: black; <%= isClientes ? "background-color: #87ceeb" : ""%>">
            <i class="fa-solid fa-clipboard"></i>
            <h1 style="font-size: 3.5vh;">Clientes</h1>
        </a>
        <a class="cajas" href="ControlerProducto?Op=Listar" style="flex-direction: row; text-decoration: none; color: black; <%= isProductos ? "background-color: #87ceeb" : ""%>">
            <i class="fa-solid fa-bottle-water"></i>
            <h1 style="font-size: 3.5vh;">Productos</h1>
        </a>
        <a class="cajas" href="ControlerPedido?Op=Listar" style="flex-direction: row; text-decoration: none; color: black; <%= isPedidos ? "background-color: #87ceeb" : ""%>">
            <i class="fa-solid fa-cart-shopping"></i>
            <h1 style="font-size: 3.5vh;">Pedidos</h1>
        </a>
        <a class="cajas" href="MenuVentas.jsp" style="flex-direction: row; text-decoration: none; color: black;<%= isVentas ? "background-color: #87ceeb" : ""%>">
            <i class="fa-solid fa-clipboard"></i>
            <h1 style="font-size: 3.5vh;">Ventas</h1>
        </a>
        <% if (esAdmin) {%>
        <a class="cajas" href="ControlerUsuario?Op=Listar" style="flex-direction: row; text-decoration: none; color: black; <%= isUsuarios ? "background-color: #87ceeb" : ""%>">
            <i class="fa-solid fa-user"></i>
            <h1 style="font-size: 3.5vh;">Usuarios</h1>
        </a>
        <% } %>
        <a class="cajas" href="CerrarSesion" style="flex-direction: row; text-decoration: none; color: black;">
            <i class="fa-solid fa-power-off"></i>
            <h1 style="font-size: 3vh;">Cerrar Sesion</h1>
        </a>
    </div>

    <div class="navMasContenido">
        <nav class="navegador" style="padding-left: 6%; background-color: #ffd700;">
            <div class="imagen">
                <img src="https://www.logogenio.es/icons/preview/11175" alt="Logo">
            </div>
            <div class="datos">
                <h2>Bienvenido </h2>
                <h1><%= XD %></h1>
            </div>
        </nav>
