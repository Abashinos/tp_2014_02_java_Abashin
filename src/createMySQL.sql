CREATE SCHEMA IF NOT EXISTS `java_db` ;
USE `java_db`;
-- -----------------------------------------------------
-- Table `java_db`.`users`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `java_db`.`users` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))