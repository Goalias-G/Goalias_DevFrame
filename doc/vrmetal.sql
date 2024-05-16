/*
 Navicat Premium Data Transfer

 Source Server         : mysql01
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : vrmetal

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 17/03/2024 12:49:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chat_effect
-- ----------------------------
DROP TABLE IF EXISTS `chat_effect`;
CREATE TABLE `chat_effect`  (
  `user_id` int NOT NULL,
  `emo_low` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `emo_high` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `chat_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chat_effect
-- ----------------------------

-- ----------------------------
-- Table structure for chatting
-- ----------------------------
DROP TABLE IF EXISTS `chatting`;
CREATE TABLE `chatting`  (
  `user_id` int NOT NULL,
  `chat_details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `chat_time` datetime NULL DEFAULT NULL,
  `chat_emo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of chatting
-- ----------------------------
INSERT INTO `chatting` VALUES (1, 'talk', '2024-03-11 15:18:45', '10');
INSERT INTO `chatting` VALUES (1, 'talk', '2024-03-16 17:29:07', '55');
INSERT INTO `chatting` VALUES (1, 'asd', '2024-03-05 19:21:42', '11');
INSERT INTO `chatting` VALUES (1, 'sad', '2024-03-06 19:21:52', '44');
INSERT INTO `chatting` VALUES (1, 'zzz', '2024-03-14 19:22:09', '22');

-- ----------------------------
-- Table structure for clinic_details
-- ----------------------------
DROP TABLE IF EXISTS `clinic_details`;
CREATE TABLE `clinic_details`  (
  `user_id` int NOT NULL,
  `diagnosis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `clinic_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of clinic_details
-- ----------------------------
INSERT INTO `clinic_details` VALUES (1, '某某心理疾病', '2024-03-16 14:14:14');
INSERT INTO `clinic_details` VALUES (2, '抑郁症', '2024-03-16 17:46:13');

-- ----------------------------
-- Table structure for doctor
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor`  (
  `id` int NOT NULL,
  `age` int NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone_num` int NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `patient_num` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of doctor
-- ----------------------------
INSERT INTO `doctor` VALUES (1, 12, '女', '小熊医生', 110, '123456', 3);

-- ----------------------------
-- Table structure for drug
-- ----------------------------
DROP TABLE IF EXISTS `drug`;
CREATE TABLE `drug`  (
  `drug_id` int NOT NULL,
  `drug_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `drug_details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `drug_num` int NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of drug
-- ----------------------------
INSERT INTO `drug` VALUES (1, '敌敌畏', '没毒，建议饭后喝', 2);
INSERT INTO `drug` VALUES (1, '敌敌畏', '没毒，建议饭后喝', 2);

-- ----------------------------
-- Table structure for manage
-- ----------------------------
DROP TABLE IF EXISTS `manage`;
CREATE TABLE `manage`  (
  `id` int NOT NULL,
  `manage_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `manage_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of manage
-- ----------------------------
INSERT INTO `manage` VALUES (1, 'manager', '111111');

-- ----------------------------
-- Table structure for model_detail
-- ----------------------------
DROP TABLE IF EXISTS `model_detail`;
CREATE TABLE `model_detail`  (
  `user_id` int NOT NULL,
  `voice_now` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `voice_history` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `character_now` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `character_history` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `env_now` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `env_history` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of model_detail
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `register_time` datetime NULL DEFAULT NULL,
  `chat_time` datetime NULL DEFAULT NULL,
  `portrait` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '男', 'gws', '123456', '17667393212', 21, '2024-03-10 19:59:16', '2024-03-07 19:59:43', 'https://test-7758.oss-cn-hangzhou.aliyuncs.com/%E4%BA%BA%E7%89%A9%E7%94%BB%E5%83%8F.png');
INSERT INTO `user` VALUES (4, 'user', '女', 'test', '123456', '1244', 11, '2024-03-13 08:58:09', '2024-03-13 08:58:14', 'https://test-7758.oss-cn-hangzhou.aliyuncs.com/%E4%BA%BA%E7%89%A9%E7%94%BB%E5%83%8F.png');
INSERT INTO `user` VALUES (5, 'yonghu', '男', 'cat', '123456', '1231111', 60, '2024-03-13 08:58:11', '2024-03-13 08:58:16', 'https://test-7758.oss-cn-hangzhou.aliyuncs.com/%E4%BA%BA%E7%89%A9%E7%94%BB%E5%83%8F.png');
INSERT INTO `user` VALUES (7, '9PZMBjaWut', '男', 'Yan Jiehong', '123456', '7675 110633', 111, '2017-05-07 00:31:53', '2007-07-20 18:59:11', 'https://test-7758.oss-cn-hangzhou.aliyuncs.com/%E4%BA%BA%E7%89%A9%E7%94%BB%E5%83%8F.png');
INSERT INTO `user` VALUES (9, 'Um3WzYyiRq', '女', 'Marie Jordan', '5pXrJPp0qp', '80-8492-2340', 28, '2004-02-01 01:52:27', '2020-09-06 07:39:54', 'https://test-7758.oss-cn-hangzhou.aliyuncs.com/%E4%BA%BA%E7%89%A9%E7%94%BB%E5%83%8F.png');
INSERT INTO `user` VALUES (10, '地址、', NULL, '', '', NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
