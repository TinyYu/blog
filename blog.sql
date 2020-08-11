/*
 Navicat Premium Data Transfer

 Source Server         : mysql_1
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 11/08/2020 21:32:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for b_blog
-- ----------------------------
DROP TABLE IF EXISTS `b_blog`;
CREATE TABLE `b_blog`  (
  `blogId` int(4) NOT NULL AUTO_INCREMENT COMMENT '博客id',
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客内容',
  `firstPicture` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客首图',
  `flag` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标记',
  `views` int(3) NOT NULL DEFAULT 0 COMMENT '浏览次数',
  `appreciation` tinyint(1) NOT NULL DEFAULT 0 COMMENT '赞赏事务 开/关',
  `shareStatement` tinyint(1) NOT NULL DEFAULT 0 COMMENT '转载声明 开/关',
  `comment` tinyint(1) NOT NULL DEFAULT 0 COMMENT '评论 开/关',
  `published` tinyint(1) NOT NULL COMMENT '发布/保存',
  `recommend` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否推荐',
  `createTime` datetime(0) NOT NULL COMMENT '发布时间',
  `updateTime` datetime(0) NOT NULL COMMENT '更新时间',
  `typeId` int(4) NOT NULL COMMENT '分类id',
  `userId` int(4) NOT NULL COMMENT '用户id',
  `blogdescribe` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`blogId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for b_comment
-- ----------------------------
DROP TABLE IF EXISTS `b_comment`;
CREATE TABLE `b_comment`  (
  `commentId` int(4) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论人的昵称',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论人的邮箱',
  `content` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论的内容',
  `avatar` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像',
  `createTime` datetime(0) NOT NULL COMMENT '发布时间',
  `blogId` int(4) NOT NULL COMMENT '博客id',
  `parentCommentId` int(4) NULL DEFAULT NULL COMMENT '对应的父评论id',
  `adminComment` tinyint(4) NOT NULL,
  PRIMARY KEY (`commentId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for b_idt
-- ----------------------------
DROP TABLE IF EXISTS `b_idt`;
CREATE TABLE `b_idt`  (
  `blogId` int(4) NOT NULL COMMENT '博客id',
  `tagId` int(4) NOT NULL COMMENT '标签id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for b_tag
-- ----------------------------
DROP TABLE IF EXISTS `b_tag`;
CREATE TABLE `b_tag`  (
  `tagId` int(4) NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名称',
  PRIMARY KEY (`tagId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for b_type
-- ----------------------------
DROP TABLE IF EXISTS `b_type`;
CREATE TABLE `b_type`  (
  `typeId` int(4) NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名称',
  PRIMARY KEY (`typeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for b_user
-- ----------------------------
DROP TABLE IF EXISTS `b_user`;
CREATE TABLE `b_user`  (
  `userId` int(4) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `nickname` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `username` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像',
  `type` int(1) NOT NULL DEFAULT 1 COMMENT '用户类型',
  `createTime` datetime(0) NOT NULL COMMENT '发布时间',
  `updateTime` datetime(0) NOT NULL COMMENT '更新时间',
  `salt` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
