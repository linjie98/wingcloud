/*
 Navicat Premium Data Transfer

 Source Server         : wingcloud
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Schema         : wingcloud

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 25/03/2019 13:04:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for shop_money
-- ----------------------------
DROP TABLE IF EXISTS `shop_money`;
CREATE TABLE `shop_money`  (
  `id` int(11) NOT NULL,
  `money` bigint(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
