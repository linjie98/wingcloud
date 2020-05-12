/*
 Navicat Premium Data Transfer

 Source Server         : wingcloud
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Schema         : wingcloud

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 25/03/2019 13:03:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for shop_msg
-- ----------------------------
DROP TABLE IF EXISTS `shop_msg`;
CREATE TABLE `shop_msg`  (
  `id` bigint(1) NOT NULL AUTO_INCREMENT,
  `userid` bigint(1) NOT NULL,
  `usergender` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `userage` int(11) NULL DEFAULT NULL,
  `userarea` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `shopid` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `shoptype` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `shoptime` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1265965 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
