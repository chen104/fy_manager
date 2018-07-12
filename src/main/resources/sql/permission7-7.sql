/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50637
Source Host           : localhost:3306
Source Database       : jfinal

Target Server Type    : MYSQL
Target Server Version : 50637
File Encoding         : 65001

Date: 2018-07-07 17:11:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `actionKey` varchar(512) NOT NULL DEFAULT '',
  `controller` varchar(512) NOT NULL DEFAULT '',
  `remark` varchar(1024) DEFAULT NULL,
  `parentId` int(11) DEFAULT '0',
  `isfy` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '/admin', 'com.jfinal.club._admin.index.IndexAdminController', '后台管理首页', '0', '');
INSERT INTO `permission` VALUES ('2', '/admin/account', 'com.jfinal.club._admin.account.AccountAdminController', '账户管理首页', '0', '');
INSERT INTO `permission` VALUES ('3', '/admin/account/addRole', 'com.jfinal.club._admin.account.AccountAdminController', '添加角色', '0', '');
INSERT INTO `permission` VALUES ('4', '/admin/account/assignRoles', 'com.jfinal.club._admin.account.AccountAdminController', '分配角色', '0', '');
INSERT INTO `permission` VALUES ('5', '/admin/account/deleteRole', 'com.jfinal.club._admin.account.AccountAdminController', '删除角色', '0', '');
INSERT INTO `permission` VALUES ('6', '/admin/account/edit', 'com.jfinal.club._admin.account.AccountAdminController', '修改账户', '0', '');
INSERT INTO `permission` VALUES ('7', '/admin/account/lock', 'com.jfinal.club._admin.account.AccountAdminController', '锁定账户', '0', '');
INSERT INTO `permission` VALUES ('8', '/admin/account/unlock', 'com.jfinal.club._admin.account.AccountAdminController', '解锁账户', '0', '');
INSERT INTO `permission` VALUES ('9', '/admin/account/update', 'com.jfinal.club._admin.account.AccountAdminController', '更新账户', '0', '');
INSERT INTO `permission` VALUES ('10', '/admin/feedback', 'com.jfinal.club._admin.feedback.FeedbackAdminController', '反馈管理首页', '0', '');
INSERT INTO `permission` VALUES ('11', '/admin/feedback/delete', 'com.jfinal.club._admin.feedback.FeedbackAdminController', '删除反馈', '0', '');
INSERT INTO `permission` VALUES ('12', '/admin/feedback/deleteReply', 'com.jfinal.club._admin.feedback.FeedbackAdminController', '删除反馈回复', '0', '');
INSERT INTO `permission` VALUES ('13', '/admin/feedback/edit', 'com.jfinal.club._admin.feedback.FeedbackAdminController', '修改反馈', '0', '');
INSERT INTO `permission` VALUES ('14', '/admin/feedback/lock', 'com.jfinal.club._admin.feedback.FeedbackAdminController', '锁定反馈', '0', '');
INSERT INTO `permission` VALUES ('15', '/admin/feedback/replyList', 'com.jfinal.club._admin.feedback.FeedbackAdminController', '反馈回复列表', '0', '');
INSERT INTO `permission` VALUES ('16', '/admin/feedback/unlock', 'com.jfinal.club._admin.feedback.FeedbackAdminController', '解锁反馈', '0', '');
INSERT INTO `permission` VALUES ('17', '/admin/feedback/update', 'com.jfinal.club._admin.feedback.FeedbackAdminController', '更新反馈', '0', '');
INSERT INTO `permission` VALUES ('18', '/admin/permission', 'com.jfinal.club._admin.permission.PermissionAdminController', '权限管理首页', '0', '');
INSERT INTO `permission` VALUES ('19', '/admin/permission/delete', 'com.jfinal.club._admin.permission.PermissionAdminController', '删除权限', '0', '');
INSERT INTO `permission` VALUES ('20', '/admin/permission/edit', 'com.jfinal.club._admin.permission.PermissionAdminController', '修改权限', '0', '');
INSERT INTO `permission` VALUES ('21', '/admin/permission/sync', 'com.jfinal.club._admin.permission.PermissionAdminController', '同步权限', '0', '');
INSERT INTO `permission` VALUES ('22', '/admin/permission/update', 'com.jfinal.club._admin.permission.PermissionAdminController', '更新权限', '0', '');
INSERT INTO `permission` VALUES ('23', '/admin/project', 'com.jfinal.club._admin.project.ProjectAdminController', '项目管理首页', '0', '');
INSERT INTO `permission` VALUES ('24', '/admin/project/delete', 'com.jfinal.club._admin.project.ProjectAdminController', '删除项目', '0', '');
INSERT INTO `permission` VALUES ('25', '/admin/project/edit', 'com.jfinal.club._admin.project.ProjectAdminController', '修改项目', '0', '');
INSERT INTO `permission` VALUES ('26', '/admin/project/lock', 'com.jfinal.club._admin.project.ProjectAdminController', '锁定项目', '0', '');
INSERT INTO `permission` VALUES ('27', '/admin/project/unlock', 'com.jfinal.club._admin.project.ProjectAdminController', '解锁项目', '0', '');
INSERT INTO `permission` VALUES ('28', '/admin/project/update', 'com.jfinal.club._admin.project.ProjectAdminController', '更新项目', '0', '');
INSERT INTO `permission` VALUES ('29', '/admin/role', 'com.jfinal.club._admin.role.RoleAdminController', '角色管理首页', '0', '');
INSERT INTO `permission` VALUES ('30', '/admin/role/add', 'com.jfinal.club._admin.role.RoleAdminController', '添加角色', '0', '');
INSERT INTO `permission` VALUES ('31', '/admin/role/addPermission', 'com.jfinal.club._admin.role.RoleAdminController', '添加权限', '0', '');
INSERT INTO `permission` VALUES ('32', '/admin/role/assignPermissions', 'com.jfinal.club._admin.role.RoleAdminController', '分配权限', '0', '');
INSERT INTO `permission` VALUES ('33', '/admin/role/delete', 'com.jfinal.club._admin.role.RoleAdminController', '删除角色', '0', '');
INSERT INTO `permission` VALUES ('34', '/admin/role/deletePermission', 'com.jfinal.club._admin.role.RoleAdminController', '删除权限', '0', '');
INSERT INTO `permission` VALUES ('35', '/admin/role/edit', 'com.jfinal.club._admin.role.RoleAdminController', '修改角色', '0', '');
INSERT INTO `permission` VALUES ('36', '/admin/role/save', 'com.jfinal.club._admin.role.RoleAdminController', '保存角色', '0', '');
INSERT INTO `permission` VALUES ('37', '/admin/role/update', 'com.jfinal.club._admin.role.RoleAdminController', '更新角色', '0', '');
INSERT INTO `permission` VALUES ('38', '/admin/share', 'com.jfinal.club._admin.share.ShareAdminController', '分享管理首页', '0', '');
INSERT INTO `permission` VALUES ('39', '/admin/share/delete', 'com.jfinal.club._admin.share.ShareAdminController', '删除分享', '0', '');
INSERT INTO `permission` VALUES ('40', '/admin/share/deleteReply', 'com.jfinal.club._admin.share.ShareAdminController', '删除分享回复', '0', '');
INSERT INTO `permission` VALUES ('41', '/admin/share/edit', 'com.jfinal.club._admin.share.ShareAdminController', '修改分享', '0', '');
INSERT INTO `permission` VALUES ('42', '/admin/share/lock', 'com.jfinal.club._admin.share.ShareAdminController', '锁定分享', '0', '');
INSERT INTO `permission` VALUES ('43', '/admin/share/replyList', 'com.jfinal.club._admin.share.ShareAdminController', '分享回复列表', '0', '');
INSERT INTO `permission` VALUES ('44', '/admin/share/unlock', 'com.jfinal.club._admin.share.ShareAdminController', '解锁分享', '0', '');
INSERT INTO `permission` VALUES ('45', '/admin/share/update', 'com.jfinal.club._admin.share.ShareAdminController', '更新分享', '0', '');
INSERT INTO `permission` VALUES ('46', '/fy/admin/account', 'com.chen.fy.controller.AccountController', '用户首页', '0', '');
INSERT INTO `permission` VALUES ('47', '/fy/admin/account/update', 'com.chen.fy.controller.AccountController', '修改账户', '0', '');
INSERT INTO `permission` VALUES ('48', '/fy/admin/account/delete', 'com.chen.fy.controller.AccountController', '删除账号', '0', '');
INSERT INTO `permission` VALUES ('49', '/fy/admin/account/save', 'com.chen.fy.controller.AccountController', '添加用户', '0', '');
INSERT INTO `permission` VALUES ('50', '/fy/admin/role', 'com.chen.fy.controller.role.RoleAdminController', '角色首页', '0', '');
INSERT INTO `permission` VALUES ('51', '/fy/admin/role/update', 'com.chen.fy.controller.role.RoleAdminController', '修改角色', '0', '');
INSERT INTO `permission` VALUES ('52', '/fy/admin/role/add', 'com.chen.fy.controller.role.RoleAdminController', '添加角色', '0', '');
INSERT INTO `permission` VALUES ('53', '/fy/admin/role/delete', 'com.chen.fy.controller.role.RoleAdminController', '删除角色', '0', '');
INSERT INTO `permission` VALUES ('54', '/fy/admin/account/assignRoles', 'com.chen.fy.controller.AccountController', '分配角色', '0', '');
INSERT INTO `permission` VALUES ('55', '/fy/admin/role/assignPermissions', 'com.chen.fy.controller.role.RoleAdminController', '角色授权', '0', '');
