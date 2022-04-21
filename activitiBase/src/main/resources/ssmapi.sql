/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : ssmapi

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 18/04/2022 20:54:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ssmapi_user
-- ----------------------------
DROP TABLE IF EXISTS `ssmapi_user`;
CREATE TABLE `ssmapi_user`  (
  `uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ssmapi_user
-- ----------------------------
INSERT INTO `ssmapi_user` VALUES ('1', '1', '1', '1', '1', '1');
INSERT INTO `ssmapi_user` VALUES ('f565136a-4f3f-43be-b926-b5fe1f31fd87', 'admin', 'admin', '13624053637', NULL, NULL);
INSERT INTO `ssmapi_user` VALUES ('f565136a-4f3f-43be-b926-b5fe1f31fd88', '康尔宝', '123456', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
