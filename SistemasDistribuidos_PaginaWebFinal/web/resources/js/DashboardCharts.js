document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('txtPedidosValor').innerText = `${datos.txtPedidosValor} Pedidos`;
    document.getElementById('txtPedidosPromedioValor').innerText = `${datos.txtPedidosPromedioValor} Pedidos diarios promedio`;
    document.getElementById('txtProductosValor').innerText = `${datos.txtProductosValor} Productos`;
    document.getElementById('txtProductosVendidosValor').innerText = `${datos.txtProductosVendidosValor} Productos vendidos`;
    document.getElementById('txtClientesValor').innerText = `${datos.txtClientesValor} Clientes`;
    document.getElementById('txtClientesActivosValor').innerText = `${datos.txtClientesActivosValor} Clientes activos`;
    document.getElementById('txtEmpleadosValor').innerText = `${datos.txtEmpleadosValor} Empleados`;
    document.getElementById('txtEmpleadosActivosValor').innerText = `${datos.txtEmpleadosActivosValor} Empleados Activos`;
});

// grafico de ventas 
let ventasChartChart = document.getElementById("ventas-chart").getContext("2d");

let ventasChart = new Chart(ventasChartChart, {
    type: "line",
    data: {
        labels: ventasData['7'].label,
        datasets: [{
                label: "Ventas",
                data: ventasData['7'].data,
                borderWidth: 3,
                borderColor: "#36A2EB"
                        // backgroundColor: ""
            }]
    },
    options: {
        responsive: false,
        maintainAspectRatio: false,
        legend: {
            display: false,
            position: "right",
            labels: {
                fontColor: "#000"
            }
        },
        tooltips: {
            backgroundColor: "#000"
        }
    }
});

const updateVentasChart = (data, labels) => {
    ventasChart.data.labels = labels;
    ventasChart.data.datasets[0].data = data;
    ventasChart.update();
};

document.addEventListener('DOMContentLoaded', function () {
    var newValue = datos.ventas7Valor;
    document.getElementById('ventasValor').innerText = `S/ ${newValue.toLocaleString()}`;
    document.querySelectorAll('#ventasDropdown .dropdown-item').forEach(item => {
        item.addEventListener('click', event => {
            event.preventDefault();
            const days = event.target.getAttribute('data-value');
            let newData, newLabels, newValue;
            ;

            switch (days) {
                case '7':
                    newData = ventasData['7'].data;
                    newLabels = ventasData['7'].label;
                    newValue = datos.ventas7Valor;
                    break;
                case '30':
                    newData = ventasData['30'].data;
                    newLabels = ventasData['30'].label;
                    newValue = datos.ventas30Valor;
                    break;
                case '365':
                    newData = ventasData['365'].data;
                    newLabels = ventasData['365'].label;
                    newValue = datos.ventas365Valor;
                    break;
                default:
                    return;
            }

            document.getElementById('dropdownMenuLinkVentas').innerText = event.target.innerText;
            console.log(`Updating value to: S/ ${newValue.toLocaleString()}`);
            document.getElementById('ventasValor').innerText = `S/ ${newValue.toLocaleString()}`;
            updateVentasChart(newData, newLabels);
        });
    });
});

// grafico de ganancias 
let gananciasChartCtx = document.getElementById("ganancias-chart").getContext("2d");

let gananciasChart = new Chart(gananciasChartCtx, {
    type: "line",
    data: {
        labels: gananciasData['7'].label,
        datasets: [{
                label: "Ganancias",
                data: gananciasData['7'].data,
                borderWidth: 3,
                borderColor: "#fd5d93"
            }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
            display: false,
            position: "right",
            labels: {
                fontColor: "#000"
            }
        },
        tooltips: {
            backgroundColor: "#000"
        }
    }
});

const updateGananciasChart = (data, labels) => {
    gananciasChart.data.labels = labels;
    gananciasChart.data.datasets[0].data = data;
    gananciasChart.update();
};

document.addEventListener('DOMContentLoaded', function () {
    var newValue = datos.ganancias7Valor;
    document.getElementById('gananciasValor').innerText = `S/ ${newValue.toLocaleString()}`;
    document.querySelectorAll('#gananciasDropdown .dropdown-item').forEach(item => {
        item.addEventListener('click', event => {
            event.preventDefault();
            const days = event.target.getAttribute('data-value');
            let newData, newLabels, newValue;

            switch (days) {
                case '7':
                    newData = gananciasData['7'].data;
                    newLabels = gananciasData['7'].label;
                    newValue = datos.ganancias7Valor;
                    break;
                case '30':
                    newData = gananciasData['30'].data;
                    newLabels = gananciasData['30'].label;
                    newValue = datos.ganancias30Valor;
                    break;
                case '365':
                    newData = gananciasData['365'].data;
                    newLabels = gananciasData['365'].label;
                    newValue = datos.ganancias365Valor;
                    break;
                default:
                    return;
            }

            document.getElementById('dropdownMenuLinkGanancias').innerText = event.target.innerText;
            console.log(`Updating value to: S/ ${newValue.toLocaleString()}`);
            document.getElementById('gananciasValor').innerText = `S/ ${newValue.toLocaleString()}`;
            updateGananciasChart(newData, newLabels);
        });
    });
});


// grafico de pedidos 
let pedidosChartChart = document.getElementById("pedidos-chart").getContext("2d");

let pedidosChart = new Chart(pedidosChartChart, {
    type: "pie",
    data: {
        labels: pedidosData.label,
        datasets: [{
                label: 'Cantidad',
                data: pedidosData.data,
                backgroundColor: pedidosData.backgroundColor,
                hoverOffset: 4
            }]
    },
    options: {
        responsive: false,
        maintainAspectRatio: false,
        legend: {
            display: false,
            position: "right",
            labels: {
                fontColor: "#000"
            }
        },
        tooltips: {
            backgroundColor: "#000"
        }
    }
});

const updatePedidosChart = (data, labels, backgroundColors) => {
    pedidosChart.data.labels = labels;
    pedidosChart.data.datasets[0].data = data;
    pedidosChart.data.datasets[0].backgroundColor = backgroundColors;
    pedidosChart.update();
};

let nuevosDatos = {
    labels: pedidosData.label,
    data: pedidosData.data,
    backgroundColors: pedidosData.backgroundColor
};

updatePedidosChart(nuevosDatos.data, nuevosDatos.labels, nuevosDatos.backgroundColors);

// grafico de empleados
let empleadosChartCanvas = document.getElementById("empleados-chart").getContext("2d");

let empleadosChart = new Chart(empleadosChartCanvas, {
    type: "line",
    data: {
        labels: empleadosData['7'].labels,
        datasets: empleadosData['7'].datasets.map(dataset => ({
                label: dataset.label,
                data: dataset.data,
                fill: false
            }))
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
            display: true,
            position: "right",
            labels: {
                fontColor: "#000"
            }
        },
        tooltips: {
            backgroundColor: "#000"
        }
    }
});

function updateEmpleadosChart(newDataSets, newLabels) {
    empleadosChart.data.datasets = newDataSets;
    empleadosChart.data.labels = newLabels;
    empleadosChart.update();
}

document.querySelectorAll('#empleadosDropdown .dropdown-item').forEach(item => {
    item.addEventListener('click', event => {
        event.preventDefault();
        const days = event.target.getAttribute('data-value');
        let newDataSets, newLabels;

        switch (days) {
            case '7':
                newDataSets = empleadosData['7'].datasets.map(dataset => ({
                        label: dataset.label,
                        data: dataset.data
                    }));
                newLabels = empleadosData['7'].labels;
                break;
            case '30':
                newDataSets = empleadosData['30'].datasets.map(dataset => ({
                        label: dataset.label,
                        data: dataset.data
                    }));
                newLabels = empleadosData['30'].labels;
                break;
            case '365':
                newDataSets = empleadosData['365'].datasets.map(dataset => ({
                        label: dataset.label,
                        data: dataset.data
                    }));
                newLabels = empleadosData['365'].labels;
                break;
            default:
                return;
        }

        document.getElementById('dropdownMenuLinkEmpleados').innerText = event.target.innerText;
        updateEmpleadosChart(newDataSets, newLabels);
    });
});
