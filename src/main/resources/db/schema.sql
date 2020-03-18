CREATE TABLE  if not exists `analyze_data` (
    `id` int(11) NOT NULL,
    `name` varchar(100) DEFAULT NULL,
    `web` varchar(200) DEFAULT NULL,
    `pv` int(11) DEFAULT NULL,
    `status` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
);