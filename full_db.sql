-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hostiteľ: 127.0.0.1
-- Čas generovania: Pi 09.Jan 2026, 21:41
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

--
-- Sťahujem dáta pre tabuľku `cart_items`
--

INSERT INTO `cart_items` (`cart_item_id`, `quantity`, `users_user_id`, `pizza_sizes_pizza_size_id`, `created_at`, `updated_at`) VALUES
(19, 1, 1, 3, '2026-01-09 21:06:04', '2026-01-09 21:06:04');

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

--
-- Sťahujem dáta pre tabuľku `orders`
--

INSERT INTO `orders` (`order_id`, `status`, `total_price`, `delivery_address`, `note`, `created_at`, `updated_at`, `users_user_id`, `cook_user_id`, `courier_user_id`) VALUES
(1, 'doručené', 19.80, 'Hlavná 1, Bratislava', 'Prosím bez cibule.', '2026-01-06 09:12:35', '2026-01-07 09:25:50', 1, 2, 3),
(2, 'doručené', 21.60, 'Hlavná 1, Bratislava', NULL, '2026-01-06 09:12:35', '2026-01-07 09:26:06', 1, 2, 3),
(6, 'doručené', 79.20, 'Jozinova ulica 13/4', 'Zvonček nefunguje XD', '2026-01-07 09:39:48', '2026-01-07 09:41:56', 1, 2, 3),
(14, 'doručené', 11.90, 'Československej armady 55/5', '', '2026-01-09 19:56:46', '2026-01-09 20:13:19', 4, 2, 3),
(15, 'čaká_na_potvrdenie', 10.20, 'Bratislavska ulica 14/7', '', '2026-01-09 20:12:06', '2026-01-09 20:12:06', 2, NULL, NULL);

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

--
-- Sťahujem dáta pre tabuľku `pizzas`
--

INSERT INTO `pizzas` (`pizza_id`, `name`, `slug`, `description`, `image_url`, `active`, `created_at`, `updated_at`, `category`, `tags_csv`) VALUES
(1, 'Capricciosa', 'capricciosa', 'Šunka, šampiňóny, olivy.', '/images/pizzas/capricciosa.webp', 1, '2026-01-06 09:12:35', '2026-01-07 13:58:53', 'CLASSIC', 'italian,cheese,ham,mushroom'),
(2, 'Carbonara pizza', 'carbonara-pizza', 'Smotanový základ, slanina, syr, vajce (podľa receptu).', '/images/pizzas/carbonara-pizza.webp', 1, '2026-01-06 09:12:35', '2026-01-07 13:58:53', 'CLASSIC', 'italian,bacon,egg,cream'),
(3, 'Delicatezza rustica', 'delicatezza-rustica', 'Rustikálna pizza so syrom a vybranými ingredienciami.', '/images/pizzas/delicatezza-rustica.webp', 1, '2026-01-06 09:12:35', '2026-01-07 13:58:53', 'PREMIUM', 'premium,cheese,prosciutto'),
(4, 'Diavola piccante', 'diavola-piccante', 'Pikantná saláma a feferónka.', '/images/pizzas/diavola-piccante.webp', 1, '2026-01-06 09:12:35', '2026-01-07 13:58:53', 'SPICY', 'spicy,salami,chili'),
(5, 'Funghi', 'funghi', 'Paradajkový základ, mozzarella, šampiňóny.', '/images/pizzas/funghi.webp', 1, '2026-01-06 09:12:35', '2026-01-07 13:58:53', 'CLASSIC', 'mushroom,cheese'),
(6, 'Funghi al Panna', 'funghi-al-panna', 'Smotanový základ, mozzarella, šampiňóny.', '/images/pizzas/funghi-al-panna.webp', 1, '2026-01-06 09:12:35', '2026-01-07 13:58:53', 'PREMIUM', 'premium,mushroom,cream'),
(7, 'Gluten free Primavera', 'gluten-free-primavera', 'Bezlepková pizza so zeleninou (primavera).', '/images/pizzas/gluten-free-primavera.webp', 1, '2026-01-06 09:12:35', '2026-01-07 13:58:53', 'VEGGIE', 'veggie,gluten-free,vegetables'),
(8, 'Hawaii classic', 'hawaii-classic', 'Šunka, kukurica (ananás voliteľne v appke).', '/images/pizzas/hawaii-classic.webp', 1, '2026-01-06 09:12:35', '2026-01-07 13:58:53', 'OTHER', 'pineapple,ham,sweet'),
(9, 'La Crema Bianca', 'la-crema-bianca', 'Biely (smotanový) základ, syr, jemná chuť.', '/images/pizzas/la-crema-bianca.webp', 1, '2026-01-06 09:12:35', '2026-01-07 13:58:53', 'PREMIUM', 'premium,white-sauce,cheese'),
(10, 'Margherita classica', 'margherita-classica', 'Paradajkový základ, mozzarella, bazalka.', '/images/pizzas/margherita-classica.webp', 1, '2026-01-06 09:12:35', '2026-01-07 13:58:53', 'CLASSIC', 'classic,tomato,basil,cheese');

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

--
-- Sťahujem dáta pre tabuľku `pizza_sizes`
--

INSERT INTO `pizza_sizes` (`pizza_size_id`, `size_name`, `price`, `diameter_cm`, `pizzas_pizza_id`, `created_at`, `updated_at`) VALUES
(1, 'S', 7.90, 32, 1, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(2, 'M', 9.90, 40, 1, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(3, 'L', 11.90, 50, 1, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(4, 'S', 8.30, 32, 2, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(5, 'M', 10.30, 40, 2, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(6, 'L', 12.30, 50, 2, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(7, 'S', 8.20, 32, 3, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(8, 'M', 10.20, 40, 3, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(9, 'L', 12.20, 50, 3, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(10, 'S', 8.50, 32, 4, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(11, 'M', 10.50, 40, 4, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(12, 'L', 12.50, 50, 4, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(13, 'S', 7.70, 32, 5, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(14, 'M', 9.70, 40, 5, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(15, 'L', 11.70, 50, 5, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(16, 'S', 7.90, 32, 6, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(17, 'M', 9.90, 40, 6, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(18, 'L', 11.90, 50, 6, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(19, 'S', 9.10, 32, 7, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(20, 'M', 11.10, 40, 7, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(21, 'L', 13.10, 50, 7, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(22, 'S', 8.40, 32, 8, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(23, 'M', 10.40, 40, 8, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(24, 'L', 12.40, 50, 8, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(25, 'S', 8.10, 32, 9, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(26, 'M', 10.10, 40, 9, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(27, 'L', 12.10, 50, 9, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(28, 'S', 7.50, 32, 10, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(29, 'M', 9.50, 40, 10, '2026-01-06 09:12:35', '2026-01-06 09:12:35'),
(30, 'L', 11.50, 50, 10, '2026-01-06 09:12:35', '2026-01-06 09:12:35');

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

--
-- Sťahujem dáta pre tabuľku `roles`
--

INSERT INTO `roles` (`role_id`, `name`, `description`) VALUES
(1, 'ROLE_CUSTOMER', 'Zákazník'),
(2, 'ROLE_COOK', 'Kuchár / manažér'),
(3, 'ROLE_COURIER', 'Kuriér'),
(4, 'ROLE_ADMIN', 'Admin / majiteľ');

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

--
-- Sťahujem dáta pre tabuľku `users`
--

INSERT INTO `users` (`user_id`, `email`, `password`, `first_name`, `last_name`, `phone_number`, `address`, `profile_image_url`, `enabled`, `created_at`, `updated_at`) VALUES
(1, 'zakaznik@demo.sk', '$2a$10$PEUkVE3PRZXVpJfGGEY7Cug4DCThxMzZChIQrFdUABBjoG.s0yhF.', 'Jánko', 'Novák', '+421900111222', 'Hlavná 1, Bratislava', 'https://picsum.photos/seed/user1/300', 1, '2026-01-06 09:12:34', '2026-01-09 19:53:58'),
(2, 'kuchar@demo.sk', '$2a$10$NKcx/Uq6drM9Vbm3DmxsQuIX6PgLPJlDLine0lws.PPj/mgH1xNDm', 'Peter', 'Kováč', '+421900222333', 'Kuchyňa 10, Bratislava', 'https://picsum.photos/seed/user2/300', 1, '2026-01-06 09:12:34', '2026-01-09 19:54:01'),
(3, 'kurier@demo.sk', '$2a$10$ob9e06oPFoGBRxONM.jScuySDY5.lgpLNtGUfQAs7RlmlYLYM2b9G', 'Marek', 'Horváth', '+421900333444', 'Depo 5, Bratislava', 'https://picsum.photos/seed/user3/300', 1, '2026-01-06 09:12:34', '2026-01-06 09:12:34'),
(4, 'admin@demo.sk', '$2a$10$n8bNd/L0T8WWr8h9MGsFGu2ZBuFbv4lUDoqQD0/3A1GCsftD.8wvS', 'Anna', 'Majiteľová', '+421900444555', 'Kancelária 2, Bratislava', 'https://picsum.photos/seed/user4/300', 1, '2026-01-06 09:12:34', '2026-01-09 19:54:06');

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
-- Sťahujem dáta pre tabuľku `user_roles`
--

INSERT INTO `user_roles` (`users_user_id`, `roles_role_id`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);

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
  MODIFY `cart_item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT pre tabuľku `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT pre tabuľku `pizzas`
--
ALTER TABLE `pizzas`
  MODIFY `pizza_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pre tabuľku `pizza_sizes`
--
ALTER TABLE `pizza_sizes`
  MODIFY `pizza_size_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT pre tabuľku `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pre tabuľku `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

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
