/*
Navicat MySQL Data Transfer

Source Server         : fyl
Source Server Version : 50723
Source Host           : 120.79.204.154:3306
Source Database       : fydatabase

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-10-30 14:01:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickName` varchar(50) NOT NULL,
  `userName` varchar(150) NOT NULL,
  `password` varchar(150) NOT NULL,
  `salt` varchar(150) NOT NULL,
  `status` int(11) NOT NULL,
  `createAt` datetime NOT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `avatar` varchar(100) NOT NULL,
  `likeCount` int(11) NOT NULL DEFAULT '0' COMMENT '被赞次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1', 'admin', 'admin@admin', '71a54e1a8c12f2561ba52c4d540473383f0462c82f3e25ba67c2132952b89fbd', 'P5EwnCsdDV_5hYqcrxpd4yPbDCLfj7kr', '1', '2018-10-12 09:51:59', '0:0:0:0:0:0:0:1', 'x.jpg', '0');
INSERT INTO `account` VALUES ('4', '陈合峰', '3439485979@qq.com', '71a54e1a8c12f2561ba52c4d540473383f0462c82f3e25ba67c2132952b89fbd', 'P5EwnCsdDV_5hYqcrxpd4yPbDCLfj7kr', '1', '2018-04-21 12:20:15', '175.12.244.105', '0/1.jpg', '0');
INSERT INTO `account` VALUES ('5', '赖建文', '3293106458@qq.com', '2c171ad61b8aadbfd3951ef8e01356caafbab901adc5247857e59d45b0c3ae16', 'EUEIoSou4tjhfafSJRe7_0xopBVQszt_', '1', '2018-04-22 09:20:18', '175.12.244.105', '0/1.jpg', '0');
INSERT INTO `account` VALUES ('6', '冼敏', '2648329328@qq.com', '440e0232e5b9870913ff16d4f265c33387c847ce3b6bbf86ab06e2a3b602cb3e', 'ENENmdKVkiSxVTQ_vs0oPEG1Okr_WGZt', '1', '2018-04-23 10:20:35', '175.12.244.105', '0/1.jpg', '0');
INSERT INTO `account` VALUES ('7', '何云峰', '1524554763@qq.com', '292d35d17fde9cca2f164a523b525db663231a5aa81afae64a336854e1236573', '0vjzYNmpSoh6pGpSi9Dt_mHUw3yWW4EC', '1', '2018-04-23 11:20:31', '175.12.244.105', '0/1.jpg', '0');
INSERT INTO `account` VALUES ('8', '郑纯娥', '2483858237@qq.com', '0c1e33c212f6748dd68fee7ce5be4decf940650febd303c8d2ecd14192403dbf', 'nBFIwC9RG90BLSr5Wi5UDQ4CdtjuBCoS', '1', '2018-04-24 12:20:53', '175.12.244.105', '0/1.jpg', '0');
INSERT INTO `account` VALUES ('9', '许增辉', '3444201404@qq.com', 'fa0598f285de33d6d81bc6ebf7937e4dd093b3c2d133c16d263887224949c36b', 'XJo-IOl2pvbabZJ5yJ5q8VrPqiky1toa', '1', '2018-04-25 09:58:19', '175.12.244.105', '0/2.jpg', '0');
INSERT INTO `account` VALUES ('10', '何发生', '2608972483@qq.com', '751462fb664af1363cbc68526d1f081a91f83e78054fd0a86678ad7fe61620d0', 'JyPEh-laXaJk3g33E0dbo0UCSvUscnEd', '1', '2018-05-06 10:26:35', '0:0:0:0:0:0:0:1', 'x.jpg', '0');
INSERT INTO `account` VALUES ('11', '樊凯', '3206863726@qq.com', 'a9c72159ab550fd39a69a0466de8d2def7e05c8987c0f6b9292975cfe4cf672d', 'W_m6X0k9l3ysRoDwHdYk44629ukXmN4H', '1', '2018-07-06 16:05:49', null, 'x.jpg', '0');
INSERT INTO `account` VALUES ('12', '蒋头兆', '2903963046@qq.com', 'aa09e6f2758853b58584f5487322ddd6250741fa3394024ee22c58e0c5c176f2', 'OsMIztl0szwmYa-crfNFaC2VSekXb0j-', '1', '2018-07-19 16:53:10', null, 'x.jpg', '0');
INSERT INTO `account` VALUES ('13', '何亚连', '2531640790@qq.com', '5a85d5582db1c34ec8fc993799ec4e2fd0dd800f0f8055473cfc979bcd2a280c', 'xQXzuXo_O8xAr3KIlbH8rpNPs6AAyGJG', '0', '2018-10-12 08:50:32', null, 'x.jpg', '0');
INSERT INTO `account` VALUES ('14', '蔡娇', '3277336196@qq.com', '5fb81b0093eb7d240a6a97838b5f9b30fabdbe8d389d8da4c31c77435b30f850', 'RPwrJNmGfWrAyshmyN3YAU6s8rJooBj-', '0', '2018-10-30 10:53:18', null, 'x.jpg', '0');

-- ----------------------------
-- Table structure for `account_role`
-- ----------------------------
DROP TABLE IF EXISTS `account_role`;
CREATE TABLE `account_role` (
  `accountId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`accountId`,`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of account_role
-- ----------------------------
INSERT INTO `account_role` VALUES ('4', '12');
INSERT INTO `account_role` VALUES ('5', '7');
INSERT INTO `account_role` VALUES ('6', '10');
INSERT INTO `account_role` VALUES ('7', '5');
INSERT INTO `account_role` VALUES ('8', '6');
INSERT INTO `account_role` VALUES ('9', '8');
INSERT INTO `account_role` VALUES ('10', '2');
INSERT INTO `account_role` VALUES ('11', '1');
INSERT INTO `account_role` VALUES ('11', '3');
INSERT INTO `account_role` VALUES ('12', '9');
INSERT INTO `account_role` VALUES ('13', '11');
INSERT INTO `account_role` VALUES ('14', '13');

-- ----------------------------
-- Table structure for `auth_code`
-- ----------------------------
DROP TABLE IF EXISTS `auth_code`;
CREATE TABLE `auth_code` (
  `id` varchar(33) NOT NULL,
  `accountId` int(11) NOT NULL,
  `expireAt` bigint(20) NOT NULL,
  `type` int(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_code
-- ----------------------------
INSERT INTO `auth_code` VALUES ('0274b678b38545a6bdcc221854037f40', '10', '1525577704477', '0');
INSERT INTO `auth_code` VALUES ('a106b0aa38204e89bd5150ba1d078b42', '10', '1525577950557', '0');
INSERT INTO `auth_code` VALUES ('a316a5ab53574815b0d364f08a4937cb', '10', '1525577241551', '0');
INSERT INTO `auth_code` VALUES ('cfb775ff523448dcbdb8e172f1d4bba5', '10', '1525577195192', '0');

-- ----------------------------
-- Table structure for `col_permision`
-- ----------------------------
DROP TABLE IF EXISTS `col_permision`;
CREATE TABLE `col_permision` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ckey` varchar(20) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  `ctable` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of col_permision
-- ----------------------------
INSERT INTO `col_permision` VALUES ('33', 'entry_salary', '入职底薪', 'person');
INSERT INTO `col_permision` VALUES ('34', 'current_salary', '当前底薪', 'person');
INSERT INTO `col_permision` VALUES ('35', 'adjust_base_salary1', '调整底薪1', 'person');
INSERT INTO `col_permision` VALUES ('36', 'adjust_date1', '调整1日期', 'person');
INSERT INTO `col_permision` VALUES ('37', 'adjust_base_salary2', '调整底薪2', 'person');
INSERT INTO `col_permision` VALUES ('38', 'adjust_date2', '调整2日期', 'person');
INSERT INTO `col_permision` VALUES ('39', 'adjust_base_salary3', '调整底薪3', 'person');
INSERT INTO `col_permision` VALUES ('40', 'adjust_date3', '调整3日期', 'person');
INSERT INTO `col_permision` VALUES ('41', 'adjust_base_salary4', '调整底薪4', 'person');
INSERT INTO `col_permision` VALUES ('42', 'adjust_date4', '调整4日期', 'person');
INSERT INTO `col_permision` VALUES ('43', 'adjust_base_salary5', '调整底薪5', 'person');
INSERT INTO `col_permision` VALUES ('44', 'adjust_date5', '调整5日期', 'person');
INSERT INTO `col_permision` VALUES ('45', 'adjust_base_salary6', '调整底薪6', 'person');
INSERT INTO `col_permision` VALUES ('46', 'adjust_date6', '调整6日期', 'person');
INSERT INTO `col_permision` VALUES ('47', 'adjust_base_salary7', '调整底薪7', 'person');
INSERT INTO `col_permision` VALUES ('48', 'adjust_date7', '调整7日期', 'person');
INSERT INTO `col_permision` VALUES ('49', 'adjust_base_salary8', '调整底薪8', 'person');
INSERT INTO `col_permision` VALUES ('50', 'adjust_date8', '调整8日', 'person');
INSERT INTO `col_permision` VALUES ('67', 'category_id', '类别', 'order');
INSERT INTO `col_permision` VALUES ('68', 'planer_id', '计划员', 'order');
INSERT INTO `col_permision` VALUES ('69', 'execu_status', '执行状态', 'order');
INSERT INTO `col_permision` VALUES ('70', 'customer_no', '客户编码', 'order');
INSERT INTO `col_permision` VALUES ('71', 'work_bill_no', '工作订单号', 'order');
INSERT INTO `col_permision` VALUES ('72', 'delivery_no', '送货单号', 'order');
INSERT INTO `col_permision` VALUES ('73', 'map_no', '图号', 'order');
INSERT INTO `col_permision` VALUES ('74', 'commodity_name', '名称', 'order');
INSERT INTO `col_permision` VALUES ('75', 'total_map_no', '总图号', 'order');
INSERT INTO `col_permision` VALUES ('76', 'quantity', '数量', 'order');
INSERT INTO `col_permision` VALUES ('77', 'unit', '单位', 'order');
INSERT INTO `col_permision` VALUES ('78', 'draw', '图纸', 'order');
INSERT INTO `col_permision` VALUES ('79', 'model_no', '型号', 'order');
INSERT INTO `col_permision` VALUES ('80', 'commodity_spec', '规格', 'order');
INSERT INTO `col_permision` VALUES ('81', 'technology', '技术条件', 'order');
INSERT INTO `col_permision` VALUES ('82', 'machining_require', '质量等级', 'order');
INSERT INTO `col_permision` VALUES ('83', 'bill_date', '订单日期', 'order');
INSERT INTO `col_permision` VALUES ('84', 'delivery_date', '交货日期', 'order');
INSERT INTO `col_permision` VALUES ('85', 'un_tax_cost', '未税单价', 'order');
INSERT INTO `col_permision` VALUES ('86', 'amount', '金额', 'order');
INSERT INTO `col_permision` VALUES ('87', 'taxrate', '税率', 'order');
INSERT INTO `col_permision` VALUES ('88', 'tax', '税额', 'order');
INSERT INTO `col_permision` VALUES ('89', 'tatol_amount', '含税金额', 'order');
INSERT INTO `col_permision` VALUES ('90', 'distribute_time', '分配时间', 'order');
INSERT INTO `col_permision` VALUES ('91', 'distribute_to', '分配流向', 'order');
INSERT INTO `col_permision` VALUES ('92', 'supplier', '制造商', 'order');
INSERT INTO `col_permision` VALUES ('93', 'purchase_cost', '采购单价', 'order');
INSERT INTO `col_permision` VALUES ('94', 'purchase_amount', '采购总价', 'order');
INSERT INTO `col_permision` VALUES ('95', 'receive_time', '接收时间', 'order');
INSERT INTO `col_permision` VALUES ('96', 'begin_time', '投产时间', 'order');
INSERT INTO `col_permision` VALUES ('97', 'in_time', '入库时间', 'order');
INSERT INTO `col_permision` VALUES ('98', 'check_time', '检测时间', 'order');
INSERT INTO `col_permision` VALUES ('99', 'out_time', '出库时间', 'order');
INSERT INTO `col_permision` VALUES ('100', 'out_status', '出库状态', 'order');
INSERT INTO `col_permision` VALUES ('101', 'send_address', '发货地址', 'order');
INSERT INTO `col_permision` VALUES ('102', 'transport_company', '物流名称', 'order');
INSERT INTO `col_permision` VALUES ('103', 'transport_no', '物流单号', 'order');
INSERT INTO `col_permision` VALUES ('104', 'hang_date', '挂账时间', 'order');
INSERT INTO `col_permision` VALUES ('105', 'hang_status', '挂账状态', 'order');
INSERT INTO `col_permision` VALUES ('106', 'hang_quantity', '挂账数量', 'order');
INSERT INTO `col_permision` VALUES ('107', 'hang_amount', '挂账金额', 'order');
INSERT INTO `col_permision` VALUES ('108', 'unhang_quantity', '未挂账数量', 'order');
INSERT INTO `col_permision` VALUES ('109', 'order_update', '订单更新', 'order');
INSERT INTO `col_permision` VALUES ('110', 'is_finsh_product', '是否成品', 'order');
INSERT INTO `col_permision` VALUES ('111', 'order_select', '选择订单', 'order');

-- ----------------------------
-- Table structure for `config`
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `id` int(11) NOT NULL DEFAULT '0',
  `acount` int(11) DEFAULT NULL,
  `page_size` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of config
-- ----------------------------

-- ----------------------------
-- Table structure for `fy_advisory_cost`
-- ----------------------------
DROP TABLE IF EXISTS `fy_advisory_cost`;
CREATE TABLE `fy_advisory_cost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `advisory_org` varchar(100) DEFAULT NULL COMMENT '询价单位',
  `advisory_persion` varchar(20) DEFAULT NULL COMMENT '询价人',
  `advisory_date` date DEFAULT NULL COMMENT '询价日期',
  `answer_persion` int(11) DEFAULT NULL COMMENT '报价人',
  `answer_date` date DEFAULT NULL COMMENT '报价日期',
  `commodity_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `commodity_spec` varchar(100) DEFAULT NULL COMMENT '商品规格',
  `map_no` varchar(100) DEFAULT NULL COMMENT '总图号，关联图纸',
  `unit` int(11) DEFAULT NULL COMMENT '单位，关联基础资料',
  `base_cost` decimal(10,2) DEFAULT NULL COMMENT '本部成本单价',
  `customer_profit_cost` decimal(10,2) DEFAULT NULL COMMENT '客户利润价价',
  `customer_profit_amount` decimal(10,2) DEFAULT NULL COMMENT '客户利润总价',
  `quantity` decimal(14,4) DEFAULT '0.0000' COMMENT '数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_advisory_cost
-- ----------------------------

-- ----------------------------
-- Table structure for `fy_base_category`
-- ----------------------------
DROP TABLE IF EXISTS `fy_base_category`;
CREATE TABLE `fy_base_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_base_category
-- ----------------------------
INSERT INTO `fy_base_category` VALUES ('3', '物资');
INSERT INTO `fy_base_category` VALUES ('5', '自动化');
INSERT INTO `fy_base_category` VALUES ('6', '工装');
INSERT INTO `fy_base_category` VALUES ('7', '机箱');

-- ----------------------------
-- Table structure for `fy_base_customer`
-- ----------------------------
DROP TABLE IF EXISTS `fy_base_customer`;
CREATE TABLE `fy_base_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_no` varchar(50) DEFAULT NULL COMMENT '客户代码',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `code` varchar(50) DEFAULT NULL COMMENT '统一代码',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `phone` varchar(20) DEFAULT NULL,
  `bank_account` varchar(50) DEFAULT NULL,
  `bank_no` varchar(50) DEFAULT NULL COMMENT '银行账号',
  `settlement_type` varchar(20) DEFAULT NULL COMMENT '结算方式',
  `settlement_cycle` varchar(20) DEFAULT NULL COMMENT '结算周期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_base_customer
-- ----------------------------
INSERT INTO `fy_base_customer` VALUES ('5', 'ZHGD-LY', '中航光电科技股份有限公司', '914100007457748527', '中国（河南）自由贸易试验区洛阳片区周山路10号', '0379-63011165', '建行涧西支行', '41001512110050000643', '电子票据', '2个月付4个月银行承兑汇票');
INSERT INTO `fy_base_customer` VALUES ('7', 'ZHGD-DG', '中航光电科技股份有限公司东莞分公司', '91441900MA4WP0FT26', '东莞市常平镇环常北路22号', '0769-89363026', '中国银行东莞常平支行', '710769003409', '电子票据', '2个月付4个月银行承兑汇票');

-- ----------------------------
-- Table structure for `fy_base_department`
-- ----------------------------
DROP TABLE IF EXISTS `fy_base_department`;
CREATE TABLE `fy_base_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `part_no` varchar(10) DEFAULT NULL COMMENT '部门编号',
  `part_name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `duty_person` int(11) DEFAULT NULL COMMENT '负责人',
  `desc` varchar(200) DEFAULT NULL COMMENT '描述',
  `create_by` int(11) DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_base_department
-- ----------------------------
INSERT INTO `fy_base_department` VALUES ('18', null, '财务部', null, null, null, null, null, null);
INSERT INTO `fy_base_department` VALUES ('19', null, '采购部', null, null, null, null, null, null);
INSERT INTO `fy_base_department` VALUES ('20', null, '生产部', null, null, null, null, null, null);
INSERT INTO `fy_base_department` VALUES ('21', null, '品检部', null, null, null, null, null, null);
INSERT INTO `fy_base_department` VALUES ('22', null, '工程部', null, null, null, null, null, null);
INSERT INTO `fy_base_department` VALUES ('23', null, '行政部', null, null, null, null, null, null);
INSERT INTO `fy_base_department` VALUES ('24', null, '业务部', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `fy_base_fyfile`
-- ----------------------------
DROP TABLE IF EXISTS `fy_base_fyfile`;
CREATE TABLE `fy_base_fyfile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) DEFAULT NULL,
  `originalFileName` varchar(255) DEFAULT NULL,
  `filepath` varchar(100) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` int(11) DEFAULT NULL,
  `file_size` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_base_fyfile
-- ----------------------------
INSERT INTO `fy_base_fyfile` VALUES ('65', 'MP-5000.pdf', 'MP-5000.pdf', 'E:\\jfinalPlace\\jfinal-fy\\src\\main\\webapp\\map', '2018-09-12 20:41:20', '2018-09-12 20:41:20', '1', null);
INSERT INTO `fy_base_fyfile` VALUES ('66', 'MP-500.pdf', 'MP-500.pdf', 'E:\\jfinalPlace\\jfinal-fy\\src\\main\\webapp\\map', '2018-09-12 20:41:20', '2018-09-12 20:41:20', '1', null);
INSERT INTO `fy_base_fyfile` VALUES ('67', 'MP-750.pdf', 'MP-750.pdf', 'E:\\jfinalPlace\\jfinal-fy\\src\\main\\webapp\\map', '2018-09-12 20:41:20', '2018-09-12 20:41:20', '1', null);
INSERT INTO `fy_base_fyfile` VALUES ('68', 'MP-1500.pdf', 'MP-1500.pdf', 'E:\\jfinalPlace\\jfinal-fy\\src\\main\\webapp\\map', '2018-09-12 20:41:20', '2018-09-12 20:41:20', '1', null);
INSERT INTO `fy_base_fyfile` VALUES ('69', 'MP-3000.pdf', 'MP-3000.pdf', 'E:\\jfinalPlace\\jfinal-fy\\src\\main\\webapp\\map', '2018-09-12 20:41:20', '2018-09-12 20:41:20', '1', null);
INSERT INTO `fy_base_fyfile` VALUES ('70', 'MPX-1500.pdf', 'MPX-1500.pdf', 'E:\\jfinalPlace\\jfinal-fy\\src\\main\\webapp\\map', '2018-09-12 20:41:20', '2018-09-12 20:41:20', '1', null);
INSERT INTO `fy_base_fyfile` VALUES ('71', 'MP-7500.pdf', 'MP-7500.pdf', 'E:\\jfinalPlace\\jfinal-fy\\src\\main\\webapp\\map', '2018-09-12 20:41:20', '2018-09-12 20:41:20', '1', null);
INSERT INTO `fy_base_fyfile` VALUES ('72', 'MP-10000.pdf', 'MP-10000.pdf', 'E:\\jfinalPlace\\jfinal-fy\\src\\main\\webapp\\map', '2018-09-12 20:41:20', '2018-09-12 20:41:20', '1', null);

-- ----------------------------
-- Table structure for `fy_base_person`
-- ----------------------------
DROP TABLE IF EXISTS `fy_base_person`;
CREATE TABLE `fy_base_person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '名字',
  `native_place` varchar(30) DEFAULT NULL COMMENT '籍贯',
  `sex` bit(1) DEFAULT NULL COMMENT '性别',
  `birth_date` date DEFAULT NULL COMMENT '生日',
  `entry_date` date DEFAULT NULL COMMENT '入职日期',
  `quit_date` date DEFAULT NULL COMMENT '离职日期',
  `contact_type` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `education` varchar(10) DEFAULT NULL COMMENT '学历',
  `political_status` varchar(10) DEFAULT NULL COMMENT '政治面貌',
  `accommodation` varchar(50) DEFAULT NULL,
  `post` varchar(30) DEFAULT NULL COMMENT '岗位',
  `department_id` int(11) DEFAULT NULL COMMENT '部门',
  `job_status` varchar(10) DEFAULT NULL COMMENT '工作状态',
  `current_salary` decimal(8,2) DEFAULT NULL COMMENT '执行底薪',
  `adjust_base_salary1` decimal(8,2) DEFAULT NULL COMMENT '调整底薪1',
  `adjust_date1` date DEFAULT NULL COMMENT '调薪1日期',
  `adjust_base_salary2` decimal(8,2) DEFAULT NULL COMMENT '调薪2',
  `adjust_date2` date DEFAULT NULL COMMENT '调薪2日期',
  `adjust_base_salary3` decimal(8,2) DEFAULT NULL COMMENT '调薪3',
  `adjust_base_salary4` decimal(8,2) DEFAULT NULL COMMENT '调薪4',
  `adjust_base_salary5` decimal(8,2) DEFAULT NULL COMMENT '调薪5',
  `adjust_date3` date DEFAULT NULL COMMENT '调薪3日期',
  `adjust_date4` date DEFAULT NULL COMMENT '调薪4日期',
  `adjust_date5` date DEFAULT NULL COMMENT '调薪5日期',
  `adjust_base_salary6` decimal(8,2) DEFAULT NULL,
  `adjust_date6` date DEFAULT NULL COMMENT '调薪6日期',
  `adjust_base_salary7` decimal(8,2) DEFAULT NULL,
  `adjust_date7` date DEFAULT NULL COMMENT '调薪7日期',
  `adjust_base_salary8` decimal(8,2) DEFAULT NULL,
  `adjust_date8` date DEFAULT NULL COMMENT '调薪8日期',
  `ethnic_group` varchar(30) DEFAULT NULL COMMENT '民族',
  `doc_no` varchar(20) DEFAULT NULL COMMENT '档案编号',
  `remark` varchar(255) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL COMMENT '联系电话',
  `quality_check` text COMMENT '质量考核',
  `administrative_check` text COMMENT '行政考核',
  `room_no` varchar(30) DEFAULT NULL COMMENT '寝室号',
  `marriage` varchar(20) DEFAULT NULL COMMENT '婚姻状态',
  `requst_out_date` date DEFAULT NULL COMMENT '申请离职日期',
  `entry_salary` decimal(12,2) DEFAULT NULL COMMENT '入职底薪',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_base_person
-- ----------------------------
INSERT INTO `fy_base_person` VALUES ('99', '蒋头兆', '湖南', '', '1974-01-12', '2014-12-01', null, '13516602646', '中专', '群众', '宿舍', '副总', null, '在职', '10000.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '2015.7.15造成液冷板批量报废 记小过罚款200元', '', '201', '已婚', null, '10000.00');
INSERT INTO `fy_base_person` VALUES ('100', '何发生', '湖南', '', '1993-06-12', '2014-12-01', null, '13560880448', '大专', '团员', '宿舍', '经理', '24', '在职', '20000.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '2015.7.15造成液冷板批量报废 记小过罚款200元', '2016.5.27遗忘关闭厂门 警告 罚款200元', '300', '已婚', null, '5000.00');
INSERT INTO `fy_base_person` VALUES ('101', '樊凯', '江西', '', '1994-11-09', '2015-04-01', null, '13549219768', '本科', '党员', '宿舍', '财务', '18', '在职', '5000.00', '2500.00', '2015-06-01', '3000.00', '2016-03-01', '3500.00', '4000.00', '5000.00', '2017-03-01', '2017-06-01', '2017-10-01', null, null, null, null, null, null, '汉族', null, null, null, '', '', '302', '未婚', null, '2000.00');
INSERT INTO `fy_base_person` VALUES ('102', '冼毓', '湖南', '', '2001-01-28', '2018-05-30', null, '15992788921', '初中', '群众', '宿舍', '学徒工', '20', '在职', '1530.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '瑶族', null, null, null, '', '', '305', '未婚', null, '1530.00');
INSERT INTO `fy_base_person` VALUES ('103', '许增辉', '江西', '', '1980-09-18', '2016-03-14', null, '13326893103', '高中', '群众', '外宿', '主管', '20', '在职', '9000.00', '9000.00', '2018-04-01', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '外宿', '已婚', null, '3400.00');
INSERT INTO `fy_base_person` VALUES ('104', '郑纯娥', '广东', '', '1993-03-18', '2016-07-28', null, '15999715412', '大专', '团员', '外宿', '文员', '20', '在职', '3800.00', '2500.00', '2016-10-01', '3000.00', '2017-03-01', '3500.00', '3800.00', null, '2018-01-01', '2018-05-01', null, null, null, null, null, null, null, '汉族', null, null, null, '', '2016.8.22旷工3小时 记小过罚款30元', '外宿', '未婚', null, '2200.00');
INSERT INTO `fy_base_person` VALUES ('105', '赖建文', '广东', '', '1980-04-17', '2015-06-10', null, '13650147221', '初中', '群众', '外宿', '核价技术员', '22', '在职', '7000.00', '3000.00', '2016-06-01', '3100.00', '2017-05-01', '6500.00', '7000.00', null, '2017-07-01', '2018-03-01', null, null, null, null, null, null, null, '汉族', null, null, null, '2015.10.12产品报废 记小过 罚款50元', '', '外宿', '已婚', null, '2900.00');
INSERT INTO `fy_base_person` VALUES ('106', '林宇凯', '福建', '', null, null, null, '15818381939', '大专', '群众', '宿舍', '质检', null, '在职', '2700.00', '2100.00', '2016-03-01', '2500.00', '2016-10-01', '2700.00', null, null, '2017-10-01', null, null, null, null, null, null, null, null, '汉族', null, null, null, '2015.7.15液冷板批量件报废 记小过 罚款50元              2015.10.20 流出不良品至客户 记小过 罚款100元    2017.10.31批量件未首检 记小过，2018.9.12质量问题记小过，罚款30元', '2015.7.1上班精神涣散 记小过 罚款30元       2016.4.221早会迟到 记小过    2016.5.27遗忘关闭厂门，警告 罚款50元', '302', '已婚', null, '1800.00');
INSERT INTO `fy_base_person` VALUES ('107', '徐妃二', '广东', '', '1995-12-16', '2018-08-01', null, '13189180729', '初中', '群众', '外宿', '质检实习', null, '在职', '1500.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '外宿', '已婚', null, '1500.00');
INSERT INTO `fy_base_person` VALUES ('108', '何盛忠', '广西', '', '1993-04-24', '2018-08-03', null, '13877715820', '初中', '群众', '外宿', '普工', '20', '在职', '1600.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '外宿', '未婚', null, '1600.00');
INSERT INTO `fy_base_person` VALUES ('109', '周永仕', '湖南', '', '1978-10-20', '2018-08-06', null, '15818489455', '高中', '群众', '外宿', '钳工', '20', '在职', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '外宿', '已婚', null, null);
INSERT INTO `fy_base_person` VALUES ('110', '杨全良', '湖南', '', '1999-01-05', '2018-07-12', null, '15200973305', '初中', '群众', '宿舍', 'CNC操机员', '20', '在职', '2500.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '2018年8月，加工报废处罚50元;', null, '304', '未婚', null, '2500.00');
INSERT INTO `fy_base_person` VALUES ('111', '陈湘仁', '湖南', '', '1996-01-28', '2018-07-02', null, '13712528864', '初中', '群众', '宿舍', '学徒工', '20', '在职', '1530.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '303', '未婚', null, '1530.00');
INSERT INTO `fy_base_person` VALUES ('113', '罗统涛', '广西', '', '1986-09-14', '2015-06-23', null, '13266239541', '高中', '群众', '宿舍', '铣床操机员', '20', '在职', '2700.00', '2500.00', '2015-07-01', '2600.00', '2016-11-01', '2700.00', null, null, '2017-06-01', null, null, null, null, null, null, null, null, '汉族', null, null, null, '2015.10.12产品报废 记小过 罚款20元', '', '305', '已婚', null, '2300.00');
INSERT INTO `fy_base_person` VALUES ('114', '何小华', '湖南', '', null, null, null, '13530225229', '初中', '群众', '外宿', '线切割操机员', '20', '在职', '3300.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '2018.9.12质量问题记小过，罚款30元', null, '外宿', '已婚', null, '3300.00');
INSERT INTO `fy_base_person` VALUES ('115', '洪清云', '江西', '', '1993-10-15', '2018-04-10', null, '13662753946', '初中', '群众', '外宿', '质检实习', null, '在职', '2100.00', '2100.00', '2018-07-01', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '外宿', '已婚', null, '1500.00');
INSERT INTO `fy_base_person` VALUES ('116', '杨好贤', '湖南', '', '1980-01-01', '2018-05-07', null, '18621466669', '初中', '群众', '外宿', 'CNC编程', '20', '在职', '9000.00', '9000.00', '2018-07-01', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '2015.7.15造成液冷板批量报废 记小过罚款100元', '', '外宿', '已婚', null, '8000.00');
INSERT INTO `fy_base_person` VALUES ('117', '袁志彬', '广东', '', '1985-12-02', '2018-05-17', null, '13712643630', '大专', '群众', '外宿', '普工', '20', '在职', '1650.00', '1900.00', '2018-09-01', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, null, null, '外宿', '已婚', null, '1650.00');
INSERT INTO `fy_base_person` VALUES ('118', '田凯', '陕西', '', '1988-01-10', '2018-05-24', null, '13592739734', '高中', '群众', '外宿', 'CNC编程', '20', '在职', '10000.00', '10000.00', '2018-07-01', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '外宿', '已婚', null, '8500.00');
INSERT INTO `fy_base_person` VALUES ('119', '吴恢龙', '湖南', '', '1986-08-02', '2018-05-25', null, '13416758169', '初中', '群众', '外宿', '铣床操机员', '20', '在职', '3300.00', '3300.00', '2018-07-01', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '外宿', '已婚', null, '3100.00');
INSERT INTO `fy_base_person` VALUES ('120', '陈合峰', '河南', '', '1988-03-25', '2018-05-26', null, '18438064602', '高中', '群众', '外宿', '跟单员', '24', '在职', '3500.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '外宿', '已婚', null, '3500.00');
INSERT INTO `fy_base_person` VALUES ('121', '甘茂盛', '湖南', '', null, null, null, '15818303293', '初中', '群众', '宿舍', '学徒工', '20', '在职', '1530.00', '1700.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '瑶族', null, null, null, null, null, '305', '未婚', null, '1530.00');
INSERT INTO `fy_base_person` VALUES ('122', '张家伟', '广东', '', '1995-11-14', '2018-03-12', null, '13592749272', '初中', '群众', '外宿', 'CNC操机员', '20', '在职', '3400.00', '3400.00', '2018-04-01', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '2018.5.2加工报废 影响交期 记小过罚款30元                         2018.5.16加工批量件报废 记小过 罚款30元', '', '外宿', '未婚', null, '3300.00');
INSERT INTO `fy_base_person` VALUES ('123', '周忠昭', '湖南', '', '1965-07-01', '2018-02-01', null, '13559743428', '小学', '群众', '宿舍', '厨师', '23', '在职', '3000.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '202', '已婚', null, '3000.00');
INSERT INTO `fy_base_person` VALUES ('124', '黄艳', '江西', '', '1991-03-13', '2018-03-02', null, '13660706465', '中专', '群众', '宿舍', '文员', null, '在职', '4200.00', '4200.00', '2018-07-01', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '301', '已婚', null, '4000.00');
INSERT INTO `fy_base_person` VALUES ('125', '郑德辉', '广西', '', '1993-05-15', '2018-03-05', null, '13686637269', '中专', '群众', '外宿', 'CNC操机员', '20', '在职', '3400.00', '3400.00', '2018-04-01', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '外宿', '未婚', null, '3200.00');
INSERT INTO `fy_base_person` VALUES ('126', '冼敏', '湖南', '', '1991-07-02', '2017-11-10', null, '18029194505', '中专', '群众', '宿舍', '质检员', null, '在职', '3400.00', '3400.00', '2018-01-01', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '304', '未婚', null, '3100.00');
INSERT INTO `fy_base_person` VALUES ('127', '莫春合', '广西', '', '1989-10-25', '2017-10-31', null, '13412811178', '初中', '群众', '外宿', '铣床操机员', '20', '离职', '3000.00', '3000.00', '2018-02-01', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '壮族', null, null, null, null, null, '外宿', '已婚', null, '2800.00');
INSERT INTO `fy_base_person` VALUES ('128', '刘诰', '江西', '', '1992-10-12', '2017-10-16', null, '15179622273', '大专', '团员', '宿舍', '钳工', '20', '在职', '1900.00', '1900.00', '2018-02-01', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '303', '未婚', null, '1600.00');
INSERT INTO `fy_base_person` VALUES ('129', '杨科', '湖南', '', '2000-01-09', '2017-07-17', null, '17711640629', '中专', '群众', '宿舍', '学徒', '20', '在职', '2300.00', '1700.00', '2017-12-01', '2000.00', '2018-04-01', '2300.00', null, null, '2018-07-01', null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '202', '未婚', null, '1530.00');
INSERT INTO `fy_base_person` VALUES ('130', '何云峰', '湖南', '', '1995-02-02', '2017-05-08', null, '15673441040', '大专', '团员', '宿舍', '采购', '19', '在职', '4500.00', '3500.00', '2017-10-01', '4000.00', '2018-01-01', '4500.00', null, null, '2018-07-01', null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '302', '未婚', null, '3000.00');
INSERT INTO `fy_base_person` VALUES ('131', '林泽发', '广西', '', '1996-06-20', '2017-04-13', null, '13557645724', '初中', '群众', '宿舍', '学徒', '20', '在职', '2400.00', '1800.00', '2017-09-01', '2100.00', '2018-02-01', '2400.00', null, null, '2018-07-01', null, null, null, null, null, null, null, null, '汉族', null, null, null, '2018.8.1工件损失100元，罚款50元', '', '303', '已婚', null, '1530.00');
INSERT INTO `fy_base_person` VALUES ('132', '古赋军', '江西', '', '1990-05-06', '2016-02-19', null, '15919877569', '小学', '团员', '外宿', '铣床操机员', '20', '在职', '3500.00', '2700.00', '2016-04-01', '2850.00', '2017-03-01', '3000.00', '3200.00', '3500.00', '2017-06-01', '2017-10-01', '2018-08-01', null, null, null, null, null, null, '汉族', null, null, null, null, null, '外宿', '已婚', null, '2400.00');
INSERT INTO `fy_base_person` VALUES ('133', '陈智华', '湖南', '', '1989-03-04', '2016-04-20', null, '13728854328', '初中', '群众', '宿舍', '学徒', '20', '在职', '2600.00', '1800.00', '2016-09-01', '2100.00', '2016-12-01', '2400.00', '2600.00', null, '2017-05-01', '2017-10-01', null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '303', '未婚', null, '1530.00');
INSERT INTO `fy_base_person` VALUES ('134', '凌太延', '江西', '', '1987-11-15', '2016-05-28', null, '13798891848', '高中', '群众', '宿舍', '铣床操机员', '20', '在职', '3500.00', '2700.00', '2016-07-01', '2850.00', '2017-03-01', '3000.00', '3200.00', '3500.00', '2017-06-01', '2017-10-01', '2018-08-01', null, null, null, null, null, null, '汉族', null, null, null, '2016.7.25加工失误报废一件       2016.8.11加工特急件过程粗心报废两件 记小过 罚款30元', '2018.6.13旷工半天 记小过罚款50元 扣当月全勤', '303', '未婚', null, '2400.00');
INSERT INTO `fy_base_person` VALUES ('135', '许涛亮', '江西', '', '1988-10-30', '2016-06-01', null, '13925838807', '高中', '群众', '宿舍', '磨床操机员', '20', '在职', '3250.00', '3000.00', '2016-07-01', '3100.00', '2017-03-01', '3250.00', null, null, '2017-10-01', null, null, null, null, null, null, null, null, '汉族', null, null, null, '2018.5.2 加工报废 记小过 罚款30元   2018.8.1工件损失100元，罚款50元', '', '304', '已婚', null, '2800.00');
INSERT INTO `fy_base_person` VALUES ('136', '杨奎贤', '湖南', '', '1986-07-01', '2017-02-13', null, '15116698461', '初中', '群众', '宿舍', '车床操机', '20', '在职', '3200.00', '3000.00', '2017-04-01', '3200.00', '2017-06-01', null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '303', '已婚', null, '2800.00');
INSERT INTO `fy_base_person` VALUES ('137', '林航', '福建', '', '1990-11-15', '2013-05-01', null, '15015225858', '中专', '群众', '宿舍', '主管', '24', '在职', '8000.00', '8000.00', '2015-01-01', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '', '', '302', '已婚', null, '5000.00');
INSERT INTO `fy_base_person` VALUES ('140', '何盛宗', '广西', '', '1993-04-24', '2018-08-03', null, '13877715820', '初中', '群众', '外宿', '普工', '20', '在职', '1600.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, '13877715820', null, null, '外宿', '未婚', null, '1600.00');
INSERT INTO `fy_base_person` VALUES ('141', '江华云', '江西', '', null, null, null, '15019958022', '高中', '群众', '宿舍', '线割操机员', '20', '在职', '3100.00', '3300.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, '2018.9.12质量问题记小过，罚款30元', null, '304', '已婚', null, '3100.00');
INSERT INTO `fy_base_person` VALUES ('142', '蒋国华', '湖南', '', null, null, null, '18007465760', '初中', '群众', '宿舍', '学徒', '20', '在职', '1530.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '瑶族', null, null, null, null, null, '304', '未婚', null, '1530.00');
INSERT INTO `fy_base_person` VALUES ('143', '阳露', '湖南', '', '2000-09-16', '2018-08-25', null, '18244756125', '高中', '群众', '宿舍', '学徒', '20', '在职', '1530.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '汉族', null, null, null, null, null, '303', '未婚', null, '1530.00');

-- ----------------------------
-- Table structure for `fy_base_supplier`
-- ----------------------------
DROP TABLE IF EXISTS `fy_base_supplier`;
CREATE TABLE `fy_base_supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '厂商名称',
  `code` varchar(50) DEFAULT NULL COMMENT '社会统一代码',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `bank_account` varchar(50) DEFAULT NULL,
  `bank_no` varchar(50) DEFAULT NULL COMMENT '银行账户',
  `settlement_type` int(5) DEFAULT NULL COMMENT '结算方式，1 现金 ， 2 转账  ，3 票据',
  `settlement_cycle` int(5) DEFAULT NULL COMMENT '结算周期，1 现结30天，2 现结60天 ，3现金',
  `contact_person` varchar(20) DEFAULT NULL,
  `contact_type` varchar(20) DEFAULT NULL,
  `annex` varchar(20) DEFAULT NULL COMMENT '附加资料',
  `base_phone` varchar(30) DEFAULT NULL COMMENT '座机、传真',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `supplier_no` varchar(50) DEFAULT NULL COMMENT '厂商编码',
  `category` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_base_supplier
-- ----------------------------
INSERT INTO `fy_base_supplier` VALUES ('27', '东莞市宏途橡塑制品有限公司', '91441900MA4WMDCK92', '寮步镇横坑社区百业五金城93号', '13580705098', '中国建设银行股份有限公司东莞寮步寮星分理处', '44050177773700000000', '3', '3', '洪生', '13580705098', null, null, null, 'CS-11', null);
INSERT INTO `fy_base_supplier` VALUES ('28', '东莞市恒元机械有限公司', '91441900MA4W96HP1A', '东莞市万江街道简沙洲社区清水凹村创业园区纬四路展泓科技园A栋2号', '13421973987', '东莞农村商业银行万江简沙洲支行', '060180190010009278', '2', '2', '刘恒中', '13421973987', null, null, null, 'CS-12', null);
INSERT INTO `fy_base_supplier` VALUES ('29', '东莞市越博金属材料有限公司', '91441900MA4W1QD88X', '东莞市寮步镇凫山金兴路595号', '0769-89879909', '中国建设银行股份有限公司东莞东坑支行', '44050177800800000259', '2', '2', '邓先生', '15876429867', null, null, null, 'CS-13', null);
INSERT INTO `fy_base_supplier` VALUES ('30', '深圳市万盛兴机械设备制造厂', '9144030068038326XR', '深圳市光明新区公明办事处李松蓢社区第二工业区荣泰佳厂房一栋二楼A区', '0755-29756554', '深圳市农村商业银行股份有限公司公明支行', '000046455812', '2', '2', '王保杰', '0755-29756554', null, null, null, 'CS-14', null);
INSERT INTO `fy_base_supplier` VALUES ('31', '东莞市寮步捷富五金加工店', '92441900L66050898H', '东莞市寮步镇新旧围村莞樟路良平段166号', '13377755328', '东莞农村商业银行寮步良平支行', '130070190010009043', '2', '2', '陈桂明', '13377755328', null, null, null, 'CS-15', null);
INSERT INTO `fy_base_supplier` VALUES ('32', '东莞市鸿升精密五金厂', '92441900MA4YRCL929', '东莞市茶山镇横江村棠里绿化坊12号', '13713332243', '东莞农村商业银行有限公司茶山横江村分理处', '280280190010003343', '2', '2', '魏士龙', '13713332243', null, null, null, 'CS-16', null);
INSERT INTO `fy_base_supplier` VALUES ('33', '东莞市万江勋华精密机械配件厂', '92441900MA4YKP5G5M', '东莞市万江街道简沙洲社区商业街北路一巷23号', '18825524220', '东莞农村商业银行股份有限公司万江新和分理处', '060200190010003929', '2', '2', '肖勋平', '18825524220', null, null, null, 'CS-17', null);
INSERT INTO `fy_base_supplier` VALUES ('34', '东莞市磊翔模具配件有限公司', '91441900MA4WYN036T', '东莞市长安镇厦边社区沙新街18号一楼', '13433066886', '东莞银行股份有限公司虎门支行', '520000801015254', '2', '2', '唐孝良', '13433066886', null, null, null, 'CS-18', null);
INSERT INTO `fy_base_supplier` VALUES ('35', '东莞市旺好电控设备有限公司', '9144190055173107XL', '东莞市东城街道温塘茶下工业区商业街一横路11号', '13712846308', '中国银行东莞会展支行635357750136', '635357750136', '1', '1', '王剑荣', '13712846308', null, null, null, 'CS-19', null);
INSERT INTO `fy_base_supplier` VALUES ('36', '深圳南德精密机械有限公司', '914403005763846844', '深圳市宝安区新桥街道黄埔社区上南东路128号3栋3楼', '13670002343', '中国银行股份有限公司深圳沙井支行', '762757933690', '3', '3', '夏总', '13670002343', null, null, null, 'CS-20', null);
INSERT INTO `fy_base_supplier` VALUES ('37', '东莞市金康宇精密模具有限公司', '91441900MA51E8AF10', '广东省东莞市长安镇上沙社区创立路9号', '18676930350', '东莞银行长安富兴支行', '580002115007403', '2', '2', '许总', '18676930350', null, null, null, 'CS-21', null);
INSERT INTO `fy_base_supplier` VALUES ('38', '森励金属有限公司', '91441900MA4ULBJD3J', '东莞市横沥四海工业区', '13711975911', '东莞农村商业银行股份有限公司横沥支行', '240160190010002951', '3', '3', '程利', '13711975911', null, null, null, 'CS-22', null);
INSERT INTO `fy_base_supplier` VALUES ('39', '东莞市仁昌五金制品有限公司', '91441900073496887H', '东莞市万江区莫屋社区莫新村新丰东一路A7号', '0769-22276203', '东莞农村商业银行万江石美支行', '060020190010008859', '2', '2', '沈胜喜', '13500091137', null, null, null, 'CS-23', null);
INSERT INTO `fy_base_supplier` VALUES ('40', '东莞市寮步新世达模具钢材经营部', '92441900MA4WUGA02', '东莞市寮步镇新旧围社区古楼岭路60号', '0769-82817871', '中国农业银行东莞东城支行', '44272601040014116', '2', '2', '李莲生', '13922523790', null, null, null, 'CS-24', null);
INSERT INTO `fy_base_supplier` VALUES ('41', '东莞市锦宏精密五金制造有限公司', '91441900MA4UWKUU78', '东莞市万江街道莫屋社区莫屋第二工业区大塘横路 1 号 3 区', '13790162397', '中国建设银行股份有限公司东莞江南支行', '44050177005900000099', '2', '2', '陈晓', '13790162397', null, null, null, 'CS-25', null);
INSERT INTO `fy_base_supplier` VALUES ('42', '方源模具零件加工', '', '东莞市石碣镇四甲黎屋新维六巷2号', '134183237942', '东莞石碣镇支行', '6228480608893370000', '2', '2', '曾生', '134183237942', null, null, null, 'CS-26', null);
INSERT INTO `fy_base_supplier` VALUES ('43', '东莞市瑄铭机械设备有限公司', '91441900MA5186BD3W', '东莞市寮步镇石步村细岭街2号一楼', 'O76988926372', '东莞市农村商业银行股份有限公司寮步石步支行', '130040190010008379', '2', '2', '罗成兵', '13925720390', null, null, null, 'CS-27', null);
INSERT INTO `fy_base_supplier` VALUES ('44', '东莞市耀盈特殊钢材有限公司', '91441900MA4UK8YW0F', '东莞市寮步镇上屯村百业路12号办工厂房一楼', '0769-22083706', '东莞农村商业银行股份有限公司东城光明分理处', '120270190010004000', '1', '1', '赵小姐', '13609691225', null, null, null, 'CS-28', null);
INSERT INTO `fy_base_supplier` VALUES ('45', '东莞东城奕卓机械加工厂', '92441900MA4X43A324', '东莞市东城街道温塘广场路75号一楼', '0769-22666680', '招商银行东莞分行星城支行', '7699 0638 6410 666', '2', '2', '张文平', '18826982013', null, null, null, 'CS-29', null);
INSERT INTO `fy_base_supplier` VALUES ('46', '深圳正峰机密机械有限公司', '91440300MA5ENJTJ6R', '深圳市保安区沙井街道坣岗社区沙园路17号B栋102', '0755-23204202', '平安银行深圳沙井支行', '15000089699540', '3', '3', '黎赛文', '15019228268', null, null, null, 'CS-30', null);
INSERT INTO `fy_base_supplier` VALUES ('47', '海翔', '', '', '', '东莞农村商业银行东莞东城兴园支行', '6230  3888 0001  5906  060', null, '2', '艾东觉', '', null, null, null, 'CS-31', null);
INSERT INTO `fy_base_supplier` VALUES ('48', '方源', '', '', '', '中国农业银行股份有限公司东莞石碣支行', '6228 4806 0889 3371 971', null, '2', '曾小东', '', null, null, null, 'CS-32', null);
INSERT INTO `fy_base_supplier` VALUES ('49', '华强', '', '', '', '中国工商银行股份有限公司东莞长安支行', '6212 2620 1003 0998 717', null, '2', '陈家华', '', null, null, null, 'CS-33', null);
INSERT INTO `fy_base_supplier` VALUES ('50', '陈金樵', '', '', '', '中国银行股份有限公司东莞桥头支行', '6217 8570 0006 0853 551', null, '2', '陈金樵', '', null, null, null, 'CS-34', null);
INSERT INTO `fy_base_supplier` VALUES ('51', '俊联', '', '', '', '中国银行股份有限公司东莞南城支行', '6217 8670 0000 1234 463', null, '2', '陈涛', '', null, null, null, 'CS-35', null);
INSERT INTO `fy_base_supplier` VALUES ('52', '新凝', '', '', '', '中国农业银行股份有限公司东莞万江支行', '6228 4806 0605 7207 577', null, '2', '戴秋香', '', null, null, null, 'CS-36', null);
INSERT INTO `fy_base_supplier` VALUES ('53', '金峰胜', '', '', '', '中国光大银行股份有限公司珠海拱北支行', '6226 6612 0295 7779', null, '2', '邓文芳', '', null, null, null, 'CS-37', null);
INSERT INTO `fy_base_supplier` VALUES ('54', '富迪邦', '', '', '', '平安银行股份有限公司惠州分行营业部', '6216 2630 0000 1262 425', null, '2', '丁月萍', '', null, null, null, 'CS-38', null);
INSERT INTO `fy_base_supplier` VALUES ('55', '启华盛', '', '', '', '广东省建设银行', '6217 0032 3002 4240 420', null, '2', '窦笛', '', null, null, null, 'CS-39', null);
INSERT INTO `fy_base_supplier` VALUES ('56', '伸柏', '', '', '', '招商银行股份有限公司东莞分行', '6226 0976 9068 9592', null, '2', '葛林龙', '', null, null, null, 'CS-40', null);
INSERT INTO `fy_base_supplier` VALUES ('57', '力信', '', '', '', '中国农业银行', '6228  4806  0193  8876 419', null, '2', '洪俊平', '', null, null, null, 'CS-41', null);
INSERT INTO `fy_base_supplier` VALUES ('58', '京熙', '', '', '', '中国工商银行股份有限公司东莞长安支行', '6212 2620 1003 3615 391', null, '2', '胡禹', '', null, null, null, 'CS-42', null);
INSERT INTO `fy_base_supplier` VALUES ('59', '祥鹏', '', '', '', '中国农业银行股份有限公司东莞长安厦边支行', '6228 4806 0539 3107 715', null, '2', '黄祥鹏', '', null, null, null, 'CS-43', null);
INSERT INTO `fy_base_supplier` VALUES ('60', '顺达', '', '', '', '东莞农村商业银行东城温塘支行', '1206 2000 7513 480', null, '2', '柯亚胜', '', null, null, null, 'CS-44', null);
INSERT INTO `fy_base_supplier` VALUES ('61', '腾达', '', '', '', '东莞农村商业银行股份有限公司', '6230 3888 0001 2303 584', null, '2', '李昌达', '', null, null, null, 'CS-45', null);
INSERT INTO `fy_base_supplier` VALUES ('62', '科艺', '', '', '', '东莞农村商业银行茶山支行', '6223  2828  0009  8749  462', null, '2', '李惠光', '', null, null, null, 'CS-46', null);
INSERT INTO `fy_base_supplier` VALUES ('63', '腾鑫', '', '', '', '广东省建设银行', '6227 0032 3004 0011 952', null, '2', '李锦程', '', null, null, null, 'CS-47', null);
INSERT INTO `fy_base_supplier` VALUES ('64', '金辉', '', '', '', '中国农业银行股份有限公司东莞东城支行', '6228 4806 0937 0323 477', null, '2', '李奇伟', '', null, null, null, 'CS-48', null);
INSERT INTO `fy_base_supplier` VALUES ('65', '和月', '', '', '', '东莞农村商业银行股份有限公司', '1206 2000 8015 109', null, '2', '李胜和', '', null, null, null, 'CS-49', null);
INSERT INTO `fy_base_supplier` VALUES ('66', '和兴', '', '', '', '中国农业银行股份有限公司东莞东城支行', '6228 4806 0622 8684 274', null, '2', '李兴松', '', null, null, null, 'CS-50', null);
INSERT INTO `fy_base_supplier` VALUES ('67', '东核', '', '', '', '邮政储蓄东莞塘厦莲湖支行', '621098602000969676268', null, '2', '刘八路', '', null, null, null, 'CS-51', null);
INSERT INTO `fy_base_supplier` VALUES ('68', '永兴', '', '', '', '中国银行股份有限公司东莞东城支行', '6217 8670 0000 0725 560', null, '2', '刘秋雄', '', null, null, null, 'CS-52', null);
INSERT INTO `fy_base_supplier` VALUES ('69', '金泰', '', '', '', '中国工商银行股份有限公司东莞长安支行', '6222 0820 1000 3051 830', null, '2', '刘永平', '', null, null, null, 'CS-53', null);
INSERT INTO `fy_base_supplier` VALUES ('70', '长城', '', '', '', '中国农业银行股份有限公司东莞寮步广场支行', '6228 4806 0932 6939 178', null, '2', '卢炳权', '', null, null, null, 'CS-54', null);
INSERT INTO `fy_base_supplier` VALUES ('71', '欣卓', '', '', '', '中国农业银行东莞寮步支行', '6228480606725900074', null, '2', '罗成兵', '', null, null, null, 'CS-55', null);
INSERT INTO `fy_base_supplier` VALUES ('72', '振凯', '', '', '', '东莞农村商业银行股份有限公司寮步支行', '6230 3888 0002 5289 069', null, '2', '吕忠平', '', null, null, null, 'CS-56', null);
INSERT INTO `fy_base_supplier` VALUES ('73', '伟隆', '', '', '', '中国农业银行股份有限公司东莞樟木头支行', '6228 4806 0382 4746 615', null, '2', '马土金', '', null, null, null, 'CS-57', null);
INSERT INTO `fy_base_supplier` VALUES ('74', '昌隆', '', '', '', '东莞农村商业银行股份有限公司东城支行', '6223 2828 0004 5925 751', null, '2', '蒙庚怀', '', null, null, null, 'CS-58', null);
INSERT INTO `fy_base_supplier` VALUES ('75', '聚安', '', '', '', '广东省建设银行', '4367 4232 3036 0944 194', null, '2', '容玉梅', '', null, null, null, 'CS-59', null);
INSERT INTO `fy_base_supplier` VALUES ('76', '鼎炬', '', '', '', '中国农业银行佛山南海西江支行', '6228  4814  6671  9938  971', null, '2', '邵刚', '', null, null, null, 'CS-60', null);
INSERT INTO `fy_base_supplier` VALUES ('77', '三鑫', '', '', '', '东莞农村商业银行股份有限公司', '6230 3888 0002 6973 760', null, '2', '宋晓峰', '', null, null, null, 'CS-61', null);
INSERT INTO `fy_base_supplier` VALUES ('78', '富展', '', '', '', '中国工商银行股份有限公司东莞分行业务处理中心', '6212  2620  1000  4376  494', null, '2', '覃科富', '', null, null, null, 'CS-62', null);
INSERT INTO `fy_base_supplier` VALUES ('79', '铭飞', '', '', '', '东莞农村商业银行股份有限公司', '6223 2828 0001 2591 149', null, '2', '屠国娟', '', null, null, null, 'CS-63', null);
INSERT INTO `fy_base_supplier` VALUES ('80', '东豪', '', '', '', '广东省建设银行', '6217 0032 3002 2503 589', null, '2', '魏晓东', '', null, null, null, 'CS-64', null);
INSERT INTO `fy_base_supplier` VALUES ('81', '腾龙', '', '', '', '中国工商银行股份有限公司东莞南城支行', '6222 0820 1000 2423 352', null, '2', '吴海飞', '', null, null, null, 'CS-65', null);
INSERT INTO `fy_base_supplier` VALUES ('82', '普华', '', '', '', '中国银行东莞南城支行', '6216  6170  0400  6795  309', null, '2', '吴小芬', '', null, null, null, 'CS-66', null);
INSERT INTO `fy_base_supplier` VALUES ('83', '富力', '', '', '', '农业银行东莞石碣支行', '6228 4806 0889 3374 876', null, '2', '吴艺平', '', null, null, null, 'CS-67', null);
INSERT INTO `fy_base_supplier` VALUES ('84', '凯康', '', '', '', '中国农业银行东莞寮步支行', '6228  4806  0634  5655  272', null, '2', '吴志强', '', null, null, null, 'CS-68', null);
INSERT INTO `fy_base_supplier` VALUES ('85', '镒凯', '', '', '', '中国农业银行东莞樟木头支行', '6228  4606  0001  4182  019', null, '2', '肖水花', '', null, null, null, 'CS-69', null);
INSERT INTO `fy_base_supplier` VALUES ('86', '勋华', '', '', '', '中国农业银行股份有限公司东莞南城支行', '6228 4806 0850 1046 973', null, '2', '肖勋平', '', null, null, null, 'CS-70', null);
INSERT INTO `fy_base_supplier` VALUES ('87', '协达', '', '', '', '中国工商银行股份有限公司东莞寮步支行', '6212 2620 1002 0578 008', null, '2', '谢亚光', '', null, null, null, 'CS-71', null);
INSERT INTO `fy_base_supplier` VALUES ('88', '星光', '', '', '', '中国工商银行股份有限公司东莞东城支行', '6222 0220 1003 3158 392', null, '2', '徐教声', '', null, null, null, 'CS-72', null);
INSERT INTO `fy_base_supplier` VALUES ('89', '群祥', '', '', '', '中国工商银行石碣崇焕支行', '6222  0220  1000  0973  583', null, '2', '许群飞', '', null, null, null, 'CS-73', null);
INSERT INTO `fy_base_supplier` VALUES ('90', '豪隆', '', '', '', '中国工商银行股份有限公司东莞长安支行', '6222 0220 1003 3954 394', null, '2', '杨建明', '', null, null, null, 'CS-74', null);
INSERT INTO `fy_base_supplier` VALUES ('91', '捷诚', '', '', '', '中国农业银行股份有限公司东莞黄江支行', '6228 4806 0143 9664 314', null, '2', '张丽君', '', null, null, null, 'CS-75', null);
INSERT INTO `fy_base_supplier` VALUES ('92', '盛兴加工', null, null, null, '广东省建设银行', '6236 6832 3000 4210 192', null, '2', '张树海', null, null, null, null, 'CS-76', null);
INSERT INTO `fy_base_supplier` VALUES ('93', '百腾', '', '', '', '中国银行东莞会展支行', '6216 6170 0400 4185 420', null, '2', '张勇旗', '', null, null, null, 'CS-77', null);
INSERT INTO `fy_base_supplier` VALUES ('94', '莞泰', '', '', '', '中国工商银行股份有限公司东莞谢岗支行', '6222 0220 1002 5558 062', null, '2', '赵玉梅', '', null, null, null, 'CS-78', null);
INSERT INTO `fy_base_supplier` VALUES ('95', '南羽', '', '', '', '中国农业银行股份有限公司东莞东城支行', '6228 4806 0936 8841 779', null, '2', '郑京林', '', null, null, null, 'CS-79', null);
INSERT INTO `fy_base_supplier` VALUES ('96', '安泰', '', '', '', '中国农业银行股份有限公司东莞东城支行', '6228 4806 0624 9124 870', null, '2', '钟苑如', '', null, null, null, 'CS-80', null);
INSERT INTO `fy_base_supplier` VALUES ('97', '鸿川', '', '', '', '中国农业银行股份有限公司东莞厚街惠民支行', '9559 9806 0159 6311 212', null, '2', '朱爱军', '', null, null, null, 'CS-81', null);
INSERT INTO `fy_base_supplier` VALUES ('98', '匠盛', null, null, null, '东莞农村商业银行支行', '6223282800096214774', '1', '2', '包基兵', null, null, null, null, 'CS-82', null);

-- ----------------------------
-- Table structure for `fy_base_tax_rate`
-- ----------------------------
DROP TABLE IF EXISTS `fy_base_tax_rate`;
CREATE TABLE `fy_base_tax_rate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tax_rate` varchar(10) DEFAULT NULL,
  `match_value` decimal(4,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_base_tax_rate
-- ----------------------------
INSERT INTO `fy_base_tax_rate` VALUES ('8', '17%', '0.17');
INSERT INTO `fy_base_tax_rate` VALUES ('9', '16%', '0.16');
INSERT INTO `fy_base_tax_rate` VALUES ('10', '12%', '0.12');
INSERT INTO `fy_base_tax_rate` VALUES ('11', '11%', '0.11');
INSERT INTO `fy_base_tax_rate` VALUES ('12', '10%', '0.10');
INSERT INTO `fy_base_tax_rate` VALUES ('13', '8%', '0.08');
INSERT INTO `fy_base_tax_rate` VALUES ('14', '6%', '0.06');
INSERT INTO `fy_base_tax_rate` VALUES ('15', '5%', '0.05');
INSERT INTO `fy_base_tax_rate` VALUES ('16', '3%', '0.03');
INSERT INTO `fy_base_tax_rate` VALUES ('17', '0', '0.00');

-- ----------------------------
-- Table structure for `fy_base_unit`
-- ----------------------------
DROP TABLE IF EXISTS `fy_base_unit`;
CREATE TABLE `fy_base_unit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '单位名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_base_unit
-- ----------------------------
INSERT INTO `fy_base_unit` VALUES ('6', '套');
INSERT INTO `fy_base_unit` VALUES ('7', '件');
INSERT INTO `fy_base_unit` VALUES ('8', 'KG');
INSERT INTO `fy_base_unit` VALUES ('9', '块');
INSERT INTO `fy_base_unit` VALUES ('10', 'ea');
INSERT INTO `fy_base_unit` VALUES ('11', 'pcs');

-- ----------------------------
-- Table structure for `fy_biz_ww_receive`
-- ----------------------------
DROP TABLE IF EXISTS `fy_biz_ww_receive`;
CREATE TABLE `fy_biz_ww_receive` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) DEFAULT NULL COMMENT '关联基础资料类别',
  `planer_id` int(11) DEFAULT NULL COMMENT '关联人员',
  `execu_status` varchar(20) DEFAULT NULL COMMENT '执行状态',
  `urgent_status` varchar(20) DEFAULT NULL COMMENT '紧急状态',
  `order_date` date DEFAULT NULL COMMENT '订单日期',
  `delivery_date` date DEFAULT NULL COMMENT '交货日期',
  `work_order_no` varchar(20) NOT NULL COMMENT '工作订单号',
  `delivery_no` varchar(20) DEFAULT NULL COMMENT '送货单号',
  `commodity_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `commodity_spec` varchar(50) DEFAULT NULL COMMENT '商品规格',
  `map_no` int(11) DEFAULT NULL COMMENT '总图号，关联图纸',
  `technology` varchar(500) DEFAULT NULL COMMENT '技术条件',
  `machining_require` varchar(500) DEFAULT NULL COMMENT '加工要求',
  `quantity` decimal(12,2) DEFAULT NULL COMMENT '数量',
  `unit` int(11) DEFAULT NULL COMMENT '单位，关联基础资料',
  `distribute_time` datetime DEFAULT NULL COMMENT '分配时间',
  `handl_status` varchar(10) DEFAULT NULL COMMENT '处理状态',
  `category_tmp` varchar(10) DEFAULT NULL COMMENT '分类中文',
  `plan_tmp` varchar(10) DEFAULT NULL COMMENT '计划员',
  `unit_tmp` varchar(10) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL COMMENT '订单号',
  `parent_id` int(11) DEFAULT NULL COMMENT '父单ID',
  `is_receive` bit(1) DEFAULT NULL COMMENT '是否已接收',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_biz_ww_receive
-- ----------------------------
INSERT INTO `fy_biz_ww_receive` VALUES ('11', null, null, '启动', '正常', '2018-06-29', '2018-07-25', '20180626-678', 'SH1806286878442', '脚轮固定板', '21E710-7759-A1', null, '', '', '4.00', null, '2018-07-25 23:14:07', null, '自动化', '朱润红', 'ea', '5282', '5310', '');

-- ----------------------------
-- Table structure for `fy_business_assist`
-- ----------------------------
DROP TABLE IF EXISTS `fy_business_assist`;
CREATE TABLE `fy_business_assist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `assist_date` date DEFAULT NULL COMMENT '外协日期',
  `single` int(11) DEFAULT NULL COMMENT '单件',
  `assist_cost` decimal(10,2) DEFAULT NULL COMMENT '外协工序单价',
  `assist_account` decimal(10,2) DEFAULT NULL COMMENT '外协工序金额',
  `tax_rate` decimal(12,2) DEFAULT NULL COMMENT '税率	根据基本信息中自动添加',
  `tax_amount` decimal(10,2) DEFAULT NULL COMMENT '税额	设置：=金额*税率',
  `tatol_amount` decimal(10,2) DEFAULT NULL COMMENT '含税金额	设置：=金额+税额',
  `assist_supplier_id` int(11) DEFAULT NULL COMMENT '外协厂商',
  `assist_process` varchar(50) DEFAULT NULL COMMENT '外协工序',
  `run_time` double(8,2) DEFAULT NULL COMMENT '外协加工耗时',
  `backtime` datetime DEFAULT NULL COMMENT '外协回厂时间',
  `check_result` varchar(50) DEFAULT NULL COMMENT '外协回厂检测结果',
  `is_create_pay` bit(1) DEFAULT NULL COMMENT '是否生成应付明细表',
  `create_time` datetime DEFAULT NULL COMMENT '生成时间',
  `parent_id` int(11) DEFAULT NULL COMMENT '上一个单据',
  `order_id` int(11) DEFAULT NULL COMMENT '订单',
  `assist_no` varchar(20) DEFAULT NULL COMMENT '外协单号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_business_assist
-- ----------------------------
INSERT INTO `fy_business_assist` VALUES ('16', null, null, null, null, null, null, null, '98', '表处理-发黑', '1.50', null, '合格', '', '2018-10-30 10:41:05', '16685', '16685', 'H20181030-1');

-- ----------------------------
-- Table structure for `fy_business_bill`
-- ----------------------------
DROP TABLE IF EXISTS `fy_business_bill`;
CREATE TABLE `fy_business_bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) DEFAULT NULL COMMENT '关联基础资料类别',
  `planer_id` int(11) DEFAULT NULL COMMENT '关联人员',
  `work_order_no` varchar(20) DEFAULT NULL COMMENT '工作订单号',
  `delivery_no` varchar(20) DEFAULT NULL COMMENT '送货单号',
  `commodity_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `commodity_spec` varchar(50) DEFAULT NULL COMMENT '商品规格',
  `map_no` int(11) DEFAULT NULL COMMENT '总图号，关联图纸',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  `unit` int(11) DEFAULT NULL COMMENT '单位，关联基础资料',
  `untax_cost` decimal(10,2) DEFAULT NULL COMMENT '未税单价',
  `hang_time` datetime DEFAULT NULL COMMENT '挂账时间	根据应收结算单反写生成',
  `hang_status` varchar(8) DEFAULT NULL COMMENT '挂账状态	1.已挂账2.部分挂账3.待挂账',
  `hang_quantity` int(11) DEFAULT NULL COMMENT '挂账数量	根据应收结算单反写生成',
  `hang_amount` decimal(12,2) DEFAULT NULL COMMENT '挂账金额',
  `unhang_quantity` int(11) DEFAULT NULL COMMENT '未挂账数量	根据应收结算单反写生成',
  `is_create_paybill` bit(1) DEFAULT NULL COMMENT '是否生成计算单',
  `paybill_create_time` datetime DEFAULT NULL COMMENT '计算时间',
  `category_tmp` varchar(10) DEFAULT NULL COMMENT '分类中文',
  `plan_tmp` varchar(10) DEFAULT NULL COMMENT '计划员',
  `order_id` int(11) DEFAULT NULL COMMENT '订单号',
  `parent_id` int(11) DEFAULT NULL COMMENT '父单ID',
  `fy_biz_ww_receive` varchar(10) DEFAULT NULL COMMENT '分类中文',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_business_bill
-- ----------------------------

-- ----------------------------
-- Table structure for `fy_business_distribute`
-- ----------------------------
DROP TABLE IF EXISTS `fy_business_distribute`;
CREATE TABLE `fy_business_distribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) DEFAULT NULL COMMENT '关联基础资料类别',
  `planer_id` int(11) DEFAULT NULL COMMENT '关联人员',
  `execu_status` varchar(20) DEFAULT NULL COMMENT '执行状态',
  `urgent_status` varchar(20) DEFAULT NULL COMMENT '紧急状态',
  `order_date` date DEFAULT NULL COMMENT '订单日期',
  `delivery_date` date DEFAULT NULL COMMENT '交货日期',
  `work_order_no` varchar(20) NOT NULL COMMENT '工作订单号',
  `delivery_no` varchar(20) DEFAULT NULL COMMENT '送货单号',
  `commodity_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `commodity_spec` varchar(50) DEFAULT NULL COMMENT '商品规格',
  `map_no` int(11) DEFAULT NULL COMMENT '总图号，关联图纸',
  `technology` varchar(500) DEFAULT NULL COMMENT '技术条件',
  `machining_require` varchar(500) DEFAULT NULL COMMENT '加工要求',
  `quantity` decimal(12,4) DEFAULT NULL COMMENT '数量',
  `unit` int(11) DEFAULT NULL COMMENT '单位，关联基础资料',
  `distribute_to` varchar(8) DEFAULT NULL COMMENT '分配流向',
  `distribute_attr` varchar(10) DEFAULT NULL COMMENT '分配属性',
  `distribute_time` datetime DEFAULT NULL COMMENT '分配时间',
  `order_id` int(11) DEFAULT NULL COMMENT '订单Id',
  `category_tmp` varchar(20) DEFAULT NULL COMMENT '关联基础资料类别',
  `receive_time` datetime DEFAULT NULL,
  `unit_tmp` varchar(20) DEFAULT NULL,
  `plan_tmp` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_business_distribute
-- ----------------------------

-- ----------------------------
-- Table structure for `fy_business_getpay`
-- ----------------------------
DROP TABLE IF EXISTS `fy_business_getpay`;
CREATE TABLE `fy_business_getpay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) DEFAULT NULL COMMENT '关联基础资料类别',
  `planer_id` int(11) DEFAULT NULL COMMENT '关联人员',
  `work_order_no` varchar(20) DEFAULT NULL COMMENT '工作订单号',
  `delivery_no` varchar(20) DEFAULT NULL COMMENT '送货单号',
  `commodity_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `commodity_spec` varchar(50) DEFAULT NULL COMMENT '商品规格',
  `map_no` int(11) DEFAULT NULL COMMENT '总图号，关联图纸',
  `quantity` decimal(12,2) DEFAULT NULL COMMENT '数量',
  `unit` int(11) DEFAULT NULL COMMENT '单位，关联基础资料',
  `untax_cost` decimal(10,2) DEFAULT NULL COMMENT '未税单价',
  `pay_unit` int(11) DEFAULT NULL COMMENT '单位',
  `out_time` datetime DEFAULT NULL COMMENT '出库时间',
  `out_quantity` decimal(12,2) DEFAULT NULL COMMENT '出库数量',
  `is_create_bill` bit(1) DEFAULT NULL COMMENT '是否生成结算单	',
  `bill_quantity` int(11) DEFAULT NULL COMMENT '结算数量',
  `bill_create_time` datetime DEFAULT NULL COMMENT '生成时间',
  `category_tmp` varchar(10) DEFAULT NULL COMMENT '分类中文',
  `plan_tmp` varchar(10) DEFAULT NULL COMMENT '计划员',
  `unit_tmp` varchar(10) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL COMMENT '订单号',
  `parent_id` int(11) DEFAULT NULL COMMENT '父单ID',
  `create_month` int(5) DEFAULT NULL COMMENT '生成月份，挂账月份',
  `getpay_month` int(5) DEFAULT NULL COMMENT '收款月份',
  `can_download` bit(1) DEFAULT b'1' COMMENT '是否可以下载',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_business_getpay
-- ----------------------------

-- ----------------------------
-- Table structure for `fy_business_getpaybill`
-- ----------------------------
DROP TABLE IF EXISTS `fy_business_getpaybill`;
CREATE TABLE `fy_business_getpaybill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(50) DEFAULT NULL COMMENT '客户名称',
  `invoice_period` varchar(10) DEFAULT NULL COMMENT '开票期间',
  `invoice_account` decimal(12,2) DEFAULT NULL COMMENT '开票金额（含税）',
  `bill_period` varchar(10) DEFAULT NULL COMMENT '结算期间',
  `bill_tool` varchar(20) DEFAULT NULL COMMENT '结算工具',
  `bill_account` decimal(12,2) DEFAULT NULL COMMENT '结算金额',
  `unbill_account` decimal(12,2) DEFAULT NULL COMMENT '未结算金额',
  `order_id` int(11) DEFAULT NULL COMMENT '订单号',
  `parent_id` int(11) DEFAULT NULL COMMENT '父单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_business_getpaybill
-- ----------------------------

-- ----------------------------
-- Table structure for `fy_business_in_warehouse`
-- ----------------------------
DROP TABLE IF EXISTS `fy_business_in_warehouse`;
CREATE TABLE `fy_business_in_warehouse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `in_time` datetime DEFAULT NULL COMMENT '入库时间',
  `in_from` varchar(10) DEFAULT NULL COMMENT '入库来源，委外，自产',
  `check_create_time` datetime DEFAULT NULL COMMENT '生成时间',
  `check_time` datetime DEFAULT NULL COMMENT '检测时间',
  `check_result` varchar(8) DEFAULT NULL COMMENT '检测结果：合格，不合格',
  `check_handle` varchar(10) DEFAULT NULL COMMENT '检测处理：1、报废 2.返工 3.让步接收 4.正常',
  `is_create_can_out` bit(1) DEFAULT NULL COMMENT '是否生成待出库',
  `order_id` int(11) DEFAULT NULL COMMENT '订单号',
  `parent_id` int(11) DEFAULT NULL COMMENT '父单ID',
  `create_time` date DEFAULT NULL COMMENT '挂账期间',
  `pay_month` date DEFAULT NULL COMMENT '付账期间',
  `in_quantity` int(11) DEFAULT '0' COMMENT '入库数量',
  `pass_quantity` int(11) DEFAULT '0' COMMENT '合格数量',
  `unpass_quantity` int(11) DEFAULT '0' COMMENT '不合格数量',
  `exception_reson` varchar(255) DEFAULT NULL COMMENT '异常原因',
  `check_remark` varchar(255) DEFAULT NULL COMMENT '检测备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_business_in_warehouse
-- ----------------------------
INSERT INTO `fy_business_in_warehouse` VALUES ('58', '2018-10-30 10:37:19', '采购', null, '2018-10-30 10:37:58', '部分合格', null, null, '16684', null, null, null, '4', '3', '1', '垂直度超差', ' ');
INSERT INTO `fy_business_in_warehouse` VALUES ('59', '2018-10-30 10:37:25', '采购', null, '2018-10-30 10:37:43', '合格', null, null, '16684', null, null, null, '1', '1', '0', null, ' ');

-- ----------------------------
-- Table structure for `fy_business_order`
-- ----------------------------
DROP TABLE IF EXISTS `fy_business_order`;
CREATE TABLE `fy_business_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) DEFAULT NULL COMMENT '关联基础资料类别',
  `planer_id` int(11) DEFAULT NULL COMMENT '关联人员',
  `execu_status` varchar(20) DEFAULT NULL COMMENT '执行状态',
  `customer_no` varchar(20) DEFAULT NULL COMMENT '紧急状态',
  `order_date` datetime DEFAULT NULL COMMENT '订单日期',
  `delivery_date` date DEFAULT NULL COMMENT '交货日期',
  `work_order_no` varchar(20) DEFAULT NULL COMMENT '工作订单号',
  `delivery_no` varchar(20) DEFAULT NULL COMMENT '送货单号',
  `commodity_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `commodity_spec` varchar(50) DEFAULT NULL COMMENT '商品规格',
  `map_no` varchar(50) DEFAULT NULL COMMENT '图号',
  `technology` varchar(500) DEFAULT NULL COMMENT '技术条件',
  `machining_require` varchar(500) DEFAULT NULL COMMENT '加工要求',
  `quantity` int(11) DEFAULT '0' COMMENT '数量',
  `unit` int(11) DEFAULT NULL COMMENT '单位，关联基础资料',
  `untaxed_cost` decimal(10,2) DEFAULT NULL COMMENT '未税金额',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `tax_rate` decimal(8,2) DEFAULT NULL COMMENT '税率，关联基础资料',
  `tax_amount` decimal(10,2) DEFAULT '0.00',
  `tatol_amount` decimal(10,2) DEFAULT '0.00' COMMENT '含税金额',
  `import_time` datetime DEFAULT NULL COMMENT '导入时间',
  `distribute_time` datetime DEFAULT NULL COMMENT '分配时间',
  `receive_time` datetime DEFAULT NULL COMMENT '接收时间',
  `warn_time` datetime DEFAULT NULL COMMENT '分配预警时间',
  `draw` int(11) DEFAULT NULL COMMENT '图纸',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `hang_time` datetime DEFAULT NULL COMMENT '挂账时间',
  `hang_status` varchar(255) DEFAULT NULL,
  `hang_quantity` int(11) DEFAULT '0' COMMENT '挂账数量',
  `unhang_quantity` int(11) DEFAULT '0' COMMENT '未挂账数量',
  `cate_tmp` varchar(20) DEFAULT NULL COMMENT '分类中文',
  `plan_tmp` varchar(20) DEFAULT NULL COMMENT '计划员',
  `map_tmp` varchar(50) DEFAULT NULL COMMENT '总图号tmp',
  `unit_tmp` varchar(10) DEFAULT NULL COMMENT '单位tmp',
  `tax_tmp` varchar(10) DEFAULT NULL,
  `distribute_to` varchar(8) DEFAULT NULL COMMENT '分配流向，委外或自产',
  `distribute_attr` varchar(10) DEFAULT NULL COMMENT '分配属性，首次、撤回',
  `is_distribute` bit(1) DEFAULT b'0' COMMENT '是否为分配表',
  `orderby` int(11) DEFAULT '0',
  `is_create_next` bit(1) DEFAULT b'0' COMMENT '是否已生成下一个单据，如生产汇总，生成生产计划单',
  `is_create_plan` bit(1) DEFAULT b'0' COMMENT '是否为生产计划表',
  `plan_time` datetime DEFAULT NULL COMMENT '预计投产时间',
  `plan_finsh_time` datetime DEFAULT NULL COMMENT '预计完工时间',
  `plan_remark` varchar(200) DEFAULT NULL COMMENT '生产备注',
  `in_warehouse_time` datetime DEFAULT NULL COMMENT '全部入库时间',
  `finish_time` datetime DEFAULT NULL COMMENT '生成计划单完工时间',
  `is_create_in_house` bit(1) DEFAULT b'0' COMMENT '是否已生成入库单',
  `dis_to` int(5) DEFAULT NULL COMMENT '分配为自产为0，委外为1',
  `handle_status` varchar(20) DEFAULT NULL COMMENT '处理状态，未处理，处理中，已处理',
  `is_finish_purchase` bit(1) DEFAULT b'0' COMMENT '是否已完成采购',
  `storage_quantity` int(11) DEFAULT '0' COMMENT '库存',
  `out_quantity` int(11) DEFAULT '0' COMMENT '已出库数量',
  `customer` int(11) DEFAULT NULL COMMENT '客户',
  `has_in_quantity` int(11) DEFAULT '0' COMMENT '已入库数量',
  `hang_account` decimal(12,2) DEFAULT '0.00' COMMENT '挂账金额',
  `ww_quantity` int(11) DEFAULT '0' COMMENT '委外挂账数量',
  `ww_unquantity` int(11) DEFAULT '0' COMMENT '委外未挂账数量',
  `ww_hang_amount` int(11) DEFAULT '0' COMMENT '委外挂账金额',
  `ww_unhang_amount` int(11) DEFAULT '0' COMMENT '未挂账金额',
  `send_address` varchar(100) DEFAULT NULL COMMENT '发货地址',
  `is_finsh` bit(1) DEFAULT b'0' COMMENT '是否已结束',
  `is_all_in_house` bit(1) DEFAULT b'0' COMMENT '是否全入库',
  `ready_id` int(11) DEFAULT NULL COMMENT '补单id',
  `model_no` varchar(50) DEFAULT NULL COMMENT '型号',
  `out_house_date` date DEFAULT NULL COMMENT '出库时间',
  `weiwai_cate` varchar(20) DEFAULT NULL COMMENT '委外分类',
  `total_map_no` varchar(255) DEFAULT NULL COMMENT '总图号',
  `order_status` int(11) DEFAULT '0' COMMENT '订单扩展状态位,默认0,1是委外接收表，2委外执行表,10为自产接收表，11为自产计划表,12生产一览表，20，为备货接收表',
  `inhouse_status` varchar(10) DEFAULT '未入库' COMMENT '入库状态，未入库，部分入库，已入库',
  `inhouse_date` date DEFAULT NULL COMMENT '入库时间',
  `is_finsh_product` varchar(255) DEFAULT NULL COMMENT '是否成品',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16688 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_business_order
-- ----------------------------
INSERT INTO `fy_business_order` VALUES ('16496', null, null, '备货', 'ZHGD-LY', null, '2018-09-03', '', '', '收口底座Ⅱ', '', '21E706-13313-J27', '', '', '3', null, '315.00', '945.00', '0.16', '50.40', '365.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16497', null, null, '备货', 'ZHGD-LY', null, '2018-09-03', '', '', '收口压头', '', '21E701-2207-J27', '', '', '3', null, '150.00', '450.00', '0.16', '24.00', '174.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16498', null, null, '备货', 'ZHGD-LY', null, '2018-09-03', '', '', '装配夹具', '', '21E706-140-J27(007)', '', '', '2', null, '816.00', '1632.00', '0.16', '130.56', '946.56', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16499', null, null, '备货', 'ZHGD-LY', null, '2018-09-04', '', '', '导柱', '', '21E708-9071-A1(001)', '', '', '50', null, '75.00', '3750.00', '0.16', '12.00', '87.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '50', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '50', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16500', null, null, '备货', 'ZHGD-LY', null, '2018-09-04', '', '', '钛合金螺母', '', '21E705-5830-A1(001)', '', '', '16', null, '80.00', '1280.00', '0.16', '12.80', '92.80', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '16', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '16', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16501', null, null, '备货', 'ZHGD-LY', null, '2018-09-04', '', '', '机箱氧化挂具', '', '21E705-5829-A1(001)', '', '', '1', null, '536.00', '536.00', '0.16', '85.76', '621.76', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16502', null, null, '备货', 'ZHGD-LY', null, '2018-09-04', '', '', '氧化夹具', '', '21E705-4692-A1(001)', '', '', '8', null, '530.00', '4240.00', '0.16', '84.80', '614.80', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '8', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '8', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16503', null, null, '备货', 'ZHGD-LY', null, '2018-09-04', '', '', 'CRM220校正基座', '', '21E706-9958-J8', '', '', '1', null, '568.00', '568.00', '0.16', '90.88', '658.88', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16504', null, null, '备货', 'ZHGD-LY', null, '2018-09-04', '', '', '电阻焊两排焊头', '', '21E354-2998-A1(001)', '', '', '50', null, '65.00', '3250.00', '0.16', '10.40', '75.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '50', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '50', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16505', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '6A-626345-1', 'SH1809037439338', '电阻焊中间焊头', '', '21E354-2997-A1(001)', '', '', '40', null, '65.00', '2600.00', '0.16', '10.40', '75.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '40', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '40', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16506', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '6A-623984-7', 'SH1809037439189', '灌胶压线板', '', '21E346-8245-B1(001)', '', '', '2', null, '4430.00', '8860.00', '0.16', '708.80', '5138.80', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16507', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '6A-626160-2', 'SH1809037439165', '绞针通用推入夹具手柄A', '', '21E350-808-L34(001)', '', '', '200', null, '39.00', '7800.00', '0.16', '6.24', '45.24', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '200', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '200', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16508', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-566', 'SH1809017401989', '', '', '21E710-18444-A1(001)', '', '', '1', null, '160.00', '160.00', '0.16', '25.60', '185.60', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18444-A1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16509', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-565', 'SH1809017402007', '', '', '21E710-18380-J8(001)', '', '', '1', null, '340.00', '340.00', '0.16', '54.40', '394.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18380-J8', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16510', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-557', 'SH1809017401993', '', '', '21E710-18440-J8(001)', '', '', '1', null, '390.00', '390.00', '0.16', '62.40', '452.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18440-J8', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16511', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-562', 'SH1809017401994', '', '', '21E710-18437-J8(001)', '', '', '1', null, '390.00', '390.00', '0.16', '62.40', '452.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18437-J8', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16512', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-564', 'SH1809017402002', '', '', '21E710-18385-J8(001)', '', '', '1', null, '375.00', '375.00', '0.16', '60.00', '435.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18385-J8', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16513', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-563', 'SH1809017402005', '', '', '21E710-18382-J8(001)', '', '', '1', null, '192.00', '192.00', '0.16', '30.72', '222.72', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18382-J8', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16514', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-504', 'SH1809017401991', '', '', '21E710-18442-A1(001)', '', '', '1', null, '160.00', '160.00', '0.16', '25.60', '185.60', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18442-A1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16515', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-488', 'SH1809017401985', '', '', '21E710-18448-A1(001)', '', '', '1', null, '160.00', '160.00', '0.16', '25.60', '185.60', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18448-A1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16516', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-505', 'SH1809017402000', '', '', '21E710-18387-J8(001)', '', '', '1', null, '140.00', '140.00', '0.16', '22.40', '162.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18387-J8', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16517', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-497', 'SH1809017401999', '', '', '21E710-18390-J8(001)', '', '', '1', null, '390.00', '390.00', '0.16', '62.40', '452.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18390-J8', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16518', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-489', 'SH1809017401998', '', '', '21E710-18391-J8(001)', '', '', '1', null, '360.00', '360.00', '0.16', '57.60', '417.60', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18391-J8', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16519', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-496', 'SH1809017401997', '', '', '21E710-18397-A1(001)', '', '', '1', null, '160.00', '160.00', '0.16', '25.60', '185.60', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18397-A1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16520', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-493', 'SH1809017401996', '', '', '21E710-18398-A1(001)', '', '', '1', null, '360.00', '360.00', '0.16', '57.60', '417.60', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18398-A1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16521', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-500', 'SH1809017401995', '', '', '21E710-18399-A1(001)', '', '', '1', null, '170.00', '170.00', '0.16', '27.20', '197.20', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18399-A1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16522', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-502', 'SH1809017401990', '', '', '21E710-18443-A1(001)', '', '', '1', null, '360.00', '360.00', '0.16', '57.60', '417.60', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18443-A1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16523', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-492', 'SH1809017401986', '', '', '21E710-18447-A1(001)', '', '', '1', null, '160.00', '160.00', '0.16', '25.60', '185.60', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18447-A1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16524', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-542', 'SH1809017402003', '', '', '21E710-18384-J8(001)', '', '', '1', null, '180.00', '180.00', '0.16', '28.80', '208.80', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18384-J8', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16525', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-498', 'SH1809017402004', '', '', '21E710-18383-J8(001)', '', '', '1', null, '140.00', '140.00', '0.16', '22.40', '162.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18383-J8', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16526', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-495', 'SH1809017401992', '', '', '21E710-18441-A1(001)', '', '', '1', null, '360.00', '360.00', '0.16', '57.60', '417.60', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18441-A1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16527', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-490', 'SH1809017402006', '', '', '21E710-18381-J8(001)', '', '', '1', null, '170.00', '170.00', '0.16', '27.20', '197.20', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18381-J8', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16528', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-499', 'SH1809017402001', '', '', '21E710-18386-J8(001)', '', '', '1', null, '190.00', '190.00', '0.16', '30.40', '220.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18386-J8', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16529', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-477', 'SH1809017401988', '', '', '21E710-18445-J8(001)', '', '', '1', null, '350.00', '350.00', '0.16', '56.00', '406.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18445-J8', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16530', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180827-476', 'SH1809017401987', '', '', '21E710-18446-J8(001)', '', '', '1', null, '350.00', '350.00', '0.16', '56.00', '406.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E813-028-B1,工装号: 21E710-18446-J8', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16531', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180824-650', 'SH1809017401981', '短销', '', '21E710-21510-J8', '', '', '24', null, '15.00', '360.00', '0.16', '2.40', '17.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '24', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '24', '0', '0', '', '', '', null, '', null, null, '.项目编号XJSCY,工装号: 21E809-014-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16532', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180824-651', 'SH1809017401984', '导轨板', '', '21E710-18561-J8', '', '', '6', null, '490.00', '2940.00', '0.16', '78.40', '568.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '6', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '6', '0', '0', '', '', '', null, '', null, null, '.项目编号XJSCY,工装号: 21E809-014-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16533', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180824-646', 'SH1809017401967', '门', '', '21E710-21527-A1', '', '', '4', null, '230.00', '920.00', '0.16', '36.80', '266.80', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '4', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '4', '0', '0', '', '', '', null, '', null, null, '.项目编号XJSCY,工装号: 21E809-014-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16534', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180824-632', 'SH1809017401965', '脚轮固定板', '', '21E710-7759-A1', '', '', '4', null, '140.00', '560.00', '0.16', '22.40', '162.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '4', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '4', '0', '0', '', '', '', null, '', null, null, '.项目编号XJSCY,工装号: 21E809-014-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16535', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180824-627', 'SH1809017401973', '大底板', '', '21E710-21521-J8', '', '', '1', null, '5600.00', '5600.00', '0.16', '896.00', '6496.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.项目编号XJSCY,工装号: 21E809-014-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16536', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180824-619', 'SH1809017401972', '电机防护罩', '', '21E710-21522-A1', '', '', '3', null, '102.00', '306.00', '0.16', '16.32', '118.32', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.项目编号XJSCY,工装号: 21E809-014-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16537', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180824-606', 'SH1809017401974', '插座组件底板', '', '21E710-21520-J8', '', '', '3', null, '688.00', '2064.00', '0.16', '110.08', '798.08', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.项目编号XJSCY,工装号: 21E809-014-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16538', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180824-599', 'SH1809017401983', '感应片', '', '21E710-18571-A1', '', '', '3', null, '100.00', '300.00', '0.16', '16.00', '116.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.项目编号XJSCY,工装号: 21E809-014-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16539', null, null, '启动', 'ZHGD-LY', null, '2018-09-04', '20180824-1', 'SH1809037406362', '安装板', '', '21E710-19362-J8(001)', '', '', '1', null, '380.00', '380.00', '0.16', '60.80', '440.80', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.21E807-186-B1,工装号: 21E710-19362-J8', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16540', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '6A-627464-17', 'SH1809047446146', '放线架', '', '21E353-7125-A1(001)', '', '', '3', null, '8000.00', '24000.00', '0.16', '1280.00', '9280.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16541', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '6A-627425-5', 'SH1809057456122', '整套装配', '', '21E705-5839-B1(001)', '', '', '1', null, '852.60', '852.60', '0.16', '136.42', '989.02', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16542', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '6A-627244-5', 'SH1809057456121', '整套装配', '', '21E351-12218-B1(001)', '', '', '2', null, '651.70', '1303.40', '0.16', '104.27', '755.97', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16543', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '6A-627251-6', 'SH1809057456120', '整套装配', '', '21E705-5833-B1(001)', '', '', '2', null, '945.70', '1891.40', '0.16', '151.31', '1097.01', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16544', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '6A-623815-1', 'SH1809057456108', '整套装配', '', '21E341-8358-B1(001)', '', '', '2', null, '1127.00', '2254.00', '0.16', '180.32', '1307.32', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16545', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '6A-625589-3', 'SH1809057456109', '整套装配', '', '21E353-19155-B1(001)', '', '', '2', null, '313.60', '627.20', '0.16', '50.18', '363.78', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16546', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '6A-625394-1', 'SH1809057456110', '整套装配', '', '21E352-321-B1(001)', '', '', '2', null, '490.00', '980.00', '0.16', '78.40', '568.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16547', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '6A-625059-6', 'SH1809057456111', '整套装配', '', '21E351-12198-B1(001)', '', '', '2', null, '390.00', '780.00', '0.16', '62.40', '452.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16548', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '6A-625294-5', 'SH1809057456112', '整套装配', '', '21E710-21667-B1(001)', '', '', '1', null, '529.20', '529.20', '0.16', '84.67', '613.87', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16549', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '6A-625904-1', 'SH1809057456102', '整套装配', '', '21E355-444-B1(001)', '', '', '1', null, '380.00', '380.00', '0.16', '60.80', '440.80', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16550', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '6A-625706-3', 'SH1809057456103', '整套装配', '', '21E335-5948-B1(001)', '', '', '1', null, '260.00', '260.00', '0.16', '41.60', '301.60', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16551', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '6A-625453-2', 'SH1809057456105', '整套装配', '', '21E353-19169-B1(001)', '', '', '2', null, '800.00', '1600.00', '0.16', '128.00', '928.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16552', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '6A-627104-1', 'SH1809057456106', '21E6-001-4902-B1铆接底座', '', '21E335-5952-J14(001)', '', '', '2', null, '261.00', '522.00', '0.16', '41.76', '302.76', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16553', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '6A-625554-2', 'SH1809057456107', '整套装配', '', '21E353-19164-B1(001)', '', '', '1', null, '6076.00', '6076.00', '0.16', '972.16', '7048.16', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16554', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '6A-625704-2', 'SH1809057456123', '21E6-001-4902-B1铆接底座', '', '21E335-5949-J8(001)', '', '', '2', null, '279.30', '558.60', '0.16', '44.69', '323.99', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16555', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '6A-626864-5', 'SH1809057456114', 'M8螺母转接头', '', '21E353-16923-J27(003)', '', '', '2', null, '254.80', '509.60', '0.16', '40.77', '295.57', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16556', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '6A-626734-1', 'SH1809057456007', '整套装配', '', '21E353-19182-B1(001)', '', '', '1', null, '774.20', '774.20', '0.16', '123.87', '898.07', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16557', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '20180831-617', 'SH1809057458144', '顶块', '', '21E710-21762-J8', '', '', '5', null, '90.00', '450.00', '0.16', '14.40', '104.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '5', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '5', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E353-18920-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16558', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '20180831-616', 'SH1809057458145', '定位块', '', '21E710-21760-J8', '', '', '5', null, '297.00', '1485.00', '0.16', '47.52', '344.52', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '5', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '5', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E353-18920-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16559', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '20180831-606', 'SH1809057458154', '下盖板', '', '21E710-21758-J8', '', '', '5', null, '108.00', '540.00', '0.16', '17.28', '125.28', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '5', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '5', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E353-18920-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16560', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '20180831-601', 'SH1809057458121', '上模盖板', '', '21E710-21763-J8', '', '', '1', null, '117.00', '117.00', '0.16', '18.72', '135.72', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E353-18920-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16561', null, null, '启动', 'ZHGD-LY', null, '2018-09-05', '20180831-600', 'SH1809057458164', '底板', '', '21E710-21759-J8', '', '', '1', null, '351.00', '351.00', '0.16', '56.16', '407.16', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E353-18920-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16562', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', 'WO-1808113823', 'SH1809057460497', '设备2-配重工装', '', '21E6-060-5366-B1', '', '', '1', null, '1200.00', '1200.00', '0.16', '192.00', '1392.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '龚建维', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16563', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', 'WO-1808113824', 'SH1809057460498', '设备3-配重工装', '', '21E6-060-5369-B1', '', '', '1', null, '1500.00', '1500.00', '0.16', '240.00', '1740.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '龚建维', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16564', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', 'WO-1808113822', 'SH1809057460495', '照明灯-配重工装', '', '21E6-040-4516-B1', '', '', '1', null, '240.00', '240.00', '0.16', '38.40', '278.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '龚建维', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16565', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', 'WO-1808113821', 'SH1809057460494', '设备6-配重机箱', '', '21E6-040-4506-B1', '', '', '1', null, '7500.00', '7500.00', '0.16', '1200.00', '8700.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '龚建维', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16566', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', 'WO-1808113820', 'SH1809057460492', '设备1-工装', '', '21E6-040-4491-B1', '', '', '1', null, '1500.00', '1500.00', '0.16', '240.00', '1740.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '龚建维', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16567', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', 'WO-1808113819', 'SH1809057460490', '设备5-配重工装', '', '21E6-040-4490-B1', '', '', '1', null, '1000.00', '1000.00', '0.16', '160.00', '1160.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '龚建维', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16568', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', 'WO-1808113818', 'SH1809057460488', '设备7-配重工装', '', '21E6-040-4489-B1', '', '', '1', null, '1200.00', '1200.00', '0.16', '192.00', '1392.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '龚建维', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16569', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', 'WO-1808113816', 'SH1809057460455', '辅显示器', '', '21E6-040-4470-B1', '', '', '1', null, '1700.00', '1700.00', '0.16', '272.00', '1972.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '龚建维', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16570', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', 'WO-1808113815', 'SH1809057460453', '设备4配重', '', '21E6-040-4469-B1', '', '', '1', null, '500.00', '500.00', '0.16', '80.00', '580.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '龚建维', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16571', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', 'WO-1808113817', 'SH1809057460460', '主显示器配重', '', '21E6-040-4471-B1', '', '', '1', null, '2700.00', '2700.00', '0.16', '432.00', '3132.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '1', '', '龚建维', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16572', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-297', 'SH1809067466310', '面板', '', '21E705-5162-E31', '', '', '4', null, '330.00', '1320.00', '0.16', '52.80', '382.80', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '4', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '4', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16573', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-299', 'SH1809067466308', '分水阀', '', '21E705-5147-E31', '', '', '4', null, '115.00', '460.00', '0.16', '18.40', '133.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '4', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '4', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16574', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-298', 'SH1809067466309', '支座', '', '21E705-5158-E31', '', '', '16', null, '105.00', '1680.00', '0.16', '16.80', '121.80', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '16', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '16', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16575', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-300', 'SH1809067466307', '主箱体', '', '21E705-5139-A1', '', '', '5', null, '320.00', '1600.00', '0.16', '51.20', '371.20', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '5', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '5', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16576', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-281', 'SH1809067466316', '防护板', '', '21E705-5163-P50', '', '', '8', null, '155.00', '1240.00', '0.16', '24.80', '179.80', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '8', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '8', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16577', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-286', 'SH1809067466311', '支脚', '', '21E705-5135-P50', '', '', '16', null, '82.00', '1312.00', '0.16', '13.12', '95.12', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '16', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '16', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16578', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-285', 'SH1809067466312', '电控箱', '', '21E705-5136-A1', '', '', '4', null, '253.00', '1012.00', '0.16', '40.48', '293.48', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '4', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '4', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16579', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-284', 'SH1809067466313', '挡水板', '', '21E705-5141-A1', '', '', '8', null, '185.00', '1480.00', '0.16', '29.60', '214.60', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '8', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '8', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16580', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-283', 'SH1809067466314', '气缸固定块', '', '21E705-5150-J8', '', '', '8', null, '115.00', '920.00', '0.16', '18.40', '133.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '8', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '8', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16581', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-282', 'SH1809067466315', '喷头固定块', '', '21E705-5152-J8', '', '', '16', null, '232.00', '3712.00', '0.16', '37.12', '269.12', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '16', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '16', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16582', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-277', 'SH1809067466317', '导柱固定块', '', '21E705-5149-J8', '', '', '8', null, '105.00', '840.00', '0.16', '16.80', '121.80', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '8', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '8', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16583', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-276', 'SH1809067466318', '水管固定块', '', '21E705-5157-E31', '', '', '8', null, '105.00', '840.00', '0.16', '16.80', '121.80', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '8', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '8', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16584', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-275', 'SH1809067466319', '底板', '', '21E707-11385-E31', '', '', '4', null, '650.00', '2600.00', '0.16', '104.00', '754.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '4', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '4', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16585', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-267', 'SH1809067466320', '内构架', '', '21E705-5140-A1', '', '', '4', null, '315.00', '1260.00', '0.16', '50.40', '365.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '4', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '4', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16586', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-265', 'SH1809067466322', '担板', '', '21E705-5161-E31', '', '', '8', null, '165.00', '1320.00', '0.16', '26.40', '191.40', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '8', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '8', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16587', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-266', 'SH1809067466321', '上气嘴固定块', '', '21E705-5153-E31', '', '', '16', null, '105.00', '1680.00', '0.16', '16.80', '121.80', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '16', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '16', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16588', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-259', 'SH1809067466306', '电气板', '', '21E705-5137-A1', '', '', '4', null, '132.00', '528.00', '0.16', '21.12', '153.12', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '4', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '4', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16589', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-252', 'SH1809067466325', '下气嘴固定块', '', '21E705-5159-E31', '', '', '8', null, '105.00', '840.00', '0.16', '16.80', '121.80', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '8', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '8', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16590', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-253', 'SH1809067466323', '活动板', '', '21E705-5151-J8', '', '', '4', null, '475.00', '1900.00', '0.16', '76.00', '551.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '4', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '4', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16591', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-254', 'SH1809067466324', '电箱盖板', '', '21E705-5138-A1', '', '', '4', null, '75.00', '300.00', '0.16', '12.00', '87.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '4', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '4', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16592', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-243', 'SH1809067466326', '分气阀', '', '21E705-5148-E31', '', '', '4', null, '160.00', '640.00', '0.16', '25.60', '185.60', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '4', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '4', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16593', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-237', 'SH1809067466328', '气嘴', '', '21E705-5160-A1', '', '', '32', null, '58.00', '1856.00', '0.16', '9.28', '67.28', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '32', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '32', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E801-180-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16594', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-215', 'SH1809067466202', '转接板', '', '21E710-21739-J8', '', '', '3', null, '135.00', '405.00', '0.16', '21.60', '156.60', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E811-013-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16595', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-206', 'SH1809067466290', '电气安装板', '', '21E710-21744-A1', '', '', '3', null, '176.00', '528.00', '0.16', '28.16', '204.16', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E811-013-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16596', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-205', 'SH1809067466291', '转接板', '', '21E710-21740-J8', '', '', '3', null, '405.00', '1215.00', '0.16', '64.80', '469.80', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E811-013-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16597', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-204', 'SH1809067466292', '开关盒', '', '21E710-12896-A1', '', '', '3', null, '112.20', '336.60', '0.16', '17.95', '130.15', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E811-013-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16598', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-198', 'SH1809067466293', '防护罩', '', '21E710-21757-A1', '', '', '3', null, '288.00', '864.00', '0.16', '46.08', '334.08', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E811-013-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16599', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-195', 'SH1809067466294', '后面板', '', '21E710-21743-A1', '', '', '3', null, '70.00', '210.00', '0.16', '11.20', '81.20', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E811-013-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16600', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-191', 'SH1809067466298', '支撑块', '', '21E710-12883-J8', '', '', '6', null, '75.00', '450.00', '0.16', '12.00', '87.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '6', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '6', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E811-013-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16601', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-192', 'SH1809067466297', '连接板', '', '21E710-12891-A1', '', '', '3', null, '62.50', '187.50', '0.16', '10.00', '72.50', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E811-013-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16602', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-193', 'SH1809067466296', '下底板', '', '21E710-12894-A1', '', '', '3', null, '62.50', '187.50', '0.16', '10.00', '72.50', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E811-013-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16603', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-194', 'SH1809067466295', '后盖', '', '21E710-12897-A1', '', '', '3', null, '37.50', '112.50', '0.16', '6.00', '43.50', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E811-013-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16604', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-184', 'SH1809067466300', '防护板2', '', '21E710-21755-A1', '', '', '3', null, '185.00', '555.00', '0.16', '29.60', '214.60', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E811-013-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16605', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-185', 'SH1809067466299', '防护板1', '', '21E710-21756-A1', '', '', '3', null, '125.00', '375.00', '0.16', '20.00', '145.00', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E811-013-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16606', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-179', 'SH1809067466301', '左面板', '', '21E710-21741-A1', '', '', '3', null, '70.00', '210.00', '0.16', '11.20', '81.20', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E811-013-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16607', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-178', 'SH1809067466302', '大底板', '', '21E710-21737-J8', '', '', '3', null, '1336.00', '4008.00', '0.16', '213.76', '1549.76', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E811-013-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16608', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-174', 'SH1809067466303', '右面板', '', '21E710-21742-A1', '', '', '3', null, '80.00', '240.00', '0.16', '12.80', '92.80', '2018-10-30 10:26:44', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E811-013-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16609', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-172', 'SH1809067466305', '固定板', '', '21E710-12882-J8', '', '', '6', null, '118.80', '712.80', '0.16', '19.01', '137.81', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '6', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '6', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E811-013-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16610', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180903-173', 'SH1809067466304', '连接块', '', '21E710-21738-J8', '', '', '3', null, '85.00', '255.00', '0.16', '13.60', '98.60', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '3', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '.工装号: 21E811-013-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16611', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180829-21', 'SH1809067466287', '聚氨酯套', '', '21E710-18933-A1(001)', '', '', '6', null, '95.00', '570.00', '0.16', '15.20', '110.20', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '6', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '6', '0', '0', '', '', '', null, '', null, null, '.21E802-365/370/371-B1,工装号: 21E710-18933-A1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16612', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180829-16', 'SH1809067466175', '电机轴套', '', '21E710-18917-A1(001)', '', '', '6', null, '105.00', '630.00', '0.16', '16.80', '121.80', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '6', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '6', '0', '0', '', '', '', null, '', null, null, '.21E802-365/370/371-B1,工装号: 21E710-18917-A1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16613', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '6A-629704-1', 'SH1809067468247', '21E352-190-A1', '', '21E352-190-A1(001)', '', '', '2', null, '450.00', '900.00', '0.16', '72.00', '522.00', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16614', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180906-24', 'SH1809067465129', '导柱', '', 'OX0043000000000011', '', '', '100', null, '16.00', '1600.00', '0.16', '2.56', '18.56', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '100', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '100', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16615', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180906-22', 'SH1809067465287', '扁线螺旋弹簧', '', '07-01-257', '', '', '50', null, '16.00', '800.00', '0.16', '2.56', '18.56', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '50', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '50', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16616', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180906-15', 'SH1809067465264', '块料', '', '01-19-018', '', '', '215', null, '22.96', '4937.45', '0.16', '3.67', '26.64', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '215', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '215', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16617', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '20180906-1', 'SH1809067464961', '型芯备件', '', '21E702-281-J24', '', '', '2000', null, '2.90', '5800.00', '0.16', '0.46', '3.36', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2000', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2000', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16618', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '6A-629264-1', 'SH1809067465644', '上刀', '', '21E309-350-J12(001)', '', '', '5', null, '351.00', '1755.00', '0.16', '56.16', '407.16', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '5', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '5', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16619', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '6A-628005-1', 'SH1809067465652', '转轴', '', '21E710-21720-J14(001)', '', '', '2', null, '480.00', '960.00', '0.16', '76.80', '556.80', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16620', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '6A-628006-1', 'SH1809067465651', '转轴', '', '21E707-12736-J14(001)', '', '', '2', null, '330.00', '660.00', '0.16', '52.80', '382.80', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16621', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '6A-628514-1', 'SH1809067465650', '工具零件', '', '21E705-3948-A1(001)', '', '', '15', null, '380.00', '5700.00', '0.16', '60.80', '440.80', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '15', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '15', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16622', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '6A-626812-1', 'SH1809067465649', '滑块', '', '21E351-11839-A1(001)', '', '', '20', null, '208.00', '4160.00', '0.16', '33.28', '241.28', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '20', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '20', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16623', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '6A-627754-4', 'SH1809067465709', '整套装配', '', '21E374-960-B1(001)', '', '', '5', null, '1000.00', '5000.00', '0.16', '160.00', '1160.00', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '5', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '5', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16624', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '6A-627861-1', 'SH1809067465707', 'J30J振动', '', '21E355-3678-A1(001)', '', '', '2', null, '6785.00', '13570.00', '0.16', '1085.60', '7870.60', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16625', null, null, '启动', 'ZHGD-LY', null, '2018-09-06', '6A-626304-2', 'SH1809067465699', 'J70冲头手柄B', '', '21E353-19144-L34', '', '', '5', null, '150.00', '750.00', '0.16', '24.00', '174.00', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '5', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '5', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16626', null, null, '启动', 'ZHGD-LY', null, '2018-09-07', '6A-628585-1', 'SH1809067471726', '钛合金桶', '', '21E705-4727-A1(001)', '', '', '2', null, '2021.00', '4042.00', '0.16', '323.36', '2344.36', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16627', null, null, '启动', 'ZHGD-LY', null, '2018-09-07', '6A-627727-3', 'SH1809067468931', '基座', '', '21E408-1603-H10', '', '', '1', null, '1170.00', '1170.00', '0.16', '187.20', '1357.20', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '1', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16628', null, null, '启动', 'ZHGD-LY', null, '2018-09-07', '6A-628835-1', 'SH1809067468881', '氧化主杆', '', '21E705-4693-A1(001)', '', '', '3', null, '572.00', '1716.00', '0.16', '91.52', '663.52', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '3', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16629', null, null, '启动', 'ZHGD-LY', null, '2018-09-07', '6A-627475-2', 'SH1809067468937', 'DP2插座五号工位推针', '', '21E200-136-J21(001)', '', '', '50', null, '80.00', '4000.00', '0.16', '12.80', '92.80', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '50', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '50', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16630', null, null, '启动', 'ZHGD-LY', null, '2018-09-07', '6A-628886-1', 'SH1809067468935', '230振动框架加强', '', '21E355-3694-A1(001)', '', '', '1', null, '7950.00', '7950.00', '0.16', '1272.00', '9222.00', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '1', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16631', null, null, '启动', 'ZHGD-LY', null, '2018-09-07', '6A-628196-1', 'SH1809067468928', 'TSN-25Z拧紧扳手', '', '21E705-5849-A1(001)', '', '', '2', null, '380.00', '760.00', '0.16', '60.80', '440.80', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16632', null, null, '启动', 'ZHGD-LY', null, '2018-09-07', '6A-628195-1', 'SH1809067468927', 'TSN-25T拧紧扳手', '', '21E705-5848-A1(001)', '', '', '2', null, '380.00', '760.00', '0.16', '60.80', '440.80', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16633', null, null, '启动', 'ZHGD-LY', null, '2018-09-07', '6A-627875-1', 'SH1809067468939', '背板三防安装孔防护工装', '', '21E353-19199-P50(001)', '', '', '50', null, '20.00', '1000.00', '0.16', '3.20', '23.20', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '50', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '50', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16634', null, null, '启动', 'ZHGD-LY', null, '2018-09-07', '6A-619598-1', 'SH1809067468934', '330×60测距工装', '', '21E705-5717-H1(001)', '', '', '2', null, '320.00', '640.00', '0.16', '51.20', '371.20', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16635', null, null, '启动', 'ZHGD-LY', null, '2018-09-07', '6A-619337-1', 'SH1809067468933', '130×100测距工装', '', '21E705-5707-H1(001)', '', '', '2', null, '240.00', '480.00', '0.16', '38.40', '278.40', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16636', null, null, '启动', 'ZHGD-LY', null, '2018-09-07', '6A-629039-1', 'SH1809077513067', '不锈钢夹子', '', '21E705-5228-A1(001)', '', '', '2000', null, '8.50', '17000.00', '0.16', '1.36', '9.86', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2000', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2000', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16637', null, null, '启动', 'ZHGD-LY', null, '2018-09-07', '6A-630060-5', 'SH1809077510708', '整套装配', '', '21E350-1457-B1(002)', '', '', '300', null, '100.00', '30000.00', '0.16', '16.00', '116.00', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '300', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '300', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16638', null, null, '启动', 'ZHGD-LY', null, '2018-09-08', 'WO-1808117750', 'SH1809077478309', '机箱部件', '', '21E6-123-5455-B1', '', '', '3', null, '16600.00', '49800.00', '0.16', '2656.00', '19256.00', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '3', '', '龚建维', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16639', null, null, '启动', 'ZHGD-LY', null, '2018-09-08', '', '', '机箱部件', '', '21E6-123-5455-B1', '', '', '3', null, '0.00', '0.00', '0.00', '0.00', '0.00', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '3', '', '龚建维', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16640', null, null, '启动', 'ZHGD-LY', null, '2018-09-08', '', '', '机箱部件', '', '21E6-123-5455-B1', '', '', '3', null, '0.00', '0.00', '0.00', '0.00', '0.00', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '3', '', '龚建维', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16641', null, null, '启动', 'ZHGD-LY', null, '2018-09-08', '', '', '机箱部件', '', '21E6-123-5455-B1', '', '', '3', null, '0.00', '0.00', '0.00', '0.00', '0.00', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '3', '', '龚建维', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16642', null, null, '启动', 'ZHGD-LY', null, '2018-09-08', '', '', '机箱部件', '', '21E6-123-5455-B1', '', '', '3', null, '0.00', '0.00', '0.00', '0.00', '0.00', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '3', '', '龚建维', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16643', null, null, '启动', 'ZHGD-LY', null, '2018-09-10', '6A-629954-6', 'SH1809087522261', '整套装配', '', '21E710-5910-A1(001)', '', '', '5', null, '310.00', '1550.00', '0.16', '49.60', '359.60', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '5', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '5', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16644', null, null, '启动', 'ZHGD-LY', null, '2018-09-10', '6A-629154-2', 'SH1809087521729', '整套装配', '', '21E353-19225-B1(001)', '', '', '10', null, '790.00', '7900.00', '0.16', '126.40', '916.40', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '10', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '10', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16645', null, null, '启动', 'ZHGD-LY', null, '2018-09-10', '6A-629662-2', 'SH1809087522258', '触摸屏挡板', '', '21E353-19233-A1(001)', '', '', '2', null, '108.00', '216.00', '0.16', '17.28', '125.28', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16646', null, null, '启动', 'ZHGD-LY', null, '2018-09-10', '20180906-12', 'SH1809087519676', '浇口套', '', 'OX0043000000000009', '', '', '100', null, '55.80', '5580.00', '0.16', '8.93', '64.73', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '100', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '100', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16647', null, null, '启动', 'ZHGD-LY', null, '2018-09-10', '20180906-11', 'SH1809087519673', '导柱', '', '21E702-441-A1', '', '', '60', null, '12.00', '720.00', '0.16', '1.92', '13.92', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '60', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '60', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16648', null, null, '启动', 'ZHGD-LY', null, '2018-09-10', '6A-628166-5', 'SH1809087522256', '整套装配', '', '21E805-216-B1(001)', '', '', '2', null, '17000.00', '34000.00', '0.16', '2720.00', '19720.00', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16649', null, null, '启动', 'ZHGD-LY', null, '2018-09-10', 'WO-S18091039', 'SH1809067468716', '诺基亚24芯热缩夹具并行线缆固定板', '', '21E353-19147-A1', '', '', '1', null, '190.00', '190.00', '0.16', '30.40', '220.40', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '1', '', '沈家福', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.付瑞请购DG2018090501', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16650', null, null, '启动', 'ZHGD-LY', null, '2018-09-10', 'WO-S18091042', 'SH1809067468708', '诺基亚24芯热缩夹具线缆固定板', '', '21E353-19138-A1', '', '', '1', null, '185.00', '185.00', '0.16', '29.60', '214.60', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '1', '', '沈家福', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.付瑞请购DG2018090501', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16651', null, null, '启动', 'ZHGD-LY', null, '2018-09-10', 'WO-S18091037', 'SH1809067468715', '诺基亚24芯热缩夹具底座', '', '21E353-19148-A1', '', '', '1', null, '165.00', '165.00', '0.16', '26.40', '191.40', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '1', '', '沈家福', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.付瑞请购DG2018090501', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16652', null, null, '启动', 'ZHGD-LY', null, '2018-09-10', 'WO-S18091123', 'SH1809067468719', '玻璃盖板', '', '21E710-9394-A1', '', '', '50', null, '38.00', '1900.00', '0.16', '6.08', '44.08', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '50', '', '沈家福', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '50', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16653', null, null, '启动', 'ZHGD-LY', null, '2018-09-10', 'WO-S18091049', 'SH1809067468717', '诺基亚24芯热缩夹具插头固定板', '', '21E353-19136-A1', '', '', '1', null, '120.00', '120.00', '0.16', '19.20', '139.20', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '1', '', '沈家福', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.付瑞请购DG2018090501', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16654', null, null, '启动', 'ZHGD-LY', null, '2018-09-11', '6A-630624-1', 'SH1809107535912', 'J5594/1D21S2-振动套筒', '', '21E355-3709-A1(001)', '', '', '3', null, '2150.00', '6450.00', '0.16', '344.00', '2494.00', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '3', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16655', null, null, '启动', 'ZHGD-LY', null, '2018-09-11', '6A-627755-2', 'SH1809107535913', 'LRMS1夹具固定体A', '', '21E374-954-H10', '', '', '5', null, '580.00', '2900.00', '0.16', '92.80', '672.80', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '5', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '5', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16656', null, null, '启动', 'ZHGD-LY', null, '2018-09-11', '6A-627755-3', 'SH1809107535915', 'LRMS1焊接夹具固定壳体', '', '21E374-955-H10', '', '', '5', null, '490.00', '2450.00', '0.16', '78.40', '568.40', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '5', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '5', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16657', null, null, '启动', 'ZHGD-LY', null, '2018-09-11', '6A-630465-5', 'SH1809107536038', '固定座2', '', '21E706-13441-A1', '', '', '2', null, '285.00', '570.00', '0.16', '45.60', '330.60', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16658', null, null, '启动', 'ZHGD-LY', null, '2018-09-11', '6A-630465-4', 'SH1809107536035', '固定座', '', '21E706-13440-A1', '', '', '2', null, '235.00', '470.00', '0.16', '37.60', '272.60', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16659', null, null, '启动', 'ZHGD-LY', null, '2018-09-11', '6A-629874-4', 'SH1809107536041', 'JQ6-100ZJ-01LD10试气夹具压板', '', '21E710-18778-E31', '', '', '2', null, '95.00', '190.00', '0.16', '15.20', '110.20', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16660', null, null, '启动', 'ZHGD-LY', null, '2018-09-11', '6A-629006-5', 'SH1809117538039', 'BPC10导销装配固定工装01', '', '21E351-12245-J8', '', '', '2', null, '235.00', '470.00', '0.16', '37.60', '272.60', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16661', null, null, '启动', 'ZHGD-LY', null, '2018-09-11', '6A-629006-8', 'SH1809107536075', 'BPC10导销装配固定工装04', '', '21E351-12248-P50', '', '', '2', null, '80.00', '160.00', '0.16', '12.80', '92.80', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16662', null, null, '启动', 'ZHGD-LY', null, '2018-09-11', '6A-629006-7', 'SH1809107536074', 'BPC10导销装配固定工装03', '', '21E351-12247-J8', '', '', '2', null, '95.00', '190.00', '0.16', '15.20', '110.20', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16663', null, null, '启动', 'ZHGD-LY', null, '2018-09-11', '6A-629006-6', 'SH1809107536069', 'BPC10导销装配固定工装02', '', '21E351-12246-P50', '', '', '2', null, '150.00', '300.00', '0.16', '24.00', '174.00', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16664', null, null, '启动', 'ZHGD-LY', null, '2018-09-11', '6A-629006-10', 'SH1809107536077', '卡座', '', '21E402-2299-A1', '', '', '2', null, '75.00', '150.00', '0.16', '12.00', '87.00', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16665', null, null, '启动', 'ZHGD-LY', null, '2018-09-12', '6A-629964-1', 'SH1809117540248', '电缆组件定位夹具', '', '21E351-6381-P50(001)', '', '', '400', null, '8.00', '3200.00', '0.16', '1.28', '9.28', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '400', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '400', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16666', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', 'WO-180920083', 'SH1809117542888', '安装盒', '', '21E8-060-17361-A1', '', '', '15', null, '2500.00', '37500.00', '0.16', '400.00', '2900.00', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '15', '', '龚建维', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '15', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16667', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '6A-630648-6', 'SH1809127554392', '整套装配', '', '21E353-19238-B1(001)', '', '', '2', null, '2850.00', '5700.00', '0.16', '456.00', '3306.00', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16668', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-164', 'SH1809137561716', '相机固定板2', '', '21E710-18213-J8', '', '', '1', null, '332.50', '332.50', '0.16', '53.20', '385.70', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.项目编号GD2Q,工装号: 21E803-731-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16669', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-165', 'SH1809137561714', '载具定位板', '', '21E710-18221-A1', '', '', '1', null, '845.50', '845.50', '0.16', '135.28', '980.78', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.项目编号GD2Q,工装号: 21E803-731-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16670', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-166', 'SH1809137561708', '气缸固定块', '', '21E710-18238-E22', '', '', '1', null, '105.00', '105.00', '0.16', '16.80', '121.80', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.项目编号GD2Q,工装号: 21E803-731-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16671', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-167', 'SH1809137561707', 'stopper 4', '', '21E710-18240-A1', '', '', '2', null, '120.00', '240.00', '0.16', '19.20', '139.20', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '.项目编号GD2Q,工装号: 21E803-731-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16672', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-150', 'SH1809137561576', '支撑块', '', '21E710-21657-J8', '', '', '2', null, '214.20', '428.40', '0.16', '34.27', '248.47', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '2', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '.项目编号GD2Q,工装号: 21E803-731-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16673', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-155', 'SH1809137561593', '固定板', '', '21E710-21643-J8', '', '', '1', null, '527.00', '527.00', '0.16', '84.32', '611.32', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.项目编号GD2Q,工装号: 21E803-731-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16674', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-154', 'SH1809137561443', '触摸屏支座', '', '21E710-4067-A1', '', '', '4', null, '180.00', '720.00', '0.16', '28.80', '208.80', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '4', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '4', '0', '0', '', '', '', null, '', null, null, '.项目编号GD2Q,工装号: 21E803-731-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16675', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-152', 'SH1809137561554', '定位板', '', '21E710-21817-A1', '', '', '1', null, '450.50', '450.50', '0.16', '72.08', '522.58', '2018-10-30 10:26:45', null, null, null, null, null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.项目编号GD2Q,工装号: 21E803-731-B1', '0', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16676', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-157', 'SH1809137561582', '定位块', '', '21E710-21651-A1', '', '', '1', null, '102.00', '102.00', '0.16', '16.32', '118.32', '2018-10-30 10:26:45', '2018-10-30 10:29:01', '2018-10-30 10:29:09', null, '67', null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, '委外', '首次', '', '1', '', '', '2018-10-30 10:32:25', null, null, null, null, '', '1', '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, '钣金', '.项目编号GD2Q,工装号: 21E803-731-B1', '4', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16677', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-140', 'SH1809137561641', '支撑板', '', '21E710-21604-J8', '', '', '2', null, '807.50', '1615.00', '0.16', '129.20', '936.70', '2018-10-30 10:26:45', '2018-10-30 10:29:01', '2018-10-30 10:29:09', null, '68', null, null, '未挂账', '0', '2', '', '朱润红', null, '件', null, '委外', '首次', '', '1', '', '', '2018-10-30 10:32:25', null, null, null, null, '', '1', '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, '钣金', '.项目编号GD2Q,工装号: 21E803-731-B1', '4', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16678', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-136', 'SH1809137561717', '相机固定板', '', '21E710-18204-A1', '', '', '1', null, '530.00', '530.00', '0.16', '84.80', '614.80', '2018-10-30 10:26:45', '2018-10-30 10:29:01', '2018-10-30 10:29:09', null, '71', null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, '委外', '首次', '', '1', '', '', '2018-10-30 10:32:25', null, null, null, null, '', '1', '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, '钣金', '.项目编号GD2Q,工装号: 21E803-731-B1', '4', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16679', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-137', 'SH1809137561713', '载具限位块', '', '21E710-18222-A1', '', '', '1', null, '325.00', '325.00', '0.16', '52.00', '377.00', '2018-10-30 10:26:45', '2018-10-30 10:28:53', '2018-10-30 10:29:09', null, '72', null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, '委外', '首次', '', '1', '', '', '2018-10-30 10:32:25', null, null, null, null, '', '1', '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, '机加', '.项目编号GD2Q,工装号: 21E803-731-B1', '4', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16680', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-138', 'SH1809137561651', '收料盒', '', '21E710-21591-A1', '', '', '1', null, '255.00', '255.00', '0.16', '40.80', '295.80', '2018-10-30 10:26:45', '2018-10-30 10:28:53', '2018-10-30 10:29:09', null, '65', null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, '委外', '首次', '', '1', '', '', '2018-10-30 10:32:25', null, null, null, null, '', '1', '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, '机加', '.项目编号GD2Q,工装号: 21E803-731-B1', '4', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16681', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-139', 'SH1809137561646', '小底板', '', '21E710-21599-J8', '', '', '1', null, '187.00', '187.00', '0.16', '29.92', '216.92', '2018-10-30 10:26:45', '2018-10-30 10:28:53', '2018-10-30 10:29:09', null, '66', null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, '委外', '首次', '', '1', '', '', '2018-10-30 10:32:25', null, null, null, null, '', '1', '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, '机加', '.项目编号GD2Q,工装号: 21E803-731-B1', '4', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16682', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-141', 'SH1809137561636', '固定板', '', '21E710-21609-J8', '', '', '2', null, '161.50', '323.00', '0.16', '25.84', '187.34', '2018-10-30 10:26:45', '2018-10-30 10:28:53', '2018-10-30 10:29:09', null, '67', null, null, '未挂账', '0', '2', '', '朱润红', null, '件', null, '委外', '首次', '', '1', '', '', '2018-10-30 10:32:25', null, null, null, null, '', '1', '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, '机加', '.项目编号GD2Q,工装号: 21E803-731-B1', '4', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16683', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-132', 'SH1809137561580', '定位板', '', '21E710-21653-J8', '', '', '1', null, '348.50', '348.50', '0.16', '55.76', '404.26', '2018-10-30 10:26:45', '2018-10-30 10:28:53', '2018-10-30 10:29:09', null, '69', null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, '委外', '首次', '', '1', '', '', '2018-10-30 10:32:25', null, null, null, null, '', '1', '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, '机加', '.项目编号GD2Q,工装号: 21E803-731-B1', '4', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16684', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-126', 'SH1809137561579', '承座', '', '21E710-21654-A1', '', '', '5', null, '382.50', '1912.50', '0.16', '61.20', '443.70', '2018-10-30 10:26:45', '2018-10-30 10:28:53', '2018-10-30 10:29:09', null, '70', null, '2018-10-30 00:00:00', '未挂账', '4', '1', '', '朱润红', null, '件', null, '委外', '首次', '', '1', '', '', '2018-10-30 10:32:50', null, null, null, null, '', '1', '未处理', '', '0', '4', null, '4', '500.00', '0', '5', '0', '0', '', '', '', null, '', null, '机加', '.项目编号GD2Q,工装号: 21E803-731-B1', '4', '已入库', '2018-10-30', null);
INSERT INTO `fy_business_order` VALUES ('16685', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-128', 'SH1809137561557', '定位板', '', '21E710-21815-A1', '', '', '1', null, '433.50', '433.50', '0.16', '69.36', '502.86', '2018-10-30 10:26:45', '2018-10-30 10:28:38', '2018-10-30 10:40:23', null, '71', null, '2018-10-30 00:00:00', '未挂账', '1', '0', '', '朱润红', null, '件', null, '自产', '首次', '', '1', '', '', '2018-10-30 00:00:00', '2018-10-31 00:00:00', null, null, null, '', '0', '未处理', '', '0', '0', null, '0', null, '0', '1', '0', '0', '', '', '', null, '', null, null, '.项目编号GD2Q,工装号: 21E803-731-B1', '12', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16686', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-129', 'SH1809137561553', '定位板', '', '21E710-21818-A1', '', '', '1', null, '306.00', '306.00', '0.16', '48.96', '354.96', '2018-10-30 10:26:45', '2018-10-30 10:28:38', '2018-10-30 10:40:23', null, '72', null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, '自产', '首次', '', '1', '', '', '2018-10-30 00:00:00', '2018-10-31 00:00:00', null, null, null, '', '0', '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.项目编号GD2Q,工装号: 21E803-731-B1', '12', '未入库', null, null);
INSERT INTO `fy_business_order` VALUES ('16687', null, null, '启动', 'ZHGD-LY', null, '2018-09-13', '20180910-112', 'SH1809137561642', '定位板', '', '21E710-21603-A1', '', '', '1', null, '153.00', '153.00', '0.16', '24.48', '177.48', '2018-10-30 10:26:45', '2018-10-30 10:28:38', '2018-10-30 10:40:23', null, '72', null, null, '未挂账', '0', '1', '', '朱润红', null, '件', null, '自产', '首次', '', '1', '', '', '2018-10-30 00:00:00', '2018-10-31 00:00:00', null, null, null, '', '0', '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '.项目编号GD2Q,工装号: 21E803-731-B1', '12', '未入库', null, null);

-- ----------------------------
-- Table structure for `fy_business_order_delete`
-- ----------------------------
DROP TABLE IF EXISTS `fy_business_order_delete`;
CREATE TABLE `fy_business_order_delete` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) DEFAULT NULL COMMENT '关联基础资料类别',
  `planer_id` int(11) DEFAULT NULL COMMENT '关联人员',
  `execu_status` varchar(20) DEFAULT NULL COMMENT '执行状态',
  `customer_no` varchar(20) DEFAULT NULL COMMENT '紧急状态',
  `order_date` datetime DEFAULT NULL COMMENT '订单日期',
  `delivery_date` date DEFAULT NULL COMMENT '交货日期',
  `work_order_no` varchar(20) DEFAULT NULL COMMENT '工作订单号',
  `delivery_no` varchar(20) DEFAULT NULL COMMENT '送货单号',
  `commodity_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `commodity_spec` varchar(50) DEFAULT NULL COMMENT '商品规格',
  `map_no` varchar(50) DEFAULT NULL COMMENT '图号',
  `technology` varchar(500) DEFAULT NULL COMMENT '技术条件',
  `machining_require` varchar(500) DEFAULT NULL COMMENT '加工要求',
  `quantity` int(11) DEFAULT '0' COMMENT '数量',
  `unit` int(11) DEFAULT NULL COMMENT '单位，关联基础资料',
  `untaxed_cost` decimal(10,2) DEFAULT NULL COMMENT '未税金额',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `tax_rate` decimal(8,2) DEFAULT NULL COMMENT '税率，关联基础资料',
  `tax_amount` decimal(10,2) DEFAULT '0.00',
  `tatol_amount` decimal(10,2) DEFAULT '0.00' COMMENT '含税金额',
  `import_time` datetime DEFAULT NULL COMMENT '导入时间',
  `distribute_time` datetime DEFAULT NULL COMMENT '分配时间',
  `receive_time` datetime DEFAULT NULL COMMENT '接收时间',
  `warn_time` datetime DEFAULT NULL COMMENT '分配预警时间',
  `draw` int(11) DEFAULT NULL COMMENT '图纸',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `hang_time` datetime DEFAULT NULL COMMENT '挂账时间',
  `hang_status` varchar(255) DEFAULT NULL,
  `hang_quantity` int(11) DEFAULT '0' COMMENT '挂账数量',
  `unhang_quantity` int(11) DEFAULT '0' COMMENT '未挂账数量',
  `cate_tmp` varchar(20) DEFAULT NULL COMMENT '分类中文',
  `plan_tmp` varchar(20) DEFAULT NULL COMMENT '计划员',
  `map_tmp` varchar(50) DEFAULT NULL COMMENT '总图号tmp',
  `unit_tmp` varchar(10) DEFAULT NULL COMMENT '单位tmp',
  `tax_tmp` varchar(10) DEFAULT NULL,
  `distribute_to` varchar(8) DEFAULT NULL COMMENT '分配流向，委外或自产',
  `distribute_attr` varchar(10) DEFAULT NULL COMMENT '分配属性，首次、撤回',
  `is_distribute` bit(1) DEFAULT b'0' COMMENT '是否为分配表',
  `orderby` int(11) DEFAULT '0',
  `is_create_next` bit(1) DEFAULT b'0' COMMENT '是否已生成下一个单据，如生产汇总，生成生产计划单',
  `is_create_plan` bit(1) DEFAULT b'0' COMMENT '是否为生产计划表',
  `plan_time` datetime DEFAULT NULL COMMENT '预计投产时间',
  `plan_finsh_time` datetime DEFAULT NULL COMMENT '预计完工时间',
  `plan_remark` varchar(200) DEFAULT NULL COMMENT '生产备注',
  `in_warehouse_time` datetime DEFAULT NULL COMMENT '全部入库时间',
  `finish_time` datetime DEFAULT NULL COMMENT '生成计划单完工时间',
  `is_create_in_house` bit(1) DEFAULT b'0' COMMENT '是否已生成入库单',
  `dis_to` int(5) DEFAULT NULL COMMENT '分配为自产为0，委外为1',
  `handle_status` varchar(20) DEFAULT NULL COMMENT '处理状态，未处理，处理中，已处理',
  `is_finish_purchase` bit(1) DEFAULT b'0' COMMENT '是否已完成采购',
  `storage_quantity` int(11) DEFAULT '0' COMMENT '库存',
  `out_quantity` int(11) DEFAULT '0' COMMENT '已出库数量',
  `customer` int(11) DEFAULT NULL COMMENT '客户',
  `has_in_quantity` int(11) DEFAULT '0' COMMENT '已入库数量',
  `hang_account` decimal(12,2) DEFAULT '0.00' COMMENT '挂账金额',
  `ww_quantity` int(11) DEFAULT '0' COMMENT '委外挂账数量',
  `ww_unquantity` int(11) DEFAULT '0' COMMENT '委外未挂账数量',
  `ww_hang_amount` int(11) DEFAULT '0' COMMENT '委外挂账金额',
  `ww_unhang_amount` int(11) DEFAULT '0' COMMENT '未挂账金额',
  `send_address` varchar(100) DEFAULT NULL COMMENT '发货地址',
  `is_finsh` bit(1) DEFAULT b'0' COMMENT '是否已结束',
  `is_all_in_house` bit(1) DEFAULT b'0' COMMENT '是否全入库',
  `ready_id` int(11) DEFAULT NULL COMMENT '补单id',
  `model_no` varchar(50) DEFAULT NULL COMMENT '型号',
  `out_house_date` date DEFAULT NULL COMMENT '出库时间',
  `weiwai_cate` varchar(20) DEFAULT NULL COMMENT '委外分类',
  `total_map_no` varchar(50) DEFAULT NULL COMMENT '总图号',
  `order_status` int(11) DEFAULT '0' COMMENT '订单扩展状态位,默认0,1是委外接收表，2委外执行表,10为自产接收表，11为自产计划表,12生产一览表，20，为备货接收表',
  `inhouse_status` varchar(10) DEFAULT '未入库' COMMENT '入库状态，未入库，部分入库，已入库',
  `inhouse_date` date DEFAULT NULL COMMENT '入库时间',
  `is_finsh_product` varchar(255) DEFAULT NULL COMMENT '是否成品',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16293 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_business_order_delete
-- ----------------------------
INSERT INTO `fy_business_order_delete` VALUES ('16284', null, null, '备货', '正常', null, '2018-09-03', '1', '2', '收口底座Ⅱ', '', '21E706-13313-J27', '', '', '3', null, '315.00', '945.00', '0.16', '50.40', '365.40', '2018-10-27 15:42:16', null, null, null, null, null, null, '未挂账', '0', '3', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order_delete` VALUES ('16285', null, null, '备货', '正常', null, '2018-09-03', '1', '2', '收口压头', '', '21E701-2207-J27', '', '', '3', null, '150.00', '450.00', '0.16', '24.00', '174.00', '2018-10-27 15:42:16', null, null, null, null, null, null, '未挂账', '0', '3', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '3', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order_delete` VALUES ('16286', null, null, '备货', '正常', null, '2018-09-03', '1', '2', '装配夹具', '', '21E706-140-J27(007)', '', '', '2', null, '816.00', '1632.00', '0.16', '130.56', '946.56', '2018-10-27 15:42:16', null, null, null, null, null, null, '未挂账', '0', '2', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '2', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order_delete` VALUES ('16287', null, null, '备货', '正常', null, '2018-09-04', '1', '2', '导柱', '', '21E708-9071-A1(001)', '', '', '50', null, '75.00', '3750.00', '0.16', '12.00', '87.00', '2018-10-27 15:42:16', null, null, null, null, null, null, '未挂账', '0', '50', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '50', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order_delete` VALUES ('16288', null, null, '备货', '正常', null, '2018-09-04', '1', '2', '钛合金螺母', '', '21E705-5830-A1(001)', '', '', '16', null, '80.00', '1280.00', '0.16', '12.80', '92.80', '2018-10-27 15:42:16', null, null, null, null, null, null, '未挂账', '0', '16', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '16', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order_delete` VALUES ('16289', null, null, '备货', '正常', null, '2018-09-04', '1', '2', '机箱氧化挂具', '', '21E705-5829-A1(001)', '', '', '1', null, '536.00', '536.00', '0.16', '85.76', '621.76', '2018-10-27 15:42:16', null, null, null, null, null, null, '未挂账', '0', '1', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order_delete` VALUES ('16290', null, null, '备货', '正常', null, '2018-09-04', '1', '2', '氧化夹具', '', '21E705-4692-A1(001)', '', '', '8', null, '530.00', '4240.00', '0.16', '84.80', '614.80', '2018-10-27 15:42:16', null, null, null, null, null, null, '未挂账', '0', '8', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '8', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order_delete` VALUES ('16291', null, null, '备货', '正常', null, '2018-09-04', '1', '2', 'CRM220校正基座', '', '21E706-9958-J8', '', '', '1', null, '568.00', '568.00', '0.16', '90.88', '658.88', '2018-10-27 15:42:16', null, null, null, null, null, null, '未挂账', '0', '1', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '1', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);
INSERT INTO `fy_business_order_delete` VALUES ('16292', null, null, '备货', '正常', null, '2018-09-04', '1', '2', '电阻焊两排焊头', '', '21E354-2998-A1(001)', '', '', '50', null, '65.00', '3250.00', '0.16', '10.40', '75.40', '2018-10-27 15:42:16', null, null, null, null, null, null, '未挂账', '0', '50', '', '喻蓓', null, '件', null, null, '首次', '', '0', '', '', null, null, null, null, null, '', null, '未处理', '', '0', '0', null, '0', '0.00', '0', '50', '0', '0', '', '', '', null, '', null, null, '', '0', '未入库', null, null);

-- ----------------------------
-- Table structure for `fy_business_out_warehouse`
-- ----------------------------
DROP TABLE IF EXISTS `fy_business_out_warehouse`;
CREATE TABLE `fy_business_out_warehouse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `receive_address` varchar(50) DEFAULT NULL COMMENT '到货地址',
  `transport_type` varchar(50) DEFAULT NULL COMMENT '运输方式',
  `transport_company` varchar(50) DEFAULT NULL COMMENT '运输公司',
  `transport_fee` decimal(10,2) DEFAULT NULL COMMENT '运输费用',
  `transport_weight` double DEFAULT NULL COMMENT '运输重量',
  `transport_no` varchar(30) DEFAULT NULL COMMENT '运输单号',
  `out_time` datetime DEFAULT NULL COMMENT '出库时间',
  `out_quantity` int(14) DEFAULT NULL COMMENT '出库数量',
  `order_id` int(11) DEFAULT NULL COMMENT '订单号',
  `parent_id` int(11) DEFAULT NULL COMMENT '父单ID',
  `is_download` bit(1) DEFAULT b'0' COMMENT '是否已下载',
  `out_status` int(5) DEFAULT '0' COMMENT '出库状态位，0正常出库，1撤回',
  `out_remark` varchar(255) DEFAULT NULL COMMENT '出库备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_business_out_warehouse
-- ----------------------------
INSERT INTO `fy_business_out_warehouse` VALUES ('9', '洛阳（新区）-中航', '物流', '东莞市莞泰货物运输有限公司', '10.00', '1.5', '545254325', '2018-10-30 00:00:00', '4', '16684', '16684', '', '0', null);

-- ----------------------------
-- Table structure for `fy_business_pay`
-- ----------------------------
DROP TABLE IF EXISTS `fy_business_pay`;
CREATE TABLE `fy_business_pay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `check_result` varchar(8) DEFAULT NULL COMMENT '检测结果	合格，不合格',
  `supplier_id` int(11) DEFAULT NULL COMMENT '厂商',
  `order_no` varchar(20) DEFAULT NULL COMMENT '订单编号',
  `tatol_amount` decimal(12,2) DEFAULT NULL COMMENT '含税金额',
  `quality_deduction` decimal(11,0) DEFAULT NULL COMMENT '质量扣款',
  `in_warehouse_time` datetime DEFAULT NULL COMMENT '入库时间',
  `in_from` varchar(8) DEFAULT NULL COMMENT '入库来源，外协，采购',
  `pay_quantity` int(11) DEFAULT NULL COMMENT '应付数量',
  `parent_id` int(11) DEFAULT NULL COMMENT '上一个单据入库单，或外协加工单',
  `order_id` int(11) DEFAULT NULL COMMENT '订单',
  `create_time` datetime DEFAULT NULL COMMENT '新建时间',
  `is_download` bit(1) DEFAULT b'1' COMMENT '是否已下载',
  `is_purchase` bit(1) DEFAULT b'1' COMMENT '是否为委外的，反之为外协加工单',
  `should_pay` decimal(12,2) DEFAULT NULL COMMENT '应付金额',
  `hang_date` date DEFAULT NULL COMMENT '挂账期间',
  `pay_date` date DEFAULT NULL COMMENT '应付期间',
  `purchase_date` date DEFAULT NULL COMMENT '采购时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_by` int(11) DEFAULT NULL,
  `check_time` datetime DEFAULT NULL COMMENT '检查时间',
  `purchase_name` varchar(255) DEFAULT NULL COMMENT '采购名称',
  `purchase_quantity` int(11) DEFAULT NULL COMMENT '采购数量',
  `purchase_cost` decimal(10,2) DEFAULT NULL COMMENT '采购单价',
  `purchase_no` varchar(20) DEFAULT NULL COMMENT '采购编号',
  `purchase_amount` decimal(12,2) DEFAULT NULL COMMENT '采购总价',
  `is_setlled` bit(1) DEFAULT b'0' COMMENT '是否已结算',
  `unpass_quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_business_pay
-- ----------------------------
INSERT INTO `fy_business_pay` VALUES ('69', '合格', '41', null, null, null, '2018-10-30 10:37:43', '采购', '1', '59', '16684', null, '', '', '125.00', '2018-10-30', '2018-12-30', '2018-10-30', null, null, '2018-10-30 10:37:43', '承座', '5', '125.00', null, '625.00', '', '0');
INSERT INTO `fy_business_pay` VALUES ('70', '部分合格', '41', null, null, null, '2018-10-30 10:37:58', '采购', '3', '58', '16684', null, '', '', '375.00', '2018-10-30', '2018-12-30', '2018-10-30', null, null, '2018-10-30 10:37:58', '承座', '5', '125.00', null, '625.00', '', '1');
INSERT INTO `fy_business_pay` VALUES ('71', '合格', '98', 'H20181030-1', null, null, null, '外协', '1', '16', '16685', '2018-10-30 10:41:25', '', '', null, '2018-10-30', '2018-12-30', null, null, null, null, '表处理-发黑', '1', null, 'H20181030-1', null, '', null);

-- ----------------------------
-- Table structure for `fy_business_produce`
-- ----------------------------
DROP TABLE IF EXISTS `fy_business_produce`;
CREATE TABLE `fy_business_produce` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_create_plan` bit(1) DEFAULT b'0' COMMENT '是否为生产计划表',
  `handle_status` varchar(10) DEFAULT NULL COMMENT '处理状态	计划表反写',
  `plan_time` datetime DEFAULT NULL COMMENT '预计投产时间',
  `plan_finsh_time` datetime DEFAULT NULL COMMENT '预计完工时间',
  `plan_remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `in_warehouse_time` datetime DEFAULT NULL COMMENT '入库时间',
  `parent_id` int(11) DEFAULT NULL COMMENT '上一个单据',
  `order_id` int(11) DEFAULT NULL COMMENT '订单',
  `finish_time` datetime DEFAULT NULL COMMENT '完工时间',
  `is_create_in_house` bit(1) DEFAULT b'0' COMMENT '是否已生成入库单',
  `is_back_back` bit(1) DEFAULT b'0' COMMENT '是否撤回应付单',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_business_produce
-- ----------------------------

-- ----------------------------
-- Table structure for `fy_business_purchase`
-- ----------------------------
DROP TABLE IF EXISTS `fy_business_purchase`;
CREATE TABLE `fy_business_purchase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `purchase_no` varchar(20) DEFAULT NULL COMMENT '采购订单号',
  `purchase_date` date DEFAULT NULL COMMENT '采购日期',
  `purchase_single_weight` decimal(12,2) DEFAULT NULL COMMENT '采购重量',
  `purchase_weight` decimal(12,2) DEFAULT NULL COMMENT '采购重量',
  `purchase_cost` decimal(12,2) DEFAULT NULL COMMENT '采购单价',
  `purchase_account` decimal(12,2) DEFAULT NULL COMMENT '采购总价',
  `parent_id` int(11) DEFAULT NULL COMMENT '上一个单据',
  `order_id` int(11) DEFAULT NULL COMMENT '订单',
  `supplier_id` int(11) DEFAULT NULL COMMENT '供应商',
  `purchase_remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `is_download` bit(1) DEFAULT b'0' COMMENT '是否已下载',
  `hang_status` varchar(255) DEFAULT NULL COMMENT '挂账状态',
  `unhang_quantity` int(11) DEFAULT '0' COMMENT '未挂账数量',
  `hang_quantity` int(11) DEFAULT '0' COMMENT '挂账数量',
  `purchase_quantity` int(11) DEFAULT NULL COMMENT '采购数量',
  `purchase_title` varchar(30) DEFAULT NULL COMMENT '采购名称',
  `purchase_delivery_date` date DEFAULT NULL COMMENT '采购交货日期',
  `audit_status` varchar(50) DEFAULT NULL COMMENT '审核状态',
  `audit_by` int(11) DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `work_order_no` varchar(50) DEFAULT NULL COMMENT '工作订单号',
  `supplier_no` varchar(50) DEFAULT NULL,
  `add_status` int(5) DEFAULT '0' COMMENT '用于数据扩展，0为执行表数据，1为审核单数据 3\r\n为采购单',
  `is_has_tax` varchar(30) DEFAULT '' COMMENT '是否含税',
  PRIMARY KEY (`id`),
  UNIQUE KEY `work_order_no` (`work_order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_business_purchase
-- ----------------------------
INSERT INTO `fy_business_purchase` VALUES ('128', null, '2018-10-30', null, null, '125.00', '625.00', null, '16684', '41', null, '', null, '0', '0', '5', null, null, '审核通过', null, null, '20180910-126', 'CS-25', '3', '含税');
INSERT INTO `fy_business_purchase` VALUES ('129', null, '2018-10-30', null, null, '126.00', '126.00', null, '16683', '41', null, '', null, '0', '0', '1', null, null, '审核通过', null, null, '20180910-132', 'CS-25', '3', '含税');
INSERT INTO `fy_business_purchase` VALUES ('130', null, '2018-10-30', null, null, '127.00', '254.00', null, '16682', '39', null, '', null, '0', '0', '2', null, null, '审核通过', null, null, '20180910-141', 'CS-23', '3', '含税');
INSERT INTO `fy_business_purchase` VALUES ('131', null, '2018-10-30', null, null, '128.00', '128.00', null, '16681', '39', null, '', null, '0', '0', '1', null, null, '审核通过', null, null, '20180910-139', 'CS-23', '3', '含税');
INSERT INTO `fy_business_purchase` VALUES ('132', null, '2018-10-30', null, null, '129.00', '129.00', null, '16680', '36', null, '', null, '0', '0', '1', null, null, '审核通过', null, null, '20180910-138', 'CS-20', '3', '含税');
INSERT INTO `fy_business_purchase` VALUES ('133', null, '2018-10-30', null, null, '130.00', '130.00', null, '16679', '36', null, '', null, '0', '0', '1', null, null, '审核通过', null, null, '20180910-137', 'CS-20', '3', '含税');
INSERT INTO `fy_business_purchase` VALUES ('134', 'FYL20181030-6', '2018-10-30', null, null, '131.00', '131.00', null, '16678', '36', null, '', null, '0', '0', '1', null, null, '审核通过', null, null, '20180910-136', 'CS-20', '3', '含税');
INSERT INTO `fy_business_purchase` VALUES ('135', 'FYL20181030-4', '2018-10-30', null, null, '132.00', '264.00', null, '16677', '33', null, '', null, '0', '0', '2', null, null, '审核通过', null, null, '20180910-140', 'CS-17', '3', '含税');
INSERT INTO `fy_business_purchase` VALUES ('136', 'FYL20181030-3', '2018-10-30', null, null, '133.00', '133.00', null, '16676', '33', null, '', null, '0', '0', '1', null, null, '审核通过', null, null, '20180910-157', 'CS-17', '3', '含税');

-- ----------------------------
-- Table structure for `fy_business_purchase_callback`
-- ----------------------------
DROP TABLE IF EXISTS `fy_business_purchase_callback`;
CREATE TABLE `fy_business_purchase_callback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `purchase_no` varchar(20) DEFAULT NULL COMMENT '采购订单号',
  `purchase_date` date DEFAULT NULL COMMENT '采购日期',
  `purchase_single_weight` decimal(12,2) DEFAULT NULL COMMENT '采购重量',
  `purchase_weight` decimal(12,2) DEFAULT NULL COMMENT '采购重量',
  `purchase_cost` decimal(12,2) DEFAULT NULL COMMENT '采购单价',
  `purchase_account` decimal(12,2) DEFAULT NULL COMMENT '采购总价',
  `parent_id` int(11) DEFAULT NULL COMMENT '上一个单据',
  `order_id` int(11) DEFAULT NULL COMMENT '订单',
  `supplier_id` int(11) DEFAULT NULL COMMENT '供应商',
  `purchase_remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `is_download` bit(1) DEFAULT b'0' COMMENT '是否已下载',
  `hang_status` varchar(255) DEFAULT NULL COMMENT '挂账状态',
  `unhang_quantity` int(11) DEFAULT '0' COMMENT '未挂账数量',
  `hang_quantity` int(11) DEFAULT '0' COMMENT '挂账数量',
  `purchase_quantity` int(11) DEFAULT NULL COMMENT '采购数量',
  `purchase_title` varchar(30) DEFAULT NULL COMMENT '采购名称',
  `purchase_delivery_date` date DEFAULT NULL COMMENT '采购交货日期',
  `audit_status` varchar(50) DEFAULT NULL COMMENT '审核状态',
  `audit_by` int(11) DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `work_order_no` varchar(50) DEFAULT NULL COMMENT '工作订单号',
  `supplier_no` varchar(50) DEFAULT NULL,
  `add_status` int(5) DEFAULT '0' COMMENT '用于数据扩展，0为执行表数据，1为审核单数据 3\r\n为采购单',
  `is_has_tax` varchar(30) DEFAULT '' COMMENT '是否含税',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_business_purchase_callback
-- ----------------------------
INSERT INTO `fy_business_purchase_callback` VALUES ('2', 'FYL20180915-1', '2018-09-15', '1.10', '1.10', '2.34', '2.34', null, '13156', '98', null, '', null, '0', '0', null, null, '2018-10-20', '审核通过', null, null, '20180824-627', 'CS-82', '3', '');
INSERT INTO `fy_business_purchase_callback` VALUES ('7', 'FYL20180915-1', '2018-09-14', '3.00', '6.00', '3.00', '6.00', null, '13117', '98', null, '', null, '0', '0', null, null, '2018-10-25', '审核通过', null, null, '6A-623984-7-3', 'CS-82', '3', '');
INSERT INTO `fy_business_purchase_callback` VALUES ('12', null, '2018-09-15', '4.00', '4.00', '2.00', '2.00', null, '13150', '96', null, '', null, '0', '0', null, null, '2018-10-01', '审核通过', null, null, '20180827-500', 'CS-80', '3', '含税');
INSERT INTO `fy_business_purchase_callback` VALUES ('14', 'FYL20181005-1', '2018-10-05', '1.00', '6.00', '1.00', '6.00', null, '13207', '47', null, '', null, '0', '0', null, null, '2018-10-07', '审核通过', null, null, '20180824-651', 'CS-31', '3', '含税');
INSERT INTO `fy_business_purchase_callback` VALUES ('35', null, '2018-10-12', null, null, '105.00', '105.00', null, '13316', '46', null, '', null, '0', '0', '1', null, null, '审核通过', null, null, '20180528-954', 'CS-30', '3', '含税');

-- ----------------------------
-- Table structure for `fy_business_ready`
-- ----------------------------
DROP TABLE IF EXISTS `fy_business_ready`;
CREATE TABLE `fy_business_ready` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer` int(11) DEFAULT NULL COMMENT '客户',
  `category_id` int(11) DEFAULT NULL COMMENT '关联基础资料类别',
  `planer_id` int(11) DEFAULT NULL COMMENT '关联人员',
  `execu_status` varchar(20) DEFAULT NULL COMMENT '执行状态',
  `urgent_status` varchar(20) DEFAULT NULL COMMENT '紧急状态',
  `import_time` datetime DEFAULT NULL COMMENT '导入时间',
  `order_date` datetime DEFAULT NULL COMMENT '订单日期',
  `delivery_date` date DEFAULT NULL COMMENT '交货日期',
  `commodity_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `commodity_spec` varchar(50) DEFAULT NULL COMMENT '商品规格',
  `map_no` varchar(50) DEFAULT NULL COMMENT '总图号，关联图纸',
  `technology` varchar(500) DEFAULT NULL COMMENT '技术条件',
  `machining_require` varchar(500) DEFAULT NULL COMMENT '加工要求',
  `quantity` decimal(14,4) DEFAULT '0.0000' COMMENT '数量',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位，关联基础资料',
  `untaxed_cost` decimal(10,2) DEFAULT NULL COMMENT '未税金额',
  `add_status` varchar(20) DEFAULT NULL COMMENT '补单状态',
  `supplier_id` int(11) DEFAULT NULL COMMENT '厂商',
  `order_no` varchar(50) DEFAULT NULL COMMENT '订单编码',
  `purchase_quanity` decimal(14,4) DEFAULT '0.0000' COMMENT '厂商数量',
  `purchase_unit` varchar(20) DEFAULT NULL COMMENT '厂商单位',
  `cost` decimal(10,2) DEFAULT NULL COMMENT '单价',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `discount` decimal(12,2) DEFAULT NULL COMMENT '折扣',
  `discount_amount` decimal(12,2) DEFAULT NULL COMMENT '折后金额',
  `check_time` datetime DEFAULT NULL COMMENT '检测时间',
  `check_result` varchar(10) DEFAULT NULL COMMENT '检测结果',
  `is_product` bit(1) DEFAULT NULL COMMENT '是否为成品',
  `out_date1` date DEFAULT NULL COMMENT '出库时间1',
  `out_quantity1` decimal(14,4) DEFAULT NULL COMMENT '出库库数量1',
  `in_quantity1` decimal(14,4) DEFAULT NULL COMMENT '入库数量1',
  `in_date1` date DEFAULT NULL COMMENT '入库时间1',
  `out_date2` date DEFAULT NULL COMMENT '出库时间2',
  `out_quantity2` decimal(14,4) DEFAULT NULL COMMENT '出库库数量2',
  `in_quantity2` decimal(14,4) DEFAULT NULL COMMENT '入库数量2',
  `in_date2` date DEFAULT NULL COMMENT '入库时间2',
  `out_date3` date DEFAULT NULL COMMENT '出库时间3',
  `out_quantity3` decimal(14,4) DEFAULT NULL COMMENT '出库库数量3',
  `in_quantity3` decimal(14,4) DEFAULT NULL COMMENT '入库数量3',
  `in_date3` date DEFAULT NULL COMMENT '入库时间3',
  `out_date4` date DEFAULT NULL COMMENT '出库时间4',
  `out_quantity4` decimal(14,4) DEFAULT NULL COMMENT '出库库数量4',
  `in_quantity4` decimal(14,4) DEFAULT NULL COMMENT '入库数量4',
  `in_date4` date DEFAULT NULL COMMENT '入库时间4',
  `out_date5` date DEFAULT NULL COMMENT '出库时间5',
  `out_quantity5` decimal(14,4) DEFAULT NULL COMMENT '出库库数量5',
  `in_quantity5` decimal(14,4) DEFAULT NULL COMMENT '入库数量5',
  `in_date5` date DEFAULT NULL COMMENT '入库时间5',
  `is_create_purchase` bit(1) DEFAULT b'0' COMMENT '是否已生成采购单',
  `hang_quantity1` decimal(14,4) DEFAULT '0.0000' COMMENT '挂账数量1',
  `hang_account1` decimal(12,2) DEFAULT '0.00' COMMENT '挂账金额1',
  `hang_date1` date DEFAULT NULL COMMENT '挂账日期1',
  `hang_quantity2` decimal(14,4) DEFAULT '0.0000' COMMENT '挂账数量2',
  `hang_account2` decimal(12,2) DEFAULT '0.00' COMMENT '挂账金额2',
  `hang_date2` date DEFAULT NULL COMMENT '挂账日期2',
  `hang_quantity3` decimal(14,4) DEFAULT '0.0000' COMMENT '挂账数量3',
  `hang_account3` decimal(12,2) DEFAULT '0.00' COMMENT '挂账金额3',
  `hang_date3` date DEFAULT NULL COMMENT '挂账日期3',
  `hang_quantity4` decimal(14,4) DEFAULT '0.0000' COMMENT '挂账数量4',
  `hang_account4` decimal(12,2) DEFAULT '0.00' COMMENT '挂账金额4',
  `hang_date4` date DEFAULT NULL COMMENT '挂账日期4',
  `hang_quantity5` decimal(14,4) DEFAULT '0.0000' COMMENT '挂账数量5',
  `hang_account5` decimal(12,2) DEFAULT '0.00' COMMENT '挂账金额5',
  `hang_date5` date DEFAULT NULL COMMENT '挂账日期5',
  `work_order_no1` varchar(20) DEFAULT NULL COMMENT '工作订单号1',
  `delivery_no1` varchar(20) DEFAULT NULL COMMENT '送货单号1',
  `work_order_no2` varchar(20) DEFAULT NULL COMMENT '工作订单号2',
  `delivery_no2` varchar(20) DEFAULT NULL COMMENT '送货单号2',
  `work_order_no3` varchar(20) DEFAULT NULL COMMENT '工作订单号3',
  `delivery_no3` varchar(20) DEFAULT NULL COMMENT '送货单号3',
  `add_quantity1` decimal(14,4) DEFAULT '0.0000' COMMENT '补单1',
  `add_quantity2` decimal(14,4) DEFAULT '0.0000' COMMENT '补单2',
  `add_quantity3` decimal(14,4) DEFAULT '0.0000' COMMENT '补单3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_business_ready
-- ----------------------------

-- ----------------------------
-- Table structure for `fy_business_sum_paybill`
-- ----------------------------
DROP TABLE IF EXISTS `fy_business_sum_paybill`;
CREATE TABLE `fy_business_sum_paybill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `supplier` int(11) DEFAULT NULL COMMENT '厂商名称',
  `bill_no` varchar(20) DEFAULT NULL COMMENT '结算编号',
  `hang_period` varchar(20) DEFAULT NULL COMMENT '挂账期间',
  `bill_period` varchar(20) DEFAULT NULL COMMENT '结算期间',
  `bill_account` decimal(12,2) DEFAULT NULL COMMENT '结算金额',
  `had_bill_account` decimal(12,2) DEFAULT NULL COMMENT '已结算金额',
  `unbill_account` decimal(12,2) DEFAULT NULL COMMENT '未结算金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_business_sum_paybill
-- ----------------------------

-- ----------------------------
-- Table structure for `fy_business_warehouse`
-- ----------------------------
DROP TABLE IF EXISTS `fy_business_warehouse`;
CREATE TABLE `fy_business_warehouse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `in_time` datetime DEFAULT NULL COMMENT '入库时间',
  `real_in_quantity` decimal(12,2) DEFAULT NULL COMMENT '入库数量',
  `in_from` varchar(10) DEFAULT NULL COMMENT '入库来源',
  `is_create_check` bit(1) DEFAULT NULL COMMENT '生成检测汇总表',
  `check_create_time` datetime DEFAULT NULL COMMENT '生成时间',
  `check_time` datetime DEFAULT NULL COMMENT '检测时间',
  `check_quantity` decimal(12,2) DEFAULT NULL COMMENT '检测数量',
  `check_result` varchar(8) DEFAULT NULL COMMENT '检测结果：合格，不合格',
  `check_handle` varchar(10) DEFAULT NULL COMMENT '检测处理：1、报废 2.返工 3.让步接收 4.正常',
  `is_create_quality` bit(1) DEFAULT NULL COMMENT '是否生成质量处理单据',
  `is_create_can_out` bit(1) DEFAULT NULL COMMENT '是否生成待出库',
  `create_out_time` datetime DEFAULT NULL COMMENT '书库单生成时间',
  `is_create_pay` bit(1) DEFAULT NULL COMMENT '是否生成应付明细表',
  `pay_create_time` datetime DEFAULT NULL COMMENT '应付单生成时间',
  `category_tmp` varchar(10) DEFAULT NULL COMMENT '分类中文',
  `plan_tmp` varchar(10) DEFAULT NULL COMMENT '计划员',
  `unit_tmp` varchar(10) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL COMMENT '订单号',
  `parent_id` int(11) DEFAULT NULL COMMENT '父单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_business_warehouse
-- ----------------------------

-- ----------------------------
-- Table structure for `fy_complaint`
-- ----------------------------
DROP TABLE IF EXISTS `fy_complaint`;
CREATE TABLE `fy_complaint` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `customer_name` varchar(50) DEFAULT NULL COMMENT '客户',
  `complaint_date` date DEFAULT NULL COMMENT '投诉日期',
  `map_no` varchar(50) DEFAULT NULL COMMENT '图号',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  `content` varchar(300) DEFAULT NULL COMMENT '投诉内容',
  `handle_method` varchar(200) DEFAULT NULL COMMENT '纠正对策',
  `solution` varchar(500) DEFAULT NULL COMMENT '处理方案',
  `duty_part` varchar(50) DEFAULT NULL COMMENT '责任部门',
  `duty_person` varchar(11) DEFAULT NULL COMMENT '责任人',
  `record_part` varchar(50) DEFAULT NULL COMMENT '记录部门',
  `record_person` varchar(11) DEFAULT NULL COMMENT '记录人',
  `handle_no` varchar(20) DEFAULT NULL COMMENT '处理编号',
  `order_id` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_complaint
-- ----------------------------
INSERT INTO `fy_complaint` VALUES ('6', null, '中航光电', null, null, null, null, '  ', null, ' ', null, null, null, null, null, '16684', '9');

-- ----------------------------
-- Table structure for `fy_exception_record`
-- ----------------------------
DROP TABLE IF EXISTS `fy_exception_record`;
CREATE TABLE `fy_exception_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cate_tmp` varchar(20) DEFAULT NULL COMMENT '分类中文',
  `plan_tmp` varchar(20) DEFAULT NULL COMMENT '计划员',
  `work_order_no` varchar(20) NOT NULL COMMENT '工作订单号',
  `map_tmp` varchar(50) DEFAULT NULL COMMENT '图号 ',
  `commodity_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `total_map_no` varchar(50) DEFAULT NULL COMMENT '总图号',
  `quantity` int(11) DEFAULT '0' COMMENT '数量',
  `unit_tmp` varchar(10) DEFAULT NULL COMMENT '单位tmp',
  `model_no` varchar(50) DEFAULT NULL COMMENT '型号',
  `commodity_spec` varchar(50) DEFAULT NULL COMMENT '商品规格',
  `technology` varchar(500) DEFAULT NULL COMMENT '技术条件',
  `machining_require` varchar(500) DEFAULT NULL COMMENT '加工要求',
  `order_date` datetime DEFAULT NULL COMMENT '订单日期',
  `distribute_to` varchar(8) DEFAULT NULL COMMENT '分配流向，委外或自产',
  `supplier_id` int(11) DEFAULT NULL COMMENT '制造商',
  `check_time` datetime DEFAULT NULL COMMENT '检查时间',
  `exception_reson` varchar(255) DEFAULT NULL COMMENT '异常原因',
  `check_remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `work_order_no` (`work_order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_exception_record
-- ----------------------------

-- ----------------------------
-- Table structure for `fy_pay_sum`
-- ----------------------------
DROP TABLE IF EXISTS `fy_pay_sum`;
CREATE TABLE `fy_pay_sum` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hang_amount` decimal(12,2) DEFAULT '0.00',
  `hang_quantity` decimal(14,4) DEFAULT '0.0000',
  `unhang_quantity` decimal(14,4) DEFAULT '0.0000',
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_pay_sum
-- ----------------------------

-- ----------------------------
-- Table structure for `fy_ready_add`
-- ----------------------------
DROP TABLE IF EXISTS `fy_ready_add`;
CREATE TABLE `fy_ready_add` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ready_order_id` int(11) DEFAULT NULL COMMENT '备货订单ID',
  `add_order_id` int(11) DEFAULT NULL COMMENT '备货订单id',
  `create_time` datetime DEFAULT NULL,
  `add_quantity` int(11) DEFAULT '0' COMMENT '补货数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='备货与补货关联表';

-- ----------------------------
-- Records of fy_ready_add
-- ----------------------------

-- ----------------------------
-- Table structure for `fy_ready_purchase`
-- ----------------------------
DROP TABLE IF EXISTS `fy_ready_purchase`;
CREATE TABLE `fy_ready_purchase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `purchase_no` varchar(20) DEFAULT NULL COMMENT '采购订单号',
  `purchase_date` date DEFAULT NULL COMMENT '采购日期',
  `purchase_single_weight` decimal(12,2) DEFAULT NULL COMMENT '采购重量',
  `purchase_weight` decimal(12,2) DEFAULT NULL COMMENT '采购重量',
  `purchase_cost` decimal(12,2) DEFAULT NULL COMMENT '采购单价',
  `purchase_account` decimal(12,2) DEFAULT NULL COMMENT '采购总价',
  `ready_order_id` int(11) DEFAULT NULL COMMENT '订单',
  `supplier_id` int(11) DEFAULT NULL COMMENT '供应商',
  `purchase_remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `can_download` bit(1) DEFAULT b'1' COMMENT '默认可以下载，下载之后沉底，不可下载',
  `purchase_quantity` decimal(14,4) DEFAULT NULL COMMENT '采购数量',
  `purchase_title` varchar(50) DEFAULT NULL COMMENT '采购名称',
  `work_order_no` varchar(50) DEFAULT NULL COMMENT '工作订单号',
  `order_id` int(11) DEFAULT NULL,
  `audit_status` varchar(50) DEFAULT NULL COMMENT '审核状态',
  `audit_by` int(11) DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_ready_purchase
-- ----------------------------

-- ----------------------------
-- Table structure for `fy_supplier_category`
-- ----------------------------
DROP TABLE IF EXISTS `fy_supplier_category`;
CREATE TABLE `fy_supplier_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `parent_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_supplier_category
-- ----------------------------
INSERT INTO `fy_supplier_category` VALUES ('2', '材料厂商', '0');
INSERT INTO `fy_supplier_category` VALUES ('3', '运输厂商', '0');
INSERT INTO `fy_supplier_category` VALUES ('4', '机物料厂商', '0');
INSERT INTO `fy_supplier_category` VALUES ('10', '整体外发厂商', '0');
INSERT INTO `fy_supplier_category` VALUES ('11', '工序外协厂商', '0');

-- ----------------------------
-- Table structure for `fy_upload_getpay`
-- ----------------------------
DROP TABLE IF EXISTS `fy_upload_getpay`;
CREATE TABLE `fy_upload_getpay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hang_date` date DEFAULT NULL COMMENT '挂账日期',
  `materials` varchar(50) DEFAULT NULL COMMENT '物料',
  `commodity_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `Brand_no` varchar(50) DEFAULT NULL COMMENT '牌号',
  `spec` varchar(50) DEFAULT NULL COMMENT '商品规格',
  `project_no` varchar(50) DEFAULT NULL COMMENT '项目编号',
  `unit` varchar(30) DEFAULT NULL COMMENT '单位',
  `quantity` decimal(14,4) DEFAULT '0.0000' COMMENT '数量',
  `cost` decimal(14,2) DEFAULT '0.00' COMMENT '单价',
  `hang_quantity` decimal(14,4) DEFAULT '0.0000' COMMENT '挂账数量',
  `invoice_stat` varchar(30) DEFAULT NULL COMMENT '发票状态',
  `hang_amount` decimal(14,2) DEFAULT '0.00' COMMENT '挂账金额',
  `perchase_person` varchar(30) DEFAULT NULL COMMENT '采购人',
  `delivery_no` varchar(50) DEFAULT NULL COMMENT '送货单号',
  `delivery_index` varchar(50) DEFAULT NULL COMMENT '送货序列号',
  `invoice_no` varchar(50) DEFAULT NULL COMMENT '发票号',
  `create_by` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `contract` varchar(50) DEFAULT NULL COMMENT '合同',
  `is_setlled` bit(1) DEFAULT b'0' COMMENT '是否已结算',
  `order_id` int(11) DEFAULT NULL COMMENT '订单id',
  `remark` varchar(255) DEFAULT NULL COMMENT '应收单备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fy_upload_getpay
-- ----------------------------
INSERT INTO `fy_upload_getpay` VALUES ('77', '2018-10-27', '', '气缸固定块', '', '21E705-5150-J8', '', '件', '8.0000', '8.00', '8.0000', '已挂账', '8.00', '朱润红', 'SH1809067466314', '', '0152882', null, null, '', '', null, null);
INSERT INTO `fy_upload_getpay` VALUES ('78', '2018-10-27', '', '喷头固定块', '', '21E705-5152-J8', '', '件', '16.0000', '16.00', '16.0000', '部分挂账', '16.00', '朱润红', 'SH1809067466315', '', '0152882', null, null, '', '', null, null);
INSERT INTO `fy_upload_getpay` VALUES ('79', '2018-10-27', '', '导柱固定块', '', '21E705-5149-J8', '', '件', '8.0000', '8.00', '8.0000', '已挂账', '8.00', '朱润红', 'SH1809067466317', '', '0152882', null, null, '', '', null, null);
INSERT INTO `fy_upload_getpay` VALUES ('80', '2018-10-27', '', '水管固定块', '', '21E705-5157-E31', '', '件', '8.0000', '8.00', '8.0000', '已挂账', '8.00', '朱润红', 'SH1809067466318', '', '0152882', null, null, '', '', null, null);
INSERT INTO `fy_upload_getpay` VALUES ('81', '2018-10-27', '', '底板', '', '21E707-11385-E31', '', '件', '4.0000', '4.00', '4.0000', '已挂账', '4.00', '朱润红', 'SH1809067466319', '', '0152882', null, null, '', '', null, null);
INSERT INTO `fy_upload_getpay` VALUES ('82', '2018-10-27', '', '内构架', '', '21E705-5140-A1', '', '件', '4.0000', '4.00', '4.0000', '已挂账', '4.00', '朱润红', 'SH1809067466320', '', '0152882', null, null, '', '', null, null);
INSERT INTO `fy_upload_getpay` VALUES ('83', '2018-10-27', '', '担板', '', '21E705-5161-E31', '', '件', '8.0000', '8.00', '8.0000', '已挂账', '8.00', '朱润红', 'SH1809067466322', '', '0152882', null, null, '', '', null, null);
INSERT INTO `fy_upload_getpay` VALUES ('84', '2018-10-27', '', '上气嘴固定块', '', '21E705-5153-E31', '', '件', '16.0000', '16.00', '16.0000', '已挂账', '16.00', '朱润红', 'SH1809067466321', '', '0152882', null, null, '', '', null, null);
INSERT INTO `fy_upload_getpay` VALUES ('85', '2018-10-27', '', '电气板', '', '21E705-5137-A1', '', '件', '4.0000', '4.00', '4.0000', '已挂账', '4.00', '朱润红', 'SH1809067466306', '', '0152882', null, null, '', '', null, null);
INSERT INTO `fy_upload_getpay` VALUES ('86', '2018-10-27', '', '下气嘴固定块', '', '21E705-5159-E31', '', '件', '8.0000', '8.00', '8.0000', '已挂账', '8.00', '朱润红', 'SH1809067466325', '', '0152882', null, null, '', '', null, null);
INSERT INTO `fy_upload_getpay` VALUES ('87', '2018-10-27', '', '活动板', '', '21E705-5151-J8', '', '件', '4.0000', '4.00', '4.0000', '已挂账', '4.00', '朱润红', 'SH1809067466323', '', '0152882', null, null, '', '', null, null);
INSERT INTO `fy_upload_getpay` VALUES ('88', '2018-10-27', '', '电箱盖板', '', '21E705-5138-A1', '', '件', '4.0000', '4.00', '4.0000', '已挂账', '4.00', '朱润红', 'SH1809067466324', '', '0152882', null, null, '', '', null, null);
INSERT INTO `fy_upload_getpay` VALUES ('89', '2018-10-27', '', '分气阀', '', '21E705-5148-E31', '', '件', '4.0000', '4.00', '4.0000', '已挂账', '4.00', '朱润红', 'SH1809067466326', '', '0152882', null, null, '', '', null, null);
INSERT INTO `fy_upload_getpay` VALUES ('90', '2018-10-27', '', '气嘴', '', '21E705-5160-A1', '', '件', '32.0000', '32.00', '32.0000', '已挂账', '32.00', '朱润红', 'SH1809067466328', '', '0152882', null, null, '', '', null, null);
INSERT INTO `fy_upload_getpay` VALUES ('91', '2018-10-27', '', '转接板', '', '21E710-21739-J8', '', '件', '3.0000', '3.00', '3.0000', '已挂账', '3.00', '朱润红', 'SH1809067466202', '', '0152882', null, null, '', '', null, null);

-- ----------------------------
-- Table structure for `login_log`
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log` (
  `accountId` int(11) NOT NULL,
  `loginAt` datetime NOT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `accountId_index` (`accountId`)
) ENGINE=InnoDB AUTO_INCREMENT=889 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login_log
-- ----------------------------
INSERT INTO `login_log` VALUES ('1', '2018-04-28 00:23:58', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `login_log` VALUES ('1', '2018-04-28 00:25:27', '0:0:0:0:0:0:0:1', '2');
INSERT INTO `login_log` VALUES ('1', '2018-04-28 00:27:00', '0:0:0:0:0:0:0:1', '3');
INSERT INTO `login_log` VALUES ('1', '2018-04-28 00:27:50', '0:0:0:0:0:0:0:1', '4');
INSERT INTO `login_log` VALUES ('1', '2018-04-28 00:29:04', '0:0:0:0:0:0:0:1', '5');
INSERT INTO `login_log` VALUES ('1', '2018-04-28 00:29:43', '0:0:0:0:0:0:0:1', '6');
INSERT INTO `login_log` VALUES ('1', '2018-04-28 00:31:59', '0:0:0:0:0:0:0:1', '7');
INSERT INTO `login_log` VALUES ('9', '2018-05-05 21:20:56', '0:0:0:0:0:0:0:1', '8');
INSERT INTO `login_log` VALUES ('9', '2018-05-06 00:11:49', '0:0:0:0:0:0:0:1', '9');
INSERT INTO `login_log` VALUES ('10', '2018-05-06 10:47:19', '0:0:0:0:0:0:0:1', '10');
INSERT INTO `login_log` VALUES ('1', '2018-05-06 11:13:11', '0:0:0:0:0:0:0:1', '11');
INSERT INTO `login_log` VALUES ('1', '2018-05-11 14:34:02', '0:0:0:0:0:0:0:1', '12');
INSERT INTO `login_log` VALUES ('1', '2018-05-29 22:14:14', '0:0:0:0:0:0:0:1', '13');
INSERT INTO `login_log` VALUES ('3', '2018-06-06 09:23:16', '0:0:0:0:0:0:0:1', '14');
INSERT INTO `login_log` VALUES ('1', '2018-06-06 09:24:11', '0:0:0:0:0:0:0:1', '15');
INSERT INTO `login_log` VALUES ('1', '2018-06-07 22:45:28', '0:0:0:0:0:0:0:1', '16');
INSERT INTO `login_log` VALUES ('3', '2018-06-07 22:59:23', '0:0:0:0:0:0:0:1', '17');
INSERT INTO `login_log` VALUES ('3', '2018-06-21 15:43:38', '0:0:0:0:0:0:0:1', '18');
INSERT INTO `login_log` VALUES ('1', '2018-06-21 15:44:03', '0:0:0:0:0:0:0:1', '19');
INSERT INTO `login_log` VALUES ('1', '2018-06-27 09:40:25', '0:0:0:0:0:0:0:1', '20');
INSERT INTO `login_log` VALUES ('1', '2018-06-27 17:38:49', '0:0:0:0:0:0:0:1', '21');
INSERT INTO `login_log` VALUES ('1', '2018-06-27 18:51:04', '0:0:0:0:0:0:0:1', '22');
INSERT INTO `login_log` VALUES ('1', '2018-06-27 18:57:51', '0:0:0:0:0:0:0:1', '23');
INSERT INTO `login_log` VALUES ('1', '2018-06-27 20:24:19', '0:0:0:0:0:0:0:1', '24');
INSERT INTO `login_log` VALUES ('1', '2018-07-01 21:54:46', '0:0:0:0:0:0:0:1', '25');
INSERT INTO `login_log` VALUES ('1', '2018-07-01 21:56:49', '0:0:0:0:0:0:0:1', '26');
INSERT INTO `login_log` VALUES ('1', '2018-07-02 09:42:19', '0:0:0:0:0:0:0:1', '27');
INSERT INTO `login_log` VALUES ('1', '2018-07-02 09:44:07', '0:0:0:0:0:0:0:1', '28');
INSERT INTO `login_log` VALUES ('1', '2018-07-02 09:57:39', '0:0:0:0:0:0:0:1', '29');
INSERT INTO `login_log` VALUES ('1', '2018-07-02 09:59:26', '0:0:0:0:0:0:0:1', '30');
INSERT INTO `login_log` VALUES ('1', '2018-07-02 10:14:08', '0:0:0:0:0:0:0:1', '31');
INSERT INTO `login_log` VALUES ('1', '2018-07-02 10:23:43', '0:0:0:0:0:0:0:1', '32');
INSERT INTO `login_log` VALUES ('1', '2018-07-02 12:45:07', '0:0:0:0:0:0:0:1', '33');
INSERT INTO `login_log` VALUES ('1', '2018-07-02 12:51:43', '0:0:0:0:0:0:0:1', '34');
INSERT INTO `login_log` VALUES ('1', '2018-07-02 12:52:18', '0:0:0:0:0:0:0:1', '35');
INSERT INTO `login_log` VALUES ('1', '2018-07-02 12:52:36', '0:0:0:0:0:0:0:1', '36');
INSERT INTO `login_log` VALUES ('1', '2018-07-02 12:54:41', '0:0:0:0:0:0:0:1', '37');
INSERT INTO `login_log` VALUES ('1', '2018-07-02 14:31:06', '0:0:0:0:0:0:0:1', '38');
INSERT INTO `login_log` VALUES ('1', '2018-07-02 14:33:28', '0:0:0:0:0:0:0:1', '39');
INSERT INTO `login_log` VALUES ('1', '2018-07-02 14:41:49', '0:0:0:0:0:0:0:1', '40');
INSERT INTO `login_log` VALUES ('1', '2018-07-06 10:58:53', '0:0:0:0:0:0:0:1', '41');
INSERT INTO `login_log` VALUES ('1', '2018-07-06 12:43:54', '0:0:0:0:0:0:0:1', '42');
INSERT INTO `login_log` VALUES ('1', '2018-07-06 14:20:53', '0:0:0:0:0:0:0:1', '43');
INSERT INTO `login_log` VALUES ('1', '2018-07-06 15:43:23', '0:0:0:0:0:0:0:1', '44');
INSERT INTO `login_log` VALUES ('1', '2018-07-06 16:03:25', '0:0:0:0:0:0:0:1', '45');
INSERT INTO `login_log` VALUES ('1', '2018-07-06 16:05:15', '0:0:0:0:0:0:0:1', '46');
INSERT INTO `login_log` VALUES ('1', '2018-07-06 16:06:44', '0:0:0:0:0:0:0:1', '47');
INSERT INTO `login_log` VALUES ('1', '2018-07-06 16:32:41', '0:0:0:0:0:0:0:1', '48');
INSERT INTO `login_log` VALUES ('1', '2018-07-06 20:14:21', '0:0:0:0:0:0:0:1', '49');
INSERT INTO `login_log` VALUES ('1', '2018-07-06 21:01:20', '0:0:0:0:0:0:0:1', '50');
INSERT INTO `login_log` VALUES ('1', '2018-07-06 21:04:05', '0:0:0:0:0:0:0:1', '51');
INSERT INTO `login_log` VALUES ('1', '2018-07-06 22:14:11', '0:0:0:0:0:0:0:1', '52');
INSERT INTO `login_log` VALUES ('1', '2018-07-06 22:44:42', '0:0:0:0:0:0:0:1', '53');
INSERT INTO `login_log` VALUES ('1', '2018-07-07 14:01:20', '0:0:0:0:0:0:0:1', '54');
INSERT INTO `login_log` VALUES ('1', '2018-07-07 17:09:29', '0:0:0:0:0:0:0:1', '55');
INSERT INTO `login_log` VALUES ('1', '2018-07-07 17:12:13', '0:0:0:0:0:0:0:1', '56');
INSERT INTO `login_log` VALUES ('1', '2018-07-07 20:21:08', '0:0:0:0:0:0:0:1', '57');
INSERT INTO `login_log` VALUES ('1', '2018-07-07 20:26:05', '0:0:0:0:0:0:0:1', '58');
INSERT INTO `login_log` VALUES ('1', '2018-07-07 21:31:46', '0:0:0:0:0:0:0:1', '59');
INSERT INTO `login_log` VALUES ('1', '2018-07-07 23:42:04', '0:0:0:0:0:0:0:1', '60');
INSERT INTO `login_log` VALUES ('1', '2018-07-08 08:58:21', '0:0:0:0:0:0:0:1', '61');
INSERT INTO `login_log` VALUES ('1', '2018-07-19 15:28:25', '0:0:0:0:0:0:0:1', '62');
INSERT INTO `login_log` VALUES ('1', '2018-07-19 16:34:24', '0:0:0:0:0:0:0:1', '63');
INSERT INTO `login_log` VALUES ('1', '2018-07-19 16:35:43', '0:0:0:0:0:0:0:1', '64');
INSERT INTO `login_log` VALUES ('1', '2018-07-19 16:48:10', '0:0:0:0:0:0:0:1', '65');
INSERT INTO `login_log` VALUES ('1', '2018-07-19 16:53:53', '0:0:0:0:0:0:0:1', '66');
INSERT INTO `login_log` VALUES ('1', '2018-07-19 21:00:41', '0:0:0:0:0:0:0:1', '67');
INSERT INTO `login_log` VALUES ('1', '2018-07-20 01:31:39', '0:0:0:0:0:0:0:1', '68');
INSERT INTO `login_log` VALUES ('1', '2018-07-20 02:04:04', '0:0:0:0:0:0:0:1', '69');
INSERT INTO `login_log` VALUES ('1', '2018-07-20 02:04:38', '0:0:0:0:0:0:0:1', '70');
INSERT INTO `login_log` VALUES ('1', '2018-07-20 02:37:08', '0:0:0:0:0:0:0:1', '71');
INSERT INTO `login_log` VALUES ('1', '2018-07-20 10:26:23', '0:0:0:0:0:0:0:1', '72');
INSERT INTO `login_log` VALUES ('1', '2018-07-20 10:36:42', '0:0:0:0:0:0:0:1', '73');
INSERT INTO `login_log` VALUES ('1', '2018-07-20 11:56:14', '0:0:0:0:0:0:0:1', '74');
INSERT INTO `login_log` VALUES ('1', '2018-07-21 01:49:30', '0:0:0:0:0:0:0:1', '75');
INSERT INTO `login_log` VALUES ('1', '2018-07-21 01:51:27', '0:0:0:0:0:0:0:1', '76');
INSERT INTO `login_log` VALUES ('1', '2018-07-21 03:05:11', '0:0:0:0:0:0:0:1', '77');
INSERT INTO `login_log` VALUES ('1', '2018-07-21 03:23:29', '0:0:0:0:0:0:0:1', '78');
INSERT INTO `login_log` VALUES ('1', '2018-07-21 04:49:04', '0:0:0:0:0:0:0:1', '79');
INSERT INTO `login_log` VALUES ('1', '2018-07-21 04:54:35', '0:0:0:0:0:0:0:1', '80');
INSERT INTO `login_log` VALUES ('1', '2018-07-21 07:27:20', '0:0:0:0:0:0:0:1', '81');
INSERT INTO `login_log` VALUES ('1', '2018-07-21 07:30:53', '0:0:0:0:0:0:0:1', '82');
INSERT INTO `login_log` VALUES ('1', '2018-07-21 07:33:28', '0:0:0:0:0:0:0:1', '83');
INSERT INTO `login_log` VALUES ('1', '2018-07-21 16:26:10', '0:0:0:0:0:0:0:1', '84');
INSERT INTO `login_log` VALUES ('1', '2018-07-21 16:26:57', '0:0:0:0:0:0:0:1', '85');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 00:32:01', '0:0:0:0:0:0:0:1', '86');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 02:32:59', '0:0:0:0:0:0:0:1', '87');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 10:05:34', '0:0:0:0:0:0:0:1', '88');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 10:56:15', '0:0:0:0:0:0:0:1', '89');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 11:06:05', '0:0:0:0:0:0:0:1', '90');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 11:47:42', '0:0:0:0:0:0:0:1', '91');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 12:34:20', '0:0:0:0:0:0:0:1', '92');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 13:04:27', '0:0:0:0:0:0:0:1', '93');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 13:48:45', '0:0:0:0:0:0:0:1', '94');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 13:49:24', '0:0:0:0:0:0:0:1', '95');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 14:10:21', '0:0:0:0:0:0:0:1', '96');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 14:35:15', '0:0:0:0:0:0:0:1', '97');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 15:14:12', '0:0:0:0:0:0:0:1', '98');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 15:31:42', '0:0:0:0:0:0:0:1', '99');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 15:40:40', '0:0:0:0:0:0:0:1', '100');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 15:48:51', '0:0:0:0:0:0:0:1', '101');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 16:48:51', '0:0:0:0:0:0:0:1', '102');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 17:22:34', '127.0.0.1', '103');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 17:43:38', '0:0:0:0:0:0:0:1', '104');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 18:16:37', '0:0:0:0:0:0:0:1', '105');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 18:24:11', '0:0:0:0:0:0:0:1', '106');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 18:31:20', '0:0:0:0:0:0:0:1', '107');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 18:32:07', '127.0.0.1', '108');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 19:46:48', '0:0:0:0:0:0:0:1', '109');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 19:52:15', '0:0:0:0:0:0:0:1', '110');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 20:25:44', '0:0:0:0:0:0:0:1', '111');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 20:48:51', '0:0:0:0:0:0:0:1', '112');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 22:07:57', '0:0:0:0:0:0:0:1', '113');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 22:10:20', '0:0:0:0:0:0:0:1', '114');
INSERT INTO `login_log` VALUES ('1', '2018-07-22 23:46:54', '0:0:0:0:0:0:0:1', '115');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 00:20:10', '0:0:0:0:0:0:0:1', '116');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 00:24:54', '0:0:0:0:0:0:0:1', '117');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 01:04:10', '0:0:0:0:0:0:0:1', '118');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 10:25:54', '0:0:0:0:0:0:0:1', '119');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 11:09:41', '0:0:0:0:0:0:0:1', '120');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 11:10:23', '127.0.0.1', '121');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 11:11:52', '0:0:0:0:0:0:0:1', '122');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 11:18:57', '0:0:0:0:0:0:0:1', '123');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 11:25:01', '0:0:0:0:0:0:0:1', '124');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 11:26:20', '0:0:0:0:0:0:0:1', '125');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 11:26:37', '0:0:0:0:0:0:0:1', '126');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 11:28:08', '0:0:0:0:0:0:0:1', '127');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 12:44:00', '0:0:0:0:0:0:0:1', '128');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 13:02:04', '0:0:0:0:0:0:0:1', '129');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 15:21:52', '0:0:0:0:0:0:0:1', '130');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 15:23:59', '0:0:0:0:0:0:0:1', '131');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 16:10:28', '0:0:0:0:0:0:0:1', '132');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 16:46:35', '0:0:0:0:0:0:0:1', '133');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 17:20:29', '0:0:0:0:0:0:0:1', '134');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 17:51:06', '0:0:0:0:0:0:0:1', '135');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 18:06:45', '0:0:0:0:0:0:0:1', '136');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 19:53:58', '0:0:0:0:0:0:0:1', '137');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 20:12:18', '0:0:0:0:0:0:0:1', '138');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 20:20:45', '0:0:0:0:0:0:0:1', '139');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 21:28:55', '0:0:0:0:0:0:0:1', '140');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 22:02:05', '0:0:0:0:0:0:0:1', '141');
INSERT INTO `login_log` VALUES ('1', '2018-07-23 22:20:34', '0:0:0:0:0:0:0:1', '142');
INSERT INTO `login_log` VALUES ('1', '2018-07-24 09:38:38', '0:0:0:0:0:0:0:1', '143');
INSERT INTO `login_log` VALUES ('1', '2018-07-24 11:38:44', '0:0:0:0:0:0:0:1', '144');
INSERT INTO `login_log` VALUES ('1', '2018-07-24 12:03:14', '127.0.0.1', '145');
INSERT INTO `login_log` VALUES ('1', '2018-07-24 12:11:28', '0:0:0:0:0:0:0:1', '146');
INSERT INTO `login_log` VALUES ('1', '2018-07-24 12:25:54', '0:0:0:0:0:0:0:1', '147');
INSERT INTO `login_log` VALUES ('1', '2018-07-24 13:16:28', '0:0:0:0:0:0:0:1', '148');
INSERT INTO `login_log` VALUES ('1', '2018-07-24 14:15:55', '0:0:0:0:0:0:0:1', '149');
INSERT INTO `login_log` VALUES ('1', '2018-07-24 15:13:32', '0:0:0:0:0:0:0:1', '150');
INSERT INTO `login_log` VALUES ('1', '2018-07-24 16:08:01', '0:0:0:0:0:0:0:1', '151');
INSERT INTO `login_log` VALUES ('1', '2018-07-24 16:08:58', '0:0:0:0:0:0:0:1', '152');
INSERT INTO `login_log` VALUES ('1', '2018-07-24 18:44:57', '0:0:0:0:0:0:0:1', '153');
INSERT INTO `login_log` VALUES ('1', '2018-07-24 19:38:15', '0:0:0:0:0:0:0:1', '154');
INSERT INTO `login_log` VALUES ('1', '2018-07-24 19:56:00', '0:0:0:0:0:0:0:1', '155');
INSERT INTO `login_log` VALUES ('1', '2018-07-24 21:19:09', '0:0:0:0:0:0:0:1', '156');
INSERT INTO `login_log` VALUES ('1', '2018-07-24 22:45:32', '0:0:0:0:0:0:0:1', '157');
INSERT INTO `login_log` VALUES ('1', '2018-07-25 10:25:15', '0:0:0:0:0:0:0:1', '158');
INSERT INTO `login_log` VALUES ('1', '2018-07-25 10:43:57', '0:0:0:0:0:0:0:1', '159');
INSERT INTO `login_log` VALUES ('1', '2018-07-25 11:13:13', '0:0:0:0:0:0:0:1', '160');
INSERT INTO `login_log` VALUES ('1', '2018-07-25 11:25:02', '0:0:0:0:0:0:0:1', '161');
INSERT INTO `login_log` VALUES ('1', '2018-07-25 14:23:45', '0:0:0:0:0:0:0:1', '162');
INSERT INTO `login_log` VALUES ('1', '2018-07-25 15:52:40', '0:0:0:0:0:0:0:1', '163');
INSERT INTO `login_log` VALUES ('1', '2018-07-25 16:29:57', '0:0:0:0:0:0:0:1', '164');
INSERT INTO `login_log` VALUES ('1', '2018-07-25 17:03:52', '0:0:0:0:0:0:0:1', '165');
INSERT INTO `login_log` VALUES ('1', '2018-07-25 17:22:53', '0:0:0:0:0:0:0:1', '166');
INSERT INTO `login_log` VALUES ('1', '2018-07-25 17:50:57', '0:0:0:0:0:0:0:1', '167');
INSERT INTO `login_log` VALUES ('1', '2018-07-25 17:53:00', '0:0:0:0:0:0:0:1', '168');
INSERT INTO `login_log` VALUES ('1', '2018-07-25 21:04:41', '0:0:0:0:0:0:0:1', '169');
INSERT INTO `login_log` VALUES ('1', '2018-07-25 22:06:20', '0:0:0:0:0:0:0:1', '170');
INSERT INTO `login_log` VALUES ('1', '2018-07-25 23:13:58', '0:0:0:0:0:0:0:1', '171');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 00:15:13', '0:0:0:0:0:0:0:1', '172');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 01:34:33', '0:0:0:0:0:0:0:1', '173');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 02:04:16', '0:0:0:0:0:0:0:1', '174');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 08:34:38', '0:0:0:0:0:0:0:1', '175');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 09:44:43', '0:0:0:0:0:0:0:1', '176');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 10:21:14', '0:0:0:0:0:0:0:1', '177');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 10:48:32', '0:0:0:0:0:0:0:1', '178');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 10:56:10', '0:0:0:0:0:0:0:1', '179');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 10:59:39', '0:0:0:0:0:0:0:1', '180');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 11:50:50', '0:0:0:0:0:0:0:1', '181');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 12:00:35', '0:0:0:0:0:0:0:1', '182');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 13:14:22', '0:0:0:0:0:0:0:1', '183');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 14:16:00', '0:0:0:0:0:0:0:1', '184');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 14:50:15', '0:0:0:0:0:0:0:1', '185');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 15:20:56', '0:0:0:0:0:0:0:1', '186');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 15:53:22', '0:0:0:0:0:0:0:1', '187');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 17:15:00', '0:0:0:0:0:0:0:1', '188');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 17:53:59', '0:0:0:0:0:0:0:1', '189');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 18:17:25', '0:0:0:0:0:0:0:1', '190');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 19:52:23', '0:0:0:0:0:0:0:1', '191');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 19:59:43', '0:0:0:0:0:0:0:1', '192');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 21:11:14', '0:0:0:0:0:0:0:1', '193');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 21:57:40', '0:0:0:0:0:0:0:1', '194');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 23:00:43', '0:0:0:0:0:0:0:1', '195');
INSERT INTO `login_log` VALUES ('1', '2018-07-26 23:19:11', '0:0:0:0:0:0:0:1', '196');
INSERT INTO `login_log` VALUES ('1', '2018-07-27 09:03:15', '0:0:0:0:0:0:0:1', '197');
INSERT INTO `login_log` VALUES ('1', '2018-07-27 09:16:49', '0:0:0:0:0:0:0:1', '198');
INSERT INTO `login_log` VALUES ('1', '2018-07-27 10:33:42', '0:0:0:0:0:0:0:1', '199');
INSERT INTO `login_log` VALUES ('1', '2018-07-27 11:48:32', '0:0:0:0:0:0:0:1', '200');
INSERT INTO `login_log` VALUES ('1', '2018-07-27 12:00:25', '0:0:0:0:0:0:0:1', '201');
INSERT INTO `login_log` VALUES ('1', '2018-07-27 13:06:36', '0:0:0:0:0:0:0:1', '202');
INSERT INTO `login_log` VALUES ('1', '2018-07-27 13:18:39', '0:0:0:0:0:0:0:1', '203');
INSERT INTO `login_log` VALUES ('1', '2018-07-27 13:50:20', '0:0:0:0:0:0:0:1', '204');
INSERT INTO `login_log` VALUES ('1', '2018-07-27 14:08:50', '0:0:0:0:0:0:0:1', '205');
INSERT INTO `login_log` VALUES ('1', '2018-07-27 14:23:25', '127.0.0.1', '206');
INSERT INTO `login_log` VALUES ('1', '2018-07-27 15:39:34', '0:0:0:0:0:0:0:1', '207');
INSERT INTO `login_log` VALUES ('1', '2018-07-27 18:48:04', '0:0:0:0:0:0:0:1', '208');
INSERT INTO `login_log` VALUES ('1', '2018-07-27 19:53:25', '0:0:0:0:0:0:0:1', '209');
INSERT INTO `login_log` VALUES ('1', '2018-07-27 22:42:57', '127.0.0.1', '210');
INSERT INTO `login_log` VALUES ('1', '2018-07-27 23:30:13', '0:0:0:0:0:0:0:1', '211');
INSERT INTO `login_log` VALUES ('1', '2018-07-28 00:01:14', '0:0:0:0:0:0:0:1', '212');
INSERT INTO `login_log` VALUES ('1', '2018-07-28 00:06:02', '0:0:0:0:0:0:0:1', '213');
INSERT INTO `login_log` VALUES ('1', '2018-07-28 10:14:50', '0:0:0:0:0:0:0:1', '214');
INSERT INTO `login_log` VALUES ('1', '2018-07-28 10:21:09', '0:0:0:0:0:0:0:1', '215');
INSERT INTO `login_log` VALUES ('1', '2018-07-28 13:33:32', '0:0:0:0:0:0:0:1', '216');
INSERT INTO `login_log` VALUES ('1', '2018-07-28 13:42:33', '0:0:0:0:0:0:0:1', '217');
INSERT INTO `login_log` VALUES ('1', '2018-07-28 14:45:16', '0:0:0:0:0:0:0:1', '218');
INSERT INTO `login_log` VALUES ('1', '2018-07-29 22:12:41', '0:0:0:0:0:0:0:1', '219');
INSERT INTO `login_log` VALUES ('1', '2018-07-29 22:50:04', '0:0:0:0:0:0:0:1', '220');
INSERT INTO `login_log` VALUES ('1', '2018-07-30 09:01:07', '0:0:0:0:0:0:0:1', '221');
INSERT INTO `login_log` VALUES ('1', '2018-07-30 11:33:26', '0:0:0:0:0:0:0:1', '222');
INSERT INTO `login_log` VALUES ('1', '2018-07-30 14:10:36', '0:0:0:0:0:0:0:1', '223');
INSERT INTO `login_log` VALUES ('1', '2018-07-30 23:18:04', '0:0:0:0:0:0:0:1', '224');
INSERT INTO `login_log` VALUES ('1', '2018-07-31 10:47:50', '0:0:0:0:0:0:0:1', '225');
INSERT INTO `login_log` VALUES ('1', '2018-07-31 11:39:05', '0:0:0:0:0:0:0:1', '226');
INSERT INTO `login_log` VALUES ('1', '2018-08-01 12:08:02', '0:0:0:0:0:0:0:1', '227');
INSERT INTO `login_log` VALUES ('1', '2018-08-01 19:39:26', '0:0:0:0:0:0:0:1', '228');
INSERT INTO `login_log` VALUES ('1', '2018-08-01 20:15:39', '0:0:0:0:0:0:0:1', '229');
INSERT INTO `login_log` VALUES ('1', '2018-08-02 07:26:34', '0:0:0:0:0:0:0:1', '230');
INSERT INTO `login_log` VALUES ('1', '2018-08-02 08:17:13', '0:0:0:0:0:0:0:1', '231');
INSERT INTO `login_log` VALUES ('1', '2018-08-02 10:59:50', '0:0:0:0:0:0:0:1', '232');
INSERT INTO `login_log` VALUES ('1', '2018-08-02 12:10:13', '127.0.0.1', '233');
INSERT INTO `login_log` VALUES ('1', '2018-08-02 13:11:05', '0:0:0:0:0:0:0:1', '234');
INSERT INTO `login_log` VALUES ('1', '2018-08-02 14:40:00', '0:0:0:0:0:0:0:1', '235');
INSERT INTO `login_log` VALUES ('1', '2018-08-02 15:35:59', '0:0:0:0:0:0:0:1', '236');
INSERT INTO `login_log` VALUES ('1', '2018-08-02 16:39:36', '0:0:0:0:0:0:0:1', '237');
INSERT INTO `login_log` VALUES ('1', '2018-08-02 18:26:23', '0:0:0:0:0:0:0:1', '238');
INSERT INTO `login_log` VALUES ('1', '2018-08-02 20:25:23', '0:0:0:0:0:0:0:1', '239');
INSERT INTO `login_log` VALUES ('1', '2018-08-02 22:37:03', '0:0:0:0:0:0:0:1', '240');
INSERT INTO `login_log` VALUES ('1', '2018-08-03 01:18:15', '0:0:0:0:0:0:0:1', '241');
INSERT INTO `login_log` VALUES ('1', '2018-08-03 09:58:51', '0:0:0:0:0:0:0:1', '242');
INSERT INTO `login_log` VALUES ('1', '2018-08-03 10:12:59', '0:0:0:0:0:0:0:1', '243');
INSERT INTO `login_log` VALUES ('1', '2018-08-03 13:06:35', '0:0:0:0:0:0:0:1', '244');
INSERT INTO `login_log` VALUES ('1', '2018-08-03 13:16:41', '0:0:0:0:0:0:0:1', '245');
INSERT INTO `login_log` VALUES ('1', '2018-08-03 14:40:27', '0:0:0:0:0:0:0:1', '246');
INSERT INTO `login_log` VALUES ('1', '2018-08-03 14:46:43', '0:0:0:0:0:0:0:1', '247');
INSERT INTO `login_log` VALUES ('1', '2018-08-03 14:49:58', '0:0:0:0:0:0:0:1', '248');
INSERT INTO `login_log` VALUES ('1', '2018-08-03 15:45:42', '0:0:0:0:0:0:0:1', '249');
INSERT INTO `login_log` VALUES ('1', '2018-08-03 15:49:11', '0:0:0:0:0:0:0:1', '250');
INSERT INTO `login_log` VALUES ('1', '2018-08-03 17:34:23', '127.0.0.1', '251');
INSERT INTO `login_log` VALUES ('1', '2018-08-03 20:40:23', '0:0:0:0:0:0:0:1', '252');
INSERT INTO `login_log` VALUES ('1', '2018-08-04 00:03:53', '0:0:0:0:0:0:0:1', '253');
INSERT INTO `login_log` VALUES ('1', '2018-08-04 00:15:54', '127.0.0.1', '254');
INSERT INTO `login_log` VALUES ('1', '2018-08-04 01:00:19', '0:0:0:0:0:0:0:1', '255');
INSERT INTO `login_log` VALUES ('1', '2018-08-04 01:40:36', '0:0:0:0:0:0:0:1', '256');
INSERT INTO `login_log` VALUES ('1', '2018-08-04 01:41:49', '0:0:0:0:0:0:0:1', '257');
INSERT INTO `login_log` VALUES ('1', '2018-08-04 07:28:57', '0:0:0:0:0:0:0:1', '258');
INSERT INTO `login_log` VALUES ('1', '2018-08-04 08:52:12', '0:0:0:0:0:0:0:1', '259');
INSERT INTO `login_log` VALUES ('1', '2018-08-04 11:49:16', '0:0:0:0:0:0:0:1', '260');
INSERT INTO `login_log` VALUES ('1', '2018-08-04 13:08:52', '0:0:0:0:0:0:0:1', '261');
INSERT INTO `login_log` VALUES ('1', '2018-08-04 14:46:57', '0:0:0:0:0:0:0:1', '262');
INSERT INTO `login_log` VALUES ('1', '2018-08-04 18:51:23', '0:0:0:0:0:0:0:1', '263');
INSERT INTO `login_log` VALUES ('1', '2018-08-05 22:10:12', '0:0:0:0:0:0:0:1', '264');
INSERT INTO `login_log` VALUES ('1', '2018-08-05 22:19:56', '0:0:0:0:0:0:0:1', '265');
INSERT INTO `login_log` VALUES ('1', '2018-08-05 23:00:28', '0:0:0:0:0:0:0:1', '266');
INSERT INTO `login_log` VALUES ('1', '2018-08-05 23:14:30', '0:0:0:0:0:0:0:1', '267');
INSERT INTO `login_log` VALUES ('1', '2018-08-06 00:18:01', '0:0:0:0:0:0:0:1', '268');
INSERT INTO `login_log` VALUES ('1', '2018-08-06 08:33:51', '0:0:0:0:0:0:0:1', '269');
INSERT INTO `login_log` VALUES ('1', '2018-08-06 10:45:23', '0:0:0:0:0:0:0:1', '270');
INSERT INTO `login_log` VALUES ('1', '2018-08-06 12:06:06', '127.0.0.1', '271');
INSERT INTO `login_log` VALUES ('1', '2018-08-06 13:44:53', '0:0:0:0:0:0:0:1', '272');
INSERT INTO `login_log` VALUES ('1', '2018-08-06 14:58:44', '0:0:0:0:0:0:0:1', '273');
INSERT INTO `login_log` VALUES ('1', '2018-08-06 22:27:43', '0:0:0:0:0:0:0:1', '274');
INSERT INTO `login_log` VALUES ('1', '2018-08-06 22:31:31', '0:0:0:0:0:0:0:1', '275');
INSERT INTO `login_log` VALUES ('1', '2018-08-06 22:49:35', '0:0:0:0:0:0:0:1', '276');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 06:21:48', '0:0:0:0:0:0:0:1', '277');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 07:03:51', '0:0:0:0:0:0:0:1', '278');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 10:29:13', '0:0:0:0:0:0:0:1', '279');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 11:12:47', '0:0:0:0:0:0:0:1', '280');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 14:00:33', '0:0:0:0:0:0:0:1', '281');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 14:47:35', '0:0:0:0:0:0:0:1', '282');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 15:29:51', '0:0:0:0:0:0:0:1', '283');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 15:31:55', '0:0:0:0:0:0:0:1', '284');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 15:35:46', '0:0:0:0:0:0:0:1', '285');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 15:37:43', '0:0:0:0:0:0:0:1', '286');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 15:39:28', '0:0:0:0:0:0:0:1', '287');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 15:47:08', '0:0:0:0:0:0:0:1', '288');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 16:11:40', '127.0.0.1', '289');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 16:41:27', '0:0:0:0:0:0:0:1', '290');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 17:07:57', '0:0:0:0:0:0:0:1', '291');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 20:03:44', '0:0:0:0:0:0:0:1', '292');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 20:47:16', '0:0:0:0:0:0:0:1', '293');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 22:06:27', '0:0:0:0:0:0:0:1', '294');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 22:07:17', '0:0:0:0:0:0:0:1', '295');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 22:13:18', '0:0:0:0:0:0:0:1', '296');
INSERT INTO `login_log` VALUES ('1', '2018-08-07 23:16:55', '0:0:0:0:0:0:0:1', '297');
INSERT INTO `login_log` VALUES ('1', '2018-08-08 08:46:59', '0:0:0:0:0:0:0:1', '298');
INSERT INTO `login_log` VALUES ('1', '2018-08-08 10:26:52', '0:0:0:0:0:0:0:1', '299');
INSERT INTO `login_log` VALUES ('1', '2018-08-08 10:33:24', '0:0:0:0:0:0:0:1', '300');
INSERT INTO `login_log` VALUES ('1', '2018-08-08 10:57:43', '0:0:0:0:0:0:0:1', '301');
INSERT INTO `login_log` VALUES ('1', '2018-08-08 14:13:26', '0:0:0:0:0:0:0:1', '302');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 09:14:55', '0:0:0:0:0:0:0:1', '303');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 09:15:32', '0:0:0:0:0:0:0:1', '304');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 09:17:53', '0:0:0:0:0:0:0:1', '305');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 09:20:22', '0:0:0:0:0:0:0:1', '306');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 09:22:36', '0:0:0:0:0:0:0:1', '307');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 09:37:15', '0:0:0:0:0:0:0:1', '308');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 09:40:24', '0:0:0:0:0:0:0:1', '309');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 09:50:32', '0:0:0:0:0:0:0:1', '310');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 09:51:40', '0:0:0:0:0:0:0:1', '311');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 10:02:19', '127.0.0.1', '312');
INSERT INTO `login_log` VALUES ('3', '2018-08-25 10:03:49', '127.0.0.1', '313');
INSERT INTO `login_log` VALUES ('3', '2018-08-25 10:06:58', '0:0:0:0:0:0:0:1', '314');
INSERT INTO `login_log` VALUES ('3', '2018-08-25 11:22:58', '127.0.0.1', '315');
INSERT INTO `login_log` VALUES ('3', '2018-08-25 11:23:07', '127.0.0.1', '316');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 11:23:18', '127.0.0.1', '317');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 11:25:29', '0:0:0:0:0:0:0:1', '318');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 11:52:18', '0:0:0:0:0:0:0:1', '319');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 12:44:46', '0:0:0:0:0:0:0:1', '320');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 13:52:45', '0:0:0:0:0:0:0:1', '321');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 13:55:01', '0:0:0:0:0:0:0:1', '322');
INSERT INTO `login_log` VALUES ('3', '2018-08-25 14:09:22', '0:0:0:0:0:0:0:1', '323');
INSERT INTO `login_log` VALUES ('3', '2018-08-25 14:26:01', '127.0.0.1', '324');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 14:26:12', '127.0.0.1', '325');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 16:18:52', '0:0:0:0:0:0:0:1', '326');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 17:07:08', '0:0:0:0:0:0:0:1', '327');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 17:12:15', '0:0:0:0:0:0:0:1', '328');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 18:12:00', '0:0:0:0:0:0:0:1', '329');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 21:26:33', '0:0:0:0:0:0:0:1', '330');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 21:53:18', '0:0:0:0:0:0:0:1', '331');
INSERT INTO `login_log` VALUES ('3', '2018-08-25 22:18:55', '0:0:0:0:0:0:0:1', '332');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 22:23:23', '0:0:0:0:0:0:0:1', '333');
INSERT INTO `login_log` VALUES ('1', '2018-08-25 23:11:04', '0:0:0:0:0:0:0:1', '334');
INSERT INTO `login_log` VALUES ('1', '2018-08-26 09:17:10', '0:0:0:0:0:0:0:1', '335');
INSERT INTO `login_log` VALUES ('1', '2018-08-26 09:30:31', '0:0:0:0:0:0:0:1', '336');
INSERT INTO `login_log` VALUES ('1', '2018-08-26 09:46:01', '0:0:0:0:0:0:0:1', '337');
INSERT INTO `login_log` VALUES ('1', '2018-08-26 10:25:37', '0:0:0:0:0:0:0:1', '338');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 10:52:25', '0:0:0:0:0:0:0:1', '339');
INSERT INTO `login_log` VALUES ('1', '2018-08-26 10:52:46', '0:0:0:0:0:0:0:1', '340');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 10:53:08', '0:0:0:0:0:0:0:1', '341');
INSERT INTO `login_log` VALUES ('1', '2018-08-26 11:25:32', '0:0:0:0:0:0:0:1', '342');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 11:26:07', '0:0:0:0:0:0:0:1', '343');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 11:37:23', '0:0:0:0:0:0:0:1', '344');
INSERT INTO `login_log` VALUES ('1', '2018-08-26 11:48:49', '0:0:0:0:0:0:0:1', '345');
INSERT INTO `login_log` VALUES ('1', '2018-08-26 13:36:04', '0:0:0:0:0:0:0:1', '346');
INSERT INTO `login_log` VALUES ('1', '2018-08-26 14:12:13', '0:0:0:0:0:0:0:1', '347');
INSERT INTO `login_log` VALUES ('1', '2018-08-26 15:29:46', '0:0:0:0:0:0:0:1', '348');
INSERT INTO `login_log` VALUES ('1', '2018-08-26 16:33:24', '0:0:0:0:0:0:0:1', '349');
INSERT INTO `login_log` VALUES ('1', '2018-08-26 16:57:03', '0:0:0:0:0:0:0:1', '350');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 17:07:40', '0:0:0:0:0:0:0:1', '351');
INSERT INTO `login_log` VALUES ('1', '2018-08-26 17:11:16', '0:0:0:0:0:0:0:1', '352');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 17:22:15', '0:0:0:0:0:0:0:1', '353');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 17:58:58', '0:0:0:0:0:0:0:1', '354');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 18:06:37', '0:0:0:0:0:0:0:1', '355');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 19:44:06', '0:0:0:0:0:0:0:1', '356');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 20:14:46', '0:0:0:0:0:0:0:1', '357');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 20:16:10', '0:0:0:0:0:0:0:1', '358');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 20:16:52', '127.0.0.1', '359');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 20:17:46', '0:0:0:0:0:0:0:1', '360');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 21:04:18', '0:0:0:0:0:0:0:1', '361');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 21:35:07', '0:0:0:0:0:0:0:1', '362');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 21:43:51', '0:0:0:0:0:0:0:1', '363');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 22:01:07', '0:0:0:0:0:0:0:1', '364');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 23:07:09', '0:0:0:0:0:0:0:1', '365');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 23:12:51', '127.0.0.1', '366');
INSERT INTO `login_log` VALUES ('3', '2018-08-26 23:17:22', '0:0:0:0:0:0:0:1', '367');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 08:08:33', '0:0:0:0:0:0:0:1', '368');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 09:30:32', '0:0:0:0:0:0:0:1', '369');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 10:03:21', '127.0.0.1', '370');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 10:14:43', '0:0:0:0:0:0:0:1', '371');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 10:22:20', '0:0:0:0:0:0:0:1', '372');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 11:41:54', '101.105.2.10', '373');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 11:59:50', '59.41.65.217', '374');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 12:12:57', '101.105.2.10', '375');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 12:20:05', '101.105.2.10', '376');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 12:35:35', '0:0:0:0:0:0:0:1', '377');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 12:44:46', '0:0:0:0:0:0:0:1', '378');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 13:31:25', '119.128.172.185', '379');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 13:51:24', '59.41.65.217', '380');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 13:55:24', '101.105.2.10', '381');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 14:05:22', '101.105.2.10', '382');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 14:25:14', '113.65.209.227', '383');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 15:29:48', '119.128.172.185', '384');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 15:43:56', '101.105.2.10', '385');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 15:46:49', '0:0:0:0:0:0:0:1', '386');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 15:49:09', '0:0:0:0:0:0:0:1', '387');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 16:26:02', '0:0:0:0:0:0:0:1', '388');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 16:43:57', '119.128.172.185', '389');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 17:31:36', '101.105.2.10', '390');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 17:32:35', '0:0:0:0:0:0:0:1', '391');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 18:07:40', '0:0:0:0:0:0:0:1', '392');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 18:11:26', '0:0:0:0:0:0:0:1', '393');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 18:54:04', '113.65.209.227', '394');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 20:00:09', '0:0:0:0:0:0:0:1', '395');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 20:04:57', '0:0:0:0:0:0:0:1', '396');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 20:23:56', '0:0:0:0:0:0:0:1', '397');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 20:30:48', '0:0:0:0:0:0:0:1', '398');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 20:37:32', '0:0:0:0:0:0:0:1', '399');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 21:13:34', '0:0:0:0:0:0:0:1', '400');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 23:27:28', '0:0:0:0:0:0:0:1', '401');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 23:49:05', '0:0:0:0:0:0:0:1', '402');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 23:51:13', '0:0:0:0:0:0:0:1', '403');
INSERT INTO `login_log` VALUES ('1', '2018-08-27 23:53:41', '0:0:0:0:0:0:0:1', '404');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 00:12:43', '0:0:0:0:0:0:0:1', '405');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 08:21:15', '119.128.172.191', '406');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 10:05:31', '0:0:0:0:0:0:0:1', '407');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 10:22:47', '0:0:0:0:0:0:0:1', '408');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 10:23:19', '0:0:0:0:0:0:0:1', '409');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 10:55:02', '127.0.0.1', '410');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 13:29:48', '0:0:0:0:0:0:0:1', '411');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 14:17:05', '0:0:0:0:0:0:0:1', '412');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 14:25:23', '0:0:0:0:0:0:0:1', '413');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 14:35:57', '0:0:0:0:0:0:0:1', '414');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 15:17:13', '0:0:0:0:0:0:0:1', '415');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 15:24:37', '0:0:0:0:0:0:0:1', '416');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 15:31:59', '0:0:0:0:0:0:0:1', '417');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 15:33:12', '0:0:0:0:0:0:0:1', '418');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 16:19:21', '0:0:0:0:0:0:0:1', '419');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 16:54:42', '0:0:0:0:0:0:0:1', '420');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 17:22:39', '0:0:0:0:0:0:0:1', '421');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 17:28:25', '0:0:0:0:0:0:0:1', '422');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 18:03:19', '113.77.87.140', '423');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 19:06:32', '0:0:0:0:0:0:0:1', '424');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 20:16:38', '0:0:0:0:0:0:0:1', '425');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 21:28:18', '0:0:0:0:0:0:0:1', '426');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 22:15:40', '0:0:0:0:0:0:0:1', '427');
INSERT INTO `login_log` VALUES ('1', '2018-08-28 23:57:02', '0:0:0:0:0:0:0:1', '428');
INSERT INTO `login_log` VALUES ('1', '2018-08-29 00:51:44', '0:0:0:0:0:0:0:1', '429');
INSERT INTO `login_log` VALUES ('1', '2018-08-29 01:04:44', '127.0.0.1', '430');
INSERT INTO `login_log` VALUES ('1', '2018-08-29 01:07:21', '127.0.0.1', '431');
INSERT INTO `login_log` VALUES ('1', '2018-08-29 01:27:53', '0:0:0:0:0:0:0:1', '432');
INSERT INTO `login_log` VALUES ('1', '2018-08-29 01:31:20', '0:0:0:0:0:0:0:1', '433');
INSERT INTO `login_log` VALUES ('1', '2018-08-29 09:23:51', '0:0:0:0:0:0:0:1', '434');
INSERT INTO `login_log` VALUES ('1', '2018-08-29 10:38:11', '0:0:0:0:0:0:0:1', '435');
INSERT INTO `login_log` VALUES ('1', '2018-08-29 11:12:15', '0:0:0:0:0:0:0:1', '436');
INSERT INTO `login_log` VALUES ('1', '2018-08-29 11:52:27', '0:0:0:0:0:0:0:1', '437');
INSERT INTO `login_log` VALUES ('1', '2018-08-29 12:10:19', '127.0.0.1', '438');
INSERT INTO `login_log` VALUES ('1', '2018-08-29 12:57:41', '0:0:0:0:0:0:0:1', '439');
INSERT INTO `login_log` VALUES ('1', '2018-08-29 20:21:59', '101.105.2.10', '440');
INSERT INTO `login_log` VALUES ('1', '2018-08-29 21:34:12', '101.105.2.10', '441');
INSERT INTO `login_log` VALUES ('1', '2018-08-29 22:22:42', '101.105.2.10', '442');
INSERT INTO `login_log` VALUES ('1', '2018-08-30 07:59:31', '113.77.86.110', '443');
INSERT INTO `login_log` VALUES ('1', '2018-08-30 08:33:33', '113.77.86.110', '444');
INSERT INTO `login_log` VALUES ('1', '2018-08-30 08:35:23', '113.77.86.110', '445');
INSERT INTO `login_log` VALUES ('1', '2018-08-30 08:43:27', '113.77.86.110', '446');
INSERT INTO `login_log` VALUES ('1', '2018-08-30 08:44:10', '113.77.86.110', '447');
INSERT INTO `login_log` VALUES ('1', '2018-08-30 09:12:28', '101.105.2.10', '448');
INSERT INTO `login_log` VALUES ('1', '2018-08-30 11:26:24', '119.128.172.34', '449');
INSERT INTO `login_log` VALUES ('1', '2018-08-30 12:02:50', '119.128.172.34', '450');
INSERT INTO `login_log` VALUES ('1', '2018-08-30 13:33:30', '113.77.87.238', '451');
INSERT INTO `login_log` VALUES ('1', '2018-08-31 00:54:59', '101.105.2.10', '452');
INSERT INTO `login_log` VALUES ('1', '2018-08-31 08:48:04', '119.128.173.35', '453');
INSERT INTO `login_log` VALUES ('1', '2018-08-31 09:39:49', '101.105.2.10', '454');
INSERT INTO `login_log` VALUES ('1', '2018-08-31 13:20:19', '119.128.173.25', '455');
INSERT INTO `login_log` VALUES ('7', '2018-08-31 14:06:06', '119.128.173.25', '456');
INSERT INTO `login_log` VALUES ('1', '2018-08-31 14:06:53', '119.128.173.25', '457');
INSERT INTO `login_log` VALUES ('1', '2018-08-31 14:27:09', '119.128.173.25', '458');
INSERT INTO `login_log` VALUES ('1', '2018-08-31 14:55:44', '119.128.173.25', '459');
INSERT INTO `login_log` VALUES ('1', '2018-08-31 15:09:12', '119.128.173.25', '460');
INSERT INTO `login_log` VALUES ('1', '2018-08-31 15:25:55', '117.136.40.187', '461');
INSERT INTO `login_log` VALUES ('1', '2018-08-31 16:10:45', '119.128.173.25', '462');
INSERT INTO `login_log` VALUES ('7', '2018-08-31 16:11:07', '119.128.173.25', '463');
INSERT INTO `login_log` VALUES ('1', '2018-08-31 16:14:25', '119.128.173.25', '464');
INSERT INTO `login_log` VALUES ('1', '2018-08-31 18:03:58', '119.128.173.25', '465');
INSERT INTO `login_log` VALUES ('1', '2018-08-31 19:14:00', '119.128.173.25', '466');
INSERT INTO `login_log` VALUES ('1', '2018-08-31 21:46:58', '14.217.202.140', '467');
INSERT INTO `login_log` VALUES ('1', '2018-09-01 07:23:17', '14.217.202.140', '468');
INSERT INTO `login_log` VALUES ('1', '2018-09-03 20:48:47', '101.105.2.10', '469');
INSERT INTO `login_log` VALUES ('1', '2018-09-04 08:09:21', '113.77.87.52', '470');
INSERT INTO `login_log` VALUES ('1', '2018-09-04 08:22:55', '113.77.87.52', '471');
INSERT INTO `login_log` VALUES ('1', '2018-09-04 09:51:09', '113.77.86.4', '472');
INSERT INTO `login_log` VALUES ('1', '2018-09-04 10:07:45', '113.77.86.4', '473');
INSERT INTO `login_log` VALUES ('1', '2018-09-04 10:27:19', '113.77.86.4', '474');
INSERT INTO `login_log` VALUES ('1', '2018-09-04 10:44:08', '101.105.2.10', '475');
INSERT INTO `login_log` VALUES ('1', '2018-09-04 11:25:11', '101.105.2.10', '476');
INSERT INTO `login_log` VALUES ('1', '2018-09-04 11:35:18', '113.77.86.4', '477');
INSERT INTO `login_log` VALUES ('1', '2018-09-04 12:09:34', '101.105.2.10', '478');
INSERT INTO `login_log` VALUES ('1', '2018-09-04 13:30:09', '113.77.85.7', '479');
INSERT INTO `login_log` VALUES ('1', '2018-09-04 13:38:07', '113.77.87.65', '480');
INSERT INTO `login_log` VALUES ('1', '2018-09-04 13:46:52', '101.105.2.10', '481');
INSERT INTO `login_log` VALUES ('1', '2018-09-04 13:48:00', '113.77.87.65', '482');
INSERT INTO `login_log` VALUES ('1', '2018-09-04 15:38:06', '113.77.86.86', '483');
INSERT INTO `login_log` VALUES ('1', '2018-09-04 16:12:50', '113.77.86.86', '484');
INSERT INTO `login_log` VALUES ('1', '2018-09-04 18:32:15', '113.77.84.47', '485');
INSERT INTO `login_log` VALUES ('1', '2018-09-04 23:11:09', '101.105.2.10', '486');
INSERT INTO `login_log` VALUES ('1', '2018-09-05 08:31:12', '113.77.87.110', '487');
INSERT INTO `login_log` VALUES ('1', '2018-09-05 08:33:14', '113.77.87.110', '488');
INSERT INTO `login_log` VALUES ('1', '2018-09-05 09:08:10', '113.77.87.110', '489');
INSERT INTO `login_log` VALUES ('1', '2018-09-05 09:09:07', '113.77.87.110', '490');
INSERT INTO `login_log` VALUES ('1', '2018-09-05 10:59:54', '113.77.85.23', '491');
INSERT INTO `login_log` VALUES ('1', '2018-09-05 11:00:03', '113.77.85.23', '492');
INSERT INTO `login_log` VALUES ('1', '2018-09-05 12:46:22', '113.88.14.26', '493');
INSERT INTO `login_log` VALUES ('1', '2018-09-05 17:50:30', '113.77.84.53', '494');
INSERT INTO `login_log` VALUES ('1', '2018-09-05 18:30:41', '113.88.14.26', '495');
INSERT INTO `login_log` VALUES ('1', '2018-09-05 19:23:14', '113.77.84.53', '496');
INSERT INTO `login_log` VALUES ('1', '2018-09-05 23:34:59', '101.105.2.10', '497');
INSERT INTO `login_log` VALUES ('1', '2018-09-06 08:18:57', '113.77.85.227', '498');
INSERT INTO `login_log` VALUES ('1', '2018-09-06 08:40:10', '113.88.13.212', '499');
INSERT INTO `login_log` VALUES ('1', '2018-09-06 09:12:19', '113.77.85.227', '500');
INSERT INTO `login_log` VALUES ('1', '2018-09-06 10:38:12', '113.88.13.212', '501');
INSERT INTO `login_log` VALUES ('1', '2018-09-06 12:19:31', '113.77.84.192', '502');
INSERT INTO `login_log` VALUES ('1', '2018-09-06 12:30:42', '113.88.13.212', '503');
INSERT INTO `login_log` VALUES ('1', '2018-09-06 13:30:31', '113.77.84.192', '504');
INSERT INTO `login_log` VALUES ('1', '2018-09-06 13:31:48', '113.77.84.192', '505');
INSERT INTO `login_log` VALUES ('1', '2018-09-06 14:13:17', '113.88.13.212', '506');
INSERT INTO `login_log` VALUES ('1', '2018-09-06 14:13:17', '113.88.13.212', '507');
INSERT INTO `login_log` VALUES ('1', '2018-09-06 14:16:30', '113.77.85.94', '508');
INSERT INTO `login_log` VALUES ('1', '2018-09-06 15:21:26', '113.88.13.212', '509');
INSERT INTO `login_log` VALUES ('1', '2018-09-06 17:17:31', '113.77.86.74', '510');
INSERT INTO `login_log` VALUES ('1', '2018-09-06 19:14:44', '113.77.87.73', '511');
INSERT INTO `login_log` VALUES ('1', '2018-09-06 21:25:00', '101.105.2.10', '512');
INSERT INTO `login_log` VALUES ('1', '2018-09-07 00:14:32', '101.105.2.10', '513');
INSERT INTO `login_log` VALUES ('1', '2018-09-07 13:48:23', '119.128.172.183', '514');
INSERT INTO `login_log` VALUES ('1', '2018-09-07 14:03:25', '119.128.172.183', '515');
INSERT INTO `login_log` VALUES ('1', '2018-09-07 16:36:59', '119.128.172.183', '516');
INSERT INTO `login_log` VALUES ('1', '2018-09-07 16:43:16', '119.128.172.183', '517');
INSERT INTO `login_log` VALUES ('1', '2018-09-07 17:19:54', '119.128.172.183', '518');
INSERT INTO `login_log` VALUES ('1', '2018-09-07 18:02:20', '119.128.172.183', '519');
INSERT INTO `login_log` VALUES ('1', '2018-09-07 18:22:49', '119.128.172.183', '520');
INSERT INTO `login_log` VALUES ('1', '2018-09-08 08:42:31', '113.77.85.180', '521');
INSERT INTO `login_log` VALUES ('1', '2018-09-08 09:34:09', '113.77.85.180', '522');
INSERT INTO `login_log` VALUES ('1', '2018-09-08 10:36:56', '113.77.84.144', '523');
INSERT INTO `login_log` VALUES ('1', '2018-09-08 13:29:57', '113.77.86.130', '524');
INSERT INTO `login_log` VALUES ('1', '2018-09-08 14:37:30', '113.77.85.25', '525');
INSERT INTO `login_log` VALUES ('1', '2018-09-08 14:56:54', '113.77.85.25', '526');
INSERT INTO `login_log` VALUES ('1', '2018-09-08 16:34:51', '113.77.84.18', '527');
INSERT INTO `login_log` VALUES ('1', '2018-09-08 17:17:02', '113.77.84.18', '528');
INSERT INTO `login_log` VALUES ('1', '2018-09-08 23:17:47', '0:0:0:0:0:0:0:1', '529');
INSERT INTO `login_log` VALUES ('1', '2018-09-09 08:35:18', '0:0:0:0:0:0:0:1', '530');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 01:49:02', '0:0:0:0:0:0:0:1', '531');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 07:52:29', '0:0:0:0:0:0:0:1', '532');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 07:57:29', '0:0:0:0:0:0:0:1', '533');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 17:03:55', '0:0:0:0:0:0:0:1', '534');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 17:43:22', '0:0:0:0:0:0:0:1', '535');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 17:50:59', '0:0:0:0:0:0:0:1', '536');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 17:58:49', '0:0:0:0:0:0:0:1', '537');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 18:38:24', '0:0:0:0:0:0:0:1', '538');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 18:41:30', '0:0:0:0:0:0:0:1', '539');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 18:42:54', '0:0:0:0:0:0:0:1', '540');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 18:43:58', '0:0:0:0:0:0:0:1', '541');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 19:40:48', '0:0:0:0:0:0:0:1', '542');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 19:43:54', '0:0:0:0:0:0:0:1', '543');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 19:44:56', '0:0:0:0:0:0:0:1', '544');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 20:35:43', '0:0:0:0:0:0:0:1', '545');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 22:05:47', '0:0:0:0:0:0:0:1', '546');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 22:24:51', '0:0:0:0:0:0:0:1', '547');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 22:29:30', '0:0:0:0:0:0:0:1', '548');
INSERT INTO `login_log` VALUES ('1', '2018-09-10 23:45:57', '0:0:0:0:0:0:0:1', '549');
INSERT INTO `login_log` VALUES ('1', '2018-09-11 08:00:54', '0:0:0:0:0:0:0:1', '550');
INSERT INTO `login_log` VALUES ('1', '2018-09-11 08:14:54', '127.0.0.1', '551');
INSERT INTO `login_log` VALUES ('1', '2018-09-11 08:38:53', '0:0:0:0:0:0:0:1', '552');
INSERT INTO `login_log` VALUES ('1', '2018-09-11 21:17:02', '0:0:0:0:0:0:0:1', '553');
INSERT INTO `login_log` VALUES ('1', '2018-09-12 01:07:53', '0:0:0:0:0:0:0:1', '554');
INSERT INTO `login_log` VALUES ('1', '2018-09-12 14:05:32', '0:0:0:0:0:0:0:1', '555');
INSERT INTO `login_log` VALUES ('1', '2018-09-12 14:10:32', '0:0:0:0:0:0:0:1', '556');
INSERT INTO `login_log` VALUES ('1', '2018-09-12 15:11:26', '0:0:0:0:0:0:0:1', '557');
INSERT INTO `login_log` VALUES ('1', '2018-09-12 15:35:35', '0:0:0:0:0:0:0:1', '558');
INSERT INTO `login_log` VALUES ('1', '2018-09-12 20:11:51', '0:0:0:0:0:0:0:1', '559');
INSERT INTO `login_log` VALUES ('1', '2018-09-12 21:28:40', '0:0:0:0:0:0:0:1', '560');
INSERT INTO `login_log` VALUES ('1', '2018-09-12 21:37:35', '0:0:0:0:0:0:0:1', '561');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 09:01:03', '0:0:0:0:0:0:0:1', '562');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 09:02:58', '0:0:0:0:0:0:0:1', '563');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 09:19:49', '0:0:0:0:0:0:0:1', '564');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 09:49:32', '0:0:0:0:0:0:0:1', '565');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 11:06:39', '0:0:0:0:0:0:0:1', '566');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 12:10:02', '0:0:0:0:0:0:0:1', '567');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 13:04:23', '0:0:0:0:0:0:0:1', '568');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 13:41:17', '0:0:0:0:0:0:0:1', '569');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 13:54:41', '0:0:0:0:0:0:0:1', '570');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 14:16:34', '0:0:0:0:0:0:0:1', '571');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 15:44:21', '0:0:0:0:0:0:0:1', '572');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 16:26:31', '0:0:0:0:0:0:0:1', '573');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 16:49:06', '0:0:0:0:0:0:0:1', '574');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 17:03:46', '0:0:0:0:0:0:0:1', '575');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 18:36:32', '0:0:0:0:0:0:0:1', '576');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 18:58:24', '0:0:0:0:0:0:0:1', '577');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 21:48:53', '0:0:0:0:0:0:0:1', '578');
INSERT INTO `login_log` VALUES ('1', '2018-09-13 23:09:19', '0:0:0:0:0:0:0:1', '579');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 07:55:23', '0:0:0:0:0:0:0:1', '580');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 08:34:31', '0:0:0:0:0:0:0:1', '581');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 09:20:57', '0:0:0:0:0:0:0:1', '582');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 10:30:19', '0:0:0:0:0:0:0:1', '583');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 10:49:17', '0:0:0:0:0:0:0:1', '584');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 11:06:23', '0:0:0:0:0:0:0:1', '585');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 13:17:52', '0:0:0:0:0:0:0:1', '586');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 13:23:08', '0:0:0:0:0:0:0:1', '587');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 14:23:40', '0:0:0:0:0:0:0:1', '588');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 15:42:46', '0:0:0:0:0:0:0:1', '589');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 15:45:29', '0:0:0:0:0:0:0:1', '590');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 15:47:30', '0:0:0:0:0:0:0:1', '591');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 15:49:12', '0:0:0:0:0:0:0:1', '592');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 15:51:07', '0:0:0:0:0:0:0:1', '593');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 16:45:37', '0:0:0:0:0:0:0:1', '594');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 17:21:02', '0:0:0:0:0:0:0:1', '595');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 19:12:56', '0:0:0:0:0:0:0:1', '596');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 19:44:10', '0:0:0:0:0:0:0:1', '597');
INSERT INTO `login_log` VALUES ('1', '2018-09-14 21:23:09', '0:0:0:0:0:0:0:1', '598');
INSERT INTO `login_log` VALUES ('1', '2018-09-15 07:44:07', '0:0:0:0:0:0:0:1', '599');
INSERT INTO `login_log` VALUES ('1', '2018-09-15 09:07:32', '0:0:0:0:0:0:0:1', '600');
INSERT INTO `login_log` VALUES ('1', '2018-09-15 09:52:46', '0:0:0:0:0:0:0:1', '601');
INSERT INTO `login_log` VALUES ('1', '2018-09-15 10:03:25', '0:0:0:0:0:0:0:1', '602');
INSERT INTO `login_log` VALUES ('1', '2018-09-15 10:46:17', '0:0:0:0:0:0:0:1', '603');
INSERT INTO `login_log` VALUES ('1', '2018-09-15 11:05:40', '0:0:0:0:0:0:0:1', '604');
INSERT INTO `login_log` VALUES ('1', '2018-09-15 13:30:03', '0:0:0:0:0:0:0:1', '605');
INSERT INTO `login_log` VALUES ('1', '2018-09-15 13:38:50', '0:0:0:0:0:0:0:1', '606');
INSERT INTO `login_log` VALUES ('1', '2018-09-15 13:48:12', '0:0:0:0:0:0:0:1', '607');
INSERT INTO `login_log` VALUES ('1', '2018-09-15 13:54:18', '0:0:0:0:0:0:0:1', '608');
INSERT INTO `login_log` VALUES ('1', '2018-09-15 14:04:47', '0:0:0:0:0:0:0:1', '609');
INSERT INTO `login_log` VALUES ('1', '2018-09-15 14:48:28', '0:0:0:0:0:0:0:1', '610');
INSERT INTO `login_log` VALUES ('1', '2018-09-15 15:45:11', '0:0:0:0:0:0:0:1', '611');
INSERT INTO `login_log` VALUES ('1', '2018-09-16 18:19:53', '0:0:0:0:0:0:0:1', '612');
INSERT INTO `login_log` VALUES ('1', '2018-09-16 18:20:36', '0:0:0:0:0:0:0:1', '613');
INSERT INTO `login_log` VALUES ('1', '2018-09-16 18:31:13', '0:0:0:0:0:0:0:1', '614');
INSERT INTO `login_log` VALUES ('1', '2018-09-16 19:21:08', '0:0:0:0:0:0:0:1', '615');
INSERT INTO `login_log` VALUES ('1', '2018-09-16 20:16:33', '0:0:0:0:0:0:0:1', '616');
INSERT INTO `login_log` VALUES ('1', '2018-09-16 20:58:47', '0:0:0:0:0:0:0:1', '617');
INSERT INTO `login_log` VALUES ('1', '2018-09-16 22:41:51', '0:0:0:0:0:0:0:1', '618');
INSERT INTO `login_log` VALUES ('1', '2018-09-17 09:18:20', '0:0:0:0:0:0:0:1', '619');
INSERT INTO `login_log` VALUES ('1', '2018-09-17 10:20:29', '0:0:0:0:0:0:0:1', '620');
INSERT INTO `login_log` VALUES ('1', '2018-09-17 12:53:42', '0:0:0:0:0:0:0:1', '621');
INSERT INTO `login_log` VALUES ('1', '2018-09-17 15:14:33', '0:0:0:0:0:0:0:1', '622');
INSERT INTO `login_log` VALUES ('1', '2018-09-17 15:27:41', '0:0:0:0:0:0:0:1', '623');
INSERT INTO `login_log` VALUES ('1', '2018-09-17 16:51:04', '0:0:0:0:0:0:0:1', '624');
INSERT INTO `login_log` VALUES ('1', '2018-09-17 17:49:53', '0:0:0:0:0:0:0:1', '625');
INSERT INTO `login_log` VALUES ('1', '2018-09-17 19:49:44', '0:0:0:0:0:0:0:1', '626');
INSERT INTO `login_log` VALUES ('1', '2018-09-17 21:15:07', '0:0:0:0:0:0:0:1', '627');
INSERT INTO `login_log` VALUES ('1', '2018-09-17 22:20:22', '0:0:0:0:0:0:0:1', '628');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 09:06:33', '0:0:0:0:0:0:0:1', '629');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 10:07:51', '0:0:0:0:0:0:0:1', '630');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 10:49:48', '0:0:0:0:0:0:0:1', '631');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 10:52:04', '0:0:0:0:0:0:0:1', '632');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 11:28:50', '0:0:0:0:0:0:0:1', '633');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 13:31:39', '0:0:0:0:0:0:0:1', '634');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 13:42:36', '0:0:0:0:0:0:0:1', '635');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 13:45:03', '0:0:0:0:0:0:0:1', '636');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 14:14:20', '0:0:0:0:0:0:0:1', '637');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 14:14:59', '0:0:0:0:0:0:0:1', '638');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 14:22:07', '0:0:0:0:0:0:0:1', '639');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 14:46:57', '0:0:0:0:0:0:0:1', '640');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 15:12:18', '0:0:0:0:0:0:0:1', '641');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 15:22:05', '0:0:0:0:0:0:0:1', '642');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 15:49:53', '0:0:0:0:0:0:0:1', '643');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 17:50:04', '0:0:0:0:0:0:0:1', '644');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 18:53:37', '0:0:0:0:0:0:0:1', '645');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 18:59:34', '0:0:0:0:0:0:0:1', '646');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 20:27:22', '0:0:0:0:0:0:0:1', '647');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 21:09:03', '0:0:0:0:0:0:0:1', '648');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 23:01:12', '0:0:0:0:0:0:0:1', '649');
INSERT INTO `login_log` VALUES ('1', '2018-09-18 23:04:40', '0:0:0:0:0:0:0:1', '650');
INSERT INTO `login_log` VALUES ('1', '2018-09-19 07:42:27', '0:0:0:0:0:0:0:1', '651');
INSERT INTO `login_log` VALUES ('1', '2018-09-19 08:16:03', '127.0.0.1', '652');
INSERT INTO `login_log` VALUES ('1', '2018-09-19 08:40:48', '0:0:0:0:0:0:0:1', '653');
INSERT INTO `login_log` VALUES ('1', '2018-09-19 09:20:56', '0:0:0:0:0:0:0:1', '654');
INSERT INTO `login_log` VALUES ('1', '2018-09-19 09:46:41', '0:0:0:0:0:0:0:1', '655');
INSERT INTO `login_log` VALUES ('1', '2018-09-19 09:48:58', '0:0:0:0:0:0:0:1', '656');
INSERT INTO `login_log` VALUES ('1', '2018-09-19 10:26:32', '0:0:0:0:0:0:0:1', '657');
INSERT INTO `login_log` VALUES ('1', '2018-09-19 10:28:17', '0:0:0:0:0:0:0:1', '658');
INSERT INTO `login_log` VALUES ('1', '2018-09-19 10:33:05', '0:0:0:0:0:0:0:1', '659');
INSERT INTO `login_log` VALUES ('1', '2018-09-19 11:02:02', '0:0:0:0:0:0:0:1', '660');
INSERT INTO `login_log` VALUES ('1', '2018-09-19 11:26:03', '0:0:0:0:0:0:0:1', '661');
INSERT INTO `login_log` VALUES ('1', '2018-09-20 10:32:35', '0:0:0:0:0:0:0:1', '662');
INSERT INTO `login_log` VALUES ('1', '2018-09-20 12:33:33', '0:0:0:0:0:0:0:1', '663');
INSERT INTO `login_log` VALUES ('1', '2018-09-20 13:02:00', '0:0:0:0:0:0:0:1', '664');
INSERT INTO `login_log` VALUES ('1', '2018-09-21 09:28:18', '0:0:0:0:0:0:0:1', '665');
INSERT INTO `login_log` VALUES ('1', '2018-09-21 21:32:55', '0:0:0:0:0:0:0:1', '666');
INSERT INTO `login_log` VALUES ('1', '2018-09-21 23:08:55', '0:0:0:0:0:0:0:1', '667');
INSERT INTO `login_log` VALUES ('1', '2018-09-21 23:56:40', '0:0:0:0:0:0:0:1', '668');
INSERT INTO `login_log` VALUES ('1', '2018-09-22 07:50:56', '0:0:0:0:0:0:0:1', '669');
INSERT INTO `login_log` VALUES ('1', '2018-09-22 08:01:19', '0:0:0:0:0:0:0:1', '670');
INSERT INTO `login_log` VALUES ('1', '2018-09-22 10:49:46', '0:0:0:0:0:0:0:1', '671');
INSERT INTO `login_log` VALUES ('1', '2018-09-22 10:51:02', '0:0:0:0:0:0:0:1', '672');
INSERT INTO `login_log` VALUES ('1', '2018-09-22 11:09:26', '0:0:0:0:0:0:0:1', '673');
INSERT INTO `login_log` VALUES ('1', '2018-09-22 12:39:43', '0:0:0:0:0:0:0:1', '674');
INSERT INTO `login_log` VALUES ('1', '2018-09-22 13:17:01', '0:0:0:0:0:0:0:1', '675');
INSERT INTO `login_log` VALUES ('1', '2018-09-22 14:02:51', '0:0:0:0:0:0:0:1', '676');
INSERT INTO `login_log` VALUES ('1', '2018-09-22 14:12:02', '0:0:0:0:0:0:0:1', '677');
INSERT INTO `login_log` VALUES ('1', '2018-09-22 14:57:02', '0:0:0:0:0:0:0:1', '678');
INSERT INTO `login_log` VALUES ('1', '2018-09-22 15:03:04', '0:0:0:0:0:0:0:1', '679');
INSERT INTO `login_log` VALUES ('1', '2018-09-22 16:29:38', '0:0:0:0:0:0:0:1', '680');
INSERT INTO `login_log` VALUES ('1', '2018-09-22 17:20:17', '0:0:0:0:0:0:0:1', '681');
INSERT INTO `login_log` VALUES ('1', '2018-09-22 18:27:19', '0:0:0:0:0:0:0:1', '682');
INSERT INTO `login_log` VALUES ('1', '2018-09-22 18:28:23', '0:0:0:0:0:0:0:1', '683');
INSERT INTO `login_log` VALUES ('1', '2018-09-24 11:46:50', '0:0:0:0:0:0:0:1', '684');
INSERT INTO `login_log` VALUES ('1', '2018-09-24 17:10:54', '0:0:0:0:0:0:0:1', '685');
INSERT INTO `login_log` VALUES ('1', '2018-09-24 18:39:25', '0:0:0:0:0:0:0:1', '686');
INSERT INTO `login_log` VALUES ('1', '2018-09-24 20:26:48', '0:0:0:0:0:0:0:1', '687');
INSERT INTO `login_log` VALUES ('1', '2018-09-24 21:47:54', '0:0:0:0:0:0:0:1', '688');
INSERT INTO `login_log` VALUES ('1', '2018-09-24 22:43:41', '0:0:0:0:0:0:0:1', '689');
INSERT INTO `login_log` VALUES ('1', '2018-09-24 23:02:52', '0:0:0:0:0:0:0:1', '690');
INSERT INTO `login_log` VALUES ('1', '2018-09-24 23:35:31', '0:0:0:0:0:0:0:1', '691');
INSERT INTO `login_log` VALUES ('1', '2018-09-25 01:00:31', '0:0:0:0:0:0:0:1', '692');
INSERT INTO `login_log` VALUES ('1', '2018-09-25 05:46:35', '0:0:0:0:0:0:0:1', '693');
INSERT INTO `login_log` VALUES ('1', '2018-09-25 07:36:51', '0:0:0:0:0:0:0:1', '694');
INSERT INTO `login_log` VALUES ('1', '2018-09-30 09:51:06', '0:0:0:0:0:0:0:1', '695');
INSERT INTO `login_log` VALUES ('1', '2018-09-30 10:34:49', '0:0:0:0:0:0:0:1', '696');
INSERT INTO `login_log` VALUES ('1', '2018-09-30 11:18:56', '0:0:0:0:0:0:0:1', '697');
INSERT INTO `login_log` VALUES ('1', '2018-09-30 12:18:30', '0:0:0:0:0:0:0:1', '698');
INSERT INTO `login_log` VALUES ('1', '2018-09-30 12:20:48', '0:0:0:0:0:0:0:1', '699');
INSERT INTO `login_log` VALUES ('1', '2018-09-30 12:30:03', '0:0:0:0:0:0:0:1', '700');
INSERT INTO `login_log` VALUES ('1', '2018-09-30 12:32:31', '0:0:0:0:0:0:0:1', '701');
INSERT INTO `login_log` VALUES ('1', '2018-09-30 13:07:14', '0:0:0:0:0:0:0:1', '702');
INSERT INTO `login_log` VALUES ('1', '2018-09-30 13:27:44', '0:0:0:0:0:0:0:1', '703');
INSERT INTO `login_log` VALUES ('1', '2018-09-30 14:06:21', '0:0:0:0:0:0:0:1', '704');
INSERT INTO `login_log` VALUES ('1', '2018-09-30 15:45:48', '0:0:0:0:0:0:0:1', '705');
INSERT INTO `login_log` VALUES ('1', '2018-09-30 16:37:03', '0:0:0:0:0:0:0:1', '706');
INSERT INTO `login_log` VALUES ('1', '2018-10-02 11:34:05', '0:0:0:0:0:0:0:1', '707');
INSERT INTO `login_log` VALUES ('1', '2018-10-02 14:56:28', '0:0:0:0:0:0:0:1', '708');
INSERT INTO `login_log` VALUES ('1', '2018-10-02 19:43:58', '0:0:0:0:0:0:0:1', '709');
INSERT INTO `login_log` VALUES ('1', '2018-10-03 14:15:38', '0:0:0:0:0:0:0:1', '710');
INSERT INTO `login_log` VALUES ('1', '2018-10-05 07:04:00', '0:0:0:0:0:0:0:1', '711');
INSERT INTO `login_log` VALUES ('1', '2018-10-05 12:03:22', '0:0:0:0:0:0:0:1', '712');
INSERT INTO `login_log` VALUES ('1', '2018-10-05 15:07:34', '0:0:0:0:0:0:0:1', '713');
INSERT INTO `login_log` VALUES ('1', '2018-10-05 15:10:44', '0:0:0:0:0:0:0:1', '714');
INSERT INTO `login_log` VALUES ('1', '2018-10-05 16:36:02', '0:0:0:0:0:0:0:1', '715');
INSERT INTO `login_log` VALUES ('1', '2018-10-05 17:56:38', '0:0:0:0:0:0:0:1', '716');
INSERT INTO `login_log` VALUES ('1', '2018-10-05 23:22:20', '0:0:0:0:0:0:0:1', '717');
INSERT INTO `login_log` VALUES ('1', '2018-10-06 09:16:53', '0:0:0:0:0:0:0:1', '718');
INSERT INTO `login_log` VALUES ('1', '2018-10-06 09:40:46', '0:0:0:0:0:0:0:1', '719');
INSERT INTO `login_log` VALUES ('1', '2018-10-06 12:33:54', '0:0:0:0:0:0:0:1', '720');
INSERT INTO `login_log` VALUES ('1', '2018-10-06 12:53:03', '0:0:0:0:0:0:0:1', '721');
INSERT INTO `login_log` VALUES ('1', '2018-10-06 12:53:59', '0:0:0:0:0:0:0:1', '722');
INSERT INTO `login_log` VALUES ('1', '2018-10-06 13:42:01', '0:0:0:0:0:0:0:1', '723');
INSERT INTO `login_log` VALUES ('1', '2018-10-06 15:47:56', '0:0:0:0:0:0:0:1', '724');
INSERT INTO `login_log` VALUES ('1', '2018-10-06 16:39:22', '0:0:0:0:0:0:0:1', '725');
INSERT INTO `login_log` VALUES ('1', '2018-10-06 18:30:32', '0:0:0:0:0:0:0:1', '726');
INSERT INTO `login_log` VALUES ('1', '2018-10-06 21:34:08', '0:0:0:0:0:0:0:1', '727');
INSERT INTO `login_log` VALUES ('1', '2018-10-06 23:25:40', '0:0:0:0:0:0:0:1', '728');
INSERT INTO `login_log` VALUES ('1', '2018-10-07 10:13:08', '0:0:0:0:0:0:0:1', '729');
INSERT INTO `login_log` VALUES ('1', '2018-10-09 09:48:32', '0:0:0:0:0:0:0:1', '730');
INSERT INTO `login_log` VALUES ('1', '2018-10-09 10:34:54', '0:0:0:0:0:0:0:1', '731');
INSERT INTO `login_log` VALUES ('1', '2018-10-09 16:13:09', '0:0:0:0:0:0:0:1', '732');
INSERT INTO `login_log` VALUES ('1', '2018-10-09 18:25:28', '0:0:0:0:0:0:0:1', '733');
INSERT INTO `login_log` VALUES ('1', '2018-10-09 18:35:05', '0:0:0:0:0:0:0:1', '734');
INSERT INTO `login_log` VALUES ('1', '2018-10-09 18:46:04', '0:0:0:0:0:0:0:1', '735');
INSERT INTO `login_log` VALUES ('1', '2018-10-09 18:52:51', '0:0:0:0:0:0:0:1', '736');
INSERT INTO `login_log` VALUES ('1', '2018-10-09 19:07:36', '0:0:0:0:0:0:0:1', '737');
INSERT INTO `login_log` VALUES ('1', '2018-10-09 20:04:40', '0:0:0:0:0:0:0:1', '738');
INSERT INTO `login_log` VALUES ('1', '2018-10-09 20:22:44', '0:0:0:0:0:0:0:1', '739');
INSERT INTO `login_log` VALUES ('1', '2018-10-09 20:53:54', '0:0:0:0:0:0:0:1', '740');
INSERT INTO `login_log` VALUES ('1', '2018-10-09 21:43:14', '0:0:0:0:0:0:0:1', '741');
INSERT INTO `login_log` VALUES ('1', '2018-10-10 08:03:15', '0:0:0:0:0:0:0:1', '742');
INSERT INTO `login_log` VALUES ('1', '2018-10-10 09:57:38', '0:0:0:0:0:0:0:1', '743');
INSERT INTO `login_log` VALUES ('1', '2018-10-10 10:12:27', '0:0:0:0:0:0:0:1', '744');
INSERT INTO `login_log` VALUES ('1', '2018-10-10 10:22:55', '0:0:0:0:0:0:0:1', '745');
INSERT INTO `login_log` VALUES ('1', '2018-10-10 12:22:40', '0:0:0:0:0:0:0:1', '746');
INSERT INTO `login_log` VALUES ('1', '2018-10-10 15:58:46', '223.104.64.24', '747');
INSERT INTO `login_log` VALUES ('1', '2018-10-10 16:36:50', '223.104.64.24', '748');
INSERT INTO `login_log` VALUES ('1', '2018-10-10 17:38:22', '119.128.172.31', '749');
INSERT INTO `login_log` VALUES ('1', '2018-10-10 19:14:21', '119.128.172.31', '750');
INSERT INTO `login_log` VALUES ('1', '2018-10-10 20:09:24', '119.128.172.31', '751');
INSERT INTO `login_log` VALUES ('1', '2018-10-11 08:25:40', '119.128.172.127', '752');
INSERT INTO `login_log` VALUES ('1', '2018-10-11 08:33:43', '119.128.172.127', '753');
INSERT INTO `login_log` VALUES ('1', '2018-10-11 08:58:52', '119.128.172.127', '754');
INSERT INTO `login_log` VALUES ('1', '2018-10-11 09:14:04', '119.128.172.127', '755');
INSERT INTO `login_log` VALUES ('1', '2018-10-11 09:53:48', '119.128.172.127', '756');
INSERT INTO `login_log` VALUES ('1', '2018-10-11 14:10:09', '0:0:0:0:0:0:0:1', '757');
INSERT INTO `login_log` VALUES ('1', '2018-10-11 15:01:21', '0:0:0:0:0:0:0:1', '758');
INSERT INTO `login_log` VALUES ('1', '2018-10-11 23:22:34', '0:0:0:0:0:0:0:1', '759');
INSERT INTO `login_log` VALUES ('1', '2018-10-12 01:03:59', '0:0:0:0:0:0:0:1', '760');
INSERT INTO `login_log` VALUES ('1', '2018-10-12 07:47:00', '0:0:0:0:0:0:0:1', '761');
INSERT INTO `login_log` VALUES ('1', '2018-10-12 08:19:44', '220.112.13.6', '762');
INSERT INTO `login_log` VALUES ('1', '2018-10-12 08:30:57', '119.128.173.66', '763');
INSERT INTO `login_log` VALUES ('13', '2018-10-12 09:42:21', '113.77.86.145', '764');
INSERT INTO `login_log` VALUES ('13', '2018-10-12 09:42:36', '113.77.86.145', '765');
INSERT INTO `login_log` VALUES ('11', '2018-10-12 09:43:09', '113.77.86.145', '766');
INSERT INTO `login_log` VALUES ('7', '2018-10-12 09:43:36', '113.77.86.145', '767');
INSERT INTO `login_log` VALUES ('8', '2018-10-12 09:43:42', '113.77.86.145', '768');
INSERT INTO `login_log` VALUES ('12', '2018-10-12 09:43:56', '113.77.86.145', '769');
INSERT INTO `login_log` VALUES ('9', '2018-10-12 09:44:02', '113.77.86.145', '770');
INSERT INTO `login_log` VALUES ('4', '2018-10-12 09:47:02', '113.77.86.145', '771');
INSERT INTO `login_log` VALUES ('4', '2018-10-12 09:49:34', '220.112.13.6', '772');
INSERT INTO `login_log` VALUES ('1', '2018-10-12 09:52:37', '220.112.13.6', '773');
INSERT INTO `login_log` VALUES ('1', '2018-10-12 09:53:10', '113.77.86.145', '774');
INSERT INTO `login_log` VALUES ('1', '2018-10-12 10:23:12', '220.112.13.6', '775');
INSERT INTO `login_log` VALUES ('1', '2018-10-12 10:55:02', '220.112.13.6', '776');
INSERT INTO `login_log` VALUES ('1', '2018-10-12 11:33:25', '220.112.13.6', '777');
INSERT INTO `login_log` VALUES ('1', '2018-10-12 13:26:56', '113.77.87.90', '778');
INSERT INTO `login_log` VALUES ('12', '2018-10-12 14:07:41', '113.77.87.90', '779');
INSERT INTO `login_log` VALUES ('1', '2018-10-12 14:07:45', '113.77.87.90', '780');
INSERT INTO `login_log` VALUES ('7', '2018-10-12 14:07:59', '113.77.87.90', '781');
INSERT INTO `login_log` VALUES ('1', '2018-10-12 14:09:24', '113.77.87.90', '782');
INSERT INTO `login_log` VALUES ('1', '2018-10-12 14:11:16', '113.77.87.90', '783');
INSERT INTO `login_log` VALUES ('7', '2018-10-12 14:11:20', '113.77.87.90', '784');
INSERT INTO `login_log` VALUES ('1', '2018-10-12 14:13:09', '113.77.87.90', '785');
INSERT INTO `login_log` VALUES ('7', '2018-10-12 14:13:39', '113.77.87.90', '786');
INSERT INTO `login_log` VALUES ('6', '2018-10-12 14:15:10', '113.77.87.90', '787');
INSERT INTO `login_log` VALUES ('1', '2018-10-12 14:16:31', '113.77.87.90', '788');
INSERT INTO `login_log` VALUES ('1', '2018-10-12 14:53:27', '220.112.13.6', '789');
INSERT INTO `login_log` VALUES ('1', '2018-10-13 00:55:55', '220.112.13.6', '790');
INSERT INTO `login_log` VALUES ('1', '2018-10-13 10:06:27', '119.128.172.246', '791');
INSERT INTO `login_log` VALUES ('1', '2018-10-13 11:05:15', '113.77.86.168', '792');
INSERT INTO `login_log` VALUES ('1', '2018-10-14 21:21:08', '220.112.13.6', '793');
INSERT INTO `login_log` VALUES ('1', '2018-10-15 08:56:44', '220.112.13.6', '794');
INSERT INTO `login_log` VALUES ('1', '2018-10-15 20:48:33', '220.112.13.6', '795');
INSERT INTO `login_log` VALUES ('1', '2018-10-15 20:48:33', '220.112.13.6', '796');
INSERT INTO `login_log` VALUES ('1', '2018-10-15 20:48:33', '220.112.13.6', '797');
INSERT INTO `login_log` VALUES ('1', '2018-10-15 20:48:41', '220.112.13.6', '798');
INSERT INTO `login_log` VALUES ('1', '2018-10-16 08:00:34', '119.128.172.206', '799');
INSERT INTO `login_log` VALUES ('1', '2018-10-16 09:35:13', '119.128.172.206', '800');
INSERT INTO `login_log` VALUES ('1', '2018-10-16 09:39:34', '220.112.13.6', '801');
INSERT INTO `login_log` VALUES ('1', '2018-10-16 11:01:53', '119.128.172.206', '802');
INSERT INTO `login_log` VALUES ('1', '2018-10-16 11:03:13', '119.128.172.206', '803');
INSERT INTO `login_log` VALUES ('1', '2018-10-16 12:56:39', '119.128.172.206', '804');
INSERT INTO `login_log` VALUES ('1', '2018-10-16 14:07:30', '119.128.172.206', '805');
INSERT INTO `login_log` VALUES ('1', '2018-10-18 10:29:22', '113.77.87.49', '806');
INSERT INTO `login_log` VALUES ('1', '2018-10-18 10:45:07', '220.112.15.130', '807');
INSERT INTO `login_log` VALUES ('1', '2018-10-18 11:38:32', '220.112.15.130', '808');
INSERT INTO `login_log` VALUES ('1', '2018-10-18 17:44:25', '113.77.84.61', '809');
INSERT INTO `login_log` VALUES ('1', '2018-10-19 09:16:26', '113.77.84.237', '810');
INSERT INTO `login_log` VALUES ('1', '2018-10-19 10:09:28', '119.128.172.253', '811');
INSERT INTO `login_log` VALUES ('1', '2018-10-19 16:45:36', '113.77.84.128', '812');
INSERT INTO `login_log` VALUES ('1', '2018-10-20 01:26:06', '220.112.15.130', '813');
INSERT INTO `login_log` VALUES ('1', '2018-10-20 09:56:45', '113.77.84.233', '814');
INSERT INTO `login_log` VALUES ('1', '2018-10-20 12:33:44', '113.77.86.129', '815');
INSERT INTO `login_log` VALUES ('1', '2018-10-20 13:56:59', '113.77.85.6', '816');
INSERT INTO `login_log` VALUES ('1', '2018-10-20 16:19:33', '117.136.79.133', '817');
INSERT INTO `login_log` VALUES ('1', '2018-10-20 17:09:53', '113.77.85.23', '818');
INSERT INTO `login_log` VALUES ('1', '2018-10-24 11:38:48', '113.77.84.167', '819');
INSERT INTO `login_log` VALUES ('1', '2018-10-26 00:26:27', '220.112.15.130', '820');
INSERT INTO `login_log` VALUES ('1', '2018-10-26 11:44:33', '113.77.87.227', '821');
INSERT INTO `login_log` VALUES ('1', '2018-10-26 16:57:16', '113.77.87.227', '822');
INSERT INTO `login_log` VALUES ('1', '2018-10-26 17:04:11', '113.77.87.227', '823');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 11:52:01', '113.77.85.214', '824');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 11:57:41', '113.77.85.214', '825');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 12:50:09', '113.77.85.214', '826');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 12:50:21', '113.77.85.214', '827');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 13:29:57', '113.77.85.214', '828');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 13:33:27', '113.77.85.214', '829');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 13:42:11', '113.77.85.214', '830');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 14:10:17', '113.77.85.214', '831');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 14:10:17', '113.77.85.214', '832');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 14:10:17', '113.77.85.214', '833');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 14:16:57', '113.77.85.214', '834');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 15:19:20', '0:0:0:0:0:0:0:1', '835');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 15:22:20', '0:0:0:0:0:0:0:1', '836');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 15:28:21', '113.77.85.214', '837');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 16:05:24', '0:0:0:0:0:0:0:1', '838');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 16:11:19', '113.77.85.214', '839');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 16:11:19', '113.77.85.214', '840');
INSERT INTO `login_log` VALUES ('1', '2018-10-27 16:11:19', '113.77.85.214', '841');
INSERT INTO `login_log` VALUES ('1', '2018-10-28 17:10:12', '180.91.136.166', '842');
INSERT INTO `login_log` VALUES ('1', '2018-10-28 17:31:06', '0:0:0:0:0:0:0:1', '843');
INSERT INTO `login_log` VALUES ('1', '2018-10-28 19:31:30', '0:0:0:0:0:0:0:1', '844');
INSERT INTO `login_log` VALUES ('1', '2018-10-28 21:06:35', '0:0:0:0:0:0:0:1', '845');
INSERT INTO `login_log` VALUES ('1', '2018-10-28 21:10:42', '0:0:0:0:0:0:0:1', '846');
INSERT INTO `login_log` VALUES ('1', '2018-10-28 21:25:02', '0:0:0:0:0:0:0:1', '847');
INSERT INTO `login_log` VALUES ('1', '2018-10-28 21:25:40', '0:0:0:0:0:0:0:1', '848');
INSERT INTO `login_log` VALUES ('1', '2018-10-29 00:15:20', '180.91.136.166', '849');
INSERT INTO `login_log` VALUES ('1', '2018-10-29 07:59:51', '113.77.86.198', '850');
INSERT INTO `login_log` VALUES ('1', '2018-10-29 08:34:31', '113.77.86.198', '851');
INSERT INTO `login_log` VALUES ('1', '2018-10-29 08:52:41', '113.77.86.198', '852');
INSERT INTO `login_log` VALUES ('1', '2018-10-29 13:30:32', '113.77.86.198', '853');
INSERT INTO `login_log` VALUES ('1', '2018-10-29 15:39:34', '113.77.86.198', '854');
INSERT INTO `login_log` VALUES ('1', '2018-10-29 19:36:51', '113.77.84.20', '855');
INSERT INTO `login_log` VALUES ('7', '2018-10-29 20:09:17', '113.77.84.20', '856');
INSERT INTO `login_log` VALUES ('8', '2018-10-29 20:09:23', '113.77.84.20', '857');
INSERT INTO `login_log` VALUES ('13', '2018-10-29 20:09:27', '113.77.84.20', '858');
INSERT INTO `login_log` VALUES ('10', '2018-10-29 20:09:31', '113.77.84.20', '859');
INSERT INTO `login_log` VALUES ('6', '2018-10-29 20:09:35', '113.77.84.20', '860');
INSERT INTO `login_log` VALUES ('12', '2018-10-29 20:09:38', '113.77.84.20', '861');
INSERT INTO `login_log` VALUES ('11', '2018-10-29 20:09:42', '113.77.84.20', '862');
INSERT INTO `login_log` VALUES ('6', '2018-10-29 20:09:45', '113.77.84.20', '863');
INSERT INTO `login_log` VALUES ('11', '2018-10-29 20:09:49', '113.77.84.20', '864');
INSERT INTO `login_log` VALUES ('11', '2018-10-29 20:10:11', '113.77.84.20', '865');
INSERT INTO `login_log` VALUES ('1', '2018-10-29 20:10:45', '180.91.136.166', '866');
INSERT INTO `login_log` VALUES ('1', '2018-10-29 20:12:17', '113.77.84.20', '867');
INSERT INTO `login_log` VALUES ('1', '2018-10-30 01:35:31', '180.91.136.166', '868');
INSERT INTO `login_log` VALUES ('1', '2018-10-30 08:33:59', '113.77.84.20', '869');
INSERT INTO `login_log` VALUES ('1', '2018-10-30 08:34:01', '180.91.136.166', '870');
INSERT INTO `login_log` VALUES ('1', '2018-10-30 10:09:10', '117.136.39.80', '871');
INSERT INTO `login_log` VALUES ('1', '2018-10-30 10:26:08', '113.77.84.20', '872');
INSERT INTO `login_log` VALUES ('1', '2018-10-30 11:16:51', '117.136.39.80', '873');
INSERT INTO `login_log` VALUES ('1', '2018-10-30 11:36:59', '113.77.84.20', '874');
INSERT INTO `login_log` VALUES ('10', '2018-10-30 12:01:27', '113.77.84.20', '875');
INSERT INTO `login_log` VALUES ('12', '2018-10-30 12:02:43', '113.77.84.20', '876');
INSERT INTO `login_log` VALUES ('10', '2018-10-30 12:04:10', '113.77.84.20', '877');
INSERT INTO `login_log` VALUES ('1', '2018-10-30 12:06:08', '113.77.84.20', '878');
INSERT INTO `login_log` VALUES ('1', '2018-10-30 13:27:06', '113.77.84.20', '879');
INSERT INTO `login_log` VALUES ('10', '2018-10-30 13:35:23', '113.77.84.20', '880');
INSERT INTO `login_log` VALUES ('1', '2018-10-30 13:35:41', '113.77.84.20', '881');
INSERT INTO `login_log` VALUES ('10', '2018-10-30 13:36:37', '113.77.84.20', '882');
INSERT INTO `login_log` VALUES ('9', '2018-10-30 13:57:56', '113.77.84.20', '883');
INSERT INTO `login_log` VALUES ('8', '2018-10-30 13:59:04', '113.77.84.20', '884');
INSERT INTO `login_log` VALUES ('6', '2018-10-30 14:00:12', '113.77.84.20', '885');
INSERT INTO `login_log` VALUES ('1', '2018-10-30 14:00:50', '113.77.84.20', '886');
INSERT INTO `login_log` VALUES ('5', '2018-10-30 14:01:07', '113.77.84.20', '887');
INSERT INTO `login_log` VALUES ('7', '2018-10-30 14:01:41', '113.77.84.20', '888');

-- ----------------------------
-- Table structure for `order_upload_log`
-- ----------------------------
DROP TABLE IF EXISTS `order_upload_log`;
CREATE TABLE `order_upload_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sucess` int(11) DEFAULT NULL COMMENT '成功条数',
  `error` int(11) DEFAULT NULL,
  `reson` varchar(300) DEFAULT NULL COMMENT '原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_upload_log
-- ----------------------------
INSERT INTO `order_upload_log` VALUES ('28', '13', null, null);
INSERT INTO `order_upload_log` VALUES ('29', '13', null, null);
INSERT INTO `order_upload_log` VALUES ('30', '13', null, null);
INSERT INTO `order_upload_log` VALUES ('31', '13', null, null);
INSERT INTO `order_upload_log` VALUES ('32', '13', null, null);
INSERT INTO `order_upload_log` VALUES ('33', '47', null, null);
INSERT INTO `order_upload_log` VALUES ('34', '47', null, null);
INSERT INTO `order_upload_log` VALUES ('35', '47', null, null);
INSERT INTO `order_upload_log` VALUES ('36', '13', null, null);
INSERT INTO `order_upload_log` VALUES ('37', '47', null, null);
INSERT INTO `order_upload_log` VALUES ('38', '47', null, null);
INSERT INTO `order_upload_log` VALUES ('39', '47', null, null);
INSERT INTO `order_upload_log` VALUES ('40', '3', null, null);
INSERT INTO `order_upload_log` VALUES ('41', '3', null, null);
INSERT INTO `order_upload_log` VALUES ('42', '3', null, null);
INSERT INTO `order_upload_log` VALUES ('43', '8', null, null);
INSERT INTO `order_upload_log` VALUES ('44', '47', null, null);
INSERT INTO `order_upload_log` VALUES ('45', '10', null, null);
INSERT INTO `order_upload_log` VALUES ('46', '0', null, null);
INSERT INTO `order_upload_log` VALUES ('47', '0', null, null);
INSERT INTO `order_upload_log` VALUES ('48', '0', null, null);
INSERT INTO `order_upload_log` VALUES ('49', '0', null, null);
INSERT INTO `order_upload_log` VALUES ('50', '0', null, null);
INSERT INTO `order_upload_log` VALUES ('51', '8', null, null);
INSERT INTO `order_upload_log` VALUES ('52', '47', null, null);
INSERT INTO `order_upload_log` VALUES ('53', '3', null, null);
INSERT INTO `order_upload_log` VALUES ('54', '1', null, null);
INSERT INTO `order_upload_log` VALUES ('55', '1', null, null);
INSERT INTO `order_upload_log` VALUES ('56', '3', null, null);
INSERT INTO `order_upload_log` VALUES ('57', '1', null, null);
INSERT INTO `order_upload_log` VALUES ('58', '1', null, null);
INSERT INTO `order_upload_log` VALUES ('59', '3', null, null);
INSERT INTO `order_upload_log` VALUES ('60', '1824', null, null);
INSERT INTO `order_upload_log` VALUES ('61', '0', null, null);
INSERT INTO `order_upload_log` VALUES ('62', '188', null, null);
INSERT INTO `order_upload_log` VALUES ('63', '188', null, null);
INSERT INTO `order_upload_log` VALUES ('64', '188', null, null);
INSERT INTO `order_upload_log` VALUES ('65', '192', null, null);
INSERT INTO `order_upload_log` VALUES ('66', '192', null, null);
INSERT INTO `order_upload_log` VALUES ('67', '18', null, null);
INSERT INTO `order_upload_log` VALUES ('68', '192', null, null);
INSERT INTO `order_upload_log` VALUES ('69', '9', null, null);
INSERT INTO `order_upload_log` VALUES ('70', '192', null, null);
INSERT INTO `order_upload_log` VALUES ('71', '36', null, null);
INSERT INTO `order_upload_log` VALUES ('72', '36', null, null);
INSERT INTO `order_upload_log` VALUES ('73', '9', null, null);
INSERT INTO `order_upload_log` VALUES ('74', '2', null, null);
INSERT INTO `order_upload_log` VALUES ('75', '192', null, null);
INSERT INTO `order_upload_log` VALUES ('76', '9', null, null);

-- ----------------------------
-- Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(30) DEFAULT NULL COMMENT '注解使用的key',
  `remark` varchar(1024) DEFAULT NULL,
  `actionKey` varchar(512) NOT NULL DEFAULT '',
  `controller` varchar(512) NOT NULL DEFAULT '',
  `parentId` int(11) DEFAULT '0',
  `isfy` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=356 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('250', 'accout', '用户首页', '/fy/admin/account', 'AccountController', '0', '');
INSERT INTO `permission` VALUES ('251', 'accout_update', '修改账户', '/fy/admin/account/update', 'AccountController', '0', '');
INSERT INTO `permission` VALUES ('252', 'accout_delete', '删除账号', '/fy/admin/account/delete', 'AccountController', '0', '');
INSERT INTO `permission` VALUES ('253', 'accout_create', '添加用户', '/fy/admin/account/save', 'AccountController', '0', '');
INSERT INTO `permission` VALUES ('254', 'role', '角色首页', '/fy/admin/role', 'role.RoleAdminController', '0', '');
INSERT INTO `permission` VALUES ('255', 'role_update', '修改角色', '/fy/admin/role/update', 'role.RoleAdminController', '0', '');
INSERT INTO `permission` VALUES ('256', 'role_create', '添加角色', '/fy/admin/role/add', 'role.RoleAdminController', '0', '');
INSERT INTO `permission` VALUES ('257', 'role_delete', '删除角色', '/fy/admin/role/delete', 'role.RoleAdminController', '0', '');
INSERT INTO `permission` VALUES ('258', 'role_distribut', '分配角色', '/fy/admin/account/assignRoles', 'AccountController', '0', '');
INSERT INTO `permission` VALUES ('259', 'role_permission', '角色授权', '/fy/admin/role/assignPermissions', 'role.RoleAdminController', '0', '');
INSERT INTO `permission` VALUES ('260', 'department', '部门首页', '/fy/admin/base/department', 'DepartmentController', '0', '');
INSERT INTO `permission` VALUES ('261', 'department_update', '修改部门', '/fy/admin/base/department/update', 'DepartmentController', '0', '');
INSERT INTO `permission` VALUES ('262', 'department_create', '添加部门', '/fy/admin/base/department/save', 'DepartmentController', '0', '');
INSERT INTO `permission` VALUES ('263', 'department_delete', '删除部门', '/fy/admin/base/department/delete', 'DepartmentController', '0', '');
INSERT INTO `permission` VALUES ('264', 'customer', '客户首页', '/fy/admin/base/customer', 'CustomerController', '0', '');
INSERT INTO `permission` VALUES ('265', 'customer_update', '修改客户', '/fy/admin/base/customer/update', 'CustomerController', '0', '');
INSERT INTO `permission` VALUES ('266', 'customer_create', '添加客户', '/fy/admin/base/customer/save', 'CustomerController', '0', '');
INSERT INTO `permission` VALUES ('267', 'customer_delete', '删除客户', '/fy/admin/base/customer/delete', 'CustomerController', '0', '');
INSERT INTO `permission` VALUES ('268', 'category', '分类首页', '/fy/admin/base/category', 'CategoryController', '0', '');
INSERT INTO `permission` VALUES ('269', 'category_update', '修改分类', '/fy/admin/base/category/update', 'CategoryController', '0', '');
INSERT INTO `permission` VALUES ('270', 'category_create', '添加分类', '/fy/admin/base/category/save', 'CategoryController', '0', '');
INSERT INTO `permission` VALUES ('271', 'category_delete', '分类删除', '/fy/admin/base/category/delete', 'CategoryController', '0', '');
INSERT INTO `permission` VALUES ('272', 'file', '文件首页', '/fy/admin/base/file', 'FileController', '0', '');
INSERT INTO `permission` VALUES ('273', 'file_create', '新增文件', '/fy/admin/base/file/update', 'FileController', '0', '');
INSERT INTO `permission` VALUES ('274', 'file_delete', '删除文件', '/fy/admin/base/file/save', 'FileController', '0', '');
INSERT INTO `permission` VALUES ('275', 'person', '人员首页', '/fy/admin/base/person', 'PersonController', '0', '');
INSERT INTO `permission` VALUES ('276', 'person_update', '修改人员', '/fy/admin/base/person/update', 'PersonController', '0', '');
INSERT INTO `permission` VALUES ('277', 'person_create', '添加人员', '/fy/admin/base/person/save', 'PersonController', '0', '');
INSERT INTO `permission` VALUES ('278', 'person_delete', '删除人员', '/fy/admin/base/person/delete', 'PersonController', '0', '');
INSERT INTO `permission` VALUES ('279', 'supplier', '厂商首页', '/fy/admin/base/supplier', 'SupplierController', '0', '');
INSERT INTO `permission` VALUES ('280', 'supplier_update', '修改厂商', '/fy/admin/base/supplier/update', 'SupplierController', '0', '');
INSERT INTO `permission` VALUES ('281', 'supplier_create', '添加厂商', '/fy/admin/base/supplier/save', 'SupplierController', '0', '');
INSERT INTO `permission` VALUES ('282', 'supplier_delete', '删除厂商', '/fy/admin/base/supplier/delete', 'SupplierController', '0', '');
INSERT INTO `permission` VALUES ('283', 'taxRate', '税率首页', '/fy/admin/base/taxRate', 'TaxRateController', '0', '');
INSERT INTO `permission` VALUES ('284', 'taxRate_update', '修改税率', '/fy/admin/base/taxRate/update', 'TaxRateController', '0', '');
INSERT INTO `permission` VALUES ('285', 'taxRate_create', '添加税率', '/fy/admin/base/taxRate/save', 'TaxRateController', '0', '');
INSERT INTO `permission` VALUES ('286', 'taxRate_delete', '删除税率', '/fy/admin/base/taxRate/delete', 'TaxRateController', '0', '');
INSERT INTO `permission` VALUES ('287', 'unit', '单位首页', '/fy/admin/base/unit', 'UnitController', '0', '');
INSERT INTO `permission` VALUES ('288', 'unit_update', '修改单位', '/fy/admin/base/unit/update', 'UnitController', '0', '');
INSERT INTO `permission` VALUES ('289', 'update_create', '添加单位', '/fy/admin/base/unit/save', 'UnitController', '0', '');
INSERT INTO `permission` VALUES ('290', 'unit_delete', '删除单位', '/fy/admin/base/unit/delete', 'UnitController', '0', '');
INSERT INTO `permission` VALUES ('291', 'supplierCate', '厂商分类', '/fy/admin/base/supplierCate', 'SupplierCategoryController', '0', '');
INSERT INTO `permission` VALUES ('292', 'supplierCate_update', '厂商分类修改', '/fy/admin/base/supplierCate/update', 'SupplierCategoryController', '0', '');
INSERT INTO `permission` VALUES ('293', 'supplierCate_delete', '厂商分类修改', '/fy/admin/base/supplierCate/delete', 'SupplierCategoryController', '0', '');
INSERT INTO `permission` VALUES ('294', 'order', '订单首页', '/fy/admin/biz/fyorder/order2', 'OrderController2', '0', '');
INSERT INTO `permission` VALUES ('295', 'order_upload', '订单上传', '/fy/admin/biz/fyorder/uploadFile', 'OrderController2', '0', '');
INSERT INTO `permission` VALUES ('296', 'order_download', '订单下载', '/fy/admin/biz/fyorder/download', 'OrderController2', '0', '');
INSERT INTO `permission` VALUES ('297', 'order_delete', '订单删除', '/fy/admin/biz/fyorder/delete', 'OrderController2', '0', '');
INSERT INTO `permission` VALUES ('298', 'order_update', '订单更新', '/fy/admin/biz/fyorder/updateOrderDedeliveryDate', 'OrderController2', '0', '');
INSERT INTO `permission` VALUES ('299', 'order_delete_list', '被删除的订单', '/fy/admin/biz/fyorder/back', 'OrderDeleteBackController', '0', '');
INSERT INTO `permission` VALUES ('300', 'order_rollback', '订单恢复', '/fy/admin/biz/fyorder/backOrder', 'OrderDeleteBackController', '0', '');
INSERT INTO `permission` VALUES ('301', 'distribut', '分配表', '/fy/admin/biz/fyorder/distribute', 'DistributController', '0', '');
INSERT INTO `permission` VALUES ('302', 'distribut_order', '订单分配', '/fy/admin/biz/fyorder/distribute/distributeBatch', 'DistributController', '0', '');
INSERT INTO `permission` VALUES ('303', 'distribut_order_map', '订单关联图纸', '/fy/admin/biz/fyorder/distribute/updateOrderMapFile', 'DistributController', '0', '');
INSERT INTO `permission` VALUES ('304', 'distribut_download', '分配表下载', '/fy/admin/biz/fyorder/distribute/downloadDistribut', 'DistributController', '0', '');
INSERT INTO `permission` VALUES ('305', 'commision', '委外接收表', '/fy/admin/biz/commission/receive', 'ReceiveController', '0', '');
INSERT INTO `permission` VALUES ('306', 'commision_receive', '委外接收', '/fy/admin/biz/commission/receive/receive', 'ReceiveController', '0', '');
INSERT INTO `permission` VALUES ('307', 'execut', '委外执行表', '/fy/admin/biz/commission/execute', 'CommisionExecutController', '0', '');
INSERT INTO `permission` VALUES ('308', 'execut_download', '报目标下载', '/fy/admin/biz/commission/execute/downloadAskCost', 'CommisionExecutController', '0', '');
INSERT INTO `permission` VALUES ('309', 'execut_upload', '上传报目标', '/fy/admin/biz/commission/audit/uploadFile', 'FyPurchaseAuditController', '0', '');
INSERT INTO `permission` VALUES ('310', 'execut_to_audit', '流转审核', '/fy/admin/biz/commission/audit/batchUpdateStatus', 'FyPurchaseAuditController', '0', '');
INSERT INTO `permission` VALUES ('311', 'execut_update_cost', '执行表修改价格', '/fy/admin/biz/commission/execute/edit', 'CommisionExecutController', '0', '');
INSERT INTO `permission` VALUES ('312', 'audit', '采购审核', '/fy/admin/biz/commission/audit', 'FyPurchaseAuditController', '0', '');
INSERT INTO `permission` VALUES ('313', 'audit_purchase', '审核采购单据', '/fy/admin/biz/commission/audit/auditPurchase', 'FyPurchaseAuditController', '0', '');
INSERT INTO `permission` VALUES ('314', 'purchase', '采购单', '/fy/admin/biz/commission/purchase', 'PurchaseController', '0', '');
INSERT INTO `permission` VALUES ('315', 'purchase_download', '采购单下载', '/fy/admin/biz/commission/purchase/downloadPurchase', 'PurchaseController', '0', '');
INSERT INTO `permission` VALUES ('316', 'commision_collect', '委外一览表', '/fy/admin/biz/commission/collect', 'CommissionCollectController', '0', '');
INSERT INTO `permission` VALUES ('317', 'commision_collect_rollback', '委外撤回', '/fy/admin/biz/fyorder/distribute/batchRollback', 'DistributController', '0', '');
INSERT INTO `permission` VALUES ('318', 'product', '自产接收表', '/fy/admin/biz/product/receive', 'ProduceReceiveController', '0', '');
INSERT INTO `permission` VALUES ('319', 'product_receive', '自产接收订单', '/fy/admin/biz/product/receive/receive', 'ProduceReceiveController', '0', '');
INSERT INTO `permission` VALUES ('320', 'product_plan', '生产计划表', '/fy/admin/biz/product/plan', 'PlanController', '0', '');
INSERT INTO `permission` VALUES ('321', 'product_plan_update', '修改计划时间', '/fy/admin/biz/product/plan/batchUpdatePlan', 'PlanController', '0', '');
INSERT INTO `permission` VALUES ('322', 'product_collect', '生产一览表', '/fy/admin/biz/product/collect', 'PlanCollectController', '0', '');
INSERT INTO `permission` VALUES ('323', 'product_collect_rollback', '生产撤回', '/fy/admin/biz/fyorder/distribute/batchRollback', 'DistributController', '0', '');
INSERT INTO `permission` VALUES ('324', 'product_collect_update', '更新生产时间', '/fy/admin/biz/product/collect/batchUpdatePlan', 'PlanCollectController', '0', '');
INSERT INTO `permission` VALUES ('325', 'product_create_assist', '新建外协加工单', '/fy/admin/biz/assist/saveAssist', 'AssistController', '0', '');
INSERT INTO `permission` VALUES ('326', 'assist', '外协加工单', '/fy/admin/biz/assist', 'AssistController', '0', '');
INSERT INTO `permission` VALUES ('327', 'wait_inhouse', '待入库', '/fy/admin/biz/whouse/waitInhouse', 'WaitInhouseController', '0', '');
INSERT INTO `permission` VALUES ('328', 'inhouse', '入库', '/fy/admin/biz/whouse/waitInhouse/inhouse', 'WaitInhouseController', '0', '');
INSERT INTO `permission` VALUES ('329', 'wait_check', '待检测', '/fy/admin/biz/whouse/check/waitCheck', 'WaitCheckController', '0', '');
INSERT INTO `permission` VALUES ('330', 'wait_check_collect', '检测一览表', '/fy/admin/biz/whouse/check/collect', 'CheckCollectController', '0', '');
INSERT INTO `permission` VALUES ('331', 'exception', '异常记录表', '/fy/admin/biz/whouse/check/exception', 'CheckExceptionController', '0', '');
INSERT INTO `permission` VALUES ('332', 'exception_download', '异常记录表下载', '/fy/admin/biz/whouse/check/exception/downloadException', 'CheckExceptionController', '0', '');
INSERT INTO `permission` VALUES ('333', 'storage', '库存', '/fy/admin/biz/whouse/storage', 'StorageController', '0', '');
INSERT INTO `permission` VALUES ('334', 'storage_out', '执行出库', '/fy/admin/biz/whouse/outhouse/batchOutHouse', 'OuthouseController', '0', '');
INSERT INTO `permission` VALUES ('335', 'out_house', '出库', '/fy/admin/biz/whouse/outhouse', 'OuthouseController', '0', '');
INSERT INTO `permission` VALUES ('336', 'out_house_download', '出库下载', '/fy/admin/biz/whouse/outhouse/downloadOut', 'OuthouseController', '0', '');
INSERT INTO `permission` VALUES ('337', 'out_house_rollback', '出库撤回', '/fy/admin/biz/whouse/outhouse/rollbackOut', 'OuthouseController', '0', '');
INSERT INTO `permission` VALUES ('338', 'getpay', '应收明细', '/fy/admin/biz/finance/upgetpay', 'UploadGetpayController', '0', '');
INSERT INTO `permission` VALUES ('339', 'getpay_upload', '上传应收明细', '/fy/admin/biz/finance/upgetpay/updload', 'UploadGetpayController', '0', '');
INSERT INTO `permission` VALUES ('340', 'getpay_to_bill', '应收更新', '/fy/admin/biz/finance/upgetpay/toBill', 'UploadGetpayController', '0', '');
INSERT INTO `permission` VALUES ('341', 'getpay_bill', '应收结算', '/fy/admin/biz/finance/upgetpaybill', 'UploadGetpayController', '0', '');
INSERT INTO `permission` VALUES ('342', 'getpay_bill_rollback', '应收结算撤回', '/fy/admin/biz/finance/upgetpaybill/rollback', 'UploadGetpayController', '0', '');
INSERT INTO `permission` VALUES ('343', 'pay', '应付明细', '/fy/admin/biz/finance/pay', 'PayController', '0', '');
INSERT INTO `permission` VALUES ('344', 'pay_download', '应付下载', '/fy/admin/biz/finance/pay/downloadPay', 'PayController', '0', '');
INSERT INTO `permission` VALUES ('345', 'pay_rollback', '应付撤回', '/fy/admin/biz/finance/pay/rollback', 'PayController', '0', '');
INSERT INTO `permission` VALUES ('346', 'pay_to_bill', '应付更新', '/fy/admin/biz/finance/pay/toBill', 'PayController', '0', '');
INSERT INTO `permission` VALUES ('347', 'paybill', '应付结算', '/fy/admin/biz/finance/paybill', 'PayBillController', '0', '');
INSERT INTO `permission` VALUES ('348', 'paybill_rollback', '应付结算撤回', '/fy/admin/biz/finance/paybill/rollback', 'PayBillController', '0', '');
INSERT INTO `permission` VALUES ('349', 'complaint', '投诉表', '/fy/admin/biz/aftersale/complaint', 'ComplaintController', '0', '');
INSERT INTO `permission` VALUES ('350', 'readyReceive', '备货接收', '/fy/admin/biz/addition/readyReceive', 'ReadyReceiveController', '0', '');
INSERT INTO `permission` VALUES ('351', 'ready', '备货', '/fy/admin/biz/addition/ready', 'ReadyController', '0', '');
INSERT INTO `permission` VALUES ('352', 'advisory', '价格查询', '/fy/admin/biz/addition/advisory', 'AdvisoryCostConllor', '0', '');
INSERT INTO `permission` VALUES ('353', 'house_product_selft', '入库自产查看', '', 'HouseAbout', '0', '');
INSERT INTO `permission` VALUES ('354', 'house_purchase', '入库委外查看', '', 'HouseAbout', '0', '');
INSERT INTO `permission` VALUES ('355', 'hang_amount_view', '订单金额统计', '', 'AccountController', '0', '');

-- ----------------------------
-- Table structure for `project`
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `title` varchar(150) NOT NULL,
  `content` text NOT NULL,
  `createAt` datetime NOT NULL,
  `clickCount` int(11) NOT NULL DEFAULT '0',
  `report` int(11) NOT NULL DEFAULT '0',
  `likeCount` int(11) NOT NULL DEFAULT '0',
  `favoriteCount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('1', '1', 'JFinal', 'jfinal 极速开发框架', '<p>JFinal 是基于 Java 语言的极速 WEB + ORM 框架，其核心设计目标是开发迅速、代码量少、学习简单、功能强大、轻量级、易扩展、Restful。在拥有Java语言所有优势的同时再拥有ruby、python、php等动态语言的开发效率！为您节约更多时间，去陪恋人、家人和朋友 :)</p><h2>JFinal有如下主要特点：</h2><ul class=\" list-paddingleft-2\"><li><p>MVC架构，设计精巧，使用简单</p></li><li><p>遵循COC原则，零配置，无xml</p></li><li><p>独创Db + Record模式，灵活便利</p></li><li><p>ActiveRecord支持，使数据库开发极致快速</p></li><li><p>自动加载修改后的java文件，开发过程中无需重启web server</p></li><li><p>AOP支持，拦截器配置灵活，功能强大</p></li><li><p>Plugin体系结构，扩展性强</p></li><li><p>多视图支持，支持FreeMarker、JSP、Velocity</p></li><li><p>强大的Validator后端校验功能</p></li><li><p>功能齐全，拥有struts2的绝大部分功能</p></li><li><p>体积小仅632K，且无第三方依赖</p></li></ul><h2>以下是JFinal实现Blog管理的示例：</h2><h3>1：控制器(支持FreeMarker、JSP、Velocity、JSON等以及自定义视图渲染)</h3><pre>@Before(BlogInterceptor.class)\npublic&nbsp;class&nbsp;BlogController&nbsp;extends&nbsp;Controller&nbsp;{\n&nbsp;&nbsp;public&nbsp;void&nbsp;index()&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;setAttr(\"blogList\",&nbsp;Blog.dao.find(\"select&nbsp;*&nbsp;from&nbsp;blog\"));\n&nbsp;&nbsp;}\n\n&nbsp;&nbsp;public&nbsp;void&nbsp;add()&nbsp;{\n&nbsp;&nbsp;}\n\n&nbsp;&nbsp;@Before(BlogValidator.class)\n&nbsp;&nbsp;public&nbsp;void&nbsp;save()&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;getModel(Blog.class).save();\n&nbsp;&nbsp;}\n\n&nbsp;&nbsp;public&nbsp;void&nbsp;edit()&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;setAttr(\"blog\",&nbsp;Blog.dao.findById(getParaToInt()));\n&nbsp;&nbsp;}\n\n&nbsp;&nbsp;@Before(BlogValidator.class)\n&nbsp;&nbsp;public&nbsp;void&nbsp;update()&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;getModel(Blog.class).update();\n&nbsp;&nbsp;}\n\n&nbsp;&nbsp;public&nbsp;void&nbsp;delete()&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;Blog.dao.deleteById(getParaToInt());\n&nbsp;&nbsp;}\n}</pre><h3>2：Model(无xml、无annotation、无attribute、无getter、无setter)</h3><pre>public&nbsp;class&nbsp;Blog&nbsp;extends&nbsp;Model&lt;Blog&gt;&nbsp;{\n}</pre><h3>3：Validator(API引导式校验，比xml校验方便N倍，有代码检查不易出错)</h3><pre>public&nbsp;class&nbsp;BlogValidator&nbsp;extends&nbsp;Validator&nbsp;{\n&nbsp;&nbsp;protected&nbsp;void&nbsp;validate(Controller&nbsp;controller)&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;validateRequiredString(\"blog.title\",&nbsp;\"titleMsg\",&nbsp;\"请输入Blog标题!\");\n&nbsp;&nbsp;&nbsp;&nbsp;validateRequiredString(\"blog.content\",&nbsp;\"contentMsg\",&nbsp;\"请输入Blog内容!\");\n&nbsp;&nbsp;}\n\n&nbsp;&nbsp;protected&nbsp;void&nbsp;handleError(Controller&nbsp;controller)&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;controller.keepModel(Blog.class);\n&nbsp;&nbsp;}\n}</pre><h3>4：拦截器(在此demo中仅为示例，本demo不需要此拦截器)</h3><pre>public&nbsp;class&nbsp;BlogInterceptor&nbsp;implements&nbsp;Interceptor&nbsp;{\n&nbsp;&nbsp;public&nbsp;void&nbsp;intercept(Invocation&nbsp;inv)&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(\"Before&nbsp;invoking&nbsp;\"&nbsp;+&nbsp;inv.getActionKey());\n&nbsp;&nbsp;&nbsp;&nbsp;inv.invoke();\n&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(\"After&nbsp;invoking&nbsp;\"&nbsp;+&nbsp;inv.getActionKey());\n&nbsp;&nbsp;}\n}</pre><h3>5：最新文档与最新下载</h3><ul class=\" list-paddingleft-2\"><li><p><a href=\"/doc\" target=\"_self\" title=\"JFinal 3.4 手册\">JFinal 3.4 手册(在线版)</a></p></li><li><p><a href=\"/download?file=jfinal-3.4-all.zip\" target=\"_blank\" title=\"JFinal 3.4 all\">JFinal 3.4 all</a></p></li><li><p><a href=\"/download?file=jfinal-3.4_demo_for_maven.zip\" target=\"_blank\" title=\"JFinal 3.4 demo for maven\">JFinal 3.4 demo for maven</a></p></li><li><p><a href=\"/download?file=jfinal-3.4_demo.zip\" target=\"_blank\" title=\"JFinal 3.4 demo\">JFinal 3.4 demo</a></p></li></ul>', '2016-06-06 06:06:06', '0', '3', '131', '66');
INSERT INTO `project` VALUES ('2', '1', 'JFinal Weixin', 'jfinal weixin 极速开发 SDK', '<p>JFinal Weixin 是基于 JFinal 的微信公众号极速开发 SDK，只需浏览 Demo 代码即可进行极速开发，自 JFinal Weixin 1.2 版本开始已添加对多公众号支持。</p><h2>1、WeixinConfig配置</h2><p>详情请见：JFinal weixin中的WeixinConfig配置</p><h2>2、WeixinMsgController</h2><p>WeixinMsgController 通过继承自 MsgController 便拥有了接收消息和发送消息的便利方法</p><h2>3、WeixinApiController</h2><p>通过调用 MenuApi、UserApi 等 Api 的相关方法即可获取封装成 ApiResult 对象的结果，使用 render 系列方法即可快捷输出结果</p><h2>4、非Maven用户得到所有依赖 jar 包两种方法</h2><p>将项目导入eclipse jee中，使用 export 功能导出 war包，其中的 WEB-INF/lib 下面会自动生成 jar 包 让使用 maven 的朋友使用 mvn package 打出 war包，其中的 WEB-INF/lib 下面会自动生成 jar 包 以上两种方法注意要先将pom.xml中的导出类型设置为 war，添加 war 内容进去即可 依赖jackson或fastjson</p><h2>5、jar包依赖详细说明</h2><p>详见请见：JFinal weixin 1.9 Jar依赖</p><h2>6、WIKI持续更新中</h2><p>WIKI：<a href=\"http://git.oschina.net/jfinal/jfinal-weixin/wikis/home\" target=\"_blank\">http://git.oschina.net/jfinal/jfinal-weixin/wikis/home</a> 欢迎更多同学来帮助完善！</p><h3>5：最新下载</h3><p><a href=\"/download?file=jfinal-weixin-1.9-bin-with-src.jar\" target=\"_blank\">JFinal weixin 1.9</a></p>', '2016-06-06 06:07:00', '0', '3', '0', '0');
INSERT INTO `project` VALUES ('3', '1', '憨憨电影网', '憨憨电影—基于jfinal搭建的电影网站', '<p>&nbsp; &nbsp; 本人利用闲余时间，通过jfinal搭建的一个电影网站,里面收录的都是高质量的电影。\n\n &nbsp; &nbsp;</p><p>&nbsp; &nbsp; 项目的主要架构是 nginx + tomcat + jetty + redis + mysql 。其中，nginx作为静态文件服务和负载均衡；tomcat 和 jetty 作为应用服务器，处理请求；redis取代session；mysql数据库大家都懂。另外，SSL 证书使用的是开源的Let\'s Encrypt。</p><p>&nbsp; &nbsp; 欢迎大家访问，网址是：<a href=\"https://www.hanhanfilm.com\" target=\"_blank\">https://www.hanhanfilm.com</a></p>', '2018-04-27 23:19:37', '0', '3', '0', '0');
INSERT INTO `project` VALUES ('5', '1', 'EOVA', 'jfinal 快速开发平台', '<p>&nbsp; &nbsp; 首创JFinal快速开发平台，降低70%开发成本。快速搞定各类管理系统，赶紧用EOVA给自己加薪吧!&nbsp;</p><p>&nbsp; &nbsp; Eova能快速实现啥效果呐?\n\n\n\n定时任务有木有?\n\n\n\n什么?你要做复杂的自定义报表?\n\n\n\nWord报表呢?&nbsp;</p><p>&nbsp; &nbsp; 你认识的控件都有!\n\n\n\n做网站后台管理，就用EOVA快速开发\n\n其它疑问请上社区提问！<a href=\"http://www.eova.cn\n\n\n来自广大用户的呼声\">http://www.eova.cn\n\n\n来自广大用户的呼声</a></p><p>&nbsp; &nbsp; 楼上这些曾经使用Eova的同学2017年都成了CTO,迎娶了白富美,走向了人生巅峰!\n\n你还在犹豫什么?\n\n赶紧抄起鼠标&nbsp;<a href=\"https://gitee.com/eova/eova\" target=\"_blank\">https://gitee.com/eova/eova</a> Star吧!</p>', '2018-04-27 23:22:18', '0', '3', '0', '0');
INSERT INTO `project` VALUES ('6', '1', 'jfinal-mail-plugin', 'jfinal 邮件发送插件', '<p>&nbsp; &nbsp; 简介</p><p>&nbsp; &nbsp; &nbsp;jfinal-mail-plugin是jfinal的一个邮件发送插件，支持发送普通邮件、与附件邮件，邮件内容支持通过模板生成，同时还支持多个邮件发送源，她继承了Jfinal核心目标“开发迅速，代码量少，学习简单。。。”，只需简单的2行代码即可实现邮件发送！为您节约更多时间，去陪恋人、家人和朋友 :) ，核心代码通过spring-context-support包的邮件模块移植，JavaMailSender对象如何发送邮件可直接参照Spring的邮件发送文档。</p><p>项目地址：<a href=\"http://git.oschina.net/xiyoufang/jfinal-mail-plugin\n\">http://git.oschina.net/xiyoufang/jfinal-mail-plugin&nbsp;</a></p><p>官方网站：<a href=\"http://www.fsdev.cn/\n\n\">http://www.fsdev.cn/&nbsp;</a></p><p><a href=\"http://www.fsdev.cn/\n\n\"></a><span style=\"font-family: 微软雅黑; font-size: 18px;\">动态SQL插件</span>：<a href=\"http://git.oschina.net/xiyoufang/jfinal-xsql-plugin\" target=\"_blank\">http://git.oschina.net/xiyoufang/jfinal-xsql-plugin </a></p><p>&nbsp;<span style=\"font-family: 微软雅黑; font-size: 18px;\">PDF生成插件：<a href=\"http://git.oschina.net/xiyoufang/jfinal-pdf-plugin\" target=\"_blank\"> http://git.oschina.net/xiyoufang/jfinal-pdf-plugin </a></span></p><p>&nbsp;</p>', '2018-04-27 23:25:12', '0', '0', '1', '1');
INSERT INTO `project` VALUES ('7', '1', 'jfinal3.0从入门到入魔', 'jfinal3.0从入门到入魔系列', '<p>&nbsp; &nbsp; 那个什么，宣布一个事情，jfinal3.0视频教程发布了，来VIP群一起和我修仙，一起上天飞吧！！捷足先登，然后封神！</p>', '2018-04-27 23:26:50', '0', '0', '1', '1');
INSERT INTO `project` VALUES ('8', '1', 'test', '测试锁定', '<p>测试锁定功能，<span style=\"font-family: 微软雅黑; font-size: 18px;\">测试锁定功能</span></p>', '2018-04-27 23:40:45', '0', '3', '0', '0');

-- ----------------------------
-- Table structure for `remind`
-- ----------------------------
DROP TABLE IF EXISTS `remind`;
CREATE TABLE `remind` (
  `accountId` int(11) NOT NULL COMMENT '用户账号id，必须手动指定，不自增',
  `referMe` int(11) NOT NULL DEFAULT '0' COMMENT '提到我的消息条数',
  `message` int(11) NOT NULL DEFAULT '0' COMMENT '私信条数',
  `fans` int(11) NOT NULL DEFAULT '0' COMMENT '粉丝增加个数',
  PRIMARY KEY (`accountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of remind
-- ----------------------------
INSERT INTO `remind` VALUES ('1', '0', '0', '0');
INSERT INTO `remind` VALUES ('3', '0', '0', '0');
INSERT INTO `remind` VALUES ('10', '0', '0', '0');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL DEFAULT '',
  `createAt` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员', '2018-03-19 09:58:19');
INSERT INTO `role` VALUES ('2', '总经理', '2018-04-27 22:37:18');
INSERT INTO `role` VALUES ('3', '财务部1', '2018-04-27 22:37:25');
INSERT INTO `role` VALUES ('5', '采购部', '2018-04-27 22:37:59');
INSERT INTO `role` VALUES ('6', '生产部1', '2018-06-27 19:01:30');
INSERT INTO `role` VALUES ('7', '工程部', '2018-08-13 18:45:08');
INSERT INTO `role` VALUES ('8', '生产部', '2018-08-13 18:45:17');
INSERT INTO `role` VALUES ('9', '副总经理', '2018-08-14 08:02:58');
INSERT INTO `role` VALUES ('10', '品检部', '2018-08-14 08:04:51');
INSERT INTO `role` VALUES ('11', '财务部2', '2018-10-12 08:54:05');
INSERT INTO `role` VALUES ('12', '采购部1', '2018-10-12 08:54:39');
INSERT INTO `role` VALUES ('13', '采购部2', '2018-10-12 09:12:14');
INSERT INTO `role` VALUES ('14', '办公室2', '2018-10-12 09:12:56');

-- ----------------------------
-- Table structure for `role_col`
-- ----------------------------
DROP TABLE IF EXISTS `role_col`;
CREATE TABLE `role_col` (
  `column_id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`column_id`,`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_col
-- ----------------------------
INSERT INTO `role_col` VALUES ('1', '1');
INSERT INTO `role_col` VALUES ('1', '2');
INSERT INTO `role_col` VALUES ('1', '3');
INSERT INTO `role_col` VALUES ('1', '5');
INSERT INTO `role_col` VALUES ('1', '6');
INSERT INTO `role_col` VALUES ('1', '8');
INSERT INTO `role_col` VALUES ('1', '10');
INSERT INTO `role_col` VALUES ('2', '1');
INSERT INTO `role_col` VALUES ('2', '2');
INSERT INTO `role_col` VALUES ('2', '3');
INSERT INTO `role_col` VALUES ('2', '5');
INSERT INTO `role_col` VALUES ('2', '6');
INSERT INTO `role_col` VALUES ('2', '8');
INSERT INTO `role_col` VALUES ('2', '10');
INSERT INTO `role_col` VALUES ('3', '1');
INSERT INTO `role_col` VALUES ('3', '2');
INSERT INTO `role_col` VALUES ('3', '3');
INSERT INTO `role_col` VALUES ('3', '5');
INSERT INTO `role_col` VALUES ('3', '6');
INSERT INTO `role_col` VALUES ('3', '8');
INSERT INTO `role_col` VALUES ('3', '10');
INSERT INTO `role_col` VALUES ('4', '1');
INSERT INTO `role_col` VALUES ('4', '2');
INSERT INTO `role_col` VALUES ('4', '3');
INSERT INTO `role_col` VALUES ('4', '5');
INSERT INTO `role_col` VALUES ('4', '6');
INSERT INTO `role_col` VALUES ('4', '8');
INSERT INTO `role_col` VALUES ('4', '10');
INSERT INTO `role_col` VALUES ('5', '1');
INSERT INTO `role_col` VALUES ('5', '2');
INSERT INTO `role_col` VALUES ('5', '3');
INSERT INTO `role_col` VALUES ('5', '5');
INSERT INTO `role_col` VALUES ('5', '6');
INSERT INTO `role_col` VALUES ('5', '8');
INSERT INTO `role_col` VALUES ('5', '10');
INSERT INTO `role_col` VALUES ('6', '1');
INSERT INTO `role_col` VALUES ('6', '2');
INSERT INTO `role_col` VALUES ('6', '3');
INSERT INTO `role_col` VALUES ('6', '5');
INSERT INTO `role_col` VALUES ('6', '6');
INSERT INTO `role_col` VALUES ('6', '8');
INSERT INTO `role_col` VALUES ('6', '10');
INSERT INTO `role_col` VALUES ('7', '1');
INSERT INTO `role_col` VALUES ('7', '2');
INSERT INTO `role_col` VALUES ('7', '3');
INSERT INTO `role_col` VALUES ('7', '5');
INSERT INTO `role_col` VALUES ('7', '6');
INSERT INTO `role_col` VALUES ('7', '8');
INSERT INTO `role_col` VALUES ('7', '10');
INSERT INTO `role_col` VALUES ('8', '1');
INSERT INTO `role_col` VALUES ('8', '2');
INSERT INTO `role_col` VALUES ('8', '3');
INSERT INTO `role_col` VALUES ('8', '5');
INSERT INTO `role_col` VALUES ('8', '6');
INSERT INTO `role_col` VALUES ('8', '8');
INSERT INTO `role_col` VALUES ('8', '10');
INSERT INTO `role_col` VALUES ('9', '1');
INSERT INTO `role_col` VALUES ('9', '2');
INSERT INTO `role_col` VALUES ('9', '3');
INSERT INTO `role_col` VALUES ('9', '5');
INSERT INTO `role_col` VALUES ('9', '6');
INSERT INTO `role_col` VALUES ('9', '8');
INSERT INTO `role_col` VALUES ('9', '10');
INSERT INTO `role_col` VALUES ('10', '1');
INSERT INTO `role_col` VALUES ('10', '2');
INSERT INTO `role_col` VALUES ('10', '3');
INSERT INTO `role_col` VALUES ('10', '5');
INSERT INTO `role_col` VALUES ('10', '6');
INSERT INTO `role_col` VALUES ('10', '8');
INSERT INTO `role_col` VALUES ('10', '10');
INSERT INTO `role_col` VALUES ('11', '1');
INSERT INTO `role_col` VALUES ('11', '2');
INSERT INTO `role_col` VALUES ('11', '3');
INSERT INTO `role_col` VALUES ('11', '5');
INSERT INTO `role_col` VALUES ('11', '6');
INSERT INTO `role_col` VALUES ('11', '8');
INSERT INTO `role_col` VALUES ('11', '10');
INSERT INTO `role_col` VALUES ('12', '1');
INSERT INTO `role_col` VALUES ('12', '2');
INSERT INTO `role_col` VALUES ('12', '3');
INSERT INTO `role_col` VALUES ('12', '5');
INSERT INTO `role_col` VALUES ('12', '6');
INSERT INTO `role_col` VALUES ('12', '8');
INSERT INTO `role_col` VALUES ('12', '10');
INSERT INTO `role_col` VALUES ('13', '1');
INSERT INTO `role_col` VALUES ('13', '2');
INSERT INTO `role_col` VALUES ('13', '3');
INSERT INTO `role_col` VALUES ('13', '5');
INSERT INTO `role_col` VALUES ('13', '6');
INSERT INTO `role_col` VALUES ('13', '8');
INSERT INTO `role_col` VALUES ('13', '10');
INSERT INTO `role_col` VALUES ('14', '1');
INSERT INTO `role_col` VALUES ('14', '2');
INSERT INTO `role_col` VALUES ('14', '3');
INSERT INTO `role_col` VALUES ('14', '5');
INSERT INTO `role_col` VALUES ('14', '6');
INSERT INTO `role_col` VALUES ('14', '8');
INSERT INTO `role_col` VALUES ('14', '10');
INSERT INTO `role_col` VALUES ('15', '1');
INSERT INTO `role_col` VALUES ('15', '2');
INSERT INTO `role_col` VALUES ('15', '3');
INSERT INTO `role_col` VALUES ('15', '5');
INSERT INTO `role_col` VALUES ('15', '6');
INSERT INTO `role_col` VALUES ('15', '8');
INSERT INTO `role_col` VALUES ('15', '10');
INSERT INTO `role_col` VALUES ('16', '1');
INSERT INTO `role_col` VALUES ('16', '2');
INSERT INTO `role_col` VALUES ('16', '3');
INSERT INTO `role_col` VALUES ('17', '1');
INSERT INTO `role_col` VALUES ('17', '2');
INSERT INTO `role_col` VALUES ('17', '3');
INSERT INTO `role_col` VALUES ('18', '1');
INSERT INTO `role_col` VALUES ('18', '2');
INSERT INTO `role_col` VALUES ('18', '3');
INSERT INTO `role_col` VALUES ('19', '1');
INSERT INTO `role_col` VALUES ('19', '2');
INSERT INTO `role_col` VALUES ('19', '3');
INSERT INTO `role_col` VALUES ('20', '1');
INSERT INTO `role_col` VALUES ('20', '2');
INSERT INTO `role_col` VALUES ('20', '3');
INSERT INTO `role_col` VALUES ('21', '1');
INSERT INTO `role_col` VALUES ('21', '2');
INSERT INTO `role_col` VALUES ('21', '3');
INSERT INTO `role_col` VALUES ('22', '1');
INSERT INTO `role_col` VALUES ('22', '2');
INSERT INTO `role_col` VALUES ('22', '3');
INSERT INTO `role_col` VALUES ('23', '1');
INSERT INTO `role_col` VALUES ('23', '2');
INSERT INTO `role_col` VALUES ('23', '3');
INSERT INTO `role_col` VALUES ('24', '1');
INSERT INTO `role_col` VALUES ('24', '2');
INSERT INTO `role_col` VALUES ('24', '3');
INSERT INTO `role_col` VALUES ('25', '1');
INSERT INTO `role_col` VALUES ('25', '2');
INSERT INTO `role_col` VALUES ('25', '3');
INSERT INTO `role_col` VALUES ('26', '1');
INSERT INTO `role_col` VALUES ('26', '2');
INSERT INTO `role_col` VALUES ('26', '3');
INSERT INTO `role_col` VALUES ('27', '1');
INSERT INTO `role_col` VALUES ('27', '2');
INSERT INTO `role_col` VALUES ('27', '3');
INSERT INTO `role_col` VALUES ('28', '1');
INSERT INTO `role_col` VALUES ('28', '2');
INSERT INTO `role_col` VALUES ('28', '3');
INSERT INTO `role_col` VALUES ('29', '1');
INSERT INTO `role_col` VALUES ('29', '2');
INSERT INTO `role_col` VALUES ('29', '3');
INSERT INTO `role_col` VALUES ('30', '1');
INSERT INTO `role_col` VALUES ('30', '2');
INSERT INTO `role_col` VALUES ('30', '3');
INSERT INTO `role_col` VALUES ('31', '1');
INSERT INTO `role_col` VALUES ('31', '2');
INSERT INTO `role_col` VALUES ('31', '3');
INSERT INTO `role_col` VALUES ('32', '1');
INSERT INTO `role_col` VALUES ('32', '2');
INSERT INTO `role_col` VALUES ('32', '3');
INSERT INTO `role_col` VALUES ('32', '5');
INSERT INTO `role_col` VALUES ('32', '6');
INSERT INTO `role_col` VALUES ('32', '8');
INSERT INTO `role_col` VALUES ('32', '10');
INSERT INTO `role_col` VALUES ('33', '1');
INSERT INTO `role_col` VALUES ('33', '2');
INSERT INTO `role_col` VALUES ('33', '3');
INSERT INTO `role_col` VALUES ('33', '9');
INSERT INTO `role_col` VALUES ('33', '11');
INSERT INTO `role_col` VALUES ('34', '1');
INSERT INTO `role_col` VALUES ('34', '2');
INSERT INTO `role_col` VALUES ('34', '3');
INSERT INTO `role_col` VALUES ('34', '9');
INSERT INTO `role_col` VALUES ('34', '11');
INSERT INTO `role_col` VALUES ('35', '1');
INSERT INTO `role_col` VALUES ('35', '2');
INSERT INTO `role_col` VALUES ('35', '3');
INSERT INTO `role_col` VALUES ('35', '9');
INSERT INTO `role_col` VALUES ('35', '11');
INSERT INTO `role_col` VALUES ('36', '1');
INSERT INTO `role_col` VALUES ('36', '2');
INSERT INTO `role_col` VALUES ('36', '3');
INSERT INTO `role_col` VALUES ('36', '9');
INSERT INTO `role_col` VALUES ('36', '11');
INSERT INTO `role_col` VALUES ('37', '1');
INSERT INTO `role_col` VALUES ('37', '2');
INSERT INTO `role_col` VALUES ('37', '3');
INSERT INTO `role_col` VALUES ('37', '9');
INSERT INTO `role_col` VALUES ('37', '11');
INSERT INTO `role_col` VALUES ('38', '1');
INSERT INTO `role_col` VALUES ('38', '2');
INSERT INTO `role_col` VALUES ('38', '3');
INSERT INTO `role_col` VALUES ('38', '9');
INSERT INTO `role_col` VALUES ('38', '11');
INSERT INTO `role_col` VALUES ('39', '1');
INSERT INTO `role_col` VALUES ('39', '2');
INSERT INTO `role_col` VALUES ('39', '3');
INSERT INTO `role_col` VALUES ('39', '9');
INSERT INTO `role_col` VALUES ('39', '11');
INSERT INTO `role_col` VALUES ('40', '1');
INSERT INTO `role_col` VALUES ('40', '2');
INSERT INTO `role_col` VALUES ('40', '3');
INSERT INTO `role_col` VALUES ('40', '9');
INSERT INTO `role_col` VALUES ('40', '11');
INSERT INTO `role_col` VALUES ('41', '1');
INSERT INTO `role_col` VALUES ('41', '2');
INSERT INTO `role_col` VALUES ('41', '3');
INSERT INTO `role_col` VALUES ('41', '9');
INSERT INTO `role_col` VALUES ('41', '11');
INSERT INTO `role_col` VALUES ('42', '1');
INSERT INTO `role_col` VALUES ('42', '2');
INSERT INTO `role_col` VALUES ('42', '3');
INSERT INTO `role_col` VALUES ('42', '9');
INSERT INTO `role_col` VALUES ('42', '11');
INSERT INTO `role_col` VALUES ('43', '1');
INSERT INTO `role_col` VALUES ('43', '2');
INSERT INTO `role_col` VALUES ('43', '3');
INSERT INTO `role_col` VALUES ('43', '9');
INSERT INTO `role_col` VALUES ('43', '11');
INSERT INTO `role_col` VALUES ('44', '1');
INSERT INTO `role_col` VALUES ('44', '2');
INSERT INTO `role_col` VALUES ('44', '3');
INSERT INTO `role_col` VALUES ('44', '9');
INSERT INTO `role_col` VALUES ('44', '11');
INSERT INTO `role_col` VALUES ('45', '1');
INSERT INTO `role_col` VALUES ('45', '2');
INSERT INTO `role_col` VALUES ('45', '3');
INSERT INTO `role_col` VALUES ('45', '9');
INSERT INTO `role_col` VALUES ('45', '11');
INSERT INTO `role_col` VALUES ('46', '1');
INSERT INTO `role_col` VALUES ('46', '2');
INSERT INTO `role_col` VALUES ('46', '3');
INSERT INTO `role_col` VALUES ('46', '9');
INSERT INTO `role_col` VALUES ('46', '11');
INSERT INTO `role_col` VALUES ('47', '1');
INSERT INTO `role_col` VALUES ('47', '2');
INSERT INTO `role_col` VALUES ('47', '3');
INSERT INTO `role_col` VALUES ('47', '9');
INSERT INTO `role_col` VALUES ('47', '11');
INSERT INTO `role_col` VALUES ('48', '1');
INSERT INTO `role_col` VALUES ('48', '2');
INSERT INTO `role_col` VALUES ('48', '3');
INSERT INTO `role_col` VALUES ('48', '9');
INSERT INTO `role_col` VALUES ('48', '11');
INSERT INTO `role_col` VALUES ('49', '1');
INSERT INTO `role_col` VALUES ('49', '2');
INSERT INTO `role_col` VALUES ('49', '3');
INSERT INTO `role_col` VALUES ('49', '9');
INSERT INTO `role_col` VALUES ('49', '11');
INSERT INTO `role_col` VALUES ('50', '1');
INSERT INTO `role_col` VALUES ('50', '2');
INSERT INTO `role_col` VALUES ('50', '3');
INSERT INTO `role_col` VALUES ('50', '9');
INSERT INTO `role_col` VALUES ('50', '11');
INSERT INTO `role_col` VALUES ('51', '1');
INSERT INTO `role_col` VALUES ('52', '1');
INSERT INTO `role_col` VALUES ('67', '1');
INSERT INTO `role_col` VALUES ('67', '2');
INSERT INTO `role_col` VALUES ('67', '3');
INSERT INTO `role_col` VALUES ('67', '5');
INSERT INTO `role_col` VALUES ('67', '6');
INSERT INTO `role_col` VALUES ('67', '7');
INSERT INTO `role_col` VALUES ('67', '8');
INSERT INTO `role_col` VALUES ('67', '9');
INSERT INTO `role_col` VALUES ('67', '10');
INSERT INTO `role_col` VALUES ('67', '11');
INSERT INTO `role_col` VALUES ('67', '12');
INSERT INTO `role_col` VALUES ('67', '13');
INSERT INTO `role_col` VALUES ('68', '1');
INSERT INTO `role_col` VALUES ('68', '2');
INSERT INTO `role_col` VALUES ('68', '3');
INSERT INTO `role_col` VALUES ('68', '5');
INSERT INTO `role_col` VALUES ('68', '6');
INSERT INTO `role_col` VALUES ('68', '7');
INSERT INTO `role_col` VALUES ('68', '8');
INSERT INTO `role_col` VALUES ('68', '9');
INSERT INTO `role_col` VALUES ('68', '10');
INSERT INTO `role_col` VALUES ('68', '11');
INSERT INTO `role_col` VALUES ('68', '12');
INSERT INTO `role_col` VALUES ('68', '13');
INSERT INTO `role_col` VALUES ('69', '1');
INSERT INTO `role_col` VALUES ('69', '2');
INSERT INTO `role_col` VALUES ('69', '3');
INSERT INTO `role_col` VALUES ('69', '5');
INSERT INTO `role_col` VALUES ('69', '6');
INSERT INTO `role_col` VALUES ('69', '7');
INSERT INTO `role_col` VALUES ('69', '8');
INSERT INTO `role_col` VALUES ('69', '9');
INSERT INTO `role_col` VALUES ('69', '10');
INSERT INTO `role_col` VALUES ('69', '11');
INSERT INTO `role_col` VALUES ('69', '12');
INSERT INTO `role_col` VALUES ('69', '13');
INSERT INTO `role_col` VALUES ('70', '1');
INSERT INTO `role_col` VALUES ('70', '2');
INSERT INTO `role_col` VALUES ('70', '3');
INSERT INTO `role_col` VALUES ('70', '5');
INSERT INTO `role_col` VALUES ('70', '6');
INSERT INTO `role_col` VALUES ('70', '7');
INSERT INTO `role_col` VALUES ('70', '8');
INSERT INTO `role_col` VALUES ('70', '9');
INSERT INTO `role_col` VALUES ('70', '10');
INSERT INTO `role_col` VALUES ('70', '11');
INSERT INTO `role_col` VALUES ('70', '12');
INSERT INTO `role_col` VALUES ('70', '13');
INSERT INTO `role_col` VALUES ('71', '1');
INSERT INTO `role_col` VALUES ('71', '2');
INSERT INTO `role_col` VALUES ('71', '3');
INSERT INTO `role_col` VALUES ('71', '5');
INSERT INTO `role_col` VALUES ('71', '6');
INSERT INTO `role_col` VALUES ('71', '7');
INSERT INTO `role_col` VALUES ('71', '8');
INSERT INTO `role_col` VALUES ('71', '9');
INSERT INTO `role_col` VALUES ('71', '10');
INSERT INTO `role_col` VALUES ('71', '11');
INSERT INTO `role_col` VALUES ('71', '12');
INSERT INTO `role_col` VALUES ('71', '13');
INSERT INTO `role_col` VALUES ('72', '1');
INSERT INTO `role_col` VALUES ('72', '2');
INSERT INTO `role_col` VALUES ('72', '3');
INSERT INTO `role_col` VALUES ('72', '5');
INSERT INTO `role_col` VALUES ('72', '6');
INSERT INTO `role_col` VALUES ('72', '7');
INSERT INTO `role_col` VALUES ('72', '8');
INSERT INTO `role_col` VALUES ('72', '9');
INSERT INTO `role_col` VALUES ('72', '10');
INSERT INTO `role_col` VALUES ('72', '11');
INSERT INTO `role_col` VALUES ('72', '12');
INSERT INTO `role_col` VALUES ('72', '13');
INSERT INTO `role_col` VALUES ('73', '1');
INSERT INTO `role_col` VALUES ('73', '2');
INSERT INTO `role_col` VALUES ('73', '3');
INSERT INTO `role_col` VALUES ('73', '5');
INSERT INTO `role_col` VALUES ('73', '6');
INSERT INTO `role_col` VALUES ('73', '7');
INSERT INTO `role_col` VALUES ('73', '8');
INSERT INTO `role_col` VALUES ('73', '9');
INSERT INTO `role_col` VALUES ('73', '10');
INSERT INTO `role_col` VALUES ('73', '11');
INSERT INTO `role_col` VALUES ('73', '12');
INSERT INTO `role_col` VALUES ('73', '13');
INSERT INTO `role_col` VALUES ('74', '1');
INSERT INTO `role_col` VALUES ('74', '2');
INSERT INTO `role_col` VALUES ('74', '3');
INSERT INTO `role_col` VALUES ('74', '5');
INSERT INTO `role_col` VALUES ('74', '6');
INSERT INTO `role_col` VALUES ('74', '7');
INSERT INTO `role_col` VALUES ('74', '8');
INSERT INTO `role_col` VALUES ('74', '9');
INSERT INTO `role_col` VALUES ('74', '10');
INSERT INTO `role_col` VALUES ('74', '11');
INSERT INTO `role_col` VALUES ('74', '12');
INSERT INTO `role_col` VALUES ('74', '13');
INSERT INTO `role_col` VALUES ('75', '1');
INSERT INTO `role_col` VALUES ('75', '2');
INSERT INTO `role_col` VALUES ('75', '3');
INSERT INTO `role_col` VALUES ('75', '5');
INSERT INTO `role_col` VALUES ('75', '6');
INSERT INTO `role_col` VALUES ('75', '7');
INSERT INTO `role_col` VALUES ('75', '8');
INSERT INTO `role_col` VALUES ('75', '9');
INSERT INTO `role_col` VALUES ('75', '10');
INSERT INTO `role_col` VALUES ('75', '11');
INSERT INTO `role_col` VALUES ('75', '12');
INSERT INTO `role_col` VALUES ('75', '13');
INSERT INTO `role_col` VALUES ('76', '1');
INSERT INTO `role_col` VALUES ('76', '2');
INSERT INTO `role_col` VALUES ('76', '3');
INSERT INTO `role_col` VALUES ('76', '5');
INSERT INTO `role_col` VALUES ('76', '6');
INSERT INTO `role_col` VALUES ('76', '7');
INSERT INTO `role_col` VALUES ('76', '8');
INSERT INTO `role_col` VALUES ('76', '9');
INSERT INTO `role_col` VALUES ('76', '10');
INSERT INTO `role_col` VALUES ('76', '11');
INSERT INTO `role_col` VALUES ('76', '12');
INSERT INTO `role_col` VALUES ('76', '13');
INSERT INTO `role_col` VALUES ('77', '1');
INSERT INTO `role_col` VALUES ('77', '2');
INSERT INTO `role_col` VALUES ('77', '3');
INSERT INTO `role_col` VALUES ('77', '5');
INSERT INTO `role_col` VALUES ('77', '6');
INSERT INTO `role_col` VALUES ('77', '7');
INSERT INTO `role_col` VALUES ('77', '8');
INSERT INTO `role_col` VALUES ('77', '9');
INSERT INTO `role_col` VALUES ('77', '10');
INSERT INTO `role_col` VALUES ('77', '11');
INSERT INTO `role_col` VALUES ('77', '12');
INSERT INTO `role_col` VALUES ('77', '13');
INSERT INTO `role_col` VALUES ('78', '1');
INSERT INTO `role_col` VALUES ('78', '2');
INSERT INTO `role_col` VALUES ('78', '3');
INSERT INTO `role_col` VALUES ('78', '5');
INSERT INTO `role_col` VALUES ('78', '6');
INSERT INTO `role_col` VALUES ('78', '7');
INSERT INTO `role_col` VALUES ('78', '8');
INSERT INTO `role_col` VALUES ('78', '9');
INSERT INTO `role_col` VALUES ('78', '10');
INSERT INTO `role_col` VALUES ('78', '11');
INSERT INTO `role_col` VALUES ('78', '12');
INSERT INTO `role_col` VALUES ('78', '13');
INSERT INTO `role_col` VALUES ('79', '1');
INSERT INTO `role_col` VALUES ('79', '2');
INSERT INTO `role_col` VALUES ('79', '3');
INSERT INTO `role_col` VALUES ('79', '5');
INSERT INTO `role_col` VALUES ('79', '6');
INSERT INTO `role_col` VALUES ('79', '7');
INSERT INTO `role_col` VALUES ('79', '8');
INSERT INTO `role_col` VALUES ('79', '9');
INSERT INTO `role_col` VALUES ('79', '10');
INSERT INTO `role_col` VALUES ('79', '11');
INSERT INTO `role_col` VALUES ('79', '12');
INSERT INTO `role_col` VALUES ('79', '13');
INSERT INTO `role_col` VALUES ('80', '1');
INSERT INTO `role_col` VALUES ('80', '2');
INSERT INTO `role_col` VALUES ('80', '3');
INSERT INTO `role_col` VALUES ('80', '5');
INSERT INTO `role_col` VALUES ('80', '6');
INSERT INTO `role_col` VALUES ('80', '7');
INSERT INTO `role_col` VALUES ('80', '8');
INSERT INTO `role_col` VALUES ('80', '9');
INSERT INTO `role_col` VALUES ('80', '10');
INSERT INTO `role_col` VALUES ('80', '11');
INSERT INTO `role_col` VALUES ('80', '12');
INSERT INTO `role_col` VALUES ('80', '13');
INSERT INTO `role_col` VALUES ('81', '1');
INSERT INTO `role_col` VALUES ('81', '2');
INSERT INTO `role_col` VALUES ('81', '3');
INSERT INTO `role_col` VALUES ('81', '5');
INSERT INTO `role_col` VALUES ('81', '6');
INSERT INTO `role_col` VALUES ('81', '7');
INSERT INTO `role_col` VALUES ('81', '8');
INSERT INTO `role_col` VALUES ('81', '9');
INSERT INTO `role_col` VALUES ('81', '10');
INSERT INTO `role_col` VALUES ('81', '11');
INSERT INTO `role_col` VALUES ('81', '12');
INSERT INTO `role_col` VALUES ('81', '13');
INSERT INTO `role_col` VALUES ('82', '1');
INSERT INTO `role_col` VALUES ('82', '2');
INSERT INTO `role_col` VALUES ('82', '3');
INSERT INTO `role_col` VALUES ('82', '5');
INSERT INTO `role_col` VALUES ('82', '6');
INSERT INTO `role_col` VALUES ('82', '7');
INSERT INTO `role_col` VALUES ('82', '8');
INSERT INTO `role_col` VALUES ('82', '9');
INSERT INTO `role_col` VALUES ('82', '10');
INSERT INTO `role_col` VALUES ('82', '11');
INSERT INTO `role_col` VALUES ('82', '12');
INSERT INTO `role_col` VALUES ('82', '13');
INSERT INTO `role_col` VALUES ('83', '1');
INSERT INTO `role_col` VALUES ('83', '2');
INSERT INTO `role_col` VALUES ('83', '3');
INSERT INTO `role_col` VALUES ('83', '5');
INSERT INTO `role_col` VALUES ('83', '6');
INSERT INTO `role_col` VALUES ('83', '7');
INSERT INTO `role_col` VALUES ('83', '8');
INSERT INTO `role_col` VALUES ('83', '9');
INSERT INTO `role_col` VALUES ('83', '10');
INSERT INTO `role_col` VALUES ('83', '11');
INSERT INTO `role_col` VALUES ('83', '12');
INSERT INTO `role_col` VALUES ('83', '13');
INSERT INTO `role_col` VALUES ('84', '1');
INSERT INTO `role_col` VALUES ('84', '2');
INSERT INTO `role_col` VALUES ('84', '3');
INSERT INTO `role_col` VALUES ('84', '5');
INSERT INTO `role_col` VALUES ('84', '6');
INSERT INTO `role_col` VALUES ('84', '7');
INSERT INTO `role_col` VALUES ('84', '8');
INSERT INTO `role_col` VALUES ('84', '9');
INSERT INTO `role_col` VALUES ('84', '10');
INSERT INTO `role_col` VALUES ('84', '11');
INSERT INTO `role_col` VALUES ('84', '12');
INSERT INTO `role_col` VALUES ('84', '13');
INSERT INTO `role_col` VALUES ('85', '1');
INSERT INTO `role_col` VALUES ('85', '2');
INSERT INTO `role_col` VALUES ('85', '3');
INSERT INTO `role_col` VALUES ('85', '11');
INSERT INTO `role_col` VALUES ('86', '1');
INSERT INTO `role_col` VALUES ('86', '2');
INSERT INTO `role_col` VALUES ('86', '3');
INSERT INTO `role_col` VALUES ('86', '11');
INSERT INTO `role_col` VALUES ('87', '1');
INSERT INTO `role_col` VALUES ('87', '2');
INSERT INTO `role_col` VALUES ('87', '3');
INSERT INTO `role_col` VALUES ('87', '11');
INSERT INTO `role_col` VALUES ('88', '1');
INSERT INTO `role_col` VALUES ('88', '2');
INSERT INTO `role_col` VALUES ('88', '3');
INSERT INTO `role_col` VALUES ('88', '11');
INSERT INTO `role_col` VALUES ('89', '1');
INSERT INTO `role_col` VALUES ('89', '2');
INSERT INTO `role_col` VALUES ('89', '3');
INSERT INTO `role_col` VALUES ('89', '11');
INSERT INTO `role_col` VALUES ('90', '1');
INSERT INTO `role_col` VALUES ('90', '2');
INSERT INTO `role_col` VALUES ('90', '3');
INSERT INTO `role_col` VALUES ('90', '7');
INSERT INTO `role_col` VALUES ('90', '11');
INSERT INTO `role_col` VALUES ('91', '1');
INSERT INTO `role_col` VALUES ('91', '2');
INSERT INTO `role_col` VALUES ('91', '3');
INSERT INTO `role_col` VALUES ('91', '7');
INSERT INTO `role_col` VALUES ('91', '9');
INSERT INTO `role_col` VALUES ('91', '11');
INSERT INTO `role_col` VALUES ('92', '1');
INSERT INTO `role_col` VALUES ('92', '2');
INSERT INTO `role_col` VALUES ('92', '3');
INSERT INTO `role_col` VALUES ('92', '5');
INSERT INTO `role_col` VALUES ('92', '9');
INSERT INTO `role_col` VALUES ('92', '10');
INSERT INTO `role_col` VALUES ('92', '11');
INSERT INTO `role_col` VALUES ('92', '12');
INSERT INTO `role_col` VALUES ('93', '1');
INSERT INTO `role_col` VALUES ('93', '2');
INSERT INTO `role_col` VALUES ('93', '3');
INSERT INTO `role_col` VALUES ('93', '5');
INSERT INTO `role_col` VALUES ('93', '9');
INSERT INTO `role_col` VALUES ('93', '11');
INSERT INTO `role_col` VALUES ('94', '1');
INSERT INTO `role_col` VALUES ('94', '2');
INSERT INTO `role_col` VALUES ('94', '3');
INSERT INTO `role_col` VALUES ('94', '5');
INSERT INTO `role_col` VALUES ('94', '9');
INSERT INTO `role_col` VALUES ('94', '11');
INSERT INTO `role_col` VALUES ('95', '1');
INSERT INTO `role_col` VALUES ('95', '2');
INSERT INTO `role_col` VALUES ('95', '3');
INSERT INTO `role_col` VALUES ('95', '5');
INSERT INTO `role_col` VALUES ('95', '6');
INSERT INTO `role_col` VALUES ('95', '8');
INSERT INTO `role_col` VALUES ('95', '9');
INSERT INTO `role_col` VALUES ('96', '1');
INSERT INTO `role_col` VALUES ('96', '2');
INSERT INTO `role_col` VALUES ('96', '3');
INSERT INTO `role_col` VALUES ('96', '5');
INSERT INTO `role_col` VALUES ('96', '6');
INSERT INTO `role_col` VALUES ('96', '8');
INSERT INTO `role_col` VALUES ('96', '9');
INSERT INTO `role_col` VALUES ('96', '11');
INSERT INTO `role_col` VALUES ('97', '1');
INSERT INTO `role_col` VALUES ('97', '2');
INSERT INTO `role_col` VALUES ('97', '3');
INSERT INTO `role_col` VALUES ('97', '5');
INSERT INTO `role_col` VALUES ('97', '6');
INSERT INTO `role_col` VALUES ('97', '8');
INSERT INTO `role_col` VALUES ('97', '9');
INSERT INTO `role_col` VALUES ('97', '10');
INSERT INTO `role_col` VALUES ('97', '11');
INSERT INTO `role_col` VALUES ('97', '12');
INSERT INTO `role_col` VALUES ('97', '13');
INSERT INTO `role_col` VALUES ('98', '1');
INSERT INTO `role_col` VALUES ('98', '2');
INSERT INTO `role_col` VALUES ('98', '3');
INSERT INTO `role_col` VALUES ('98', '5');
INSERT INTO `role_col` VALUES ('98', '6');
INSERT INTO `role_col` VALUES ('98', '8');
INSERT INTO `role_col` VALUES ('98', '9');
INSERT INTO `role_col` VALUES ('98', '10');
INSERT INTO `role_col` VALUES ('98', '11');
INSERT INTO `role_col` VALUES ('98', '12');
INSERT INTO `role_col` VALUES ('99', '1');
INSERT INTO `role_col` VALUES ('99', '2');
INSERT INTO `role_col` VALUES ('99', '3');
INSERT INTO `role_col` VALUES ('99', '5');
INSERT INTO `role_col` VALUES ('99', '9');
INSERT INTO `role_col` VALUES ('99', '11');
INSERT INTO `role_col` VALUES ('99', '12');
INSERT INTO `role_col` VALUES ('100', '1');
INSERT INTO `role_col` VALUES ('100', '2');
INSERT INTO `role_col` VALUES ('100', '3');
INSERT INTO `role_col` VALUES ('100', '5');
INSERT INTO `role_col` VALUES ('100', '9');
INSERT INTO `role_col` VALUES ('100', '11');
INSERT INTO `role_col` VALUES ('100', '12');
INSERT INTO `role_col` VALUES ('101', '1');
INSERT INTO `role_col` VALUES ('101', '2');
INSERT INTO `role_col` VALUES ('101', '3');
INSERT INTO `role_col` VALUES ('101', '5');
INSERT INTO `role_col` VALUES ('101', '9');
INSERT INTO `role_col` VALUES ('101', '12');
INSERT INTO `role_col` VALUES ('102', '1');
INSERT INTO `role_col` VALUES ('102', '2');
INSERT INTO `role_col` VALUES ('102', '3');
INSERT INTO `role_col` VALUES ('102', '5');
INSERT INTO `role_col` VALUES ('102', '9');
INSERT INTO `role_col` VALUES ('102', '12');
INSERT INTO `role_col` VALUES ('103', '1');
INSERT INTO `role_col` VALUES ('103', '2');
INSERT INTO `role_col` VALUES ('103', '3');
INSERT INTO `role_col` VALUES ('103', '5');
INSERT INTO `role_col` VALUES ('103', '9');
INSERT INTO `role_col` VALUES ('103', '12');
INSERT INTO `role_col` VALUES ('104', '1');
INSERT INTO `role_col` VALUES ('104', '3');
INSERT INTO `role_col` VALUES ('104', '11');
INSERT INTO `role_col` VALUES ('105', '1');
INSERT INTO `role_col` VALUES ('105', '2');
INSERT INTO `role_col` VALUES ('105', '3');
INSERT INTO `role_col` VALUES ('105', '11');
INSERT INTO `role_col` VALUES ('106', '1');
INSERT INTO `role_col` VALUES ('106', '2');
INSERT INTO `role_col` VALUES ('106', '3');
INSERT INTO `role_col` VALUES ('106', '11');
INSERT INTO `role_col` VALUES ('107', '1');
INSERT INTO `role_col` VALUES ('107', '3');
INSERT INTO `role_col` VALUES ('107', '11');
INSERT INTO `role_col` VALUES ('108', '1');
INSERT INTO `role_col` VALUES ('108', '2');
INSERT INTO `role_col` VALUES ('108', '3');
INSERT INTO `role_col` VALUES ('108', '11');
INSERT INTO `role_col` VALUES ('109', '1');
INSERT INTO `role_col` VALUES ('109', '2');
INSERT INTO `role_col` VALUES ('109', '3');
INSERT INTO `role_col` VALUES ('109', '11');
INSERT INTO `role_col` VALUES ('110', '1');
INSERT INTO `role_col` VALUES ('110', '2');
INSERT INTO `role_col` VALUES ('110', '3');
INSERT INTO `role_col` VALUES ('110', '5');
INSERT INTO `role_col` VALUES ('110', '6');
INSERT INTO `role_col` VALUES ('110', '8');
INSERT INTO `role_col` VALUES ('110', '9');
INSERT INTO `role_col` VALUES ('110', '10');
INSERT INTO `role_col` VALUES ('110', '11');
INSERT INTO `role_col` VALUES ('110', '12');
INSERT INTO `role_col` VALUES ('110', '13');
INSERT INTO `role_col` VALUES ('111', '1');
INSERT INTO `role_col` VALUES ('111', '2');
INSERT INTO `role_col` VALUES ('111', '3');
INSERT INTO `role_col` VALUES ('111', '11');

-- ----------------------------
-- Table structure for `role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `roleId` int(11) NOT NULL,
  `permissionId` int(11) NOT NULL,
  PRIMARY KEY (`roleId`,`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('2', '1');
INSERT INTO `role_permission` VALUES ('2', '5');
INSERT INTO `role_permission` VALUES ('2', '11');
INSERT INTO `role_permission` VALUES ('2', '15');
INSERT INTO `role_permission` VALUES ('2', '19');
INSERT INTO `role_permission` VALUES ('2', '23');
INSERT INTO `role_permission` VALUES ('2', '26');
INSERT INTO `role_permission` VALUES ('2', '30');
INSERT INTO `role_permission` VALUES ('2', '34');
INSERT INTO `role_permission` VALUES ('2', '38');
INSERT INTO `role_permission` VALUES ('2', '42');
INSERT INTO `role_permission` VALUES ('2', '45');
INSERT INTO `role_permission` VALUES ('2', '47');
INSERT INTO `role_permission` VALUES ('2', '50');
INSERT INTO `role_permission` VALUES ('2', '58');
INSERT INTO `role_permission` VALUES ('2', '59');
INSERT INTO `role_permission` VALUES ('2', '61');
INSERT INTO `role_permission` VALUES ('2', '67');
INSERT INTO `role_permission` VALUES ('2', '69');
INSERT INTO `role_permission` VALUES ('2', '73');
INSERT INTO `role_permission` VALUES ('2', '75');
INSERT INTO `role_permission` VALUES ('2', '79');
INSERT INTO `role_permission` VALUES ('2', '80');
INSERT INTO `role_permission` VALUES ('2', '82');
INSERT INTO `role_permission` VALUES ('2', '83');
INSERT INTO `role_permission` VALUES ('2', '84');
INSERT INTO `role_permission` VALUES ('2', '85');
INSERT INTO `role_permission` VALUES ('2', '88');
INSERT INTO `role_permission` VALUES ('2', '89');
INSERT INTO `role_permission` VALUES ('2', '91');
INSERT INTO `role_permission` VALUES ('2', '94');
INSERT INTO `role_permission` VALUES ('2', '96');
INSERT INTO `role_permission` VALUES ('2', '97');
INSERT INTO `role_permission` VALUES ('2', '100');
INSERT INTO `role_permission` VALUES ('2', '101');
INSERT INTO `role_permission` VALUES ('2', '103');
INSERT INTO `role_permission` VALUES ('2', '105');
INSERT INTO `role_permission` VALUES ('2', '106');
INSERT INTO `role_permission` VALUES ('2', '250');
INSERT INTO `role_permission` VALUES ('2', '251');
INSERT INTO `role_permission` VALUES ('2', '253');
INSERT INTO `role_permission` VALUES ('2', '258');
INSERT INTO `role_permission` VALUES ('2', '260');
INSERT INTO `role_permission` VALUES ('2', '264');
INSERT INTO `role_permission` VALUES ('2', '265');
INSERT INTO `role_permission` VALUES ('2', '266');
INSERT INTO `role_permission` VALUES ('2', '268');
INSERT INTO `role_permission` VALUES ('2', '269');
INSERT INTO `role_permission` VALUES ('2', '270');
INSERT INTO `role_permission` VALUES ('2', '271');
INSERT INTO `role_permission` VALUES ('2', '272');
INSERT INTO `role_permission` VALUES ('2', '273');
INSERT INTO `role_permission` VALUES ('2', '274');
INSERT INTO `role_permission` VALUES ('2', '275');
INSERT INTO `role_permission` VALUES ('2', '276');
INSERT INTO `role_permission` VALUES ('2', '278');
INSERT INTO `role_permission` VALUES ('2', '279');
INSERT INTO `role_permission` VALUES ('2', '283');
INSERT INTO `role_permission` VALUES ('2', '284');
INSERT INTO `role_permission` VALUES ('2', '285');
INSERT INTO `role_permission` VALUES ('2', '286');
INSERT INTO `role_permission` VALUES ('2', '287');
INSERT INTO `role_permission` VALUES ('2', '288');
INSERT INTO `role_permission` VALUES ('2', '289');
INSERT INTO `role_permission` VALUES ('2', '291');
INSERT INTO `role_permission` VALUES ('2', '292');
INSERT INTO `role_permission` VALUES ('2', '293');
INSERT INTO `role_permission` VALUES ('2', '294');
INSERT INTO `role_permission` VALUES ('2', '295');
INSERT INTO `role_permission` VALUES ('2', '296');
INSERT INTO `role_permission` VALUES ('2', '297');
INSERT INTO `role_permission` VALUES ('2', '298');
INSERT INTO `role_permission` VALUES ('2', '301');
INSERT INTO `role_permission` VALUES ('2', '302');
INSERT INTO `role_permission` VALUES ('2', '303');
INSERT INTO `role_permission` VALUES ('2', '304');
INSERT INTO `role_permission` VALUES ('2', '305');
INSERT INTO `role_permission` VALUES ('2', '306');
INSERT INTO `role_permission` VALUES ('2', '307');
INSERT INTO `role_permission` VALUES ('2', '308');
INSERT INTO `role_permission` VALUES ('2', '311');
INSERT INTO `role_permission` VALUES ('2', '314');
INSERT INTO `role_permission` VALUES ('2', '315');
INSERT INTO `role_permission` VALUES ('2', '316');
INSERT INTO `role_permission` VALUES ('2', '317');
INSERT INTO `role_permission` VALUES ('2', '318');
INSERT INTO `role_permission` VALUES ('2', '319');
INSERT INTO `role_permission` VALUES ('2', '320');
INSERT INTO `role_permission` VALUES ('2', '321');
INSERT INTO `role_permission` VALUES ('2', '322');
INSERT INTO `role_permission` VALUES ('2', '323');
INSERT INTO `role_permission` VALUES ('2', '324');
INSERT INTO `role_permission` VALUES ('2', '325');
INSERT INTO `role_permission` VALUES ('2', '326');
INSERT INTO `role_permission` VALUES ('2', '327');
INSERT INTO `role_permission` VALUES ('2', '328');
INSERT INTO `role_permission` VALUES ('2', '329');
INSERT INTO `role_permission` VALUES ('2', '330');
INSERT INTO `role_permission` VALUES ('2', '331');
INSERT INTO `role_permission` VALUES ('2', '332');
INSERT INTO `role_permission` VALUES ('2', '333');
INSERT INTO `role_permission` VALUES ('2', '334');
INSERT INTO `role_permission` VALUES ('2', '335');
INSERT INTO `role_permission` VALUES ('2', '336');
INSERT INTO `role_permission` VALUES ('2', '337');
INSERT INTO `role_permission` VALUES ('2', '341');
INSERT INTO `role_permission` VALUES ('2', '347');
INSERT INTO `role_permission` VALUES ('2', '349');
INSERT INTO `role_permission` VALUES ('2', '350');
INSERT INTO `role_permission` VALUES ('2', '351');
INSERT INTO `role_permission` VALUES ('2', '352');
INSERT INTO `role_permission` VALUES ('2', '353');
INSERT INTO `role_permission` VALUES ('2', '354');
INSERT INTO `role_permission` VALUES ('2', '355');
INSERT INTO `role_permission` VALUES ('3', '1');
INSERT INTO `role_permission` VALUES ('3', '2');
INSERT INTO `role_permission` VALUES ('3', '3');
INSERT INTO `role_permission` VALUES ('3', '4');
INSERT INTO `role_permission` VALUES ('3', '5');
INSERT INTO `role_permission` VALUES ('3', '6');
INSERT INTO `role_permission` VALUES ('3', '7');
INSERT INTO `role_permission` VALUES ('3', '8');
INSERT INTO `role_permission` VALUES ('3', '9');
INSERT INTO `role_permission` VALUES ('3', '10');
INSERT INTO `role_permission` VALUES ('3', '11');
INSERT INTO `role_permission` VALUES ('3', '12');
INSERT INTO `role_permission` VALUES ('3', '13');
INSERT INTO `role_permission` VALUES ('3', '14');
INSERT INTO `role_permission` VALUES ('3', '15');
INSERT INTO `role_permission` VALUES ('3', '16');
INSERT INTO `role_permission` VALUES ('3', '17');
INSERT INTO `role_permission` VALUES ('3', '18');
INSERT INTO `role_permission` VALUES ('3', '19');
INSERT INTO `role_permission` VALUES ('3', '20');
INSERT INTO `role_permission` VALUES ('3', '21');
INSERT INTO `role_permission` VALUES ('3', '22');
INSERT INTO `role_permission` VALUES ('3', '23');
INSERT INTO `role_permission` VALUES ('3', '24');
INSERT INTO `role_permission` VALUES ('3', '25');
INSERT INTO `role_permission` VALUES ('3', '26');
INSERT INTO `role_permission` VALUES ('3', '27');
INSERT INTO `role_permission` VALUES ('3', '28');
INSERT INTO `role_permission` VALUES ('3', '29');
INSERT INTO `role_permission` VALUES ('3', '30');
INSERT INTO `role_permission` VALUES ('3', '31');
INSERT INTO `role_permission` VALUES ('3', '32');
INSERT INTO `role_permission` VALUES ('3', '33');
INSERT INTO `role_permission` VALUES ('3', '34');
INSERT INTO `role_permission` VALUES ('3', '35');
INSERT INTO `role_permission` VALUES ('3', '36');
INSERT INTO `role_permission` VALUES ('3', '37');
INSERT INTO `role_permission` VALUES ('3', '38');
INSERT INTO `role_permission` VALUES ('3', '39');
INSERT INTO `role_permission` VALUES ('3', '40');
INSERT INTO `role_permission` VALUES ('3', '41');
INSERT INTO `role_permission` VALUES ('3', '42');
INSERT INTO `role_permission` VALUES ('3', '43');
INSERT INTO `role_permission` VALUES ('3', '44');
INSERT INTO `role_permission` VALUES ('3', '45');
INSERT INTO `role_permission` VALUES ('3', '46');
INSERT INTO `role_permission` VALUES ('3', '47');
INSERT INTO `role_permission` VALUES ('3', '48');
INSERT INTO `role_permission` VALUES ('3', '49');
INSERT INTO `role_permission` VALUES ('3', '50');
INSERT INTO `role_permission` VALUES ('3', '51');
INSERT INTO `role_permission` VALUES ('3', '52');
INSERT INTO `role_permission` VALUES ('3', '53');
INSERT INTO `role_permission` VALUES ('3', '54');
INSERT INTO `role_permission` VALUES ('3', '55');
INSERT INTO `role_permission` VALUES ('3', '56');
INSERT INTO `role_permission` VALUES ('3', '57');
INSERT INTO `role_permission` VALUES ('3', '58');
INSERT INTO `role_permission` VALUES ('3', '59');
INSERT INTO `role_permission` VALUES ('3', '60');
INSERT INTO `role_permission` VALUES ('3', '61');
INSERT INTO `role_permission` VALUES ('3', '62');
INSERT INTO `role_permission` VALUES ('3', '63');
INSERT INTO `role_permission` VALUES ('3', '64');
INSERT INTO `role_permission` VALUES ('3', '65');
INSERT INTO `role_permission` VALUES ('3', '66');
INSERT INTO `role_permission` VALUES ('3', '67');
INSERT INTO `role_permission` VALUES ('3', '68');
INSERT INTO `role_permission` VALUES ('3', '69');
INSERT INTO `role_permission` VALUES ('3', '70');
INSERT INTO `role_permission` VALUES ('3', '71');
INSERT INTO `role_permission` VALUES ('3', '72');
INSERT INTO `role_permission` VALUES ('3', '73');
INSERT INTO `role_permission` VALUES ('3', '74');
INSERT INTO `role_permission` VALUES ('3', '75');
INSERT INTO `role_permission` VALUES ('3', '76');
INSERT INTO `role_permission` VALUES ('3', '77');
INSERT INTO `role_permission` VALUES ('3', '78');
INSERT INTO `role_permission` VALUES ('3', '79');
INSERT INTO `role_permission` VALUES ('3', '80');
INSERT INTO `role_permission` VALUES ('3', '81');
INSERT INTO `role_permission` VALUES ('3', '82');
INSERT INTO `role_permission` VALUES ('3', '83');
INSERT INTO `role_permission` VALUES ('3', '84');
INSERT INTO `role_permission` VALUES ('3', '85');
INSERT INTO `role_permission` VALUES ('3', '86');
INSERT INTO `role_permission` VALUES ('3', '87');
INSERT INTO `role_permission` VALUES ('3', '88');
INSERT INTO `role_permission` VALUES ('3', '89');
INSERT INTO `role_permission` VALUES ('3', '90');
INSERT INTO `role_permission` VALUES ('3', '91');
INSERT INTO `role_permission` VALUES ('3', '92');
INSERT INTO `role_permission` VALUES ('3', '93');
INSERT INTO `role_permission` VALUES ('3', '94');
INSERT INTO `role_permission` VALUES ('3', '95');
INSERT INTO `role_permission` VALUES ('3', '96');
INSERT INTO `role_permission` VALUES ('3', '97');
INSERT INTO `role_permission` VALUES ('3', '98');
INSERT INTO `role_permission` VALUES ('3', '99');
INSERT INTO `role_permission` VALUES ('3', '100');
INSERT INTO `role_permission` VALUES ('3', '101');
INSERT INTO `role_permission` VALUES ('3', '102');
INSERT INTO `role_permission` VALUES ('3', '104');
INSERT INTO `role_permission` VALUES ('3', '105');
INSERT INTO `role_permission` VALUES ('3', '106');
INSERT INTO `role_permission` VALUES ('3', '250');
INSERT INTO `role_permission` VALUES ('3', '251');
INSERT INTO `role_permission` VALUES ('3', '252');
INSERT INTO `role_permission` VALUES ('3', '253');
INSERT INTO `role_permission` VALUES ('3', '254');
INSERT INTO `role_permission` VALUES ('3', '255');
INSERT INTO `role_permission` VALUES ('3', '256');
INSERT INTO `role_permission` VALUES ('3', '257');
INSERT INTO `role_permission` VALUES ('3', '258');
INSERT INTO `role_permission` VALUES ('3', '259');
INSERT INTO `role_permission` VALUES ('3', '260');
INSERT INTO `role_permission` VALUES ('3', '261');
INSERT INTO `role_permission` VALUES ('3', '262');
INSERT INTO `role_permission` VALUES ('3', '263');
INSERT INTO `role_permission` VALUES ('3', '264');
INSERT INTO `role_permission` VALUES ('3', '265');
INSERT INTO `role_permission` VALUES ('3', '266');
INSERT INTO `role_permission` VALUES ('3', '267');
INSERT INTO `role_permission` VALUES ('3', '268');
INSERT INTO `role_permission` VALUES ('3', '269');
INSERT INTO `role_permission` VALUES ('3', '270');
INSERT INTO `role_permission` VALUES ('3', '271');
INSERT INTO `role_permission` VALUES ('3', '272');
INSERT INTO `role_permission` VALUES ('3', '273');
INSERT INTO `role_permission` VALUES ('3', '274');
INSERT INTO `role_permission` VALUES ('3', '275');
INSERT INTO `role_permission` VALUES ('3', '276');
INSERT INTO `role_permission` VALUES ('3', '277');
INSERT INTO `role_permission` VALUES ('3', '278');
INSERT INTO `role_permission` VALUES ('3', '279');
INSERT INTO `role_permission` VALUES ('3', '280');
INSERT INTO `role_permission` VALUES ('3', '281');
INSERT INTO `role_permission` VALUES ('3', '282');
INSERT INTO `role_permission` VALUES ('3', '283');
INSERT INTO `role_permission` VALUES ('3', '284');
INSERT INTO `role_permission` VALUES ('3', '285');
INSERT INTO `role_permission` VALUES ('3', '286');
INSERT INTO `role_permission` VALUES ('3', '287');
INSERT INTO `role_permission` VALUES ('3', '288');
INSERT INTO `role_permission` VALUES ('3', '289');
INSERT INTO `role_permission` VALUES ('3', '290');
INSERT INTO `role_permission` VALUES ('3', '291');
INSERT INTO `role_permission` VALUES ('3', '292');
INSERT INTO `role_permission` VALUES ('3', '293');
INSERT INTO `role_permission` VALUES ('3', '294');
INSERT INTO `role_permission` VALUES ('3', '295');
INSERT INTO `role_permission` VALUES ('3', '296');
INSERT INTO `role_permission` VALUES ('3', '297');
INSERT INTO `role_permission` VALUES ('3', '298');
INSERT INTO `role_permission` VALUES ('3', '299');
INSERT INTO `role_permission` VALUES ('3', '300');
INSERT INTO `role_permission` VALUES ('3', '301');
INSERT INTO `role_permission` VALUES ('3', '302');
INSERT INTO `role_permission` VALUES ('3', '303');
INSERT INTO `role_permission` VALUES ('3', '304');
INSERT INTO `role_permission` VALUES ('3', '305');
INSERT INTO `role_permission` VALUES ('3', '307');
INSERT INTO `role_permission` VALUES ('3', '308');
INSERT INTO `role_permission` VALUES ('3', '310');
INSERT INTO `role_permission` VALUES ('3', '311');
INSERT INTO `role_permission` VALUES ('3', '312');
INSERT INTO `role_permission` VALUES ('3', '313');
INSERT INTO `role_permission` VALUES ('3', '314');
INSERT INTO `role_permission` VALUES ('3', '315');
INSERT INTO `role_permission` VALUES ('3', '316');
INSERT INTO `role_permission` VALUES ('3', '318');
INSERT INTO `role_permission` VALUES ('3', '320');
INSERT INTO `role_permission` VALUES ('3', '322');
INSERT INTO `role_permission` VALUES ('3', '325');
INSERT INTO `role_permission` VALUES ('3', '326');
INSERT INTO `role_permission` VALUES ('3', '327');
INSERT INTO `role_permission` VALUES ('3', '328');
INSERT INTO `role_permission` VALUES ('3', '329');
INSERT INTO `role_permission` VALUES ('3', '330');
INSERT INTO `role_permission` VALUES ('3', '331');
INSERT INTO `role_permission` VALUES ('3', '332');
INSERT INTO `role_permission` VALUES ('3', '333');
INSERT INTO `role_permission` VALUES ('3', '334');
INSERT INTO `role_permission` VALUES ('3', '335');
INSERT INTO `role_permission` VALUES ('3', '336');
INSERT INTO `role_permission` VALUES ('3', '338');
INSERT INTO `role_permission` VALUES ('3', '339');
INSERT INTO `role_permission` VALUES ('3', '340');
INSERT INTO `role_permission` VALUES ('3', '341');
INSERT INTO `role_permission` VALUES ('3', '342');
INSERT INTO `role_permission` VALUES ('3', '343');
INSERT INTO `role_permission` VALUES ('3', '344');
INSERT INTO `role_permission` VALUES ('3', '345');
INSERT INTO `role_permission` VALUES ('3', '346');
INSERT INTO `role_permission` VALUES ('3', '347');
INSERT INTO `role_permission` VALUES ('3', '348');
INSERT INTO `role_permission` VALUES ('3', '349');
INSERT INTO `role_permission` VALUES ('3', '350');
INSERT INTO `role_permission` VALUES ('3', '351');
INSERT INTO `role_permission` VALUES ('3', '352');
INSERT INTO `role_permission` VALUES ('3', '353');
INSERT INTO `role_permission` VALUES ('3', '354');
INSERT INTO `role_permission` VALUES ('3', '355');
INSERT INTO `role_permission` VALUES ('5', '260');
INSERT INTO `role_permission` VALUES ('5', '268');
INSERT INTO `role_permission` VALUES ('5', '279');
INSERT INTO `role_permission` VALUES ('5', '281');
INSERT INTO `role_permission` VALUES ('5', '283');
INSERT INTO `role_permission` VALUES ('5', '285');
INSERT INTO `role_permission` VALUES ('5', '287');
INSERT INTO `role_permission` VALUES ('5', '289');
INSERT INTO `role_permission` VALUES ('5', '291');
INSERT INTO `role_permission` VALUES ('5', '305');
INSERT INTO `role_permission` VALUES ('5', '306');
INSERT INTO `role_permission` VALUES ('5', '307');
INSERT INTO `role_permission` VALUES ('5', '308');
INSERT INTO `role_permission` VALUES ('5', '309');
INSERT INTO `role_permission` VALUES ('5', '310');
INSERT INTO `role_permission` VALUES ('5', '314');
INSERT INTO `role_permission` VALUES ('5', '315');
INSERT INTO `role_permission` VALUES ('5', '316');
INSERT INTO `role_permission` VALUES ('5', '317');
INSERT INTO `role_permission` VALUES ('5', '327');
INSERT INTO `role_permission` VALUES ('5', '328');
INSERT INTO `role_permission` VALUES ('5', '333');
INSERT INTO `role_permission` VALUES ('5', '334');
INSERT INTO `role_permission` VALUES ('5', '335');
INSERT INTO `role_permission` VALUES ('5', '336');
INSERT INTO `role_permission` VALUES ('5', '344');
INSERT INTO `role_permission` VALUES ('5', '347');
INSERT INTO `role_permission` VALUES ('5', '350');
INSERT INTO `role_permission` VALUES ('5', '351');
INSERT INTO `role_permission` VALUES ('5', '354');
INSERT INTO `role_permission` VALUES ('6', '275');
INSERT INTO `role_permission` VALUES ('6', '277');
INSERT INTO `role_permission` VALUES ('6', '279');
INSERT INTO `role_permission` VALUES ('6', '281');
INSERT INTO `role_permission` VALUES ('6', '318');
INSERT INTO `role_permission` VALUES ('6', '319');
INSERT INTO `role_permission` VALUES ('6', '320');
INSERT INTO `role_permission` VALUES ('6', '322');
INSERT INTO `role_permission` VALUES ('6', '324');
INSERT INTO `role_permission` VALUES ('6', '325');
INSERT INTO `role_permission` VALUES ('6', '326');
INSERT INTO `role_permission` VALUES ('6', '327');
INSERT INTO `role_permission` VALUES ('6', '328');
INSERT INTO `role_permission` VALUES ('6', '353');
INSERT INTO `role_permission` VALUES ('7', '301');
INSERT INTO `role_permission` VALUES ('7', '302');
INSERT INTO `role_permission` VALUES ('7', '303');
INSERT INTO `role_permission` VALUES ('7', '304');
INSERT INTO `role_permission` VALUES ('7', '352');
INSERT INTO `role_permission` VALUES ('8', '318');
INSERT INTO `role_permission` VALUES ('8', '319');
INSERT INTO `role_permission` VALUES ('8', '320');
INSERT INTO `role_permission` VALUES ('8', '321');
INSERT INTO `role_permission` VALUES ('8', '322');
INSERT INTO `role_permission` VALUES ('8', '323');
INSERT INTO `role_permission` VALUES ('8', '324');
INSERT INTO `role_permission` VALUES ('8', '325');
INSERT INTO `role_permission` VALUES ('8', '326');
INSERT INTO `role_permission` VALUES ('8', '327');
INSERT INTO `role_permission` VALUES ('8', '328');
INSERT INTO `role_permission` VALUES ('8', '353');
INSERT INTO `role_permission` VALUES ('9', '256');
INSERT INTO `role_permission` VALUES ('9', '258');
INSERT INTO `role_permission` VALUES ('9', '260');
INSERT INTO `role_permission` VALUES ('9', '264');
INSERT INTO `role_permission` VALUES ('9', '268');
INSERT INTO `role_permission` VALUES ('9', '272');
INSERT INTO `role_permission` VALUES ('9', '279');
INSERT INTO `role_permission` VALUES ('9', '287');
INSERT INTO `role_permission` VALUES ('9', '291');
INSERT INTO `role_permission` VALUES ('9', '294');
INSERT INTO `role_permission` VALUES ('9', '296');
INSERT INTO `role_permission` VALUES ('9', '299');
INSERT INTO `role_permission` VALUES ('9', '301');
INSERT INTO `role_permission` VALUES ('9', '302');
INSERT INTO `role_permission` VALUES ('9', '304');
INSERT INTO `role_permission` VALUES ('9', '305');
INSERT INTO `role_permission` VALUES ('9', '307');
INSERT INTO `role_permission` VALUES ('9', '314');
INSERT INTO `role_permission` VALUES ('9', '316');
INSERT INTO `role_permission` VALUES ('9', '317');
INSERT INTO `role_permission` VALUES ('9', '318');
INSERT INTO `role_permission` VALUES ('9', '320');
INSERT INTO `role_permission` VALUES ('9', '321');
INSERT INTO `role_permission` VALUES ('9', '322');
INSERT INTO `role_permission` VALUES ('9', '323');
INSERT INTO `role_permission` VALUES ('9', '326');
INSERT INTO `role_permission` VALUES ('9', '327');
INSERT INTO `role_permission` VALUES ('9', '329');
INSERT INTO `role_permission` VALUES ('9', '330');
INSERT INTO `role_permission` VALUES ('9', '331');
INSERT INTO `role_permission` VALUES ('9', '332');
INSERT INTO `role_permission` VALUES ('9', '333');
INSERT INTO `role_permission` VALUES ('9', '334');
INSERT INTO `role_permission` VALUES ('9', '336');
INSERT INTO `role_permission` VALUES ('9', '349');
INSERT INTO `role_permission` VALUES ('9', '352');
INSERT INTO `role_permission` VALUES ('9', '353');
INSERT INTO `role_permission` VALUES ('9', '354');
INSERT INTO `role_permission` VALUES ('9', '355');
INSERT INTO `role_permission` VALUES ('10', '258');
INSERT INTO `role_permission` VALUES ('10', '327');
INSERT INTO `role_permission` VALUES ('10', '328');
INSERT INTO `role_permission` VALUES ('10', '329');
INSERT INTO `role_permission` VALUES ('10', '330');
INSERT INTO `role_permission` VALUES ('10', '331');
INSERT INTO `role_permission` VALUES ('10', '332');
INSERT INTO `role_permission` VALUES ('10', '349');
INSERT INTO `role_permission` VALUES ('11', '250');
INSERT INTO `role_permission` VALUES ('11', '268');
INSERT INTO `role_permission` VALUES ('11', '287');
INSERT INTO `role_permission` VALUES ('11', '291');
INSERT INTO `role_permission` VALUES ('11', '294');
INSERT INTO `role_permission` VALUES ('11', '304');
INSERT INTO `role_permission` VALUES ('11', '314');
INSERT INTO `role_permission` VALUES ('11', '316');
INSERT INTO `role_permission` VALUES ('11', '320');
INSERT INTO `role_permission` VALUES ('11', '322');
INSERT INTO `role_permission` VALUES ('11', '326');
INSERT INTO `role_permission` VALUES ('11', '329');
INSERT INTO `role_permission` VALUES ('11', '330');
INSERT INTO `role_permission` VALUES ('11', '331');
INSERT INTO `role_permission` VALUES ('11', '332');
INSERT INTO `role_permission` VALUES ('11', '333');
INSERT INTO `role_permission` VALUES ('11', '335');
INSERT INTO `role_permission` VALUES ('11', '338');
INSERT INTO `role_permission` VALUES ('11', '341');
INSERT INTO `role_permission` VALUES ('11', '343');
INSERT INTO `role_permission` VALUES ('11', '344');
INSERT INTO `role_permission` VALUES ('11', '347');
INSERT INTO `role_permission` VALUES ('11', '349');
INSERT INTO `role_permission` VALUES ('11', '350');
INSERT INTO `role_permission` VALUES ('11', '352');
INSERT INTO `role_permission` VALUES ('11', '353');
INSERT INTO `role_permission` VALUES ('11', '354');
INSERT INTO `role_permission` VALUES ('11', '355');
INSERT INTO `role_permission` VALUES ('12', '334');
INSERT INTO `role_permission` VALUES ('12', '335');
INSERT INTO `role_permission` VALUES ('12', '336');
INSERT INTO `role_permission` VALUES ('13', '260');
INSERT INTO `role_permission` VALUES ('13', '279');
INSERT INTO `role_permission` VALUES ('13', '281');
INSERT INTO `role_permission` VALUES ('13', '283');
INSERT INTO `role_permission` VALUES ('13', '287');
INSERT INTO `role_permission` VALUES ('13', '291');
INSERT INTO `role_permission` VALUES ('13', '305');
INSERT INTO `role_permission` VALUES ('13', '306');
INSERT INTO `role_permission` VALUES ('13', '314');
INSERT INTO `role_permission` VALUES ('13', '315');
INSERT INTO `role_permission` VALUES ('13', '316');
INSERT INTO `role_permission` VALUES ('13', '317');
INSERT INTO `role_permission` VALUES ('13', '327');
INSERT INTO `role_permission` VALUES ('13', '333');
INSERT INTO `role_permission` VALUES ('13', '344');
INSERT INTO `role_permission` VALUES ('13', '347');
INSERT INTO `role_permission` VALUES ('13', '350');
INSERT INTO `role_permission` VALUES ('13', '351');
INSERT INTO `role_permission` VALUES ('13', '354');

-- ----------------------------
-- Table structure for `sensitive_words`
-- ----------------------------
DROP TABLE IF EXISTS `sensitive_words`;
CREATE TABLE `sensitive_words` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(32) NOT NULL DEFAULT '',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `word_pinyin` varchar(64) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sensitive_words
-- ----------------------------
INSERT INTO `sensitive_words` VALUES ('1', '发票', '1', 'fapiao');

-- ----------------------------
-- Table structure for `session`
-- ----------------------------
DROP TABLE IF EXISTS `session`;
CREATE TABLE `session` (
  `id` varchar(33) NOT NULL,
  `accountId` int(11) NOT NULL,
  `expireAt` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of session
-- ----------------------------
INSERT INTO `session` VALUES ('01c566e253d54a65ae37d526de84e574', '1', '1535785751998');
INSERT INTO `session` VALUES ('030f26fc3968426dbdfb860e6f79d28a', '1', '1536997365876');
INSERT INTO `session` VALUES ('051cb132c37e4e02be1c148c3b83c042', '1', '1537179592848');
INSERT INTO `session` VALUES ('07006cdf46fa456495b70ca75137b63e', '1', '1540955810542');
INSERT INTO `session` VALUES ('08a9973639cb4466839c5f6a02557664', '1', '1536326699715');
INSERT INTO `session` VALUES ('09b48be4418347aab2365da79db7b5fc', '1', '1539306844154');
INSERT INTO `session` VALUES ('0bb3f1c9ec1b4b91a192e82ca5eaf7fa', '1', '1540901444773');
INSERT INTO `session` VALUES ('0c9f59a0ad8c49bb85c5ce383b0a7bb5', '1', '1537179636253');
INSERT INTO `session` VALUES ('0d71694ab9f94594a929687933a6e67e', '1', '1536385702736');
INSERT INTO `session` VALUES ('0e6f223167df40f28cb38a1728785456', '1', '1539694112653');
INSERT INTO `session` VALUES ('144b6a072b354f3e90304be65880074f', '1', '1536106161479');
INSERT INTO `session` VALUES ('148af63a154c4d758d37434b05340ad7', '1', '1536298307903');
INSERT INTO `session` VALUES ('15c6cfe4724d458cb9f07b13d6784999', '1', '1539694121017');
INSERT INTO `session` VALUES ('15dec4cfc77445c289c452c8bad1a309', '1', '1535784943854');
INSERT INTO `session` VALUES ('19878d4b39b44a828f7d012e1aada11d', '3', '1535249029301');
INSERT INTO `session` VALUES ('1a86035aaa304320944e446d27857d35', '1', '1535343531617');
INSERT INTO `session` VALUES ('1b09960a0df64b59b90a55145952bb3b', '1', '1537180272982');
INSERT INTO `session` VALUES ('1c759be287ff4d7598e16c5d53e1f7e9', '1', '1536279537101');
INSERT INTO `session` VALUES ('1ed75f8914d8447db9e43159a2379564', '1', '1539244726217');
INSERT INTO `session` VALUES ('2142952a3ede4602a537a4b317e5053e', '1', '1536202803272');
INSERT INTO `session` VALUES ('22d97b9ad1094f58a8a9d917516c8149', '1', '1537847210394');
INSERT INTO `session` VALUES ('24ea64b304f54c71a2a2b5a18a8ae4df', '1', '1539250701852');
INSERT INTO `session` VALUES ('28a741e52aa2412db14c186b6ca7a86d', '1', '1535263862609');
INSERT INTO `session` VALUES ('2b47e0c103304d438187e9ac1d80922b', '1', '1539304423203');
INSERT INTO `session` VALUES ('2c076f03aa0748758d529ec24af50ae1', '1', '1539305931887');
INSERT INTO `session` VALUES ('33d9d7e11fa64669984b1611a865ddd9', '1', '1535685984059');
INSERT INTO `session` VALUES ('37f0141de1f3451f8eec96c0ad24231b', '1', '1539395556688');
INSERT INTO `session` VALUES ('3960667ea0f44d27a521917e2b769173', '1', '1536293971400');
INSERT INTO `session` VALUES ('3a6fcff779d44c799884a3dd0013cf16', '1', '1537011850293');
INSERT INTO `session` VALUES ('3af1a588f3274b4c9127efcaebfd9333', '1', '1535434285133');
INSERT INTO `session` VALUES ('3b8fc955f4314e37a54caa6786b258cc', '1', '1535676206506');
INSERT INTO `session` VALUES ('3ccdadb6e6ad476c9c01510bc846e7c8', '1', '1539694112652');
INSERT INTO `session` VALUES ('3d5738f52db542c7ad8af9e256a02790', '1', '1535246422084');
INSERT INTO `session` VALUES ('42b8c062a31540c79832a04a579cb90c', '1', '1540096423973');
INSERT INTO `session` VALUES ('45b41e4e4a4c44f7b08eb77f5be67fc6', '1', '1536126480345');
INSERT INTO `session` VALUES ('4aec863aa4ae47dbaca15481a0a0102a', '7', '1540965700749');
INSERT INTO `session` VALUES ('4b14f31fa52e4b9694974a80f90c8ed0', '1', '1536229841186');
INSERT INTO `session` VALUES ('4c540f80bfd0403d876246cb6187636e', '1', '1540630636223');
INSERT INTO `session` VALUES ('4c84c3dd381346ebb1f467fb0105fb57', '1', '1539745313417');
INSERT INTO `session` VALUES ('4e7b84c9f3814b228aff9e4364e303c9', '1', '1535248291969');
INSERT INTO `session` VALUES ('512b63941a804eba91a6ba69dc9eb9ff', '1', '1540951750295');
INSERT INTO `session` VALUES ('517f4b64f59443a489acd3796364d9bd', '1', '1539449755114');
INSERT INTO `session` VALUES ('51a794422b20487daf869b03029d61f5', '1', '1535246131560');
INSERT INTO `session` VALUES ('51e91cc28dba4b35b6ac19c46113b59e', '1', '1540804212241');
INSERT INTO `session` VALUES ('549cb68c323642f4974c7f8bae737da4', '1', '1536125887044');
INSERT INTO `session` VALUES ('5524d5303eee4d88961e3030de9842cb', '1', '1538358665432');
INSERT INTO `session` VALUES ('57b58621776c44e4947f5f8eeb27d7b2', '1', '1535167873809');
INSERT INTO `session` VALUES ('5917c84b7f9a43858b280095f2c7092f', '1', '1535675722836');
INSERT INTO `session` VALUES ('59cdd1642f00443c83c990d24fd73dfa', '1', '1535676249916');
INSERT INTO `session` VALUES ('5a140e82700840c0a4528e5f83441cdb', '1', '1535184835894');
INSERT INTO `session` VALUES ('5f4fc3206dea43c38a009b8fc8ebff9f', '1', '1537009975608');
INSERT INTO `session` VALUES ('63d8572d62884adb9f3ac377b8316952', '1', '1540571186438');
INSERT INTO `session` VALUES ('661e7163a2c048caa07773bea8a7e434', '1', '1539256460962');
INSERT INTO `session` VALUES ('67d2032a7b4a426db9bc227db5816bd2', '1', '1536336872002');
INSERT INTO `session` VALUES ('69a51cd71c1e4beaa20e172f484ab822', '1', '1536453751322');
INSERT INTO `session` VALUES ('6d75e582d2644c5a8af82f5d478bbae8', '1', '1535414912317');
INSERT INTO `session` VALUES ('73810580d6004bf4b7f1eefe36c255e4', '1', '1536227430437');
INSERT INTO `session` VALUES ('762346af4c314fd5aff0a21d65407820', '1', '1536160268636');
INSERT INTO `session` VALUES ('76456eb77b674d5d8097a5366adb54bd', '1', '1535673570744');
INSERT INTO `session` VALUES ('7abf5ea419904aa5bfe6027af51443cd', '1', '1536601742440');
INSERT INTO `session` VALUES ('7b54eba429c9469e876241a78235d9c6', '1', '1535188430214');
INSERT INTO `session` VALUES ('7e481a8bd4ee46ee8af7f58dcfc238e1', '1', '1536470997462');
INSERT INTO `session` VALUES ('7efb38f5b0cc484fb78dcac657507f25', '1', '1535428790335');
INSERT INTO `session` VALUES ('81e75f626fd74398a9508737037a5b0c', '1', '1535246273253');
INSERT INTO `session` VALUES ('8a6996a7cf8641fd98b509a8ebafe8b2', '1', '1535442236282');
INSERT INTO `session` VALUES ('8cfb06003c4f4ad49a1ac7f72f492f89', '1', '1535429576752');
INSERT INTO `session` VALUES ('8fd005938d354b4d80ff51c53060b616', '1', '1536506266542');
INSERT INTO `session` VALUES ('92a82920893d435cac9f4d5aacb96954', '1', '1537883273478');
INSERT INTO `session` VALUES ('9526368bc9924da58a8b6fd4b8fb60ff', '1', '1536065326830');
INSERT INTO `session` VALUES ('994969c77ce34c98a623509531877e6c', '1', '1536932932812');
INSERT INTO `session` VALUES ('99a18bdcf88f45b280124f991fc82e6f', '1', '1536476213646');
INSERT INTO `session` VALUES ('9a8fe5d8034e471786898d31ca65aeae', '1', '1539694112659');
INSERT INTO `session` VALUES ('9d936cfde6fb44fb8fae38f7d47e779e', '1', '1535245961918');
INSERT INTO `session` VALUES ('9faaba6e4da04abb9378920851a5ecc7', '1', '1539609668151');
INSERT INTO `session` VALUES ('a1b9d29ceb364685af16a06c9dc53d8a', '3', '1535253777630');
INSERT INTO `session` VALUES ('a354c1916eed4db2b9e97f9f52ca6af7', '1', '1535762884322');
INSERT INTO `session` VALUES ('a4032a799d344a508bb6d038c6888374', '1', '1535246555876');
INSERT INTO `session` VALUES ('a6dd080e1c8d42179b3b24145d8076c3', '1', '1540711159477');
INSERT INTO `session` VALUES ('ab98adc157464450aa119fe757b9dfbe', '1', '1535345133661');
INSERT INTO `session` VALUES ('af49cf58498a472aaef151b7e5054a9b', '1', '1535536998795');
INSERT INTO `session` VALUES ('b018ad7c531d4ee7859b159e4d46e833', '1', '1539917106953');
INSERT INTO `session` VALUES ('b07ebcf89ea34de1bae33cf5af84341a', '1', '1536196147044');
INSERT INTO `session` VALUES ('b53b1d6ef1b646ae9cef49d0fe33dcad', '1', '1535174284086');
INSERT INTO `session` VALUES ('b600b973ba8c414cab15684fe7f90f0c', '1', '1536300989514');
INSERT INTO `session` VALUES ('b7d6b5fa7b9543a1bb7ffee811ce0514', '1', '1539740374141');
INSERT INTO `session` VALUES ('b9ae1caf9dbe4e3b9481cd7cc5e5a76f', '1', '1535248231652');
INSERT INTO `session` VALUES ('ba1bc9ecfc52415991ba31b1106e8e01', '1', '1535246090942');
INSERT INTO `session` VALUES ('bb08d80eaedf415da1adc20f3dc48a72', '1', '1535173220144');
INSERT INTO `session` VALUES ('bd0ad63e19c340219f927310e42e86ad', '1', '1540698720848');
INSERT INTO `session` VALUES ('c710384c3e764aa880d72003c8ffc700', '1', '1536484621799');
INSERT INTO `session` VALUES ('c9021993d9414d8f96ffe05b01f60883', '1', '1539389983723');
INSERT INTO `session` VALUES ('c9c77de5afe5454193672fc57a07d601', '1', '1536298231370');
INSERT INTO `session` VALUES ('cb96de7abecc4170bb84db1844305812', '1', '1535247435313');
INSERT INTO `session` VALUES ('cbeeaeb838984177aca75d6fcd0e9d83', '1', '1536311850632');
INSERT INTO `session` VALUES ('ccd3394526f646a1b8963b0d38e490b5', '1', '1536193871681');
INSERT INTO `session` VALUES ('ce5d0636283b4e3c98529c38ce35bfe6', '1', '1538964787652');
INSERT INTO `session` VALUES ('d26de4d1c8754166a6574fc0f0c7bcab', '1', '1539411391170');
INSERT INTO `session` VALUES ('d3d78a7f30bb49b3b9ed2425b2fb5c06', '1', '1540056366311');
INSERT INTO `session` VALUES ('d413ac315272458c8b44bd44145e9a4b', '1', '1536193993485');
INSERT INTO `session` VALUES ('d647ec52ea714bb1916852c9a4f3dcf4', '1', '1535789665158');
INSERT INTO `session` VALUES ('d6c8a9201b464ea2844e841f981d5e11', '1', '1539324608964');
INSERT INTO `session` VALUES ('daed9dc4731e4eeabec30bad096885f2', '1', '1536386604965');
INSERT INTO `session` VALUES ('dc104468b126438e983c74a4fff7c289', '3', '1535249218076');
INSERT INTO `session` VALUES ('dd49177440e74b14bf897dd55678aea7', '1', '1536135170234');
INSERT INTO `session` VALUES ('e0480772e6e14f71aa7bf780cec4a64f', '1', '1536248099044');
INSERT INTO `session` VALUES ('e198b14463fb49b881ddbf1d5cd9e59e', '1', '1536623548938');
INSERT INTO `session` VALUES ('e2cd350d8cac4e2180017b674eac8650', '1', '1538633737938');
INSERT INTO `session` VALUES ('e300159d318c43f1a4b965d871ee3e39', '1', '1535247624431');
INSERT INTO `session` VALUES ('e61bcd826c0848a89cbe755a5dbf1bb5', '1', '1535187181070');
INSERT INTO `session` VALUES ('f0c03a0917aa4113855dd80f4ec6e280', '1', '1536202793561');
INSERT INTO `session` VALUES ('f10b85a2aa4647e4b90779fee22533a0', '1', '1535185156309');
INSERT INTO `session` VALUES ('f1aa8c6bafde41a3849405daea1afcd9', '1', '1535596691209');
INSERT INTO `session` VALUES ('f320d04016334c26b23e3ec52621f0bc', '1', '1535782012518');
INSERT INTO `session` VALUES ('f6ecf7c848684632a5e7d730b371aae9', '1', '1540805465724');
INSERT INTO `session` VALUES ('f7ec412586174942937dc88d12a831e0', '1', '1536115448165');
INSERT INTO `session` VALUES ('f88165744b054caba53347c73aff1570', '1', '1535192752502');
INSERT INTO `session` VALUES ('fc42b4627bfe4ee3911e234c83a3893e', '1', '1536106974903');

-- ----------------------------
-- Table structure for `task_list`
-- ----------------------------
DROP TABLE IF EXISTS `task_list`;
CREATE TABLE `task_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL,
  `refId` int(11) NOT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0初始，1成功，2失败',
  `msg` varchar(1000) DEFAULT '' COMMENT '用substring保证长度不超出范围',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_list
-- ----------------------------

-- ----------------------------
-- Table structure for `task_run_log`
-- ----------------------------
DROP TABLE IF EXISTS `task_run_log`;
CREATE TABLE `task_run_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taskName` varchar(50) NOT NULL,
  `createAt` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1084 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_run_log
-- ----------------------------
INSERT INTO `task_run_log` VALUES ('1', 'PageViewUpdateTask', '2018-05-05 21:00:00');
INSERT INTO `task_run_log` VALUES ('2', 'PageViewUpdateTask', '2018-05-05 22:00:00');
INSERT INTO `task_run_log` VALUES ('3', 'PageViewUpdateTask', '2018-05-05 23:00:00');
INSERT INTO `task_run_log` VALUES ('4', 'PageViewUpdateTask', '2018-05-06 00:00:00');
INSERT INTO `task_run_log` VALUES ('5', 'PageViewUpdateTask', '2018-05-06 10:00:00');
INSERT INTO `task_run_log` VALUES ('6', 'PageViewUpdateTask', '2018-05-06 11:00:00');
INSERT INTO `task_run_log` VALUES ('7', 'PageViewUpdateTask', '2018-05-06 13:00:00');
INSERT INTO `task_run_log` VALUES ('8', 'PageViewUpdateTask', '2018-05-06 16:00:00');
INSERT INTO `task_run_log` VALUES ('9', 'PageViewUpdateTask', '2018-05-06 17:00:00');
INSERT INTO `task_run_log` VALUES ('10', 'PageViewUpdateTask', '2018-05-06 20:00:56');
INSERT INTO `task_run_log` VALUES ('11', 'PageViewUpdateTask', '2018-05-06 21:00:00');
INSERT INTO `task_run_log` VALUES ('12', 'PageViewUpdateTask', '2018-05-06 22:00:00');
INSERT INTO `task_run_log` VALUES ('13', 'PageViewUpdateTask', '2018-05-06 23:00:00');
INSERT INTO `task_run_log` VALUES ('14', 'PageViewUpdateTask', '2018-05-07 12:00:00');
INSERT INTO `task_run_log` VALUES ('15', 'PageViewUpdateTask', '2018-05-07 13:00:00');
INSERT INTO `task_run_log` VALUES ('16', 'PageViewUpdateTask', '2018-06-07 23:00:00');
INSERT INTO `task_run_log` VALUES ('17', 'PageViewUpdateTask', '2018-06-08 00:00:00');
INSERT INTO `task_run_log` VALUES ('18', 'PageViewUpdateTask', '2018-06-08 01:00:00');
INSERT INTO `task_run_log` VALUES ('19', 'PageViewUpdateTask', '2018-06-08 02:00:00');
INSERT INTO `task_run_log` VALUES ('20', 'PageViewUpdateTask', '2018-06-08 03:00:00');
INSERT INTO `task_run_log` VALUES ('21', 'PageViewUpdateTask', '2018-06-08 04:00:00');
INSERT INTO `task_run_log` VALUES ('22', 'PageViewUpdateTask', '2018-06-08 05:00:00');
INSERT INTO `task_run_log` VALUES ('23', 'PageViewUpdateTask', '2018-06-08 06:00:00');
INSERT INTO `task_run_log` VALUES ('24', 'PageViewUpdateTask', '2018-06-08 07:00:00');
INSERT INTO `task_run_log` VALUES ('25', 'PageViewUpdateTask', '2018-06-08 08:00:00');
INSERT INTO `task_run_log` VALUES ('26', 'PageViewUpdateTask', '2018-06-08 09:00:00');
INSERT INTO `task_run_log` VALUES ('27', 'PageViewUpdateTask', '2018-06-08 10:00:00');
INSERT INTO `task_run_log` VALUES ('28', 'PageViewUpdateTask', '2018-06-21 16:00:00');
INSERT INTO `task_run_log` VALUES ('29', 'PageViewUpdateTask', '2018-06-27 10:00:00');
INSERT INTO `task_run_log` VALUES ('30', 'PageViewUpdateTask', '2018-06-27 11:00:00');
INSERT INTO `task_run_log` VALUES ('31', 'PageViewUpdateTask', '2018-06-27 12:00:00');
INSERT INTO `task_run_log` VALUES ('32', 'PageViewUpdateTask', '2018-06-27 13:00:00');
INSERT INTO `task_run_log` VALUES ('33', 'PageViewUpdateTask', '2018-06-27 14:00:00');
INSERT INTO `task_run_log` VALUES ('34', 'PageViewUpdateTask', '2018-06-27 18:00:00');
INSERT INTO `task_run_log` VALUES ('35', 'PageViewUpdateTask', '2018-06-27 19:00:00');
INSERT INTO `task_run_log` VALUES ('36', 'PageViewUpdateTask', '2018-06-27 20:00:00');
INSERT INTO `task_run_log` VALUES ('37', 'PageViewUpdateTask', '2018-07-01 22:00:00');
INSERT INTO `task_run_log` VALUES ('38', 'PageViewUpdateTask', '2018-07-01 23:00:00');
INSERT INTO `task_run_log` VALUES ('39', 'PageViewUpdateTask', '2018-07-02 00:00:00');
INSERT INTO `task_run_log` VALUES ('40', 'PageViewUpdateTask', '2018-07-02 10:00:00');
INSERT INTO `task_run_log` VALUES ('41', 'PageViewUpdateTask', '2018-07-02 11:00:00');
INSERT INTO `task_run_log` VALUES ('42', 'PageViewUpdateTask', '2018-07-02 12:00:00');
INSERT INTO `task_run_log` VALUES ('43', 'PageViewUpdateTask', '2018-07-02 13:00:00');
INSERT INTO `task_run_log` VALUES ('44', 'PageViewUpdateTask', '2018-07-06 11:00:00');
INSERT INTO `task_run_log` VALUES ('45', 'PageViewUpdateTask', '2018-07-06 12:00:00');
INSERT INTO `task_run_log` VALUES ('46', 'PageViewUpdateTask', '2018-07-06 13:00:00');
INSERT INTO `task_run_log` VALUES ('47', 'PageViewUpdateTask', '2018-07-06 15:00:00');
INSERT INTO `task_run_log` VALUES ('48', 'PageViewUpdateTask', '2018-07-06 16:00:00');
INSERT INTO `task_run_log` VALUES ('49', 'PageViewUpdateTask', '2018-07-06 17:00:00');
INSERT INTO `task_run_log` VALUES ('50', 'PageViewUpdateTask', '2018-07-06 18:00:00');
INSERT INTO `task_run_log` VALUES ('51', 'PageViewUpdateTask', '2018-07-06 19:00:00');
INSERT INTO `task_run_log` VALUES ('52', 'PageViewUpdateTask', '2018-07-06 20:00:00');
INSERT INTO `task_run_log` VALUES ('53', 'PageViewUpdateTask', '2018-07-06 21:00:12');
INSERT INTO `task_run_log` VALUES ('54', 'PageViewUpdateTask', '2018-07-06 22:00:00');
INSERT INTO `task_run_log` VALUES ('55', 'PageViewUpdateTask', '2018-07-06 23:00:00');
INSERT INTO `task_run_log` VALUES ('56', 'PageViewUpdateTask', '2018-07-07 00:00:00');
INSERT INTO `task_run_log` VALUES ('57', 'PageViewUpdateTask', '2018-07-07 01:00:00');
INSERT INTO `task_run_log` VALUES ('58', 'PageViewUpdateTask', '2018-07-07 10:00:00');
INSERT INTO `task_run_log` VALUES ('59', 'PageViewUpdateTask', '2018-07-07 11:00:00');
INSERT INTO `task_run_log` VALUES ('60', 'PageViewUpdateTask', '2018-07-07 12:00:00');
INSERT INTO `task_run_log` VALUES ('61', 'PageViewUpdateTask', '2018-07-07 13:00:00');
INSERT INTO `task_run_log` VALUES ('62', 'PageViewUpdateTask', '2018-07-07 14:00:00');
INSERT INTO `task_run_log` VALUES ('63', 'PageViewUpdateTask', '2018-07-07 15:00:00');
INSERT INTO `task_run_log` VALUES ('64', 'PageViewUpdateTask', '2018-07-07 16:00:00');
INSERT INTO `task_run_log` VALUES ('65', 'PageViewUpdateTask', '2018-07-07 17:00:00');
INSERT INTO `task_run_log` VALUES ('66', 'PageViewUpdateTask', '2018-07-07 18:00:00');
INSERT INTO `task_run_log` VALUES ('67', 'PageViewUpdateTask', '2018-07-07 21:00:00');
INSERT INTO `task_run_log` VALUES ('68', 'PageViewUpdateTask', '2018-07-07 22:00:00');
INSERT INTO `task_run_log` VALUES ('69', 'PageViewUpdateTask', '2018-07-07 23:00:00');
INSERT INTO `task_run_log` VALUES ('70', 'PageViewUpdateTask', '2018-07-08 00:00:00');
INSERT INTO `task_run_log` VALUES ('71', 'PageViewUpdateTask', '2018-07-08 01:00:00');
INSERT INTO `task_run_log` VALUES ('72', 'PageViewUpdateTask', '2018-07-08 09:00:00');
INSERT INTO `task_run_log` VALUES ('73', 'PageViewUpdateTask', '2018-07-08 10:00:00');
INSERT INTO `task_run_log` VALUES ('74', 'PageViewUpdateTask', '2018-07-08 11:00:00');
INSERT INTO `task_run_log` VALUES ('75', 'PageViewUpdateTask', '2018-07-08 12:00:00');
INSERT INTO `task_run_log` VALUES ('76', 'PageViewUpdateTask', '2018-07-08 13:00:00');
INSERT INTO `task_run_log` VALUES ('77', 'PageViewUpdateTask', '2018-07-08 14:00:00');
INSERT INTO `task_run_log` VALUES ('78', 'PageViewUpdateTask', '2018-07-08 15:00:00');
INSERT INTO `task_run_log` VALUES ('79', 'PageViewUpdateTask', '2018-07-08 16:00:00');
INSERT INTO `task_run_log` VALUES ('80', 'PageViewUpdateTask', '2018-07-08 17:00:00');
INSERT INTO `task_run_log` VALUES ('81', 'PageViewUpdateTask', '2018-07-08 20:00:00');
INSERT INTO `task_run_log` VALUES ('82', 'PageViewUpdateTask', '2018-07-08 21:00:00');
INSERT INTO `task_run_log` VALUES ('83', 'PageViewUpdateTask', '2018-07-10 17:00:00');
INSERT INTO `task_run_log` VALUES ('84', 'PageViewUpdateTask', '2018-07-10 19:00:00');
INSERT INTO `task_run_log` VALUES ('85', 'PageViewUpdateTask', '2018-07-10 20:00:00');
INSERT INTO `task_run_log` VALUES ('86', 'PageViewUpdateTask', '2018-07-10 21:00:00');
INSERT INTO `task_run_log` VALUES ('87', 'PageViewUpdateTask', '2018-07-10 22:00:00');
INSERT INTO `task_run_log` VALUES ('88', 'PageViewUpdateTask', '2018-07-10 23:00:00');
INSERT INTO `task_run_log` VALUES ('89', 'PageViewUpdateTask', '2018-07-11 12:00:00');
INSERT INTO `task_run_log` VALUES ('90', 'PageViewUpdateTask', '2018-07-11 13:00:00');
INSERT INTO `task_run_log` VALUES ('91', 'PageViewUpdateTask', '2018-07-11 21:00:00');
INSERT INTO `task_run_log` VALUES ('92', 'PageViewUpdateTask', '2018-07-11 22:00:00');
INSERT INTO `task_run_log` VALUES ('93', 'PageViewUpdateTask', '2018-07-11 23:00:00');
INSERT INTO `task_run_log` VALUES ('94', 'PageViewUpdateTask', '2018-07-12 10:00:00');
INSERT INTO `task_run_log` VALUES ('95', 'PageViewUpdateTask', '2018-07-12 11:00:00');
INSERT INTO `task_run_log` VALUES ('96', 'PageViewUpdateTask', '2018-07-12 12:00:00');
INSERT INTO `task_run_log` VALUES ('97', 'PageViewUpdateTask', '2018-07-12 13:00:00');
INSERT INTO `task_run_log` VALUES ('98', 'PageViewUpdateTask', '2018-07-12 15:00:00');
INSERT INTO `task_run_log` VALUES ('99', 'PageViewUpdateTask', '2018-07-12 16:00:00');
INSERT INTO `task_run_log` VALUES ('100', 'PageViewUpdateTask', '2018-07-12 17:00:00');
INSERT INTO `task_run_log` VALUES ('101', 'PageViewUpdateTask', '2018-07-12 18:00:00');
INSERT INTO `task_run_log` VALUES ('102', 'PageViewUpdateTask', '2018-07-12 19:00:00');
INSERT INTO `task_run_log` VALUES ('103', 'PageViewUpdateTask', '2018-07-12 20:00:00');
INSERT INTO `task_run_log` VALUES ('104', 'PageViewUpdateTask', '2018-07-12 21:00:00');
INSERT INTO `task_run_log` VALUES ('105', 'PageViewUpdateTask', '2018-07-12 22:00:00');
INSERT INTO `task_run_log` VALUES ('106', 'PageViewUpdateTask', '2018-07-12 23:00:00');
INSERT INTO `task_run_log` VALUES ('107', 'PageViewUpdateTask', '2018-07-13 00:00:00');
INSERT INTO `task_run_log` VALUES ('108', 'PageViewUpdateTask', '2018-07-14 06:00:00');
INSERT INTO `task_run_log` VALUES ('109', 'PageViewUpdateTask', '2018-07-14 07:00:00');
INSERT INTO `task_run_log` VALUES ('110', 'PageViewUpdateTask', '2018-07-14 08:00:00');
INSERT INTO `task_run_log` VALUES ('111', 'PageViewUpdateTask', '2018-07-14 09:00:00');
INSERT INTO `task_run_log` VALUES ('112', 'PageViewUpdateTask', '2018-07-15 19:00:00');
INSERT INTO `task_run_log` VALUES ('113', 'PageViewUpdateTask', '2018-07-15 20:00:00');
INSERT INTO `task_run_log` VALUES ('114', 'PageViewUpdateTask', '2018-07-15 21:00:00');
INSERT INTO `task_run_log` VALUES ('115', 'PageViewUpdateTask', '2018-07-15 22:00:00');
INSERT INTO `task_run_log` VALUES ('116', 'PageViewUpdateTask', '2018-07-15 23:00:00');
INSERT INTO `task_run_log` VALUES ('117', 'PageViewUpdateTask', '2018-07-16 00:00:00');
INSERT INTO `task_run_log` VALUES ('118', 'PageViewUpdateTask', '2018-07-16 21:00:00');
INSERT INTO `task_run_log` VALUES ('119', 'PageViewUpdateTask', '2018-07-16 22:00:00');
INSERT INTO `task_run_log` VALUES ('120', 'PageViewUpdateTask', '2018-07-16 23:00:00');
INSERT INTO `task_run_log` VALUES ('121', 'PageViewUpdateTask', '2018-07-17 00:00:00');
INSERT INTO `task_run_log` VALUES ('122', 'PageViewUpdateTask', '2018-07-17 10:00:00');
INSERT INTO `task_run_log` VALUES ('123', 'PageViewUpdateTask', '2018-07-17 11:00:00');
INSERT INTO `task_run_log` VALUES ('124', 'PageViewUpdateTask', '2018-07-17 12:00:00');
INSERT INTO `task_run_log` VALUES ('125', 'PageViewUpdateTask', '2018-07-17 15:00:00');
INSERT INTO `task_run_log` VALUES ('126', 'PageViewUpdateTask', '2018-07-17 16:00:00');
INSERT INTO `task_run_log` VALUES ('127', 'PageViewUpdateTask', '2018-07-17 17:00:00');
INSERT INTO `task_run_log` VALUES ('128', 'PageViewUpdateTask', '2018-07-17 18:00:00');
INSERT INTO `task_run_log` VALUES ('129', 'PageViewUpdateTask', '2018-07-17 19:00:00');
INSERT INTO `task_run_log` VALUES ('130', 'PageViewUpdateTask', '2018-07-17 20:00:00');
INSERT INTO `task_run_log` VALUES ('131', 'PageViewUpdateTask', '2018-07-17 21:00:00');
INSERT INTO `task_run_log` VALUES ('132', 'PageViewUpdateTask', '2018-07-17 22:00:00');
INSERT INTO `task_run_log` VALUES ('133', 'PageViewUpdateTask', '2018-07-17 23:00:00');
INSERT INTO `task_run_log` VALUES ('134', 'PageViewUpdateTask', '2018-07-18 07:00:00');
INSERT INTO `task_run_log` VALUES ('135', 'PageViewUpdateTask', '2018-07-18 08:00:00');
INSERT INTO `task_run_log` VALUES ('136', 'PageViewUpdateTask', '2018-07-18 09:00:00');
INSERT INTO `task_run_log` VALUES ('137', 'PageViewUpdateTask', '2018-07-18 10:00:00');
INSERT INTO `task_run_log` VALUES ('138', 'PageViewUpdateTask', '2018-07-18 11:00:00');
INSERT INTO `task_run_log` VALUES ('139', 'PageViewUpdateTask', '2018-07-18 12:00:00');
INSERT INTO `task_run_log` VALUES ('140', 'PageViewUpdateTask', '2018-07-18 16:00:00');
INSERT INTO `task_run_log` VALUES ('141', 'PageViewUpdateTask', '2018-07-18 17:00:00');
INSERT INTO `task_run_log` VALUES ('142', 'PageViewUpdateTask', '2018-07-19 10:00:00');
INSERT INTO `task_run_log` VALUES ('143', 'PageViewUpdateTask', '2018-07-19 11:00:00');
INSERT INTO `task_run_log` VALUES ('144', 'PageViewUpdateTask', '2018-07-19 12:00:00');
INSERT INTO `task_run_log` VALUES ('145', 'PageViewUpdateTask', '2018-07-19 13:00:00');
INSERT INTO `task_run_log` VALUES ('146', 'PageViewUpdateTask', '2018-07-19 16:00:00');
INSERT INTO `task_run_log` VALUES ('147', 'PageViewUpdateTask', '2018-07-19 17:00:00');
INSERT INTO `task_run_log` VALUES ('148', 'PageViewUpdateTask', '2018-07-19 18:00:00');
INSERT INTO `task_run_log` VALUES ('149', 'PageViewUpdateTask', '2018-07-19 19:00:00');
INSERT INTO `task_run_log` VALUES ('150', 'PageViewUpdateTask', '2018-07-19 20:00:00');
INSERT INTO `task_run_log` VALUES ('151', 'PageViewUpdateTask', '2018-07-19 21:00:00');
INSERT INTO `task_run_log` VALUES ('152', 'PageViewUpdateTask', '2018-07-19 22:00:00');
INSERT INTO `task_run_log` VALUES ('153', 'PageViewUpdateTask', '2018-07-19 23:00:00');
INSERT INTO `task_run_log` VALUES ('154', 'PageViewUpdateTask', '2018-07-20 00:00:00');
INSERT INTO `task_run_log` VALUES ('155', 'PageViewUpdateTask', '2018-07-20 02:00:00');
INSERT INTO `task_run_log` VALUES ('156', 'PageViewUpdateTask', '2018-07-20 10:00:00');
INSERT INTO `task_run_log` VALUES ('157', 'PageViewUpdateTask', '2018-07-20 11:00:00');
INSERT INTO `task_run_log` VALUES ('158', 'PageViewUpdateTask', '2018-07-20 12:00:00');
INSERT INTO `task_run_log` VALUES ('159', 'PageViewUpdateTask', '2018-07-20 13:00:00');
INSERT INTO `task_run_log` VALUES ('160', 'PageViewUpdateTask', '2018-07-20 15:00:00');
INSERT INTO `task_run_log` VALUES ('161', 'PageViewUpdateTask', '2018-07-21 02:00:00');
INSERT INTO `task_run_log` VALUES ('162', 'PageViewUpdateTask', '2018-07-21 03:00:00');
INSERT INTO `task_run_log` VALUES ('163', 'PageViewUpdateTask', '2018-07-21 04:00:00');
INSERT INTO `task_run_log` VALUES ('164', 'PageViewUpdateTask', '2018-07-21 05:00:00');
INSERT INTO `task_run_log` VALUES ('165', 'PageViewUpdateTask', '2018-07-21 06:00:00');
INSERT INTO `task_run_log` VALUES ('166', 'PageViewUpdateTask', '2018-07-21 07:00:00');
INSERT INTO `task_run_log` VALUES ('167', 'PageViewUpdateTask', '2018-07-21 08:00:00');
INSERT INTO `task_run_log` VALUES ('168', 'PageViewUpdateTask', '2018-07-21 09:00:00');
INSERT INTO `task_run_log` VALUES ('169', 'PageViewUpdateTask', '2018-07-22 01:00:00');
INSERT INTO `task_run_log` VALUES ('170', 'PageViewUpdateTask', '2018-07-22 02:00:00');
INSERT INTO `task_run_log` VALUES ('171', 'PageViewUpdateTask', '2018-07-22 11:00:00');
INSERT INTO `task_run_log` VALUES ('172', 'PageViewUpdateTask', '2018-07-22 12:00:00');
INSERT INTO `task_run_log` VALUES ('173', 'PageViewUpdateTask', '2018-07-22 13:00:00');
INSERT INTO `task_run_log` VALUES ('174', 'PageViewUpdateTask', '2018-07-22 14:00:00');
INSERT INTO `task_run_log` VALUES ('175', 'PageViewUpdateTask', '2018-07-22 15:00:00');
INSERT INTO `task_run_log` VALUES ('176', 'PageViewUpdateTask', '2018-07-22 16:00:00');
INSERT INTO `task_run_log` VALUES ('177', 'PageViewUpdateTask', '2018-07-22 17:00:00');
INSERT INTO `task_run_log` VALUES ('178', 'PageViewUpdateTask', '2018-07-22 18:00:00');
INSERT INTO `task_run_log` VALUES ('179', 'PageViewUpdateTask', '2018-07-22 19:00:00');
INSERT INTO `task_run_log` VALUES ('180', 'PageViewUpdateTask', '2018-07-22 20:00:00');
INSERT INTO `task_run_log` VALUES ('181', 'PageViewUpdateTask', '2018-07-22 21:00:00');
INSERT INTO `task_run_log` VALUES ('182', 'PageViewUpdateTask', '2018-07-22 22:00:00');
INSERT INTO `task_run_log` VALUES ('183', 'PageViewUpdateTask', '2018-07-23 00:00:00');
INSERT INTO `task_run_log` VALUES ('184', 'PageViewUpdateTask', '2018-07-23 01:00:00');
INSERT INTO `task_run_log` VALUES ('185', 'PageViewUpdateTask', '2018-07-23 11:00:00');
INSERT INTO `task_run_log` VALUES ('186', 'PageViewUpdateTask', '2018-07-23 12:00:00');
INSERT INTO `task_run_log` VALUES ('187', 'PageViewUpdateTask', '2018-07-23 13:00:00');
INSERT INTO `task_run_log` VALUES ('188', 'PageViewUpdateTask', '2018-07-23 16:00:00');
INSERT INTO `task_run_log` VALUES ('189', 'PageViewUpdateTask', '2018-07-23 17:00:00');
INSERT INTO `task_run_log` VALUES ('190', 'PageViewUpdateTask', '2018-07-23 18:00:00');
INSERT INTO `task_run_log` VALUES ('191', 'PageViewUpdateTask', '2018-07-23 19:00:00');
INSERT INTO `task_run_log` VALUES ('192', 'PageViewUpdateTask', '2018-07-23 20:00:00');
INSERT INTO `task_run_log` VALUES ('193', 'PageViewUpdateTask', '2018-07-23 22:00:00');
INSERT INTO `task_run_log` VALUES ('194', 'PageViewUpdateTask', '2018-07-23 23:00:00');
INSERT INTO `task_run_log` VALUES ('195', 'PageViewUpdateTask', '2018-07-24 00:00:00');
INSERT INTO `task_run_log` VALUES ('196', 'PageViewUpdateTask', '2018-07-24 10:00:00');
INSERT INTO `task_run_log` VALUES ('197', 'PageViewUpdateTask', '2018-07-24 11:00:00');
INSERT INTO `task_run_log` VALUES ('198', 'PageViewUpdateTask', '2018-07-24 12:00:00');
INSERT INTO `task_run_log` VALUES ('199', 'PageViewUpdateTask', '2018-07-24 13:00:00');
INSERT INTO `task_run_log` VALUES ('200', 'PageViewUpdateTask', '2018-07-24 14:00:00');
INSERT INTO `task_run_log` VALUES ('201', 'PageViewUpdateTask', '2018-07-24 15:00:00');
INSERT INTO `task_run_log` VALUES ('202', 'PageViewUpdateTask', '2018-07-24 16:00:00');
INSERT INTO `task_run_log` VALUES ('203', 'PageViewUpdateTask', '2018-07-24 19:00:00');
INSERT INTO `task_run_log` VALUES ('204', 'PageViewUpdateTask', '2018-07-24 20:00:00');
INSERT INTO `task_run_log` VALUES ('205', 'PageViewUpdateTask', '2018-07-24 21:00:00');
INSERT INTO `task_run_log` VALUES ('206', 'PageViewUpdateTask', '2018-07-24 22:00:00');
INSERT INTO `task_run_log` VALUES ('207', 'PageViewUpdateTask', '2018-07-24 23:00:00');
INSERT INTO `task_run_log` VALUES ('208', 'PageViewUpdateTask', '2018-07-25 00:00:00');
INSERT INTO `task_run_log` VALUES ('209', 'PageViewUpdateTask', '2018-07-25 01:00:00');
INSERT INTO `task_run_log` VALUES ('210', 'PageViewUpdateTask', '2018-07-25 10:00:00');
INSERT INTO `task_run_log` VALUES ('211', 'PageViewUpdateTask', '2018-07-25 11:00:00');
INSERT INTO `task_run_log` VALUES ('212', 'PageViewUpdateTask', '2018-07-25 12:00:00');
INSERT INTO `task_run_log` VALUES ('213', 'PageViewUpdateTask', '2018-07-25 13:00:00');
INSERT INTO `task_run_log` VALUES ('214', 'PageViewUpdateTask', '2018-07-25 14:00:00');
INSERT INTO `task_run_log` VALUES ('215', 'PageViewUpdateTask', '2018-07-25 15:00:00');
INSERT INTO `task_run_log` VALUES ('216', 'PageViewUpdateTask', '2018-07-25 16:00:00');
INSERT INTO `task_run_log` VALUES ('217', 'PageViewUpdateTask', '2018-07-25 17:00:00');
INSERT INTO `task_run_log` VALUES ('218', 'PageViewUpdateTask', '2018-07-25 18:00:00');
INSERT INTO `task_run_log` VALUES ('219', 'PageViewUpdateTask', '2018-07-25 19:00:00');
INSERT INTO `task_run_log` VALUES ('220', 'PageViewUpdateTask', '2018-07-25 20:00:00');
INSERT INTO `task_run_log` VALUES ('221', 'PageViewUpdateTask', '2018-07-25 21:00:00');
INSERT INTO `task_run_log` VALUES ('222', 'PageViewUpdateTask', '2018-07-25 22:00:00');
INSERT INTO `task_run_log` VALUES ('223', 'PageViewUpdateTask', '2018-07-25 23:00:00');
INSERT INTO `task_run_log` VALUES ('224', 'PageViewUpdateTask', '2018-07-26 02:00:00');
INSERT INTO `task_run_log` VALUES ('225', 'PageViewUpdateTask', '2018-07-26 09:00:00');
INSERT INTO `task_run_log` VALUES ('226', 'PageViewUpdateTask', '2018-07-26 10:00:00');
INSERT INTO `task_run_log` VALUES ('227', 'PageViewUpdateTask', '2018-07-26 11:00:00');
INSERT INTO `task_run_log` VALUES ('228', 'PageViewUpdateTask', '2018-07-26 12:00:00');
INSERT INTO `task_run_log` VALUES ('229', 'PageViewUpdateTask', '2018-07-26 13:00:00');
INSERT INTO `task_run_log` VALUES ('230', 'PageViewUpdateTask', '2018-07-26 14:00:00');
INSERT INTO `task_run_log` VALUES ('231', 'PageViewUpdateTask', '2018-07-26 16:00:00');
INSERT INTO `task_run_log` VALUES ('232', 'PageViewUpdateTask', '2018-07-26 17:00:00');
INSERT INTO `task_run_log` VALUES ('233', 'PageViewUpdateTask', '2018-07-26 20:00:00');
INSERT INTO `task_run_log` VALUES ('234', 'PageViewUpdateTask', '2018-07-26 21:00:00');
INSERT INTO `task_run_log` VALUES ('235', 'PageViewUpdateTask', '2018-07-26 22:00:00');
INSERT INTO `task_run_log` VALUES ('236', 'PageViewUpdateTask', '2018-07-26 23:00:00');
INSERT INTO `task_run_log` VALUES ('237', 'PageViewUpdateTask', '2018-07-27 00:00:00');
INSERT INTO `task_run_log` VALUES ('238', 'PageViewUpdateTask', '2018-07-27 10:00:00');
INSERT INTO `task_run_log` VALUES ('239', 'PageViewUpdateTask', '2018-07-27 11:00:00');
INSERT INTO `task_run_log` VALUES ('240', 'PageViewUpdateTask', '2018-07-27 13:00:00');
INSERT INTO `task_run_log` VALUES ('241', 'PageViewUpdateTask', '2018-07-27 14:00:00');
INSERT INTO `task_run_log` VALUES ('242', 'PageViewUpdateTask', '2018-07-27 15:00:00');
INSERT INTO `task_run_log` VALUES ('243', 'PageViewUpdateTask', '2018-07-27 16:00:00');
INSERT INTO `task_run_log` VALUES ('244', 'PageViewUpdateTask', '2018-07-27 17:00:00');
INSERT INTO `task_run_log` VALUES ('245', 'PageViewUpdateTask', '2018-07-27 18:00:00');
INSERT INTO `task_run_log` VALUES ('246', 'PageViewUpdateTask', '2018-07-27 19:00:00');
INSERT INTO `task_run_log` VALUES ('247', 'PageViewUpdateTask', '2018-07-27 20:00:00');
INSERT INTO `task_run_log` VALUES ('248', 'PageViewUpdateTask', '2018-07-27 21:00:00');
INSERT INTO `task_run_log` VALUES ('249', 'PageViewUpdateTask', '2018-07-27 22:00:00');
INSERT INTO `task_run_log` VALUES ('250', 'PageViewUpdateTask', '2018-07-27 23:00:00');
INSERT INTO `task_run_log` VALUES ('251', 'PageViewUpdateTask', '2018-07-28 09:00:00');
INSERT INTO `task_run_log` VALUES ('252', 'PageViewUpdateTask', '2018-07-28 10:00:00');
INSERT INTO `task_run_log` VALUES ('253', 'PageViewUpdateTask', '2018-07-28 15:00:00');
INSERT INTO `task_run_log` VALUES ('254', 'PageViewUpdateTask', '2018-07-29 00:00:00');
INSERT INTO `task_run_log` VALUES ('255', 'PageViewUpdateTask', '2018-07-29 22:00:00');
INSERT INTO `task_run_log` VALUES ('256', 'PageViewUpdateTask', '2018-07-29 23:00:00');
INSERT INTO `task_run_log` VALUES ('257', 'PageViewUpdateTask', '2018-07-30 00:00:00');
INSERT INTO `task_run_log` VALUES ('258', 'PageViewUpdateTask', '2018-07-30 09:00:00');
INSERT INTO `task_run_log` VALUES ('259', 'PageViewUpdateTask', '2018-07-30 10:00:00');
INSERT INTO `task_run_log` VALUES ('260', 'PageViewUpdateTask', '2018-07-30 11:00:00');
INSERT INTO `task_run_log` VALUES ('261', 'PageViewUpdateTask', '2018-07-30 12:00:00');
INSERT INTO `task_run_log` VALUES ('262', 'PageViewUpdateTask', '2018-07-30 13:00:00');
INSERT INTO `task_run_log` VALUES ('263', 'PageViewUpdateTask', '2018-07-30 14:00:00');
INSERT INTO `task_run_log` VALUES ('264', 'PageViewUpdateTask', '2018-07-31 00:00:00');
INSERT INTO `task_run_log` VALUES ('265', 'PageViewUpdateTask', '2018-07-31 01:00:00');
INSERT INTO `task_run_log` VALUES ('266', 'PageViewUpdateTask', '2018-07-31 02:00:00');
INSERT INTO `task_run_log` VALUES ('267', 'PageViewUpdateTask', '2018-07-31 03:00:00');
INSERT INTO `task_run_log` VALUES ('268', 'PageViewUpdateTask', '2018-07-31 04:00:00');
INSERT INTO `task_run_log` VALUES ('269', 'PageViewUpdateTask', '2018-07-31 05:00:00');
INSERT INTO `task_run_log` VALUES ('270', 'PageViewUpdateTask', '2018-07-31 09:00:00');
INSERT INTO `task_run_log` VALUES ('271', 'PageViewUpdateTask', '2018-07-31 11:00:00');
INSERT INTO `task_run_log` VALUES ('272', 'PageViewUpdateTask', '2018-07-31 12:00:00');
INSERT INTO `task_run_log` VALUES ('273', 'PageViewUpdateTask', '2018-07-31 13:00:00');
INSERT INTO `task_run_log` VALUES ('274', 'PageViewUpdateTask', '2018-08-01 13:00:00');
INSERT INTO `task_run_log` VALUES ('275', 'PageViewUpdateTask', '2018-08-01 14:00:00');
INSERT INTO `task_run_log` VALUES ('276', 'PageViewUpdateTask', '2018-08-01 18:00:00');
INSERT INTO `task_run_log` VALUES ('277', 'PageViewUpdateTask', '2018-08-01 19:00:00');
INSERT INTO `task_run_log` VALUES ('278', 'PageViewUpdateTask', '2018-08-01 20:00:00');
INSERT INTO `task_run_log` VALUES ('279', 'PageViewUpdateTask', '2018-08-01 21:00:00');
INSERT INTO `task_run_log` VALUES ('280', 'PageViewUpdateTask', '2018-08-01 22:00:00');
INSERT INTO `task_run_log` VALUES ('281', 'PageViewUpdateTask', '2018-08-01 23:00:00');
INSERT INTO `task_run_log` VALUES ('282', 'PageViewUpdateTask', '2018-08-02 00:00:00');
INSERT INTO `task_run_log` VALUES ('283', 'PageViewUpdateTask', '2018-08-02 08:00:00');
INSERT INTO `task_run_log` VALUES ('284', 'PageViewUpdateTask', '2018-08-02 09:00:00');
INSERT INTO `task_run_log` VALUES ('285', 'PageViewUpdateTask', '2018-08-02 10:00:00');
INSERT INTO `task_run_log` VALUES ('286', 'PageViewUpdateTask', '2018-08-02 11:00:00');
INSERT INTO `task_run_log` VALUES ('287', 'PageViewUpdateTask', '2018-08-02 12:00:00');
INSERT INTO `task_run_log` VALUES ('288', 'PageViewUpdateTask', '2018-08-02 13:00:00');
INSERT INTO `task_run_log` VALUES ('289', 'PageViewUpdateTask', '2018-08-02 14:00:00');
INSERT INTO `task_run_log` VALUES ('290', 'PageViewUpdateTask', '2018-08-02 15:00:00');
INSERT INTO `task_run_log` VALUES ('291', 'PageViewUpdateTask', '2018-08-02 17:00:00');
INSERT INTO `task_run_log` VALUES ('292', 'PageViewUpdateTask', '2018-08-02 18:00:00');
INSERT INTO `task_run_log` VALUES ('293', 'PageViewUpdateTask', '2018-08-02 19:00:00');
INSERT INTO `task_run_log` VALUES ('294', 'PageViewUpdateTask', '2018-08-02 20:00:00');
INSERT INTO `task_run_log` VALUES ('295', 'PageViewUpdateTask', '2018-08-02 21:00:00');
INSERT INTO `task_run_log` VALUES ('296', 'PageViewUpdateTask', '2018-08-02 22:00:00');
INSERT INTO `task_run_log` VALUES ('297', 'PageViewUpdateTask', '2018-08-02 23:00:00');
INSERT INTO `task_run_log` VALUES ('298', 'PageViewUpdateTask', '2018-08-03 00:00:00');
INSERT INTO `task_run_log` VALUES ('299', 'PageViewUpdateTask', '2018-08-03 01:00:00');
INSERT INTO `task_run_log` VALUES ('300', 'PageViewUpdateTask', '2018-08-03 10:00:00');
INSERT INTO `task_run_log` VALUES ('301', 'PageViewUpdateTask', '2018-08-03 11:00:00');
INSERT INTO `task_run_log` VALUES ('302', 'PageViewUpdateTask', '2018-08-03 12:00:00');
INSERT INTO `task_run_log` VALUES ('303', 'PageViewUpdateTask', '2018-08-03 13:00:00');
INSERT INTO `task_run_log` VALUES ('304', 'PageViewUpdateTask', '2018-08-03 14:00:00');
INSERT INTO `task_run_log` VALUES ('305', 'PageViewUpdateTask', '2018-08-03 15:00:00');
INSERT INTO `task_run_log` VALUES ('306', 'PageViewUpdateTask', '2018-08-03 16:00:00');
INSERT INTO `task_run_log` VALUES ('307', 'PageViewUpdateTask', '2018-08-03 17:00:00');
INSERT INTO `task_run_log` VALUES ('308', 'PageViewUpdateTask', '2018-08-03 18:00:00');
INSERT INTO `task_run_log` VALUES ('309', 'PageViewUpdateTask', '2018-08-03 19:00:00');
INSERT INTO `task_run_log` VALUES ('310', 'PageViewUpdateTask', '2018-08-03 20:00:00');
INSERT INTO `task_run_log` VALUES ('311', 'PageViewUpdateTask', '2018-08-03 21:00:00');
INSERT INTO `task_run_log` VALUES ('312', 'PageViewUpdateTask', '2018-08-03 22:00:00');
INSERT INTO `task_run_log` VALUES ('313', 'PageViewUpdateTask', '2018-08-03 23:00:00');
INSERT INTO `task_run_log` VALUES ('314', 'PageViewUpdateTask', '2018-08-04 00:00:00');
INSERT INTO `task_run_log` VALUES ('315', 'PageViewUpdateTask', '2018-08-04 01:00:00');
INSERT INTO `task_run_log` VALUES ('316', 'PageViewUpdateTask', '2018-08-04 02:00:00');
INSERT INTO `task_run_log` VALUES ('317', 'PageViewUpdateTask', '2018-08-04 07:00:00');
INSERT INTO `task_run_log` VALUES ('318', 'PageViewUpdateTask', '2018-08-04 08:00:00');
INSERT INTO `task_run_log` VALUES ('319', 'PageViewUpdateTask', '2018-08-04 09:00:00');
INSERT INTO `task_run_log` VALUES ('320', 'PageViewUpdateTask', '2018-08-04 10:00:00');
INSERT INTO `task_run_log` VALUES ('321', 'PageViewUpdateTask', '2018-08-04 15:00:00');
INSERT INTO `task_run_log` VALUES ('322', 'PageViewUpdateTask', '2018-08-04 19:00:00');
INSERT INTO `task_run_log` VALUES ('323', 'PageViewUpdateTask', '2018-08-05 02:00:00');
INSERT INTO `task_run_log` VALUES ('324', 'PageViewUpdateTask', '2018-08-05 03:00:00');
INSERT INTO `task_run_log` VALUES ('325', 'PageViewUpdateTask', '2018-08-05 04:00:00');
INSERT INTO `task_run_log` VALUES ('326', 'PageViewUpdateTask', '2018-08-05 05:00:00');
INSERT INTO `task_run_log` VALUES ('327', 'PageViewUpdateTask', '2018-08-05 06:00:00');
INSERT INTO `task_run_log` VALUES ('328', 'PageViewUpdateTask', '2018-08-05 22:00:00');
INSERT INTO `task_run_log` VALUES ('329', 'PageViewUpdateTask', '2018-08-05 23:00:00');
INSERT INTO `task_run_log` VALUES ('330', 'PageViewUpdateTask', '2018-08-06 09:00:00');
INSERT INTO `task_run_log` VALUES ('331', 'PageViewUpdateTask', '2018-08-06 11:00:00');
INSERT INTO `task_run_log` VALUES ('332', 'PageViewUpdateTask', '2018-08-06 13:00:00');
INSERT INTO `task_run_log` VALUES ('333', 'PageViewUpdateTask', '2018-08-06 14:00:00');
INSERT INTO `task_run_log` VALUES ('334', 'PageViewUpdateTask', '2018-08-06 15:00:00');
INSERT INTO `task_run_log` VALUES ('335', 'PageViewUpdateTask', '2018-08-06 16:00:00');
INSERT INTO `task_run_log` VALUES ('336', 'PageViewUpdateTask', '2018-08-06 17:00:00');
INSERT INTO `task_run_log` VALUES ('337', 'PageViewUpdateTask', '2018-08-06 18:00:00');
INSERT INTO `task_run_log` VALUES ('338', 'PageViewUpdateTask', '2018-08-06 19:00:00');
INSERT INTO `task_run_log` VALUES ('339', 'PageViewUpdateTask', '2018-08-06 20:00:00');
INSERT INTO `task_run_log` VALUES ('340', 'PageViewUpdateTask', '2018-08-06 21:00:00');
INSERT INTO `task_run_log` VALUES ('341', 'PageViewUpdateTask', '2018-08-06 22:00:00');
INSERT INTO `task_run_log` VALUES ('342', 'PageViewUpdateTask', '2018-08-06 23:00:00');
INSERT INTO `task_run_log` VALUES ('343', 'PageViewUpdateTask', '2018-08-07 00:00:00');
INSERT INTO `task_run_log` VALUES ('344', 'PageViewUpdateTask', '2018-08-07 07:00:00');
INSERT INTO `task_run_log` VALUES ('345', 'PageViewUpdateTask', '2018-08-07 08:00:00');
INSERT INTO `task_run_log` VALUES ('346', 'PageViewUpdateTask', '2018-08-07 10:00:00');
INSERT INTO `task_run_log` VALUES ('347', 'PageViewUpdateTask', '2018-08-07 11:00:00');
INSERT INTO `task_run_log` VALUES ('348', 'PageViewUpdateTask', '2018-08-07 14:00:00');
INSERT INTO `task_run_log` VALUES ('349', 'PageViewUpdateTask', '2018-08-07 15:00:00');
INSERT INTO `task_run_log` VALUES ('350', 'PageViewUpdateTask', '2018-08-07 16:00:00');
INSERT INTO `task_run_log` VALUES ('351', 'PageViewUpdateTask', '2018-08-07 17:00:00');
INSERT INTO `task_run_log` VALUES ('352', 'PageViewUpdateTask', '2018-08-07 18:00:00');
INSERT INTO `task_run_log` VALUES ('353', 'PageViewUpdateTask', '2018-08-07 19:00:00');
INSERT INTO `task_run_log` VALUES ('354', 'PageViewUpdateTask', '2018-08-07 20:00:00');
INSERT INTO `task_run_log` VALUES ('355', 'PageViewUpdateTask', '2018-08-07 21:00:00');
INSERT INTO `task_run_log` VALUES ('356', 'PageViewUpdateTask', '2018-08-07 22:00:00');
INSERT INTO `task_run_log` VALUES ('357', 'PageViewUpdateTask', '2018-08-07 23:00:00');
INSERT INTO `task_run_log` VALUES ('358', 'PageViewUpdateTask', '2018-08-08 00:00:00');
INSERT INTO `task_run_log` VALUES ('359', 'PageViewUpdateTask', '2018-08-08 09:00:00');
INSERT INTO `task_run_log` VALUES ('360', 'PageViewUpdateTask', '2018-08-08 10:00:00');
INSERT INTO `task_run_log` VALUES ('361', 'PageViewUpdateTask', '2018-08-08 11:00:00');
INSERT INTO `task_run_log` VALUES ('362', 'PageViewUpdateTask', '2018-08-08 12:00:00');
INSERT INTO `task_run_log` VALUES ('363', 'PageViewUpdateTask', '2018-08-08 13:00:00');
INSERT INTO `task_run_log` VALUES ('364', 'PageViewUpdateTask', '2018-08-08 14:00:00');
INSERT INTO `task_run_log` VALUES ('365', 'PageViewUpdateTask', '2018-08-08 15:00:00');
INSERT INTO `task_run_log` VALUES ('366', 'PageViewUpdateTask', '2018-08-08 16:00:00');
INSERT INTO `task_run_log` VALUES ('367', 'PageViewUpdateTask', '2018-08-08 17:00:00');
INSERT INTO `task_run_log` VALUES ('368', 'PageViewUpdateTask', '2018-08-08 18:00:00');
INSERT INTO `task_run_log` VALUES ('369', 'PageViewUpdateTask', '2018-08-08 21:00:00');
INSERT INTO `task_run_log` VALUES ('370', 'PageViewUpdateTask', '2018-08-08 22:00:00');
INSERT INTO `task_run_log` VALUES ('371', 'PageViewUpdateTask', '2018-08-08 23:00:00');
INSERT INTO `task_run_log` VALUES ('372', 'PageViewUpdateTask', '2018-08-09 00:00:00');
INSERT INTO `task_run_log` VALUES ('373', 'PageViewUpdateTask', '2018-08-09 06:37:49');
INSERT INTO `task_run_log` VALUES ('374', 'PageViewUpdateTask', '2018-08-09 07:00:00');
INSERT INTO `task_run_log` VALUES ('375', 'PageViewUpdateTask', '2018-08-09 08:00:00');
INSERT INTO `task_run_log` VALUES ('376', 'PageViewUpdateTask', '2018-08-09 09:00:00');
INSERT INTO `task_run_log` VALUES ('377', 'PageViewUpdateTask', '2018-08-09 10:00:00');
INSERT INTO `task_run_log` VALUES ('378', 'PageViewUpdateTask', '2018-08-09 10:00:13');
INSERT INTO `task_run_log` VALUES ('379', 'PageViewUpdateTask', '2018-08-09 10:10:24');
INSERT INTO `task_run_log` VALUES ('380', 'PageViewUpdateTask', '2018-08-09 10:33:48');
INSERT INTO `task_run_log` VALUES ('381', 'PageViewUpdateTask', '2018-08-09 11:00:00');
INSERT INTO `task_run_log` VALUES ('382', 'PageViewUpdateTask', '2018-08-09 11:00:00');
INSERT INTO `task_run_log` VALUES ('383', 'PageViewUpdateTask', '2018-08-09 12:00:00');
INSERT INTO `task_run_log` VALUES ('384', 'PageViewUpdateTask', '2018-08-09 12:00:00');
INSERT INTO `task_run_log` VALUES ('385', 'PageViewUpdateTask', '2018-08-09 13:00:00');
INSERT INTO `task_run_log` VALUES ('386', 'PageViewUpdateTask', '2018-08-09 13:00:00');
INSERT INTO `task_run_log` VALUES ('387', 'PageViewUpdateTask', '2018-08-09 14:00:00');
INSERT INTO `task_run_log` VALUES ('388', 'PageViewUpdateTask', '2018-08-09 14:00:00');
INSERT INTO `task_run_log` VALUES ('389', 'PageViewUpdateTask', '2018-08-09 15:00:00');
INSERT INTO `task_run_log` VALUES ('390', 'PageViewUpdateTask', '2018-08-09 15:00:00');
INSERT INTO `task_run_log` VALUES ('391', 'PageViewUpdateTask', '2018-08-09 16:00:00');
INSERT INTO `task_run_log` VALUES ('392', 'PageViewUpdateTask', '2018-08-09 16:00:00');
INSERT INTO `task_run_log` VALUES ('393', 'PageViewUpdateTask', '2018-08-09 17:00:00');
INSERT INTO `task_run_log` VALUES ('394', 'PageViewUpdateTask', '2018-08-09 17:00:00');
INSERT INTO `task_run_log` VALUES ('395', 'PageViewUpdateTask', '2018-08-09 18:00:00');
INSERT INTO `task_run_log` VALUES ('396', 'PageViewUpdateTask', '2018-08-09 18:00:00');
INSERT INTO `task_run_log` VALUES ('397', 'PageViewUpdateTask', '2018-08-09 19:00:00');
INSERT INTO `task_run_log` VALUES ('398', 'PageViewUpdateTask', '2018-08-09 19:00:00');
INSERT INTO `task_run_log` VALUES ('399', 'PageViewUpdateTask', '2018-08-09 20:00:00');
INSERT INTO `task_run_log` VALUES ('400', 'PageViewUpdateTask', '2018-08-09 20:00:00');
INSERT INTO `task_run_log` VALUES ('401', 'PageViewUpdateTask', '2018-08-09 21:00:00');
INSERT INTO `task_run_log` VALUES ('402', 'PageViewUpdateTask', '2018-08-09 21:00:00');
INSERT INTO `task_run_log` VALUES ('403', 'PageViewUpdateTask', '2018-08-09 22:00:00');
INSERT INTO `task_run_log` VALUES ('404', 'PageViewUpdateTask', '2018-08-09 22:00:00');
INSERT INTO `task_run_log` VALUES ('405', 'PageViewUpdateTask', '2018-08-09 23:00:00');
INSERT INTO `task_run_log` VALUES ('406', 'PageViewUpdateTask', '2018-08-09 23:00:00');
INSERT INTO `task_run_log` VALUES ('407', 'PageViewUpdateTask', '2018-08-10 00:00:00');
INSERT INTO `task_run_log` VALUES ('408', 'PageViewUpdateTask', '2018-08-10 00:00:00');
INSERT INTO `task_run_log` VALUES ('409', 'PageViewUpdateTask', '2018-08-10 01:00:00');
INSERT INTO `task_run_log` VALUES ('410', 'PageViewUpdateTask', '2018-08-10 02:00:00');
INSERT INTO `task_run_log` VALUES ('411', 'PageViewUpdateTask', '2018-08-10 03:00:00');
INSERT INTO `task_run_log` VALUES ('412', 'PageViewUpdateTask', '2018-08-10 04:00:00');
INSERT INTO `task_run_log` VALUES ('413', 'PageViewUpdateTask', '2018-08-10 05:00:00');
INSERT INTO `task_run_log` VALUES ('414', 'PageViewUpdateTask', '2018-08-10 06:00:00');
INSERT INTO `task_run_log` VALUES ('415', 'PageViewUpdateTask', '2018-08-10 07:00:00');
INSERT INTO `task_run_log` VALUES ('416', 'PageViewUpdateTask', '2018-08-10 08:00:00');
INSERT INTO `task_run_log` VALUES ('417', 'PageViewUpdateTask', '2018-08-10 09:00:00');
INSERT INTO `task_run_log` VALUES ('418', 'PageViewUpdateTask', '2018-08-10 10:00:00');
INSERT INTO `task_run_log` VALUES ('419', 'PageViewUpdateTask', '2018-08-10 11:00:00');
INSERT INTO `task_run_log` VALUES ('420', 'PageViewUpdateTask', '2018-08-10 11:00:00');
INSERT INTO `task_run_log` VALUES ('421', 'PageViewUpdateTask', '2018-08-10 12:00:00');
INSERT INTO `task_run_log` VALUES ('422', 'PageViewUpdateTask', '2018-08-10 12:00:00');
INSERT INTO `task_run_log` VALUES ('423', 'PageViewUpdateTask', '2018-08-10 13:00:00');
INSERT INTO `task_run_log` VALUES ('424', 'PageViewUpdateTask', '2018-08-10 13:00:00');
INSERT INTO `task_run_log` VALUES ('425', 'PageViewUpdateTask', '2018-08-10 14:00:00');
INSERT INTO `task_run_log` VALUES ('426', 'PageViewUpdateTask', '2018-08-10 14:00:00');
INSERT INTO `task_run_log` VALUES ('427', 'PageViewUpdateTask', '2018-08-10 15:00:00');
INSERT INTO `task_run_log` VALUES ('428', 'PageViewUpdateTask', '2018-08-10 15:00:00');
INSERT INTO `task_run_log` VALUES ('429', 'PageViewUpdateTask', '2018-08-10 16:00:00');
INSERT INTO `task_run_log` VALUES ('430', 'PageViewUpdateTask', '2018-08-10 16:00:00');
INSERT INTO `task_run_log` VALUES ('431', 'PageViewUpdateTask', '2018-08-10 17:00:00');
INSERT INTO `task_run_log` VALUES ('432', 'PageViewUpdateTask', '2018-08-10 17:00:00');
INSERT INTO `task_run_log` VALUES ('433', 'PageViewUpdateTask', '2018-08-10 18:00:00');
INSERT INTO `task_run_log` VALUES ('434', 'PageViewUpdateTask', '2018-08-10 18:00:00');
INSERT INTO `task_run_log` VALUES ('435', 'PageViewUpdateTask', '2018-08-10 19:00:00');
INSERT INTO `task_run_log` VALUES ('436', 'PageViewUpdateTask', '2018-08-10 19:00:00');
INSERT INTO `task_run_log` VALUES ('437', 'PageViewUpdateTask', '2018-08-10 20:00:00');
INSERT INTO `task_run_log` VALUES ('438', 'PageViewUpdateTask', '2018-08-10 20:00:00');
INSERT INTO `task_run_log` VALUES ('439', 'PageViewUpdateTask', '2018-08-10 21:00:00');
INSERT INTO `task_run_log` VALUES ('440', 'PageViewUpdateTask', '2018-08-10 21:00:00');
INSERT INTO `task_run_log` VALUES ('441', 'PageViewUpdateTask', '2018-08-10 22:00:00');
INSERT INTO `task_run_log` VALUES ('442', 'PageViewUpdateTask', '2018-08-10 22:00:00');
INSERT INTO `task_run_log` VALUES ('443', 'PageViewUpdateTask', '2018-08-10 23:00:00');
INSERT INTO `task_run_log` VALUES ('444', 'PageViewUpdateTask', '2018-08-10 23:00:00');
INSERT INTO `task_run_log` VALUES ('445', 'PageViewUpdateTask', '2018-08-11 00:00:00');
INSERT INTO `task_run_log` VALUES ('446', 'PageViewUpdateTask', '2018-08-11 01:00:00');
INSERT INTO `task_run_log` VALUES ('447', 'PageViewUpdateTask', '2018-08-11 02:00:00');
INSERT INTO `task_run_log` VALUES ('448', 'PageViewUpdateTask', '2018-08-11 03:00:00');
INSERT INTO `task_run_log` VALUES ('449', 'PageViewUpdateTask', '2018-08-11 04:00:00');
INSERT INTO `task_run_log` VALUES ('450', 'PageViewUpdateTask', '2018-08-11 05:00:00');
INSERT INTO `task_run_log` VALUES ('451', 'PageViewUpdateTask', '2018-08-11 06:00:00');
INSERT INTO `task_run_log` VALUES ('452', 'PageViewUpdateTask', '2018-08-11 07:00:00');
INSERT INTO `task_run_log` VALUES ('453', 'PageViewUpdateTask', '2018-08-11 08:00:00');
INSERT INTO `task_run_log` VALUES ('454', 'PageViewUpdateTask', '2018-08-11 09:00:00');
INSERT INTO `task_run_log` VALUES ('455', 'PageViewUpdateTask', '2018-08-11 09:00:00');
INSERT INTO `task_run_log` VALUES ('456', 'PageViewUpdateTask', '2018-08-11 10:00:00');
INSERT INTO `task_run_log` VALUES ('457', 'PageViewUpdateTask', '2018-08-11 10:00:00');
INSERT INTO `task_run_log` VALUES ('458', 'PageViewUpdateTask', '2018-08-11 11:00:00');
INSERT INTO `task_run_log` VALUES ('459', 'PageViewUpdateTask', '2018-08-11 12:00:00');
INSERT INTO `task_run_log` VALUES ('460', 'PageViewUpdateTask', '2018-08-11 13:00:00');
INSERT INTO `task_run_log` VALUES ('461', 'PageViewUpdateTask', '2018-08-11 14:00:00');
INSERT INTO `task_run_log` VALUES ('462', 'PageViewUpdateTask', '2018-08-11 14:00:00');
INSERT INTO `task_run_log` VALUES ('463', 'PageViewUpdateTask', '2018-08-11 15:00:00');
INSERT INTO `task_run_log` VALUES ('464', 'PageViewUpdateTask', '2018-08-11 15:00:00');
INSERT INTO `task_run_log` VALUES ('465', 'PageViewUpdateTask', '2018-08-11 16:00:00');
INSERT INTO `task_run_log` VALUES ('466', 'PageViewUpdateTask', '2018-08-11 16:00:00');
INSERT INTO `task_run_log` VALUES ('467', 'PageViewUpdateTask', '2018-08-11 17:00:00');
INSERT INTO `task_run_log` VALUES ('468', 'PageViewUpdateTask', '2018-08-11 18:00:00');
INSERT INTO `task_run_log` VALUES ('469', 'PageViewUpdateTask', '2018-08-11 19:00:00');
INSERT INTO `task_run_log` VALUES ('470', 'PageViewUpdateTask', '2018-08-11 20:00:00');
INSERT INTO `task_run_log` VALUES ('471', 'PageViewUpdateTask', '2018-08-11 21:00:00');
INSERT INTO `task_run_log` VALUES ('472', 'PageViewUpdateTask', '2018-08-11 22:00:00');
INSERT INTO `task_run_log` VALUES ('473', 'PageViewUpdateTask', '2018-08-11 23:00:00');
INSERT INTO `task_run_log` VALUES ('474', 'PageViewUpdateTask', '2018-08-12 00:00:00');
INSERT INTO `task_run_log` VALUES ('475', 'PageViewUpdateTask', '2018-08-12 01:00:00');
INSERT INTO `task_run_log` VALUES ('476', 'PageViewUpdateTask', '2018-08-12 02:00:00');
INSERT INTO `task_run_log` VALUES ('477', 'PageViewUpdateTask', '2018-08-12 03:00:00');
INSERT INTO `task_run_log` VALUES ('478', 'PageViewUpdateTask', '2018-08-12 04:00:00');
INSERT INTO `task_run_log` VALUES ('479', 'PageViewUpdateTask', '2018-08-12 05:00:00');
INSERT INTO `task_run_log` VALUES ('480', 'PageViewUpdateTask', '2018-08-12 06:00:00');
INSERT INTO `task_run_log` VALUES ('481', 'PageViewUpdateTask', '2018-08-12 07:00:00');
INSERT INTO `task_run_log` VALUES ('482', 'PageViewUpdateTask', '2018-08-12 08:00:00');
INSERT INTO `task_run_log` VALUES ('483', 'PageViewUpdateTask', '2018-08-12 09:00:00');
INSERT INTO `task_run_log` VALUES ('484', 'PageViewUpdateTask', '2018-08-12 10:00:00');
INSERT INTO `task_run_log` VALUES ('485', 'PageViewUpdateTask', '2018-08-12 10:00:00');
INSERT INTO `task_run_log` VALUES ('486', 'PageViewUpdateTask', '2018-08-12 11:00:00');
INSERT INTO `task_run_log` VALUES ('487', 'PageViewUpdateTask', '2018-08-12 11:00:00');
INSERT INTO `task_run_log` VALUES ('488', 'PageViewUpdateTask', '2018-08-12 12:00:00');
INSERT INTO `task_run_log` VALUES ('489', 'PageViewUpdateTask', '2018-08-12 12:00:00');
INSERT INTO `task_run_log` VALUES ('490', 'PageViewUpdateTask', '2018-08-12 13:00:00');
INSERT INTO `task_run_log` VALUES ('491', 'PageViewUpdateTask', '2018-08-12 14:00:00');
INSERT INTO `task_run_log` VALUES ('492', 'PageViewUpdateTask', '2018-08-12 15:00:00');
INSERT INTO `task_run_log` VALUES ('493', 'PageViewUpdateTask', '2018-08-12 15:00:00');
INSERT INTO `task_run_log` VALUES ('494', 'PageViewUpdateTask', '2018-08-12 16:00:00');
INSERT INTO `task_run_log` VALUES ('495', 'PageViewUpdateTask', '2018-08-12 16:00:00');
INSERT INTO `task_run_log` VALUES ('496', 'PageViewUpdateTask', '2018-08-12 17:00:00');
INSERT INTO `task_run_log` VALUES ('497', 'PageViewUpdateTask', '2018-08-12 17:00:00');
INSERT INTO `task_run_log` VALUES ('498', 'PageViewUpdateTask', '2018-08-12 18:00:00');
INSERT INTO `task_run_log` VALUES ('499', 'PageViewUpdateTask', '2018-08-12 18:00:00');
INSERT INTO `task_run_log` VALUES ('500', 'PageViewUpdateTask', '2018-08-12 19:00:00');
INSERT INTO `task_run_log` VALUES ('501', 'PageViewUpdateTask', '2018-08-12 19:00:00');
INSERT INTO `task_run_log` VALUES ('502', 'PageViewUpdateTask', '2018-08-12 20:00:00');
INSERT INTO `task_run_log` VALUES ('503', 'PageViewUpdateTask', '2018-08-12 21:00:00');
INSERT INTO `task_run_log` VALUES ('504', 'PageViewUpdateTask', '2018-08-12 21:00:00');
INSERT INTO `task_run_log` VALUES ('505', 'PageViewUpdateTask', '2018-08-12 22:00:00');
INSERT INTO `task_run_log` VALUES ('506', 'PageViewUpdateTask', '2018-08-12 22:00:00');
INSERT INTO `task_run_log` VALUES ('507', 'PageViewUpdateTask', '2018-08-12 23:00:00');
INSERT INTO `task_run_log` VALUES ('508', 'PageViewUpdateTask', '2018-08-12 23:00:00');
INSERT INTO `task_run_log` VALUES ('509', 'PageViewUpdateTask', '2018-08-13 00:00:00');
INSERT INTO `task_run_log` VALUES ('510', 'PageViewUpdateTask', '2018-08-13 00:00:00');
INSERT INTO `task_run_log` VALUES ('511', 'PageViewUpdateTask', '2018-08-13 01:00:00');
INSERT INTO `task_run_log` VALUES ('512', 'PageViewUpdateTask', '2018-08-13 01:00:00');
INSERT INTO `task_run_log` VALUES ('513', 'PageViewUpdateTask', '2018-08-13 02:00:00');
INSERT INTO `task_run_log` VALUES ('514', 'PageViewUpdateTask', '2018-08-13 02:00:00');
INSERT INTO `task_run_log` VALUES ('515', 'PageViewUpdateTask', '2018-08-13 03:00:00');
INSERT INTO `task_run_log` VALUES ('516', 'PageViewUpdateTask', '2018-08-13 04:00:00');
INSERT INTO `task_run_log` VALUES ('517', 'PageViewUpdateTask', '2018-08-13 05:00:00');
INSERT INTO `task_run_log` VALUES ('518', 'PageViewUpdateTask', '2018-08-13 06:00:00');
INSERT INTO `task_run_log` VALUES ('519', 'PageViewUpdateTask', '2018-08-13 07:00:00');
INSERT INTO `task_run_log` VALUES ('520', 'PageViewUpdateTask', '2018-08-13 08:00:00');
INSERT INTO `task_run_log` VALUES ('521', 'PageViewUpdateTask', '2018-08-13 09:00:00');
INSERT INTO `task_run_log` VALUES ('522', 'PageViewUpdateTask', '2018-08-13 09:00:00');
INSERT INTO `task_run_log` VALUES ('523', 'PageViewUpdateTask', '2018-08-13 10:00:00');
INSERT INTO `task_run_log` VALUES ('524', 'PageViewUpdateTask', '2018-08-13 10:00:00');
INSERT INTO `task_run_log` VALUES ('525', 'PageViewUpdateTask', '2018-08-13 11:00:00');
INSERT INTO `task_run_log` VALUES ('526', 'PageViewUpdateTask', '2018-08-13 12:00:00');
INSERT INTO `task_run_log` VALUES ('527', 'PageViewUpdateTask', '2018-08-13 13:00:00');
INSERT INTO `task_run_log` VALUES ('528', 'PageViewUpdateTask', '2018-08-13 14:00:00');
INSERT INTO `task_run_log` VALUES ('529', 'PageViewUpdateTask', '2018-08-13 15:00:00');
INSERT INTO `task_run_log` VALUES ('530', 'PageViewUpdateTask', '2018-08-13 15:48:50');
INSERT INTO `task_run_log` VALUES ('531', 'PageViewUpdateTask', '2018-08-13 16:00:00');
INSERT INTO `task_run_log` VALUES ('532', 'PageViewUpdateTask', '2018-08-13 16:00:00');
INSERT INTO `task_run_log` VALUES ('533', 'PageViewUpdateTask', '2018-08-13 17:00:00');
INSERT INTO `task_run_log` VALUES ('534', 'PageViewUpdateTask', '2018-08-13 17:00:00');
INSERT INTO `task_run_log` VALUES ('535', 'PageViewUpdateTask', '2018-08-13 18:00:00');
INSERT INTO `task_run_log` VALUES ('536', 'PageViewUpdateTask', '2018-08-13 18:00:00');
INSERT INTO `task_run_log` VALUES ('537', 'PageViewUpdateTask', '2018-08-13 19:00:00');
INSERT INTO `task_run_log` VALUES ('538', 'PageViewUpdateTask', '2018-08-13 19:00:00');
INSERT INTO `task_run_log` VALUES ('539', 'PageViewUpdateTask', '2018-08-13 20:00:00');
INSERT INTO `task_run_log` VALUES ('540', 'PageViewUpdateTask', '2018-08-13 20:00:00');
INSERT INTO `task_run_log` VALUES ('541', 'PageViewUpdateTask', '2018-08-13 21:00:00');
INSERT INTO `task_run_log` VALUES ('542', 'PageViewUpdateTask', '2018-08-13 21:00:00');
INSERT INTO `task_run_log` VALUES ('543', 'PageViewUpdateTask', '2018-08-13 22:00:00');
INSERT INTO `task_run_log` VALUES ('544', 'PageViewUpdateTask', '2018-08-13 22:00:00');
INSERT INTO `task_run_log` VALUES ('545', 'PageViewUpdateTask', '2018-08-13 23:00:00');
INSERT INTO `task_run_log` VALUES ('546', 'PageViewUpdateTask', '2018-08-13 23:00:00');
INSERT INTO `task_run_log` VALUES ('547', 'PageViewUpdateTask', '2018-08-14 00:00:00');
INSERT INTO `task_run_log` VALUES ('548', 'PageViewUpdateTask', '2018-08-14 00:00:00');
INSERT INTO `task_run_log` VALUES ('549', 'PageViewUpdateTask', '2018-08-14 01:00:00');
INSERT INTO `task_run_log` VALUES ('550', 'PageViewUpdateTask', '2018-08-14 01:00:00');
INSERT INTO `task_run_log` VALUES ('551', 'PageViewUpdateTask', '2018-08-14 02:00:00');
INSERT INTO `task_run_log` VALUES ('552', 'PageViewUpdateTask', '2018-08-14 02:00:00');
INSERT INTO `task_run_log` VALUES ('553', 'PageViewUpdateTask', '2018-08-14 03:00:00');
INSERT INTO `task_run_log` VALUES ('554', 'PageViewUpdateTask', '2018-08-14 03:00:00');
INSERT INTO `task_run_log` VALUES ('555', 'PageViewUpdateTask', '2018-08-14 04:00:00');
INSERT INTO `task_run_log` VALUES ('556', 'PageViewUpdateTask', '2018-08-14 04:00:00');
INSERT INTO `task_run_log` VALUES ('557', 'PageViewUpdateTask', '2018-08-14 05:00:00');
INSERT INTO `task_run_log` VALUES ('558', 'PageViewUpdateTask', '2018-08-14 06:00:00');
INSERT INTO `task_run_log` VALUES ('559', 'PageViewUpdateTask', '2018-08-14 07:00:00');
INSERT INTO `task_run_log` VALUES ('560', 'PageViewUpdateTask', '2018-08-14 08:00:00');
INSERT INTO `task_run_log` VALUES ('561', 'PageViewUpdateTask', '2018-08-14 09:00:00');
INSERT INTO `task_run_log` VALUES ('562', 'PageViewUpdateTask', '2018-08-14 09:00:00');
INSERT INTO `task_run_log` VALUES ('563', 'PageViewUpdateTask', '2018-08-14 10:00:00');
INSERT INTO `task_run_log` VALUES ('564', 'PageViewUpdateTask', '2018-08-14 10:00:00');
INSERT INTO `task_run_log` VALUES ('565', 'PageViewUpdateTask', '2018-08-14 11:00:00');
INSERT INTO `task_run_log` VALUES ('566', 'PageViewUpdateTask', '2018-08-14 11:00:00');
INSERT INTO `task_run_log` VALUES ('567', 'PageViewUpdateTask', '2018-08-14 12:00:00');
INSERT INTO `task_run_log` VALUES ('568', 'PageViewUpdateTask', '2018-08-14 12:00:00');
INSERT INTO `task_run_log` VALUES ('569', 'PageViewUpdateTask', '2018-08-14 13:00:00');
INSERT INTO `task_run_log` VALUES ('570', 'PageViewUpdateTask', '2018-08-14 13:00:00');
INSERT INTO `task_run_log` VALUES ('571', 'PageViewUpdateTask', '2018-08-14 14:00:00');
INSERT INTO `task_run_log` VALUES ('572', 'PageViewUpdateTask', '2018-08-14 14:00:00');
INSERT INTO `task_run_log` VALUES ('573', 'PageViewUpdateTask', '2018-08-14 15:00:00');
INSERT INTO `task_run_log` VALUES ('574', 'PageViewUpdateTask', '2018-08-14 15:00:00');
INSERT INTO `task_run_log` VALUES ('575', 'PageViewUpdateTask', '2018-08-14 16:00:00');
INSERT INTO `task_run_log` VALUES ('576', 'PageViewUpdateTask', '2018-08-14 16:00:00');
INSERT INTO `task_run_log` VALUES ('577', 'PageViewUpdateTask', '2018-08-14 17:00:00');
INSERT INTO `task_run_log` VALUES ('578', 'PageViewUpdateTask', '2018-08-14 17:00:00');
INSERT INTO `task_run_log` VALUES ('579', 'PageViewUpdateTask', '2018-08-14 18:00:00');
INSERT INTO `task_run_log` VALUES ('580', 'PageViewUpdateTask', '2018-08-14 18:00:00');
INSERT INTO `task_run_log` VALUES ('581', 'PageViewUpdateTask', '2018-08-14 19:00:00');
INSERT INTO `task_run_log` VALUES ('582', 'PageViewUpdateTask', '2018-08-14 19:00:00');
INSERT INTO `task_run_log` VALUES ('583', 'PageViewUpdateTask', '2018-08-14 20:00:00');
INSERT INTO `task_run_log` VALUES ('584', 'PageViewUpdateTask', '2018-08-14 20:00:00');
INSERT INTO `task_run_log` VALUES ('585', 'PageViewUpdateTask', '2018-08-14 21:00:00');
INSERT INTO `task_run_log` VALUES ('586', 'PageViewUpdateTask', '2018-08-14 21:00:00');
INSERT INTO `task_run_log` VALUES ('587', 'PageViewUpdateTask', '2018-08-14 22:00:00');
INSERT INTO `task_run_log` VALUES ('588', 'PageViewUpdateTask', '2018-08-14 22:00:00');
INSERT INTO `task_run_log` VALUES ('589', 'PageViewUpdateTask', '2018-08-14 23:00:00');
INSERT INTO `task_run_log` VALUES ('590', 'PageViewUpdateTask', '2018-08-14 23:00:00');
INSERT INTO `task_run_log` VALUES ('591', 'PageViewUpdateTask', '2018-08-15 00:00:00');
INSERT INTO `task_run_log` VALUES ('592', 'PageViewUpdateTask', '2018-08-15 00:00:00');
INSERT INTO `task_run_log` VALUES ('593', 'PageViewUpdateTask', '2018-08-15 01:00:00');
INSERT INTO `task_run_log` VALUES ('594', 'PageViewUpdateTask', '2018-08-15 02:00:00');
INSERT INTO `task_run_log` VALUES ('595', 'PageViewUpdateTask', '2018-08-15 03:00:00');
INSERT INTO `task_run_log` VALUES ('596', 'PageViewUpdateTask', '2018-08-15 04:00:00');
INSERT INTO `task_run_log` VALUES ('597', 'PageViewUpdateTask', '2018-08-15 05:00:00');
INSERT INTO `task_run_log` VALUES ('598', 'PageViewUpdateTask', '2018-08-15 06:00:00');
INSERT INTO `task_run_log` VALUES ('599', 'PageViewUpdateTask', '2018-08-15 07:00:00');
INSERT INTO `task_run_log` VALUES ('600', 'PageViewUpdateTask', '2018-08-15 08:00:00');
INSERT INTO `task_run_log` VALUES ('601', 'PageViewUpdateTask', '2018-08-15 08:00:00');
INSERT INTO `task_run_log` VALUES ('602', 'PageViewUpdateTask', '2018-08-15 09:00:00');
INSERT INTO `task_run_log` VALUES ('603', 'PageViewUpdateTask', '2018-08-15 09:00:00');
INSERT INTO `task_run_log` VALUES ('604', 'PageViewUpdateTask', '2018-08-15 10:00:00');
INSERT INTO `task_run_log` VALUES ('605', 'PageViewUpdateTask', '2018-08-15 10:00:00');
INSERT INTO `task_run_log` VALUES ('606', 'PageViewUpdateTask', '2018-08-15 11:00:00');
INSERT INTO `task_run_log` VALUES ('607', 'PageViewUpdateTask', '2018-08-15 11:00:00');
INSERT INTO `task_run_log` VALUES ('608', 'PageViewUpdateTask', '2018-08-15 12:00:00');
INSERT INTO `task_run_log` VALUES ('609', 'PageViewUpdateTask', '2018-08-15 12:00:00');
INSERT INTO `task_run_log` VALUES ('610', 'PageViewUpdateTask', '2018-08-15 13:00:00');
INSERT INTO `task_run_log` VALUES ('611', 'PageViewUpdateTask', '2018-08-15 13:00:00');
INSERT INTO `task_run_log` VALUES ('612', 'PageViewUpdateTask', '2018-08-15 14:00:00');
INSERT INTO `task_run_log` VALUES ('613', 'PageViewUpdateTask', '2018-08-15 14:00:00');
INSERT INTO `task_run_log` VALUES ('614', 'PageViewUpdateTask', '2018-08-15 15:00:00');
INSERT INTO `task_run_log` VALUES ('615', 'PageViewUpdateTask', '2018-08-15 16:00:00');
INSERT INTO `task_run_log` VALUES ('616', 'PageViewUpdateTask', '2018-08-15 16:00:00');
INSERT INTO `task_run_log` VALUES ('617', 'PageViewUpdateTask', '2018-08-15 17:00:00');
INSERT INTO `task_run_log` VALUES ('618', 'PageViewUpdateTask', '2018-08-15 17:00:00');
INSERT INTO `task_run_log` VALUES ('619', 'PageViewUpdateTask', '2018-08-15 18:00:00');
INSERT INTO `task_run_log` VALUES ('620', 'PageViewUpdateTask', '2018-08-15 18:00:00');
INSERT INTO `task_run_log` VALUES ('621', 'PageViewUpdateTask', '2018-08-15 19:00:00');
INSERT INTO `task_run_log` VALUES ('622', 'PageViewUpdateTask', '2018-08-15 19:00:00');
INSERT INTO `task_run_log` VALUES ('623', 'PageViewUpdateTask', '2018-08-15 20:00:00');
INSERT INTO `task_run_log` VALUES ('624', 'PageViewUpdateTask', '2018-08-15 20:00:00');
INSERT INTO `task_run_log` VALUES ('625', 'PageViewUpdateTask', '2018-08-15 21:00:00');
INSERT INTO `task_run_log` VALUES ('626', 'PageViewUpdateTask', '2018-08-15 21:00:00');
INSERT INTO `task_run_log` VALUES ('627', 'PageViewUpdateTask', '2018-08-15 22:00:00');
INSERT INTO `task_run_log` VALUES ('628', 'PageViewUpdateTask', '2018-08-15 22:00:00');
INSERT INTO `task_run_log` VALUES ('629', 'PageViewUpdateTask', '2018-08-15 23:00:00');
INSERT INTO `task_run_log` VALUES ('630', 'PageViewUpdateTask', '2018-08-15 23:00:00');
INSERT INTO `task_run_log` VALUES ('631', 'PageViewUpdateTask', '2018-08-16 00:00:00');
INSERT INTO `task_run_log` VALUES ('632', 'PageViewUpdateTask', '2018-08-16 00:00:00');
INSERT INTO `task_run_log` VALUES ('633', 'PageViewUpdateTask', '2018-08-16 01:00:00');
INSERT INTO `task_run_log` VALUES ('634', 'PageViewUpdateTask', '2018-08-16 02:00:00');
INSERT INTO `task_run_log` VALUES ('635', 'PageViewUpdateTask', '2018-08-16 03:00:00');
INSERT INTO `task_run_log` VALUES ('636', 'PageViewUpdateTask', '2018-08-16 04:00:00');
INSERT INTO `task_run_log` VALUES ('637', 'PageViewUpdateTask', '2018-08-16 05:00:00');
INSERT INTO `task_run_log` VALUES ('638', 'PageViewUpdateTask', '2018-08-16 06:00:00');
INSERT INTO `task_run_log` VALUES ('639', 'PageViewUpdateTask', '2018-08-16 07:00:00');
INSERT INTO `task_run_log` VALUES ('640', 'PageViewUpdateTask', '2018-08-16 08:00:00');
INSERT INTO `task_run_log` VALUES ('641', 'PageViewUpdateTask', '2018-08-16 09:00:00');
INSERT INTO `task_run_log` VALUES ('642', 'PageViewUpdateTask', '2018-08-16 09:00:00');
INSERT INTO `task_run_log` VALUES ('643', 'PageViewUpdateTask', '2018-08-16 10:00:00');
INSERT INTO `task_run_log` VALUES ('644', 'PageViewUpdateTask', '2018-08-16 10:00:00');
INSERT INTO `task_run_log` VALUES ('645', 'PageViewUpdateTask', '2018-08-16 11:00:00');
INSERT INTO `task_run_log` VALUES ('646', 'PageViewUpdateTask', '2018-08-16 11:00:00');
INSERT INTO `task_run_log` VALUES ('647', 'PageViewUpdateTask', '2018-08-16 12:00:00');
INSERT INTO `task_run_log` VALUES ('648', 'PageViewUpdateTask', '2018-08-16 12:00:00');
INSERT INTO `task_run_log` VALUES ('649', 'PageViewUpdateTask', '2018-08-16 13:00:00');
INSERT INTO `task_run_log` VALUES ('650', 'PageViewUpdateTask', '2018-08-16 13:00:00');
INSERT INTO `task_run_log` VALUES ('651', 'PageViewUpdateTask', '2018-08-16 14:00:00');
INSERT INTO `task_run_log` VALUES ('652', 'PageViewUpdateTask', '2018-08-16 14:00:00');
INSERT INTO `task_run_log` VALUES ('653', 'PageViewUpdateTask', '2018-08-16 15:00:00');
INSERT INTO `task_run_log` VALUES ('654', 'PageViewUpdateTask', '2018-08-16 15:00:00');
INSERT INTO `task_run_log` VALUES ('655', 'PageViewUpdateTask', '2018-08-16 16:00:00');
INSERT INTO `task_run_log` VALUES ('656', 'PageViewUpdateTask', '2018-08-16 16:00:00');
INSERT INTO `task_run_log` VALUES ('657', 'PageViewUpdateTask', '2018-08-16 17:00:00');
INSERT INTO `task_run_log` VALUES ('658', 'PageViewUpdateTask', '2018-08-16 17:00:00');
INSERT INTO `task_run_log` VALUES ('659', 'PageViewUpdateTask', '2018-08-16 18:00:00');
INSERT INTO `task_run_log` VALUES ('660', 'PageViewUpdateTask', '2018-08-16 18:00:00');
INSERT INTO `task_run_log` VALUES ('661', 'PageViewUpdateTask', '2018-08-16 19:00:00');
INSERT INTO `task_run_log` VALUES ('662', 'PageViewUpdateTask', '2018-08-16 19:00:00');
INSERT INTO `task_run_log` VALUES ('663', 'PageViewUpdateTask', '2018-08-16 20:00:00');
INSERT INTO `task_run_log` VALUES ('664', 'PageViewUpdateTask', '2018-08-16 20:00:00');
INSERT INTO `task_run_log` VALUES ('665', 'PageViewUpdateTask', '2018-08-16 21:00:00');
INSERT INTO `task_run_log` VALUES ('666', 'PageViewUpdateTask', '2018-08-16 21:00:00');
INSERT INTO `task_run_log` VALUES ('667', 'PageViewUpdateTask', '2018-08-16 22:00:00');
INSERT INTO `task_run_log` VALUES ('668', 'PageViewUpdateTask', '2018-08-16 22:00:00');
INSERT INTO `task_run_log` VALUES ('669', 'PageViewUpdateTask', '2018-08-16 23:00:00');
INSERT INTO `task_run_log` VALUES ('670', 'PageViewUpdateTask', '2018-08-16 23:00:00');
INSERT INTO `task_run_log` VALUES ('671', 'PageViewUpdateTask', '2018-08-17 00:00:00');
INSERT INTO `task_run_log` VALUES ('672', 'PageViewUpdateTask', '2018-08-17 00:00:00');
INSERT INTO `task_run_log` VALUES ('673', 'PageViewUpdateTask', '2018-08-17 01:00:00');
INSERT INTO `task_run_log` VALUES ('674', 'PageViewUpdateTask', '2018-08-17 02:00:00');
INSERT INTO `task_run_log` VALUES ('675', 'PageViewUpdateTask', '2018-08-17 03:00:00');
INSERT INTO `task_run_log` VALUES ('676', 'PageViewUpdateTask', '2018-08-17 04:00:00');
INSERT INTO `task_run_log` VALUES ('677', 'PageViewUpdateTask', '2018-08-17 05:00:00');
INSERT INTO `task_run_log` VALUES ('678', 'PageViewUpdateTask', '2018-08-17 06:00:00');
INSERT INTO `task_run_log` VALUES ('679', 'PageViewUpdateTask', '2018-08-17 07:00:00');
INSERT INTO `task_run_log` VALUES ('680', 'PageViewUpdateTask', '2018-08-17 08:00:00');
INSERT INTO `task_run_log` VALUES ('681', 'PageViewUpdateTask', '2018-08-17 09:00:00');
INSERT INTO `task_run_log` VALUES ('682', 'PageViewUpdateTask', '2018-08-17 09:00:00');
INSERT INTO `task_run_log` VALUES ('683', 'PageViewUpdateTask', '2018-08-17 10:00:00');
INSERT INTO `task_run_log` VALUES ('684', 'PageViewUpdateTask', '2018-08-17 10:00:00');
INSERT INTO `task_run_log` VALUES ('685', 'PageViewUpdateTask', '2018-08-17 11:00:00');
INSERT INTO `task_run_log` VALUES ('686', 'PageViewUpdateTask', '2018-08-17 11:00:00');
INSERT INTO `task_run_log` VALUES ('687', 'PageViewUpdateTask', '2018-08-17 12:00:00');
INSERT INTO `task_run_log` VALUES ('688', 'PageViewUpdateTask', '2018-08-17 12:00:00');
INSERT INTO `task_run_log` VALUES ('689', 'PageViewUpdateTask', '2018-08-17 13:00:00');
INSERT INTO `task_run_log` VALUES ('690', 'PageViewUpdateTask', '2018-08-17 13:00:00');
INSERT INTO `task_run_log` VALUES ('691', 'PageViewUpdateTask', '2018-08-17 14:00:00');
INSERT INTO `task_run_log` VALUES ('692', 'PageViewUpdateTask', '2018-08-17 14:00:00');
INSERT INTO `task_run_log` VALUES ('693', 'PageViewUpdateTask', '2018-08-17 15:00:00');
INSERT INTO `task_run_log` VALUES ('694', 'PageViewUpdateTask', '2018-08-17 15:00:00');
INSERT INTO `task_run_log` VALUES ('695', 'PageViewUpdateTask', '2018-08-17 16:00:00');
INSERT INTO `task_run_log` VALUES ('696', 'PageViewUpdateTask', '2018-08-17 16:00:00');
INSERT INTO `task_run_log` VALUES ('697', 'PageViewUpdateTask', '2018-08-17 17:00:00');
INSERT INTO `task_run_log` VALUES ('698', 'PageViewUpdateTask', '2018-08-17 17:00:00');
INSERT INTO `task_run_log` VALUES ('699', 'PageViewUpdateTask', '2018-08-17 18:00:00');
INSERT INTO `task_run_log` VALUES ('700', 'PageViewUpdateTask', '2018-08-17 18:00:00');
INSERT INTO `task_run_log` VALUES ('701', 'PageViewUpdateTask', '2018-08-17 19:00:00');
INSERT INTO `task_run_log` VALUES ('702', 'PageViewUpdateTask', '2018-08-17 19:00:00');
INSERT INTO `task_run_log` VALUES ('703', 'PageViewUpdateTask', '2018-08-17 20:00:00');
INSERT INTO `task_run_log` VALUES ('704', 'PageViewUpdateTask', '2018-08-17 20:00:00');
INSERT INTO `task_run_log` VALUES ('705', 'PageViewUpdateTask', '2018-08-17 21:00:00');
INSERT INTO `task_run_log` VALUES ('706', 'PageViewUpdateTask', '2018-08-17 21:00:00');
INSERT INTO `task_run_log` VALUES ('707', 'PageViewUpdateTask', '2018-08-17 22:00:00');
INSERT INTO `task_run_log` VALUES ('708', 'PageViewUpdateTask', '2018-08-17 22:00:00');
INSERT INTO `task_run_log` VALUES ('709', 'PageViewUpdateTask', '2018-08-17 23:00:00');
INSERT INTO `task_run_log` VALUES ('710', 'PageViewUpdateTask', '2018-08-17 23:00:00');
INSERT INTO `task_run_log` VALUES ('711', 'PageViewUpdateTask', '2018-08-18 00:00:00');
INSERT INTO `task_run_log` VALUES ('712', 'PageViewUpdateTask', '2018-08-18 00:00:00');
INSERT INTO `task_run_log` VALUES ('713', 'PageViewUpdateTask', '2018-08-18 01:00:00');
INSERT INTO `task_run_log` VALUES ('714', 'PageViewUpdateTask', '2018-08-18 02:00:00');
INSERT INTO `task_run_log` VALUES ('715', 'PageViewUpdateTask', '2018-08-18 03:00:00');
INSERT INTO `task_run_log` VALUES ('716', 'PageViewUpdateTask', '2018-08-18 04:00:00');
INSERT INTO `task_run_log` VALUES ('717', 'PageViewUpdateTask', '2018-08-18 05:00:00');
INSERT INTO `task_run_log` VALUES ('718', 'PageViewUpdateTask', '2018-08-18 06:00:00');
INSERT INTO `task_run_log` VALUES ('719', 'PageViewUpdateTask', '2018-08-18 07:00:00');
INSERT INTO `task_run_log` VALUES ('720', 'PageViewUpdateTask', '2018-08-18 08:00:00');
INSERT INTO `task_run_log` VALUES ('721', 'PageViewUpdateTask', '2018-08-18 09:00:00');
INSERT INTO `task_run_log` VALUES ('722', 'PageViewUpdateTask', '2018-08-18 10:00:00');
INSERT INTO `task_run_log` VALUES ('723', 'PageViewUpdateTask', '2018-08-18 11:00:00');
INSERT INTO `task_run_log` VALUES ('724', 'PageViewUpdateTask', '2018-08-18 11:00:00');
INSERT INTO `task_run_log` VALUES ('725', 'PageViewUpdateTask', '2018-08-18 12:00:00');
INSERT INTO `task_run_log` VALUES ('726', 'PageViewUpdateTask', '2018-08-18 12:00:00');
INSERT INTO `task_run_log` VALUES ('727', 'PageViewUpdateTask', '2018-08-18 13:00:00');
INSERT INTO `task_run_log` VALUES ('728', 'PageViewUpdateTask', '2018-08-18 13:00:00');
INSERT INTO `task_run_log` VALUES ('729', 'PageViewUpdateTask', '2018-08-18 14:00:00');
INSERT INTO `task_run_log` VALUES ('730', 'PageViewUpdateTask', '2018-08-18 14:00:00');
INSERT INTO `task_run_log` VALUES ('731', 'PageViewUpdateTask', '2018-08-18 15:00:00');
INSERT INTO `task_run_log` VALUES ('732', 'PageViewUpdateTask', '2018-08-18 15:00:00');
INSERT INTO `task_run_log` VALUES ('733', 'PageViewUpdateTask', '2018-08-18 16:00:00');
INSERT INTO `task_run_log` VALUES ('734', 'PageViewUpdateTask', '2018-08-18 16:00:00');
INSERT INTO `task_run_log` VALUES ('735', 'PageViewUpdateTask', '2018-08-18 17:00:00');
INSERT INTO `task_run_log` VALUES ('736', 'PageViewUpdateTask', '2018-08-18 17:00:00');
INSERT INTO `task_run_log` VALUES ('737', 'PageViewUpdateTask', '2018-08-18 18:00:00');
INSERT INTO `task_run_log` VALUES ('738', 'PageViewUpdateTask', '2018-08-18 18:00:00');
INSERT INTO `task_run_log` VALUES ('739', 'PageViewUpdateTask', '2018-08-18 19:00:00');
INSERT INTO `task_run_log` VALUES ('740', 'PageViewUpdateTask', '2018-08-18 19:00:00');
INSERT INTO `task_run_log` VALUES ('741', 'PageViewUpdateTask', '2018-08-18 20:00:00');
INSERT INTO `task_run_log` VALUES ('742', 'PageViewUpdateTask', '2018-08-18 20:00:00');
INSERT INTO `task_run_log` VALUES ('743', 'PageViewUpdateTask', '2018-08-18 21:00:00');
INSERT INTO `task_run_log` VALUES ('744', 'PageViewUpdateTask', '2018-08-18 21:00:00');
INSERT INTO `task_run_log` VALUES ('745', 'PageViewUpdateTask', '2018-08-18 22:00:00');
INSERT INTO `task_run_log` VALUES ('746', 'PageViewUpdateTask', '2018-08-18 22:00:00');
INSERT INTO `task_run_log` VALUES ('747', 'PageViewUpdateTask', '2018-08-18 23:00:00');
INSERT INTO `task_run_log` VALUES ('748', 'PageViewUpdateTask', '2018-08-18 23:00:00');
INSERT INTO `task_run_log` VALUES ('749', 'PageViewUpdateTask', '2018-08-19 00:00:00');
INSERT INTO `task_run_log` VALUES ('750', 'PageViewUpdateTask', '2018-08-19 00:00:00');
INSERT INTO `task_run_log` VALUES ('751', 'PageViewUpdateTask', '2018-08-19 01:00:00');
INSERT INTO `task_run_log` VALUES ('752', 'PageViewUpdateTask', '2018-08-19 01:00:00');
INSERT INTO `task_run_log` VALUES ('753', 'PageViewUpdateTask', '2018-08-19 02:00:00');
INSERT INTO `task_run_log` VALUES ('754', 'PageViewUpdateTask', '2018-08-19 03:00:00');
INSERT INTO `task_run_log` VALUES ('755', 'PageViewUpdateTask', '2018-08-19 04:00:00');
INSERT INTO `task_run_log` VALUES ('756', 'PageViewUpdateTask', '2018-08-19 05:00:00');
INSERT INTO `task_run_log` VALUES ('757', 'PageViewUpdateTask', '2018-08-19 06:00:00');
INSERT INTO `task_run_log` VALUES ('758', 'PageViewUpdateTask', '2018-08-19 07:00:00');
INSERT INTO `task_run_log` VALUES ('759', 'PageViewUpdateTask', '2018-08-19 08:00:00');
INSERT INTO `task_run_log` VALUES ('760', 'PageViewUpdateTask', '2018-08-19 09:00:00');
INSERT INTO `task_run_log` VALUES ('761', 'PageViewUpdateTask', '2018-08-19 09:00:00');
INSERT INTO `task_run_log` VALUES ('762', 'PageViewUpdateTask', '2018-08-19 10:00:00');
INSERT INTO `task_run_log` VALUES ('763', 'PageViewUpdateTask', '2018-08-19 10:00:00');
INSERT INTO `task_run_log` VALUES ('764', 'PageViewUpdateTask', '2018-08-19 11:00:00');
INSERT INTO `task_run_log` VALUES ('765', 'PageViewUpdateTask', '2018-08-19 11:00:00');
INSERT INTO `task_run_log` VALUES ('766', 'PageViewUpdateTask', '2018-08-19 12:00:00');
INSERT INTO `task_run_log` VALUES ('767', 'PageViewUpdateTask', '2018-08-19 12:00:00');
INSERT INTO `task_run_log` VALUES ('768', 'PageViewUpdateTask', '2018-08-19 13:00:00');
INSERT INTO `task_run_log` VALUES ('769', 'PageViewUpdateTask', '2018-08-19 13:00:00');
INSERT INTO `task_run_log` VALUES ('770', 'PageViewUpdateTask', '2018-08-19 14:00:00');
INSERT INTO `task_run_log` VALUES ('771', 'PageViewUpdateTask', '2018-08-19 14:00:00');
INSERT INTO `task_run_log` VALUES ('772', 'PageViewUpdateTask', '2018-08-19 15:00:00');
INSERT INTO `task_run_log` VALUES ('773', 'PageViewUpdateTask', '2018-08-19 15:00:00');
INSERT INTO `task_run_log` VALUES ('774', 'PageViewUpdateTask', '2018-08-19 16:00:00');
INSERT INTO `task_run_log` VALUES ('775', 'PageViewUpdateTask', '2018-08-19 16:00:00');
INSERT INTO `task_run_log` VALUES ('776', 'PageViewUpdateTask', '2018-08-19 17:00:00');
INSERT INTO `task_run_log` VALUES ('777', 'PageViewUpdateTask', '2018-08-19 17:00:00');
INSERT INTO `task_run_log` VALUES ('778', 'PageViewUpdateTask', '2018-08-19 18:00:00');
INSERT INTO `task_run_log` VALUES ('779', 'PageViewUpdateTask', '2018-08-19 18:00:00');
INSERT INTO `task_run_log` VALUES ('780', 'PageViewUpdateTask', '2018-08-19 19:00:00');
INSERT INTO `task_run_log` VALUES ('781', 'PageViewUpdateTask', '2018-08-19 19:00:00');
INSERT INTO `task_run_log` VALUES ('782', 'PageViewUpdateTask', '2018-08-19 20:00:00');
INSERT INTO `task_run_log` VALUES ('783', 'PageViewUpdateTask', '2018-08-19 20:00:00');
INSERT INTO `task_run_log` VALUES ('784', 'PageViewUpdateTask', '2018-08-19 21:00:00');
INSERT INTO `task_run_log` VALUES ('785', 'PageViewUpdateTask', '2018-08-19 21:00:00');
INSERT INTO `task_run_log` VALUES ('786', 'PageViewUpdateTask', '2018-08-19 22:00:00');
INSERT INTO `task_run_log` VALUES ('787', 'PageViewUpdateTask', '2018-08-19 22:00:00');
INSERT INTO `task_run_log` VALUES ('788', 'PageViewUpdateTask', '2018-08-19 23:00:00');
INSERT INTO `task_run_log` VALUES ('789', 'PageViewUpdateTask', '2018-08-19 23:00:00');
INSERT INTO `task_run_log` VALUES ('790', 'PageViewUpdateTask', '2018-08-20 00:00:00');
INSERT INTO `task_run_log` VALUES ('791', 'PageViewUpdateTask', '2018-08-20 01:00:00');
INSERT INTO `task_run_log` VALUES ('792', 'PageViewUpdateTask', '2018-08-20 02:00:00');
INSERT INTO `task_run_log` VALUES ('793', 'PageViewUpdateTask', '2018-08-20 03:00:00');
INSERT INTO `task_run_log` VALUES ('794', 'PageViewUpdateTask', '2018-08-20 04:00:00');
INSERT INTO `task_run_log` VALUES ('795', 'PageViewUpdateTask', '2018-08-20 05:00:00');
INSERT INTO `task_run_log` VALUES ('796', 'PageViewUpdateTask', '2018-08-20 06:00:00');
INSERT INTO `task_run_log` VALUES ('797', 'PageViewUpdateTask', '2018-08-20 07:00:00');
INSERT INTO `task_run_log` VALUES ('798', 'PageViewUpdateTask', '2018-08-20 08:00:00');
INSERT INTO `task_run_log` VALUES ('799', 'PageViewUpdateTask', '2018-08-20 09:00:00');
INSERT INTO `task_run_log` VALUES ('800', 'PageViewUpdateTask', '2018-08-20 10:00:00');
INSERT INTO `task_run_log` VALUES ('801', 'PageViewUpdateTask', '2018-08-20 10:00:00');
INSERT INTO `task_run_log` VALUES ('802', 'PageViewUpdateTask', '2018-08-20 11:00:00');
INSERT INTO `task_run_log` VALUES ('803', 'PageViewUpdateTask', '2018-08-20 11:00:00');
INSERT INTO `task_run_log` VALUES ('804', 'PageViewUpdateTask', '2018-08-20 12:00:00');
INSERT INTO `task_run_log` VALUES ('805', 'PageViewUpdateTask', '2018-08-20 12:00:00');
INSERT INTO `task_run_log` VALUES ('806', 'PageViewUpdateTask', '2018-08-20 13:00:00');
INSERT INTO `task_run_log` VALUES ('807', 'PageViewUpdateTask', '2018-08-20 13:00:00');
INSERT INTO `task_run_log` VALUES ('808', 'PageViewUpdateTask', '2018-08-20 14:00:00');
INSERT INTO `task_run_log` VALUES ('809', 'PageViewUpdateTask', '2018-08-20 14:00:00');
INSERT INTO `task_run_log` VALUES ('810', 'PageViewUpdateTask', '2018-08-20 15:00:00');
INSERT INTO `task_run_log` VALUES ('811', 'PageViewUpdateTask', '2018-08-20 15:00:00');
INSERT INTO `task_run_log` VALUES ('812', 'PageViewUpdateTask', '2018-08-20 16:00:00');
INSERT INTO `task_run_log` VALUES ('813', 'PageViewUpdateTask', '2018-08-20 16:00:00');
INSERT INTO `task_run_log` VALUES ('814', 'PageViewUpdateTask', '2018-08-20 17:00:00');
INSERT INTO `task_run_log` VALUES ('815', 'PageViewUpdateTask', '2018-08-20 17:00:00');
INSERT INTO `task_run_log` VALUES ('816', 'PageViewUpdateTask', '2018-08-20 18:00:00');
INSERT INTO `task_run_log` VALUES ('817', 'PageViewUpdateTask', '2018-08-20 18:00:00');
INSERT INTO `task_run_log` VALUES ('818', 'PageViewUpdateTask', '2018-08-20 19:00:00');
INSERT INTO `task_run_log` VALUES ('819', 'PageViewUpdateTask', '2018-08-20 19:00:00');
INSERT INTO `task_run_log` VALUES ('820', 'PageViewUpdateTask', '2018-08-20 20:00:00');
INSERT INTO `task_run_log` VALUES ('821', 'PageViewUpdateTask', '2018-08-20 20:00:00');
INSERT INTO `task_run_log` VALUES ('822', 'PageViewUpdateTask', '2018-08-20 21:00:00');
INSERT INTO `task_run_log` VALUES ('823', 'PageViewUpdateTask', '2018-08-20 21:00:00');
INSERT INTO `task_run_log` VALUES ('824', 'PageViewUpdateTask', '2018-08-20 22:00:00');
INSERT INTO `task_run_log` VALUES ('825', 'PageViewUpdateTask', '2018-08-20 22:00:00');
INSERT INTO `task_run_log` VALUES ('826', 'PageViewUpdateTask', '2018-08-20 23:00:00');
INSERT INTO `task_run_log` VALUES ('827', 'PageViewUpdateTask', '2018-08-20 23:00:00');
INSERT INTO `task_run_log` VALUES ('828', 'PageViewUpdateTask', '2018-08-21 00:00:00');
INSERT INTO `task_run_log` VALUES ('829', 'PageViewUpdateTask', '2018-08-21 00:00:00');
INSERT INTO `task_run_log` VALUES ('830', 'PageViewUpdateTask', '2018-08-21 01:00:00');
INSERT INTO `task_run_log` VALUES ('831', 'PageViewUpdateTask', '2018-08-21 01:00:00');
INSERT INTO `task_run_log` VALUES ('832', 'PageViewUpdateTask', '2018-08-21 02:00:00');
INSERT INTO `task_run_log` VALUES ('833', 'PageViewUpdateTask', '2018-08-21 02:00:00');
INSERT INTO `task_run_log` VALUES ('834', 'PageViewUpdateTask', '2018-08-21 03:00:00');
INSERT INTO `task_run_log` VALUES ('835', 'PageViewUpdateTask', '2018-08-21 03:00:00');
INSERT INTO `task_run_log` VALUES ('836', 'PageViewUpdateTask', '2018-08-21 04:00:00');
INSERT INTO `task_run_log` VALUES ('837', 'PageViewUpdateTask', '2018-08-21 05:00:00');
INSERT INTO `task_run_log` VALUES ('838', 'PageViewUpdateTask', '2018-08-21 06:00:00');
INSERT INTO `task_run_log` VALUES ('839', 'PageViewUpdateTask', '2018-08-21 07:00:00');
INSERT INTO `task_run_log` VALUES ('840', 'PageViewUpdateTask', '2018-08-21 08:00:00');
INSERT INTO `task_run_log` VALUES ('841', 'PageViewUpdateTask', '2018-08-21 09:00:00');
INSERT INTO `task_run_log` VALUES ('842', 'PageViewUpdateTask', '2018-08-21 09:00:00');
INSERT INTO `task_run_log` VALUES ('843', 'PageViewUpdateTask', '2018-08-21 10:00:00');
INSERT INTO `task_run_log` VALUES ('844', 'PageViewUpdateTask', '2018-08-21 10:00:00');
INSERT INTO `task_run_log` VALUES ('845', 'PageViewUpdateTask', '2018-08-21 11:00:00');
INSERT INTO `task_run_log` VALUES ('846', 'PageViewUpdateTask', '2018-08-21 11:00:00');
INSERT INTO `task_run_log` VALUES ('847', 'PageViewUpdateTask', '2018-08-21 12:00:00');
INSERT INTO `task_run_log` VALUES ('848', 'PageViewUpdateTask', '2018-08-21 12:00:00');
INSERT INTO `task_run_log` VALUES ('849', 'PageViewUpdateTask', '2018-08-21 13:00:00');
INSERT INTO `task_run_log` VALUES ('850', 'PageViewUpdateTask', '2018-08-21 13:00:00');
INSERT INTO `task_run_log` VALUES ('851', 'PageViewUpdateTask', '2018-08-21 14:00:00');
INSERT INTO `task_run_log` VALUES ('852', 'PageViewUpdateTask', '2018-08-21 14:00:00');
INSERT INTO `task_run_log` VALUES ('853', 'PageViewUpdateTask', '2018-08-21 15:00:00');
INSERT INTO `task_run_log` VALUES ('854', 'PageViewUpdateTask', '2018-08-21 15:00:00');
INSERT INTO `task_run_log` VALUES ('855', 'PageViewUpdateTask', '2018-08-21 16:00:00');
INSERT INTO `task_run_log` VALUES ('856', 'PageViewUpdateTask', '2018-08-21 16:00:00');
INSERT INTO `task_run_log` VALUES ('857', 'PageViewUpdateTask', '2018-08-21 17:00:00');
INSERT INTO `task_run_log` VALUES ('858', 'PageViewUpdateTask', '2018-08-21 17:00:00');
INSERT INTO `task_run_log` VALUES ('859', 'PageViewUpdateTask', '2018-08-21 18:00:00');
INSERT INTO `task_run_log` VALUES ('860', 'PageViewUpdateTask', '2018-08-21 18:00:00');
INSERT INTO `task_run_log` VALUES ('861', 'PageViewUpdateTask', '2018-08-21 19:00:00');
INSERT INTO `task_run_log` VALUES ('862', 'PageViewUpdateTask', '2018-08-21 19:00:00');
INSERT INTO `task_run_log` VALUES ('863', 'PageViewUpdateTask', '2018-08-21 20:00:00');
INSERT INTO `task_run_log` VALUES ('864', 'PageViewUpdateTask', '2018-08-21 20:00:00');
INSERT INTO `task_run_log` VALUES ('865', 'PageViewUpdateTask', '2018-08-21 21:00:00');
INSERT INTO `task_run_log` VALUES ('866', 'PageViewUpdateTask', '2018-08-21 21:00:00');
INSERT INTO `task_run_log` VALUES ('867', 'PageViewUpdateTask', '2018-08-21 22:00:00');
INSERT INTO `task_run_log` VALUES ('868', 'PageViewUpdateTask', '2018-08-21 22:00:00');
INSERT INTO `task_run_log` VALUES ('869', 'PageViewUpdateTask', '2018-08-21 23:00:00');
INSERT INTO `task_run_log` VALUES ('870', 'PageViewUpdateTask', '2018-08-21 23:00:00');
INSERT INTO `task_run_log` VALUES ('871', 'PageViewUpdateTask', '2018-08-22 00:00:00');
INSERT INTO `task_run_log` VALUES ('872', 'PageViewUpdateTask', '2018-08-22 00:00:00');
INSERT INTO `task_run_log` VALUES ('873', 'PageViewUpdateTask', '2018-08-22 01:00:00');
INSERT INTO `task_run_log` VALUES ('874', 'PageViewUpdateTask', '2018-08-22 02:00:00');
INSERT INTO `task_run_log` VALUES ('875', 'PageViewUpdateTask', '2018-08-22 03:00:00');
INSERT INTO `task_run_log` VALUES ('876', 'PageViewUpdateTask', '2018-08-22 04:00:00');
INSERT INTO `task_run_log` VALUES ('877', 'PageViewUpdateTask', '2018-08-22 05:00:00');
INSERT INTO `task_run_log` VALUES ('878', 'PageViewUpdateTask', '2018-08-22 06:00:00');
INSERT INTO `task_run_log` VALUES ('879', 'PageViewUpdateTask', '2018-08-22 07:00:00');
INSERT INTO `task_run_log` VALUES ('880', 'PageViewUpdateTask', '2018-08-22 08:00:00');
INSERT INTO `task_run_log` VALUES ('881', 'PageViewUpdateTask', '2018-08-22 09:00:00');
INSERT INTO `task_run_log` VALUES ('882', 'PageViewUpdateTask', '2018-08-22 09:24:05');
INSERT INTO `task_run_log` VALUES ('883', 'PageViewUpdateTask', '2018-08-22 10:00:00');
INSERT INTO `task_run_log` VALUES ('884', 'PageViewUpdateTask', '2018-08-22 10:00:00');
INSERT INTO `task_run_log` VALUES ('885', 'PageViewUpdateTask', '2018-08-22 11:00:00');
INSERT INTO `task_run_log` VALUES ('886', 'PageViewUpdateTask', '2018-08-22 11:00:00');
INSERT INTO `task_run_log` VALUES ('887', 'PageViewUpdateTask', '2018-08-22 12:00:00');
INSERT INTO `task_run_log` VALUES ('888', 'PageViewUpdateTask', '2018-08-22 12:00:00');
INSERT INTO `task_run_log` VALUES ('889', 'PageViewUpdateTask', '2018-08-22 13:00:00');
INSERT INTO `task_run_log` VALUES ('890', 'PageViewUpdateTask', '2018-08-22 13:00:00');
INSERT INTO `task_run_log` VALUES ('891', 'PageViewUpdateTask', '2018-08-22 14:00:00');
INSERT INTO `task_run_log` VALUES ('892', 'PageViewUpdateTask', '2018-08-22 14:00:00');
INSERT INTO `task_run_log` VALUES ('893', 'PageViewUpdateTask', '2018-08-22 15:00:00');
INSERT INTO `task_run_log` VALUES ('894', 'PageViewUpdateTask', '2018-08-22 15:00:00');
INSERT INTO `task_run_log` VALUES ('895', 'PageViewUpdateTask', '2018-08-22 16:00:00');
INSERT INTO `task_run_log` VALUES ('896', 'PageViewUpdateTask', '2018-08-22 16:00:00');
INSERT INTO `task_run_log` VALUES ('897', 'PageViewUpdateTask', '2018-08-22 17:00:00');
INSERT INTO `task_run_log` VALUES ('898', 'PageViewUpdateTask', '2018-08-22 17:00:00');
INSERT INTO `task_run_log` VALUES ('899', 'PageViewUpdateTask', '2018-08-22 18:00:00');
INSERT INTO `task_run_log` VALUES ('900', 'PageViewUpdateTask', '2018-08-22 18:00:00');
INSERT INTO `task_run_log` VALUES ('901', 'PageViewUpdateTask', '2018-08-22 19:00:00');
INSERT INTO `task_run_log` VALUES ('902', 'PageViewUpdateTask', '2018-08-22 19:00:00');
INSERT INTO `task_run_log` VALUES ('903', 'PageViewUpdateTask', '2018-08-22 20:00:00');
INSERT INTO `task_run_log` VALUES ('904', 'PageViewUpdateTask', '2018-08-22 20:00:00');
INSERT INTO `task_run_log` VALUES ('905', 'PageViewUpdateTask', '2018-08-22 21:00:00');
INSERT INTO `task_run_log` VALUES ('906', 'PageViewUpdateTask', '2018-08-22 21:00:00');
INSERT INTO `task_run_log` VALUES ('907', 'PageViewUpdateTask', '2018-08-22 22:00:00');
INSERT INTO `task_run_log` VALUES ('908', 'PageViewUpdateTask', '2018-08-22 22:00:00');
INSERT INTO `task_run_log` VALUES ('909', 'PageViewUpdateTask', '2018-08-22 23:00:00');
INSERT INTO `task_run_log` VALUES ('910', 'PageViewUpdateTask', '2018-08-22 23:00:00');
INSERT INTO `task_run_log` VALUES ('911', 'PageViewUpdateTask', '2018-08-23 00:00:00');
INSERT INTO `task_run_log` VALUES ('912', 'PageViewUpdateTask', '2018-08-23 00:00:00');
INSERT INTO `task_run_log` VALUES ('913', 'PageViewUpdateTask', '2018-08-23 01:00:00');
INSERT INTO `task_run_log` VALUES ('914', 'PageViewUpdateTask', '2018-08-23 02:00:00');
INSERT INTO `task_run_log` VALUES ('915', 'PageViewUpdateTask', '2018-08-23 03:00:00');
INSERT INTO `task_run_log` VALUES ('916', 'PageViewUpdateTask', '2018-08-23 04:00:00');
INSERT INTO `task_run_log` VALUES ('917', 'PageViewUpdateTask', '2018-08-23 05:00:00');
INSERT INTO `task_run_log` VALUES ('918', 'PageViewUpdateTask', '2018-08-23 06:00:00');
INSERT INTO `task_run_log` VALUES ('919', 'PageViewUpdateTask', '2018-08-23 07:00:00');
INSERT INTO `task_run_log` VALUES ('920', 'PageViewUpdateTask', '2018-08-23 08:00:00');
INSERT INTO `task_run_log` VALUES ('921', 'PageViewUpdateTask', '2018-08-23 09:00:00');
INSERT INTO `task_run_log` VALUES ('922', 'PageViewUpdateTask', '2018-08-23 09:00:00');
INSERT INTO `task_run_log` VALUES ('923', 'PageViewUpdateTask', '2018-08-23 10:00:00');
INSERT INTO `task_run_log` VALUES ('924', 'PageViewUpdateTask', '2018-08-23 10:00:00');
INSERT INTO `task_run_log` VALUES ('925', 'PageViewUpdateTask', '2018-08-23 11:00:00');
INSERT INTO `task_run_log` VALUES ('926', 'PageViewUpdateTask', '2018-08-23 11:00:00');
INSERT INTO `task_run_log` VALUES ('927', 'PageViewUpdateTask', '2018-08-23 12:00:00');
INSERT INTO `task_run_log` VALUES ('928', 'PageViewUpdateTask', '2018-08-23 12:00:00');
INSERT INTO `task_run_log` VALUES ('929', 'PageViewUpdateTask', '2018-08-23 13:00:00');
INSERT INTO `task_run_log` VALUES ('930', 'PageViewUpdateTask', '2018-08-23 13:00:00');
INSERT INTO `task_run_log` VALUES ('931', 'PageViewUpdateTask', '2018-08-23 14:00:00');
INSERT INTO `task_run_log` VALUES ('932', 'PageViewUpdateTask', '2018-08-23 14:00:00');
INSERT INTO `task_run_log` VALUES ('933', 'PageViewUpdateTask', '2018-08-23 15:00:00');
INSERT INTO `task_run_log` VALUES ('934', 'PageViewUpdateTask', '2018-08-23 16:00:00');
INSERT INTO `task_run_log` VALUES ('935', 'PageViewUpdateTask', '2018-08-23 17:00:00');
INSERT INTO `task_run_log` VALUES ('936', 'PageViewUpdateTask', '2018-08-23 17:46:39');
INSERT INTO `task_run_log` VALUES ('937', 'PageViewUpdateTask', '2018-08-23 19:00:00');
INSERT INTO `task_run_log` VALUES ('938', 'PageViewUpdateTask', '2018-08-23 19:00:00');
INSERT INTO `task_run_log` VALUES ('939', 'PageViewUpdateTask', '2018-08-23 20:00:00');
INSERT INTO `task_run_log` VALUES ('940', 'PageViewUpdateTask', '2018-08-23 20:00:00');
INSERT INTO `task_run_log` VALUES ('941', 'PageViewUpdateTask', '2018-08-23 21:00:00');
INSERT INTO `task_run_log` VALUES ('942', 'PageViewUpdateTask', '2018-08-23 21:00:00');
INSERT INTO `task_run_log` VALUES ('943', 'PageViewUpdateTask', '2018-08-23 22:00:00');
INSERT INTO `task_run_log` VALUES ('944', 'PageViewUpdateTask', '2018-08-23 22:00:00');
INSERT INTO `task_run_log` VALUES ('945', 'PageViewUpdateTask', '2018-08-23 23:00:00');
INSERT INTO `task_run_log` VALUES ('946', 'PageViewUpdateTask', '2018-08-23 23:00:00');
INSERT INTO `task_run_log` VALUES ('947', 'PageViewUpdateTask', '2018-08-24 00:00:00');
INSERT INTO `task_run_log` VALUES ('948', 'PageViewUpdateTask', '2018-08-24 00:00:00');
INSERT INTO `task_run_log` VALUES ('949', 'PageViewUpdateTask', '2018-08-24 01:00:00');
INSERT INTO `task_run_log` VALUES ('950', 'PageViewUpdateTask', '2018-08-24 01:00:00');
INSERT INTO `task_run_log` VALUES ('951', 'PageViewUpdateTask', '2018-08-24 02:00:00');
INSERT INTO `task_run_log` VALUES ('952', 'PageViewUpdateTask', '2018-08-24 02:00:00');
INSERT INTO `task_run_log` VALUES ('953', 'PageViewUpdateTask', '2018-08-24 03:00:00');
INSERT INTO `task_run_log` VALUES ('954', 'PageViewUpdateTask', '2018-08-24 03:00:00');
INSERT INTO `task_run_log` VALUES ('955', 'PageViewUpdateTask', '2018-08-24 04:00:00');
INSERT INTO `task_run_log` VALUES ('956', 'PageViewUpdateTask', '2018-08-24 05:00:00');
INSERT INTO `task_run_log` VALUES ('957', 'PageViewUpdateTask', '2018-08-24 06:00:00');
INSERT INTO `task_run_log` VALUES ('958', 'PageViewUpdateTask', '2018-08-24 07:00:00');
INSERT INTO `task_run_log` VALUES ('959', 'PageViewUpdateTask', '2018-08-24 08:00:00');
INSERT INTO `task_run_log` VALUES ('960', 'PageViewUpdateTask', '2018-08-24 09:00:00');
INSERT INTO `task_run_log` VALUES ('961', 'PageViewUpdateTask', '2018-08-24 10:00:00');
INSERT INTO `task_run_log` VALUES ('962', 'PageViewUpdateTask', '2018-08-24 11:00:00');
INSERT INTO `task_run_log` VALUES ('963', 'PageViewUpdateTask', '2018-08-24 12:00:00');
INSERT INTO `task_run_log` VALUES ('964', 'PageViewUpdateTask', '2018-08-24 13:00:00');
INSERT INTO `task_run_log` VALUES ('965', 'PageViewUpdateTask', '2018-08-24 13:00:00');
INSERT INTO `task_run_log` VALUES ('966', 'PageViewUpdateTask', '2018-08-24 14:00:00');
INSERT INTO `task_run_log` VALUES ('967', 'PageViewUpdateTask', '2018-08-24 14:00:00');
INSERT INTO `task_run_log` VALUES ('968', 'PageViewUpdateTask', '2018-08-24 15:00:00');
INSERT INTO `task_run_log` VALUES ('969', 'PageViewUpdateTask', '2018-08-24 15:00:00');
INSERT INTO `task_run_log` VALUES ('970', 'PageViewUpdateTask', '2018-08-24 16:00:00');
INSERT INTO `task_run_log` VALUES ('971', 'PageViewUpdateTask', '2018-08-24 16:00:00');
INSERT INTO `task_run_log` VALUES ('972', 'PageViewUpdateTask', '2018-08-24 16:59:39');
INSERT INTO `task_run_log` VALUES ('973', 'PageViewUpdateTask', '2018-08-24 17:58:18');
INSERT INTO `task_run_log` VALUES ('974', 'PageViewUpdateTask', '2018-08-24 18:00:00');
INSERT INTO `task_run_log` VALUES ('975', 'PageViewUpdateTask', '2018-08-24 18:00:00');
INSERT INTO `task_run_log` VALUES ('976', 'PageViewUpdateTask', '2018-08-24 18:00:00');
INSERT INTO `task_run_log` VALUES ('977', 'PageViewUpdateTask', '2018-08-24 18:00:42');
INSERT INTO `task_run_log` VALUES ('978', 'PageViewUpdateTask', '2018-08-24 18:05:08');
INSERT INTO `task_run_log` VALUES ('979', 'PageViewUpdateTask', '2018-08-24 18:06:54');
INSERT INTO `task_run_log` VALUES ('980', 'PageViewUpdateTask', '2018-08-24 18:08:33');
INSERT INTO `task_run_log` VALUES ('981', 'PageViewUpdateTask', '2018-08-24 18:19:16');
INSERT INTO `task_run_log` VALUES ('982', 'PageViewUpdateTask', '2018-08-24 18:22:07');
INSERT INTO `task_run_log` VALUES ('983', 'PageViewUpdateTask', '2018-08-24 18:54:34');
INSERT INTO `task_run_log` VALUES ('984', 'PageViewUpdateTask', '2018-08-24 19:00:00');
INSERT INTO `task_run_log` VALUES ('985', 'PageViewUpdateTask', '2018-08-24 19:00:00');
INSERT INTO `task_run_log` VALUES ('986', 'PageViewUpdateTask', '2018-08-24 20:00:00');
INSERT INTO `task_run_log` VALUES ('987', 'PageViewUpdateTask', '2018-08-24 20:00:00');
INSERT INTO `task_run_log` VALUES ('988', 'PageViewUpdateTask', '2018-08-24 20:00:00');
INSERT INTO `task_run_log` VALUES ('989', 'PageViewUpdateTask', '2018-08-24 21:00:00');
INSERT INTO `task_run_log` VALUES ('990', 'PageViewUpdateTask', '2018-08-24 21:00:00');
INSERT INTO `task_run_log` VALUES ('991', 'PageViewUpdateTask', '2018-08-24 21:00:00');
INSERT INTO `task_run_log` VALUES ('992', 'PageViewUpdateTask', '2018-08-24 22:00:00');
INSERT INTO `task_run_log` VALUES ('993', 'PageViewUpdateTask', '2018-08-24 22:00:00');
INSERT INTO `task_run_log` VALUES ('994', 'PageViewUpdateTask', '2018-08-24 22:00:00');
INSERT INTO `task_run_log` VALUES ('995', 'PageViewUpdateTask', '2018-08-24 23:00:00');
INSERT INTO `task_run_log` VALUES ('996', 'PageViewUpdateTask', '2018-08-25 00:00:00');
INSERT INTO `task_run_log` VALUES ('997', 'PageViewUpdateTask', '2018-08-25 01:00:00');
INSERT INTO `task_run_log` VALUES ('998', 'PageViewUpdateTask', '2018-08-25 02:00:00');
INSERT INTO `task_run_log` VALUES ('999', 'PageViewUpdateTask', '2018-08-25 03:00:00');
INSERT INTO `task_run_log` VALUES ('1000', 'PageViewUpdateTask', '2018-08-25 04:00:00');
INSERT INTO `task_run_log` VALUES ('1001', 'PageViewUpdateTask', '2018-08-25 05:00:00');
INSERT INTO `task_run_log` VALUES ('1002', 'PageViewUpdateTask', '2018-08-25 06:00:00');
INSERT INTO `task_run_log` VALUES ('1003', 'PageViewUpdateTask', '2018-08-25 06:19:26');
INSERT INTO `task_run_log` VALUES ('1004', 'PageViewUpdateTask', '2018-08-25 07:00:00');
INSERT INTO `task_run_log` VALUES ('1005', 'PageViewUpdateTask', '2018-08-25 07:00:00');
INSERT INTO `task_run_log` VALUES ('1006', 'PageViewUpdateTask', '2018-08-25 08:00:00');
INSERT INTO `task_run_log` VALUES ('1007', 'PageViewUpdateTask', '2018-08-25 08:00:00');
INSERT INTO `task_run_log` VALUES ('1008', 'PageViewUpdateTask', '2018-08-25 09:00:00');
INSERT INTO `task_run_log` VALUES ('1009', 'PageViewUpdateTask', '2018-08-25 09:00:00');
INSERT INTO `task_run_log` VALUES ('1010', 'PageViewUpdateTask', '2018-08-25 09:00:00');
INSERT INTO `task_run_log` VALUES ('1011', 'PageViewUpdateTask', '2018-08-25 10:00:00');
INSERT INTO `task_run_log` VALUES ('1012', 'PageViewUpdateTask', '2018-08-25 10:00:00');
INSERT INTO `task_run_log` VALUES ('1013', 'PageViewUpdateTask', '2018-08-25 10:00:00');
INSERT INTO `task_run_log` VALUES ('1014', 'PageViewUpdateTask', '2018-08-25 11:00:00');
INSERT INTO `task_run_log` VALUES ('1015', 'PageViewUpdateTask', '2018-08-25 11:00:00');
INSERT INTO `task_run_log` VALUES ('1016', 'PageViewUpdateTask', '2018-08-25 11:00:00');
INSERT INTO `task_run_log` VALUES ('1017', 'PageViewUpdateTask', '2018-08-25 12:00:00');
INSERT INTO `task_run_log` VALUES ('1018', 'PageViewUpdateTask', '2018-08-25 12:00:00');
INSERT INTO `task_run_log` VALUES ('1019', 'PageViewUpdateTask', '2018-08-25 12:00:00');
INSERT INTO `task_run_log` VALUES ('1020', 'PageViewUpdateTask', '2018-08-25 13:00:00');
INSERT INTO `task_run_log` VALUES ('1021', 'PageViewUpdateTask', '2018-08-25 13:00:00');
INSERT INTO `task_run_log` VALUES ('1022', 'PageViewUpdateTask', '2018-08-25 13:00:00');
INSERT INTO `task_run_log` VALUES ('1023', 'PageViewUpdateTask', '2018-08-25 14:00:00');
INSERT INTO `task_run_log` VALUES ('1024', 'PageViewUpdateTask', '2018-08-25 14:00:00');
INSERT INTO `task_run_log` VALUES ('1025', 'PageViewUpdateTask', '2018-08-25 14:00:00');
INSERT INTO `task_run_log` VALUES ('1026', 'PageViewUpdateTask', '2018-08-25 15:00:00');
INSERT INTO `task_run_log` VALUES ('1027', 'PageViewUpdateTask', '2018-08-25 15:00:00');
INSERT INTO `task_run_log` VALUES ('1028', 'PageViewUpdateTask', '2018-08-25 16:00:00');
INSERT INTO `task_run_log` VALUES ('1029', 'PageViewUpdateTask', '2018-08-25 16:00:00');
INSERT INTO `task_run_log` VALUES ('1030', 'PageViewUpdateTask', '2018-08-25 17:00:00');
INSERT INTO `task_run_log` VALUES ('1031', 'PageViewUpdateTask', '2018-08-25 17:00:00');
INSERT INTO `task_run_log` VALUES ('1032', 'PageViewUpdateTask', '2018-08-25 18:00:00');
INSERT INTO `task_run_log` VALUES ('1033', 'PageViewUpdateTask', '2018-08-25 18:00:00');
INSERT INTO `task_run_log` VALUES ('1034', 'PageViewUpdateTask', '2018-08-25 19:00:00');
INSERT INTO `task_run_log` VALUES ('1035', 'PageViewUpdateTask', '2018-08-25 19:00:00');
INSERT INTO `task_run_log` VALUES ('1036', 'PageViewUpdateTask', '2018-08-25 20:00:00');
INSERT INTO `task_run_log` VALUES ('1037', 'PageViewUpdateTask', '2018-08-25 20:00:00');
INSERT INTO `task_run_log` VALUES ('1038', 'PageViewUpdateTask', '2018-08-25 21:00:00');
INSERT INTO `task_run_log` VALUES ('1039', 'PageViewUpdateTask', '2018-08-25 21:00:00');
INSERT INTO `task_run_log` VALUES ('1040', 'PageViewUpdateTask', '2018-08-25 22:00:00');
INSERT INTO `task_run_log` VALUES ('1041', 'PageViewUpdateTask', '2018-08-25 22:00:00');
INSERT INTO `task_run_log` VALUES ('1042', 'PageViewUpdateTask', '2018-08-25 23:00:00');
INSERT INTO `task_run_log` VALUES ('1043', 'PageViewUpdateTask', '2018-08-25 23:00:00');
INSERT INTO `task_run_log` VALUES ('1044', 'PageViewUpdateTask', '2018-08-26 00:00:00');
INSERT INTO `task_run_log` VALUES ('1045', 'PageViewUpdateTask', '2018-08-26 01:00:00');
INSERT INTO `task_run_log` VALUES ('1046', 'PageViewUpdateTask', '2018-08-26 02:00:00');
INSERT INTO `task_run_log` VALUES ('1047', 'PageViewUpdateTask', '2018-08-26 03:00:00');
INSERT INTO `task_run_log` VALUES ('1048', 'PageViewUpdateTask', '2018-08-26 04:00:00');
INSERT INTO `task_run_log` VALUES ('1049', 'PageViewUpdateTask', '2018-08-26 05:00:00');
INSERT INTO `task_run_log` VALUES ('1050', 'PageViewUpdateTask', '2018-08-26 06:00:00');
INSERT INTO `task_run_log` VALUES ('1051', 'PageViewUpdateTask', '2018-08-26 07:00:00');
INSERT INTO `task_run_log` VALUES ('1052', 'PageViewUpdateTask', '2018-08-26 08:00:00');
INSERT INTO `task_run_log` VALUES ('1053', 'PageViewUpdateTask', '2018-08-26 09:00:00');
INSERT INTO `task_run_log` VALUES ('1054', 'PageViewUpdateTask', '2018-08-26 10:00:00');
INSERT INTO `task_run_log` VALUES ('1055', 'PageViewUpdateTask', '2018-08-26 10:00:00');
INSERT INTO `task_run_log` VALUES ('1056', 'PageViewUpdateTask', '2018-08-26 11:00:00');
INSERT INTO `task_run_log` VALUES ('1057', 'PageViewUpdateTask', '2018-08-26 11:00:00');
INSERT INTO `task_run_log` VALUES ('1058', 'PageViewUpdateTask', '2018-08-26 12:00:00');
INSERT INTO `task_run_log` VALUES ('1059', 'PageViewUpdateTask', '2018-08-26 12:00:00');
INSERT INTO `task_run_log` VALUES ('1060', 'PageViewUpdateTask', '2018-08-26 13:00:00');
INSERT INTO `task_run_log` VALUES ('1061', 'PageViewUpdateTask', '2018-08-26 14:00:00');
INSERT INTO `task_run_log` VALUES ('1062', 'PageViewUpdateTask', '2018-08-26 15:00:00');
INSERT INTO `task_run_log` VALUES ('1063', 'PageViewUpdateTask', '2018-08-26 16:00:00');
INSERT INTO `task_run_log` VALUES ('1064', 'PageViewUpdateTask', '2018-08-26 17:00:00');
INSERT INTO `task_run_log` VALUES ('1065', 'PageViewUpdateTask', '2018-08-26 18:00:00');
INSERT INTO `task_run_log` VALUES ('1066', 'PageViewUpdateTask', '2018-08-26 19:00:00');
INSERT INTO `task_run_log` VALUES ('1067', 'PageViewUpdateTask', '2018-08-26 20:00:00');
INSERT INTO `task_run_log` VALUES ('1068', 'PageViewUpdateTask', '2018-08-26 21:00:00');
INSERT INTO `task_run_log` VALUES ('1069', 'PageViewUpdateTask', '2018-08-26 22:00:00');
INSERT INTO `task_run_log` VALUES ('1070', 'PageViewUpdateTask', '2018-08-26 23:00:00');
INSERT INTO `task_run_log` VALUES ('1071', 'PageViewUpdateTask', '2018-08-27 00:00:00');
INSERT INTO `task_run_log` VALUES ('1072', 'PageViewUpdateTask', '2018-08-27 01:00:00');
INSERT INTO `task_run_log` VALUES ('1073', 'PageViewUpdateTask', '2018-08-27 02:00:00');
INSERT INTO `task_run_log` VALUES ('1074', 'PageViewUpdateTask', '2018-08-27 03:00:00');
INSERT INTO `task_run_log` VALUES ('1075', 'PageViewUpdateTask', '2018-08-27 04:00:00');
INSERT INTO `task_run_log` VALUES ('1076', 'PageViewUpdateTask', '2018-08-27 05:00:00');
INSERT INTO `task_run_log` VALUES ('1077', 'PageViewUpdateTask', '2018-08-27 06:00:00');
INSERT INTO `task_run_log` VALUES ('1078', 'PageViewUpdateTask', '2018-08-27 07:00:00');
INSERT INTO `task_run_log` VALUES ('1079', 'PageViewUpdateTask', '2018-08-27 08:00:00');
INSERT INTO `task_run_log` VALUES ('1080', 'PageViewUpdateTask', '2018-08-27 09:00:00');
INSERT INTO `task_run_log` VALUES ('1081', 'PageViewUpdateTask', '2018-08-27 10:00:00');
INSERT INTO `task_run_log` VALUES ('1082', 'PageViewUpdateTask', '2018-08-27 11:00:00');
INSERT INTO `task_run_log` VALUES ('1083', 'PageViewUpdateTask', '2018-08-27 11:35:31');

-- ----------------------------
-- Table structure for `upload_counter`
-- ----------------------------
DROP TABLE IF EXISTS `upload_counter`;
CREATE TABLE `upload_counter` (
  `uploadType` varchar(50) NOT NULL,
  `counter` int(11) NOT NULL,
  `descr` varchar(50) NOT NULL,
  PRIMARY KEY (`uploadType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of upload_counter
-- ----------------------------
INSERT INTO `upload_counter` VALUES ('club', '0', '记录club模块上传图片的总数量，用于生成相对路径');
INSERT INTO `upload_counter` VALUES ('document', '0', '记录document模块上传图片的总数量，用于生成相对路径');
INSERT INTO `upload_counter` VALUES ('feedback', '312', '记录feedback模块上传图片的总数量，用于生成相对路径');
INSERT INTO `upload_counter` VALUES ('project', '72', '记录project模块上传图片的总数量，用于生成相对路径');
INSERT INTO `upload_counter` VALUES ('share', '196', '记录share模块上传图片的总数量，用于生成相对路径');
