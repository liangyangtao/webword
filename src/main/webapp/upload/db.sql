DROP TABLE IF EXISTS `know_contentplugin`;
CREATE TABLE `know_contentplugin` (
  `contentplugin_id` int(11) NOT NULL AUTO_INCREMENT,
  `contentplugin_name` varchar(255) DEFAULT NULL,
  `contentplugin_nodeid` int(11) DEFAULT NULL,
  `contentplugin_articleid` int(11) DEFAULT NULL,
  `contentplugin_userid` int(11) DEFAULT NULL,
  `contentplugin_insertdate` date DEFAULT NULL COMMENT '内容插件名称',
  `contentplugin_must` varchar(255) DEFAULT NULL COMMENT '必须包含',
  `contentplugin_arbitrarily` varchar(255) DEFAULT NULL COMMENT '包含任意',
  `contentplugin_not` varchar(255) DEFAULT NULL COMMENT '不包含',
  `contentplugin_searchsource` int(11) DEFAULT NULL COMMENT '搜索来源1:全文 2:标题',
  `contentplugin_sort` int(11) DEFAULT NULL COMMENT '排序方式 1:时间 2 权重',
  `contentplugin_fromsource` varchar(255) DEFAULT NULL COMMENT '新闻来源',
  `contentplugin_pagesize` int(11) DEFAULT '1' COMMENT '返回记录',
  `contentplugin_searchcondition` text,
  `contentplugin_starttime` date DEFAULT NULL COMMENT '开始时间',
  `contentplugin_endtime` date DEFAULT NULL COMMENT '结束时间',
  `contentplugin_repeatflag` int(10) unsigned zerofill DEFAULT '0000000000' COMMENT '1:自动执行',
  `contentplugin_repeattime` int(11) DEFAULT '1' COMMENT '执行周期 ',
  `contentplugin_updatetime` date DEFAULT NULL COMMENT '执行的时间',
  `contentplugin_inserttime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '自动更新时间',
  PRIMARY KEY (`contentplugin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=255 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `know_article`;
CREATE TABLE `know_article` (
  `article_id` int(11) NOT NULL AUTO_INCREMENT,
  `savestatus` int(11) DEFAULT '0',
  `article_name` varchar(200) DEFAULT '新建文档',
  `article_type` enum('template','document') DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `pass_type` enum('SAVED','SUBMITTED','FAILED','HANDLED','PASSED') DEFAULT NULL,
  `pass_time` datetime DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `article_skip` varchar(500) DEFAULT NULL,
  `pass_user` int(11) DEFAULT NULL,
  `down_time` datetime DEFAULT NULL,
  `attachment_path` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=461 DEFAULT CHARSET=utf8 COMMENT='文章表';

DROP TABLE IF EXISTS `know_whole`;
CREATE TABLE `know_whole` (
  `whole_id` int(11) NOT NULL AUTO_INCREMENT,
  `node_id` int(11) DEFAULT NULL,
  `node_content` longtext,
  `article_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`whole_id`),
  KEY `fk_know_whole_know_node1_idx` (`node_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8 COMMENT='1.9	节点内容表';