# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.6.34)
# Database: lncbbs
# Generation Time: 2017-01-09 16:09:42 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table article_body
# ------------------------------------------------------------

DROP TABLE IF EXISTS `article_body`;

CREATE TABLE `article_body` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext NOT NULL,
  `latestModifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

LOCK TABLES `article_body` WRITE;
/*!40000 ALTER TABLE `article_body` DISABLE KEYS */;

INSERT INTO `article_body` (`id`, `content`, `latestModifiedDate`)
VALUES
	(1,'The first article of this website!',NULL),
	(2,'I need some html chars... <>,d,.s,,d.sa,d.-=*;;++++<!!!--- ',NULL),
	(3,'I am so sleepy that....\r\n\r\ner......\r\n\r\n\r\n?????\r\n\r\n\r\n<!-- Hahahahahhahaha -->',NULL),
	(4,'The forth article',NULL),
	(5,'The Fifth article',NULL),
	(6,'The sixth article',NULL),
	(7,'The seventh article',NULL),
	(8,'The eighth article',NULL),
	(9,'The ninth article',NULL),
	(10,'The tenth article',NULL),
	(11,'Fooooooooo',NULL),
	(12,'Fooooooooo',NULL),
	(13,'Fooooooooo',NULL),
	(14,'Fooooooooo',NULL),
	(15,'Fooooooooo',NULL),
	(16,'Fooooooooo',NULL),
	(17,'Fooooooooo',NULL),
	(18,'Fooooooooo',NULL),
	(19,'Fooooooooo',NULL),
	(20,'Fooooooooo',NULL),
	(21,'Fooooooooo',NULL),
	(22,'Fooooooooo',NULL),
	(23,'Fooooooooo',NULL),
	(24,'Fooooooooo',NULL),
	(25,'Fooooooooo',NULL),
	(26,'set two topics to this article',NULL),
	(27,'set two topics to this article',NULL),
	(28,'set two topics to this article',NULL),
	(29,'Test Hello Topic',NULL);

/*!40000 ALTER TABLE `article_body` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table article_head
# ------------------------------------------------------------

DROP TABLE IF EXISTS `article_head`;

CREATE TABLE `article_head` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createDate` datetime NOT NULL,
  `status` varchar(15) NOT NULL,
  `subtitle` varchar(255) DEFAULT NULL,
  `title` varchar(128) NOT NULL,
  `author_id` int(11) DEFAULT NULL,
  `body_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqfvyj4eqxyt1f7rc18x0ng3ou` (`author_id`),
  KEY `FKfd0g13a1qkuisslxjrtkj3hg2` (`body_id`),
  CONSTRAINT `FKfd0g13a1qkuisslxjrtkj3hg2` FOREIGN KEY (`body_id`) REFERENCES `article_body` (`id`),
  CONSTRAINT `FKqfvyj4eqxyt1f7rc18x0ng3ou` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

LOCK TABLES `article_head` WRITE;
/*!40000 ALTER TABLE `article_head` DISABLE KEYS */;

INSERT INTO `article_head` (`id`, `createDate`, `status`, `subtitle`, `title`, `author_id`, `body_id`)
VALUES
	(1,'2017-01-01 01:59:50','published','The first article of this website!','Hello !',1,1),
	(2,'2017-01-01 02:09:02','published','The second article of this website','Second article',1,2),
	(3,'2017-01-01 02:10:42','published','The third article of this website...','The third article',1,3),
	(4,'2017-01-01 07:45:22','published','The forth article','The forth article',1,4),
	(5,'2017-01-01 07:45:36','published','The Fifth article','The Fifth article',1,5),
	(6,'2017-01-01 07:45:49','published','The sixth article','The sixth article',1,6),
	(7,'2017-01-01 07:46:01','published','The seventh article','The seventh article',1,7),
	(8,'2017-01-01 07:46:16','published','The eighth article','The eighth article',1,8),
	(9,'2017-01-01 07:46:27','published','The ninth article','The ninth article',1,9),
	(10,'2017-01-01 07:46:44','published','The tenth article','The tenth article',1,10),
	(11,'2017-01-01 07:46:53','published','Fooooooooo','Fooooooooo',1,11),
	(12,'2017-01-01 07:47:02','published','Fooooooooo','Fooooooooo',1,12),
	(13,'2017-01-01 07:47:08','published','Fooooooooo','Fooooooooo',1,13),
	(14,'2017-01-01 07:47:13','published','Fooooooooo','Fooooooooo',1,14),
	(15,'2017-01-01 07:47:37','published','Fooooooooo','Fooooooooo',1,15),
	(16,'2017-01-01 07:47:42','published','Fooooooooo','Fooooooooo',1,16),
	(17,'2017-01-01 07:47:46','published','Fooooooooo','Fooooooooo',1,17),
	(18,'2017-01-01 07:48:05','published','Fooooooooo','Fooooooooo',1,18),
	(19,'2017-01-01 07:48:10','published','Fooooooooo','Fooooooooo',1,19),
	(20,'2017-01-01 07:48:14','published','Fooooooooo','Fooooooooo',1,20),
	(21,'2017-01-01 07:48:19','published','FoooooooooFooooooooo','Fooooooooo',1,21),
	(22,'2017-01-01 07:48:25','published','FoooooooooFooooooooo','Fooooooooo',1,22),
	(23,'2017-01-01 07:48:40','published','Fooooooooo','Fooooooooo',1,23),
	(24,'2017-01-01 07:48:45','published','Fooooooooo','Fooooooooo',1,24),
	(25,'2017-01-01 07:48:50','published','FoooooooooFooooooooo','Fooooooooo',1,25),
	(27,'2017-01-07 19:31:03','published','Try to add topic to this article','Try to set topic',1,27),
	(28,'2017-01-07 19:33:55','published','Try to add topic to this article','Try to set topic',1,28),
	(29,'2017-01-08 00:20:25','published','Test Hello Topic','Hello!',1,29);

/*!40000 ALTER TABLE `article_head` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table article_head_topics
# ------------------------------------------------------------

DROP TABLE IF EXISTS `article_head_topics`;

CREATE TABLE `article_head_topics` (
  `Article_id` int(11) NOT NULL,
  `topics_id` int(11) NOT NULL,
  PRIMARY KEY (`Article_id`,`topics_id`),
  KEY `FKdr7ps069iav0323x90y9039tt` (`topics_id`),
  CONSTRAINT `FKdr7ps069iav0323x90y9039tt` FOREIGN KEY (`topics_id`) REFERENCES `topics` (`id`),
  CONSTRAINT `FKle7vrontgtgpyu5vayyny5idg` FOREIGN KEY (`Article_id`) REFERENCES `article_head` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `article_head_topics` WRITE;
/*!40000 ALTER TABLE `article_head_topics` DISABLE KEYS */;

INSERT INTO `article_head_topics` (`Article_id`, `topics_id`)
VALUES
	(28,1),
	(28,2),
	(29,2),
	(29,3);

/*!40000 ALTER TABLE `article_head_topics` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table bulletins
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bulletins`;

CREATE TABLE `bulletins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `imageLink` varchar(255) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `type` varchar(15) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL,
  `periodOfValidity` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr0iugwlu46x0hkmxdeiuf5sy` (`author_id`),
  CONSTRAINT `FKr0iugwlu46x0hkmxdeiuf5sy` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

LOCK TABLES `bulletins` WRITE;
/*!40000 ALTER TABLE `bulletins` DISABLE KEYS */;

INSERT INTO `bulletins` (`id`, `content`, `createDate`, `imageLink`, `link`, `type`, `author_id`, `periodOfValidity`)
VALUES
	(1,'Hello a bulletins','2017-01-01 03:17:23','','','Homepage',1,NULL),
	(2,'My headpic','2017-01-01 03:26:12','http://tva1.sinaimg.cn/crop.0.0.864.864.180/64b74a30jw8f0cz1knmhuj20o00o275o.jpg','','Hello',1,NULL),
	(3,'My Weibo','2017-01-03 04:49:23','http://tva1.sinaimg.cn/crop.0.0.864.864.180/64b74a30jw8f0cz1knmhuj20o00o275o.jpg','http://weibo.com/lingerkong','Hello',1,NULL),
	(4,'Some Hello Content','2017-01-05 17:59:26','','','Hello',1,NULL),
	(5,'Hello again','2017-01-07 03:05:41','','','Hello',1,'2017-11-07 00:00:21'),
	(6,'So Hello again?','2017-01-07 03:07:08','','','Hello',1,'2017-01-07 04:39:04');

/*!40000 ALTER TABLE `bulletins` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table commits
# ------------------------------------------------------------

DROP TABLE IF EXISTS `commits`;

CREATE TABLE `commits` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contents` varchar(255) NOT NULL,
  `date` datetime DEFAULT NULL,
  `replyTo_id` int(11) DEFAULT NULL,
  `targetArticle_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmfa04t3l1eak012jrcgyffkvf` (`replyTo_id`),
  KEY `FKiutdi2nj6w032rdla53rgw6mx` (`targetArticle_id`),
  KEY `FKfa4u16dveycc80e31og6lsq77` (`user_id`),
  CONSTRAINT `FKfa4u16dveycc80e31og6lsq77` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKiutdi2nj6w032rdla53rgw6mx` FOREIGN KEY (`targetArticle_id`) REFERENCES `article_head` (`id`),
  CONSTRAINT `FKmfa04t3l1eak012jrcgyffkvf` FOREIGN KEY (`replyTo_id`) REFERENCES `commits` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table permissions
# ------------------------------------------------------------

DROP TABLE IF EXISTS `permissions`;

CREATE TABLE `permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enable` bit(1) DEFAULT NULL,
  `negative` bit(1) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `uri` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table roles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enable` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table roles_permissions
# ------------------------------------------------------------

DROP TABLE IF EXISTS `roles_permissions`;

CREATE TABLE `roles_permissions` (
  `Role_id` int(11) NOT NULL,
  `permissions_id` int(11) NOT NULL,
  PRIMARY KEY (`Role_id`,`permissions_id`),
  UNIQUE KEY `UK_oll9subcln0cdjt31bp72a3uv` (`permissions_id`),
  CONSTRAINT `FK570wuy6sacdnrw8wdqjfh7j0q` FOREIGN KEY (`permissions_id`) REFERENCES `permissions` (`id`),
  CONSTRAINT `FKdvdpcorrmepai1o6nm0udvclh` FOREIGN KEY (`Role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table topics
# ------------------------------------------------------------

DROP TABLE IF EXISTS `topics`;

CREATE TABLE `topics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `creator_id` int(11) DEFAULT NULL,
  `weight` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_71rjsqaorlydivvwh8xgousre` (`title`),
  KEY `FK39iycweywrsj2dvnhl0uq5lnl` (`creator_id`),
  CONSTRAINT `FK39iycweywrsj2dvnhl0uq5lnl` FOREIGN KEY (`creator_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

LOCK TABLES `topics` WRITE;
/*!40000 ALTER TABLE `topics` DISABLE KEYS */;

INSERT INTO `topics` (`id`, `title`, `createDate`, `creator_id`, `weight`)
VALUES
	(1,'New Topic 1',NULL,NULL,NULL),
	(2,'Another Topic','2017-01-07 02:52:57',1,1000),
	(3,'Hello!','2017-01-08 00:19:13',1,1000);

/*!40000 ALTER TABLE `topics` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_profiles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_profiles`;

CREATE TABLE `user_profiles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admissionDate` datetime DEFAULT NULL,
  `contactingInfo` varchar(255) DEFAULT NULL,
  `educationalInfo` varchar(255) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `headPic` varchar(255) DEFAULT NULL,
  `nickname` varchar(32) DEFAULT NULL,
  `secret` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

LOCK TABLES `user_profiles` WRITE;
/*!40000 ALTER TABLE `user_profiles` DISABLE KEYS */;

INSERT INTO `user_profiles` (`id`, `admissionDate`, `contactingInfo`, `educationalInfo`, `gender`, `headPic`, `nickname`, `secret`)
VALUES
	(1,NULL,NULL,NULL,'male','','',b'0'),
	(2,NULL,NULL,NULL,'male','http://tva1.sinaimg.cn/crop.0.0.864.864.180/64b74a30jw8f0cz1knmhuj20o00o275o.jpg','阪本先生',b'0');

/*!40000 ALTER TABLE `user_profiles` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `registerDate` date DEFAULT NULL,
  `profile_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9ni9y01cgm4kt2lp4d8smxm45` (`profile_id`),
  CONSTRAINT `FK9ni9y01cgm4kt2lp4d8smxm45` FOREIGN KEY (`profile_id`) REFERENCES `user_profiles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;

INSERT INTO `users` (`id`, `name`, `password`, `registerDate`, `profile_id`)
VALUES
	(1,'CattenLinger','000000','2017-01-01',2),
	(2,'xiaobai','000000','2017-01-08',1),
	(3,'SnipyBHY','000000','2017-01-09',NULL);

/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table users_roles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `users_id` int(11) NOT NULL,
  `roles_id` int(11) NOT NULL,
  PRIMARY KEY (`users_id`,`roles_id`),
  KEY `FKa62j07k5mhgifpp955h37ponj` (`roles_id`),
  CONSTRAINT `FKa62j07k5mhgifpp955h37ponj` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKml90kef4w2jy7oxyqv742tsfc` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
