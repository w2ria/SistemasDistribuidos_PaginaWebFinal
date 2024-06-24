<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@page import="Entidades.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
        <script src="https://kit.fontawesome.com/26a3cc7edf.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="resources/css/Style.css">
    </head>

    <body style="display: flex; flex-direction: column; min-height: 100vh;">  
        <%@ include file="NavBar.jsp" %>
        <div class="navMasContenido">
            <div class="Contenido" style="padding-left: 7%;  padding-right: 1%;  padding-top: 1%; padding-bottom: 1%; background-color: #87ceeb; display: flex; justify-content: center; width: 100%;">

                <div class="row row-deck row-cards align-items-stretch justify-content-center">
                    <div class="col-sm-5 col-lg-5 mb-3 d-flex">
                        <div class="card w-100">
                            <div class="card-body">
                                <div class="d-flex align-items-center">
                                    <div class="subheader">Ventas</div>
                                    <div class="ms-auto lh-1">
                                        <div class="dropdown">
                                            <a class="dropdown-toggle text-secondary" href="#" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Últimos 7 días</a>
                                            <div class="dropdown-menu dropdown-menu-end" style="">
                                                <a class="dropdown-item active" href="#">Últimos 7 días</a>
                                                <a class="dropdown-item" href="#">Últimos 30 días</a>
                                                <a class="dropdown-item" href="#">Últimos 365 días</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="h1 mb-3">75%</div>
                                <div class="d-flex mb-2">
                                    <div>Tasa de conversión</div>
                                    <div class="ms-auto">
                                        <span class="text-green d-inline-flex align-items-center lh-1">
                                            7% <!-- Download SVG icon from http://tabler-icons.io/i/trending-up -->
                                            <svg xmlns="http://www.w3.org/2000/svg" class="icon ms-1" width="24" height="24" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round"><path stroke="none" d="M0 0h24v24H0z" fill="none"></path><path d="M3 17l6 -6l4 4l8 -8"></path><path d="M14 7l7 0l0 7"></path></svg>
                                        </span>
                                    </div>
                                </div>
                                <div class="progress progress-sm">
                                    <div class="progress-bar bg-primary" style="width: 75%" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" aria-label="75% Complete">
                                        <span class="visually-hidden">75% Completo</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-5 col-lg-5 mb-3 d-flex">
                        <div class="card w-100">
                            <div class="card-body">
                                <div class="d-flex align-items-center">
                                    <div class="subheader">Ganancias</div>
                                    <div class="ms-auto lh-1">
                                        <div class="dropdown">
                                            <a class="dropdown-toggle text-secondary" href="#" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Últimos 7 días</a>
                                            <div class="dropdown-menu dropdown-menu-end" style="">
                                                <a class="dropdown-item active" href="#">Últimos 7 días</a>
                                                <a class="dropdown-item" href="#">Últimos 30 días</a>
                                                <a class="dropdown-item" href="#">Últimos 365 días</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="d-flex align-items-baseline">
                                    <div class="h1 mb-0 me-2">$4,300</div>
                                    <div class="me-auto">
                                        <span class="text-green d-inline-flex align-items-center lh-1">
                                            8% <!-- Download SVG icon from http://tabler-icons.io/i/trending-up -->
                                            <svg xmlns="http://www.w3.org/2000/svg" class="icon ms-1" width="24" height="24" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round"><path stroke="none" d="M0 0h24v24H0z" fill="none"></path><path d="M3 17l6 -6l4 4l8 -8"></path><path d="M14 7l7 0l0 7"></path></svg>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div id="chart-revenue-bg" class="chart-sm" style="min-height: 40px;"> <!-- aqui va grafico --> </div>
                        </div>
                    </div>
                    <div class="col-10">
                        <div class="row row-cards">
                            <div class="col-sm-5 col-lg-3 mb-3 d-flex">
                                <div class="card card-sm w-100">
                                    <div class="card-body d-flex flex-column justify-content-center align-items-center">
                                        <div class="row align-items-center">
                                            <div class="col-auto">
                                                <i class="fa-solid fa-truck" style="font-size: 35px;"></i>
                                            </div>
                                            <div class="col">
                                                <div class="font-weight-medium">
                                                    132 Pedidos
                                                </div>
                                                <div class="text-secondary">
                                                    12 Pedido completados
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-lg-3 mb-3 d-flex">
                                <div class="card card-sm w-100">
                                    <div class="card-body d-flex flex-column justify-content-center align-items-center">
                                        <div class="row align-items-center">
                                            <div class="col-auto">
                                                <i class="fa-solid fa-basket-shopping" style="font-size: 35px;"></i>
                                            </div>
                                            <div class="col">
                                                <div class="font-weight-medium">
                                                    78 Productos
                                                </div>
                                                <div class="text-secondary">
                                                    32 Productos vendidos
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-lg-3 mb-3 d-flex">
                                <div class="card card-sm w-100">
                                    <div class="card-body d-flex flex-column justify-content-center align-items-center">
                                        <div class="row align-items-center">
                                            <div class="col-auto">
                                                <i class="fa-solid fa-user-group" style="font-size: 35px;"></i>
                                            </div>
                                            <div class="col">
                                                <div class="font-weight-medium">
                                                    623 Clientes
                                                </div>
                                                <div class="text-secondary">
                                                    16 Clientes activos
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-lg-3 mb-3 d-flex">
                                <div class="card card-sm w-100">
                                    <div class="card-body d-flex flex-column justify-content-center align-items-center">
                                        <div class="row align-items-center">
                                            <div class="col-auto">
                                                <i class="fa-solid fa-user-tie" style="font-size: 35px;"></i>
                                            </div>
                                            <div class="col">
                                                <div class="font-weight-medium">
                                                    132 Empleados
                                                </div>
                                                <div class="text-secondary">
                                                    21 Empleados Activos
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-5 style="padding-top: 1%;">
                        <div class="card w-100">
                            <div class="card-body">
                                <h3 class="card-title" style="font-size: 18px;">Seguimiento de Pedidos</h3>

                                <!-- aqui va grafico -->
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-5 style="padding-top: 1%;">
                        <div class="card w-100">
                            <div class="card-body">
                                <h3 class="card-title" style="font-size: 18px;">Empleados con Mayor Número de Ventas</h3>

                                <!-- aqui va grafico -->
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