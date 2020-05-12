/*
 Navicat Premium Data Transfer

 Source Server         : wingcloud
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Schema         : wingcloud

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 25/03/2019 13:04:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint(1) NOT NULL COMMENT '用户id',
  `user_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户昵称',
  `user_password` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户密码',
  `user_email` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户email',
  `user_authority` int(11) NULL DEFAULT NULL COMMENT '用户权限级别\n1：普通用户\n2：顶级用户\n3:  普通管理员\n4:  顶级管理员',
  `user_online_state` int(11) NULL DEFAULT NULL COMMENT '用户在线状态\n1:  在线\n2:  离线',
  `user_loggedoff_state` int(11) NULL DEFAULT NULL COMMENT '用户是否永久注销状态\n1:  注销\n2：未注销',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
