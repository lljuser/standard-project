/*
 Navicat Premium Data Transfer

 Source Server         : test-edu-school
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 10.1.1.214:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 15/10/2018 18:37:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_oguserproperty
-- ----------------------------
DROP TABLE IF EXISTS `sys_oguserproperty`;
CREATE TABLE `sys_oguserproperty`  (
  `UserId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PropertyId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`UserId`) USING BTREE,
  INDEX `PK_UP`(`UserId`, `PropertyId`) USING BTREE,
  INDEX `FK_PropertyId`(`PropertyId`) USING BTREE,
  CONSTRAINT `FK_PropertyId` FOREIGN KEY (`PropertyId`) REFERENCES `sys_ogproperty` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_UserId` FOREIGN KEY (`UserId`) REFERENCES `sys_oguser` (`Id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oguserproperty
-- ----------------------------
INSERT INTO `sys_oguserproperty` VALUES ('00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

SET FOREIGN_KEY_CHECKS = 1;
