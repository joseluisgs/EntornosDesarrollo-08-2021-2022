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

INSERT INTO `acuerdo` (`id`, `nombre`, `fecha`, `aportacion`)
VALUES (1, 'Acuerdo Diplomático', '2022-03-25 20:59:57', 12345.89),
       (2, 'Acuerdo de Fronteras', '2022-03-25 21:01:40', 999.99);

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

INSERT INTO `linea_acuerdo` (`id_acuerdo`, `id_pais`, `año`, `subvencion`)
VALUES (1, 1, 2022, 1234.56),
       (1, 2, 2022, 345.32),
       (2, 4, 2022, 450.8),
       (2, 7, 2022, 6666.66),
       (2, 2, 2022, 333.33);

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

INSERT INTO `pais` (`id`, `nombre`, `codigo`, `idioma`, `moneda`, `capital`, `presupuesto`)
VALUES (1, 'España', 'ES', 'Español', 'Euro', 'Madrid', 1234567.23),
       (2, 'Francia', 'FR', 'Francés', 'Euro', 'París', 123456.67),
       (3, 'Italia', 'IT', 'Italiano', 'Euro', 'Roma', 564568.34),
       (4, 'Alemania', 'DE', 'Alemán', 'Euro', 'Berlín', 234567.23),
       (5, 'Reino Unido', 'UK', 'Inglés', 'Libra', 'Londres', 232323.34),
       (6, 'Japón', 'JP', 'Jsaponés', 'Yen', 'Tokio', 21212.12),
       (7, 'China', 'CN', 'Chino', 'Yuan', 'Pekín', 121212.34),
       (8, 'Australia', 'AU', 'Inglés', 'Dólar', 'Canberra', 567654.23),
       (9, 'Argentina', 'AR', 'Español', 'Peso', 'Buenos Aires', 123456.34),
       (10, 'Brasil', 'BR', 'Portugués', 'Real', 'Brasilia', 251251.62),
       (11, 'Estados Unidos', 'US', 'Inglés', 'Dólar', 'Washington', 1212415.98);

-- 2022-03-26 11:15:34