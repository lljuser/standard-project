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

 Date: 15/10/2018 18:37:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `birthDay` date DEFAULT NULL,
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 84 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'llj', 34, '1984-09-21', 'shanghai');
INSERT INTO `t_user` VALUES (2, 'cctv', 100, '1949-10-01', 'beijing');
INSERT INTO `t_user` VALUES (3, 'mybatis', 111, '2018-07-11', 'anhui');

SET FOREIGN_KEY_CHECKS = 1;
