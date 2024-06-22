-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 06-06-2024 a las 04:50:36
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bd_rest`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_cliente`
--

CREATE TABLE `t_cliente` (
  `Id_Cliente` varchar(6) NOT NULL,
  `Apellidos` varchar(50) DEFAULT NULL,
  `Nombres` varchar(50) DEFAULT NULL,
  `Direccion` varchar(100) DEFAULT NULL,
  `DNI` varchar(8) DEFAULT NULL,
  `Telefono` varchar(10) DEFAULT NULL,
  `Movil` varchar(9) DEFAULT NULL,
  `estado` varchar(11) DEFAULT 'activo',
  `EnLinea` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `t_cliente`
--

INSERT INTO `t_cliente` (`Id_Cliente`, `Apellidos`, `Nombres`, `Direccion`, `DNI`, `Telefono`, `Movil`, `estado`, `EnLinea`) VALUES
('C00001', 'Garcia', 'Carlos', 'Calle Falsa 123', '12345678', '9876543210', '987654321', 'activo', 0),
('C00002', 'Martinez', 'Ana', 'Av. Siempreviva 742', '23456789', '9876543211', '987654322', 'activo', 0),
('C00003', 'Lopez', 'Juan', 'Calle Inventada 456', '34567890', '9876543212', '987654323', 'activo', 0),
('C00004', 'Perez', 'Luisa', 'Av. Ejemplo 789', '45678901', '9876543213', '987654324', 'activo', 0),
('C00005', 'Gomez', 'Maria', 'Calle Prueba 101', '56789012', '9876543214', '987654325', 'activo', 0),
('C00006', 'Sanchez', 'Pedro', 'Av. Modelo 202', '67890123', '9876543215', '987654326', 'activo', 0),
('C00007', 'Romero', 'Laura', 'Calle Ficticia 303', '78901234', '9876543216', '987654327', 'activo', 0),
('C00008', 'Torres', 'Miguel', 'Av. Simulada 404', '89012345', '9876543217', '987654328', 'activo', 0),
('C00009', 'Diaz', 'Lucia', 'Calle Ficción 505', '90123456', '9876543218', '987654329', 'activo', 0),
('C00010', 'Vargas', 'Andres', 'Av. Ensayo 606', '01234567', '9876543219', '987654330', 'activo', 0),
('C00011', 'Ramirez', 'Sofia', 'Calle Test 707', '12345670', '9876543220', '987654331', 'activo', 0),
('C00012', 'Fernandez', 'Luis', 'Av. Prueba 808', '23456781', '9876543221', '987654332', 'activo', 0),
('C00013', 'Mendoza', 'Carmen', 'Calle Ejemplo 909', '34567892', '9876543222', '987654333', 'activo', 0),
('C00014', 'Castro', 'Jose', 'Av. Modelo 010', '45678903', '9876543223', '987654334', 'activo', 0),
('C00015', 'Herrera', 'Diana', 'Calle Muestra 111', '56789014', '9876543224', '987654335', 'activo', 0),
('C00016', 'Ape', 'Nom2', 'Dir', '123', '123', '123', 'inactivo', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_detalle_pedido`
--

CREATE TABLE `t_detalle_pedido` (
  `Id_DetallePedido` varchar(6) NOT NULL,
  `Id_Pedido` varchar(6) DEFAULT NULL,
  `Id_Prod` varchar(6) DEFAULT NULL,
  `cantidad` double(8,2) DEFAULT NULL,
  `precio` double(8,2) DEFAULT NULL,
  `TotalDeta` double(8,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_pedido`
--

CREATE TABLE `t_pedido` (
  `Id_Pedido` varchar(6) NOT NULL,
  `Id_Cliente` varchar(6) DEFAULT NULL,
  `Id_Usuario` varchar(6) DEFAULT NULL,
  `Fecha` date DEFAULT NULL,
  `SubTotal` double(8,2) DEFAULT NULL,
  `TotalVenta` double(8,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_producto`
--

CREATE TABLE `t_producto` (
  `Id_Prod` varchar(6) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  `imagen` varchar(50) DEFAULT NULL,
  `costo` double(8,2) DEFAULT NULL,
  `precio` double(8,2) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `estado` varchar(11) DEFAULT 'activo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `t_producto`
--

INSERT INTO `t_producto` (`Id_Prod`, `Descripcion`, `imagen`, `costo`, `precio`, `cantidad`, `estado`) VALUES
('P00001', 'Arroz', 'imagen1', 1.50, 1.50, 100, 'activo'),
('P00002', 'Azúcar', 'imagen2', 2.00, 2.00, 200, 'activo'),
('P00003', 'Leche', 'imagen3', 1.20, 1.20, 150, 'activo'),
('P00004', 'Aceite', 'imagen4', 3.00, 3.00, 80, 'activo'),
('P00005', 'Pan', 'imagen5', 0.50, 0.50, 300, 'activo'),
('P00006', 'Huevos', 'imagen6', 2.50, 2.50, 200, 'activo'),
('P00007', 'Café', 'imagen7', 4.00, 4.00, 100, 'activo'),
('P00008', 'Té', 'imagen8', 1.50, 1.50, 120, 'activo'),
('P00009', 'Mantequilla', 'imagen9', 3.50, 3.50, 90, 'activo'),
('P00010', 'Queso', 'imagen10', 5.00, 5.00, 60, 'activo'),
('P00011', 'Jabón', 'imagen11', 0.80, 0.80, 200, 'activo'),
('P00012', 'Detergente', 'imagen12', 2.20, 2.20, 150, 'activo'),
('P00013', 'Papel Higiénico', 'imagen13', 3.00, 3.00, 180, 'activo'),
('P00014', 'Fideos', 'imagen14', 1.00, 1.00, 250, 'activo'),
('P00015', 'Galletas', 'imagen15', 1.20, 1.20, 220, 'activo'),
('P00016', 'd', 'i', 1.00, 2.00, 3, 'activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_usuario`
--

CREATE TABLE `t_usuario` (
  `Id_Usuario` varchar(50) NOT NULL,
  `Passwd` varchar(50) DEFAULT NULL,
  `Apellidos` varchar(50) DEFAULT NULL,
  `Nombres` varchar(50) DEFAULT NULL,
  `imagen` varchar(50) DEFAULT NULL,
  `Direccion` varchar(100) DEFAULT NULL,
  `DNI` varchar(8) DEFAULT NULL,
  `Telefono` varchar(10) DEFAULT NULL,
  `Movil` varchar(9) DEFAULT NULL,
  `EnLinea` tinyint(1) DEFAULT 0,
  `Estado` varchar(10) DEFAULT 'activo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `t_usuario`
--

INSERT INTO `t_usuario` (`Id_Usuario`, `Passwd`, `Apellidos`, `Nombres`, `imagen`, `Direccion`, `DNI`, `Telefono`, `Movil`, `EnLinea`, `Estado`) VALUES
('ADaniel', 'XOur79BJ8VDeBFAMLdRVfQ', 'Custodio', 'Daniel', 'imagen1', 'Calle Falsa 123', '12345678', '9876543210', '987654321', 0, 'activo'),
('AGarcia', 'XOur79BJ8VDeBFAMLdRVfQ', 'Garcia Andonaire', 'Javier David ', 'imagen2', 'Av. Siempreviva 742', '23456789', '9876543211', '987654322', 0, 'activo'),
('APrueba', 'QRknobzgkBCt45s3rG8uTQ==', '', '', '', '', '', '', '', 0, 'inactivo'),
('U00001', 'XOur79BJ8VDeBFAMLdRVfQ', 'Sanchez', 'Pedro', 'imagen6', 'Av. Modelo 202', '67890123', '9876543215', '987654326', 0, 'activo'),
('U00002', 'XOur79BJ8VDeBFAMLdRVfQ', 'Romero', 'Laura', 'imagen7', 'Calle Ficticia 303', '78901234', '9876543216', '987654327', 0, 'activo'),
('U00003', 'XOur79BJ8VDeBFAMLdRVfQ', 'Torres', 'Miguel', 'imagen8', 'Av. Simulada 404', '89012345', '9876543217', '987654328', 0, 'activo'),
('U00004', 'XOur79BJ8VDeBFAMLdRVfQ', 'Diaz', 'Lucia', 'imagen9', 'Calle Ficción 505', '90123456', '9876543218', '987654329', 0, 'activo'),
('U00005', 'XOur79BJ8VDeBFAMLdRVfQ', 'Vargas', 'Andres', 'imagen10', 'Av. Ensayo 606', '01234567', '9876543219', '987654330', 0, 'activo');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `t_cliente`
--
ALTER TABLE `t_cliente`
  ADD PRIMARY KEY (`Id_Cliente`);

--
-- Indices de la tabla `t_detalle_pedido`
--
ALTER TABLE `t_detalle_pedido`
  ADD PRIMARY KEY (`Id_DetallePedido`),
  ADD KEY `Id_Pedido` (`Id_Pedido`),
  ADD KEY `Id_Prod` (`Id_Prod`);

--
-- Indices de la tabla `t_pedido`
--
ALTER TABLE `t_pedido`
  ADD PRIMARY KEY (`Id_Pedido`),
  ADD KEY `Id_Cliente` (`Id_Cliente`),
  ADD KEY `Id_Usuario` (`Id_Usuario`);

--
-- Indices de la tabla `t_producto`
--
ALTER TABLE `t_producto`
  ADD PRIMARY KEY (`Id_Prod`);

--
-- Indices de la tabla `t_usuario`
--
ALTER TABLE `t_usuario`
  ADD PRIMARY KEY (`Id_Usuario`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `t_detalle_pedido`
--
ALTER TABLE `t_detalle_pedido`
  ADD CONSTRAINT `t_detalle_pedido_ibfk_1` FOREIGN KEY (`Id_Pedido`) REFERENCES `t_pedido` (`Id_Pedido`),
  ADD CONSTRAINT `t_detalle_pedido_ibfk_2` FOREIGN KEY (`Id_Prod`) REFERENCES `t_producto` (`Id_Prod`);

--
-- Filtros para la tabla `t_pedido`
--
ALTER TABLE `t_pedido`
  ADD CONSTRAINT `t_pedido_ibfk_1` FOREIGN KEY (`Id_Cliente`) REFERENCES `t_cliente` (`Id_Cliente`),
  ADD CONSTRAINT `t_pedido_ibfk_2` FOREIGN KEY (`Id_Usuario`) REFERENCES `t_usuario` (`Id_Usuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
