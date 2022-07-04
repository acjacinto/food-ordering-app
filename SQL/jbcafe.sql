-- phpMyAdmin SQL Dump
-- version 4.8.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 21, 2018 at 05:32 PM
-- Server version: 10.1.33-MariaDB
-- PHP Version: 7.2.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jbcafe`
--

-- --------------------------------------------------------

--
-- Table structure for table `banner`
--

CREATE TABLE `banner` (
  `ID` int(11) NOT NULL,
  `Name` text NOT NULL,
  `Link` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `banner`
--

INSERT INTO `banner` (`ID`, `Name`, `Link`) VALUES
(2, 'Main Series', 'https://image.ibb.co/mtXerK/bmenumain.png'),
(3, 'Iced Espresso', 'https://image.ibb.co/bUikWK/biced_esp.png'),
(4, 'Hot Espresso', 'https://image.ibb.co/ha2OJz/bhot_esp.png'),
(5, 'Coff-Tea Series', 'https://image.ibb.co/iMkzrK/bcoff_tea.png'),
(6, 'Frappes', 'https://image.ibb.co/mHSqyz/bfrappes.png');

-- --------------------------------------------------------

--
-- Table structure for table `drink`
--

CREATE TABLE `drink` (
  `ID` int(11) NOT NULL,
  `Name` text NOT NULL,
  `Link` text NOT NULL,
  `Price` float NOT NULL,
  `Price2` float NOT NULL,
  `MenuId` int(11) NOT NULL,
  `Description` text NOT NULL,
  `DrinkSizeLink` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `drink`
--

INSERT INTO `drink` (`ID`, `Name`, `Link`, `Price`, `Price2`, `MenuId`, `Description`, `DrinkSizeLink`) VALUES
(1, 'White Mocha Frappe', 'https://image.ibb.co/nrp3mL/White-Mocha-Frappe.png', 130, 145, 5, 'Made with rich milk chocolate flavor and a hint of coffee, our Frappe Mocha is blended with ice and covered with whipped topping and chocolate drizzle.', 'https://image.ibb.co/f5ETrL/Cold-Drink-Size.png'),
(3, 'Chocolate Frappe', 'https://image.ibb.co/dZuEt0/Chocolate-Frappe.png', 125, 140, 5, 'Chip made with an indulgent blend of sweet caramel, chocolate chips, and a hint of coffee. Topped with whipped topping, a chocolate drizzle, and caramel.', 'https://image.ibb.co/f5ETrL/Cold-Drink-Size.png'),
(7, 'Cookies n Cream Frappe', 'https://image.ibb.co/k9yw6L/Cookies-n-Cream.png', 125, 140, 5, 'is a variety of ice cream and milkshake based on flavoring from chocolate cookies. Uses sweet cream ice cream (often vanilla) and chocolate cookies, especially chocolate sandwich cookies, chocolate biscuits with sweet, white creme filling.', 'https://image.ibb.co/f5ETrL/Cold-Drink-Size.png'),
(9, 'Coffee Frappe', 'https://image.ibb.co/jpQnD0/Coffee-Frappe.png', 120, 135, 5, 'covered iced coffee drink made from instant coffee (generally, spray-dried Nescafe), water and sugar.', 'https://image.ibb.co/f5ETrL/Cold-Drink-Size.png'),
(10, 'Mocha Frappe', 'https://image.ibb.co/gRyJLf/Mocha-Frappe.png', 130, 145, 5, 'Made with rich chocolate flavor and a hint of coffee, our Frappe Mocha is blended with ice and covered with whipped topping and chocolate drizzle.', 'https://image.ibb.co/f5ETrL/Cold-Drink-Size.png'),
(16, 'Blue Lemonade Slush', 'https://image.ibb.co/hatAGL/Blue-Lemonade.png', 95, 110, 6, 'Exciting Blue Lemonade Juice. A colorful twist on a familiar favorite, Blue Lemonade adds variety & excitement to any menu making it ideal.', 'https://image.ibb.co/f5ETrL/Cold-Drink-Size.png'),
(17, 'Lemon and Mint Slush', 'https://image.ibb.co/ecejbL/Lemon-n-Mint.png', 95, 110, 6, 'Use fresh lemon and mint, iced, often in combination with other herbs such as spearmint.', 'https://image.ibb.co/f5ETrL/Cold-Drink-Size.png'),
(21, 'JB Milk Tea', 'https://image.ibb.co/nruswL/JBMilk-Tea.png', 85, 100, 8, 'Jelly Bean Original flavored Milktea vary based on the amount of each of these key ingredients, the method of preparation, and the inclusion of other ingredients (varying from sugar or honey to salt or cardamom) Instant milk tea powder is a mass-produced product. ', 'https://image.ibb.co/f5ETrL/Cold-Drink-Size.png'),
(22, 'Almond Roca Milk Tea', 'https://image.ibb.co/k93kGL/Almond-Roca-Tea.png', 90, 105, 8, 'A miltea mixed with a brand of chocolate-covered, almond butter crunch, hard toffee with a coating of ground almonds. It is similar to chocolate-covered English toffee.', 'https://image.ibb.co/f5ETrL/Cold-Drink-Size.png'),
(23, 'Creme Caramel Milk Tea', 'https://image.ibb.co/cAtkGL/Creme-Caramel-Tea.png', 90, 105, 8, 'The thick layer of white, frothy cream can be applied to bubble tea. Various people like to add crema to different types of drinks depending on their taste preference. Crema is most popular in coffee and teas to add a new layer of flavor.', 'https://image.ibb.co/f5ETrL/Cold-Drink-Size.png'),
(24, 'Black', 'https://image.ibb.co/eOjEAf/Black.png', 60, 75, 9, 'Black coffee contains no significant amounts of the macro nutrients, fat, carbohydrate and protein.', 'https://image.ibb.co/n18A5f/Hot-Drink-Size.png'),
(25, 'Cappuccino', 'https://image.ibb.co/hyqnVf/Cappuccino.png', 85, 100, 9, 'A cappuccino is an espresso based coffee drink that originated in Italy, and is traditionally prepared with double espresso and steamed milk foam. ', 'https://image.ibb.co/n18A5f/Hot-Drink-Size.png'),
(26, 'Flat White', 'https://image.ibb.co/e18wO0/Flat-White.png', 90, 100, 9, 'A flat white is a coffee drink consisting of espresso with micro foam (steamed milk with small, fine bubbles and a glossy or velvety consistency). It is similar to the cafe latte, but smaller in volume and with less micro foam, therefore having a higher proportion of coffee to milk, and milk that is more velvety in consistency.', 'https://image.ibb.co/n18A5f/Hot-Drink-Size.png'),
(27, 'Latte', 'https://image.ibb.co/idxCwL/Latte.png', 90, 100, 9, 'A latte is a coffee drink made with espresso and steamed milk.', 'https://image.ibb.co/n18A5f/Hot-Drink-Size.png'),
(28, 'Macchiato', 'https://image.ibb.co/b5CO30/Macchiato.png', 95, 110, 9, 'Cafe sometimes called espresso, is an espresso coffee drink with a small amount of milk, usually foamed.', 'https://image.ibb.co/n18A5f/Hot-Drink-Size.png'),
(29, 'Mocha', 'https://image.ibb.co/ddZEAf/Mocha.png', 105, 120, 9, 'espresso plus milk drinks with chocolate added. Some places will add hot chocolate to the drink, others will add a chocolate syrup.', 'https://image.ibb.co/n18A5f/Hot-Drink-Size.png'),
(30, 'Mochaccino', 'https://image.ibb.co/bz21qf/Mochaccino.png', 100, 115, 9, 'is a chocolate-flavored variant of a cafe latte. Other commonly used spellings are mocha chino and also mocha chino. ', 'https://image.ibb.co/n18A5f/Hot-Drink-Size.png'),
(45, 'Hawaiian Pizza', 'https://image.ibb.co/bEXjAf/Hawaiian.png', 89, 340, 12, 'A pizza topped with tomato sauce, cheese, pineapple, and  ham. Includes peppers, mushrooms, bacon or pepperoni.', ''),
(46, 'All Meat Pizza', 'https://image.ibb.co/iSUaGL/AllMeat.png', 99, 380, 12, 'A pizza with olive oil, pizza sauce, pizza-cheese blend (80 percent part-skim mozzarella, 20 percent Parmesan), shredded, spicy Italian sausage,pepperoni,ham, sliced, bacon, crumbled, pizza cheese blend.', ''),
(47, 'Supreme Pizza', 'https://image.ibb.co/mRi2wL/Supreme.png', 105, 405, 12, 'A pizza consist of pepperoni, rasher bacon, ground beef, Italian sausage, mushroom, pineapple, topped with oregano & fresh sliced spring onion.', ''),
(49, 'Pearl Topping', 'https://image.ibb.co/nrOXVf/pearls.png', 15, 0, 11, '', ''),
(50, 'Jelly Topping', 'https://image.ibb.co/bCjvi0/Jelly.png', 15, 0, 11, '', ''),
(51, 'Floral Jelly (Small)', 'https://image.ibb.co/hzmjff/Floral-Jelly.png', 70, 0, 15, '', ''),
(52, 'Floral Jelly (Large)', 'https://image.ibb.co/iet86L/Floral-Jelly-Large.png', 400, 0, 15, '', ''),
(53, 'Carrot Cake', 'https://image.ibb.co/dCr4Ff/Carrot-cake-slice.png', 110, 1200, 13, 'Carrot Cake with Cream Cheese Icing. By Chelsea Sugar. A popular carrot cake recipe, moist and flavorful with grated carrots and topped with a delicious cream cheese icing. A popular carrot cake recipe, moist and flavorful with grated carrots and topped with a delicious cream cheese icing.', 'https://image.ibb.co/c9ak5f/Carrot-Cake-Size.png'),
(54, 'Chocolate Cake', 'https://image.ibb.co/cdZrvf/Chocolatet-cake-slice.png', 90, 1000, 13, 'Chocolate cake or chocolate  is a cake flavored with melted chocolate, cocoa powder, or both.', 'https://image.ibb.co/hqHXkf/Chocolate-Cake-Size.png'),
(55, 'Blueberry Cheese Cake', 'https://image.ibb.co/b0uPFf/Blueberry-cheese-cake-slice.png', 95, 760, 13, 'A sweet dessert consisting of blueberry at the top one or more layers. The main, and thickest layer, consists of a mixture of soft, fresh cheese, eggs, vanilla and sugar; if there is a bottom layer it often consists of a crust or base made from crushed cookies, graham crackers, pastry, or sponge cake.', 'https://image.ibb.co/mONnJ0/Cheese-Cake-Size-Blueberry.png'),
(56, 'Strawberry Cheese Cake', 'https://image.ibb.co/mZFaML/Strawberry-cheese-cake-slice.png', 90, 720, 13, 'A sweet dessert consisting of strawberry at the top one or more layers. The main, and thickest layer, consists of a mixture of soft, fresh cheese, eggs, vanilla and sugar; if there is a bottom layer it often consists of a crust or base made from crushed cookies, graham crackers, pastry, or sponge cake.', 'https://image.ibb.co/gH7nJ0/Cheese-Cake-Size-Strawberry.png'),
(57, 'Banafire Cupcake', 'https://image.ibb.co/b7Pf5f/Banafire-cupcake.png', 35, 0, 14, 'A cupcake mixed with peanut butter,cashew, and banana topped with melted sugar.', ''),
(58, 'Pumpkin Cupcake', 'https://image.ibb.co/ch6ckf/Pumpkin-Cupcake.png', 25, 0, 14, 'A cupcake mixed with milk and pumpkins.', '');

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `ID` int(11) NOT NULL,
  `Name` text NOT NULL,
  `Link` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`ID`, `Name`, `Link`) VALUES
(5, 'Frappe', 'https://image.ibb.co/nrp3mL/White-Mocha-Frappe.png'),
(6, 'Slush', 'https://image.ibb.co/ecejbL/Lemon-n-Mint.png'),
(8, 'Milk Tea', 'https://image.ibb.co/nruswL/JBMilk-Tea.png'),
(9, 'Hot Drink', 'https://image.ibb.co/bz21qf/Mochaccino.png'),
(11, 'Topping', 'https://image.ibb.co/nrOXVf/pearls.png'),
(12, 'Pizza', 'https://image.ibb.co/mRi2wL/Supreme.png'),
(13, 'Cakes', 'https://image.ibb.co/dCr4Ff/Carrot-cake-slice.png'),
(14, 'Cupcakes', 'https://image.ibb.co/b7Pf5f/Banafire-cupcake.png'),
(15, 'Floral Jelly ', 'https://image.ibb.co/hzmjff/Floral-Jelly.png');

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `OrderId` bigint(20) NOT NULL,
  `OrderDate` datetime NOT NULL,
  `OrderStatus` tinyint(4) NOT NULL,
  `OrderPrice` float NOT NULL,
  `OrderDetail` text NOT NULL,
  `OrderComment` text NOT NULL,
  `OrderAddress` text NOT NULL,
  `UserPhone` text NOT NULL,
  `PaymentMethod` varchar(11) NOT NULL DEFAULT 'Braintree'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`OrderId`, `OrderDate`, `OrderStatus`, `OrderPrice`, `OrderDetail`, `OrderComment`, `OrderAddress`, `UserPhone`, `PaymentMethod`) VALUES
(6, '2018-09-13 01:54:50', 1, 50, '[{\"amount\":1,\"id\":2,\"link\":\"https://image.ibb.co/jdjSgK/Vanilla_Frappe.png\",\"name\":\"Vanilla Frappe\",\"price\":50.0,\"size\":0,\"sugar\":100,\"toppingExtras\":\"\"}]', 'ok ok', 'blk6 lot 5', '+639457723338', 'Braintree'),
(7, '2018-09-13 01:54:50', 2, 80, '[{\"amount\":1,\"id\":1,\"link\":\"https://image.ibb.co/jEwjTz/Charcoal_Mocha.png\",\"name\":\"Charcoal Mocha\",\"price\":80.0,\"size\":0,\"sugar\":100,\"toppingExtras\":\"\"}]', 'hehehhe', 'blk6 lot 5', '+639457723338', 'Braintree'),
(8, '2018-09-13 01:54:50', 3, 120, '[{\"amount\":2,\"id\":2,\"link\":\"https://image.ibb.co/jdjSgK/Vanilla_Frappe.png\",\"name\":\"Vanilla Frappe\",\"price\":120.0,\"size\":0,\"sugar\":100,\"toppingExtras\":\"Strawberry Jelly\\n\"}]', '', 'blk6 lot 5', '+639457723338', 'Braintree'),
(16, '2018-09-13 18:43:55', 1, 140, '[{\"amount\":2,\"id\":1,\"link\":\"https://image.ibb.co/jdjSgK/Vanilla_Frappe.png\",\"name\":\"Vanilla Frappe\",\"price\":140.0,\"size\":1,\"sugar\":25,\"toppingExtras\":\"Passion Pop\\nMini Pearls\\n\"}]', 'Pakibilisan', 'Blk 6 Lot 5', '+639457723338', 'Braintree'),
(17, '2018-09-18 14:38:31', -1, 70, '[{\"amount\":1,\"id\":1,\"link\":\"https://image.ibb.co/jdjSgK/Vanilla_Frappe.png\",\"name\":\"Vanilla Frappe\",\"price\":70.0,\"size\":0,\"sugar\":100,\"toppingExtras\":\"Mini Pearls\\nApple Jelly\\n\"}]', 'Paki bilisan po', 'Earist Cavite Campus', '+639457723338', 'Braintree'),
(93, '2018-10-03 22:47:18', 0, 50, '[{\"amount\":1,\"id\":2,\"link\":\"https://image.ibb.co/ndjvFe/Yakult_Lemon_Green_Tea.png\",\"name\":\"Yakult Lemon Green Tea\",\"price\":50.0,\"size\":0,\"sugar\":100,\"toppingExtras\":\"\"}]', 'this', 'blk 6 lot 5', '+639457723338', 'COD'),
(94, '2018-10-10 17:06:47', 3, 90, '[{\"amount\":1,\"id\":3,\"link\":\"https://image.ibb.co/bsTngK/Matcha_Green_Tea_Frappe.png\",\"name\":\"Matcha Green Tea Frappe\",\"price\":90.0,\"size\":0,\"sugar\":100,\"toppingExtras\":\"Strawberry Jelly\\nNata de Coco\\n\"}]', 'Paki bilisan', 'Blk 6 Lot 5', '+639457723338', 'Braintree'),
(95, '2018-10-13 00:17:07', 0, 50, '[{\"amount\":1,\"id\":5,\"link\":\"https://image.ibb.co/jdjSgK/Vanilla_Frappe.png\",\"name\":\"Vanilla Frappe\",\"price\":50.0,\"size\":0,\"sugar\":100,\"toppingExtras\":\"\"}]', '', 'Blk 6 Lot 5', '+639457723338', 'Braintree'),
(96, '2018-10-14 16:57:08', 0, 115, '[{\"amount\":1,\"id\":6,\"link\":\"https://image.ibb.co/jdjSgK/Vanilla_Frappe.png\",\"name\":\"Vanilla Frappe\",\"price\":50.0,\"size\":0,\"sugar\":100,\"toppingExtras\":\"\"},{\"amount\":1,\"id\":7,\"link\":\"https://image.ibb.co/fXn2Fe/Maui_Mocha.png\",\"name\":\"Maui Mocha Frappe\",\"price\":65.0,\"size\":0,\"sugar\":100,\"toppingExtras\":\"\"}]', '2s', 'Blk 6 Lot 5', '+639457723338', 'Braintree'),
(97, '2018-10-14 21:06:02', 0, 50, '[{\"amount\":1,\"id\":11,\"link\":\"https://image.ibb.co/jdjSgK/Vanilla_Frappe.png\",\"name\":\"Vanilla Frappe\",\"price\":50.0,\"size\":0,\"sugar\":100,\"toppingExtras\":\"\"}]', '14 10 2018', 'blk 6 Lot 5 Poblacion 2 (Area I)', '+639457723338', 'COD'),
(98, '2018-10-19 16:02:17', 1, 100, '[{\"amount\":1,\"id\":13,\"link\":\"https://image.ibb.co/nruswL/JBMilk-Tea.png\",\"name\":\"JB Milk Tea\",\"price\":100.0,\"size\":0,\"sugar\":25,\"toppingExtras\":\"Pearl\\n\"}]', '', 'Blk 6 Lot 5', '+639457723338', 'Braintree');

-- --------------------------------------------------------

--
-- Table structure for table `store`
--

CREATE TABLE `store` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `store`
--

INSERT INTO `store` (`id`, `name`, `lat`, `lng`) VALUES
(1, 'Jelly Bean Cafe', 14.294841, 121.006918);

-- --------------------------------------------------------

--
-- Table structure for table `token`
--

CREATE TABLE `token` (
  `phone` varchar(15) NOT NULL,
  `token` text NOT NULL,
  `isServerToken` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `token`
--

INSERT INTO `token` (`phone`, `token`, `isServerToken`) VALUES
('+639457723338', 'f_hMEGYLcLg:APA91bFm-tFQGBsPJ4dWYenT7oHeeBMqtxMc5beJlbh7ebUFQmJoLdgA4RuwlNAvm_YIGZH4k22h3J8uIE3CTkg4apgx2CSQdmW7eItv7z_DZDJskcCFwGGOvW0dfW2syBcB_W1A3LJO', 0),
('server_app_01', 'dE-OQeXp2RE:APA91bFs8lSHReSa2_jg1Fz8b6BJ4LuizB0T8L0nNmEeGZW0Wmdgeaa2bAg_0XWcOhkl4w_L9j0VGwkafhWXYG-pjF8cLPXce25_4_MkXQv6ThvM7ReLHcwJgf180xlZZeiL0T32E2Ko', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `Phone` varchar(20) NOT NULL,
  `avatarUrl` text NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Birthdate` date NOT NULL,
  `Email` text NOT NULL,
  `Password` text NOT NULL,
  `Address` text NOT NULL,
  `Barangay` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`Phone`, `avatarUrl`, `Name`, `Birthdate`, `Email`, `Password`, `Address`, `Barangay`) VALUES
('+639457723338', '', 'AC Jacinto', '1999-10-11', 'jacintoalydacaye@gmail.com', 'acjacinto', 'Blk 6 Lot 5', 'Poblacion 2 (Area I)');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `banner`
--
ALTER TABLE `banner`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `drink`
--
ALTER TABLE `drink`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `MenuId` (`MenuId`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`OrderId`);

--
-- Indexes for table `store`
--
ALTER TABLE `store`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `token`
--
ALTER TABLE `token`
  ADD PRIMARY KEY (`phone`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`Phone`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `banner`
--
ALTER TABLE `banner`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `drink`
--
ALTER TABLE `drink`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `OrderId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=99;

--
-- AUTO_INCREMENT for table `store`
--
ALTER TABLE `store`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `drink`
--
ALTER TABLE `drink`
  ADD CONSTRAINT `drink_ibfk_1` FOREIGN KEY (`MenuId`) REFERENCES `menu` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
