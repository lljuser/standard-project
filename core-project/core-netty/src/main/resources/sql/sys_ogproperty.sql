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

 Date: 15/10/2018 18:37:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_ogproperty
-- ----------------------------
DROP TABLE IF EXISTS `sys_ogproperty`;
CREATE TABLE `sys_ogproperty`  (
  `Id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ParentId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `PropertyNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PropertyType` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `RoleId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `SortIndex` int(255) DEFAULT NULL,
  `Description` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `IsEnabled` bit(1) NOT NULL,
  `CreateTime` date DEFAULT NULL,
  `CreateUserName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `CreateWorkNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `LastModifyTime` date DEFAULT NULL,
  `LastModifyUserName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `LastModifyWorkNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `Id`(`Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_ogproperty
-- ----------------------------
INSERT INTO `sys_ogproperty` VALUES ('00000000-0000-0000-0000-000000000001', NULL, 'Root', '公司组织', 'SysOrg', NULL, 1, NULL, b'1', '2018-10-13', NULL, NULL, '2018-10-13', NULL, NULL);
INSERT INTO `sys_ogproperty` VALUES ('00000000-0000-0000-0000-000000000002', '00000000-0000-0000-0000-000000000001', 'Dev', '开发部', 'SysOrg', NULL, 2, NULL, b'1', '2018-10-13', NULL, NULL, '2018-10-13', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
