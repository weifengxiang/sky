-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: platform
-- ------------------------------------------------------
-- Server version	5.6.10

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_dict`
--

DROP TABLE IF EXISTS `sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dict` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `CODE` varchar(20) DEFAULT NULL COMMENT '字典编号',
  `NAME` varchar(50) DEFAULT NULL COMMENT '字典名称',
  `NOTE` varchar(100) DEFAULT NULL COMMENT '备注',
  `CREATER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYS_DICT_UIndex_CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict`
--

LOCK TABLES `sys_dict` WRITE;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_item`
--

DROP TABLE IF EXISTS `sys_dict_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dict_item` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `DICT_CODE` varchar(20) DEFAULT NULL COMMENT '字典类别',
  `CODE` varchar(20) DEFAULT NULL COMMENT '字典项编号',
  `NAME` varchar(50) DEFAULT NULL COMMENT '字典项名称',
  `IS_USE` char(1) DEFAULT '1' COMMENT '有效标志',
  `SEQ` smallint(6) DEFAULT NULL COMMENT '序号',
  `NOTE` varchar(100) DEFAULT NULL COMMENT '备注',
  `CREATER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYS_DICT_ITEM_UIndex` (`DICT_CODE`,`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典项表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_item`
--

LOCK TABLES `sys_dict_item` WRITE;
/*!40000 ALTER TABLE `sys_dict_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dict_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_file`
--

DROP TABLE IF EXISTS `sys_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_file` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `TYPE` varchar(100) DEFAULT NULL COMMENT '附件类型',
  `NAME` varchar(500) DEFAULT NULL COMMENT '附件名称',
  `SIZE` int(11) DEFAULT NULL COMMENT '附件大小',
  `PATH` varchar(500) DEFAULT NULL COMMENT '附件保存路径',
  `SUFFIX` varchar(100) DEFAULT NULL COMMENT '附件后缀',
  `BIZ_CODE` varchar(50) DEFAULT NULL COMMENT '业务编号',
  `BIZ_TYPE` varchar(50) DEFAULT NULL COMMENT '业务类型',
  `NOTE` varchar(100) DEFAULT NULL COMMENT '备注',
  `CREATER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_file`
--

LOCK TABLES `sys_file` WRITE;
/*!40000 ALTER TABLE `sys_file` DISABLE KEYS */;
INSERT INTO `sys_file` VALUES ('ff2539e2f2b88c44ed284e7d49a51b3f','image/png','logo.png',1801,'D://ATTACHMENT_DIR//Test\\6666logo.png','png','6666','Test',NULL,'SUPERADMIN','2017-02-16 13:17:03','SUPERADMIN','2017-02-16 13:17:03');
/*!40000 ALTER TABLE `sys_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_log` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `USER_CODE` varchar(20) DEFAULT NULL COMMENT '操作用户',
  `OPT_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `IP` varchar(100) DEFAULT NULL COMMENT 'IP地址',
  `URL` varchar(500) DEFAULT NULL COMMENT '请求URL',
  `OPT_NAME` varchar(500) DEFAULT NULL COMMENT '执行操作',
  `OPT_METHOD` varchar(500) DEFAULT NULL COMMENT '执行方法',
  `OPT_PARAM` varchar(500) DEFAULT NULL COMMENT '执行参数',
  `LAST_TIME` smallint(6) DEFAULT NULL COMMENT '消耗时间',
  `OPT_RESULT` varchar(500) DEFAULT NULL COMMENT '执行结果',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `CODE` varchar(40) DEFAULT NULL COMMENT '菜单编号',
  `NAME` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `PARENT_CODE` varchar(40) DEFAULT NULL COMMENT '父级编号',
  `SEQ` smallint(6) DEFAULT NULL COMMENT '序号',
  `IS_LEAF` char(1) DEFAULT '0' COMMENT '是否叶子菜单',
  `ICON` varchar(100) DEFAULT NULL COMMENT '图标',
  `NOTE` varchar(100) DEFAULT NULL COMMENT '备注',
  `CREATER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYS_MENU_UIndex_CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES ('002e086e071106a5a176cc5e6e0b3528','0001','测试','root',1,'0','icon-map','','SUPERADMIN','2017-02-04 13:03:02','SUPERADMIN','2017-02-04 13:03:02'),('3005b432eeedc9e092bd3930d82c81ff','99990009','附件管理','9999',9,'1','icon-folder','','SUPERADMIN','2017-02-03 14:43:02','SUPERADMIN','2017-02-03 14:43:02'),('38e5e08fc8ec18bd37553995588f78e4','999900060001','在线用户管理','99990006',1,'1','icon-status_online','','SUPERADMIN','2017-02-03 14:32:03','SUPERADMIN','2017-02-03 14:32:03'),('48206a6e2eadd87b708187ee2a70238b','99990004','用户管理','9999',4,'1','icon-people','','SUPERADMIN','2017-02-03 13:53:29','SUPERADMIN','2017-02-03 13:53:29'),('4ad992dd0dbe617421ff6919e3c3cedf','99990006','安全策略','9999',6,'0','icon-shield_rainbow','','SUPERADMIN','2017-02-03 14:25:59','SUPERADMIN','2017-02-03 14:25:59'),('68c491da8d1fecb408491514422837e4','99990003','角色管理','9999',3,'1','icon-20130406014211289_easyicon_net_16','','SUPERADMIN','2017-02-03 13:50:49','SUPERADMIN','2017-02-03 13:50:49'),('82685fa3a1cce4bb071fb946552be180','99990008','定时任务管理','9999',8,'1','icon-clock','','SUPERADMIN','2017-02-03 14:41:35','SUPERADMIN','2017-02-03 14:41:35'),('9999','9999','系统管理','root',9999,'0','icon-2012080404391',NULL,'SUPERADMIN','2017-02-02 07:17:09','SUPERADMIN','2017-01-31 14:54:06'),('99990001','99990001','功能管理','9999',1,'1','icon-2012080111634',NULL,'SUPERADMIN','2017-02-02 07:17:09','SUPERADMIN','0000-00-00 00:00:00'),('9e62ed46ffb944463c6572347cd1c9a5','99990002','组织机构管理','9999',2,'0','icon-chart_organisation','','SUPERADMIN','2017-02-03 13:47:55','SUPERADMIN','2017-02-03 13:47:55'),('adb7d49c76ab8b2dee4243aa2f2f2369','99990005','字典管理','9999',5,'1','icon-book','','SUPERADMIN','2017-02-03 14:24:22','SUPERADMIN','2017-02-03 14:24:22'),('d79754c6a3718f765f5356c4dde790d3','99990007','微件管理','9999',7,'1','icon-plugin','','SUPERADMIN','2017-02-16 13:07:08','SUPERADMIN','2017-02-16 13:07:08'),('e3b905d51f72837f01a47b90236a1570','999900020002','组织机构管理','99990002',2,'1','icon-chart_organisation_add','','SUPERADMIN','2017-02-03 13:48:50','SUPERADMIN','2017-02-03 13:48:50'),('f630c3a4b0b6ce1720d994cb712e0e16','999900020001','机构类型管理','99990002',1,'1','icon-cmy','','SUPERADMIN','2017-02-03 13:48:11','SUPERADMIN','2017-02-03 13:48:11');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_operation`
--

DROP TABLE IF EXISTS `sys_operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_operation` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `CODE` varchar(20) DEFAULT NULL COMMENT '操作编号',
  `NAME` varchar(100) DEFAULT NULL COMMENT '操作名称',
  `URL` varchar(100) DEFAULT NULL COMMENT '操作地址',
  `IS_DEFAULT` char(1) DEFAULT '0' COMMENT '是否默认',
  `MENU_CODE` varchar(40) DEFAULT NULL COMMENT '菜单编号',
  `SEQ` smallint(6) DEFAULT NULL COMMENT '序号',
  `NOTE` varchar(100) DEFAULT NULL COMMENT '备注',
  `CREATER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYS_OPERATION_UIndex_CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_operation`
--

LOCK TABLES `sys_operation` WRITE;
/*!40000 ALTER TABLE `sys_operation` DISABLE KEYS */;
INSERT INTO `sys_operation` VALUES ('23066c9b0d1f2ef53e63a490550826db','999900040001','打开页面','sys/SysUser/initSysUserManagePage','1','99990004',1,'','SUPERADMIN','2017-02-13 12:25:56','SUPERADMIN','2017-02-13 12:25:56'),('3fed3c2f4d04afe82842b0eb6dbd5ffe','999900030001','打开页面','sys/SysRole/initSysRoleManagePage','1','99990003',1,'','SUPERADMIN','2017-02-12 05:20:27','SUPERADMIN','2017-02-12 05:20:27'),('49f105c2251f79cf5cdd5c204e9c82b7','999900050001','打开页面','sys/SysDict/initSysDictListPage','1','99990005',1,'','SUPERADMIN','2017-02-16 13:09:19','SUPERADMIN','2017-02-16 13:09:19'),('7b65f483edbaa5e20408608771e605f0','9999000200020001','打开页面','sys/SysOrgan/initSysOrganManagePage','1','999900020002',1,'','SUPERADMIN','2017-02-05 09:30:24','SUPERADMIN','2017-02-05 09:30:24'),('99990001','99990001','功能管理','sys/function/initSysFunctionManangePage','1','99990001',1,'icon-1012333','SUPERADMIN','2017-02-05 09:26:04','SUPERADMIN','0000-00-00 00:00:00'),('9a84e613f12730302f8a5335a6bb4b9d','999900070001','打开页面','sys/SysWidget/initSysWidgetListPage','1','99990007',1,'','SUPERADMIN','2017-02-16 13:07:43','SUPERADMIN','2017-02-16 13:07:43'),('aaa42d416712822bf1e6222e1b7eb13e','9999000200010001','打开列表页面','sys/SysOrganType/initSysOrganTypeListPage','1','999900020001',1,'','SUPERADMIN','2017-02-04 14:24:11','SUPERADMIN','2017-02-04 14:24:11'),('cbe1b25dd60115a414f596cbe3344e8f','9999000600010001','打开页面','sys/onlineuser/initOnlineUserListPage','1','999900060001',1,'','SUPERADMIN','2017-02-16 13:15:33','SUPERADMIN','2017-02-16 13:15:33'),('f6b024861a3e0fca9426b0d26197fa0b','999900090001','打开页面','sys/SysFile/initSysFileListPage','1','99990009',1,'','SUPERADMIN','2017-02-16 13:06:24','SUPERADMIN','2017-02-16 13:06:24');
/*!40000 ALTER TABLE `sys_operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_organ`
--

DROP TABLE IF EXISTS `sys_organ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_organ` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `CODE` varchar(40) DEFAULT NULL COMMENT '机构编号',
  `NAME` varchar(100) DEFAULT NULL COMMENT '机构名称',
  `PAR_CODE` varchar(40) DEFAULT NULL COMMENT '父级编号',
  `TYPE` varchar(10) DEFAULT NULL COMMENT '机构类型',
  `SEQ` smallint(6) DEFAULT NULL COMMENT '序号',
  `NOTE` varchar(100) DEFAULT NULL COMMENT '备注',
  `CREATER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYS_ORGAN_UIndex_CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_organ`
--

LOCK TABLES `sys_organ` WRITE;
/*!40000 ALTER TABLE `sys_organ` DISABLE KEYS */;
INSERT INTO `sys_organ` VALUES ('837e50a7a702877153c6d5c77c0e88b4','0001','总局','root','1',1,'1','SUPERADMIN','2017-02-05 12:57:03','SUPERADMIN','2017-02-05 12:57:03'),('dd0de890d2268c5c044a18814c498310','00010002','山东局','0001','2',2,'2','SUPERADMIN','2017-02-05 13:20:04','SUPERADMIN','2017-02-05 13:20:04');
/*!40000 ALTER TABLE `sys_organ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_organ_type`
--

DROP TABLE IF EXISTS `sys_organ_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_organ_type` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `CODE` varchar(10) DEFAULT NULL COMMENT '类型编号',
  `NAME` varchar(100) DEFAULT NULL COMMENT '类型名称',
  `SEQ` smallint(6) DEFAULT NULL COMMENT '序号',
  `ICON` varchar(100) DEFAULT NULL COMMENT '图标',
  `NOTE` varchar(100) DEFAULT NULL COMMENT '备注',
  `CREATER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYS_ORGAN_TYPE_UIndex_CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_organ_type`
--

LOCK TABLES `sys_organ_type` WRITE;
/*!40000 ALTER TABLE `sys_organ_type` DISABLE KEYS */;
INSERT INTO `sys_organ_type` VALUES ('91d5e9a43dc31797c7a98a29197c9fc4','1','单位',1,'icon-c','','SUPERADMIN','2017-02-05 07:14:45','SUPERADMIN','2017-02-05 07:14:45'),('f6234227b39e2e64a0369c9a0904d9d0','2','部门',2,'icon-d','','SUPERADMIN','2017-02-05 07:15:07','SUPERADMIN','2017-02-05 07:15:07');
/*!40000 ALTER TABLE `sys_organ_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `CODE` varchar(20) DEFAULT NULL COMMENT '角色编号',
  `NAME` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `ORGAN_CODE` varchar(40) DEFAULT NULL COMMENT '所属组织机构',
  `NOTE` varchar(100) DEFAULT NULL COMMENT '备注',
  `CREATER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYS_ROLE_UIndex_CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES ('0a5cafbdbe5b93f337ad0bef383af8c7','333','程序猿','0001','','SUPERADMIN','2017-02-14 14:21:56','SUPERADMIN','2017-02-14 14:21:56'),('6d8a0efd6632b8c36048d07aeece1500','12','高管','0001','222','SUPERADMIN','2017-02-14 14:21:45','SUPERADMIN','2017-02-14 14:21:45');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_operation`
--

DROP TABLE IF EXISTS `sys_role_operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_operation` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `ROLE_CODE` varchar(20) DEFAULT NULL COMMENT '角色编号',
  `OPE_CODE` varchar(20) DEFAULT NULL COMMENT '操作编号',
  `NOTE` varchar(100) DEFAULT NULL COMMENT '备注',
  `CREATER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYS_ROLE_OPERATION_UIndex` (`ROLE_CODE`,`OPE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色操作表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_operation`
--

LOCK TABLES `sys_role_operation` WRITE;
/*!40000 ALTER TABLE `sys_role_operation` DISABLE KEYS */;
INSERT INTO `sys_role_operation` VALUES ('9df7dd9521f5ea84cc609077ecc34777','12','999900030001',NULL,'SUPERADMIN','2017-02-12 14:11:07','SUPERADMIN','2017-02-12 14:11:07'),('a4b20c1c28a48ffcf708d502afb7c6b6','12','9999000200020001',NULL,'SUPERADMIN','2017-02-12 14:11:07','SUPERADMIN','2017-02-12 14:11:07'),('ed69c9b7e20a56a0c37d24596f53f6d0','12','9999000200010001',NULL,'SUPERADMIN','2017-02-12 14:11:07','SUPERADMIN','2017-02-12 14:11:07');
/*!40000 ALTER TABLE `sys_role_operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_widget`
--

DROP TABLE IF EXISTS `sys_role_widget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_widget` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `ROLE_CODE` varchar(40) DEFAULT NULL COMMENT '角色编号',
  `WIDGET_ID` varchar(32) DEFAULT NULL COMMENT '微件ID',
  `NOTE` varchar(500) DEFAULT NULL COMMENT '备注',
  `SEQ` int(11) DEFAULT NULL COMMENT '序号',
  `CREATER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色微件';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_widget`
--

LOCK TABLES `sys_role_widget` WRITE;
/*!40000 ALTER TABLE `sys_role_widget` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_widget` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `CODE` varchar(20) DEFAULT NULL COMMENT '用户编号',
  `NAME` varchar(100) DEFAULT NULL COMMENT '用户名称',
  `PASSWORD` varchar(100) DEFAULT NULL COMMENT '密码',
  `STATUS` char(1) DEFAULT NULL COMMENT '账户状态',
  `IS_SYS` char(1) DEFAULT '0' COMMENT '是否系统用户',
  `SEX` char(1) DEFAULT NULL COMMENT '性别',
  `LOCK_TIME` timestamp NULL DEFAULT NULL COMMENT '锁定时间',
  `EXPIRED_TIME` timestamp NULL DEFAULT NULL COMMENT '逾期时间',
  `ORGAN_CODE` varchar(40) DEFAULT NULL COMMENT '所属组织机构',
  `MOBELTEL` varchar(20) DEFAULT NULL COMMENT '移动电话',
  `WORKTEL` varchar(20) DEFAULT NULL COMMENT '办公电话',
  `EMAIL` varchar(40) DEFAULT NULL COMMENT '邮箱',
  `NOTE` varchar(100) DEFAULT NULL COMMENT '备注',
  `CREATER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYS_USER_UIndex_CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES ('0A166913D9F44187BDB55FFE3C0FCEAB','SUPERADMIN','超级管理员','96e79218965eb72c92a549dd5a330112','0','1','1','2017-01-28 15:10:10','0000-00-00 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00',NULL,'0000-00-00 00:00:00'),('0e41b7a087d47bbdbb080bad058aad15','DD','DD',NULL,'O','0','M',NULL,NULL,'0001','','','','','SUPERADMIN','2017-02-13 13:23:07','SUPERADMIN','2017-02-13 13:23:07'),('98578dc76641342275e574cbeb78fecd','C','C',NULL,'L','1','M',NULL,NULL,'0001','','','C@123','','SUPERADMIN','2017-02-13 12:47:12','SUPERADMIN','2017-02-13 13:22:43');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_data_access`
--

DROP TABLE IF EXISTS `sys_user_data_access`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_data_access` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `USER_CODE` varchar(20) DEFAULT NULL COMMENT '用户编号',
  `ORGAN_CODE` varchar(40) DEFAULT NULL COMMENT '组织机构编号',
  `NOTE` varchar(100) DEFAULT NULL COMMENT '备注',
  `CREATER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYS_USER_DATA_ACCESS_UIndex` (`USER_CODE`,`ORGAN_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户数据权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_data_access`
--

LOCK TABLES `sys_user_data_access` WRITE;
/*!40000 ALTER TABLE `sys_user_data_access` DISABLE KEYS */;
INSERT INTO `sys_user_data_access` VALUES ('5918003faac05a3e3ea6c236c3234c65','DD','0001','1','SUPERADMIN','2017-02-14 15:14:36','SUPERADMIN','2017-02-14 15:17:05'),('f7154aa347de0b4189d5bb323573a4da','DD','00010002','2','SUPERADMIN','2017-02-14 15:14:43','SUPERADMIN','2017-02-14 15:17:05');
/*!40000 ALTER TABLE `sys_user_data_access` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `USER_CODE` varchar(20) DEFAULT NULL COMMENT '用户编号',
  `ROLE_CODE` varchar(20) DEFAULT NULL COMMENT '角色编号',
  `NOTE` varchar(100) DEFAULT NULL COMMENT '备注',
  `CREATER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYS_USER_ROLE_UIndex` (`USER_CODE`,`ROLE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_widget`
--

DROP TABLE IF EXISTS `sys_widget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_widget` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `NAME` varchar(50) DEFAULT NULL COMMENT '名称',
  `SRC` varchar(500) DEFAULT NULL COMMENT '地址',
  `IS_SYS` char(1) DEFAULT NULL COMMENT '是否系统',
  `OPTS` varchar(500) DEFAULT NULL COMMENT '属性',
  `NOTE` varchar(500) DEFAULT NULL COMMENT '备注',
  `SEQ` int(11) DEFAULT NULL COMMENT '序号',
  `CREATER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微件';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_widget`
--

LOCK TABLES `sys_widget` WRITE;
/*!40000 ALTER TABLE `sys_widget` DISABLE KEYS */;
INSERT INTO `sys_widget` VALUES ('62cb68aecc341e23411a6848086230fe','地图','http://map.baidu.com/','1','','',1,'SUPERADMIN','2017-02-16 13:16:30','SUPERADMIN','2017-02-16 13:16:30');
/*!40000 ALTER TABLE `sys_widget` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-02-16 21:24:29
