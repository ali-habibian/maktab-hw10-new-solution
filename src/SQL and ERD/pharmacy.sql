/*
 Navicat Premium Data Transfer

 Source Server         : ali
 Source Server Type    : MySQL
 Source Server Version : 100421
 Source Host           : localhost:3306
 Source Schema         : pharmacy

 Target Server Type    : MySQL
 Target Server Version : 100421
 File Encoding         : 65001

 Date: 14/10/2021 00:33:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admins
-- ----------------------------
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admins
-- ----------------------------
INSERT INTO `admins` VALUES (1, 'admin', '123');

-- ----------------------------
-- Table structure for items
-- ----------------------------
DROP TABLE IF EXISTS `items`;
CREATE TABLE `items`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `does_exist` tinyint NOT NULL DEFAULT 0,
  `price` double NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of items
-- ----------------------------
INSERT INTO `items` VALUES (1, 'item1', 1, 100);
INSERT INTO `items` VALUES (3, 'item2', 1, 500);
INSERT INTO `items` VALUES (4, 'item3', 1, 600);
INSERT INTO `items` VALUES (5, 'item4', 0, 0);
INSERT INTO `items` VALUES (6, 'item5', 1, 300);

-- ----------------------------
-- Table structure for patients
-- ----------------------------
DROP TABLE IF EXISTS `patients`;
CREATE TABLE `patients`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of patients
-- ----------------------------
INSERT INTO `patients` VALUES (1, 'patient1', '1');
INSERT INTO `patients` VALUES (2, 'patient2', '2');
INSERT INTO `patients` VALUES (3, 'patient3', '3');

-- ----------------------------
-- Table structure for prescription_item
-- ----------------------------
DROP TABLE IF EXISTS `prescription_item`;
CREATE TABLE `prescription_item`  (
  `prescription_id` int NOT NULL,
  `item_id` int NOT NULL,
  PRIMARY KEY (`prescription_id`, `item_id`) USING BTREE,
  INDEX `FK_item`(`item_id`) USING BTREE,
  CONSTRAINT `FK_item` FOREIGN KEY (`item_id`) REFERENCES `items` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_prescroption` FOREIGN KEY (`prescription_id`) REFERENCES `prescriptions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of prescription_item
-- ----------------------------
INSERT INTO `prescription_item` VALUES (3, 1);
INSERT INTO `prescription_item` VALUES (3, 3);
INSERT INTO `prescription_item` VALUES (3, 6);
INSERT INTO `prescription_item` VALUES (4, 1);
INSERT INTO `prescription_item` VALUES (4, 5);
INSERT INTO `prescription_item` VALUES (4, 6);
INSERT INTO `prescription_item` VALUES (5, 1);
INSERT INTO `prescription_item` VALUES (5, 5);
INSERT INTO `prescription_item` VALUES (5, 6);
INSERT INTO `prescription_item` VALUES (6, 1);
INSERT INTO `prescription_item` VALUES (6, 3);
INSERT INTO `prescription_item` VALUES (6, 6);

-- ----------------------------
-- Table structure for prescriptions
-- ----------------------------
DROP TABLE IF EXISTS `prescriptions`;
CREATE TABLE `prescriptions`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int NOT NULL,
  `is_confirmed` tinyint NOT NULL DEFAULT 0,
  `admin_id` int NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_patient`(`patient_id`) USING BTREE,
  INDEX `FK_admin`(`admin_id`) USING BTREE,
  CONSTRAINT `FK_patient` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_admin` FOREIGN KEY (`admin_id`) REFERENCES `admins` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of prescriptions
-- ----------------------------
INSERT INTO `prescriptions` VALUES (3, 1, 1, 1);
INSERT INTO `prescriptions` VALUES (4, 1, 1, 1);
INSERT INTO `prescriptions` VALUES (5, 2, 1, 1);
INSERT INTO `prescriptions` VALUES (6, 3, 1, 1);
INSERT INTO `prescriptions` VALUES (7, 3, 0, 1);

SET FOREIGN_KEY_CHECKS = 1;
