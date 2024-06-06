<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@page import="Entidades.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    List<Cliente> Lista = (List<Cliente>) request.getAttribute("Lista");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://kit.fontawesome.com/26a3cc7edf.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <style>
            * {
                margin: 0px;
                padding: 0px;
            }
            .btnDesplegable {
                height: 100vh;
                width: 6%;
                position: fixed;
                transition: transform 0.5s ease;
                z-index: 1;
            }
            .cajas {
                height: 12vh;
                display: flex;
                justify-content: center;
                align-items: center;
                cursor: pointer;
            }
            .cajas:hover{
                background-color: #87ceeb;
            }
            .cajas:nth-child(n+2) {
                flex-direction: column;
            }
            .cajas:last-child {
                width: 100%;
                position: absolute;
                bottom: 0;
                margin-bottom: 2vh;
            }
            .cajas i {
                font-size: 5vh;
                padding: 0.2vh;
            }
            .cajas h1 {
                font-size: 2.5vh;
                padding: 0.2vh;
                text-align: center;
            }
            .navMasContenido {
                /*width: 94vw;*/
                width: 100%;

                /*margin-left: 6%;*/

            }
            .navegador {
                height: 12vh;

                display: flex;
                justify-content: space-around;
            }
            .imagen {
                width: 12wh;
                height: 100%;
            }
            .imagen img {
                width: 100%;
                height: 100%;
            }
            .datos {
                display: flex;
                align-items: center;
            }

            .Aparecer {
                transform: translateX(0);
            }

        </style>
    </head>
    <%
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        if (session.getAttribute("user") == null) {
            response.sendRedirect("Login.jsp");
        }
        String XD = (String) request.getAttribute("Nombre");
        // Obtener el ID del usuario de la sesión
        String idUsuario = (String) session.getAttribute("Id_Usuario");
        // Verificar si el usuario es administrador
        boolean esAdmin = idUsuario != null && idUsuario.startsWith("A");
    %>

    <body style="display: flex">    
        <div id="sidebar1" class="btnDesplegable" style="background-color: #f0f0f0">
            <form class="cajas" onclick="toggleSidebar()">
                <i class="fa-solid fa-bars"></i>
            </form>
            <a class="cajas" href="ControlerCliente?Op=Listar&idUsuario=<%= idUsuario %>&Nombre=<%= XD %>" style="text-decoration: none; color: black">
                <i class="fa-solid fa-clipboard"></i>
                <h1>Clientes</h1>
            </a>
            <form class="cajas" style="background-color: #87ceeb">
                <i class="fa-solid fa-bottle-water"></i>
                <h1>Productos</h1>
            </form>
            <form class="cajas">
                <i class="fa-solid fa-cart-shopping"></i>
                <h1>Pedidos</h1>
            </form>
            <% if (esAdmin) { %> 
            <a class="cajas" href="ControlerUsuario?Op=Listar&idUsuario=<%= idUsuario %>&Nombre=<%= XD %>" style="text-decoration: none; color: black">
                <i class="fa-solid fa-user"></i>
                <h1>Usuarios</h1>
            </a>
            <% } %>
            <a class="cajas" style="text-decoration: none; color: black" href="CerrarSesion">
                <i class="fa-solid fa-power-off"></i>
                <h1>Cerrar Sesion</h1>
            </a>            
        </div>

        <div id="sidebar2" class="btnDesplegable" style="transform: translateX(-100%); width: 12%; background-color: #f0f0f0">
            <form class="cajas" onclick="toggleSidebar()">
                <i class="fa-solid fa-bars"></i>
            </form>
            <a class="cajas" style="flex-direction: row; text-decoration: none; color: black" href="ControlerCliente?Op=Listar&idUsuario=<%= idUsuario %>&Nombre=<%= XD %>">
                <i class="fa-solid fa-clipboard"></i>
                <h1 style="font-size: 3.5vh;">Clientes</h1>
            </a>
            <form class="cajas"style="flex-direction: row; background-color: #87ceeb">
                <i class="fa-solid fa-bottle-water"></i>
                <h1 style="font-size: 3.5vh;">Productos</h1>
            </form>
            <form class="cajas" style="flex-direction: row">
                <i class="fa-solid fa-cart-shopping"></i>
                <h1 style="font-size: 3.5vh;">Pedidos</h1>
            </form>

            <% if (esAdmin) { %>
            <a class="cajas" style="flex-direction: row; text-decoration: none; color: black" href="ControlerUsuario?Op=Listar&idUsuario=<%= idUsuario %>&Nombre=<%= XD %>">
                <i class="fa-solid fa-user"></i>
                <h1 style="font-size: 3.5vh;">Usuarios</h1>
            </a>
            <% }%>
            <a class="cajas"style="flex-direction: row; text-decoration: none; color: black" href="CerrarSesion">
                <i class="fa-solid fa-power-off"></i>
                <h1 style="font-size: 3vh;">Cerrar Sesion</h1>
            </a>            
        </div>


        <div class="navMasContenido">
            <nav class="navegador" style="padding-left: 6%; background-color: #ffd700">
                <div class="imagen">
                    <img src="https://www.logogenio.es/icons/preview/11175">
                </div>
                <div class="datos">
                    <h2>Bienvenido</h2>
                    <h1><%= XD%></h1>
                </div>
            </nav>
            <div class="Contenido" style="padding-left: 6%; background-color: #87ceeb; display: flex; justify-content: center"><!--Poner TODO ACA-->            
                <div class="tabla" style=" width: 70%">
                    <div class="titulo" style="font-size: 10vh; text-align: center">LISTA PRODUCTOS</div>
                    <a  style="margin: 1vh" href="#" class="Agregar btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" data-id="" data-tipo="" data-raza="" data-nombre="" data-fecha="" data-edad="" data-color="" data-peso="" data-tamano="" data-genero=""><i class="fa-solid fa-user-plus" style="padding: 0px 0.5vh"></i>Agregar</a>
                    <table class="table table-striped">
                        <thead>
                            <tr>

                                <th scope="col">idProducto</th>
                                <th scope="col">Descripcion</th>
                                <th scope="col">Imagen</th>
                                <th scope="col">Costo</th>
                                <th scope="col">Precio</th>
                                <th scope="col">Cantidad</th>
                                <th scope="col">Estado</th>
                                
                                <th scope="col">Editar</th>
                                <th scope="col">Eliminar</th>
                            </tr>
                        </thead>
                        <c:forEach var="campo" items="${Lista}">
                            <tbody>   
                                <tr>
                                    <td>${campo.idProd}</td>
                                    <td>${campo.descripcion}</td>
                                    <td>${campo.imagen}</td>
                                    <td>${campo.costo}</td>
                                    <td>${campo.precio}</td>
                                    <td>${campo.cantidad}</td>
                                    <td>${campo.estado}</td>
                                    
                                    <td>
                                        <a href="#" class="Actualizar btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" data-idProd="${campo.idProd}" data-desc="${campo.descripcion}" data-imag="${campo.imagen}" data-costo="${campo.costo}" data-prec="${campo.precio}" data-cant="${campo.cantidad}"><i class="fas fa-edit"></i>Actualizar</a>
                                    </td>
                                    <td>
                                        <a href="ControlerProducto?Op=Eliminar&Id=${campo.idProd}" class="btn btn-danger" onclick="return confirm('¿Estás seguro de que deseas eliminar este cliente?');">
                                            <i class="fas fa-trash-alt"></i> Eliminar
                                        </a>
                                    </td>
                                </tr>        
                            </tbody>
                        </c:forEach>
                    </table>

                </div>

            </div>
        </div>

        <!-- Modal -->
        <form action="ControlerProducto" method="Post" class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" style="z-index: 1000000"> <!--enctype="multipart/form-data"-->
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="tituloAgregar">Agregar Clientes</h1>
                        <h1 class="modal-title fs-5" id="tituloEditar">Actualizar Clientes</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div id="updateForm">
                            <div class="mb-3">
                                <label for="idMascota" class="form-label">ID Producto</label>
                                <input type="text" class="form-control" id="idProd" name="codProd" readonly="">
                            </div>
                            <div class="mb-3">
                                <label for="tipoMascota" class="form-label">Descripcion</label>
                                <input type="text" class="form-control" id="desc" name="descripcion">
                            </div>
                            <div class="mb-3">
                                <label for="raza" class="form-label">Imagen</label>
                                <input type="text" class="form-control" id="imag"  name="imagen">
                            </div>
                            <div class="mb-3">
                                <label for="nombre" class="form-label">Costo</label>
                                <input type="text" class="form-control" id="costo" name="costo">
                            </div>

                            <div class="mb-3">
                                <label for="fechaNacimiento" class="form-label">Precio</label>
                                <input type="text" class="form-control" id="prec" name="precio">
                            </div>
                            <div class="mb-3">
                                <label for="edad" class="form-label">Cantidad</label>
                                <input type="text" class="form-control" id="cant" name="cantidad">
                            </div>
                            
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>                        
                        <input id="inputActualizar" type="submit" name="Op" value="Actualizar"/>
                        <input id="inputAgregar" type="submit" name="Op" value="Guardar"/>
                    </div>
                </div>
            </div>
        </form>


        <script>

            /*Es para que aparezca o desaparezca los botones de agregar o actualizar p    */
            document.addEventListener("DOMContentLoaded", function () {

                var enlaceAgregar = document.querySelector(".Agregar");
                var enlacesActualizar = document.querySelectorAll(".Actualizar");
                var inputAgregar = document.getElementById("inputAgregar");
                var inputActualizar = document.getElementById("inputActualizar");
                var tituloAgregar = document.getElementById("tituloAgregar");
                var tituloEditar = document.getElementById("tituloEditar");

                //pa ocultar 1ero
                inputActualizar.style.display = "none";
                tituloEditar.style.display = "none";

                // Agregar evento clic al enlace Agregar
                enlaceAgregar.addEventListener("click", function () {
                    inputActualizar.style.display = "none";
                    inputAgregar.style.display = "block";
                    tituloEditar.style.display = "none";
                    tituloAgregar.style.display = "block";
                    // Limpiar los campos del formulario
                    document.getElementById('idProd').value = "";
                    document.getElementById('desc').value = "";
                    document.getElementById('imag').value = "";
                    document.getElementById('costo').value = "";
                    document.getElementById('prec').value = "";
                    document.getElementById('cant').value = "";
                    
                });

                // Agregar evento clic a cada enlace Actualizar
                enlacesActualizar.forEach(function (enlace) {
                    enlace.addEventListener("click", function () {
                        inputAgregar.style.display = "none";
                        inputActualizar.style.display = "block";
                        tituloAgregar.style.display = "none";
                        tituloEditar.style.display = "block";

                        // Obtener los datos del cliente del enlace
                        const idProd = enlace.getAttribute('data-idProd');
                        const desc = enlace.getAttribute('data-desc');
                        const imag = enlace.getAttribute('data-imag');
                        const costo = enlace.getAttribute('data-costo');
                        const prec = enlace.getAttribute('data-prec');
                        const cant = enlace.getAttribute('data-cant');
                        

                        // Asignar los datos a los campos del formulario
                        document.getElementById('idProd').value = idProd;
                        document.getElementById('desc').value = desc;
                        document.getElementById('imag').value = imag;
                        document.getElementById('costo').value = costo;
                        document.getElementById('prec').value = prec;
                        document.getElementById('cant').value = cant;
                        
                    });
                });
            });

            function toggleSidebar() {/*Para el menu*/
                const sidebar1 = document.getElementById('sidebar1');
                const sidebar2 = document.getElementById('sidebar2');
                if (sidebar1.style.transform === 'translateX(-100%)') {
                    sidebar1.style.transform = 'translateX(0)';
                    sidebar2.style.transform = 'translateX(-100%)';
                } else {
                    sidebar1.style.transform = 'translateX(-100%)';
                    sidebar2.style.transform = 'translateX(0)';
                }
            }
        </script>    
        <%@ include file="ModalSesionExpirada.jsp" %>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</html>
