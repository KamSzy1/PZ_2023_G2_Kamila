SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

DROP DATABASE IF EXISTS firma;
CREATE DATABASE firma DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `firma`;

DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `id_login` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `email` varchar(128) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `login` (`id_login`, `user_id`, `email`, `password`) VALUES
(1, 1, 'email@wp.pl', '207023ccb44feb4d7dadca005ce29a64'),
(2, 2, 'email1@wp.pl', '6d932c406fa15164ee48ff5a52f81dae'),
(3, 3, 'email2@wp.pl', 'ed71c5d55af657bc2413020e5580d4dd'),
(4, 4, 'email3@wp.pl', 'daa9bda719032ae88abadb9cda4aa846'),
(5, 5, 'email4@wp.pl', '4bc0550cd0afc7bbe97be48a36303f6e');

DROP TABLE IF EXISTS `positions`;
CREATE TABLE `positions` (
  `id_position` int(11) NOT NULL,
  `position_name` varchar(128) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `positions` (`id_position`, `position_name`, `description`) VALUES
(1, 'Administrator', 'Posiada uprawnienia do zarządzania pracownikami, do dodawania zadań oraz może generować raporty'),
(2, 'Kierownik', 'Przydziela pracowników do zadań, może generować raporty'),
(3, 'Pracownik', 'Posiada uprawnienia do zmiany statusu zadań');

DROP TABLE IF EXISTS `statuses`;
CREATE TABLE `statuses` (
  `id_status` int(1) NOT NULL,
  `name` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `statuses` (`id_status`, `name`) VALUES
(1, 'Zrealizowano'),
(2, 'Do realizacji'),
(3, 'W trakcie'),
(4, 'Po terminie');

DROP TABLE IF EXISTS `tasks`;
CREATE TABLE `tasks` (
  `id_task` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` mediumtext NOT NULL,
  `status_id` int(1) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `tasks` (`id_task`, `title`, `description`, `status_id`, `user_id`) VALUES
(1, 'Funkcje pracownika', 'Tworzenie funkcjonalności panelu pracownika', 3, 1),
(2, 'Tworzenie dokumentacji', 'Utworzenie podstawowego zarysu projektu', 1, 1),
(3, 'Testy jednostkowe', 'Utworzenie testów jednostkowych', 2, 3),
(4, 'Optymalizacja wydajności serwera', ' Zadanie polegające na analizie i zoptymalizowaniu wydajności serwera w celu zapewnienia szybkiego działania aplikacji oraz zwiększenia przepustowości i stabilności systemu.', 2, 2),
(5, 'Testowanie aplikacji mobilnych', 'zadanie polegające na przeprowadzeniu testów funkcjonalnych i niezawodnościowych aplikacji mobilnych na różnych platformach, w tym iOS i Android.', 2, 4);

DROP TABLE IF EXISTS `tasks_history`;
CREATE TABLE `tasks_history` (
  `id_task_history` int(11) NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `tasks_id` int(11) DEFAULT NULL,
  `planned_end` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(128) NOT NULL,
  `address` varchar(255) NOT NULL,
  `zip` varchar(6) NOT NULL,
  `place` varchar(128) NOT NULL,
  `phone_num` int(9) NOT NULL,
  `position_id` int(11) DEFAULT NULL,
  `token` varchar(250) NOT NULL,
  `groups` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `users` (`id_user`, `name`, `surname`, `address`, `zip`, `place`, `phone_num`, `position_id`, `token`, `groups`) VALUES
(1, 'Patryk', 'Wyczawski', 'Urzednicza 2/9', '32-032', 'Poznań', 123456789, 3, 'token', 1),
(2, 'Kamila', 'Szydełko', 'Warszawska 2', '31-111', 'Kraków', 123123123, 1, 'token1', 2),
(3, 'Kacper', 'Dziuba', 'Podwisłocze 31', '11-111', 'Rzeszów', 321321321, 3, 'token2', 3),
(4, 'Lidia', 'Pacyna', 'Lwowska 307', '21-021', 'Radom', 123459876, 2, 'token3', 2),
(5, 'Weronika', 'Nowacka', 'Mieszka 1', '11-111', 'Rzeszów', 987654321, 2, 'token4', 1);

INSERT INTO `tasks_history` (`id_task_history`,`start_date`,`end_date`,`tasks_id`,`planned_end`) VALUES
(1,'2023-05-27',NULL,1,'2023-06-14'),
(2,'2023-05-29',NULL,2,'2023-06-13'),
(3,'2023-06-05',NULL,3,'2023-06-11'),
(4,'2023-06-04',NULL,4,'2023-06-16'),
(5,'2023-06-01',NULL,5,'2023-06-11');

ALTER TABLE `login`
  ADD PRIMARY KEY (`id_login`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `users_id` (`user_id`);

ALTER TABLE `positions`
  ADD PRIMARY KEY (`id_position`);

ALTER TABLE `statuses`
  ADD PRIMARY KEY (`id_status`);

ALTER TABLE `tasks`
  ADD PRIMARY KEY (`id_task`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `status_id` (`status_id`);

ALTER TABLE `tasks_history`
  ADD PRIMARY KEY (`id_task_history`),
  ADD UNIQUE KEY `tasks_id` (`tasks_id`);

ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `phone_num` (`phone_num`),
  ADD UNIQUE KEY `token` (`token`),
  ADD KEY `position_id` (`position_id`);

ALTER TABLE `login`
  MODIFY `id_login` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

ALTER TABLE `positions`
  MODIFY `id_position` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `statuses`
  MODIFY `id_status` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `tasks`
  MODIFY `id_task` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

ALTER TABLE `tasks_history`
  MODIFY `id_task_history` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

ALTER TABLE `login`
  ADD CONSTRAINT `login_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `tasks`
  ADD CONSTRAINT `status_id` FOREIGN KEY (`status_id`) REFERENCES `statuses` (`id_status`),
  ADD CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id_user`);

ALTER TABLE `tasks_history`
  ADD CONSTRAINT `tasks_id` FOREIGN KEY (`tasks_id`) REFERENCES `tasks` (`id_task`);

ALTER TABLE `users`
  ADD CONSTRAINT `position_id` FOREIGN KEY (`position_id`) REFERENCES `positions` (`id_position`);
COMMIT;