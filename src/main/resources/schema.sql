DROP TABLE IF EXISTS `t_coffee`;


CREATE TABLE `t_coffee` (
                            `id` bigint(20) NOT NULL,
                            `name` varchar(255) DEFAULT NULL,
                            `price` bigint(20) NOT NULL,
                            `create_time` datetime DEFAULT NULL,
                            `update_time` datetime DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;