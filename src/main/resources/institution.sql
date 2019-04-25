-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema institution
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema institution
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `institution` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `institution`;

-- -----------------------------------------------------
-- Table `institution`.`classes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `institution`.`classes`
(
    `class_id` BIGINT(20)  NOT NULL,
    `title`    VARCHAR(45) NULL DEFAULT NULL,
    PRIMARY KEY (`class_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `institution`.`disciplines`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `institution`.`disciplines`
(
    `id`    BIGINT(20)    NOT NULL,
    `title` VARCHAR(1024) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `institution`.`classes_has_disciplines`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `institution`.`classes_has_disciplines`
(
    `classes_class_id`    BIGINT(20) NOT NULL,
    `disciplines_dspl_id` BIGINT(20) NOT NULL,
    PRIMARY KEY (`classes_class_id`, `disciplines_dspl_id`),
    INDEX `fk_classes_has_disciplines_disciplines1_idx` (`disciplines_dspl_id` ASC) VISIBLE,
    INDEX `fk_classes_has_disciplines_classes1_idx` (`classes_class_id` ASC) VISIBLE,
    CONSTRAINT `fk_classes_has_disciplines_classes1`
        FOREIGN KEY (`classes_class_id`)
            REFERENCES `institution`.`classes` (`class_id`),
    CONSTRAINT `fk_classes_has_disciplines_disciplines1`
        FOREIGN KEY (`disciplines_dspl_id`)
            REFERENCES `institution`.`disciplines` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `institution`.`teachers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `institution`.`teachers`
(
    `teacher_id`    BIGINT(20)  NOT NULL,
    `name`          VARCHAR(45) NULL DEFAULT NULL,
    `surname`       VARCHAR(45) NULL DEFAULT NULL,
    `middle_name`   VARCHAR(45) NULL DEFAULT NULL,
    `date_of_birth` VARCHAR(45) NULL DEFAULT NULL,
    `gender`        VARCHAR(45) NULL DEFAULT NULL,
    PRIMARY KEY (`teacher_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `institution`.`classes_has_teachers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `institution`.`classes_has_teachers`
(
    `classes_class_id`    BIGINT(20) NOT NULL,
    `teachers_teacher_id` BIGINT(20) NOT NULL,
    PRIMARY KEY (`classes_class_id`, `teachers_teacher_id`),
    INDEX `fk_classes_has_teachers_teachers1_idx` (`teachers_teacher_id` ASC) VISIBLE,
    INDEX `fk_classes_has_teachers_classes1_idx` (`classes_class_id` ASC) VISIBLE,
    CONSTRAINT `fk_classes_has_teachers_classes1`
        FOREIGN KEY (`classes_class_id`)
            REFERENCES `institution`.`classes` (`class_id`),
    CONSTRAINT `fk_classes_has_teachers_teachers1`
        FOREIGN KEY (`teachers_teacher_id`)
            REFERENCES `institution`.`teachers` (`teacher_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `institution`.`disciplines_has_teachers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `institution`.`disciplines_has_teachers`
(
    `disciplines_dspl_id` BIGINT(20) NOT NULL,
    `teachers_teacher_id` BIGINT(20) NOT NULL,
    PRIMARY KEY (`disciplines_dspl_id`, `teachers_teacher_id`),
    INDEX `fk_disciplines_has_teachers_teachers1_idx` (`teachers_teacher_id` ASC) VISIBLE,
    INDEX `fk_disciplines_has_teachers_disciplines1_idx` (`disciplines_dspl_id` ASC) VISIBLE,
    CONSTRAINT `fk_disciplines_has_teachers_disciplines1`
        FOREIGN KEY (`disciplines_dspl_id`)
            REFERENCES `institution`.`disciplines` (`id`),
    CONSTRAINT `fk_disciplines_has_teachers_teachers1`
        FOREIGN KEY (`teachers_teacher_id`)
            REFERENCES `institution`.`teachers` (`teacher_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `institution`.`students`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `institution`.`students`
(
    `student_id`       BIGINT(20)  NOT NULL,
    `name`             VARCHAR(45) NULL DEFAULT NULL,
    `surname`          VARCHAR(45) NULL DEFAULT NULL,
    `middle_name`      VARCHAR(45) NULL DEFAULT NULL,
    `date_of_birth`    VARCHAR(45) NULL DEFAULT NULL,
    `gender`           VARCHAR(45) NULL DEFAULT NULL,
    `classes_class_id` BIGINT(20)  NOT NULL,
    PRIMARY KEY (`student_id`),
    INDEX `fk_students_classes_idx` (`classes_class_id` ASC) VISIBLE,
    CONSTRAINT `fk_students_classes`
        FOREIGN KEY (`classes_class_id`)
            REFERENCES `institution`.`classes` (`class_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
