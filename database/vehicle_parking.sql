-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 01, 2025 at 02:03 PM
-- Server version: 5.7.36
-- PHP Version: 8.1.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vehicle_parking`
--

-- --------------------------------------------------------

--
-- Table structure for table `parking`
--

DROP TABLE IF EXISTS `parking`;
CREATE TABLE IF NOT EXISTS `parking` (
  `slot_number` int(11) NOT NULL,
  `vehicle_number` varchar(50) DEFAULT NULL,
  `vehicle_type` varchar(50) DEFAULT NULL,
  `entry_time` datetime DEFAULT NULL,
  `is_available` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`slot_number`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `parking`
--

INSERT INTO `parking` (`slot_number`, `vehicle_number`, `vehicle_type`, `entry_time`, `is_available`) VALUES
(1, 'BJG 9866', 'Bike', '2025-01-01 00:59:58', 0),
(2, NULL, NULL, NULL, 1),
(3, NULL, NULL, NULL, 1),
(4, NULL, NULL, NULL, 1),
(5, NULL, NULL, NULL, 1),
(6, NULL, NULL, NULL, 1),
(7, NULL, NULL, NULL, 1),
(8, NULL, NULL, NULL, 1),
(9, NULL, NULL, NULL, 1),
(10, NULL, NULL, NULL, 1),
(11, NULL, NULL, NULL, 1),
(12, NULL, NULL, NULL, 1),
(13, NULL, NULL, NULL, 1),
(14, NULL, NULL, NULL, 1),
(15, NULL, NULL, NULL, 1),
(16, NULL, NULL, NULL, 1),
(17, NULL, NULL, NULL, 1),
(18, NULL, NULL, NULL, 1),
(19, NULL, NULL, NULL, 1),
(20, NULL, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `parkinghistory`
--

DROP TABLE IF EXISTS `parkinghistory`;
CREATE TABLE IF NOT EXISTS `parkinghistory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vehicle_number` varchar(50) NOT NULL,
  `vehicle_type` varchar(50) DEFAULT NULL,
  `slot_number` int(11) NOT NULL,
  `entry_time` datetime DEFAULT NULL,
  `exit_time` datetime NOT NULL,
  `fee` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `parkinghistory`
--

INSERT INTO `parkinghistory` (`id`, `vehicle_number`, `vehicle_type`, `slot_number`, `entry_time`, `exit_time`, `fee`) VALUES
(1, 'asd', 'Car', 1, '2024-12-22 13:23:24', '2024-12-22 13:36:51', 2.5),
(2, 'asdf', 'Car', 4, '2024-12-22 13:12:09', '2024-12-22 13:36:58', 2.5),
(3, 'asdf', NULL, 4, NULL, '2024-12-22 13:48:10', 2.5),
(4, 'hj56', NULL, 1, NULL, '2024-12-22 14:05:13', 2.5),
(5, '', NULL, 1, NULL, '2024-12-22 14:06:58', 1),
(6, 'BJG 123', NULL, 1, NULL, '2024-12-22 14:08:27', 1),
(7, 'AAN 123', 'Truck', 2, '2024-12-22 14:07:47', '2024-12-22 14:24:09', 5),
(8, '', 'Car', 1, '2024-12-22 14:30:50', '2024-12-22 16:10:30', 2.5),
(9, 'BJG9866', 'Bike', 6, '2024-12-31 18:35:58', '2024-12-31 18:37:53', 1),
(10, 'as', 'Car', 2, '2024-12-22 14:33:30', '2025-01-01 00:48:12', 565),
(11, '123', 'Car', 1, '2025-01-01 00:48:20', '2025-01-01 00:59:47', 2.5);

-- --------------------------------------------------------

--
-- Table structure for table `rates`
--

DROP TABLE IF EXISTS `rates`;
CREATE TABLE IF NOT EXISTS `rates` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vehicle_type` varchar(20) NOT NULL,
  `rate_per_hour` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rates`
--

INSERT INTO `rates` (`id`, `vehicle_type`, `rate_per_hour`) VALUES
(1, 'Car', '2.50'),
(2, 'Bike', '1.00'),
(3, 'Truck', '5.00');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`) VALUES
(1, 'ishan', '123');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
