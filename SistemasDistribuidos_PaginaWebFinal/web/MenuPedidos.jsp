<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="Entidades.Pedido" %>
<%@ page import="Entidades.DetallePedido" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Listado de Pedidos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/26a3cc7edf.js" crossorigin="anonymous"></script>
    <style>
        body {
            min-height: 100vh;
            background-color: #87ceeb;
        }
        .modal-backdrop {
            background-color: rgba(0,0,0,0.5);
        }
        .modal-content {
            background-color: white;
            border-radius: 0.5rem;
            padding: 1rem;
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
        .Aparecer {
            transform: translateX(0);
        }
    </style>
</head>
<body style="display: flex">
    <%@ include file="NavBar.jsp" %>
    <div class="navMasContenido">
        <div class="Contenido" style="padding-left: 6%; background-color: #87ceeb; display: flex; justify-content: center; width: 100%;">
            <div class="tabla" style="width: 70%">
                <div class="titulo" style="font-size: 10vh; text-align: center">LISTA PEDIDOS</div>
                <form action="ControlerPedido" method="post" style="display:inline">
                    <button type="submit" name="Op" value="Exportar" class="btn btn-primary" style="margin: 1vh">
                        <i class="fa-solid fa-file-export" style="padding: 0px 0.5vh"></i> Exportar
                    </button>
                </form>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col">Id_Pedido</th>
                            <th scope="col">Cliente</th>
                            <th scope="col">Usuario</th>
                            <th scope="col">Fecha</th>
                            <th scope="col">SubTotal</th>
                            <th scope="col">TotalVenta</th>
                            <th scope="col">Detalles</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="pedido" items="${pedidos}">
                                <tr>
                                    <td>${pedido.idPedido}</td>
                                    <td>${pedido.idCliente}</td>
                                    <td>${pedido.idUsuario}</td>
                                    <td>${pedido.fecha}</td>
                                    <td>${pedido.subTotal}</td>
                                    <td>${pedido.totalVenta}</td>
                                    <td>
                                        <button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#modalDetalles${pedido.idPedido}">
                                            Ver Detalles
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>

                    </tbody>
                </table>
                
                
                <c:forEach var="pedido" items="${pedidos}">
                    <div class="modal fade" id="modalDetalles${pedido.idPedido}" tabindex="-1" role="dialog" aria-labelledby="modalDetalles${pedido.idPedido}Label" aria-hidden="true" style="z-index: 99999999">
                        <div class="modal-dialog modal-xl" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="modalDetalles${pedido.idPedido}Label">Detalles del Pedido ${pedido.idPedido}</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th scope="col">Id_DetallePedido</th>
                                                <th scope="col">Id_Pedido</th>
                                                <th scope="col">Productos</th>
                                                <th scope="col">Cantidad</th>
                                                <th scope="col">Precio</th>
                                                <th scope="col">TotalDeta</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="detalle" items="${detalles}">
                                                    <c:if test="${detalle.idPedido eq pedido.idPedido}">
                                                        <tr>
                                                            <td>${detalle.idDetallePedido}</td>
                                                            <td>${detalle.idPedido}</td>
                                                            <td>${detalle.descripcion}</td>
                                                            <td>${detalle.cantidad}</td>
                                                            <td>${detalle.precio}</td>
                                                            <td>${detalle.totalDeta}</td>
                                                        </tr>
                                                    </c:if>
                                                </c:forEach>

                                        </tbody>
                                    </table>
                                </div>
                                <div class="modal-footer">
                                    <form action="ControlerPedido" method="post" style="display:inline">
                                        <input type="hidden" name="Id_Pedido" value="${pedido.idPedido}">
                                        <button type="submit" name="Op" value="ExportarDetalles" class="btn btn-primary">
                                            <i class="fa-solid fa-file-export" style="padding: 0px 0.5vh"></i> Exportar
                                        </button>
                                    </form>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <%@ include file="ModalSesionExpirada.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
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
</body>
</html>
