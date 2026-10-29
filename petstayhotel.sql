-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-10-2026 a las 21:39:17
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `petstayhotel`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `habitacion`
--

CREATE TABLE `habitacion` (
  `Id_Habitacion` int(11) NOT NULL,
  `Numero` varchar(20) DEFAULT NULL,
  `Tipo` varchar(50) DEFAULT NULL,
  `Capacidad` int(11) DEFAULT NULL,
  `Precio_Noche` decimal(10,2) DEFAULT NULL,
  `Estado` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `habitacion`
--

INSERT INTO `habitacion` (`Id_Habitacion`, `Numero`, `Tipo`, `Capacidad`, `Precio_Noche`, `Estado`) VALUES
(1, '101', 'Sencilla', 1, 40000.00, 'Disponible'),
(2, '102', 'Doble', 2, 60000.00, 'Disponible'),
(3, '201', 'Suite', 3, 90000.00, 'Disponible'),
(4, '301', 'Familiar', 4, 120000.00, 'Disponible');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mascota`
--

CREATE TABLE `mascota` (
  `Id_Mascota` int(11) NOT NULL,
  `Id_Usuario` int(11) NOT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `Especie` varchar(50) DEFAULT NULL,
  `Raza` varchar(50) DEFAULT NULL,
  `Edad` int(11) DEFAULT NULL,
  `Peso` decimal(5,2) DEFAULT NULL,
  `Observaciones` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `mascota`
--

INSERT INTO `mascota` (`Id_Mascota`, `Id_Usuario`, `Nombre`, `Especie`, `Raza`, `Edad`, `Peso`, `Observaciones`) VALUES
(1, 1, 'Copito', 'Gato', 'Siames', 8, 5.00, 'Vacunas al día'),
(3, 8, 'Rey', 'Perro', 'Labrador', 10, 20.10, 'Vacunas al día');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
  `Id_Pago` int(11) NOT NULL,
  `Id_Reserva` int(11) NOT NULL,
  `Fecha_Pago` date DEFAULT NULL,
  `Valor` decimal(10,2) DEFAULT NULL,
  `Modelo_Pago` varchar(50) DEFAULT NULL,
  `Estado` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva`
--

CREATE TABLE `reserva` (
  `Id_Reserva` int(11) NOT NULL,
  `Id_Mascota` int(11) NOT NULL,
  `Id_Habitacion` int(11) NOT NULL,
  `Fecha_Ingreso` date DEFAULT NULL,
  `Fecha_Salida` date DEFAULT NULL,
  `Estado` varchar(30) DEFAULT NULL,
  `Total` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reserva`
--

INSERT INTO `reserva` (`Id_Reserva`, `Id_Mascota`, `Id_Habitacion`, `Fecha_Ingreso`, `Fecha_Salida`, `Estado`, `Total`) VALUES
(1, 1, 1, '2026-11-10', '2026-11-13', 'Cancelada', 120000.00),
(4, 1, 1, '2026-11-10', '2026-11-13', 'Reservada', 120000.00),
(5, 3, 1, '2026-11-10', '2026-11-13', 'Reservada', 120000.00),
(6, 3, 1, '2026-11-10', '2026-11-13', 'Cancelada', 120000.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva_servicio`
--

CREATE TABLE `reserva_servicio` (
  `Id_Reserva_Servicio` int(11) NOT NULL,
  `Id_Reserva` int(11) NOT NULL,
  `Id_Servicio` int(11) NOT NULL,
  `Cantidad` int(11) DEFAULT NULL,
  `Precio_Unitario` decimal(10,2) DEFAULT NULL,
  `Subtotal` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reserva_servicio`
--

INSERT INTO `reserva_servicio` (`Id_Reserva_Servicio`, `Id_Reserva`, `Id_Servicio`, `Cantidad`, `Precio_Unitario`, `Subtotal`) VALUES
(1, 1, 1, 2, 25000.00, 50000.00),
(2, 6, 1, 2, 25000.00, 50000.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicio`
--

CREATE TABLE `servicio` (
  `Id_Servicio` int(11) NOT NULL,
  `Nombre` varchar(100) DEFAULT NULL,
  `Descripcion` varchar(255) DEFAULT NULL,
  `Precio` decimal(10,2) DEFAULT NULL,
  `Estado` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `servicio`
--

INSERT INTO `servicio` (`Id_Servicio`, `Nombre`, `Descripcion`, `Precio`, `Estado`) VALUES
(1, 'Baño', 'Baño y secado', 25000.00, 'Activo'),
(2, 'Paseo', 'Paseo de 30 minutos', 15000.00, 'Activo'),
(3, 'Peluquería', 'Corte y cepillado', 35000.00, 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `Id_Usuario` int(11) NOT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `Apellido` varchar(50) DEFAULT NULL,
  `Cedula` varchar(20) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Correo` varchar(100) DEFAULT NULL,
  `Direccion` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`Id_Usuario`, `Nombre`, `Apellido`, `Cedula`, `Telefono`, `Correo`, `Direccion`) VALUES
(1, 'Santiago', 'Garrido', '1111111112', '3052674678', 'santiago@gmail.com', 'calle de testeo'),
(2, 'Pepe', 'Torres', '1234567', '3052674672', 'pepe@gmail.com', 'calle de testeo'),
(8, 'Laura', 'Pérez', '1098765432', '3209998877', 'laura@gmail.com', 'Calle 10 # 3-17');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `habitacion`
--
ALTER TABLE `habitacion`
  ADD PRIMARY KEY (`Id_Habitacion`);

--
-- Indices de la tabla `mascota`
--
ALTER TABLE `mascota`
  ADD PRIMARY KEY (`Id_Mascota`),
  ADD KEY `FK_Mascota_Usuario` (`Id_Usuario`);

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
  ADD PRIMARY KEY (`Id_Pago`),
  ADD KEY `FK_Pago_Reserva` (`Id_Reserva`);

--
-- Indices de la tabla `reserva`
--
ALTER TABLE `reserva`
  ADD PRIMARY KEY (`Id_Reserva`),
  ADD KEY `FK_Reserva_Mascota` (`Id_Mascota`),
  ADD KEY `FK_Reserva_Habitacion` (`Id_Habitacion`);

--
-- Indices de la tabla `reserva_servicio`
--
ALTER TABLE `reserva_servicio`
  ADD PRIMARY KEY (`Id_Reserva_Servicio`),
  ADD KEY `FK_ReservaServicio_Reserva` (`Id_Reserva`),
  ADD KEY `FK_ReservaServicio_Servicio` (`Id_Servicio`);

--
-- Indices de la tabla `servicio`
--
ALTER TABLE `servicio`
  ADD PRIMARY KEY (`Id_Servicio`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`Id_Usuario`),
  ADD UNIQUE KEY `uq_usuario_cedula` (`Cedula`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `habitacion`
--
ALTER TABLE `habitacion`
  MODIFY `Id_Habitacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `mascota`
--
ALTER TABLE `mascota`
  MODIFY `Id_Mascota` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `Id_Pago` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `reserva`
--
ALTER TABLE `reserva`
  MODIFY `Id_Reserva` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `reserva_servicio`
--
ALTER TABLE `reserva_servicio`
  MODIFY `Id_Reserva_Servicio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `servicio`
--
ALTER TABLE `servicio`
  MODIFY `Id_Servicio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `Id_Usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `mascota`
--
ALTER TABLE `mascota`
  ADD CONSTRAINT `FK_Mascota_Usuario` FOREIGN KEY (`Id_Usuario`) REFERENCES `usuario` (`Id_Usuario`);

--
-- Filtros para la tabla `pago`
--
ALTER TABLE `pago`
  ADD CONSTRAINT `FK_Pago_Reserva` FOREIGN KEY (`Id_Reserva`) REFERENCES `reserva` (`Id_Reserva`);

--
-- Filtros para la tabla `reserva`
--
ALTER TABLE `reserva`
  ADD CONSTRAINT `FK_Reserva_Habitacion` FOREIGN KEY (`Id_Habitacion`) REFERENCES `habitacion` (`Id_Habitacion`),
  ADD CONSTRAINT `FK_Reserva_Mascota` FOREIGN KEY (`Id_Mascota`) REFERENCES `mascota` (`Id_Mascota`);

--
-- Filtros para la tabla `reserva_servicio`
--
ALTER TABLE `reserva_servicio`
  ADD CONSTRAINT `FK_ReservaServicio_Reserva` FOREIGN KEY (`Id_Reserva`) REFERENCES `reserva` (`Id_Reserva`),
  ADD CONSTRAINT `FK_ReservaServicio_Servicio` FOREIGN KEY (`Id_Servicio`) REFERENCES `servicio` (`Id_Servicio`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
