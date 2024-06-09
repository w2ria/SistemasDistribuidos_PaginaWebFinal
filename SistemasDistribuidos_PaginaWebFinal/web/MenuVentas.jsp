<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ventas</title>
    <script src="https://kit.fontawesome.com/26a3cc7edf.js" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
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
            margin: 20px 0; /* Add margin to separate the tables */
        }
        .titulo {
            font-size: 10vh;
            text-align: center;
            margin-bottom: 20px; /* Add margin to separate the title */
        }
        .content-container {
            display: flex;
            justify-content: space-around; /* Change to space-around for better spacing */
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
            padding: 15px; /* Add padding to table cells for more space */
        }
    </style>
</head>

<body>
    <% String idUsuario4 = (String) request.getAttribute("Id_Usuario");
    System.out.println("Usuario traido hacia jsp: " + idUsuario4); %>
    <%@ include file="NavBar.jsp" %>
    <div class="navMasContenido">
        <div class="Contenido">
            <div class="tabla">
                <div class="titulo">Ventas</div>
                <div class="content-container">
                    <div class="col-sm-4">
                        <div class="card">
                            <form action="" method="Post">
                                <div class="card-body">
                                    <div class="form-group">
                                        <label>Datos del Cliente</label>
                                    </div>
                                    
                                    <div class="form-group row" style="margin:5px">
                                        <div class="col-sm-8" >
                                            <input type="text" name="dni" class="form-control" placeholder="DNI del Cliente">
                                        </div>
                                        <div class="col-sm-4" >
                                            <input type="submit" name="accion" value="Buscar" class="btn btn-outline-info btn-block">
                                        </div>
                                    </div>
                                    <div class="form-group" style="margin:10px">
                                        <label>Datos Productos</label>
                                    </div>
                                    <div class="form-group row" style="margin:5px">
                                        <div class="col-sm-8" >
                                            <input type="text" name="producto" class="form-control" placeholder="Producto">
                                        </div>
                                        <div class="col-sm-4" >
                                            <input type="submit" name="accion" value="Buscar" class="btn btn-outline-info btn-block">
                                        </div>
                                    </div>
                                    <div class="form-group row" style="margin:5px">
                                        <div class="col-sm-6 d-flex">
                                            <input type="text" name="precio" class="form-control" placeholder="S/.0.00">
                                        </div>
                                        <div class="col-sm-3">
                                            <input type="number" name="cant" placeholder="" class="form-control">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <input type="submit" name="accion" value="Agregar" class="btn btn-outline-info">
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="col-sm-7">
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex col-sm-5 ml-auto">
                                    <label>Nro.Serie: </label>
                                    <input type="text" name="" class="form-control">
                                </div>
                                <br>
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th>Nro</th>
                                        <th>Codigo</th>
                                        <th>Descripcion</th>
                                        <th>Precio</th>
                                        <th>Cantidad</th>
                                        <th>SubTotal</th>
                                        <th>Acciones</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="card-footer d-flex">
                                <div class="col-sm-6">
                                    <input type="submit" name="" value="Generar Venta" class="btn btn-success">
                                    <input type="submit" name="" value="Cancelar" class="btn btn-danger">
                                </div>
                                <div class="col-sm-3 ml-auto">
                                    <input type="text" name="" value="" class="form-control">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="ModalSesionExpirada.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>
