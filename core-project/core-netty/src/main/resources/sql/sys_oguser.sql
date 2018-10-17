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

 Date: 15/10/2018 18:37:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_oguser
-- ----------------------------
DROP TABLE IF EXISTS `sys_oguser`;
CREATE TABLE `sys_oguser`  (
  `Id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `WorkNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `UserName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `TelPhone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `OfficePhone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Domain` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Sex` bit(1) NOT NULL,
  `IsEnabled` bit(1) DEFAULT NULL,
  `ShortMobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Birthday` date DEFAULT NULL,
  `PropertyId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `PropertyName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `PropertyNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Duty` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Avator` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `SortIndex` int(255) DEFAULT NULL,
  `CreateTime` date DEFAULT NULL,
  `CreateUserName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `CreateWorkNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `LastModifyTime` date DEFAULT NULL,
  `LastModifyUserName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `LastModifyWorkNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oguser
-- ----------------------------
INSERT INTO `sys_oguser` VALUES ('00000000-0000-0000-0000-000000000001', 'admin', '管理员', '62F49B53887C55184D8B2F339E883C05', '15001762770', 'lljstar@163.com', NULL, 'cnabs', b'0', b'0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-01-01', '管理员', 'admin', '2018-10-13', NULL, NULL);
INSERT INTO `sys_oguser` VALUES ('00000000-0000-0000-0000-000000000002', 'admin1', '管理员1', '62F49B53887C55184D8B2F339E883C05', '15001762770', 'lljstar@163.com', NULL, 'fund', b'0', b'0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-01-01', '管理员', 'admin', '2018-10-13', NULL, NULL);
INSERT INTO `sys_oguser` VALUES ('8a81831766770f8b0166770f8b590000', 'llj', '刘立军', NULL, NULL, NULL, NULL, NULL, b'1', b'1', NULL, '1984-09-20', NULL, NULL, NULL, NULL, NULL, NULL, '2018-10-15', NULL, NULL, '2018-10-15', NULL, NULL);
INSERT INTO `sys_oguser` VALUES ('8a818317667711b301667711b46a0000', 'llj', '刘立军', NULL, NULL, NULL, NULL, NULL, b'1', b'1', NULL, '1984-09-20', NULL, NULL, NULL, NULL, NULL, NULL, '2018-10-15', NULL, NULL, '2018-10-15', NULL, NULL);
INSERT INTO `sys_oguser` VALUES ('8a81831766771509016677150a1b0000', 'llj', '刘立军', NULL, NULL, NULL, NULL, NULL, b'1', b'1', NULL, '1984-09-20', NULL, NULL, NULL, NULL, NULL, NULL, '2018-10-15', NULL, NULL, '2018-10-15', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
