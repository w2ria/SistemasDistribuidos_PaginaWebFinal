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
        <title>Lista Clientes</title>
        <script src="https://kit.fontawesome.com/26a3cc7edf.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="resources/css/Style.css">
    </head>

    <body style="display: flex">    
        <%String idUsuario4 = (String) request.getAttribute("Id_Usuario");
            System.out.println("Usuario traido hacia jsp" + idUsuario4);%>
        <%@ include file="NavBar.jsp" %>
        <div class="navMasContenido">
            <div class="Contenido" style="padding-left: 6%; background-color: #87ceeb; display: flex; justify-content: center"><!--Poner TODO ACA-->            
                <div class="tabla" style=" width: 70%">
                    <div class="titulo" style="font-size: 10vh; text-align: center">LISTA CLIENTES</div>
                    <a  style="margin: 1vh" href="#" class="Agregar btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" data-id="" data-tipo="" data-raza="" data-nombre="" data-fecha="" data-edad="" data-color="" data-peso="" data-tamano="" data-genero=""><i class="fa-solid fa-user-plus" style="padding: 0px 0.5vh"></i>Agregar</a>
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>

                                    <th scope="col">idCliente </th>
                                    <th scope="col">Apellidos</th>
                                    <th scope="col">Nombres</th>
                                    <th scope="col">Direccion</th>
                                    <th scope="col">Número de documento</th>
                                    <th scope="col">Tipo de Documento</th>
                                    <th scope="col">Telefono</th>
                                    <th scope="col">Movil</th>
                                    <th scope="col">Estado</th>
                                    <th scope="col">En Linea</th>
                                    <th scope="col">Editar</th>
                                    <th scope="col">Cambiar estado</th>
                                </tr>
                            </thead>
                            <c:forEach var="campo" items="${Lista}">
                                <tbody>   
                                    <tr>
                                        <td>${campo.id}</td>
                                        <td>${campo.apellidos}</td>
                                        <td>${campo.nombres}</td>
                                        <td>${campo.direccion}</td>
                                        <td>${campo.numeroDocumento}</td>
                                        <td>${campo.tipoDocumento}</td>
                                        <td>${campo.telefono}</td>
                                        <td>${campo.movil}</td>
                                        <td>${campo.estado}</td>
                                        <td>${campo.enLinea}</td>
                                        <td>
                                            <a href="#" class="Actualizar btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" data-id="${campo.id}" data-ape="${campo.apellidos}" data-nom="${campo.nombres}" data-direc="${campo.direccion}" data-numeroDocumento="${campo.numeroDocumento}" data-tipoDocumento="${campo.tipoDocumento}" data-telef="${campo.telefono}" data-movil="${campo.movil}"><i class="fas fa-edit"></i>Actualizar</a>
                                        </td>
                                        <td>
                                            <a href="ControlerCliente?Op=Eliminar&Id=${campo.id}" class="btn btn-danger" onclick="return confirm('¿Estás seguro de que deseas cambiar el estado del cliente con id: ${campo.id}?');">
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
        <form action="ControlerCliente" method="Post" class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" style="z-index: 1000000"> <!--enctype="multipart/form-data"-->
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
                                <label for="idMascota" class="form-label">ID Cliente</label>
                                <input type="text" class="form-control" id="idCliente" name="cod" readonly="">
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
                                <label for="nombre" class="form-label">Direccion</label>
                                <input type="text" class="form-control" id="direccion" name="direccion" required>
                            </div>

                            <div class="mb-3">
                                <label for="tipoDocumento" class="form-label">Tipo Documento</label>
                                <select class="form-select" id="tipoDocumento" name="tipoDocumento" required>
                                    <option value="" disabled selected>Seleccionar tipo de documento</option>
                                    <option value="DNI">DNI</option>
                                    <option value="RUC">RUC</option>
                                    <option value="Pasaporte">Pasaporte</option>
                                    <option value="Carnet de Extranjería">Carnet de Extranjería</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="numeroDocumento" class="form-label">Número de Documento</label>
                                <input type="text" class="form-control" id="numeroDocumento" name="numeroDocumento" required>
                                <div class="invalid-feedback">
                                    El número de documento no es válido.
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="edad" class="form-label">Telefono</label>
                                <input type="number" class="form-control" id="telefono" name="telefono" step="1" min="1" required>
                            </div>
                            <div class="mb-3">
                                <label for="color" class="form-label">Movil</label>
                                <input type="number" class="form-control" id="movil" name="movil"  step="1" min="1" required>
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
                    document.getElementById('idCliente').value = "";
                    document.getElementById('apellidos').value = "";
                    document.getElementById('nombres').value = "";
                    document.getElementById('direccion').value = "";
                    document.getElementById('numeroDocumento').value = "";
                    document.getElementById('tipoDocumento').value = "";
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
                        const id = enlace.getAttribute('data-id');
                        const ape = enlace.getAttribute('data-ape');
                        const nom = enlace.getAttribute('data-nom');
                        const direc = enlace.getAttribute('data-direc');
                        const num = enlace.getAttribute('data-numeroDocumento');
                        const tipo = enlace.getAttribute('data-tipoDocumento');
                        const telef = enlace.getAttribute('data-telef');
                        const movil = enlace.getAttribute('data-movil');

                        // Asignar los datos a los campos del formulario
                        document.getElementById('idCliente').value = id;
                        document.getElementById('apellidos').value = ape;
                        document.getElementById('nombres').value = nom;
                        document.getElementById('direccion').value = direc;
                        document.getElementById('numeroDocumento').value = num;
                        document.getElementById('tipoDocumento').value = tipo;
                        document.getElementById('telefono').value = telef;
                        document.getElementById('movil').value = movil;
                    });
                });
            });

            document.getElementById('tipoDocumento').addEventListener('change', function () {
                var tipoDocumento = this.value;
                var numeroDocumentoInput = document.getElementById('numeroDocumento');

                switch (tipoDocumento) {
                    case 'DNI':
                        numeroDocumentoInput.setAttribute('maxlength', '8');
                        numeroDocumentoInput.setAttribute('pattern', '\\d{8}');
                        numeroDocumentoInput.setAttribute('title', 'El DNI debe tener 8 dígitos. Ejemplo: 12345678');
                        break;
                    case 'RUC':
                        numeroDocumentoInput.setAttribute('maxlength', '11');
                        numeroDocumentoInput.setAttribute('pattern', '\\d{11}');
                        numeroDocumentoInput.setAttribute('title', 'El RUC debe tener 11 dígitos. Ejemplo: 12345678901');
                        break;
                    case 'Pasaporte':
                        numeroDocumentoInput.setAttribute('maxlength');
                        numeroDocumentoInput.setAttribute('pattern', '[a-zA-Z0-9]{8,9}');
                        numeroDocumentoInput.setAttribute('title', 'El Pasaporte puede tener entre 8 y 9 caracteres alfanuméricos. Ejemplo: A1234567 o AB1234567');
                        break;
                    case 'Carnet de Extranjería':
                        numeroDocumentoInput.setAttribute('maxlength', '9');
                        numeroDocumentoInput.setAttribute('pattern', '[a-zA-Z0-9]{9}');
                        numeroDocumentoInput.setAttribute('title', 'El Carnet de Extranjería debe tener 9 caracteres. Ejemplo: X12345678');
                        break;
                    default:
                        numeroDocumentoInput.removeAttribute('maxlength');
                        numeroDocumentoInput.removeAttribute('pattern');
                        numeroDocumentoInput.removeAttribute('title');
                }

                numeroDocumentoInput.value = ''; // Clear the input field on change
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
