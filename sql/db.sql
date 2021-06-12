-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema finalDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema finalDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `finalDB` DEFAULT CHARACTER SET utf8 ;
USE `finalDB` ;

-- -----------------------------------------------------
-- Table `finalDB`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finalDB`.`user` (
                                                `id_user` INT NOT NULL AUTO_INCREMENT,
                                                `login` VARCHAR(50) NOT NULL,
                                                `password` VARCHAR(255) NOT NULL,
                                                `account` DECIMAL(13,2) UNSIGNED NOT NULL,
                                                `role` VARCHAR(10) NOT NULL,
                                                `user_status` VARCHAR(20) NOT NULL,
                                                PRIMARY KEY (`id_user`),
                                                UNIQUE INDEX `id_UNIQUE` (`id_user` ASC) VISIBLE,
                                                UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finalDB`.`theme`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finalDB`.`theme` (
                                                 `id_theme` INT NOT NULL AUTO_INCREMENT,
                                                 `theme` VARCHAR(45) NOT NULL,
                                                 PRIMARY KEY (`id_theme`),
                                                 UNIQUE INDEX `id_UNIQUE` (`id_theme` ASC) VISIBLE,
                                                 UNIQUE INDEX `name_UNIQUE` (`theme` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finalDB`.`edition`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finalDB`.`edition` (
                                                   `id_edition` INT NOT NULL AUTO_INCREMENT,
                                                   `name` VARCHAR(45) NOT NULL,
                                                   `price` DECIMAL(13,2) UNSIGNED NOT NULL DEFAULT 0,
                                                   `edition_status` VARCHAR(10) NOT NULL DEFAULT 1,
                                                   `theme_id` INT NOT NULL,
                                                   PRIMARY KEY (`id_edition`),
                                                   UNIQUE INDEX `id_UNIQUE` (`id_edition` ASC) VISIBLE,
                                                   INDEX `fk_edition_theme1_idx` (`theme_id` ASC) VISIBLE,
                                                   CONSTRAINT `fk_edition_theme1`
                                                       FOREIGN KEY (`theme_id`)
                                                           REFERENCES `finalDB`.`theme` (`id_theme`)
                                                           ON DELETE CASCADE
                                                           ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finalDB`.`subscription`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finalDB`.`subscription` (
                                                        `id_subscription` INT NOT NULL AUTO_INCREMENT,
                                                        `next_pay_date` DATE NOT NULL,
                                                        `subscription_status` VARCHAR(15) NOT NULL,
                                                        `user_id` INT NOT NULL,
                                                        `edition_id` INT NOT NULL,
                                                        PRIMARY KEY (`id_subscription`),
                                                        UNIQUE INDEX `id_subscription_UNIQUE` (`id_subscription` ASC) VISIBLE,
                                                        INDEX `fk_subscription_user_idx` (`user_id` ASC) VISIBLE,
                                                        INDEX `fk_subscription_edition1_idx` (`edition_id` ASC) VISIBLE,
                                                        CONSTRAINT `fk_subscription_user`
                                                            FOREIGN KEY (`user_id`)
                                                                REFERENCES `finalDB`.`user` (`id_user`)
                                                                ON DELETE CASCADE
                                                                ON UPDATE CASCADE,
                                                        CONSTRAINT `fk_subscription_edition1`
                                                            FOREIGN KEY (`edition_id`)
                                                                REFERENCES `finalDB`.`edition` (`id_edition`)
                                                                ON DELETE CASCADE
                                                                ON UPDATE CASCADE)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
