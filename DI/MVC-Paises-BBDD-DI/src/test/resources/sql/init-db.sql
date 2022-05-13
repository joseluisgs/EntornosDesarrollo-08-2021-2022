-- Adminer 4.8.1 MySQL 5.5.5-10.6.4-MariaDB dump

SET NAMES utf8;
SET
time_zone = '+00:00';
SET
foreign_key_checks = 0;
SET
sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP
DATABASE IF EXISTS `dam`;
CREATE
DATABASE `dam` /*!40100 DEFAULT CHARACTER SET utf8mb3 */;
USE
`dam`;

DROP TABLE IF EXISTS `acuerdo`;
CREATE TABLE `acuerdo`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `nombre`     varchar(100) NOT NULL,
    `fecha`      datetime     NOT NULL,
    `aportacion` double       NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


DROP TABLE IF EXISTS `linea_acuerdo`;
CREATE TABLE `linea_acuerdo`
(
    `id_acuerdo` int(11) NOT NULL,
    `id_pais`    int(11) NOT NULL,
    `año`        int(11) NOT NULL,
    `subvencion` double NOT NULL,
    KEY          `id_acuerdo` (`id_acuerdo`),
    KEY          `id_pais` (`id_pais`),
    CONSTRAINT `linea_acuerdo_ibfk_1` FOREIGN KEY (`id_acuerdo`) REFERENCES `acuerdo` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS `pais`;
CREATE TABLE `pais`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `nombre`      varchar(100) NOT NULL,
    `codigo`      varchar(5)   NOT NULL,
    `idioma`      varchar(100) NOT NULL,
    `moneda`      varchar(50)  NOT NULL,
    `capital`     varchar(100) NOT NULL,
    `presupuesto` double DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 2022-03-26 11:15:34