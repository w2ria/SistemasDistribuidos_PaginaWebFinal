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
        <title>Lista Usuarios</title>
        <link rel="stylesheet" href="resources/css/Style.css">
        <script src="https://kit.fontawesome.com/26a3cc7edf.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    </head>
    <body style="display: flex">    
        <%@ include file="NavBar.jsp" %>
        <div class="navMasContenido">
            <div class="Contenido" style="padding-left: 6%; background-color: #87ceeb; display: flex; justify-content: center; width: 100%;"><!--Poner TODO ACA-->            
                <div class="tabla" style=" width: 97%">
                    <div class="titulo" style="font-size: 10vh; text-align: center">LISTA USUARIOS</div>
                    <a  style="margin: 1vh" href="#" class="Agregar btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" data-id="" data-tipo="" data-raza="" data-nombre="" data-fecha="" data-edad="" data-color="" data-peso="" data-tamano="" data-genero=""><i class="fa-solid fa-user-plus" style="padding: 0px 0.5vh"></i>Agregar</a>
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>

                                    <th scope="col">idUsuario</th>
                                    <th scope="col">Contraseña</th>
                                    <th scope="col">Apellidos</th>
                                    <th scope="col">Nombres</th>
                                    <th scope="col">Imagen</th>
                                    <th scope="col">Direccion</th>
                                    <th scope="col">Dni</th>
                                    <th scope="col">Telefono</th>
                                    <th scope="col">Movil</th>
                                    <th scope="col">En Linea</th>
                                    <th scope="col">Estado</th>

                                    <th scope="col">Editar</th>
                                    <th scope="col">Cambiar estado</th>
                                </tr>
                            </thead>
                            <c:forEach var="campo" items="${Lista}">
                                <tbody>   
                                    <tr>
                                        <td>${campo.id_usuario}</td>
                                        <td>${campo.contraseña}</td>
                                        <td>${campo.apellidos}</td>
                                        <td>${campo.nombres}</td>
                                        <td><img src="${pageContext.request.contextPath}/resources/img/${campo.imagen}" class="imagen-producto" style="width: 100px;height:110px;object-fit: cover;"></td>
                                        <td>${campo.direccion}</td>                                    
                                        <td>${campo.DNI}</td>
                                        <td>${campo.telefono}</td>
                                        <td>${campo.movil}</td>
                                        <td>${campo.enLinea}</td>
                                        <td>${campo.estado}</td>

                                        <td>
                                            <a href="#" class="Actualizar btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" data-idUsuario="${campo.id_usuario}" data-contraseña="${campo.contraseña}" data-ape="${campo.apellidos}" data-nom="${campo.nombres}" data-imagen="${campo.imagen}" data-direc="${campo.direccion}" data-dni="${campo.DNI}" data-telef="${campo.telefono}" data-movil="${campo.movil}"><i class="fas fa-edit"></i>Actualizar</a>
                                        </td>
                                        <td>
                                            <a href="ControlerUsuario?Op=Eliminar&Id=${campo.id_usuario}" class="btn btn-danger" onclick="return confirm('¿Estás seguro de que deseas cambiar el estado del usuario con id: ${campo.id_usuario}?');">
                                                <i class="fas fa-trash-alt"></i> Cambiar estado
                                            </a>
                                        </td>
                                    </tr>        
                                </tbody>
                            </c:forEach>
                        </table>
                    </div>

                </div>

            </div>
        </div>

        <!-- Modal -->
        <form action="ControlerUsuario" method="Post" class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" style="z-index: 1000000" enctype="multipart/form-data">
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
                                <label for="idMascota" class="form-label">ID Usuario</label>
                                <input type="text" class="form-control" id="idUsuario" name="cod" >
                            </div>
                            <div class="mb-3">
                                <label for="idMascota" class="form-label">Contraseña</label>
                                <input type="text" class="form-control" id="contraseña" name="contra" required>
                            </div>
                            <div class="mb-3">
                                <label for="tipoMascota" class="form-label">Apellidos</label>
                                <input type="text" class="form-control" id="apellidos" name="apellidos" required>
                            </div>
                            <div class="mb-3">
                                <label for="raza" class="form-label">Nombres</label>
                                <input type="text" class="form-control" id="nombres"  name="nombres" required>
                            </div>
                            <div class="mb-3">
                                <label for="raza" class="form-label">Imagen</label>
                                <input type="file" class="form-control" id="imagen"  name="imagen">
                                <input type="hidden" name="imagenActual" id="editImagenActual">
                            </div>
                            <div class="mb-3">
                                <label for="nombre" class="form-label">Direccion</label>
                                <input type="text" class="form-control" id="direccion" name="direccion" required>
                            </div>

                            <div class="mb-3">
                                <label for="fechaNacimiento" class="form-label">DNI</label>
                                <input type="number" class="form-control" id="dni" name="dni" step="1" min="1" required>
                            </div>
                            <div class="mb-3">
                                <label for="edad" class="form-label">Telefono</label>
                                <input type="number" class="form-control" id="telefono" name="telefono" step="1" min="1" required>
                            </div>
                            <div class="mb-3">
                                <label for="color" class="form-label">Movil</label>
                                <input type="number" class="form-control" id="movil" name="movil" step="1" min="1" required>
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
                    document.getElementById('idUsuario').value = "";
                    document.getElementById('contraseña').value = "";
                    document.getElementById('apellidos').value = "";
                    document.getElementById('nombres').value = "";
                    document.getElementById('imagen').value = "";
                    document.getElementById('direccion').value = "";
                    document.getElementById('dni').value = "";
                    document.getElementById('telefono').value = "";
                    document.getElementById('movil').value = "";
                });

                // Agregar evento clic a cada enlace Actualizar
                enlacesActualizar.forEach(function (enlace) {
                    enlace.addEventListener("click", function () {
                        inputAgregar.style.display = "none";
                        inputActualizar.style.display = "block";
                        tituloAgregar.style.display = "none";
                        tituloEditar.style.display = "block";

                        // Obtener los datos del cliente del enlace
                        const idUsuario = enlace.getAttribute('data-idUsuario');
                        const contraseña = enlace.getAttribute('data-contraseña');
                        const ape = enlace.getAttribute('data-ape');
                        const nom = enlace.getAttribute('data-nom');
                        const imagen = enlace.getAttribute('data-imagen');
                        const direc = enlace.getAttribute('data-direc');
                        const dni = enlace.getAttribute('data-dni');
                        const telef = enlace.getAttribute('data-telef');
                        const movil = enlace.getAttribute('data-movil');

                        // Asignar los datos a los campos del formulario
                        document.getElementById('idUsuario').value = idUsuario;
                        document.getElementById('contraseña').value = contraseña;
                        document.getElementById('apellidos').value = ape;
                        document.getElementById('nombres').value = nom;
                        document.getElementById('editImagenActual').value = imagen;
                        document.getElementById('direccion').value = direc;
                        document.getElementById('dni').value = dni;
                        document.getElementById('telefono').value = telef;
                        document.getElementById('movil').value = movil;
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
