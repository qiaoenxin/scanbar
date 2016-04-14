/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.6.23 : Database - pro
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pro` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `pro`;

/*Table structure for table `pro_device` */

DROP TABLE IF EXISTS `pro_device`;

CREATE TABLE `pro_device` (
  `id` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `device_key` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `serial` text,
  `create_by` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `device_key` (`device_key`),
  KEY `FK43187C28E18EEA67` (`create_by`),
  KEY `FK43187C2849B6237A` (`update_by`),
  CONSTRAINT `FK43187C2849B6237A` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FK43187C28E18EEA67` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pro_device` */

insert  into `pro_device`(`id`,`create_date`,`del_flag`,`remarks`,`update_date`,`device_key`,`remark`,`serial`,`create_by`,`update_by`) values ('0397982f2b38465c92840b1f6cce76bb','2016-04-03 20:55:51','0','','2016-04-03 20:55:51','43E9FD522FB4DF66B1A1B201007EA1E65540C7C4',NULL,'1459685260760|nPiL5pfsB/J8wdvvn0bcPt9W7pJLjDEoC7DzsbhLSWDFNG42PoAStPX/gZ7S6dXX+/PzyVKMXzo70w66iSFlRRPat9GCG1SKGG7rfSQUBW8ztz3nOqDBMW6sIkgMBnJbzJFwLdnwyzG30bod8YL5dDG/wUVMoHeVPAjhPwZAJZKHBo75uTQLOzkAGezINDXNomnxxI63jCEpHFVq23ey7wnyB5CETCiutw/ggvNylJmTdQv9Y4225LCjDzfhXVH3Bf0yh8eDwvEiR31zE63cN9wKqQLzXM9G4G+DgaFjsGRPnTxlH5eUSBmZ1RWDenVVqJh8wrGfPHHSeXCD05KJAw==','1','1'),('4c3e0ac20e6b40e4a84dc1e0dcf97557','2016-04-03 20:52:44','0','','2016-04-03 20:52:44','12B8C27D719C0E824680A069442A2337535044E8',NULL,'1459687940582|hf+ODsUab4Jff3q9GBmCpCJTOlLvDBEhhjcPTYZXGfTPLyhWnpLpwK1X0RsTuwHKN3LMJ9ODh5HpTxXXXqo/ij3P7B5MQ2/TRiZhCUZZI0QSDCnZk77k1LIUVrnvQEmgZngTXbVuw+kXgMG2zeOiczuOZLFU1VA784h5PtJIRptGnItjRyl20ZnCuUCBxr/1RmTp6541r9OIgmfjLL7OSpsme26P9Dvsm77rluDek5YdQbJuuwJtQBJz4hquxkFqOQU0bNbqTVLZZIDLjB5mv4IpGtLLMEdLui1I9sU/AlDkUnfT8JIibRG406uoT/IeO3gNGnt6IwmmUBA5Ffx4Cg==','1','1');

/*Table structure for table `pro_product` */

DROP TABLE IF EXISTS `pro_product`;

CREATE TABLE `pro_product` (
  `id` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `flow` text,
  `serial_num` varchar(255) DEFAULT NULL,
  `snp_num` int(11) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `field1` varchar(255) DEFAULT NULL,
  `field2` varchar(255) DEFAULT NULL,
  `field3` varchar(255) DEFAULT NULL,
  `field4` varchar(255) DEFAULT NULL,
  `field5` varchar(255) DEFAULT NULL,
  `field6` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `assy` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB08C295DE18EEA67` (`create_by`),
  KEY `FKB08C295D49B6237A` (`update_by`),
  CONSTRAINT `FKB08C295D49B6237A` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKB08C295DE18EEA67` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pro_product` */

insert  into `pro_product`(`id`,`create_date`,`del_flag`,`remarks`,`update_date`,`flow`,`serial_num`,`snp_num`,`create_by`,`update_by`,`field1`,`field2`,`field3`,`field4`,`field5`,`field6`,`name`,`assy`) values ('175c98b8f17e48a492c61fb1f0590c17','2016-04-04 21:26:15','0','','2016-04-04 21:26:15','','10045',10,'1','1','2BW',NULL,NULL,NULL,NULL,NULL,'w231',0),('31515995676b467bae1da568994a7ff6','2016-03-30 14:52:51','0','','2016-03-30 14:52:51','','10001',10,'1','1','2bw',NULL,NULL,NULL,NULL,NULL,'46340-t9j',1),('3267517ca5f749d5a877b570c3c70a9f','2016-03-30 14:53:16','0','','2016-03-30 14:53:16','','10002',10,'1','1','2bw',NULL,NULL,NULL,NULL,NULL,'17730-t9j',1),('3f0e28bbcfe746d9a65125bb943453e0','2016-03-30 14:51:16','0','','2016-03-30 14:51:16','','10003',30,'1','1','2bw',NULL,NULL,NULL,NULL,NULL,'z-17750-79j-h520',1),('55e851a0b27f4b778a0ff85e9aac0139','2016-03-30 14:51:55','0','','2016-03-30 14:58:11','','10004',10,'1','1','2bw',NULL,NULL,NULL,NULL,NULL,'1700-t9j',1),('5cee7c49c66242f69d919b26b51b06d1','2016-03-30 14:52:36','0','','2016-03-30 14:52:36','','10005',10,'1','1','2bw',NULL,NULL,NULL,NULL,NULL,'46330-t9j',1),('b459f753d1a74110b44e05191fa741eb','2016-03-30 14:52:13','0','','2016-04-03 21:49:01','[{\"id\":\"1\",\"fields\":[{\"field\":\"field1\",\"value\":\"2003\"},{\"field\":\"field2\",\"value\":\"22\"},{\"field\":\"field3\",\"value\":\"L73\"},{\"field\":\"field4\",\"value\":\"ISO\"},{\"field\":\"field5\",\"value\":\"\"},{\"field\":\"field6\",\"value\":\"\"},{\"field\":\"field7\",\"value\":\"\"},{\"field\":\"field8\",\"value\":\"\"}]},{\"id\":\"2\",\"fields\":[{\"field\":\"field1\",\"value\":\"20010\"},{\"field\":\"field2\",\"value\":\"\"}]},{\"id\":\"3\",\"fields\":[]}]','10006',10,'1','1','2bw',NULL,NULL,NULL,NULL,NULL,'1720-t9j',1),('ebd896f9095b4c718caa263cdefd09af','2016-03-30 14:49:45','0','','2016-03-30 14:49:45','','10007',30,'1','1','2-bw',NULL,NULL,NULL,NULL,NULL,'c-17750-79j-h520',1);

/*Table structure for table `pro_product_tree` */

DROP TABLE IF EXISTS `pro_product_tree`;

CREATE TABLE `pro_product_tree` (
  `id` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `number` int(11) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK61C95540E18EEA67` (`create_by`),
  KEY `FK61C9554049B6237A` (`update_by`),
  KEY `FK61C955405D663C18` (`product_id`),
  KEY `FK61C955407050227D` (`parent_id`),
  KEY `FK61C9554015DD6BBB` (`parent_id`),
  CONSTRAINT `FK61C9554049B6237A` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FK61C955405D663C18` FOREIGN KEY (`product_id`) REFERENCES `pro_product` (`id`),
  CONSTRAINT `FK61C955407050227D` FOREIGN KEY (`parent_id`) REFERENCES `pro_product` (`id`),
  CONSTRAINT `FK61C95540E18EEA67` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pro_product_tree` */

insert  into `pro_product_tree`(`id`,`create_date`,`del_flag`,`remarks`,`update_date`,`number`,`create_by`,`update_by`,`product_id`,`parent_id`) values ('01797ad1f5be4b76855f9e2da230b7c4','2016-03-30 14:59:58','0',NULL,'2016-03-30 14:59:58',1,'1','1','55e851a0b27f4b778a0ff85e9aac0139','3f0e28bbcfe746d9a65125bb943453e0'),('096a240de72d4ce5a1454e2a7e574d81','2016-03-30 15:00:04','0',NULL,'2016-03-30 15:00:04',1,'1','1','3267517ca5f749d5a877b570c3c70a9f','3f0e28bbcfe746d9a65125bb943453e0'),('0a9f161a3a7b40fbbda4a69dae35dd7d','2016-03-30 14:59:52','0',NULL,'2016-03-30 14:59:52',1,'1','1','31515995676b467bae1da568994a7ff6','3f0e28bbcfe746d9a65125bb943453e0'),('15e32fa301434ae5b53161bb0cbc48c0','2016-03-30 15:00:09','0',NULL,'2016-03-30 15:00:09',1,'1','1','5cee7c49c66242f69d919b26b51b06d1','3f0e28bbcfe746d9a65125bb943453e0'),('80113ef1d74541829020302a63d7ee02','2016-03-30 14:59:28','0',NULL,'2016-03-30 14:59:28',1,'1','1','ebd896f9095b4c718caa263cdefd09af',NULL),('c35a387dce3345688d537ac17bf5b8d3','2016-03-30 14:59:37','0',NULL,'2016-03-30 14:59:37',1,'1','1','3f0e28bbcfe746d9a65125bb943453e0','ebd896f9095b4c718caa263cdefd09af'),('f8b83da722b041f4acbabbdd0d93642f','2016-03-30 15:00:15','0',NULL,'2016-03-30 15:00:15',1,'1','1','b459f753d1a74110b44e05191fa741eb','3f0e28bbcfe746d9a65125bb943453e0');

/*Table structure for table `pro_production` */

DROP TABLE IF EXISTS `pro_production`;

CREATE TABLE `pro_production` (
  `id` varchar(255) NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `begin_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `number` int(11) NOT NULL,
  `priority` int(11) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `serial_num` varchar(255) DEFAULT NULL,
  `plan_id` varchar(255) NOT NULL,
  `complete_num` int(11) NOT NULL,
  `product_id` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6C3158BE18EEA67` (`create_by`),
  KEY `FK6C3158B49B6237A` (`update_by`),
  KEY `FK6C3158B538CBEF5` (`plan_id`),
  KEY `FK6C3158B5D663C18` (`product_id`),
  CONSTRAINT `FK6C3158B49B6237A` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FK6C3158B538CBEF5` FOREIGN KEY (`plan_id`) REFERENCES `pro_production_plan` (`id`),
  CONSTRAINT `FK6C3158B5D663C18` FOREIGN KEY (`product_id`) REFERENCES `pro_product` (`id`),
  CONSTRAINT `FK6C3158BE18EEA67` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pro_production` */

insert  into `pro_production`(`id`,`update_date`,`create_date`,`del_flag`,`remarks`,`begin_date`,`end_date`,`number`,`priority`,`create_by`,`update_by`,`serial_num`,`plan_id`,`complete_num`,`product_id`,`type`) values ('479c995052de4944b5d78829fed6fb0a','2016-04-04 14:19:28','2016-04-04 14:19:28','0',NULL,NULL,NULL,10,0,'1','1','2016040414192801','13120eef60c14d81ba63d2088e38ffd8',0,'ebd896f9095b4c718caa263cdefd09af',1),('5920578df22b4ddaaa4b62b688086e55','2016-04-03 21:45:00','2016-04-03 21:45:00','0',NULL,NULL,NULL,30,2,'1','1','2016040321450001','13120eef60c14d81ba63d2088e38ffd8',0,'ebd896f9095b4c718caa263cdefd09af',1),('993905a333ab4e8f8d039694c4c35afd','2016-04-04 21:55:56','2016-04-04 21:55:56','0',NULL,NULL,NULL,10,0,'1','1','16040510045','13120eef60c14d81ba63d2088e38ffd8',0,'175c98b8f17e48a492c61fb1f0590c17',0);

/*Table structure for table `pro_production_detail` */

DROP TABLE IF EXISTS `pro_production_detail`;

CREATE TABLE `pro_production_detail` (
  `id` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `data` varchar(255) DEFAULT NULL,
  `serial_num` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `production_id` varchar(255) NOT NULL,
  `number` int(11) NOT NULL,
  `product_tree_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK99A8BD85E18EEA67` (`create_by`),
  KEY `FK99A8BD8549B6237A` (`update_by`),
  KEY `FK99A8BD85D97C59C` (`production_id`),
  KEY `FK99A8BD85EC85C0F7` (`product_tree_id`),
  CONSTRAINT `FK99A8BD8549B6237A` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FK99A8BD85D97C59C` FOREIGN KEY (`production_id`) REFERENCES `pro_production` (`id`),
  CONSTRAINT `FK99A8BD85E18EEA67` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FK99A8BD85EC85C0F7` FOREIGN KEY (`product_tree_id`) REFERENCES `pro_product_tree` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pro_production_detail` */

insert  into `pro_production_detail`(`id`,`create_date`,`del_flag`,`remarks`,`update_date`,`data`,`serial_num`,`status`,`create_by`,`update_by`,`production_id`,`number`,`product_tree_id`) values ('056ecb8433344294b536b20f6e0dcaac','2016-04-03 21:55:21','0',NULL,'2016-04-03 21:55:21',NULL,'160405100070017',NULL,'1','1','5920578df22b4ddaaa4b62b688086e55',10,'f8b83da722b041f4acbabbdd0d93642f'),('0af7af3280774d23a64474f217aeea21','2016-04-03 21:55:21','0',NULL,'2016-04-03 21:55:21',NULL,'160405100070011',NULL,'1','1','5920578df22b4ddaaa4b62b688086e55',10,'096a240de72d4ce5a1454e2a7e574d81'),('0e486af2882a41048fb6fb284c25d13d','2016-04-03 21:55:21','0',NULL,'2016-04-03 21:55:21',NULL,'160405100070015',NULL,'1','1','5920578df22b4ddaaa4b62b688086e55',10,'f8b83da722b041f4acbabbdd0d93642f'),('2158f63e5e884e7d947e7491ab82e173','2016-04-03 21:55:21','0',NULL,'2016-04-03 21:55:21',NULL,'160405100070014',NULL,'1','1','5920578df22b4ddaaa4b62b688086e55',10,'15e32fa301434ae5b53161bb0cbc48c0'),('335b44ad138c4cdeb79a2470e2635920','2016-04-03 21:55:21','0',NULL,'2016-04-03 21:55:21',NULL,'160405100070007',NULL,'1','1','5920578df22b4ddaaa4b62b688086e55',10,'01797ad1f5be4b76855f9e2da230b7c4'),('36766717ff5f49139df5827a450bb136','2016-04-03 21:55:21','0',NULL,'2016-04-03 21:55:21',NULL,'160405100070004',NULL,'1','1','5920578df22b4ddaaa4b62b688086e55',10,'0a9f161a3a7b40fbbda4a69dae35dd7d'),('3fc40eda68af4defb426be012f7a3641','2016-04-03 21:55:21','0',NULL,'2016-04-03 21:55:21',NULL,'160405100070016',NULL,'1','1','5920578df22b4ddaaa4b62b688086e55',10,'f8b83da722b041f4acbabbdd0d93642f'),('4103e7925d884c4281bda2f0547b9fb2','2016-04-03 21:55:21','0',NULL,'2016-04-03 21:55:21',NULL,'160405100070010',NULL,'1','1','5920578df22b4ddaaa4b62b688086e55',10,'096a240de72d4ce5a1454e2a7e574d81'),('5d2ae5190ba949f79c0dd0218d444b68','2016-04-03 21:55:21','0',NULL,'2016-04-03 21:55:21',NULL,'160405100070009',NULL,'1','1','5920578df22b4ddaaa4b62b688086e55',10,'096a240de72d4ce5a1454e2a7e574d81'),('62fb6e6f8ba7417cae24ff4608e52ff4','2016-04-03 21:55:21','0',NULL,'2016-04-03 21:55:21',NULL,'160405100070005',NULL,'1','1','5920578df22b4ddaaa4b62b688086e55',10,'0a9f161a3a7b40fbbda4a69dae35dd7d'),('679e82597db34cdc841eb125244518f3','2016-04-04 21:56:45','0',NULL,'2016-04-04 21:56:45',NULL,'160405100450001',NULL,'1','1','993905a333ab4e8f8d039694c4c35afd',10,NULL),('73cb3bda175c4fd899001f6869f4bfc3','2016-04-03 21:55:21','0',NULL,'2016-04-03 21:55:21',NULL,'160405100070008',NULL,'1','1','5920578df22b4ddaaa4b62b688086e55',10,'01797ad1f5be4b76855f9e2da230b7c4'),('74b5f63da0a34706b8197f22937c59ad','2016-04-03 21:55:21','0',NULL,'2016-04-03 21:55:21',NULL,'160405100070002',NULL,'1','1','5920578df22b4ddaaa4b62b688086e55',30,'c35a387dce3345688d537ac17bf5b8d3'),('77f4dbdd1cd14b049d0c6580ec82c5c7','2016-04-03 21:55:21','0',NULL,'2016-04-03 21:55:21',NULL,'160405100070012',NULL,'1','1','5920578df22b4ddaaa4b62b688086e55',10,'15e32fa301434ae5b53161bb0cbc48c0'),('b3058ec7397643c6b49d6dd295f8150a','2016-04-03 21:55:21','0',NULL,'2016-04-03 21:55:21',NULL,'160405100070013',NULL,'1','1','5920578df22b4ddaaa4b62b688086e55',10,'15e32fa301434ae5b53161bb0cbc48c0'),('befed4fc809f4c2ba0e17d42ba7b8acd','2016-04-03 21:55:21','0',NULL,'2016-04-03 21:55:21',NULL,'160405100070001',NULL,'1','1','5920578df22b4ddaaa4b62b688086e55',30,'80113ef1d74541829020302a63d7ee02'),('ce7a928c30d144bd999878eb190f661c','2016-04-03 21:55:21','0',NULL,'2016-04-03 21:55:21',NULL,'160405100070003',NULL,'1','1','5920578df22b4ddaaa4b62b688086e55',10,'0a9f161a3a7b40fbbda4a69dae35dd7d'),('f3f35c4b7e744248afd9be6af26b2cf8','2016-04-03 21:55:21','0',NULL,'2016-04-03 21:55:21',NULL,'160405100070006',NULL,'1','1','5920578df22b4ddaaa4b62b688086e55',10,'01797ad1f5be4b76855f9e2da230b7c4');

/*Table structure for table `pro_production_history` */

DROP TABLE IF EXISTS `pro_production_history`;

CREATE TABLE `pro_production_history` (
  `id` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `detail_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK75D5E5E0E18EEA67` (`create_by`),
  KEY `FK75D5E5E049B6237A` (`update_by`),
  KEY `FK75D5E5E09B9CC335` (`detail_id`),
  CONSTRAINT `FK75D5E5E049B6237A` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FK75D5E5E09B9CC335` FOREIGN KEY (`detail_id`) REFERENCES `pro_production_detail` (`id`),
  CONSTRAINT `FK75D5E5E0E18EEA67` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pro_production_history` */

/*Table structure for table `pro_production_plan` */

DROP TABLE IF EXISTS `pro_production_plan`;

CREATE TABLE `pro_production_plan` (
  `id` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `begin_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `number` int(11) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `field1` varchar(255) DEFAULT NULL,
  `serial_num` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8382ED5DE18EEA67` (`create_by`),
  KEY `FK8382ED5D49B6237A` (`update_by`),
  CONSTRAINT `FK8382ED5D49B6237A` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FK8382ED5DE18EEA67` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pro_production_plan` */

insert  into `pro_production_plan`(`id`,`create_date`,`del_flag`,`remarks`,`update_date`,`begin_date`,`end_date`,`number`,`status`,`create_by`,`update_by`,`name`,`field1`,`serial_num`) values ('13120eef60c14d81ba63d2088e38ffd8','2016-04-03 18:56:53','0','','2016-04-03 18:56:53','2016-04-03 00:00:00','2016-04-03 00:00:00',0,'','1','1','春季计划','W-2WH','160405');

/*Table structure for table `pro_stock` */

DROP TABLE IF EXISTS `pro_stock`;

CREATE TABLE `pro_stock` (
  `id` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `number` int(11) NOT NULL,
  `use_number` int(11) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `product_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB8B19704E18EEA67` (`create_by`),
  KEY `FKB8B1970449B6237A` (`update_by`),
  KEY `FKB8B197045D663C18` (`product_id`),
  CONSTRAINT `FKB8B1970449B6237A` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKB8B197045D663C18` FOREIGN KEY (`product_id`) REFERENCES `pro_product` (`id`),
  CONSTRAINT `FKB8B19704E18EEA67` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pro_stock` */

insert  into `pro_stock`(`id`,`create_date`,`del_flag`,`remarks`,`update_date`,`number`,`use_number`,`create_by`,`update_by`,`product_id`) values ('0a26bfd7819144afa856334b809843d0','2016-03-30 14:51:55','0',NULL,'2016-03-30 14:51:55',0,0,'1','1','55e851a0b27f4b778a0ff85e9aac0139'),('15108b9077c440b98b25aed021c31c25','2016-03-30 14:52:13','0',NULL,'2016-03-30 14:52:13',0,0,'1','1','b459f753d1a74110b44e05191fa741eb'),('6fc6928df07e4553b915e2e0390733e1','2016-04-04 21:26:15','0',NULL,'2016-04-04 21:26:15',0,0,'1','1','175c98b8f17e48a492c61fb1f0590c17'),('7d95ec3951f149ca9378b95e68eadb45','2016-03-30 14:52:51','0',NULL,'2016-04-04 20:25:11',-1,0,'1','1','31515995676b467bae1da568994a7ff6'),('8e27eade8a5a4aedb96b4659a3476fe0','2016-03-30 14:49:45','0',NULL,'2016-04-04 20:25:00',35,0,'1','1','ebd896f9095b4c718caa263cdefd09af'),('968b78cd5f2548cb8a82325462e8df7b','2016-03-30 14:52:36','0',NULL,'2016-03-30 14:52:36',0,0,'1','1','5cee7c49c66242f69d919b26b51b06d1'),('dfefce023b3946ed9cc9e5ae2f095636','2016-03-30 14:53:16','0',NULL,'2016-03-30 14:53:16',0,0,'1','1','3267517ca5f749d5a877b570c3c70a9f'),('ffde6707ad9845b692d11d9c58055cb2','2016-03-30 14:51:16','0',NULL,'2016-03-30 16:27:43',70,0,'1','1','3f0e28bbcfe746d9a65125bb943453e0');

/*Table structure for table `pro_stock_bills` */

DROP TABLE IF EXISTS `pro_stock_bills`;

CREATE TABLE `pro_stock_bills` (
  `id` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `number` int(11) NOT NULL,
  `use_number` int(11) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `product_id` varchar(255) NOT NULL,
  `from_date` datetime DEFAULT NULL,
  `prev_number` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKF8300451E18EEA67` (`create_by`),
  KEY `FKF830045149B6237A` (`update_by`),
  KEY `FKF83004515D663C18` (`product_id`),
  KEY `create_date` (`create_date`),
  CONSTRAINT `FKF830045149B6237A` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKF83004515D663C18` FOREIGN KEY (`product_id`) REFERENCES `pro_product` (`id`),
  CONSTRAINT `FKF8300451E18EEA67` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pro_stock_bills` */

insert  into `pro_stock_bills`(`id`,`create_date`,`del_flag`,`remarks`,`update_date`,`number`,`use_number`,`create_by`,`update_by`,`product_id`,`from_date`,`prev_number`) values ('171d40080c4d4c24bc092d4f9fa331d1','2016-04-10 13:08:56','0',NULL,'2016-04-10 13:08:56',0,0,'1','1','5cee7c49c66242f69d919b26b51b06d1','2016-04-10 13:07:21',0),('1ad04e8661e24dbdb66c0cf194ac0bce','2016-04-10 13:08:56','0',NULL,'2016-04-10 13:08:56',-1,0,'1','1','31515995676b467bae1da568994a7ff6','2016-04-10 13:07:21',-1),('1edb7b73e79244479feae0e4a47d84d4','2016-04-10 13:07:21','0',NULL,'2016-04-10 13:07:21',0,0,'1','1','b459f753d1a74110b44e05191fa741eb',NULL,0),('2336509824fa43b8b7ab1e2dbd1cc54d','2016-04-11 03:31:39','0',NULL,'2016-04-11 03:31:39',0,0,NULL,NULL,'b459f753d1a74110b44e05191fa741eb','2016-04-10 13:08:56',0),('26316e6a3d234396b01cdefa523abeb4','2016-04-10 13:08:56','0',NULL,'2016-04-10 13:08:56',0,0,'1','1','55e851a0b27f4b778a0ff85e9aac0139','2016-04-10 13:07:21',0),('3d8e5c22f6924365a11a3f6a1a725c43','2016-04-10 13:08:56','0',NULL,'2016-04-10 13:08:56',35,0,'1','1','ebd896f9095b4c718caa263cdefd09af','2016-04-10 13:07:21',35),('636d61505f48461ead76f8b78d247b06','2016-04-10 13:07:21','0',NULL,'2016-04-10 13:07:21',0,0,'1','1','175c98b8f17e48a492c61fb1f0590c17',NULL,0),('6de4224c2a354ab88b1844b0fe6733c2','2016-04-11 03:31:39','0',NULL,'2016-04-11 03:31:39',0,0,NULL,NULL,'3267517ca5f749d5a877b570c3c70a9f','2016-04-10 13:08:56',0),('74595f2a06e2473387218c03d8fd04a7','2016-04-11 03:31:39','0',NULL,'2016-04-11 03:31:39',35,0,NULL,NULL,'ebd896f9095b4c718caa263cdefd09af','2016-04-10 13:08:56',35),('7bc718f70b714136abf8b9efa22fc7f4','2016-04-10 13:07:21','0',NULL,'2016-04-10 13:07:21',0,0,'1','1','3267517ca5f749d5a877b570c3c70a9f',NULL,0),('7c3359da566743ad9f166e61f7461a6a','2016-04-11 03:31:39','0',NULL,'2016-04-11 03:31:39',70,0,NULL,NULL,'3f0e28bbcfe746d9a65125bb943453e0','2016-04-10 13:08:56',70),('810e609a0c4e4cb4bc69ea2911addad4','2016-04-10 13:07:21','0',NULL,'2016-04-10 13:07:21',-1,0,'1','1','31515995676b467bae1da568994a7ff6',NULL,0),('9f5f164cf5634be3ad78b210e5456012','2016-04-10 13:08:56','0',NULL,'2016-04-10 13:08:56',0,0,'1','1','175c98b8f17e48a492c61fb1f0590c17','2016-04-10 13:07:21',0),('b8da1bd14ede47818e84cd9d5b966d44','2016-04-10 13:08:56','0',NULL,'2016-04-10 13:08:56',0,0,'1','1','3267517ca5f749d5a877b570c3c70a9f','2016-04-10 13:07:21',0),('b9c524a808bf497092fd1be1e9e720e8','2016-04-10 13:07:21','0',NULL,'2016-04-10 13:07:21',70,0,'1','1','3f0e28bbcfe746d9a65125bb943453e0',NULL,0),('c8c1be6075ed4686a12bee462a18b378','2016-04-11 03:31:39','0',NULL,'2016-04-11 03:31:39',0,0,NULL,NULL,'175c98b8f17e48a492c61fb1f0590c17','2016-04-10 13:08:56',0),('d5e5f42eac5c4e63bc1439d359e5b227','2016-04-11 03:31:39','0',NULL,'2016-04-11 03:31:39',0,0,NULL,NULL,'55e851a0b27f4b778a0ff85e9aac0139','2016-04-10 13:08:56',0),('d6d5aceb1c25462eb2435dae223bab8a','2016-04-10 13:08:56','0',NULL,'2016-04-10 13:08:56',0,0,'1','1','b459f753d1a74110b44e05191fa741eb','2016-04-10 13:07:21',0),('df026cdad93a458bb239747a5f093c87','2016-04-11 03:31:39','0',NULL,'2016-04-11 03:31:39',-1,0,NULL,NULL,'31515995676b467bae1da568994a7ff6','2016-04-10 13:08:56',-1),('e2dd8da5d8fc4f6799ef402c428d646f','2016-04-11 03:31:39','0',NULL,'2016-04-11 03:31:39',0,0,NULL,NULL,'5cee7c49c66242f69d919b26b51b06d1','2016-04-10 13:08:56',0),('e3cf914b640d483aa89f725b5e70e375','2016-04-10 13:07:21','0',NULL,'2016-04-10 13:07:21',0,0,'1','1','5cee7c49c66242f69d919b26b51b06d1',NULL,0),('efa52484072c4901a999eae538f8396f','2016-04-10 13:08:56','0',NULL,'2016-04-10 13:08:56',70,0,'1','1','3f0e28bbcfe746d9a65125bb943453e0','2016-04-10 13:07:21',70),('f0de224eefb74afb8b5b6cba6dd177fe','2016-04-10 13:07:21','0',NULL,'2016-04-10 13:07:21',35,0,'1','1','ebd896f9095b4c718caa263cdefd09af',NULL,0),('f6ade378c9d145e58f329ff86a4a71d9','2016-04-10 13:07:21','0',NULL,'2016-04-10 13:07:21',0,0,'1','1','55e851a0b27f4b778a0ff85e9aac0139',NULL,0);

/*Table structure for table `pro_stock_history` */

DROP TABLE IF EXISTS `pro_stock_history`;

CREATE TABLE `pro_stock_history` (
  `id` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `number` int(11) NOT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `product_id` varchar(255) NOT NULL,
  `production_detail_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKEA0BDE59E18EEA67` (`create_by`),
  KEY `FKEA0BDE5949B6237A` (`update_by`),
  KEY `FKEA0BDE595D663C18` (`product_id`),
  KEY `FKEA0BDE594133DB2F` (`production_detail_id`),
  CONSTRAINT `FKEA0BDE594133DB2F` FOREIGN KEY (`production_detail_id`) REFERENCES `pro_production_detail` (`id`),
  CONSTRAINT `FKEA0BDE5949B6237A` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKEA0BDE595D663C18` FOREIGN KEY (`product_id`) REFERENCES `pro_product` (`id`),
  CONSTRAINT `FKEA0BDE59E18EEA67` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pro_stock_history` */

insert  into `pro_stock_history`(`id`,`create_date`,`del_flag`,`remarks`,`update_date`,`number`,`reason`,`type`,`create_by`,`update_by`,`product_id`,`production_detail_id`) values ('f4cf8c5a5b2247028a46274145c2efff','2016-04-04 20:25:11','0',NULL,'2016-04-04 20:25:11',-1,'a','5','1','1','31515995676b467bae1da568994a7ff6',NULL),('f50e5f76fb4543dda650dcd881ee527b','2016-04-04 20:25:00','0',NULL,'2016-04-04 20:25:00',5,'aa','1','1','1','ebd896f9095b4c718caa263cdefd09af',NULL);

/*Table structure for table `sys_area` */

DROP TABLE IF EXISTS `sys_area`;

CREATE TABLE `sys_area` (
  `id` varchar(255) NOT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `parent_ids` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(1) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK749F02BFE18EEA67` (`create_by`),
  KEY `FK749F02BF49B6237A` (`update_by`),
  CONSTRAINT `FK749F02BF49B6237A` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FK749F02BFE18EEA67` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_area` */

insert  into `sys_area`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`remarks`,`create_by`,`create_date`,`update_by`,`update_date`,`del_flag`) values ('1','0','0,','100000','中国','1',NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('10','8','0,1,2,','370532','历城区','4',NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('11','8','0,1,2,','370533','历城区','4',NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('12','8','0,1,2,','370534','历下区','4',NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('13','8','0,1,2,','370535','天桥区','4',NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('14','8','0,1,2,','370536','槐荫区','4',NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('2','1','0,1,','110000','北京市','2',NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('3','2','0,1,2,','110101','东城区','4',NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('4','2','0,1,2,','110102','西城区','4',NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('5','2','0,1,2,','110103','朝阳区','4',NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('6','2','0,1,2,','110104','丰台区','4',NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('7','2','0,1,2,','110105','海淀区','4',NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('8','1','0,1,','370000','山东省','2',NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('9','8','0,1,2,','370531','济南市','3',NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0');

/*Table structure for table `sys_dict` */

DROP TABLE IF EXISTS `sys_dict`;

CREATE TABLE `sys_dict` (
  `id` varchar(255) NOT NULL,
  `label` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  `update_by` varchar(200) DEFAULT NULL,
  `update_date` timestamp NULL DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK74A03DE8E18EEA67` (`create_by`),
  KEY `FK74A03DE849B6237A` (`update_by`),
  CONSTRAINT `FK74A03DE849B6237A` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FK74A03DE8E18EEA67` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_dict` */

insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`remarks`,`create_by`,`create_date`,`update_by`,`update_date`,`del_flag`) values ('043451afbb5c46e882e240244d407e22','库存损失','7','stock_type','-1',7,NULL,'1','2016-03-13 14:19:59','1','2016-03-13 14:54:46','0'),('08da77724f374aa0b27b7c747b1b0822','中','2','production_priority','生产类型',2,NULL,'1','2016-03-13 16:14:25','1','2016-03-13 16:14:25','0'),('1','正常','0','del_flag','删除标记',10,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('10','黄色',NULL,'color','颜色值',40,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('11','橙色',NULL,'color','颜色值',50,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('12','默认主题',NULL,'theme','主题方案',10,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('13','天蓝主题',NULL,'theme','主题方案',20,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('14','橙色主题',NULL,'theme','主题方案',30,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('15','红色主题',NULL,'theme','主题方案',40,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('16','Flat主题',NULL,'theme','主题方案',60,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('17','国家','1','sys_area_type','区域类型',10,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('18','省份、直辖市','2','sys_area_type','区域类型',20,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('19','地市','3','sys_area_type','区域类型',30,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('2','删除','1','del_flag','删除标记',20,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('20','区县','4','sys_area_type','区域类型',40,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('21','公司','1','sys_office_type','机构类型',60,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('22','部门','2','sys_office_type','机构类型',70,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('23','一级','1','sys_office_grade','机构等级',10,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('24','二级','2','sys_office_grade','机构等级',20,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('25','三级','3','sys_office_grade','机构等级',30,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('26','四级','4','sys_office_grade','机构等级',40,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('27','所有数据','1','sys_data_scope','数据范围',10,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('28','所在公司及以下数据','2','sys_data_scope','数据范围',20,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('29','所在公司数据','3','sys_data_scope','数据范围',30,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('3','显示','1','show_hide','显示/隐藏',10,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('30','所在部门及以下数据','4','sys_data_scope','数据范围',40,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('31','所在部门数据','5','sys_data_scope','数据范围',50,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('32','仅本人数据','8','sys_data_scope','数据范围',90,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('33','按明细设置','9','sys_data_scope','数据范围',100,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('34','系统管理','1','sys_user_type','用户类型',10,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('35','部门经理','2','sys_user_type','用户类型',20,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('36','普通用户','3','sys_user_type','用户类型',30,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('37','基础主题',NULL,'cms_theme','站点主题',10,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('38','蓝色主题',NULL,'cms_theme','站点主题',20,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','1'),('39','红色主题',NULL,'cms_theme','站点主题',30,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','1'),('4','隐藏','0','show_hide','显示/隐藏',20,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('40','文章模型',NULL,'cms_module','栏目模型',10,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('41','图片模型',NULL,'cms_module','栏目模型',20,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','1'),('42','下载模型',NULL,'cms_module','栏目模型',30,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','1'),('43','链接模型',NULL,'cms_module','栏目模型',40,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('44','专题模型',NULL,'cms_module','栏目模型',50,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','1'),('45','默认展现方式','0','cms_show_modes','展现方式',10,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('46','首栏目内容列表','1','cms_show_modes','展现方式',20,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('460ab2c325eb4c059e5ef2b7c9935338','借入','4','stock_type','1',4,NULL,'1','2016-03-13 14:18:59','1','2016-03-13 14:54:29','0'),('47','栏目第一条内容','2','cms_show_modes','展现方式',30,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('48','发布','0','cms_del_flag','内容状态',10,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('49','删除','1','cms_del_flag','内容状态',20,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('5','是','1','yes_no','是/否',10,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('50','审核','2','cms_del_flag','内容状态',15,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('51','首页焦点图','1','cms_posid','推荐位',10,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('52','栏目页文章推荐','2','cms_posid','推荐位',20,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('53','咨询','1','cms_guestbook','留言板分类',10,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('5308c7a95972494c816014b91606008a','借出','8','stock_type','-1',8,NULL,'1','2016-03-13 14:20:20','1','2016-03-13 14:54:51','0'),('54','建议','2','cms_guestbook','留言板分类',20,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('55','投诉','3','cms_guestbook','留言板分类',30,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('56','其它','4','cms_guestbook','留言板分类',40,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('57','公休','1','oa_leave_type','请假类型',10,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('58','病假','2','oa_leave_type','请假类型',20,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('59','事假','3','oa_leave_type','请假类型',30,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('5db8031a426647bd91b82e560ae999a1','【检查】','3','flow_type','功序流程',3,NULL,'1','2016-03-27 16:49:46','1','2016-03-27 16:49:46','0'),('6','否','0','yes_no','是/否',20,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('60','调休','4','oa_leave_type','请假类型',40,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('61','婚假','5','oa_leave_type','请假类型',60,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('62','接入日志','1','sys_log_type','日志类型',30,NULL,'1','2013-06-03 00:00:00','1','2013-06-03 00:00:00','0'),('63','异常日志','2','sys_log_type','日志类型',40,NULL,'1','2013-06-03 00:00:00','1','2013-06-03 00:00:00','0'),('64','单表增删改查',NULL,'prj_template_type','代码模板',10,NULL,'1','2013-06-03 00:00:00','1','2013-06-03 00:00:00','0'),('65','所有entity和dao',NULL,'prj_template_type','代码模板',20,NULL,'1','2013-06-03 00:00:00','1','2013-06-03 00:00:00','0'),('66','号段','com.thinkgem.jeesite.modules.crbt.strategy.PhoneStrategy','advert_strategie_type','广告投放策略类型',1,NULL,'1','2016-02-20 18:13:23','1','2016-02-27 20:36:39','0'),('7','红色',NULL,'color','颜色值',10,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('8','绿色',NULL,'color','颜色值',20,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('8f6271854dd04cd7bbc00e706e197a7d','进货入库','1','stock_type','1',1,NULL,'1','2016-03-13 14:17:41','1','2016-03-13 14:54:15','0'),('9','蓝色',NULL,'color','颜色值',30,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('92b95f0275a9474cb7fd185c8149c6b0','盘点溢出','3','stock_type','1',3,NULL,'1','2016-03-13 14:18:37','1','2016-03-13 14:54:25','0'),('9e864b64529949d7995a465ce384edb1','低','3','production_priority','生产类型',3,NULL,'1','2016-03-13 16:14:43','1','2016-03-13 16:14:43','0'),('a4582ef6b5714a21bdc5e8a5a013715b','号段','com.thinkgem.jeesite.modules.crbt.strategy.PhoneStrategy','widget_strategie_type','场景投放策略类型',1,NULL,'1','2016-03-05 22:16:55','1','2016-03-05 22:16:55','0'),('a6c9fccf753b49f1b4f4aff859443ad7','生产入库','2','stock_type','1',2,NULL,'1','2016-03-13 14:18:17','1','2016-03-13 14:54:20','0'),('bf00b4ddf47f49ecae7c02b0b15b62da','销售出库','5','stock_type','-1',5,NULL,'1','2016-03-13 14:19:21','1','2016-03-13 14:54:36','0'),('cca1d7922df14399b0d427051a1b4bbc','生产消耗','6','stock_type','-1',6,NULL,'1','2016-03-13 14:19:40','1','2016-03-13 14:54:41','0'),('dff9da9ec0fd42d79a373841dc698f22','【弯曲】','2','flow_type','功序流程',2,NULL,'1','2016-03-12 19:20:49','1','2016-03-27 16:49:59','0'),('ea7513afaa144b908f6be53ea19902cb','高','1','production_priority','生产类型',1,NULL,'1','2016-03-13 16:14:02','1','2016-03-13 16:14:02','0'),('ee60a3d0b80e46baa99cd296bd707897','【组立】','4','flow_type','功序流程',4,NULL,'1','2016-03-12 19:21:05','1','2016-03-27 16:50:06','0'),('fee6c0c9c0a84fbfb78506cd03608dbf','【端末加工】','1','flow_type','功序流程',1,NULL,'1','2016-03-12 18:58:47','1','2016-03-27 16:49:52','0');

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `id` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `exception` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `params` varchar(255) DEFAULT NULL,
  `remote_addr` varchar(255) DEFAULT NULL,
  `request_uri` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK986862D2E18EEA67` (`create_by`),
  CONSTRAINT `FK986862D2E18EEA67` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_log` */

/*Table structure for table `sys_mdict` */

DROP TABLE IF EXISTS `sys_mdict`;

CREATE TABLE `sys_mdict` (
  `id` varchar(255) DEFAULT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `parent_ids` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `sort` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_date` varchar(255) DEFAULT NULL,
  `del_flag` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_mdict` */

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` varchar(255) NOT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `parent_ids` varchar(2000) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `is_show` char(1) DEFAULT NULL,
  `is_activiti` char(1) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK74A44791E18EEA67` (`create_by`),
  KEY `FK74A4479149B6237A` (`update_by`),
  CONSTRAINT `FK74A4479149B6237A` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FK74A44791E18EEA67` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`is_activiti`,`permission`,`remarks`,`create_by`,`create_date`,`update_by`,`update_date`,`del_flag`) values ('0328679fb4e14f51bf85e005a212d37c','a63e86129afc4b9ba88dc848099775f8','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,7cbfb21dbf2d498eb052f9e11038166e,a63e86129afc4b9ba88dc848099775f8,','编辑','','','',2,'0','0','crbt:advertCategory:edit',NULL,'1','2016-02-20 17:53:15','1','2016-02-27 23:11:06','1'),('04e2936e23524b53802f71155b002555','fa48b1b2ba5844fdbdb14264b00ca2f2','0,1,fa48b1b2ba5844fdbdb14264b00ca2f2,','产品管理','/pro/product/list','','',1,'1','0','pro:product:view',NULL,'1','2016-03-12 18:06:21','1','2016-03-20 22:53:31','0'),('058b27ecc4d14669a9dc18311b00e7ba','1','0,1,','生产管理','','','',4,'1','0','',NULL,'1','2016-03-12 18:12:32','1','2016-03-20 22:53:31','0'),('1','0','0,','顶级菜单',NULL,NULL,NULL,0,'1','0',NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('10','3','0,1,2,3,','字典管理','/sys/dict/',NULL,'th-list',60,'1','0',NULL,NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:34','0'),('11','10','0,1,2,3,10,','查看',NULL,NULL,NULL,30,'0','0','sys:dict:view',NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:34','0'),('119e2a26a0974510ace3a41902098fde','2bb73aeb7cda45f6b4a55f195eed31ed','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,','铃音管理','','','',2,'1','0','',NULL,'1','2016-02-27 20:10:24','1','2016-02-27 23:11:07','1'),('12','10','0,1,2,3,10,','修改',NULL,NULL,NULL,30,'0','0','sys:dict:edit',NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:34','0'),('13','2','0,1,2,','机构用户',NULL,NULL,NULL,1,'1','0',NULL,NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:32','0'),('14','13','0,1,2,13,','区域管理','/sys/area/',NULL,'th',50,'1','0',NULL,NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:33','0'),('15','14','0,1,2,13,14,','查看',NULL,NULL,NULL,30,'0','0','sys:area:view',NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:33','0'),('16','14','0,1,2,13,14,','修改',NULL,NULL,NULL,30,'0','0','sys:area:edit',NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:33','0'),('17','13','0,1,2,13,','机构管理','/sys/office/',NULL,'th-large',40,'1','0',NULL,NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:33','0'),('18','17','0,1,2,13,17,','查看',NULL,NULL,NULL,30,'0','0','sys:office:view',NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:33','0'),('19','17','0,1,2,13,17,','修改',NULL,NULL,NULL,30,'0','0','sys:office:edit',NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:33','0'),('1c78d4ff3a424904a83f4807d26c0d7f','e5ce08fa68b54b4385707ac92f5249dc','0,1,e5ce08fa68b54b4385707ac92f5249dc,','库存轧账','/pro/stockBills','','',30,'1','0','pro:stockBills:view',NULL,'1','2016-04-03 22:36:34','1','2016-04-03 22:36:34','0'),('1d282a691a4a448aa5d25ec8194d1c58','e5ce08fa68b54b4385707ac92f5249dc','0,1,e5ce08fa68b54b4385707ac92f5249dc,','出入库记录','/pro/stockHistory/list','','',2,'1','0','pro:stockHistory:view',NULL,'1','2016-03-12 18:09:59','1','2016-03-29 22:19:02','0'),('1f11fd7fd9ab4fba84ccd90eaa96b464','b22f77ff2b8048bba3994e753cbefc41','0,1,058b27ecc4d14669a9dc18311b00e7ba,b22f77ff2b8048bba3994e753cbefc41,','编辑','','','',1,'0','0','pro:productionDetail:edit',NULL,'1','2016-03-12 18:14:46','1','2016-03-20 22:53:32','0'),('2','1','0,1,','系统设置',NULL,NULL,NULL,5,'1','0',NULL,NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:32','0'),('20','13','0,1,2,13,','登录账号管理','/sys/user/','','user',30,'1','0','',NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:33','0'),('21','20','0,1,2,13,20,','查看',NULL,NULL,NULL,30,'0','0','sys:user:view',NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:33','0'),('22','20','0,1,2,13,20,','修改',NULL,NULL,NULL,30,'0','0','sys:user:edit',NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:33','0'),('24','23','0,1,2,23','项目首页','http://jeesite.com','_blank',NULL,30,'1','0',NULL,NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:32','0'),('25','23','0,1,2,23','项目支持','http://jeesite.com/donation.html','_blank',NULL,50,'1','0',NULL,NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:32','0'),('258e89c12e6c427eaebe3f1b92a119d6','119e2a26a0974510ace3a41902098fde','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,119e2a26a0974510ace3a41902098fde,','互动历史','/crbt/interactHistory','','',2,'1','0','',NULL,'1','2016-02-27 17:12:47','1','2016-02-27 23:11:07','1'),('26','23','0,1,2,23','论坛交流','http://bbs.jeesite.com','_blank',NULL,80,'1','0',NULL,NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:32','0'),('27','1','0,1,','我的面板',NULL,NULL,NULL,1,'1','0',NULL,NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:31','0'),('28','27','0,1,27,','个人信息',NULL,NULL,NULL,1,'1','0',NULL,NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:31','0'),('29','28','0,1,27,28,','个人信息','/sys/user/info',NULL,'user',1,'1','0',NULL,NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:31','0'),('2bb73aeb7cda45f6b4a55f195eed31ed','1','0,1,','彩铃管理','','','',2,'1','0','',NULL,'1','2016-02-20 17:44:45','1','2016-02-27 23:11:06','1'),('2c644a1b11d5438081f966f9cbcc268f','13','0,1,2,13,','设备管理','/pro/device','','',30,'1','0','pro:device:view',NULL,'1','2016-04-03 20:25:13','1','2016-04-03 20:25:13','0'),('2c85f9e4d0e746b7a8d4f4293e64e883','79c35949da59478c9c9a194432fcb5a3','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,71f0137ca6004ec98e0bae9e8a12f356,79c35949da59478c9c9a194432fcb5a3,','显示','','','',1,'0','0','cms:ckfinder:view',NULL,'1','2016-02-20 18:20:06','1','2016-02-27 23:11:07','1'),('3','2','0,1,2,','系统设置',NULL,NULL,NULL,2,'1','0',NULL,NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:33','0'),('30','28','0,1,27,28,','修改密码','/sys/user/modifyPwd',NULL,'lock',2,'1','0',NULL,NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:31','0'),('3843049173ba47018779cebf78b4ad9c','2c644a1b11d5438081f966f9cbcc268f','0,1,2,13,2c644a1b11d5438081f966f9cbcc268f,','添加','','','',30,'0','0','pro:device:edit',NULL,'1','2016-04-03 20:36:52','1','2016-04-03 20:36:52','0'),('3dd015f4edde4badaef02cd24660e966','e957b002d2f347c89e3627f109074e1d','0,1,e957b002d2f347c89e3627f109074e1d,','铃音统计','','','',2,'1','0','',NULL,'1','2016-02-27 23:00:54','1','2016-02-27 23:11:07','1'),('4','3','0,1,2,3,','菜单管理','/sys/menu/',NULL,'list-alt',30,'1','0',NULL,NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:33','0'),('410c2e6017b34649b8070d7f23f3dca1','6333d6cccaf24ef9a68ee47d9e215398','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,119e2a26a0974510ace3a41902098fde,6333d6cccaf24ef9a68ee47d9e215398,','显示','','','',1,'0','0','crbt:ring:view',NULL,'1','2016-02-27 17:24:02','1','2016-02-27 23:11:07','1'),('44eaa1e20a1049b2a577575fc46f7ab1','119e2a26a0974510ace3a41902098fde','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,119e2a26a0974510ace3a41902098fde,','购买记录','/crbt/buyHistory','','',3,'1','0','',NULL,'1','2016-02-27 16:38:47','1','2016-02-27 23:11:07','1'),('4b32f5715ddb4d5ca6789d13272a8404','1','0,1,','终端管理','','','',3,'1','0','',NULL,'1','2016-02-27 20:03:04','1','2016-02-27 23:11:07','1'),('4b585d9d938140a3add6e8b8819f950e','7cbfb21dbf2d498eb052f9e11038166e','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,7cbfb21dbf2d498eb052f9e11038166e,','广告管理','/crbt/advert','','',2,'1','0','',NULL,'1','2016-02-20 17:56:29','1','2016-02-27 23:11:06','1'),('4bc15f4b751c41e0864086eaf9dbb890','4b585d9d938140a3add6e8b8819f950e','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,7cbfb21dbf2d498eb052f9e11038166e,4b585d9d938140a3add6e8b8819f950e,','编辑','','','',2,'0','0','crbt:advert:edit',NULL,'1','2016-02-20 17:57:10','1','2016-02-27 23:11:06','1'),('5','4','0,1,2,3,4,','查看',NULL,NULL,NULL,30,'0','0','sys:menu:view',NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:33','0'),('54d18f155481454fa991488d33a878cc','2bb73aeb7cda45f6b4a55f195eed31ed','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,','历史查询','','','',4,'1','0','',NULL,'1','2016-02-27 14:07:26','1','2016-02-27 23:11:06','1'),('5b74e8e1962844c7a1533f1e7ebe0713','058b27ecc4d14669a9dc18311b00e7ba','0,1,058b27ecc4d14669a9dc18311b00e7ba,','生产指令','/pro/productionPlan','','',1,'1','0','pro:productionPlan:view',NULL,'1','2016-03-20 22:53:05','1','2016-04-10 11:40:25','0'),('5f1a1ad805b442d7a981a47eb38d4497','e957b002d2f347c89e3627f109074e1d','0,1,e957b002d2f347c89e3627f109074e1d,','用户统计','','','',1,'1','0','',NULL,'1','2016-02-27 23:02:04','1','2016-02-27 23:11:07','1'),('5f3520952f85494f834a513c88085b21','e85b0969455a4336b80ffecfb5030c8c','0,1,4b32f5715ddb4d5ca6789d13272a8404,e85b0969455a4336b80ffecfb5030c8c,','版本管理','/crbt/clientVersion','','',1,'1','0','',NULL,'1','2016-02-27 17:44:01','1','2016-02-27 23:11:07','1'),('6','4','0,1,2,3,4,','修改',NULL,NULL,NULL,30,'0','0','sys:menu:edit',NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:33','0'),('620ce85047c94b0285845c68ac83bf12','8be54c6187ea49c690bea6269b3a3828','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,8be54c6187ea49c690bea6269b3a3828,','问答查询','/crbt/answerHistory','','',30,'1','0','crbt:answerHistory:view',NULL,'1','2016-03-06 18:15:04','1','2016-03-06 18:15:04','1'),('6333d6cccaf24ef9a68ee47d9e215398','119e2a26a0974510ace3a41902098fde','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,119e2a26a0974510ace3a41902098fde,','铃音查询','/crbt/ring','','',1,'1','0','',NULL,'1','2016-02-27 17:23:48','1','2016-02-27 23:11:07','1'),('63d4d6103da74d47bfaa9bfb8033a21b','4b32f5715ddb4d5ca6789d13272a8404','0,1,4b32f5715ddb4d5ca6789d13272a8404,','客户端用户','','','',1,'1','0','',NULL,'1','2016-02-27 20:03:32','1','2016-02-27 23:11:07','1'),('6624794d5d1741c4870db1d92d78e9d3','5f3520952f85494f834a513c88085b21','0,1,4b32f5715ddb4d5ca6789d13272a8404,e85b0969455a4336b80ffecfb5030c8c,5f3520952f85494f834a513c88085b21,','显示','','','',1,'0','0','crbt:clientVersion:view',NULL,'1','2016-02-27 17:44:17','1','2016-02-27 23:11:07','1'),('67','2','0,1,2,','日志查询',NULL,NULL,NULL,3,'1','0',NULL,NULL,'1','2013-06-03 00:00:00','1','2016-03-20 22:53:34','0'),('68','67','0,1,2,67,','日志查询','/sys/log',NULL,'pencil',1,'1','0','sys:log:view',NULL,'1','2013-06-03 00:00:00','1','2016-03-20 22:53:34','0'),('6a106b6bc6f44a2b963e3a9069ad6187','cd5398ba213a4d47bff1c832ede8e255','0,1,e957b002d2f347c89e3627f109074e1d,cd5398ba213a4d47bff1c832ede8e255,','播放统计','/crbt/advert/statisticPlayHistory','','',1,'1','0','',NULL,'1','2016-02-28 21:32:18','1','2016-02-28 21:32:18','1'),('6ec2dec2a20a4e0e83a7b15918e3010d','058b27ecc4d14669a9dc18311b00e7ba','0,1,058b27ecc4d14669a9dc18311b00e7ba,','工艺流水','/pro/productionHistory/list','','',4,'1','0','pro:productionHistory:view',NULL,'1','2016-03-12 18:11:25','1','2016-03-20 22:53:32','0'),('7','3','0,1,2,3,','角色管理','/sys/role/',NULL,'lock',50,'1','0',NULL,NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:33','0'),('71f0137ca6004ec98e0bae9e8a12f356','2bb73aeb7cda45f6b4a55f195eed31ed','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,','资源管理','','','',3,'1','0','',NULL,'1','2016-02-27 20:14:19','1','2016-02-27 23:11:07','1'),('73ceb528363545ca929fd07c3adcfc20','8be54c6187ea49c690bea6269b3a3828','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,8be54c6187ea49c690bea6269b3a3828,','场景设置','/crbt/widget','','',1,'1','0','crbt:widgetService:view',NULL,'1','2016-03-05 21:39:04','1','2016-03-06 18:14:01','1'),('79c35949da59478c9c9a194432fcb5a3','71f0137ca6004ec98e0bae9e8a12f356','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,71f0137ca6004ec98e0bae9e8a12f356,','资源管理','/../static/ckfinder/ckfinder.html','','',1,'1','0','',NULL,'1','2016-02-20 18:19:22','1','2016-02-27 23:11:07','1'),('7cbfb21dbf2d498eb052f9e11038166e','2bb73aeb7cda45f6b4a55f195eed31ed','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,','广告管理','','','',1,'1','0','',NULL,'1','2016-02-20 17:52:05','1','2016-02-27 23:11:06','1'),('8','7','0,1,2,3,7,','查看',NULL,NULL,NULL,30,'0','0','sys:role:view',NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:33','0'),('84c2df01e12d4e8ca78ae1e66492c5ed','e5ce08fa68b54b4385707ac92f5249dc','0,1,e5ce08fa68b54b4385707ac92f5249dc,','库存管理','/pro/stock/list','','',1,'1','0','pro:stock:view',NULL,'1','2016-03-12 18:09:00','1','2016-03-29 22:19:02','0'),('8996b0d73dbc486ba20d10e3916fcd6c','3dd015f4edde4badaef02cd24660e966','0,1,e957b002d2f347c89e3627f109074e1d,3dd015f4edde4badaef02cd24660e966,','互动统计','/crbt/ring/statisticInteractHistory','','',2,'1','0','',NULL,'1','2016-02-28 20:59:16','1','2016-02-28 20:59:16','1'),('8a219851e2cd4f5584020a1ca3abaad1','63d4d6103da74d47bfaa9bfb8033a21b','0,1,4b32f5715ddb4d5ca6789d13272a8404,63d4d6103da74d47bfaa9bfb8033a21b,','用户查询','/crbt/clientUser','','',1,'1','0','',NULL,'1','2016-02-27 16:57:22','1','2016-02-27 23:11:07','1'),('8be54c6187ea49c690bea6269b3a3828','2bb73aeb7cda45f6b4a55f195eed31ed','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,','场景管理','','','',4,'1','0','',NULL,'1','2016-03-06 18:13:26','1','2016-03-06 18:13:26','1'),('9','7','0,1,2,3,7,','修改',NULL,NULL,NULL,30,'0','0','sys:role:edit',NULL,'1','2013-05-27 00:00:00','1','2016-03-20 22:53:33','0'),('9194b762224f46caad18053945ee04e7','5f1a1ad805b442d7a981a47eb38d4497','0,1,e957b002d2f347c89e3627f109074e1d,5f1a1ad805b442d7a981a47eb38d4497,','活跃量统计','/crbt/clientUser/statisticUser','','',1,'1','0','',NULL,'1','2016-02-28 23:41:06','1','2016-02-28 23:41:06','1'),('96924e6e881c40feb0362eace7a388a9','1c78d4ff3a424904a83f4807d26c0d7f','0,1,e5ce08fa68b54b4385707ac92f5249dc,1c78d4ff3a424904a83f4807d26c0d7f,','编辑','','','',30,'0','0','pro:stockBills:edit',NULL,'1','2016-04-03 22:37:30','1','2016-04-03 22:38:12','0'),('99ecd3de592a49aab046923987c89f3f','04e2936e23524b53802f71155b002555','0,1,fa48b1b2ba5844fdbdb14264b00ca2f2,04e2936e23524b53802f71155b002555,','编辑','','','',1,'0','0','pro:product:edit',NULL,'1','2016-03-12 18:06:43','1','2016-03-20 22:53:31','0'),('9c3de94580c4422a8aa37d000eef5409','4b585d9d938140a3add6e8b8819f950e','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,7cbfb21dbf2d498eb052f9e11038166e,4b585d9d938140a3add6e8b8819f950e,','显示','','','',1,'0','0','crbt:advert:view',NULL,'1','2016-02-20 17:56:51','1','2016-02-27 23:11:06','1'),('9dfe4d2a2b4e41ec8e6c1825f9463a1a','73ceb528363545ca929fd07c3adcfc20','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,8be54c6187ea49c690bea6269b3a3828,73ceb528363545ca929fd07c3adcfc20,','策略配置','','','',1,'0','0','crbt:widget:strategy:edit',NULL,'1','2016-03-05 22:18:32','1','2016-03-06 18:14:01','1'),('9fa8c3ed32e74798afde6548d99eea00','79c35949da59478c9c9a194432fcb5a3','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,71f0137ca6004ec98e0bae9e8a12f356,79c35949da59478c9c9a194432fcb5a3,','上传','','','',3,'0','0','cms:ckfinder:upload',NULL,'1','2016-02-20 18:20:33','1','2016-02-27 23:11:07','1'),('a0aa75c127fa48b6a58363096eb88049','5b74e8e1962844c7a1533f1e7ebe0713','0,1,058b27ecc4d14669a9dc18311b00e7ba,5b74e8e1962844c7a1533f1e7ebe0713,','编辑','','','',1,'0','0','pro:productionPlan:edit',NULL,'1','2016-03-20 22:53:22','1','2016-04-10 11:40:26','0'),('a18fbe1c784d4688aad962da2864159e','5f3520952f85494f834a513c88085b21','0,1,4b32f5715ddb4d5ca6789d13272a8404,e85b0969455a4336b80ffecfb5030c8c,5f3520952f85494f834a513c88085b21,','编辑','','','',2,'0','0','crbt:clientVersion:edit',NULL,'1','2016-02-27 17:44:31','1','2016-02-27 23:11:07','1'),('a1a35137d9e647efa712797e0d282378','84c2df01e12d4e8ca78ae1e66492c5ed','0,1,e5ce08fa68b54b4385707ac92f5249dc,84c2df01e12d4e8ca78ae1e66492c5ed,','编辑','','','',1,'0','0','pro:stock:edit',NULL,'1','2016-03-12 18:09:21','1','2016-03-29 22:19:02','0'),('a598eb21aeec43c2991aa0e3035cac91','8a219851e2cd4f5584020a1ca3abaad1','0,1,4b32f5715ddb4d5ca6789d13272a8404,63d4d6103da74d47bfaa9bfb8033a21b,8a219851e2cd4f5584020a1ca3abaad1,','显示','','','',1,'0','0','crbt:user:view',NULL,'1','2016-02-27 16:57:36','1','2016-02-27 23:11:07','1'),('a63e86129afc4b9ba88dc848099775f8','7cbfb21dbf2d498eb052f9e11038166e','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,7cbfb21dbf2d498eb052f9e11038166e,','分类管理','/crbt/advertCategory','','',1,'1','0','',NULL,'1','2016-02-20 17:52:43','1','2016-02-27 23:11:06','1'),('ad5d0904b9584f1e88a9175da2f493c5','1d282a691a4a448aa5d25ec8194d1c58','0,1,e5ce08fa68b54b4385707ac92f5249dc,1d282a691a4a448aa5d25ec8194d1c58,','编辑','','','',1,'0','0','pro:stockHistory:edit',NULL,'1','2016-03-12 18:10:17','1','2016-03-29 22:19:02','0'),('b22f77ff2b8048bba3994e753cbefc41','058b27ecc4d14669a9dc18311b00e7ba','0,1,058b27ecc4d14669a9dc18311b00e7ba,','生产明细','/pro/productionDetail/list','','',3,'1','0','pro:productionDetail:view',NULL,'1','2016-03-12 18:14:28','1','2016-03-20 22:53:32','0'),('b2eb85ddeb7d4bce91fb3a76d230ef82','4b585d9d938140a3add6e8b8819f950e','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,7cbfb21dbf2d498eb052f9e11038166e,4b585d9d938140a3add6e8b8819f950e,','策略编辑','','','',4,'0','0','crbt:advert:strateg:edit',NULL,'1','2016-02-20 20:56:41','1','2016-02-27 23:11:07','1'),('b536f1b5424d4e39943804a7cf9e946d','058b27ecc4d14669a9dc18311b00e7ba','0,1,058b27ecc4d14669a9dc18311b00e7ba,','生产管理','/pro/production/list','','',2,'1','0','pro:production:view',NULL,'1','2016-03-12 18:13:16','1','2016-03-20 22:53:32','0'),('b96b392440af47109dd9d69e10ab0ec3','bd2434b5fb9642c2ab683197389a4401','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,7cbfb21dbf2d498eb052f9e11038166e,bd2434b5fb9642c2ab683197389a4401,','查看','','','',1,'0','0','crbt:advertPlayHistory:view',NULL,'1','2016-02-27 14:09:19','1','2016-02-27 23:11:07','1'),('bb2315fb81734b848306a65b407d473b','a63e86129afc4b9ba88dc848099775f8','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,7cbfb21dbf2d498eb052f9e11038166e,a63e86129afc4b9ba88dc848099775f8,','显示','','','',1,'0','0','crbt:advertCategory:view',NULL,'1','2016-02-20 17:53:00','1','2016-02-27 23:11:06','1'),('bd1157d6cebc4beda25d9a64474a1b2c','f45716b2cd094195af901db8f244ed74','0,1,fa48b1b2ba5844fdbdb14264b00ca2f2,f45716b2cd094195af901db8f244ed74,','编辑','','','',1,'0','0','pro:productTree:edit',NULL,'1','2016-03-12 18:07:45','1','2016-03-20 22:53:31','0'),('bd2434b5fb9642c2ab683197389a4401','7cbfb21dbf2d498eb052f9e11038166e','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,7cbfb21dbf2d498eb052f9e11038166e,','播放记录','/crbt/advertPlayHistory','','',3,'1','0','',NULL,'1','2016-02-27 14:09:00','1','2016-02-27 23:11:07','1'),('c2c5d82692e84b74aa9319dfe374b1b3','6ec2dec2a20a4e0e83a7b15918e3010d','0,1,058b27ecc4d14669a9dc18311b00e7ba,6ec2dec2a20a4e0e83a7b15918e3010d,','编辑','','','',1,'0','0','pro:productionHistory:edit',NULL,'1','2016-03-12 18:11:41','1','2016-03-20 22:53:32','0'),('c75d0aba638849d3b089a26a208cdecb','79c35949da59478c9c9a194432fcb5a3','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,71f0137ca6004ec98e0bae9e8a12f356,79c35949da59478c9c9a194432fcb5a3,','编辑','','','',2,'0','0','cms:ckfinder:edit',NULL,'1','2016-02-20 18:20:18','1','2016-02-27 23:11:07','1'),('cd5398ba213a4d47bff1c832ede8e255','e957b002d2f347c89e3627f109074e1d','0,1,e957b002d2f347c89e3627f109074e1d,','广告统计','','','',3,'1','0','',NULL,'1','2016-02-27 23:00:32','1','2016-02-27 23:11:07','1'),('cfe505af107446a098b4becee458b1d8','44eaa1e20a1049b2a577575fc46f7ab1','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,119e2a26a0974510ace3a41902098fde,44eaa1e20a1049b2a577575fc46f7ab1,','显示','','','',1,'0','0','crbt:buyHistory:view',NULL,'1','2016-02-27 16:39:02','1','2016-02-27 23:11:07','1'),('d2fabb339955467fb005e2e60b8991f8','4b585d9d938140a3add6e8b8819f950e','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,7cbfb21dbf2d498eb052f9e11038166e,4b585d9d938140a3add6e8b8819f950e,','策略查看','','','',3,'0','0','crbt:advert:strateg:view',NULL,'1','2016-02-20 20:56:23','1','2016-02-27 23:11:07','1'),('e5ce08fa68b54b4385707ac92f5249dc','1','0,1,','库存管理','','','',3,'1','0','',NULL,'1','2016-03-12 18:08:33','1','2016-03-29 22:19:02','0'),('e85b0969455a4336b80ffecfb5030c8c','4b32f5715ddb4d5ca6789d13272a8404','0,1,4b32f5715ddb4d5ca6789d13272a8404,','版本管理','','','',2,'1','0','',NULL,'1','2016-02-27 20:07:10','1','2016-02-27 23:11:07','1'),('e957b002d2f347c89e3627f109074e1d','1','0,1,','分析统计','','','',4,'1','0','',NULL,'1','2016-02-27 22:57:48','1','2016-02-27 23:11:07','1'),('eec274a149184788ba68fbc21e15293f','b536f1b5424d4e39943804a7cf9e946d','0,1,058b27ecc4d14669a9dc18311b00e7ba,b536f1b5424d4e39943804a7cf9e946d,','编辑','','','',1,'0','0','pro:production:edit',NULL,'1','2016-03-12 18:13:39','1','2016-03-20 22:53:32','0'),('f45716b2cd094195af901db8f244ed74','fa48b1b2ba5844fdbdb14264b00ca2f2','0,1,fa48b1b2ba5844fdbdb14264b00ca2f2,','产品树管理','/pro/productTree/list','','',2,'1','0','pro:productTree:view',NULL,'1','2016-03-12 18:07:28','1','2016-03-20 22:53:31','0'),('fa48b1b2ba5844fdbdb14264b00ca2f2','1','0,1,','产品管理','','','',2,'1','0','',NULL,'1','2016-03-12 18:05:33','1','2016-03-20 22:53:31','0'),('fb1bfbf4156c4fd58b55214da830fb26','258e89c12e6c427eaebe3f1b92a119d6','0,1,2bb73aeb7cda45f6b4a55f195eed31ed,119e2a26a0974510ace3a41902098fde,258e89c12e6c427eaebe3f1b92a119d6,','显示','','','',1,'0','0','crbt:interactHistory:view',NULL,'1','2016-02-27 17:13:07','1','2016-02-27 23:11:07','1'),('ffce9ff2f5d948f6a5dc9741a6174b88','3dd015f4edde4badaef02cd24660e966','0,1,e957b002d2f347c89e3627f109074e1d,3dd015f4edde4badaef02cd24660e966,','购买统计','/crbt/ring/statisticBuyHistory','','',1,'1','0','',NULL,'1','2016-02-28 20:58:22','1','2016-02-28 20:58:22','1');

/*Table structure for table `sys_office` */

DROP TABLE IF EXISTS `sys_office`;

CREATE TABLE `sys_office` (
  `id` varchar(255) NOT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `parent_ids` varchar(255) DEFAULT NULL,
  `area_id` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(1) DEFAULT NULL,
  `grade` varchar(1) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL,
  `master` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKE024AC6EE18EEA67` (`create_by`),
  KEY `FKE024AC6E49B6237A` (`update_by`),
  KEY `FKE024AC6E915D2ABC` (`area_id`),
  CONSTRAINT `FKE024AC6E49B6237A` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKE024AC6E915D2ABC` FOREIGN KEY (`area_id`) REFERENCES `sys_area` (`id`),
  CONSTRAINT `FKE024AC6EE18EEA67` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_office` */

insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`area_id`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`remarks`,`create_by`,`create_date`,`update_by`,`update_date`,`del_flag`) values ('1','0','0,','2','100000','北京市总公司','1','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('10','7','0,1,7,','8','200003','市场部','2','2',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('11','7','0,1,7,','8','200004','技术部','2','2',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('12','7','0,1,7,','9','201000','济南市分公司','1','3',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('13','12','0,1,7,12,','9','201001','公司领导','2','3',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('14','12','0,1,7,12,','9','201002','综合部','2','3',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('15','12','0,1,7,12,','9','201003','市场部','2','3',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('16','12','0,1,7,12,','9','201004','技术部','2','3',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('17','12','0,1,7,12,','11','201010','济南市历城区分公司','1','4',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('18','17','0,1,7,12,17,','11','201011','公司领导','2','4',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('19','17','0,1,7,12,17,','11','201012','综合部','2','4',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('2','1','0,1,','2','100001','公司领导','2','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('20','17','0,1,7,12,17,','11','201013','市场部','2','4',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('21','17','0,1,7,12,17,','11','201014','技术部','2','4',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('22','12','0,1,7,12,','12','201020','济南市历下区分公司','1','4',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('23','22','0,1,7,12,22,','12','201021','公司领导','2','4',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('24','22','0,1,7,12,22,','12','201022','综合部','2','4',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('25','22','0,1,7,12,22,','12','201023','市场部','2','4',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('26','22','0,1,7,12,22,','12','201024','技术部','2','4',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('3','1','0,1,','2','100002','人力部','2','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('4','1','0,1,','2','100003','市场部','2','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('5','1','0,1,','2','100004','技术部','2','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('6','1','0,1,','2','100005','研发部','2','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('7','1','0,1,','8','200000','山东省分公司','1','2',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('8','7','0,1,7,','8','200001','公司领导','2','2',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0'),('9','7','0,1,7,','8','200002','综合部','2','2',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` varchar(255) NOT NULL,
  `office_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `data_scope` char(1) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK74A6B2A8E18EEA67` (`create_by`),
  KEY `FK74A6B2A849B6237A` (`update_by`),
  KEY `FK74A6B2A845EC47DC` (`office_id`),
  CONSTRAINT `FK74A6B2A845EC47DC` FOREIGN KEY (`office_id`) REFERENCES `sys_office` (`id`),
  CONSTRAINT `FK74A6B2A849B6237A` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FK74A6B2A8E18EEA67` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`office_id`,`name`,`data_scope`,`remarks`,`create_by`,`create_date`,`update_by`,`update_date`,`del_flag`) values ('1','1','系统管理员','1',NULL,'1','2013-05-27 00:00:00','1','2013-05-27 00:00:00','0');

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` varchar(255) DEFAULT NULL,
  `menu_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`role_id`,`menu_id`) values ('1','1'),('1','2'),('1','3'),('1','4'),('1','5'),('1','6'),('1','7'),('1','8'),('1','9'),('1','10'),('1','11'),('1','12'),('1','13'),('1','14'),('1','15'),('1','16'),('1','17'),('1','18'),('1','19'),('1','20'),('1','21'),('1','22'),('1','23'),('1','24'),('1','25'),('1','26'),('1','27'),('1','28'),('1','29'),('1','30'),('1','31'),('1','32'),('1','33'),('1','34'),('1','35'),('1','36'),('1','37'),('1','38'),('1','39'),('1','40'),('1','41'),('1','42'),('1','43'),('1','44'),('1','45'),('1','46'),('1','47'),('1','48'),('1','49'),('1','50'),('1','51'),('1','52'),('1','53'),('1','54'),('1','55'),('1','56'),('1','57'),('1','58'),('1','59'),('2','1'),('2','2'),('2','3'),('2','4'),('2','5'),('2','6'),('2','7'),('2','8'),('2','9'),('2','10'),('2','11'),('2','12'),('2','13'),('2','14'),('2','15'),('2','16'),('2','17'),('2','18'),('2','19'),('2','20'),('2','21'),('2','22'),('2','23'),('2','24'),('2','25'),('2','26'),('2','27'),('2','28'),('2','29'),('2','30'),('2','31'),('2','32'),('2','33'),('2','34'),('2','35'),('2','36'),('2','37'),('2','38'),('2','39'),('2','40'),('2','41'),('2','42'),('2','43'),('2','44'),('2','45'),('2','46'),('2','47'),('2','48'),('2','49'),('2','50'),('2','51'),('2','52'),('2','53'),('2','54'),('2','55'),('2','56'),('2','57'),('2','58'),('2','59'),('3','1'),('3','2'),('3','3'),('3','4'),('3','5'),('3','6'),('3','7'),('3','8'),('3','9'),('3','10'),('3','11'),('3','12'),('3','13'),('3','14'),('3','15'),('3','16'),('3','17'),('3','18'),('3','19'),('3','20'),('3','21'),('3','22'),('3','23'),('3','24'),('3','25'),('3','26'),('3','27'),('3','28'),('3','29'),('3','30'),('3','31'),('3','32'),('3','33'),('3','34'),('3','35'),('3','36'),('3','37'),('3','38'),('3','39'),('3','40'),('3','41'),('3','42'),('3','43'),('3','44'),('3','45'),('3','46'),('3','47'),('3','48'),('3','49'),('3','50'),('3','51'),('3','52'),('3','53'),('3','54'),('3','55'),('3','56'),('3','57'),('3','58'),('3','59'),('4','1'),('4','2'),('4','3'),('4','4'),('4','5'),('4','6'),('4','7'),('4','8'),('4','9'),('4','10'),('4','11'),('4','12'),('4','13'),('4','14'),('4','15'),('4','16'),('4','17'),('4','18'),('4','19'),('4','20'),('4','21'),('4','22'),('4','23'),('4','24'),('4','25'),('4','26'),('4','27'),('4','28'),('4','29'),('4','30'),('4','31'),('4','32'),('4','33'),('4','34'),('4','35'),('4','36'),('4','37'),('4','38'),('4','39'),('4','40'),('4','41'),('4','42'),('4','43'),('4','44'),('4','45'),('4','46'),('4','47'),('4','48'),('4','49'),('4','50'),('4','51'),('4','52'),('4','53'),('4','54'),('4','55'),('4','56'),('4','57'),('4','58'),('4','59'),('5','1'),('5','2'),('5','3'),('5','4'),('5','5'),('5','6'),('5','7'),('5','8'),('5','9'),('5','10'),('5','11'),('5','12'),('5','13'),('5','14'),('5','15'),('5','16'),('5','17'),('5','18'),('5','19'),('5','20'),('5','21'),('5','22'),('5','23'),('5','24'),('5','25'),('5','26'),('5','27'),('5','28'),('5','29'),('5','30'),('5','31'),('5','32'),('5','33'),('5','34'),('5','35'),('5','36'),('5','37'),('5','38'),('5','39'),('5','40'),('5','41'),('5','42'),('5','43'),('5','44'),('5','45'),('5','46'),('5','47'),('5','48'),('5','49'),('5','50'),('5','51'),('5','52'),('5','53'),('5','54'),('5','55'),('5','56'),('5','57'),('5','58'),('5','59'),('6','1'),('6','2'),('6','3'),('6','4'),('6','5'),('6','6'),('6','7'),('6','8'),('6','9'),('6','10'),('6','11'),('6','12'),('6','13'),('6','14'),('6','15'),('6','16'),('6','17'),('6','18'),('6','19'),('6','20'),('6','21'),('6','22'),('6','23'),('6','24'),('6','25'),('6','26'),('6','27'),('6','28'),('6','29'),('6','30'),('6','31'),('6','32'),('6','33'),('6','34'),('6','35'),('6','36'),('6','37'),('6','38'),('6','39'),('6','40'),('6','41'),('6','42'),('6','43'),('6','44'),('6','45'),('6','46'),('6','47'),('6','48'),('6','49'),('6','50'),('6','51'),('6','52'),('6','53'),('6','54'),('6','55'),('6','56'),('6','57'),('6','58'),('6','59'),('7','1'),('7','2'),('7','3'),('7','4'),('7','5'),('7','6'),('7','7'),('7','8'),('7','9'),('7','10'),('7','11'),('7','12'),('7','13'),('7','14'),('7','15'),('7','16'),('7','17'),('7','18'),('7','19'),('7','20'),('7','21'),('7','22'),('7','23'),('7','24'),('7','25'),('7','26'),('7','27'),('7','28'),('7','29'),('7','30'),('7','31'),('7','32'),('7','33'),('7','34'),('7','35'),('7','36'),('7','37'),('7','38'),('7','39'),('7','40'),('7','41'),('7','42'),('7','43'),('7','44'),('7','45'),('7','46'),('7','47'),('7','48'),('7','49'),('7','50'),('7','51'),('7','52'),('7','53'),('7','54'),('7','55'),('7','56'),('7','57'),('7','58'),('7','59'),('1','60'),('1','61'),('1','62'),('1','63'),('1','64'),('1','65'),('1','66'),('2','60'),('2','61'),('2','62'),('2','63'),('2','64'),('2','65'),('2','66'),('3','60'),('3','61'),('3','62'),('3','63'),('3','64'),('3','65'),('3','66'),('4','60'),('4','61'),('4','62'),('4','63'),('4','64'),('4','65'),('4','66'),('5','60'),('5','61'),('5','62'),('5','63'),('5','64'),('5','65'),('5','66'),('6','60'),('6','61'),('6','62'),('6','63'),('6','64'),('6','65'),('6','66'),('7','60'),('7','61'),('7','62'),('7','63'),('7','64'),('7','65'),('7','66'),('1','67'),('1','68'),('2','67'),('2','68'),('3','67'),('3','68'),('4','67'),('4','68'),('5','67'),('5','68'),('6','67'),('6','68'),('7','67'),('7','68'),('1','69'),('2','69'),('3','69'),('4','69'),('5','69'),('6','69'),('7','69'),('1','70'),('2','70'),('3','70'),('4','70'),('5','70'),('6','70'),('7','70'),('1','71'),('1','72');

/*Table structure for table `sys_role_office` */

DROP TABLE IF EXISTS `sys_role_office`;

CREATE TABLE `sys_role_office` (
  `role_id` varchar(255) DEFAULT NULL,
  `office_id` varchar(255) DEFAULT NULL,
  KEY `FK4639BC3345EC47DC` (`office_id`),
  KEY `FK4639BC33FF4B61C` (`role_id`),
  CONSTRAINT `FK4639BC3345EC47DC` FOREIGN KEY (`office_id`) REFERENCES `sys_office` (`id`),
  CONSTRAINT `FK4639BC33FF4B61C` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_office` */

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` varchar(255) NOT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  `office_id` varchar(255) DEFAULT NULL,
  `login_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `no` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `user_type` char(1) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  `login_date` datetime DEFAULT NULL,
  `login_ip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK74A81DFDE18EEA67` (`create_by`),
  KEY `FK74A81DFD49B6237A` (`update_by`),
  KEY `FK74A81DFD9976E67B` (`company_id`),
  KEY `FK74A81DFD45EC47DC` (`office_id`),
  CONSTRAINT `FK74A81DFD45EC47DC` FOREIGN KEY (`office_id`) REFERENCES `sys_office` (`id`),
  CONSTRAINT `FK74A81DFD49B6237A` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FK74A81DFD9976E67B` FOREIGN KEY (`company_id`) REFERENCES `sys_office` (`id`),
  CONSTRAINT `FK74A81DFDE18EEA67` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`company_id`,`office_id`,`login_name`,`password`,`no`,`name`,`email`,`phone`,`mobile`,`user_type`,`remarks`,`create_by`,`create_date`,`update_by`,`update_date`,`del_flag`,`login_date`,`login_ip`) values ('1','1','1','thinkgem','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','0001','Thinkgem','thinkgem@163.com','8675','8675',NULL,'最高管理员s','1','2013-05-27 00:00:00','1','2016-03-06 17:01:45','0','2016-04-10 17:58:32','0:0:0:0:0:0:0:1');

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` double DEFAULT NULL,
  `role_id` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`user_id`,`role_id`) values (1,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
