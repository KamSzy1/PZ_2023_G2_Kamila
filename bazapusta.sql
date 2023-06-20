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

DROP TABLE IF EXISTS `positions`;
CREATE TABLE `positions` (
  `id_position` int(11) NOT NULL,
  `position_name` varchar(128) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `statuses`;
CREATE TABLE `statuses` (
  `id_status` int(1) NOT NULL,
  `name` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `tasks`;
CREATE TABLE `tasks` (
  `id_task` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` mediumtext NOT NULL,
  `status_id` int(1) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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