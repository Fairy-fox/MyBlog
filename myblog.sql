/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MariaDB
 Source Server Version : 100311
 Source Host           : localhost:3306
 Source Schema         : myblog

 Target Server Type    : MariaDB
 Target Server Version : 100311
 File Encoding         : 65001

 Date: 12/03/2020 17:38:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `article_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `content` text CHARACTER SET gbk COLLATE gbk_chinese_ci,
  `created_time` datetime(0) DEFAULT NULL,
  `updated_time` datetime(0) DEFAULT NULL,
  `comments_num` bigint(20) DEFAULT NULL,
  `stared` tinyint(1) DEFAULT NULL,
  `toped` tinyint(1) DEFAULT NULL,
  `elited` tinyint(1) DEFAULT NULL,
  `column_id` int(11) DEFAULT NULL,
  `title` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci DEFAULT NULL,
  `wanted` int(11) DEFAULT NULL,
  `needed` int(11) DEFAULT NULL,
  `viewed` bigint(20) DEFAULT 0,
  PRIMARY KEY (`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = gbk COLLATE = gbk_chinese_ci COMMENT = '文章\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, 1, 'agagawawgwga', '2020-03-02 19:22:15', '2020-03-02 19:22:15', 0, 0, 0, 1, 5, 'awagawgawg', 0, 0, 0);
INSERT INTO `article` VALUES (2, 1, '希望大家友善沟通哦[hr]face[阴险] ', '2020-03-02 19:22:15', '2020-03-11 19:23:46', 4, 0, 1, 0, 5, '欢迎大家访问MyBlog', 0, 0, 82);
INSERT INTO `article` VALUES (3, 1, '不要出门', '2020-03-02 21:51:56', '2020-03-02 21:51:56', 2, 0, 0, 0, 2, '肺炎形势严峻大家注意个人身体健康', 0, 0, 1);
INSERT INTO `article` VALUES (4, 2, 'afawfawf', '2020-03-04 19:21:02', '2020-03-04 19:21:02', 0, 0, 0, 0, 5, 'afwaf', 0, 0, 1);
INSERT INTO `article` VALUES (5, 1, '嘿嘿', '2020-03-05 09:50:41', '2020-03-05 09:50:41', 0, 0, 0, 0, 5, '这是一个分享贴', 0, 0, 0);
INSERT INTO `article` VALUES (6, 1, '嘿嘿', '2020-03-05 09:51:10', '2020-03-05 09:51:10', 0, 0, 0, 0, 3, '这是一个建议帖子', 0, 0, 0);
INSERT INTO `article` VALUES (7, 1, 'fawfwaaw', '2020-03-05 16:22:19', '2020-03-05 16:22:19', 0, 0, 0, 0, 6, 'wfafaw', 0, 0, 0);
INSERT INTO `article` VALUES (8, 1, 'aawwgwag', '2020-03-05 16:22:26', '2020-03-05 16:22:26', 0, 0, 0, 0, 4, 'agawwg', 0, 0, 0);
INSERT INTO `article` VALUES (9, 1, 'awfaf', '2020-03-05 16:22:33', '2020-03-05 16:22:33', 0, 0, 0, 0, 5, 'awaf', 0, 20, 0);
INSERT INTO `article` VALUES (10, 1, 'awfaawg', '2020-03-05 16:22:40', '2020-03-05 16:22:40', 0, 0, 0, 0, 6, 'agaw', 0, 0, 0);
INSERT INTO `article` VALUES (11, 1, 'afawaf', '2020-03-05 16:22:45', '2020-03-05 16:22:45', 0, 0, 0, 0, 4, 'awaaf', 0, 0, 0);
INSERT INTO `article` VALUES (12, 1, 'waaaw', '2020-03-05 16:22:56', '2020-03-05 16:22:56', 0, 0, 0, 0, 6, 'afafa', 0, 0, 0);
INSERT INTO `article` VALUES (13, 1, 'aafwag', '2020-03-05 16:23:02', '2020-03-05 16:23:02', 0, 0, 0, 0, 4, 'awdawf', 0, 0, 0);
INSERT INTO `article` VALUES (14, 1, 'awfaf', '2020-03-05 16:43:08', '2020-03-05 16:43:08', 0, 0, 0, 0, 5, 'awfaf', 0, 0, 1);
INSERT INTO `article` VALUES (15, 1, 'afwfwafa', '2020-03-05 17:03:34', '2020-03-05 17:03:34', 3, 0, 0, 0, 3, 'awdaw', 0, 0, 0);
INSERT INTO `article` VALUES (16, 1, 'awfawfaf', '2020-03-05 20:19:36', '2020-03-05 20:19:36', 1, 0, 0, 0, 6, 'afwa ', 0, 0, 9);
INSERT INTO `article` VALUES (20, 1, '123', '2020-03-09 20:31:07', '2020-03-09 20:31:07', 1, 0, 0, 0, 6, '123', 0, 0, 5);
INSERT INTO `article` VALUES (21, 1, 'img[http://image.my.com/2020/03/11/96ad60e919564edaa9e315e2c6e73a8d.png] ', '2020-03-11 19:15:10', '2020-03-11 19:19:26', 0, 0, 0, 0, 4, '图片上传测试', 0, 0, 9);
INSERT INTO `article` VALUES (22, 1, '<img src=\'http://image.my.com/2020/03/11/07e2bbf1297f4959b25d594569697e93.png\'/><hr><pre>\nawdaddaw\n</pre>', '2020-03-11 20:03:10', '2020-03-11 20:03:10', 0, 0, 0, 0, 5, '测试', 0, 0, 1);
INSERT INTO `article` VALUES (23, 1, '<pre>\n富文本编辑器的终极测试\n</pre><img src=\'http://image.my.com/2020/03/11/06381bb263fb41cda9cb7d2c89516a81.png\'/><hr><img src=\'http://image.my.com/2020/03/11/276bbbbd17d34897b77b22b24670d177.png\'/><pre>\n大家好\n</pre><a href=\'http://www.baidu.com\'>http://www.baidu.com</a>', '2020-03-11 20:06:38', '2020-03-11 20:08:55', 9, 0, 1, 0, 1, '富文本编辑器终极配置', 0, 0, 70);
INSERT INTO `article` VALUES (24, 1, '<img src=\'http://image.my.com/2020/03/11/3e8c77d6658a42ba87f7d6111f71b8bf.png\'/><a href=\'http://www.baidu.com\'>http://www.baidu.com</a><pre>\n重要信息\n</pre><hr><hr><hr><hr>伟大达娃啊挖法', '2020-03-11 20:32:19', '2020-03-11 20:32:41', 0, 0, 0, 0, 6, '12312311', 0, 0, 6);

-- ----------------------------
-- Table structure for article_collection
-- ----------------------------
DROP TABLE IF EXISTS `article_collection`;
CREATE TABLE `article_collection`  (
  `collection_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `article_id` bigint(20) DEFAULT NULL,
  `created_time` datetime(0) DEFAULT NULL,
  `title` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci DEFAULT NULL,
  PRIMARY KEY (`collection_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_collection
-- ----------------------------
INSERT INTO `article_collection` VALUES (1, 1, 16, '2020-03-07 22:33:25', 'afwa ');
INSERT INTO `article_collection` VALUES (19, 1, 20, '2020-03-09 20:34:08', '123');

-- ----------------------------
-- Table structure for article_column
-- ----------------------------
DROP TABLE IF EXISTS `article_column`;
CREATE TABLE `article_column`  (
  `column_id` int(11) NOT NULL AUTO_INCREMENT,
  `column_name` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci DEFAULT NULL,
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_column
-- ----------------------------
INSERT INTO `article_column` VALUES (1, '动态');
INSERT INTO `article_column` VALUES (2, '公告');
INSERT INTO `article_column` VALUES (3, '建议');
INSERT INTO `article_column` VALUES (4, '讨论');
INSERT INTO `article_column` VALUES (5, '分享');
INSERT INTO `article_column` VALUES (6, '提问');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) DEFAULT NULL,
  `content` text CHARACTER SET gbk COLLATE gbk_chinese_ci,
  `user_id` bigint(20) DEFAULT NULL,
  `created_time` datetime(0) DEFAULT NULL,
  `updated_time` datetime(0) DEFAULT NULL,
  `agreed` int(11) DEFAULT NULL,
  `selected` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = gbk COLLATE = gbk_chinese_ci COMMENT = '文章评论' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (6, 3, '了解了解', 2, '2020-03-04 22:22:08', '2020-03-04 22:22:08', 0, 0);
INSERT INTO `comment` VALUES (7, 3, '啊啊挖法', 2, '2020-03-04 22:22:20', '2020-03-04 22:22:20', 0, 0);
INSERT INTO `comment` VALUES (8, 2, '欢迎大家', 2, '2020-03-04 22:22:45', '2020-03-04 22:22:45', 1, 1);
INSERT INTO `comment` VALUES (9, 15, '啊我发我', 1, '2020-03-05 20:10:19', '2020-03-05 20:10:19', 0, 0);
INSERT INTO `comment` VALUES (10, 15, '啊伟大伟大', 1, '2020-03-05 20:10:22', '2020-03-05 20:10:22', 0, 0);
INSERT INTO `comment` VALUES (11, 15, '案发无法', 1, '2020-03-05 20:10:47', '2020-03-05 20:10:47', 0, 0);
INSERT INTO `comment` VALUES (15, 16, 'faef', 1, '2020-03-08 17:36:34', '2020-03-08 17:36:34', 0, 0);
INSERT INTO `comment` VALUES (16, 20, 'adfaw', 1, '2020-03-09 20:31:13', '2020-03-09 20:31:13', 0, 0);
INSERT INTO `comment` VALUES (17, 2, '嘿嘿', 1, '2020-03-10 21:10:24', '2020-03-10 21:10:24', 0, 0);
INSERT INTO `comment` VALUES (18, 2, 'OK', 1, '2020-03-10 21:10:28', '2020-03-10 21:10:28', 0, 0);
INSERT INTO `comment` VALUES (19, 2, '哈哈哈哈', 1, '2020-03-10 21:10:32', '2020-03-10 21:10:32', 0, 0);
INSERT INTO `comment` VALUES (21, 23, '嘿嘿', 1, '2020-03-12 14:57:50', '2020-03-12 14:57:50', 0, 0);
INSERT INTO `comment` VALUES (22, 23, '哇', 1, '2020-03-12 15:01:56', '2020-03-12 15:01:56', 0, 0);
INSERT INTO `comment` VALUES (25, 23, '我的', 1, '2020-03-12 15:27:56', '2020-03-12 15:27:56', 0, 0);
INSERT INTO `comment` VALUES (26, 23, 'adw', 1, '2020-03-12 15:29:24', '2020-03-12 15:29:24', 0, 0);
INSERT INTO `comment` VALUES (27, 23, '<img src=\'http://image.my.com/2020/03/12/03348761f97a4f4f9e1475aceb214ee9.png\'/>', 1, '2020-03-12 15:32:16', '2020-03-12 15:32:16', 0, 0);
INSERT INTO `comment` VALUES (28, 23, '<pre>@<a href=\'javascript:;\' class=\'fly-aite\'>11133333</a></pre>', 1, '2020-03-12 15:40:50', '2020-03-12 15:40:50', 0, 0);
INSERT INTO `comment` VALUES (29, 23, '123', 1, '2020-03-12 16:57:25', '2020-03-12 16:57:25', 0, 0);
INSERT INTO `comment` VALUES (31, 23, '<pre>@<a href=\'/user/home?userId=1\' class=\'fly-aite\'>11133333</a></pre>456', 1, '2020-03-12 17:05:23', '2020-03-12 17:05:23', 0, 0);
INSERT INTO `comment` VALUES (32, 23, '短消息测试', 1, '2020-03-12 17:27:39', '2020-03-12 17:27:39', 0, 0);

-- ----------------------------
-- Table structure for comment_like
-- ----------------------------
DROP TABLE IF EXISTS `comment_like`;
CREATE TABLE `comment_like`  (
  `like_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `created_time` datetime(0) DEFAULT NULL,
  `article_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`like_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment_like
-- ----------------------------
INSERT INTO `comment_like` VALUES (2, 8, 1, '2020-03-10 19:54:32', 2);
INSERT INTO `comment_like` VALUES (4, 20, 1, '2020-03-11 20:32:45', 24);

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager`  (
  `manage_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `manage_level` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`manage_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `message_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `to_user_id` bigint(20) DEFAULT NULL,
  `from_user_id` bigint(20) DEFAULT NULL,
  `content` text CHARACTER SET gbk COLLATE gbk_chinese_ci,
  `article_id` bigint(20) DEFAULT NULL,
  `comment_id` bigint(20) DEFAULT NULL,
  `created_time` datetime(0) DEFAULT NULL,
  `viewed` int(11) DEFAULT 0,
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci DEFAULT NULL,
  `password` varchar(32) CHARACTER SET gbk COLLATE gbk_chinese_ci DEFAULT NULL,
  `created_time` datetime(0) DEFAULT NULL,
  `updated_time` datetime(0) DEFAULT NULL,
  `location` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci DEFAULT NULL,
  `kissed` bigint(20) DEFAULT NULL,
  `email` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci DEFAULT NULL,
  `gender` char(1) CHARACTER SET gbk COLLATE gbk_chinese_ci DEFAULT NULL,
  `signature` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci DEFAULT NULL,
  `picture` char(100) CHARACTER SET gbk COLLATE gbk_chinese_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = gbk COLLATE = gbk_chinese_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '11133333', 'e10adc3949ba59abbe56e057f20f883e', '2020-03-01 21:34:35', '2020-03-12 10:54:10', '湖北武汉', 5, '123@qq.com', 'f', 'hhhh234234', 'http://image.my.com/2020/03/11/b91f2110f8bf4a6caa28827ab86bc5a4.png');
INSERT INTO `user` VALUES (2, 'Saphir', 'e10adc3949ba59abbe56e057f20f883e', '2020-03-04 19:17:22', '2020-03-04 19:17:22', '湖北武汉', 0, '123@123.com', 'm', 'hhhh', 'http://image.my.com/2020/03/09/80931d0d6b7140d9ac7f0ad9c2912dcf.png');

-- ----------------------------
-- Table structure for user_signin
-- ----------------------------
DROP TABLE IF EXISTS `user_signin`;
CREATE TABLE `user_signin`  (
  `sign_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `latest_time` date DEFAULT NULL,
  `continue_sign` int(11) DEFAULT NULL,
  PRIMARY KEY (`sign_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_signin
-- ----------------------------
INSERT INTO `user_signin` VALUES (17, 1, '2020-03-12', 0);

SET FOREIGN_KEY_CHECKS = 1;
