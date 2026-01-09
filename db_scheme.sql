-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hostiteľ: 127.0.0.1
-- Čas generovania: Pi 09.Jan 2026, 21:47
-- Verzia serveru: 10.4.32-MariaDB
-- Verzia PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databáza: `pizzeria_db`
--
CREATE DATABASE IF NOT EXISTS `pizzeria_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `pizzeria_db`;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
CREATE TABLE `cart_items` (
  `cart_item_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `users_user_id` int(11) NOT NULL,
  `pizza_sizes_pizza_size_id` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `status` varchar(45) NOT NULL,
  `total_price` decimal(10,2) NOT NULL DEFAULT 0.00,
  `delivery_address` varchar(255) NOT NULL,
  `note` text DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `users_user_id` int(11) NOT NULL,
  `cook_user_id` int(11) DEFAULT NULL,
  `courier_user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `pizzas`
--

DROP TABLE IF EXISTS `pizzas`;
CREATE TABLE `pizzas` (
  `pizza_id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `slug` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `category` varchar(30) DEFAULT NULL,
  `tags_csv` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `pizza_sizes`
--

DROP TABLE IF EXISTS `pizza_sizes`;
CREATE TABLE `pizza_sizes` (
  `pizza_size_id` int(11) NOT NULL,
  `size_name` varchar(10) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `diameter_cm` int(11) NOT NULL,
  `pizzas_pizza_id` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `profile_image_url` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT 1,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `users_user_id` int(11) NOT NULL,
  `roles_role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Kľúče pre exportované tabuľky
--

--
-- Indexy pre tabuľku `cart_items`
--
ALTER TABLE `cart_items`
  ADD PRIMARY KEY (`cart_item_id`),
  ADD UNIQUE KEY `uq_cart_unique_item` (`users_user_id`,`pizza_sizes_pizza_size_id`),
  ADD KEY `idx_cart_user` (`users_user_id`),
  ADD KEY `idx_cart_pizza_size` (`pizza_sizes_pizza_size_id`);

--
-- Indexy pre tabuľku `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `idx_orders_user` (`users_user_id`),
  ADD KEY `idx_orders_status` (`status`),
  ADD KEY `idx_orders_cook` (`cook_user_id`),
  ADD KEY `idx_orders_courier` (`courier_user_id`),
  ADD KEY `idx_orders_status_cook` (`status`,`cook_user_id`),
  ADD KEY `idx_orders_status_courier` (`status`,`courier_user_id`);

--
-- Indexy pre tabuľku `pizzas`
--
ALTER TABLE `pizzas`
  ADD PRIMARY KEY (`pizza_id`),
  ADD UNIQUE KEY `uq_pizzas_slug` (`slug`),
  ADD UNIQUE KEY `uq_pizzas_name` (`name`);

--
-- Indexy pre tabuľku `pizza_sizes`
--
ALTER TABLE `pizza_sizes`
  ADD PRIMARY KEY (`pizza_size_id`),
  ADD UNIQUE KEY `uq_pizza_size_unique` (`pizzas_pizza_id`,`size_name`,`diameter_cm`),
  ADD KEY `idx_pizza_sizes_pizza` (`pizzas_pizza_id`);

--
-- Indexy pre tabuľku `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`role_id`),
  ADD UNIQUE KEY `uq_roles_name` (`name`);

--
-- Indexy pre tabuľku `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `uq_users_email` (`email`);

--
-- Indexy pre tabuľku `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`users_user_id`,`roles_role_id`),
  ADD KEY `fk_user_roles_role` (`roles_role_id`);

--
-- AUTO_INCREMENT pre exportované tabuľky
--

--
-- AUTO_INCREMENT pre tabuľku `cart_items`
--
ALTER TABLE `cart_items`
  MODIFY `cart_item_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pre tabuľku `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pre tabuľku `pizzas`
--
ALTER TABLE `pizzas`
  MODIFY `pizza_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pre tabuľku `pizza_sizes`
--
ALTER TABLE `pizza_sizes`
  MODIFY `pizza_size_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pre tabuľku `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pre tabuľku `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Obmedzenie pre exportované tabuľky
--

--
-- Obmedzenie pre tabuľku `cart_items`
--
ALTER TABLE `cart_items`
  ADD CONSTRAINT `fk_cart_pizza_size` FOREIGN KEY (`pizza_sizes_pizza_size_id`) REFERENCES `pizza_sizes` (`pizza_size_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_cart_user` FOREIGN KEY (`users_user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Obmedzenie pre tabuľku `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `fk_orders_cook_user` FOREIGN KEY (`cook_user_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_orders_courier_user` FOREIGN KEY (`courier_user_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_orders_user` FOREIGN KEY (`users_user_id`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE;

--
-- Obmedzenie pre tabuľku `pizza_sizes`
--
ALTER TABLE `pizza_sizes`
  ADD CONSTRAINT `fk_pizza_sizes_pizza` FOREIGN KEY (`pizzas_pizza_id`) REFERENCES `pizzas` (`pizza_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Obmedzenie pre tabuľku `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `fk_user_roles_role` FOREIGN KEY (`roles_role_id`) REFERENCES `roles` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_user_roles_user` FOREIGN KEY (`users_user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
