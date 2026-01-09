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

--
-- Sťahujem dáta pre tabuľku `cart_items`
--

INSERT INTO `cart_items` (`cart_item_id`, `quantity`, `users_user_id`, `pizza_sizes_pizza_size_id`, `created_at`, `updated_at`) VALUES
(19, 1, 1, 3, '2026-01-09 21:06:04', '2026-01-09 21:06:04');

--
-- Sťahujem dáta pre tabuľku `orders`
--

INSERT INTO `orders` (`order_id`, `status`, `total_price`, `delivery_address`, `note`, `created_at`, `updated_at`, `users_user_id`, `cook_user_id`, `courier_user_id`) VALUES
(1, 'doručené', 19.80, 'Hlavná 1, Bratislava', 'Prosím bez cibule.', '2026-01-06 09:12:35', '2026-01-07 09:25:50', 1, 2, 3),
(2, 'doručené', 21.60, 'Hlavná 1, Bratislava', NULL, '2026-01-06 09:12:35', '2026-01-07 09:26:06', 1, 2, 3),
(6, 'doručené', 79.20, 'Jozinova ulica 13/4', 'Zvonček nefunguje XD', '2026-01-07 09:39:48', '2026-01-07 09:41:56', 1, 2, 3),
(14, 'doručené', 11.90, 'Československej armady 55/5', '', '2026-01-09 19:56:46', '2026-01-09 20:13:19', 4, 2, 3),
(15, 'čaká_na_potvrdenie', 10.20, 'Bratislavska ulica 14/7', '', '2026-01-09 20:12:06', '2026-01-09 20:12:06', 2, NULL, NULL);

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

--
-- Sťahujem dáta pre tabuľku `roles`
--

INSERT INTO `roles` (`role_id`, `name`, `description`) VALUES
(1, 'ROLE_CUSTOMER', 'Zákazník'),
(2, 'ROLE_COOK', 'Kuchár / manažér'),
(3, 'ROLE_COURIER', 'Kuriér'),
(4, 'ROLE_ADMIN', 'Admin / majiteľ');

--
-- Sťahujem dáta pre tabuľku `users`
--

INSERT INTO `users` (`user_id`, `email`, `password`, `first_name`, `last_name`, `phone_number`, `address`, `profile_image_url`, `enabled`, `created_at`, `updated_at`) VALUES
(1, 'zakaznik@demo.sk', '$2a$10$PEUkVE3PRZXVpJfGGEY7Cug4DCThxMzZChIQrFdUABBjoG.s0yhF.', 'Jánko', 'Novák', '+421900111222', 'Hlavná 1, Bratislava', 'https://picsum.photos/seed/user1/300', 1, '2026-01-06 09:12:34', '2026-01-09 19:53:58'),
(2, 'kuchar@demo.sk', '$2a$10$NKcx/Uq6drM9Vbm3DmxsQuIX6PgLPJlDLine0lws.PPj/mgH1xNDm', 'Peter', 'Kováč', '+421900222333', 'Kuchyňa 10, Bratislava', 'https://picsum.photos/seed/user2/300', 1, '2026-01-06 09:12:34', '2026-01-09 19:54:01'),
(3, 'kurier@demo.sk', '$2a$10$ob9e06oPFoGBRxONM.jScuySDY5.lgpLNtGUfQAs7RlmlYLYM2b9G', 'Marek', 'Horváth', '+421900333444', 'Depo 5, Bratislava', 'https://picsum.photos/seed/user3/300', 1, '2026-01-06 09:12:34', '2026-01-06 09:12:34'),
(4, 'admin@demo.sk', '$2a$10$n8bNd/L0T8WWr8h9MGsFGu2ZBuFbv4lUDoqQD0/3A1GCsftD.8wvS', 'Anna', 'Majiteľová', '+421900444555', 'Kancelária 2, Bratislava', 'https://picsum.photos/seed/user4/300', 1, '2026-01-06 09:12:34', '2026-01-09 19:54:06');

--
-- Sťahujem dáta pre tabuľku `user_roles`
--

INSERT INTO `user_roles` (`users_user_id`, `roles_role_id`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
