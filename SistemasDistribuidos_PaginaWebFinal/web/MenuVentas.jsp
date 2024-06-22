
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ventas</title>
    <script src="https://kit.fontawesome.com/26a3cc7edf.js" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        * {
            margin: 0px;
            padding: 0px;
        }
        body, html {
            height: 100%;
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
        .cajas:hover {
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
            width: 100%;
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
        .Contenido {
            display: flex;
            justify-content: center;
            width: 100%;
            background-color: #87ceeb;
            min-height: 100vh;
        }
        .tabla {
            width: 70%;
            margin: 20px 0; 
        }
        .titulo {
            font-size: 10vh;
            text-align: center;
            margin-bottom: 20px; 
        }
        .content-container {
            display: flex;
            justify-content: space-around;
            width: 100%;
        }
        .card {
            margin-bottom: 20px;
        }
        .card-footer {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .table td{
            padding: 15px; 
        }
    </style>
</head>

<body>
     <%String idUsuario4 = (String) request.getAttribute("Id_Usuario");
     System.out.println("Usuario traido hacia jsp"+idUsuario4);%>
    <%@ include file="NavBar.jsp" %>
    
    <div class="navMasContenido">
        <div class="Contenido">
            <div class="tabla">
                <div class="titulo">Ventas</div>
                <div class="content-container">
                    <div class="col-sm-4">
                        <div class="card">
                            <div class="card-body">
                                <div class="form-group">
                                    <label>Datos del Cliente</label>
                                </div>
                                <form action="ControlerVenta" method="post"> 
                                    <input type="hidden" name="Op" value="BuscarCliente"> 
                                    <div class="form-group row" style="margin:5px">
                                         <div class="col-sm-8" style="margin-top:10px;">
                                            <input type="text" name="dni" class="form-control" placeholder="DNI del Cliente" value="${sessionScope.dniCliente}">
                                        </div>
                                        <div class="col-sm-4">
                                            <input type="submit" name="accion" value="Buscar" class="btn btn-outline-info btn-block">
                                        </div>
                                        
                                        <div class="col-sm-8">
                                            <input type="text" name="nombre" class="form-control" placeholder="Nombre del Cliente" value="${sessionScope.nombreCliente}">
                                        </div>
                                        <div class="col-sm-8" style="margin-top:10px;">
                                            <input type="text" name="apellidos" class="form-control" placeholder="Apellidos del Cliente" value="${sessionScope.apellidosCliente}">
                                        </div>
                                       <div class="col-sm-4" style="margin-top:10px;">
                                            <button type="button" class="btn btn-outline-secondary btn-block" onclick="limpiarCliente()" style="font-size: 95%;">Limpiar</button>
                                        </div>

                                       
                                    </div>
                                </form>
                                        <!-- Modal Cliente no Registrado-->
                                    <c:if test="${not empty requestScope.mensajeClienteNoRegistrado}">
                                        <div class="modal_cli" id="clienteNoRegistradoModal" style="display: block;">
                                            <div class="modal-content">
                                                <span class="close">&times;</span>
                                                <p>${requestScope.mensajeClienteNoRegistrado}</p>
                                            </div>
                                        </div>
                                    </c:if>

                                
                                <div class="form-group">
                                    <label>Buscar Producto</label>
                                </div>
                                <form action="ControlerVenta" method="post" id="formBuscarProducto">
                                    <input type="hidden" name="Op" value="BuscarProducto"> 
                                    <div class="form-group row" style="margin:5px">
                                        <div class="col-sm-8">
                                            <input type="text" id="nombreProductoInput" name="nombreProducto" class="form-control" placeholder="Nombre del Producto" autocomplete="off">
                                            <select id="nombreProductoSelect" name="nombreProductoSelect" class="form-control" style="display: none;">
                                                <!-- Las opciones serán añadidas dinámicamente por JavaScript -->
                                            </select>

                                        </div>

                                        <div class="col-sm-4">
                                            <input type="submit" name="accion" value="Buscar" class="btn btn-outline-info btn-block">
                                        </div>
                                    </div>
                                </form>
                                        
                                        

                                <!-- Modal Producto no Registrado-->
                                    <c:if test="${not empty requestScope.mensajeProductoNoRegistrado}">
                                        <div class="modal_cli" id="ProductoNoRegistradoModal" style="display: block;">
                                            <div class="modal-content">
                                                <span class="close">&times;</span>
                                                <p>${requestScope.mensajeProductoNoRegistrado}</p>
                                            </div>
                                        </div>
                                    </c:if>
                                
                                <%-- para mostar el producto seleccionado para agregar a la venta --%>
                                <c:if test="${not empty nombreProducto}">
                                    <div class="form-group">
                                        <label>Datos del Producto</label>
                                    </div>
                                    <form action="ControlerVenta" method="post" onsubmit="return validarCantidadProducto()">
                                        <input type="hidden" name="Op" value="AgregarProducto">
                                        <input type="hidden" name="codigoProducto" value="${codigoProducto}">
                                        <input type="hidden" name="nombreProducto" value="${nombreProducto}">
                                        <input type="hidden" name="precioProducto" value="${precioProducto}">
                                        <input type="hidden" name="stockProducto" value="${stockProducto}">
                                        <div class="form-group row" style="margin:5px">
                                            <div class="col-sm-8">
                                                <input type="text" id="codigo" name="codigoProducto" class="form-control" placeholder="Código del Producto" value="${codigoProducto}">
                                            </div>
                                            <div class="col-sm-8">
                                                <input type="text" id="precio" name="precioProducto" class="form-control" placeholder="Precio del Producto" value="${precioProducto}">
                                            </div>
                                            <div class="col-sm-3">
                                                <input type="number" id="cant" value="1" name="cantidadProducto" placeholder="" class="form-control" style="width: 80px;" onchange="actualizarStock()">
                                            </div>
                                            <div class="col-sm-8" style="margin-top:10px;">
                                                <input type="text" id="stock" name="stockProducto" class="form-control" placeholder="Stock del Producto" value="${stockProducto}" readonly>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <button type="submit" class="btn btn-outline-primary">Agregar Producto</button>
                                        </div>
                                    </form>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-7">
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex col-sm-5 ml-auto">
                                    <label>Nro.Serie Anterior: </label>
                                    <input type="text" name="" class="form-control" value="${sessionScope.idPedido}" readonly>
                                </div>

                                <br>
                                <table id="cuerpoTabla" class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>Nro</th>
                                            <th>Codigo</th>
                                            <th>Nombre</th>
                                            <th>Precio</th>
                                            <th>Cantidad</th>
                                            <th>Importe</th>
                                            <th>Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="venta" items="${sessionScope.listaVentas}">
                                            <tr>
                                                <td>${venta.index}</td>
                                                <td>${venta.codigoProducto}</td>
                                                <td>${venta.nombreProducto}</td>
                                                <td>${venta.precioProducto}</td>
                                                <td>${venta.cantidadProducto}</td>
                                                <td>${venta.totalDeta}</td>
                                               <td>
                                                    <!-- Enlaces para editar y eliminar productos -->
                                                    <a href="ControlerVenta?Op=EditarProducto&index=${venta.index}" class="btn btn-info btn-sm">Editar</a>
                                                    <a href="ControlerVenta?Op=EliminarProducto&index=${venta.index}" class="btn btn-danger btn-sm">Eliminar</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-sm-6 ml-auto">
                                <div class="form-group d-flex">
                                    <label class="mr-4">SubTotal: </label>
                                    <input type="text" name="subtotalCompra" id="subtotalCompra" class="form-control form-control-sm" style="width: 100px;" value="S/. ${subtotal}">
                                </div>
                                <div class="form-group d-flex">
                                    <label class="mr-4">IGV 18%:</label>
                                    <input type="text" name="IGV" id="IGV" class="form-control form-control-sm" style="width: 100px;" value="S/. ${igv}">
                                </div>
                            </div>
                            <div class="card-footer d-flex">
                                <form action="ControlerVenta" method="post" onsubmit="return validarFormulario()">
                                    <input type="hidden" name="Op" value="GenerarVenta">
                                    <input type="submit" value="Generar Venta" class="btn btn-success">
                                </form>

                                <form action="ControlerVenta" method="post" style="margin-left: 10px;">
                                    <input type="hidden" name="Op" value="CancelarVenta">
                                    <input type="submit" value="Cancelar" class="btn btn-danger">
                                </form>
                                <div class="col-sm-3 ml-auto">
                                    <input type="text" name="totalCompra" id="totalCompra" class="form-control" value="S/. ${totalCompra}0">
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
        <div id="ventaGuardadaModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="cerrarModal('ventaGuardadaModal')">&times;</span>
        <p>Venta generada correctamente.</p>
    </div>
</div>


       <!-- Modal Campos Vacíos-->
        <div class="modal" id="camposVaciosModal">
    <div class="modal-content">
        <span class="close" onclick="cerrarModal('camposVaciosModal')">&times;</span>
        <p>Por favor, complete todos los campos antes de registrar la venta.</p>
    </div>
</div>


         <!-- Modal Sin Productos -->
    <c:if test="${not empty requestScope.mensajeSinProductos}">
        <div id="productosVaciosModal" class="modal" style="display: block;">
            <div class="modal-content">
                <span class="close" onclick="cerrarModal('productosVaciosModal')">&times;</span>
                <p>${requestScope.mensajeSinProductos}</p>
            </div>
        </div>
    </c:if>
         

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        
        function actualizarStock() {
            const cantidadElement = document.getElementById('cant');
            const stockElement = document.getElementById('stock');
            const stockOriginal = parseInt(stockElement.defaultValue);
            const cantidad = parseInt(cantidadElement.value);

            if (!isNaN(stockOriginal) && !isNaN(cantidad)) {
                const nuevoStock = stockOriginal - cantidad;
                stockElement.value = nuevoStock < 0 ? 0 : nuevoStock;
            }
        }

        // Validar que la cantidad no exceda el stock disponible
        function validarCantidadProducto() {
            const cantidadElement = document.getElementById('cant');
            const stockElement = document.getElementById('stock');
            const cantidad = parseInt(cantidadElement.value);
            const stock = parseInt(stockElement.value);

            if (cantidad > stock) {
                alert('La cantidad no puede exceder el stock disponible.');
                return false;
            }
            return true;
        }
        
        
  

        document.addEventListener("DOMContentLoaded", function() {
            $(document).ready(function() {
                $('#nombreProductoInput').on('input', function() {
                    var term = $(this).val();
                    $.ajax({
                        type: 'GET',
                        url: 'ControlerVenta?Op=BuscarProductosAutocompletado&term=' + term,
                        dataType: 'json',
                        success: function(data) {
                            $('#nombreProductoSelect').empty();
                            if (data.length > 0) {
                                // Mostrar el select si hay resultados
                                $('#nombreProductoSelect').show();
                                // Agregar una opción por defecto para indicar selección
                                $('#nombreProductoSelect').append('<option value="">Selecciona un producto...</option>');
                                $.each(data, function(index, value) {
                                    $('#nombreProductoSelect').append('<option value="' + value + '">' + value + '</option>');
                                });
                            } else {
                                // Ocultar el select si no hay resultados
                                $('#nombreProductoSelect').hide();
                                $('#nombreProductoSelect').append('<option value="">No se encontraron productos</option>');
                            }
                        }
                    });
                });

                // Manejar el evento change del select
                $('#nombreProductoSelect').on('change', function() {
                    var selectedOption = $(this).val();
                    $('#nombreProductoInput').val(selectedOption);
                });
            });
        });

        
        
        
        document.addEventListener("DOMContentLoaded", function() {
            // Mostrar modal de cliente no registrado si está presente
            if ("${not empty requestScope.mensajeClienteNoRegistrado}") {
                document.getElementById("clienteNoRegistradoModal").style.display = 'block';
                setTimeout(function() {
                    document.getElementById("clienteNoRegistradoModal").style.display = 'none';
                }, 5000);
            }

            
        });
        
        document.addEventListener("DOMContentLoaded", function() {
            
            // Mostrar modal de productos no encontrados
            if ("${not empty requestScope.mensajeProductoNoRegistrado}") {
                document.getElementById("ProductoNoRegistradoModal").style.display = 'block';
                setTimeout(function() {
                    document.getElementById("ProductoNoRegistradoModal").style.display = 'none';
                }, 5000);
            }
        });
        
        document.addEventListener("DOMContentLoaded", function() {
            
            // Mostrar modal sin productos
            if ("${not empty requestScope.mensajeSinProductos}") {
                document.getElementById("productosVaciosModal").style.display = 'block';
                setTimeout(function() {
                    document.getElementById("productosVaciosModal").style.display = 'none';
                }, 5000);
            }
        });
        
        document.addEventListener("DOMContentLoaded", function() {
            document.getElementById("camposVaciosModal");
            setTimeout(function() {
                 document.getElementById("camposVaciosModal").style.display = 'none';
            }, 5000); // 5000 milisegundos = 5 segundos
        });
        
       

        function cerrarModal(idModal) {
            var modal = document.getElementById(idModal);
            modal.style.display = "none";
        }

        function validarFormulario() {
            if (${sessionScope.listaVentas == null || sessionScope.listaVentas.isEmpty()}) {
                document.getElementById("productosVaciosModal").style.display = 'block';
                return false; // Evitar el envío del formulario
            }
            return true;
        }
    
        document.addEventListener("DOMContentLoaded", function() {
          const modal = document.getElementById("ventaGuardadaModal");
          const span = document.getElementsByClassName("close")[0];

          // Mostrar el modal 
          if (window.location.search.includes("ventaGuardada=true")) {
            modal.style.display = "block";
            setTimeout(function() {
                modal.style.display = "none";

                window.location.href = "MenuVentas.jsp";
            }, 5000); 
          }

        // Cerrar el modal con la X
        span.onclick = function() {
          modal.style.display = "none";
          window.location.href = "MenuVentas.jsp"; 
        }

        // Cerrar el modal con clic fuera del modal
        window.onclick = function(event) {
          if (event.target == modal) {
            modal.style.display = "none";
            window.location.href = "MenuVentas.jsp"; 
          }
        }
    });

    function validarCantidadProducto() {
    var cantidad = parseInt(document.getElementsByName('cantidadProducto')[0].value);
    var stock = parseInt("${stockProducto}");

    if (cantidad > stock) {
        alert("La cantidad que desea comprar es mayor al stock disponible (" + stock + ")");
        return false; // Evitar que se envíe el formulario
    }
    return true; // Permitir el envío del formulario si todo está correcto
}


    function validarFormulario() {
        // Verificar si los campos obligatorios están vacíos
        var dni = document.getElementsByName('dni')[0].value;
        var nombre = document.getElementsByName('nombre')[0].value;
        var apellidos = document.getElementsByName('apellidos')[0].value;


        if (dni === '' || nombre === '' || apellidos === '' ) {
            // Mostrar el modal de campos vacíos
            document.getElementById('camposVaciosModal').style.display = 'block';
            return false; // Evitar que se envíe el formulario
        }
        return true; // Permitir el envío del formulario si todo está completo 
    }
    
    function cerrarModal(idModal) {
        var modal = document.getElementById(idModal);
        modal.style.display = "none";
        window.location.href = "MenuVentas.jsp"; // Redireccionar a menu ventas
    }
    
      function limpiarCliente() {
        $.ajax({
            url: 'ControlerVenta',
            type: 'POST',
            data: {
                Op: 'LimpiarCliente'
            },
            success: function(response) {
                
                document.getElementsByName('dni')[0].value = '';
                document.getElementsByName('nombre')[0].value = '';
                document.getElementsByName('apellidos')[0].value = '';
                
            }
        });
    }
    
</script>
<script>
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

<!-- CSS Modal Venta Generada -->
<style>
.modal {
  display: none; 
  position: fixed; 
  z-index: 1; 
  left: 0;
  top: 0;
  width: 100%; 
  height: 100%; 
  overflow: auto; 
  background-color: rgba(0, 0, 0, 0.5); /* Fondo transparente negro */
}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto; /* Centrado verticalmente */
  padding: 20px;
  border: 1px solid #888;
  width: 50%; 
  height: 10%;
  position: relative; /* Para posicionar el X */
}

.close {
  color: #aaa;
  position: absolute; 
  top: 10px;
  right: 10px;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}


 /* css para modal */
    .modal_cli {
        display: none;
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgb(0,0,0);
        background-color: rgba(0,0,0,0.4);
    }

    .modal_cli-content {
        background-color: #fefefe;
        margin: 15% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 30%;
        text-align: center;
    }

    .close_cli {
        color: #aaaaaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
    }

    .close_cli:hover,
    .close_cli:focus {
        color: #000;
        text-decoration: none;
        cursor: pointer;
    }

</style>
                
</body>

    <%@ include file="ModalSesionExpirada.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
    
</html>
