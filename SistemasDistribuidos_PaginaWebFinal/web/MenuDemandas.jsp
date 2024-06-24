<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="Entidades.ProductosDemandados" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Productos Más y Menos Comprados</title>
   
    <script src="https://kit.fontawesome.com/26a3cc7edf.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
      <style>
        body {
            min-height: 100vh;
            background-color: #ffffff; 
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
         /* centrar el form */
        .formulario-centro {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100%;
        }


        label {
            margin-bottom: 5px;
            font-weight: bold;
            color: #333333;
        }

        input[type="date"],
        input[type="number"],
        button {
            width: 100%;
            padding: 10px;
            border: 1px solid #cccccc;
            border-radius: 5px;
            font-size: 16px;
        }

        button {
            background-color: #8767e0;
            color: #ffffff;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #000000;
        }
       
        /* form groupss */
        .form-group {
            width: 21%; /* tamaño de cda form-group */
            margin-bottom: 1%;  /* espacio de separacion con la fila del bpton */
            margin-left: 3.45%;
            margin-right: 5%;
        }

        .form-group.full-width {
            width: 20%; 
            margin: 0 auto; /* centar el botón */
           
        }

        /* Ajustar el formulario */
        #formProductos {
            display: flex;
            flex-wrap: wrap;
            justify-content: center; /* Centra el contenido del formulario */
            width: 80%;  
        }
          /* Contenedor de gráficos */
        .graficos-contenedor {
            display: flex;
            justify-content: space-around; 
            align-items: center; 
            width: 80%;
            margin: auto;
            margin-top: 1%; /* Espacio arriba de los gráficos */
        }

        .grafico {
            flex: 1; 
            margin: 0 2%; /* Margen horizontal espacio entre cada grafico*/
        }
    </style>
</head>
<body style="display: flex">
    <%@ include file="NavBar.jsp" %> 
   <div class="navMasContenido">
        <div class="Contenido" style="padding-left: 6%; background-color: #ffffff; ; display: flex; justify-content: center; ">
            <div class="container" style="background-color: #ffffff; width: 100%; height: 100%;">
                <h1 style="text-align: center;">Demanda De Productos</h1>
                
                <div class="formulario-centro">
                    <form action="ControlerDemanda" method="post" id="formProductos" style="display: flex; flex-wrap: wrap; gap: 10px;">
                        <div class="form-group">
                            <label for="fechaInicio">Fecha de Inicio:</label>
                            <input type="date" id="fechaInicio" name="fechaInicio" required>
                        </div>

                        <div class="form-group">
                            <label for="fechaFin">Fecha de Fin:</label>
                            <input type="date" id="fechaFin" name="fechaFin" required>
                        </div>

                        <div class="form-group">
                            <label for="cantidadMostrar">Cantidad de Productos:</label>
                            <input type="number" id="cantidadMostrar" name="cantidadProductos" min="1" value="1" required>
                        </div>

                        <div class="form-group full-width">
                            <button type="submit" name="Op" value="generarGraficos" class="btn btn-primary">Mostrar</button>
                            <button type="button" id="btnLimpiar" class="btn btn-secondary" onclick="limpiarFormulario()">Limpiar</button>
                        </div>
                       

                    </form>
                </div>

                <br>
                <div class="graficos-contenedor">
                    <div class="grafico">
                        <div class="titulo-mas" style="display: none;">
                            <h2 style="text-align: center;">Productos Más Comprados</h2>
                        </div>
                        <canvas id="graficoProductosMasComprados"></canvas>
                    </div>
                    <div class="grafico">
                        <div class="titulo-menos" style="display: none;">
                            <h2 style="text-align: center;">Productos Menos Comprados</h2>
                        </div>
                        <canvas id="graficoProductosMenosComprados"></canvas>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <%@ include file="ModalSesionExpirada.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script>
        
        function limpiarFormulario() {
            // Limpiar los campos del formulario
            document.getElementById('fechaInicio').value = '';
            document.getElementById('fechaFin').value = '';
            document.getElementById('cantidadMostrar').value = '1';

            // Ocultar los títulos de los gráficos
            document.querySelector('.titulo-mas').style.display = 'none';
            document.querySelector('.titulo-menos').style.display = 'none';

            // para limpiar los datos de los gráficos
            var ctxMas = document.getElementById('graficoProductosMasComprados').getContext('2d');
            var ctxMenos = document.getElementById('graficoProductosMenosComprados').getContext('2d');

            // Limpiar los datos de los gráficos
            ctxMas.data.labels = [];
            ctxMas.data.datasets = [];
            ctxMas.update(); // para actualizar el gráfico

            ctxMenos.data.labels = [];
            ctxMenos.data.datasets = [];
            ctxMenos.update();
        }


       document.addEventListener('DOMContentLoaded', function() {
            var form = document.getElementById('formProductos');
            form.addEventListener('submit', function(event) {
                event.preventDefault();

                var fechaInicio = document.getElementById('fechaInicio').value;
                var fechaFin = document.getElementById('fechaFin').value;
                var cantidadMostrar = document.getElementById('cantidadMostrar').value;

                console.log("Datos enviados al servidor:", {
                    fechaInicio: fechaInicio,
                    fechaFin: fechaFin,
                    cantidadProductos: cantidadMostrar
                });

                // para hacer la solicitud Ajax al controler
                $.ajax({
                    type: 'POST',
                    url: 'ControlerDemanda',
                    cache: false,
                    data: {
                        Op: 'generarGraficos',
                        fechaInicio: fechaInicio,
                        fechaFin: fechaFin,
                        cantidadProductos: cantidadMostrar
                    },
                    dataType: 'json',
                    success: function(data) {
                        console.log("Datos recibidos del servidor:", data);

                       
                        var datosMasVendidos = data.masVendidos;
                        var ctxMas = document.getElementById('graficoProductosMasComprados').getContext('2d');
                        var labelsMas = [];
                        var valoresMas = [];

                        for (var i = 0; i < datosMasVendidos.length; i++) {
                            labelsMas.push(datosMasVendidos[i].nombreProducto);
                            valoresMas.push(datosMasVendidos[i].cantidadComprada);
                        }

                        console.log("Labels para gráfico de productos más vendidos:", labelsMas);
                        console.log("Valores para gráfico de productos más vendidos:", valoresMas);

                        // Crear el gráfico de productos más vendidos si no existe
                        if (!window.graficoMasComprados) {
                            window.graficoMasComprados = new Chart(ctxMas, {
                                type: 'bar',
                                data: {
                                    labels: labelsMas,
                                    datasets: [{
                                        label: 'Cantidad Comprada',
                                        data: valoresMas,
                                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                                        borderColor: 'rgba(54, 162, 235, 1)',
                                        borderWidth: 1
                                    }]
                                },
                                options: {
                                    scales: {
                                        y: {
                                            beginAtZero: true
                                        }
                                    }
                                }
                            });
                        } else {
                            // Actualizar el gráfico de productos más vendidos
                            window.graficoMasComprados.data.labels = labelsMas;
                            window.graficoMasComprados.data.datasets[0].data = valoresMas;
                            window.graficoMasComprados.update();
                        }

                        // Muestra el titurlo deProdcutos Mas vendidos
                        document.querySelector('.titulo-mas').style.display = 'block';

                        
                        var datosMenosVendidos = data.menosVendidos;
                        var ctxMenos = document.getElementById('graficoProductosMenosComprados').getContext('2d');
                        var labelsMenos = [];
                        var valoresMenos = [];

                        for (var i = 0; i < datosMenosVendidos.length; i++) {
                            labelsMenos.push(datosMenosVendidos[i].nombreProducto);
                            valoresMenos.push(datosMenosVendidos[i].cantidadComprada);
                        }

                        console.log("Labels para gráfico de productos menos vendidos:", labelsMenos);
                        console.log("Valores para gráfico de productos menos vendidos:", valoresMenos);

                        // Crear el gráfico de productos menos vendidos si no existe
                        if (!window.graficoMenosComprados) {
                            window.graficoMenosComprados = new Chart(ctxMenos, {
                                type: 'bar',
                                data: {
                                    labels: labelsMenos,
                                    datasets: [{
                                        label: 'Cantidad Comprada',
                                        data: valoresMenos,
                                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                                        borderColor: 'rgba(255, 99, 132, 1)',
                                        borderWidth: 1
                                    }]
                                },
                                options: {
                                    scales: {
                                        y: {
                                            beginAtZero: true
                                        }
                                    }
                                }
                            });
                        } else {
                            // para actualizar el gráfico de productos menos vendidos
                            window.graficoMenosComprados.data.labels = labelsMenos;
                            window.graficoMenosComprados.data.datasets[0].data = valoresMenos;
                            window.graficoMenosComprados.update();
                        }

                        // Muestra el titurlo
                        document.querySelector('.titulo-menos').style.display = 'block';
                    },
                    error: function(xhr, status, error) {
                        console.error('Error al obtener datos del servidor:', error);
                    }
                });
            });
        });


    </script>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>