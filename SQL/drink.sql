-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 09, 2018 at 09:33 AM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.2.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `testshop`
--

-- --------------------------------------------------------

--
-- Table structure for table `drink`
--

CREATE TABLE `drink` (
  `ID` int(11) NOT NULL,
  `Name` text NOT NULL,
  `Link` text NOT NULL,
  `Price` float NOT NULL,
  `MenuId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `drink`
--

INSERT INTO `drink` (`ID`, `Name`, `Link`, `Price`, `MenuId`) VALUES
(1, 'Vanilla Frappe', 'https://image.ibb.co/jdjSgK/Vanilla_Frappe.png', 50.00, 5),
(3, 'Maui Mocha Frappe', 'https://image.ibb.co/fXn2Fe/Maui_Mocha.png', 65.00, 5),
(4, 'Matcha Green Tea Frappe', 'https://image.ibb.co/bsTngK/Matcha_Green_Tea_Frappe.png', 70.00, 5),
(5, 'JavaChip Frappe', 'https://image.ibb.co/ctLYMK/JavaChip.png', 75.00, 5),
(6, 'Frozen Chocolate Frappe', 'https://image.ibb.co/hWJL1K/Frozen_Chocolate.png', 50.00, 5),
(7, 'Cookies n Cream Frappe', 'https://image.ibb.co/bKOW8z/Cookies_n_Cream.png', 65.00, 5),
(8, 'Chocolate Mint Mocha Frappe', 'https://image.ibb.co/bJrwae/Chocolate_Mint_Mocha.png', 75.00, 5),
(9, 'Coffee Frappe', 'https://image.ibb.co/e7qpve/Coffee_Frappe.png', 50.00, 5),
(10, 'Caramel Frappe', 'https://image.ibb.co/cnAB8z/Caramel_Frappe.png', 55.00, 5),
(11, 'Charcoal Mocha', 'https://image.ibb.co/jEwjTz/Charcoal_Mocha.png', 80.00, 5),
(12, 'Yakult Lemon Green Tea', 'https://image.ibb.co/ndjvFe/Yakult_Lemon_Green_Tea.png', 50.00, 6),
(13, 'Green Apple Green Tea', 'https://image.ibb.co/icHU8z/Green_Apple_Green_Tea.png', 60.00, 6),
(14, 'Green Tea Macchiato', 'https://image.ibb.co/eeswoz/Green_Tea_Macchiato.png', 70.00, 6),
(15, 'Yakult Green Tea', 'https://image.ibb.co/mXU6MK/Yakult_Green_Tea.png', 45.00, 6),
(16, 'Yakult Apple Jelly Green Tea', 'https://image.ibb.co/kY0fgK/Yakult_Apple_Jelly_Green_Tea.png', 65.00, 6),
(17, 'Fresh Lemon Green Tea', 'https://image.ibb.co/bY6d1K/Fresh_Lemon_Green_Tea.png', 50.00, 6),
(18, 'Fresh Lemon Lime Juice', 'https://image.ibb.co/ibWBMK/Fresh_Lemon_Lime_Juice.png', 55.00, 7),
(19, 'Honey Lemon Juice', 'https://image.ibb.co/k5RNTz/Honey_Lemon_Juice.png', 60.00, 7),
(20, 'Honey with Calamansi Juice', 'https://image.ibb.co/bt3rMK/Honeywith_Calamansi_Juice.png', 55.00, 7),
(21, 'Earl Milk Tea', 'https://image.ibb.co/c2eboz/Earl_Milk_Tea.png', 80.00, 8),
(22, 'Pearl Milk Tea', 'https://image.ibb.co/hQ1NTz/Pearl_Milk_Tea.png', 85.00, 8),
(23, 'Oolong Pearl Milk Tea', 'https://image.ibb.co/dbfGoz/Oolong_Pearl_Milk_Tea.png', 80.00, 8),
(24, 'Assam Black Tea', 'https://image.ibb.co/nkuy1K/Assam_Black_Tea.png', 80.00, 9),
(25, 'Oolong Green Tea', 'https://image.ibb.co/kJskgK/Oolong_Green_Tea.png', 75.00, 9),
(26, 'Peach Oolong Tea', 'https://image.ibb.co/nbZy1K/Peach_Oolong_Tea.png', 70.00, 9),
(27, 'Purple Plum Tea', 'https://image.ibb.co/iP8Boz/Purple_Plum_Tea.png', 90.00, 9),
(28, 'Rose Lychee Black Tea', 'https://image.ibb.co/hnbp8z/Rose_Lychee_Black_Tea.png', 85.00, 9),
(29, 'Chocolate Oreo Smoothie', 'https://image.ibb.co/nOhU8z/Chocolate_Oreo_Smoothie.png', 80.00, 10),
(30, 'Chocolate Snowshake', 'https://image.ibb.co/jgFSve/Chocolate_Snowshake.png', 85.00, 10),
(31, 'Strawberry Snowshake', 'https://image.ibb.co/inxhve/Strawberry_Snowshake.png', 85.00, 10),
(32, 'White Pearls', 'https://image.ibb.co/djT2Tz/white_pearls.png', 10.00, 11),
(33, 'Strawberry Jelly', 'https://image.ibb.co/nb6BMK/strawberry_jelly.png', 10.00, 11),
(34, 'Passion Pop', 'https://image.ibb.co/izHwoz/passion_pop.png', 10.00, 11),
(35, 'Nata de Coco', 'https://image.ibb.co/dh998z/nata_de_coco.png', 10.00, 11),
(36, 'Mini Pearls', 'https://image.ibb.co/kPMd1K/minl_pearls.png', 10.00, 11),
(37, 'Mesona Jelly', 'https://image.ibb.co/m2998z/mesona_jelly.png', 10.00, 11),
(38, 'Golden Pearls', 'https://image.ibb.co/jyvSve/golden_pearls.png', 10.00, 11),
(39, 'Azuki Beans', 'https://image.ibb.co/hYymoz/azuki_beans.png', 10.00, 11),
(40, 'Apple Jelly', 'https://image.ibb.co/cVVcve/apple_jelly.png', 10.00, 11),
(41, 'Aloe Vera', 'https://image.ibb.co/iXOagK/aloe_vera.png', 10.00, 11),
(42, 'Ai Yu Jelly', 'https://image.ibb.co/d0psTz/ai_yu_jelly.png', 10.00, 11),
(43, 'Pearls', 'https://image.ibb.co/nqtVFe/pearls.png', 10.00, 11);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `drink`
--
ALTER TABLE `drink`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `MenuId` (`MenuId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `drink`
--
ALTER TABLE `drink`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
