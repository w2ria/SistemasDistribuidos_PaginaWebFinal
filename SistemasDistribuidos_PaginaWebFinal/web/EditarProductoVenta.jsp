<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ventas</title>
    <script src="https://kit.fontawesome.com/26a3cc7edf.js" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        * {
            margin: 0;
            padding: 0;
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
        
        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .form-container {
            width: 400px;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .form-container h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .btn-primary {
            background-color: #000000;
            border-color: #000000;
            margin-top: 10px;
        }
        .btn-primary:hover {
            background-color: #333333;
            border-color: #333333;
        }
        .btn-cancelar {
            background-color: #000000;
            border-color: #000000;
            color: #ffffff;
            margin-top: 10px;
        }
        .btn-cancelar:hover {
            background-color: #333333;
            border-color: #333333;
        }
    </style>
</head>

<body>
    <%@ include file="NavBar.jsp" %>
               
   <div class="container">
        <div class="form-container">
            <h1>Editar Venta</h1>
            <form id="editVentaForm" action="ControlerVenta" method="post" onsubmit="return validateCantidad()">
                <input type="hidden" name="Op" value="ActualizarProducto">
                <input type="hidden" name="indexEditar" value="${indexEditar}">
                <div class="form-group">
                    <label>Código del Producto</label>
                    <input type="text" name="codigoProducto" class="form-control" value="${codigoProductoEditar}" readonly>
                </div>
                <div class="form-group">
                    <label>Nombre del Producto</label>
                    <input type="text" name="nombreProducto" class="form-control" value="${nombreProductoEditar}" readonly>
                </div>
                <div class="form-group">
                    <label>Precio del Producto</label>
                    <input type="text" name="precioProducto" class="form-control" value="${precioProductoEditar}" readonly>
                </div>
                <div class="form-group">
                    <label>Cantidad</label>
                    <input type="number" name="cantidadProducto" class="form-control" value="${cantidadProductoEditar}" required>
                </div>
               <div class="form-group">
                    <label>Stock del Producto</label>
                    <input type="text" name="stockProducto" class="form-control" value="${stockProductoEditar}" readonly>
                </div>

                <div class="d-flex justify-content-between">
                    <button type="submit" class="btn btn-primary btn-block">Guardar Cambios</button>
                    <a href="MenuVentas.jsp" class="btn btn-cancelar">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
    
    <script>
        function validateCantidad() {
            var cantidadInput = document.getElementById('cantidadInput');
            var stockProducto = ${stockProductoEditar}; // Obtén el valor de stock desde tu servidor (usando JSP)

            if (parseInt(cantidadInput.value) > parseInt(stockProducto)) {
                alert('La cantidad no puede ser mayor al stock disponible.');
                return false; // Evita que se envíe el formulario
            }
            return true; // Permite que se envíe el formulario
        }
    </script>
    <%@ include file="ModalSesionExpirada.jsp" %>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>
