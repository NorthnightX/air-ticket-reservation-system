/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : air_ticket_reservation_system

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 27/12/2023 12:52:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `administrator_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES (1, 'admin1', 'admin', '4297f44b13955235245b2497399d7a93', NULL, '1', '1');
INSERT INTO `administrator` VALUES (3, 'admin11', 'admin1', '4297f44b13955235245b2497399d7a93', '', '2', '1');
INSERT INTO `administrator` VALUES (4, 'admin12', 'admin2', '4297f44b13955235245b2497399d7a93', '', '2', '0');
INSERT INTO `administrator` VALUES (5, 'admin1231', 'admin3', '4297f44b13955235245b2497399d7a93', '', '2', '0');
INSERT INTO `administrator` VALUES (6, 'admin13', 'admin4', '4297f44b13955235245b2497399d7a93', '', '2', '1');
INSERT INTO `administrator` VALUES (7, 'admin14', 'admin5', '4297f44b13955235245b2497399d7a93', '', '2', '1');
INSERT INTO `administrator` VALUES (8, 'admin123', 'admin6', '4297f44b13955235245b2497399d7a93', '', '2', '1');
INSERT INTO `administrator` VALUES (9, 'admin112', 'admin7', '4297f44b13955235245b2497399d7a93', '', '2', '1');
INSERT INTO `administrator` VALUES (10, 'admin1d', 'admin89', '4297f44b13955235245b2497399d7a93', '', '2', '1');
INSERT INTO `administrator` VALUES (28, 'user', 'user', '4297f44b13955235245b2497399d7a93', NULL, '2', '1');
INSERT INTO `administrator` VALUES (29, 'user11', 'user11', '4297f44b13955235245b2497399d7a93', NULL, '1', '1');

-- ----------------------------
-- Table structure for agent
-- ----------------------------
DROP TABLE IF EXISTS `agent`;
CREATE TABLE `agent`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `qualification_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `level` int NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agent
-- ----------------------------
INSERT INTO `agent` VALUES (6, 'agent5', 'agent', '4297f44b13955235245b2497399d7a93', '', 'dsafagfadshagsdFd', 2, '1');
INSERT INTO `agent` VALUES (9, 'agent8', 'agent1', '4297f44b13955235245b2497399d7a93', '', 'dsafagfadshagsdFd', 4, '1');
INSERT INTO `agent` VALUES (11, 'agent101', 'agent2', '4297f44b13955235245b2497399d7a93', '', '31232112312', 10, '1');
INSERT INTO `agent` VALUES (39, 'user', 'user', '4297f44b13955235245b2497399d7a93', NULL, 'user', 1, '1');

-- ----------------------------
-- Table structure for aircraft_information
-- ----------------------------
DROP TABLE IF EXISTS `aircraft_information`;
CREATE TABLE `aircraft_information`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `model_id` int NULL DEFAULT NULL,
  `aircraft_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `purchase_date` date NULL DEFAULT NULL,
  `service_years` int NULL DEFAULT NULL,
  `last_maintenance_date` date NULL DEFAULT NULL,
  `publish_time` datetime NULL DEFAULT NULL,
  `modify_time` datetime NULL DEFAULT NULL,
  `creator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `modifier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `model_id`(`model_id`) USING BTREE,
  CONSTRAINT `aircraft_information_ibfk_1` FOREIGN KEY (`model_id`) REFERENCES `aircraft_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of aircraft_information
-- ----------------------------
INSERT INTO `aircraft_information` VALUES (1, 1, 'ABC1234', '2020-02-20', 3, '2022-12-30', '2023-06-28 14:45:16', '2023-07-03 01:21:11', 'John Doe', 'admin1');
INSERT INTO `aircraft_information` VALUES (2, 2, 'DEF456', '2019-05-15', 4, '2023-02-28', '2023-06-28 14:45:16', '2023-06-30 15:02:08', 'Jane Smith', 'admin1');
INSERT INTO `aircraft_information` VALUES (3, 3, 'GHI789', '2021-08-01', 2, '2023-06-10', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Michael Johnson', 'Michael Johnson');
INSERT INTO `aircraft_information` VALUES (4, 4, 'JKL012', '2018-12-05', 6, '2023-01-05', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Emily Davis', 'Emily Davis');
INSERT INTO `aircraft_information` VALUES (5, 5, 'MNO345', '2020-06-20', 4, '2022-11-15', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Robert Wilson', 'Robert Wilson');
INSERT INTO `aircraft_information` VALUES (6, 6, 'PQR678', '2019-03-10', 5, '2023-03-25', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Laura Thompson', 'Laura Thompson');
INSERT INTO `aircraft_information` VALUES (7, 7, 'STU901', '2021-11-18', 1, '2023-05-08', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'David Martinez', 'David Martinez');
INSERT INTO `aircraft_information` VALUES (8, 8, 'VWX234', '2020-02-28', 3, '2022-09-30', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Sarah Anderson', 'Sarah Anderson');
INSERT INTO `aircraft_information` VALUES (9, 9, 'YZA567', '2019-07-12', 4, '2023-04-12', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Daniel Brown', 'Daniel Brown');
INSERT INTO `aircraft_information` VALUES (10, 10, 'BCD890', '2018-09-02', 6, '2022-12-15', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Olivia Garcia', 'Olivia Garcia');
INSERT INTO `aircraft_information` VALUES (11, 11, 'EFG123', '2020-03-25', 3, '2023-01-20', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Matthew Wilson', 'Matthew Wilson');
INSERT INTO `aircraft_information` VALUES (12, 12, 'HIJ456', '2019-06-10', 4, '2023-02-05', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Ava Rodriguez', 'Ava Rodriguez');
INSERT INTO `aircraft_information` VALUES (13, 13, 'KLM789', '2021-09-15', 2, '2023-06-05', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'James Moore', 'James Moore');
INSERT INTO `aircraft_information` VALUES (14, 14, 'NOP012', '2018-11-30', 6, '2023-03-10', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Emma Thomas', 'Emma Thomas');
INSERT INTO `aircraft_information` VALUES (15, 15, 'PQR345', '2020-07-05', 4, '2022-10-25', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Christopher Lee', 'Christopher Lee');
INSERT INTO `aircraft_information` VALUES (16, 16, 'STU678', '2019-04-20', 5, '2023-01-15', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Sophia Wilson', 'Sophia Wilson');
INSERT INTO `aircraft_information` VALUES (17, 17, 'VWX901', '2021-12-28', 1, '2023-05-20', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Daniel Taylor', 'Daniel Taylor');
INSERT INTO `aircraft_information` VALUES (18, 18, 'YZA234', '2020-04-10', 3, '2022-09-12', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Isabella Hernandez', 'Isabella Hernandez');
INSERT INTO `aircraft_information` VALUES (19, 19, 'BCD567', '2019-08-25', 4, '2023-04-05', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Joseph Martinez', 'Joseph Martinez');
INSERT INTO `aircraft_information` VALUES (20, 20, 'EFG890', '2018-10-15', 6, '2022-12-30', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Mia Thompson', 'Mia Thompson');
INSERT INTO `aircraft_information` VALUES (21, 21, 'HIJ123', '2020-04-30', 3, '2023-01-25', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'William Davis', 'William Davis');
INSERT INTO `aircraft_information` VALUES (22, 22, 'KLM456', '2019-07-15', 4, '2023-02-10', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Sophia Wilson', 'Sophia Wilson');
INSERT INTO `aircraft_information` VALUES (23, 23, 'NOP789', '2021-10-20', 2, '2023-06-01', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Elijah Johnson', 'Elijah Johnson');
INSERT INTO `aircraft_information` VALUES (24, 24, 'PQR012', '2018-12-02', 6, '2023-03-05', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Grace Anderson', 'Grace Anderson');
INSERT INTO `aircraft_information` VALUES (25, 25, 'STU345', '2020-08-10', 4, '2022-11-30', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Benjamin Brown', 'Benjamin Brown');
INSERT INTO `aircraft_information` VALUES (26, 26, 'VWX678', '2019-05-25', 5, '2023-01-30', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Avery Garcia', 'Avery Garcia');
INSERT INTO `aircraft_information` VALUES (27, 27, 'YZA901', '2022-01-05', 1, '2023-05-25', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Lucas Wilson', 'Lucas Wilson');
INSERT INTO `aircraft_information` VALUES (28, 28, 'BCD234', '2020-05-15', 3, '2022-09-20', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Scarlett Moore', 'Scarlett Moore');
INSERT INTO `aircraft_information` VALUES (29, 29, 'EFG567', '2019-09-30', 4, '2023-04-20', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Henry Thomas', 'Henry Thomas');
INSERT INTO `aircraft_information` VALUES (30, 30, 'HIJ890', '2018-11-20', 6, '2022-12-05', '2023-06-28 14:45:16', '2023-06-28 14:45:16', 'Chloe Rodriguez', 'Chloe Rodriguez');
INSERT INTO `aircraft_information` VALUES (33, 1, '111', '2023-06-29', 0, '2023-07-07', '2023-06-29 11:40:38', '2023-06-29 11:41:26', 'admin1', 'admin1');

-- ----------------------------
-- Table structure for aircraft_type
-- ----------------------------
DROP TABLE IF EXISTS `aircraft_type`;
CREATE TABLE `aircraft_type`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `model` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `passenger_capacity` int NULL DEFAULT NULL,
  `first_class_capacity` int NULL DEFAULT NULL,
  `economy_class_capacity` int NULL DEFAULT NULL,
  `publish_time` datetime NULL DEFAULT NULL,
  `modify_time` datetime NULL DEFAULT NULL,
  `creator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `modifier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of aircraft_type
-- ----------------------------
INSERT INTO `aircraft_type` VALUES (1, 'Boeing 737', 150, 16, 134, '2023-06-28 10:02:45', '2023-07-03 01:21:07', 'John Doe', 'admin1');
INSERT INTO `aircraft_type` VALUES (2, 'Airbus A320', 180, 12, 168, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Jane Smith', 'Jane Smith');
INSERT INTO `aircraft_type` VALUES (3, 'Boeing 787', 250, 30, 220, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Michael Johnson', 'Michael Johnson');
INSERT INTO `aircraft_type` VALUES (4, 'Airbus A380', 500, 50, 450, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Emily Davis', 'Emily Davis');
INSERT INTO `aircraft_type` VALUES (5, 'Embraer E190', 100, 10, 90, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Robert Wilson', 'Robert Wilson');
INSERT INTO `aircraft_type` VALUES (6, 'Bombardier CRJ900', 90, 8, 82, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Laura Thompson', 'Laura Thompson');
INSERT INTO `aircraft_type` VALUES (7, 'Boeing 747', 400, 40, 360, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'David Martinez', 'David Martinez');
INSERT INTO `aircraft_type` VALUES (8, 'Airbus A330', 300, 24, 276, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Sarah Anderson', 'Sarah Anderson');
INSERT INTO `aircraft_type` VALUES (9, 'Embraer E195', 108, 12, 96, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Daniel Brown', 'Daniel Brown');
INSERT INTO `aircraft_type` VALUES (10, 'Bombardier CS300', 135, 14, 121, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Olivia Garcia', 'Olivia Garcia');
INSERT INTO `aircraft_type` VALUES (11, 'Boeing 777', 350, 32, 318, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Matthew Wilson', 'Matthew Wilson');
INSERT INTO `aircraft_type` VALUES (12, 'Airbus A350', 320, 28, 292, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Ava Rodriguez', 'Ava Rodriguez');
INSERT INTO `aircraft_type` VALUES (13, 'Embraer E170', 76, 8, 68, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'James Moore', 'James Moore');
INSERT INTO `aircraft_type` VALUES (14, 'Bombardier CRJ700', 70, 6, 64, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Emma Thomas', 'Emma Thomas');
INSERT INTO `aircraft_type` VALUES (15, 'Boeing 767', 280, 20, 260, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Christopher Lee', 'Christopher Lee');
INSERT INTO `aircraft_type` VALUES (16, 'Airbus A321', 200, 16, 184, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Sophia Wilson', 'Sophia Wilson');
INSERT INTO `aircraft_type` VALUES (17, 'Embraer E175', 88, 10, 78, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Daniel Taylor', 'Daniel Taylor');
INSERT INTO `aircraft_type` VALUES (18, 'Bombardier CRJ200', 50, 4, 46, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Isabella Lewis', 'Isabella Lewis');
INSERT INTO `aircraft_type` VALUES (19, 'Boeing 737 MAX', 180, 16, 164, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Mia Anderson', 'Mia Anderson');
INSERT INTO `aircraft_type` VALUES (20, 'Airbus A319', 160, 14, 146, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'William Davis', 'William Davis');
INSERT INTO `aircraft_type` VALUES (21, 'Embraer E145', 50, 4, 46, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Ella Hernandez', 'Ella Hernandez');
INSERT INTO `aircraft_type` VALUES (22, 'Bombardier Q400', 78, 6, 72, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Benjamin Martinez', 'Benjamin Martinez');
INSERT INTO `aircraft_type` VALUES (23, 'Boeing 757', 240, 24, 216, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Victoria Garcia', 'Victoria Garcia');
INSERT INTO `aircraft_type` VALUES (24, 'Airbus A318', 110, 10, 100, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Alexander Brown', 'Alexander Brown');
INSERT INTO `aircraft_type` VALUES (25, 'Embraer E135', 37, 4, 33, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Sofia Wilson', 'Sofia Wilson');
INSERT INTO `aircraft_type` VALUES (26, 'Bombardier CRJ1000', 104, 10, 94, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Jackson Thompson', 'Jackson Thompson');
INSERT INTO `aircraft_type` VALUES (27, 'Boeing 737 NG', 160, 16, 144, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Aiden Thomas', 'Aiden Thomas');
INSERT INTO `aircraft_type` VALUES (28, 'Airbus A317', 100, 10, 90, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Madison Taylor', 'Madison Taylor');
INSERT INTO `aircraft_type` VALUES (29, 'Embraer E165', 38, 4, 34, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Liam Hernandez', 'Liam Hernandez');
INSERT INTO `aircraft_type` VALUES (30, 'Bombardier CRJ550', 50, 6, 44, '2023-06-28 10:02:45', '2023-06-28 10:02:45', 'Penelope Martinez', 'Penelope Martinez');

-- ----------------------------
-- Table structure for airport
-- ----------------------------
DROP TABLE IF EXISTS `airport`;
CREATE TABLE `airport`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `airport_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `airport_coordinate_x` float NULL DEFAULT NULL,
  `airport_coordinate_y` float NULL DEFAULT NULL,
  `publish_time` datetime NULL DEFAULT NULL,
  `modify_time` datetime NULL DEFAULT NULL,
  `creator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `modifier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 75 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of airport
-- ----------------------------
INSERT INTO `airport` VALUES (1, '北京市', '北京市', '北京首都国际机场', 39.9022, 116.407, '2023-06-27 10:00:00', '2023-07-03 01:53:32', 'John', 'admin1');
INSERT INTO `airport` VALUES (2, '上海市', '上海市', '上海浦东国际机场', 31.1443, 121.808, '2023-06-27 10:00:00', '2023-06-30 15:01:50', 'John', 'admin1');
INSERT INTO `airport` VALUES (3, '广东省', '广州市', '广州白云国际机场', 23.3964, 113.308, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (4, '江苏省', '南京市', '南京禄口国际机场', 31.7353, 118.867, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (5, '浙江省', '杭州市', '杭州萧山国际机场', 30.2361, 120.432, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (6, '四川省', '成都市', '成都双流国际机场', 30.5785, 103.946, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (7, '湖北省', '武汉市', '武汉天河国际机场', 30.7808, 114.208, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (8, '河南省', '郑州市', '郑州新郑国际机场', 34.5292, 113.841, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (9, '湖南省', '长沙市', '长沙黄花国际机场', 28.1891, 113.221, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (10, '江西省', '南昌市', '南昌昌北国际机场', 28.865, 115.908, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (11, '广东省', '深圳市', '深圳宝安国际机场', 22.6397, 113.81, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (12, '山东省', '青岛市', '青岛流亭国际机场', 36.2633, 120.381, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (13, '湖南省', '长沙市', '长沙黄花国际机场', 28.1891, 113.221, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (14, '河北省', '石家庄市', '石家庄正定国际机场', 38.2813, 114.699, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (15, '福建省', '厦门市', '厦门高崎国际机场', 24.5444, 118.127, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (16, '江苏省', '苏州市', '苏州广济国际机场', 31.3012, 120.598, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (17, '浙江省', '温州市', '温州龙湾国际机场', 27.9144, 120.852, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (18, '广东省', '珠海市', '珠海金湾国际机场', 22.0076, 113.377, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (19, '四川省', '乐山市', '乐山乐家坪国际机场', 29.5184, 103.748, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (20, '云南省', '昆明市', '昆明长水国际机场', 24.9938, 102.743, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (21, '陕西省', '西安市', '西安咸阳国际机场', 34.4416, 108.752, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (22, '江西省', '赣州市', '赣州黄金国际机场', 25.8653, 114.925, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (23, '山东省', '烟台市', '烟台蓬莱国际机场', 37.6693, 120.981, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (24, '福建省', '福州市', '福州长乐国际机场', 25.9324, 119.662, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (25, '广西壮族自治区', '南宁市', '南宁吴圩国际机场', 22.6136, 108.173, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (26, '重庆市', '重庆市', '重庆江北国际机场', 29.7199, 106.641, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (27, '甘肃省', '兰州市', '兰州中川国际机场', 36.5163, 103.621, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (28, '山西省', '太原市', '太原武宿国际机场', 37.7509, 112.632, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (29, '新疆维吾尔自治区', '乌鲁木齐市', '乌鲁木齐地窝堡国际机场', 43.9078, 87.4749, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (30, '青海省', '西宁市', '西宁曹家堡国际机场', 36.5274, 102.041, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (31, '黑龙江省', '哈尔滨市', '哈尔滨太平国际机场', 45.6215, 126.241, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (32, '广东省', '深圳市', '深圳宝安国际机场', 22.6397, 113.81, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (33, '山东省', '青岛市', '青岛流亭国际机场', 36.2633, 120.381, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (34, '湖南省', '长沙市', '长沙黄花国际机场', 28.1891, 113.221, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (35, '河北省', '石家庄市', '石家庄正定国际机场', 38.2813, 114.699, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (36, '福建省', '厦门市', '厦门高崎国际机场', 24.5444, 118.127, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (37, '江苏省', '苏州市', '苏州广济国际机场', 31.3012, 120.598, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (38, '浙江省', '温州市', '温州龙湾国际机场', 27.9144, 120.852, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (39, '广东省', '珠海市', '珠海金湾国际机场', 22.0076, 113.377, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (40, '四川省', '乐山市', '乐山乐家坪国际机场', 29.5184, 103.748, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (41, '云南省', '昆明市', '昆明长水国际机场', 24.9938, 102.743, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (42, '陕西省', '西安市', '西安咸阳国际机场', 34.4416, 108.752, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (43, '江西省', '赣州市', '赣州黄金国际机场', 25.8653, 114.925, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (44, '山东省', '烟台市', '烟台蓬莱国际机场', 37.6693, 120.981, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (45, '福建省', '福州市', '福州长乐国际机场', 25.9324, 119.662, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (46, '广西壮族自治区', '南宁市', '南宁吴圩国际机场', 22.6136, 108.173, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (47, '重庆市', '重庆市', '重庆江北国际机场', 29.7199, 106.641, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (48, '甘肃省', '兰州市', '兰州中川国际机场', 36.5163, 103.621, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (49, '山西省', '太原市', '太原武宿国际机场', 37.7509, 112.632, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (50, '新疆维吾尔自治区', '乌鲁木齐市', '乌鲁木齐地窝堡国际机场', 43.9078, 87.4749, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (51, '青海省', '西宁市', '西宁曹家堡国际机场', 36.5274, 102.041, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (52, '黑龙江省', '哈尔滨市', '哈尔滨太平国际机场', 45.6215, 126.241, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (53, '山西省', '大同市', '大同云冈国际机场', 40.0549, 113.483, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (54, '江苏省', '无锡市', '无锡硕放国际机场', 31.4944, 120.433, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (55, '福建省', '泉州市', '泉州晋江国际机场', 24.7995, 118.59, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (56, '湖南省', '怀化市', '怀化芷江国际机场', 27.5469, 109.699, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (57, '贵州省', '贵阳市', '贵阳龙洞堡国际机场', 26.535, 106.798, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (58, '四川省', '德阳市', '德阳天府国际机场', 31.1289, 104.398, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (59, '云南省', '大理市', '大理荒草坝国际机场', 25.6663, 100.32, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (60, '广东省', '梅州市', '梅州梅县国际机场', 24.3144, 116.123, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (61, '湖南省', '张家界市', '张家界荷花国际机场', 29.108, 110.464, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (62, '辽宁省', '大连市', '大连周水子国际机场', 38.9637, 121.536, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (63, '陕西省', '延安市', '延安二十里铺国际机场', 36.6302, 109.555, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (64, '湖南省', '益阳市', '益阳岳阳国际机场', 28.8011, 112.36, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (65, '山东省', '济宁市', '济宁曲阜国际机场', 35.4054, 116.259, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (66, '广东省', '湛江市', '湛江乐东国际机场', 21.2047, 110.361, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (67, '江苏省', '南京市', '南京禄口国际机场', 31.7354, 118.867, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (68, '黑龙江省', '齐齐哈尔市', '齐齐哈尔三家子国际机场', 47.243, 123.915, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (69, '湖北省', '荆州市', '荆州沙市国际机场', 30.3129, 112.239, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (70, '河南省', '安阳市', '安阳林州国际机场', 36.0246, 113.833, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (71, '浙江省', '宁波市', '宁波栎社国际机场', 29.8181, 121.462, '2023-06-27 10:00:00', '2023-06-28 08:25:00', 'John', 'admin1');
INSERT INTO `airport` VALUES (72, '福建省', '莆田市', '莆田涵江国际机场', 25.9789, 119.091, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `airport` VALUES (73, '广东省', '东莞市', '东莞虎门国际机场', 22.7559, 113.482, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');

-- ----------------------------
-- Table structure for announcement
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `publish_time` datetime NULL DEFAULT NULL,
  `modify_time` datetime NULL DEFAULT NULL,
  `creator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `modifier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `ttl` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of announcement
-- ----------------------------
INSERT INTO `announcement` VALUES (1, 'Test Announcement 1111', 'This is the content of test announcement 1This is the content of test announcement 1.This is the content of test announcement 1.This is the content of test announcement 1.This is the content of test announcement 1.This is the content of test announcement 1.This is the content of test announcement 1..', '2023-06-29 09:00:00', '2023-07-03 01:23:27', 'John Doe', 'admin1', 1, '2023-06-29 16:00:40');
INSERT INTO `announcement` VALUES (2, 'Test Announcement 2', 'This is the content of test announcement 2.', '2023-06-29 10:00:00', '2023-06-29 10:00:00', 'Jane Smith', 'Jane Smith', 1, '2023-07-29 10:00:00');
INSERT INTO `announcement` VALUES (3, 'Test Announcement 3', 'This is the content of test announcement 3.', '2023-06-29 11:00:00', '2023-06-29 11:00:00', 'John Doe', 'John Doe', 1, '2023-07-29 11:00:00');
INSERT INTO `announcement` VALUES (4, 'Test Announcement 4', 'This is the content of test announcement 4.', '2023-06-29 12:00:00', '2023-06-29 12:00:00', 'Jane Smith', 'Jane Smith', 1, '2023-07-29 12:00:00');
INSERT INTO `announcement` VALUES (5, 'Test Announcement 5', 'This is the content of test announcement 5.', '2023-06-29 13:00:00', '2023-06-29 13:00:00', 'John Doe', 'John Doe', 1, '2023-07-29 13:00:00');
INSERT INTO `announcement` VALUES (6, 'Test Announcement 6', 'This is the content of test announcement 6.', '2023-06-29 14:00:00', '2023-06-29 14:00:00', 'Jane Smith', 'Jane Smith', 1, '2023-07-29 14:00:00');
INSERT INTO `announcement` VALUES (7, 'Test Announcement 7', 'This is the content of test announcement 7.', '2023-06-29 15:00:00', '2023-06-29 15:00:00', 'John Doe', 'John Doe', 1, '2023-07-29 15:00:00');
INSERT INTO `announcement` VALUES (8, 'Test Announcement 8', 'This is the content of test announcement 8.', '2023-06-29 16:00:00', '2023-06-29 16:00:00', 'Jane Smith', 'Jane Smith', 1, '2023-07-29 16:00:00');
INSERT INTO `announcement` VALUES (9, 'Test Announcement 9', 'This is the content of test announcement 9.', '2023-06-29 17:00:00', '2023-06-29 17:00:00', 'John Doe', 'John Doe', 1, '2023-07-29 17:00:00');
INSERT INTO `announcement` VALUES (12, 'Test Announcement 12', 'This is the content of test announcement 12.', '2023-06-29 20:00:00', '2023-06-29 20:00:00', 'Jane Smith', 'Jane Smith', 1, '2023-07-29 20:00:00');
INSERT INTO `announcement` VALUES (14, '111', '111', '2023-06-29 16:04:19', '2023-06-29 16:04:19', 'admin1', 'admin1', 1, '2023-06-29 16:04:14');
INSERT INTO `announcement` VALUES (15, '1', '1', '2023-06-29 16:20:50', '2023-06-29 16:20:50', 'admin1', 'admin1', 0, '2023-06-29 17:20:43');

-- ----------------------------
-- Table structure for baggage
-- ----------------------------
DROP TABLE IF EXISTS `baggage`;
CREATE TABLE `baggage`  (
  `baggage_id` bigint NOT NULL AUTO_INCREMENT,
  `baggage_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ticket_id` bigint NULL DEFAULT NULL,
  `baggage_weight` decimal(10, 2) NULL DEFAULT NULL,
  `baggage_item_count` int NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  PRIMARY KEY (`baggage_id`) USING BTREE,
  INDEX `baggage_ibfk_1`(`ticket_id`) USING BTREE,
  CONSTRAINT `baggage_ibfk_1` FOREIGN KEY (`ticket_id`) REFERENCES `orders` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of baggage
-- ----------------------------

-- ----------------------------
-- Table structure for flight
-- ----------------------------
DROP TABLE IF EXISTS `flight`;
CREATE TABLE `flight`  (
  `flight_id` int NOT NULL AUTO_INCREMENT,
  `route_id` int NULL DEFAULT NULL,
  `aircraft_id` int NULL DEFAULT NULL,
  `departure_time` datetime NULL DEFAULT NULL,
  `arrival_time` datetime NULL DEFAULT NULL,
  `first_class_num` int NULL DEFAULT NULL,
  `economy_class_num` int NULL DEFAULT NULL,
  `economy_class_price` decimal(10, 2) NULL DEFAULT NULL,
  `first_class_price` decimal(10, 2) NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `publish_time` datetime NULL DEFAULT NULL,
  `modify_time` datetime NULL DEFAULT NULL,
  `creator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `modifier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`flight_id`) USING BTREE,
  INDEX `route_id`(`route_id`) USING BTREE,
  INDEX `aircraft_id`(`aircraft_id`) USING BTREE,
  CONSTRAINT `flight_ibfk_1` FOREIGN KEY (`route_id`) REFERENCES `route` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `flight_ibfk_2` FOREIGN KEY (`aircraft_id`) REFERENCES `aircraft_information` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 186 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flight
-- ----------------------------
INSERT INTO `flight` VALUES (153, 2, 2, '2023-06-29 10:00:00', '2023-06-29 12:00:00', 20, 134, 150.00, 300.00, 0, '2023-06-28 09:00:00', '2023-07-03 01:21:02', 'John Doe', 'admin1');
INSERT INTO `flight` VALUES (154, 2, 2, '2023-06-30 15:30:00', '2023-06-30 18:00:00', 10, 16, 200.00, 400.00, 1, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Jane Smith', 'Jane Smith');
INSERT INTO `flight` VALUES (155, 3, 3, '2023-07-01 08:45:00', '2023-07-01 10:30:00', 0, 22, 120.50, 240.00, 2, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Michael Johnson', 'Michael Johnson');
INSERT INTO `flight` VALUES (156, 4, 4, '2023-07-02 12:15:00', '2023-07-02 14:00:00', 2, 22, 180.00, 360.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Emily Brown', 'Emily Brown');
INSERT INTO `flight` VALUES (157, 5, 5, '2023-07-03 09:30:00', '2023-07-03 11:15:00', 9, 22, 160.00, 320.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'David Wilson', 'David Wilson');
INSERT INTO `flight` VALUES (158, 6, 6, '2023-07-04 14:20:00', '2023-07-04 16:00:00', 13, 22, 190.00, 380.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Olivia Davis', 'Olivia Davis');
INSERT INTO `flight` VALUES (159, 7, 7, '2023-07-05 11:45:00', '2023-07-05 13:30:00', 11, 18, 140.00, 280.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Daniel Wilson', 'Daniel Wilson');
INSERT INTO `flight` VALUES (160, 8, 8, '2023-07-06 16:10:00', '2023-07-06 17:45:00', 11, 22, 170.00, 340.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Sophia Garcia', 'Sophia Garcia');
INSERT INTO `flight` VALUES (161, 9, 9, '2023-07-07 13:25:00', '2023-07-07 15:00:00', 11, 22, 130.00, 260.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Liam Miller', 'Liam Miller');
INSERT INTO `flight` VALUES (162, 10, 10, '2023-07-08 18:30:00', '2023-07-08 20:15:00', 10, 22, 220.00, 440.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Ava Martinez', 'Ava Martinez');
INSERT INTO `flight` VALUES (163, 2, 11, '2023-07-09 10:00:00', '2023-07-09 12:00:00', 10, 22, 150.00, 300.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'John Doe', 'John Doe');
INSERT INTO `flight` VALUES (164, 2, 12, '2023-07-10 15:30:00', '2023-07-10 18:00:00', 11, 22, 200.00, 400.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Jane Smith', 'Jane Smith');
INSERT INTO `flight` VALUES (165, 3, 13, '2023-07-11 08:45:00', '2023-07-11 10:30:00', 11, 22, 120.50, 240.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Michael Johnson', 'Michael Johnson');
INSERT INTO `flight` VALUES (166, 4, 14, '2023-07-12 12:15:00', '2023-07-12 14:00:00', 11, 22, 180.00, 360.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Emily Brown', 'Emily Brown');
INSERT INTO `flight` VALUES (167, 5, 15, '2023-07-13 09:30:00', '2023-07-13 11:15:00', 11, 22, 160.00, 320.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'David Wilson', 'David Wilson');
INSERT INTO `flight` VALUES (168, 6, 16, '2023-07-14 14:20:00', '2023-07-14 16:00:00', 11, 22, 190.00, 380.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Olivia Davis', 'Olivia Davis');
INSERT INTO `flight` VALUES (169, 7, 17, '2023-07-15 11:45:00', '2023-07-15 13:30:00', 11, 22, 140.00, 280.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Daniel Wilson', 'Daniel Wilson');
INSERT INTO `flight` VALUES (170, 8, 18, '2023-07-16 16:10:00', '2023-07-16 17:45:00', 11, 22, 170.00, 340.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Sophia Garcia', 'Sophia Garcia');
INSERT INTO `flight` VALUES (171, 9, 19, '2023-07-17 13:25:00', '2023-07-17 15:00:00', 11, 21, 130.00, 260.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Liam Miller', 'Liam Miller');
INSERT INTO `flight` VALUES (172, 10, 5, '2023-07-18 18:30:00', '2023-07-18 20:15:00', 11, 16, 220.00, 440.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Ava Martinez', 'Ava Martinez');
INSERT INTO `flight` VALUES (173, 2, 4, '2023-07-19 10:00:00', '2023-07-19 12:00:00', 11, 22, 150.00, 300.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'John Doe', 'John Doe');
INSERT INTO `flight` VALUES (174, 2, 3, '2023-07-20 15:30:00', '2023-07-20 18:00:00', 11, 22, 200.00, 400.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Jane Smith', 'Jane Smith');
INSERT INTO `flight` VALUES (175, 3, 13, '2023-07-21 08:45:00', '2023-07-21 10:30:00', 11, 22, 120.50, 240.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Michael Johnson', 'Michael Johnson');
INSERT INTO `flight` VALUES (176, 4, 2, '2023-07-22 12:15:00', '2023-07-22 14:00:00', 11, 22, 180.00, 360.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Emily Brown', 'Emily Brown');
INSERT INTO `flight` VALUES (177, 5, 16, '2023-07-23 09:30:00', '2023-07-23 11:15:00', 11, 0, 160.00, 320.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'David Wilson', 'David Wilson');
INSERT INTO `flight` VALUES (178, 6, 15, '2023-07-24 14:20:00', '2023-07-24 16:00:00', 11, 22, 190.00, 380.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Olivia Davis', 'Olivia Davis');
INSERT INTO `flight` VALUES (179, 7, 14, '2023-07-25 11:45:00', '2023-07-25 13:30:00', 11, 22, 140.00, 280.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Daniel Wilson', 'Daniel Wilson');
INSERT INTO `flight` VALUES (180, 8, 13, '2023-07-26 16:10:00', '2023-07-26 17:45:00', 11, 22, 170.00, 340.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Sophia Garcia', 'Sophia Garcia');
INSERT INTO `flight` VALUES (181, 9, 12, '2023-07-27 13:25:00', '2023-07-27 15:00:00', 11, 22, 130.00, 260.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Liam Miller', 'Liam Miller');
INSERT INTO `flight` VALUES (182, 10, 11, '2023-07-28 18:30:00', '2023-07-28 20:15:00', 11, 22, 220.00, 440.00, 0, '2023-06-28 09:00:00', '2023-06-28 09:00:00', 'Ava Martinez', 'Ava Martinez');
INSERT INTO `flight` VALUES (185, 3, 2, '2023-06-01 00:00:00', '2023-06-30 00:00:00', 1, 1, 1.00, 1.00, 0, '2023-06-29 11:14:41', '2023-06-29 11:15:29', 'admin1', 'admin1');
INSERT INTO `flight` VALUES (186, 2, 6, '2023-06-29 00:00:00', '2023-07-13 00:00:00', 2, 2, 2.00, 2.00, 0, '2023-06-29 11:18:42', '2023-06-29 11:18:42', 'admin1', 'admin1');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `order_id` bigint NOT NULL AUTO_INCREMENT,
  `flight_id` int NULL DEFAULT NULL,
  `seat_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `booking_person` int NULL DEFAULT NULL,
  `passenger` int NULL DEFAULT NULL,
  `is_checked_baggage` tinyint(1) NULL DEFAULT NULL,
  `amount` decimal(10, 2) NULL DEFAULT NULL,
  `order_time` datetime NULL DEFAULT NULL,
  `is_cancelled` tinyint(1) NULL DEFAULT NULL,
  `cancellation_time` datetime NULL DEFAULT NULL,
  `is_upgrade` tinyint(1) NULL DEFAULT NULL,
  `upgrade_order_id` bigint NULL DEFAULT NULL,
  `is_upgrade_order` int NULL DEFAULT NULL,
  `is_use` int NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `flight_id`(`flight_id`) USING BTREE,
  INDEX `orders_ibfk_2`(`upgrade_order_id`) USING BTREE,
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`flight_id`) REFERENCES `flight` (`flight_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`upgrade_order_id`) REFERENCES `orders` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1675694838957240320 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1675125754431557632, 153, '1', 1, 1, 0, 150.00, '2023-07-01 20:54:37', 1, '2023-07-01 21:39:56', 1, NULL, NULL, 0);
INSERT INTO `orders` VALUES (1675125755454959616, 153, '1', 1, 1, 0, 150.00, '2023-07-01 20:54:37', 1, '2023-07-01 21:13:03', NULL, NULL, NULL, 1);
INSERT INTO `orders` VALUES (1675146122445725696, 153, '1', 1, 1, 0, 150.00, '2023-07-01 22:15:33', 1, '2023-07-01 22:16:46', NULL, NULL, NULL, 1);
INSERT INTO `orders` VALUES (1675146123993448448, 158, '1', 1, 1, 0, 150.00, '2023-07-01 22:15:33', 1, '2023-07-01 22:18:02', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (1675311768194347008, 158, '1', 1, 1, 0, 150.00, '2023-06-13 09:13:46', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (1675311768890515456, 158, '1', 1, 1, 0, 150.00, '2023-07-02 09:13:46', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (1675311831511568384, 158, '0', 1, 1, 0, 150.00, '2023-07-02 09:14:01', NULL, NULL, 1, 1675315832969756672, NULL, NULL);
INSERT INTO `orders` VALUES (1675311832039976960, 158, '1', 1, 1, 0, 150.00, '2023-07-02 09:14:01', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (1675313405696704512, 158, '1', 1, 1, 0, 150.00, '2023-07-02 09:14:01', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (1675315495202533376, 158, '1', 1, 1, 0, 150.00, '2023-07-02 09:14:01', 1, '2023-07-02 21:25:24', NULL, NULL, 1, NULL);
INSERT INTO `orders` VALUES (1675315832969756672, 158, '1', 1, 1, 0, 150.00, '2023-07-02 09:14:01', NULL, NULL, NULL, NULL, 1, NULL);
INSERT INTO `orders` VALUES (1675361724942856192, 158, '0', 1, 1, 0, 150.00, '2023-07-02 12:33:16', 1, '2023-07-03 10:35:57', 1, 1675694838957240320, 0, 0);
INSERT INTO `orders` VALUES (1675361725672677376, 158, '1', 1, 1, 0, 150.00, '2023-07-02 12:33:17', 0, NULL, NULL, NULL, 0, 0);
INSERT INTO `orders` VALUES (1675495888622981120, 172, '0', 1, 1, 0, 150.00, '2023-07-02 21:25:24', 1, '2023-07-03 10:35:49', 0, NULL, 0, 0);
INSERT INTO `orders` VALUES (1675496796299792384, 182, '0', 1, 1, 0, 150.00, '2023-07-02 21:29:00', 1, '2023-07-02 21:30:53', 0, NULL, 0, 0);
INSERT INTO `orders` VALUES (1675694712041861120, 163, '1', 1, 1, NULL, 270.00, '2023-07-03 10:35:27', 0, NULL, 0, NULL, 0, 0);
INSERT INTO `orders` VALUES (1675694804064899072, 162, '1', 1, 1, NULL, 290.00, '2023-07-03 10:35:49', 0, NULL, 0, NULL, 0, 0);
INSERT INTO `orders` VALUES (1675694838957240320, 158, '1', 1, 1, 0, 190.00, '2023-07-02 12:33:16', 0, NULL, NULL, NULL, 1, 0);

-- ----------------------------
-- Table structure for passenger_information
-- ----------------------------
DROP TABLE IF EXISTS `passenger_information`;
CREATE TABLE `passenger_information`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `passenger_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `identification_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone_number` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_passenger_user`(`user_id`) USING BTREE,
  CONSTRAINT `fk_passenger_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of passenger_information
-- ----------------------------
INSERT INTO `passenger_information` VALUES (1, 1, 'ntx', '111111', '111111');

-- ----------------------------
-- Table structure for route
-- ----------------------------
DROP TABLE IF EXISTS `route`;
CREATE TABLE `route`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `departure_airport_id` int NULL DEFAULT NULL,
  `destination_airport_id` int NULL DEFAULT NULL,
  `publish_time` datetime NULL DEFAULT NULL,
  `modify_time` datetime NULL DEFAULT NULL,
  `creator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `modifier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `departure_airport_id`(`departure_airport_id`) USING BTREE,
  INDEX `destination_airport_id`(`destination_airport_id`) USING BTREE,
  CONSTRAINT `route_ibfk_1` FOREIGN KEY (`departure_airport_id`) REFERENCES `airport` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `route_ibfk_2` FOREIGN KEY (`destination_airport_id`) REFERENCES `airport` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of route
-- ----------------------------
INSERT INTO `route` VALUES (2, 1, 2, '2023-06-27 10:00:00', '2023-07-03 01:20:52', 'John', 'admin1');
INSERT INTO `route` VALUES (3, 2, 3, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `route` VALUES (4, 3, 4, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `route` VALUES (5, 4, 5, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `route` VALUES (6, 5, 6, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `route` VALUES (7, 6, 7, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `route` VALUES (8, 7, 8, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `route` VALUES (9, 8, 9, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `route` VALUES (10, 9, 10, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `route` VALUES (11, 10, 11, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `route` VALUES (12, 11, 21, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `route` VALUES (13, 23, 3, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `route` VALUES (14, 12, 4, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `route` VALUES (15, 23, 13, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `route` VALUES (16, 43, 23, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `route` VALUES (17, 35, 33, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `route` VALUES (18, 7, 44, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `route` VALUES (19, 19, 55, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');
INSERT INTO `route` VALUES (20, 22, 7, '2023-06-27 10:00:00', '2023-06-27 10:00:00', 'John', 'John');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `id_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `vip_status` int NULL DEFAULT NULL,
  `account_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `total_expenses` decimal(10, 2) NULL DEFAULT NULL,
  `user_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 139 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'ntx', 'northnightx', '4297f44b13955235245b2497399d7a93', 'avatar1.jpg', '15588222448', 'z15588222448@gmail.com', 'ID001', 25, 'Male', 10, '1', 1004949.00, 'Regular');

-- ----------------------------
-- Table structure for vip
-- ----------------------------
DROP TABLE IF EXISTS `vip`;
CREATE TABLE `vip`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `level` int NULL DEFAULT NULL,
  `required_spending` decimal(10, 2) NULL DEFAULT NULL,
  `discount_rate` decimal(4, 2) NULL DEFAULT NULL,
  `publish_time` datetime NULL DEFAULT NULL,
  `modify_time` datetime NULL DEFAULT NULL,
  `creator` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `modifier` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vip
-- ----------------------------
INSERT INTO `vip` VALUES (1, 1, 1000.00, 1.00, '2023-01-01 09:00:00', '2023-01-01 09:00:00', 'John', 'John');
INSERT INTO `vip` VALUES (2, 2, 2000.00, 0.99, '2023-01-02 10:00:00', '2023-01-02 10:00:00', 'Jane', 'Jane');
INSERT INTO `vip` VALUES (3, 3, 3000.00, 0.98, '2023-01-03 11:00:00', '2023-01-03 11:00:00', 'Bob', 'Bob');
INSERT INTO `vip` VALUES (4, 4, 4000.00, 0.97, '2023-01-04 12:00:00', '2023-01-04 12:00:00', 'Alice', 'Alice');
INSERT INTO `vip` VALUES (5, 5, 5000.00, 0.97, '2023-01-05 13:00:00', '2023-01-05 13:00:00', 'Mike', 'Mike');
INSERT INTO `vip` VALUES (6, 6, 15000.00, 0.96, '2023-01-06 14:00:00', '2023-01-06 14:00:00', 'Sarah', 'Sarah');
INSERT INTO `vip` VALUES (7, 7, 250000.00, 0.95, '2023-01-07 15:00:00', '2023-01-07 15:00:00', 'David', 'David');
INSERT INTO `vip` VALUES (8, 8, 500000.00, 0.93, '2023-01-08 16:00:00', '2023-01-08 16:00:00', 'Emily', 'Emily');
INSERT INTO `vip` VALUES (9, 9, 750000.00, 0.92, '2023-01-09 17:00:00', '2023-01-09 17:00:00', 'Chris', 'Chris');
INSERT INTO `vip` VALUES (10, 10, 1000000.00, 0.90, '2023-01-10 18:00:00', '2023-07-01 15:31:04', 'Linda', 'admin1');

SET FOREIGN_KEY_CHECKS = 1;
