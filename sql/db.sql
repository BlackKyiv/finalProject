-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`account_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`account_status` (
                                                       `id` INT NOT NULL AUTO_INCREMENT,
                                                       `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `idaccount_status_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`role` (
                                             `id` INT NOT NULL AUTO_INCREMENT,
                                             `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`account` (
                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                `login` VARCHAR(50) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `account` DOUBLE UNSIGNED NOT NULL,
    `role_id` INT NOT NULL,
    `account_status_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
    INDEX `fk_account_account_status1_idx` (`account_status_id` ASC) VISIBLE,
    INDEX `fk_account_role1_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `fk_account_account_status1`
    FOREIGN KEY (`account_status_id`)
    REFERENCES `mydb`.`account_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_account_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `mydb`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`theme`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`theme` (
                                              `id` INT NOT NULL AUTO_INCREMENT,
                                              `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`edition_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`edition_status` (
                                                       `id` INT NOT NULL AUTO_INCREMENT,
                                                       `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`edition`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`edition` (
                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                `name` VARCHAR(45) NOT NULL,
    `price` DOUBLE UNSIGNED NOT NULL DEFAULT 0,
    `theme_id` INT NOT NULL,
    `edition_status_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    INDEX `fk_edition_theme_idx` (`theme_id` ASC) VISIBLE,
    INDEX `fk_edition_edition_status1_idx` (`edition_status_id` ASC) VISIBLE,
    CONSTRAINT `fk_edition_theme`
    FOREIGN KEY (`theme_id`)
    REFERENCES `mydb`.`theme` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_edition_edition_status1`
    FOREIGN KEY (`edition_status_id`)
    REFERENCES `mydb`.`edition_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`subscription_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`subscription_status` (
                                                            `id` INT NOT NULL AUTO_INCREMENT,
                                                            `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`subscription`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`subscription` (
                                                     `id` INT NOT NULL AUTO_INCREMENT,
                                                     `edition_id` INT NOT NULL,
                                                     `account_id` INT NOT NULL,
                                                     `next_pay_date` DATETIME NOT NULL,
                                                     `subscription_status_id` INT NOT NULL,
                                                     PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    INDEX `fk_subscription_edition1_idx` (`edition_id` ASC) VISIBLE,
    INDEX `fk_subscription_account1_idx` (`account_id` ASC) VISIBLE,
    INDEX `fk_subscription_subscription_status1_idx` (`subscription_status_id` ASC) VISIBLE,
    CONSTRAINT `fk_subscription_edition1`
    FOREIGN KEY (`edition_id`)
    REFERENCES `mydb`.`edition` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_subscription_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `mydb`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_subscription_subscription_status1`
    FOREIGN KEY (`subscription_status_id`)
    REFERENCES `mydb`.`subscription_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
