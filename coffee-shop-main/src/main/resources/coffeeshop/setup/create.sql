-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema coffee_shop
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `coffee_shop` ;

-- -----------------------------------------------------
-- Schema coffee_shop
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `coffee_shop` DEFAULT CHARACTER SET utf8 ;
USE `coffee_shop` ;

-- -----------------------------------------------------
-- Table `coffee_shop`.`user_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffee_shop`.`user_info` ;

CREATE TABLE IF NOT EXISTS `coffee_shop`.`user_info` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `date_created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `password` VARCHAR(256) NOT NULL,
  `role` VARCHAR(32) NOT NULL COMMENT 'customer/staff/admin',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coffee_shop`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffee_shop`.`customer` ;

CREATE TABLE IF NOT EXISTS `coffee_shop`.`customer` (
  `user_id` INT UNSIGNED NOT NULL,
  `nickname` VARCHAR(45) NOT NULL,
  INDEX `fk_customer_user1_idx` (`user_id` ASC) VISIBLE,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_customer_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `coffee_shop`.`user_info` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coffee_shop`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffee_shop`.`address` ;

CREATE TABLE IF NOT EXISTS `coffee_shop`.`address` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(45) NOT NULL,
  `province` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `district` VARCHAR(45) NOT NULL,
  `detail` VARCHAR(512) NOT NULL,
  `receiver` VARCHAR(45) NOT NULL,
  `receiver_phone` VARCHAR(20) NOT NULL COMMENT '收件人电话\n',
  `is_available` TINYINT NOT NULL DEFAULT 1,
  `customer_user_id` INT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_address_customer1_idx` (`customer_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_address_customer1`
    FOREIGN KEY (`customer_user_id`)
    REFERENCES `coffee_shop`.`customer` (`user_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coffee_shop`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffee_shop`.`category` ;

CREATE TABLE IF NOT EXISTS `coffee_shop`.`category` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coffee_shop`.`nutrition`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffee_shop`.`nutrition` ;

CREATE TABLE IF NOT EXISTS `coffee_shop`.`nutrition` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `calories` INT NULL,
  `fat` INT NULL,
  `carbon` INT NULL,
  `fiber` INT NULL,
  `protein` INT NULL,
  `sodium` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coffee_shop`.`image`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffee_shop`.`image` ;

CREATE TABLE IF NOT EXISTS `coffee_shop`.`image` (
  `uuid` CHAR(36) NOT NULL,
  `media_type` VARCHAR(45) NOT NULL,
  `content` MEDIUMBLOB NOT NULL,
  PRIMARY KEY (`uuid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coffee_shop`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffee_shop`.`product` ;

CREATE TABLE IF NOT EXISTS `coffee_shop`.`product` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(512) NULL,
  `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `cost` DECIMAL(12,2) UNSIGNED NOT NULL,
  `category_id` INT UNSIGNED NOT NULL,
  `nutrition_id` INT UNSIGNED NULL,
  `image_uuid` CHAR(36) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_category1_idx` (`category_id` ASC) VISIBLE,
  INDEX `fk_product_nutrition1_idx` (`nutrition_id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  INDEX `fk_product_image_storage1_idx` (`image_uuid` ASC) VISIBLE,
  UNIQUE INDEX `image_uuid_UNIQUE` (`image_uuid` ASC) VISIBLE,
  CONSTRAINT `fk_product_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `coffee_shop`.`category` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_product_nutrition1`
    FOREIGN KEY (`nutrition_id`)
    REFERENCES `coffee_shop`.`nutrition` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_product_image_storage1`
    FOREIGN KEY (`image_uuid`)
    REFERENCES `coffee_shop`.`image` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coffee_shop`.`ingredient_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffee_shop`.`ingredient_category` ;

CREATE TABLE IF NOT EXISTS `coffee_shop`.`ingredient_category` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coffee_shop`.`ingredient`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffee_shop`.`ingredient` ;

CREATE TABLE IF NOT EXISTS `coffee_shop`.`ingredient` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(512) NULL,
  `cost` DECIMAL(5,2) UNSIGNED NOT NULL,
  `ingredient_category_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_ingredient_ingredient_category1_idx` (`ingredient_category_id` ASC) VISIBLE,
  CONSTRAINT `fk_ingredient_ingredient_category1`
    FOREIGN KEY (`ingredient_category_id`)
    REFERENCES `coffee_shop`.`ingredient_category` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coffee_shop`.`store`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffee_shop`.`store` ;

CREATE TABLE IF NOT EXISTS `coffee_shop`.`store` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(45) NOT NULL,
  `province` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `district` VARCHAR(45) NOT NULL,
  `detail` VARCHAR(512) NOT NULL,
  `is_available` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coffee_shop`.`order_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffee_shop`.`order_info` ;

CREATE TABLE IF NOT EXISTS `coffee_shop`.`order_info` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `date_create` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `amount` DECIMAL(12,2) UNSIGNED NOT NULL,
  `confirmation` INT UNSIGNED NOT NULL COMMENT 'confirmation id',
  `is_prepared` TINYINT NOT NULL DEFAULT 0,
  `is_finished` TINYINT NOT NULL DEFAULT 0,
  `store_id` INT UNSIGNED NOT NULL,
  `address_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_store1_idx` (`store_id` ASC) VISIBLE,
  INDEX `fk_order_address1_idx` (`address_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_store1`
    FOREIGN KEY (`store_id`)
    REFERENCES `coffee_shop`.`store` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_order_address1`
    FOREIGN KEY (`address_id`)
    REFERENCES `coffee_shop`.`address` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coffee_shop`.`delivery`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffee_shop`.`delivery` ;

CREATE TABLE IF NOT EXISTS `coffee_shop`.`delivery` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `date_create` TIMESTAMP NOT NULL,
  `phone` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coffee_shop`.`season_special`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffee_shop`.`season_special` ;

CREATE TABLE IF NOT EXISTS `coffee_shop`.`season_special` (
  `product_id` INT UNSIGNED NOT NULL,
  INDEX `fk_season_special_product1_idx` (`product_id` ASC) VISIBLE,
  PRIMARY KEY (`product_id`),
  CONSTRAINT `fk_season_special_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `coffee_shop`.`product` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coffee_shop`.`suborder`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffee_shop`.`suborder` ;

CREATE TABLE IF NOT EXISTS `coffee_shop`.`suborder` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` INT UNSIGNED NOT NULL,
  `product_id` INT UNSIGNED NOT NULL,
  `quantity` SMALLINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_has_product_product1_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_order_has_product_order1_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_has_product_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `coffee_shop`.`order_info` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_order_has_product_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `coffee_shop`.`product` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coffee_shop`.`staff`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffee_shop`.`staff` ;

CREATE TABLE IF NOT EXISTS `coffee_shop`.`staff` (
  `user_id` INT UNSIGNED NOT NULL,
  `store_id` INT UNSIGNED NOT NULL,
  INDEX `fk_staff_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_staff_store1_idx` (`store_id` ASC) VISIBLE,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_staff_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `coffee_shop`.`user_info` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_staff_store1`
    FOREIGN KEY (`store_id`)
    REFERENCES `coffee_shop`.`store` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coffee_shop`.`product_option`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffee_shop`.`product_option` ;

CREATE TABLE IF NOT EXISTS `coffee_shop`.`product_option` (
  `product_id` INT UNSIGNED NOT NULL,
  `ingredient_category_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`product_id`, `ingredient_category_id`),
  INDEX `fk_product_has_ingredient_category_ingredient_category1_idx` (`ingredient_category_id` ASC) VISIBLE,
  INDEX `fk_product_has_ingredient_category_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_has_ingredient_category_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `coffee_shop`.`product` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_product_has_ingredient_category_ingredient_category1`
    FOREIGN KEY (`ingredient_category_id`)
    REFERENCES `coffee_shop`.`ingredient_category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coffee_shop`.`ordered_product_has_ingredient`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffee_shop`.`ordered_product_has_ingredient` ;

CREATE TABLE IF NOT EXISTS `coffee_shop`.`ordered_product_has_ingredient` (
  `ordered_product_id` INT UNSIGNED NOT NULL,
  `ingredient_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ordered_product_id`, `ingredient_id`),
  INDEX `fk_ordered_product_has_ingredient_ingredient1_idx` (`ingredient_id` ASC) VISIBLE,
  INDEX `fk_ordered_product_has_ingredient_ordered_product1_idx` (`ordered_product_id` ASC) VISIBLE,
  CONSTRAINT `fk_ordered_product_has_ingredient_ordered_product1`
    FOREIGN KEY (`ordered_product_id`)
    REFERENCES `coffee_shop`.`suborder` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ordered_product_has_ingredient_ingredient1`
    FOREIGN KEY (`ingredient_id`)
    REFERENCES `coffee_shop`.`ingredient` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
