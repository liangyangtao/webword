-- ----------------------------
-- 更新的数据库sql
-- ----------------------------
ALTER TABLE `word_plate`
MODIFY COLUMN `conditions`  varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '搜索的条件' AFTER `user_id`;

ALTER TABLE `word_column`
MODIFY COLUMN `conditions`  varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '搜索的条件' AFTER `user_id`;

ALTER TABLE `know_article`
MODIFY COLUMN `article_format`  enum('ppt','pdf','pptx','docx','doc') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'doc' AFTER `article_save`;


ALTER TABLE `word_journal`
ADD COLUMN `have_data`  varchar(1) NULL COMMENT '状态默认0，有报告后为1' AFTER `user_id`,
ADD COLUMN `update_time`  datetime NULL COMMENT '期刊增加报告时间' AFTER `have_data`;

CREATE TABLE `company_group_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `companygroupid` int(11) NOT NULL DEFAULT '0' COMMENT '企业组id',
  `companyid` int(11) NOT NULL DEFAULT '0' COMMENT '企业id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=utf8;

CREATE TABLE `riskcompany` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '企业编号',
  `companyname` varchar(200) NOT NULL COMMENT '企业组名称',
  `companynumber` varchar(30) DEFAULT NULL COMMENT '备用',
  `admin` int(11) DEFAULT '0' COMMENT '添加人',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `spareint` int(11) DEFAULT '0' COMMENT '备用整型',
  `sparevar` varchar(200) DEFAULT NULL COMMENT '备用var',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

CREATE TABLE `user_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL COMMENT '用户id',
  `companygroupid` int(11) NOT NULL DEFAULT '0' COMMENT '企业组id',
  `adminid` int(11) DEFAULT '0' COMMENT '授权人id',
  `admindate` datetime DEFAULT NULL COMMENT '授权时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

ALTER TABLE `word_article_count`
MODIFY COLUMN `down_count`  int(11) NULL DEFAULT 0 COMMENT '下载的次数' AFTER `article_id`,
MODIFY COLUMN `view_count`  int(11) NULL DEFAULT 0 COMMENT '预览的次数' AFTER `down_count`;


创享平台3.0-

DROP TABLE IF EXISTS `word_journal`;
CREATE TABLE `word_journal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '期刊名字',
  `skip` varchar(255) NOT NULL COMMENT '简介',
  `label` varchar(255) NOT NULL COMMENT '标签',
  `keyword` varchar(255) NOT NULL COMMENT '关键词',
  `cover` varchar(255) NOT NULL COMMENT '封面',
  `type` varchar(255) DEFAULT NULL COMMENT '期刊类型',
  `type_id` int(11) NOT NULL DEFAULT '0' COMMENT '期刊类型',
  `pass_type` enum('FAILED','PASSED','SUBMITTED','SAVED') DEFAULT NULL COMMENT 'SAVED:私有保存,SUBMITTED:审核中,PASSED:审核成功,FAILED:审核失败',
  `es_id` varchar(255) DEFAULT NULL COMMENT '生成的esId',
  `pass_userId` int(11) DEFAULT NULL COMMENT '审核人',
  `pass_time` datetime DEFAULT NULL COMMENT '审核日期',
  `submit_time` datetime DEFAULT NULL COMMENT '提交审核时间',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '建立的时间',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `word_journal_type`;
CREATE TABLE `word_journal_type` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `type` varchar(255) DEFAULT NULL COMMENT '类型',
  `order_id` int(11) DEFAULT NULL,
  `des` varchar(255) DEFAULT NULL COMMENT '描述',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of word_journal_type
-- ----------------------------
INSERT INTO `word_journal_type` VALUES ('1', '日刊', 'day', '1', '2015-08-16', '2015-10-27 11:43:40');
INSERT INTO `word_journal_type` VALUES ('2', '周刊', 'week', '2', '2015-08-1周', '2015-10-27 11:43:51');
INSERT INTO `word_journal_type` VALUES ('3', '半月刊', 'halfMonths', '3', '2015-08上', '2015-10-27 11:43:59');
INSERT INTO `word_journal_type` VALUES ('4', '月刊', 'months', '4', '2015-08', '2015-10-27 11:44:09');
INSERT INTO `word_journal_type` VALUES ('5', '双月刊', 'twoMonths', '5', '2015-08', '2015-10-27 11:44:20');
INSERT INTO `word_journal_type` VALUES ('6', '季刊', 'season', '6', '2015-1季度', '2015-10-27 11:44:28');
INSERT INTO `word_journal_type` VALUES ('7', '半年刊', 'halfYear', '7', '2015上半年', '2015-10-27 11:44:36');
INSERT INTO `word_journal_type` VALUES ('8', '年刊', 'year', '8', '2015年', '2015-10-27 11:44:39');

DROP TABLE IF EXISTS `word_article_count`;
CREATE TABLE `word_article_count` (
  `id` int(11) NOT NULL,
  `article_id` int(11) DEFAULT NULL COMMENT '文档ID',
  `down_count` int(11) DEFAULT NULL COMMENT '下载的次数',
  `view_count` int(11) DEFAULT NULL COMMENT '预览的次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `word_column`;
CREATE TABLE `word_column` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT '0' COMMENT '父id',
  `column_name` varchar(255) DEFAULT NULL COMMENT '栏目名字',
  `user_id` int(11) DEFAULT NULL COMMENT '建立的人',
  `conditions` varchar(2000) DEFAULT NULL COMMENT '搜索的条件',
  `insert_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '建立的时间',
  `type_id` int(11) DEFAULT NULL COMMENT '栏目类型',
  `del_list` varchar(255) DEFAULT NULL COMMENT '删除的id',
  `top_list` varchar(255) DEFAULT NULL COMMENT '置顶id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `word_column_group`;
CREATE TABLE `word_column_group` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '栏目名字',
  `user_id` int(11) DEFAULT NULL COMMENT '建立人',
  `type_id` int(11) DEFAULT NULL COMMENT '栏目组类型',
  `inser_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '建立时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `word_column_contact`;
CREATE TABLE `word_column_contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) DEFAULT NULL COMMENT '组的id',
  `column_id` int(11) DEFAULT NULL COMMENT '栏目id',
  `order_id` int(11) DEFAULT '1' COMMENT '排序id',
  `insert_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '建立时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `word_column_type`;
CREATE TABLE `word_column_type` (
  `id` int(11) NOT NULL DEFAULT '0',
  `tname` varchar(255) DEFAULT NULL COMMENT '类型名字',
  `insert_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of word_column_type
-- ----------------------------
INSERT INTO `word_column_type` VALUES ('1', '首页左侧', '2015-10-29 14:46:32');
INSERT INTO `word_column_type` VALUES ('2', '首页中间', '2015-10-29 14:46:45');
INSERT INTO `word_column_type` VALUES ('3', '热点栏目', '2015-10-29 14:47:04');
INSERT INTO `word_column_type` VALUES ('4', '测试类型', '2015-11-12 10:55:14');



ALTER TABLE `word_plate`
ADD COLUMN `column_id`  int NULL DEFAULT 0 COMMENT '栏目id' AFTER `pid`;
ADD COLUMN `top_list`  varchar(255) NULL DEFAULT NULL COMMENT '置顶期刊id' AFTER `plate_other`,
ADD COLUMN `del_list`  varchar(255) NULL DEFAULT NULL COMMENT '删除期刊id' AFTER `top_list`;

ADD COLUMN `top_list`  varchar(255) NULL DEFAULT NULL COMMENT '置顶id' AFTER `plate_other`,
ADD COLUMN `del_list`  varchar(255) NULL DEFAULT NULL COMMENT '删除id' AFTER `top_list`;

ALTER TABLE `know_article`
ADD COLUMN `article_save`  enum('write','upload') NULL DEFAULT 'write' COMMENT 'write:编写 upload:' AFTER `doc_path`,
ADD COLUMN `article_format`  enum('ppt','pdf','doc') NULL DEFAULT 'doc' AFTER `article_save`,
ADD COLUMN `article_journal_id`  int NULL DEFAULT 0 COMMENT '期刊id' AFTER `article_format`,
ADD COLUMN `article_journal_name`  varchar(255) NULL AFTER `article_journal_id`,
ADD COLUMN `article_journal_time`  datetime NULL DEFAULT NULL AFTER `article_journal_name`;



#增加日志输出
ALTER TABLE `verifydoc_log`
ADD COLUMN `esId`  varchar(255) NULL COMMENT '索引Id' AFTER `useFlag`,
ADD COLUMN `reviewFlag`  int UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '默认0,1:复审不通过' AFTER `esId`,
ADD COLUMN `userCount`  int ZEROFILL NULL DEFAULT 0 COMMENT '新闻使用次数，默认0；' AFTER `reviewFlag`;

ALTER TABLE `verifydoc_log`
ADD INDEX `esId` (`esId`) USING BTREE ;

#将"F:\\tomcat8080" 改成 "D:\\service\\tomcat8080"

update know_homepage_property  set basepicpath = replace(basepicpath,"F\:\\tomcat8080","D\:\\service\\tomcat8080") 

update know_homepage_property  set savepicpath = replace(savepicpath,"F\:\\tomcat8080","D\:\\service\\tomcat8080") 
#where homepage_property_id=1670;

select * from verifydoc_log where esId="AVBpEFl52dqbBBtATVVQ"

update verifydoc_log set userCount=userCount+1 where esId="AVBpEFl52dqbBBtATVVQ"