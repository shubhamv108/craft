CREATE TABLE `users` (
    `id` VARCHAR(36) NOT NULL,
    `created_at` datetime NOT NULL,
    `created_by` VARCHAR(36) NOT NULL,
    `updated_at` datetime NOT NULL,
    `updated_by` VARCHAR(36) NOT NULL,
    `version` int DEFAULT NULL,
    `name` varchar(128) NOT NULL,
    `email` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ofx65keruapi6vyqpv6f2or37` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `users`(`id`, `created_at`, `created_by`, `updated_at`, `updated_by`, `version`, `name`, `email`) VALUES(UUID(), NOW(), "$$FLYWAY$$", NOW(), "$$FLYWAY$$", 1, 'test-admin', 'shubhamv.test@gmail.com');

CREATE TABLE `roles` (
  `id` VARCHAR(36) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` VARCHAR(36) NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_by` VARCHAR(36) NOT NULL,
  `version` int DEFAULT NULL,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `roles`(`id`,`created_at`, `created_by`, `updated_at`, `updated_by`, `version`, `name`) VALUES(UUID(), NOW(), (SELECT `id` FROM `users` WHERE `email` = 'shubhamv.test@gmail.com'), NOW(), (SELECT `id` FROM `users` WHERE `email` = 'shubhamv.test@gmail.com'), 1, 'ADMIN');
INSERT INTO `roles`(`id`,`created_at`, `created_by`, `updated_at`, `updated_by`, `version`, `name`) VALUES(UUID(), NOW(), (SELECT `id` FROM `users` WHERE `email` = 'shubhamv.test@gmail.com'), NOW(), (SELECT `id` FROM `users` WHERE `email` = 'shubhamv.test@gmail.com'), 1, 'USER');


CREATE TABLE `users_roles` (
  `id` VARCHAR(36) NOT NULL,
  `user_id` VARCHAR(36) NOT NULL,
  `role_id` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `users_roles` (`id`,`user_id`, `role_id`) VALUES(UUID(), (SELECT `id` FROM `users` WHERE `email` = 'shubhamv.test@gmail.com'), (SELECT `id` FROM `roles` WHERE `name` = 'ADMIN'));
