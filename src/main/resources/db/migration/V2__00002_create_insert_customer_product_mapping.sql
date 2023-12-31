CREATE TABLE `customer_type__product_mapping` (
    `id` VARCHAR(36) NOT NULL,
    `created_at` datetime NOT NULL,
    `created_by` VARCHAR(36) NOT NULL,
    `updated_at` datetime NOT NULL,
    `updated_by` VARCHAR(36) NOT NULL,
    `version` int DEFAULT NULL,
    `customer_type` varchar(64) NOT NULL,
    `product_id` varchar(36) NOT NULL,
    `quantity` int DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `index_customer_type__product_mapping_customer_type` (`customer_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `customer_type__product_mapping`(`id`, `created_at`, `created_by`, `updated_at`, `updated_by`, `version`, `customer_type`, `product_id`, `quantity`) VALUES(UUID(), NOW(), (SELECT `id` FROM `users` WHERE `email` = 'shubhamv.test@gmail.com'), NOW(), (SELECT `id` FROM `users` WHERE `email` = 'shubhamv.test@gmail.com'), 1, 'DRIVER', 'TRACKING_DEVICE', 1);