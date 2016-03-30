
ALTER TABLE `know_article`
ADD COLUMN `content_status`  int NULL DEFAULT 0 COMMENT '0:默认,1:生成' AFTER `article_price`;


CREATE TABLE `word_article_html` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) DEFAULT '0' COMMENT '文档id',
  `content_type` enum('style','html') DEFAULT 'style' COMMENT 'html:内容,style:样式',
  `page_id` int(11) DEFAULT '0' COMMENT '页面ID',
  `conetnt` longtext,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING HASH,
  KEY `article_id` (`article_id`),
  CONSTRAINT `article_id` FOREIGN KEY (`article_id`) REFERENCES `know_article` (`article_id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#付费表相关
ALTER TABLE `word_users`
DROP INDEX `user_id_index` ,
ADD UNIQUE INDEX `user_id_index` (`user_id`) USING BTREE ;

ALTER TABLE `know_article`
ADD COLUMN `article_price`  double NULL DEFAULT 0 COMMENT '文档的价格' AFTER `article_journal_time`;

ALTER TABLE `word_journal`
ADD COLUMN `price`  double NULL DEFAULT 0 COMMENT '期刊价格' AFTER `have_data`;

ALTER TABLE `word_column`
ADD COLUMN `do_id`  int NULL DEFAULT 1 COMMENT '建立人' AFTER `user_id`;

ALTER TABLE `word_column_group`
ADD COLUMN `do_id`  int NULL DEFAULT 1 COMMENT '建立人' AFTER `user_id`;

ALTER TABLE `word_plate`
ADD COLUMN `do_id`  int NULL DEFAULT 0 COMMENT '建立人Id' AFTER `user_id`;

#是下面已经生成的
---------------------------------------


ALTER TABLE `word_users`
ADD COLUMN `session_id`  varchar(100) NULL AFTER `user_other`;

CREATE TABLE `user_other_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL COMMENT '银行或企业ID',
  `company_info` varchar(200) DEFAULT NULL COMMENT '用户自定义信息',
  `user_id` int(11) DEFAULT NULL,
  `company_type` int(1) DEFAULT NULL COMMENT '用户输入企业类型1企业 2银行',
  `user_edit` varchar(200) DEFAULT NULL COMMENT '用户输入信息',
  `user_job` varchar(200) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

#支付表
CREATE TABLE `alipayasynchronouslog` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `notify_time` datetime NOT NULL DEFAULT '2014-10-14 00:00:00' COMMENT '通知时间',
  `notify_type` varchar(20) NOT NULL DEFAULT 'trade_status_default' COMMENT '通知类型',
  `notify_id` varchar(50) NOT NULL DEFAULT '000' COMMENT '通知校验ID',
  `sign_type` varchar(10) NOT NULL DEFAULT '' COMMENT '签名方式',
  `sign` varchar(300) NOT NULL DEFAULT '000' COMMENT '签名',
  `out_trade_no` varchar(64) NOT NULL DEFAULT '000' COMMENT '订单编号，银联信生成，支付宝原样返回',
  `subject` varchar(256) DEFAULT 'report' COMMENT '商品名称',
  `payment_type` varchar(4) DEFAULT '1' COMMENT '支付类型，默认值1即商品购买',
  `trade_no` varchar(64) DEFAULT '00' COMMENT '支付宝交易号',
  `trade_status` varchar(20) DEFAULT '000' COMMENT '交易状态',
  `seller_id` varchar(30) DEFAULT '00' COMMENT '支付宝卖家ID',
  `seller_email` varchar(100) DEFAULT '00' COMMENT '卖家支付宝账号',
  `buyer_id` varchar(30) DEFAULT '00' COMMENT '买家支付宝用户号',
  `buyer_email` varchar(100) DEFAULT '00' COMMENT '买家email',
  `total_fee` varchar(20) DEFAULT '0' COMMENT '交易金额',
  `quantity` int(10) unsigned DEFAULT '0' COMMENT '购买数量',
  `price` varchar(20) DEFAULT '0' COMMENT '单价',
  `body` varchar(400) DEFAULT 'desc' COMMENT '商品描述',
  `gmt_create` datetime DEFAULT '2014-10-14 00:00:00' COMMENT '交易创建时间',
  `gmt_payment` datetime DEFAULT '2014-10-14 00:00:00' COMMENT '付款时间',
  `is_total_fee_adjust` int(1) DEFAULT '0' COMMENT '是否调整总价',
  `use_coupon` int(1) DEFAULT '0' COMMENT '是否使用红包',
  `discount` varchar(20) DEFAULT '00' COMMENT '折扣',
  `gmt_close` datetime DEFAULT NULL COMMENT '交易关闭时间',
  `business_scene` varchar(20) DEFAULT NULL COMMENT '是否扫码支付',
  `out_channel_inst` varchar(20) DEFAULT NULL COMMENT '实际支付渠道',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `alipaysynchronouslog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_success` varchar(11) NOT NULL COMMENT '成功表示',
  `sign_type` varchar(10) NOT NULL DEFAULT 'MD5' COMMENT '签名方式',
  `sign` varchar(300) NOT NULL COMMENT '签名',
  `out_trade_no` varchar(100) DEFAULT NULL COMMENT '订单号',
  `subject` varchar(100) DEFAULT NULL COMMENT '商品描述',
  `payment_type` varchar(4) DEFAULT '1' COMMENT '支付类型',
  `exterface` varchar(100) DEFAULT NULL COMMENT '接口名称',
  `trade_no` varchar(100) DEFAULT NULL COMMENT '支付宝交易号',
  `trade_status` varchar(100) DEFAULT 'TRADE_FINISHED' COMMENT '交易状态',
  `notify_id` varchar(100) DEFAULT NULL COMMENT '通知校验ID',
  `notify_time` datetime DEFAULT NULL COMMENT '通知时间',
  `notify_type` varchar(100) DEFAULT 'trade_status_sync' COMMENT '通知类型',
  `seller_email` varchar(100) DEFAULT NULL COMMENT '卖家支付宝账号',
  `buyer_email` varchar(100) DEFAULT NULL COMMENT '卖家支付宝账号',
  `seller_id` varchar(100) DEFAULT NULL COMMENT '卖家支付宝账户号',
  `buyer_id` varchar(100) DEFAULT NULL COMMENT '买家支付宝账户号',
  `total_fee` varchar(100) DEFAULT NULL COMMENT '交易金额',
  `body` varchar(400) DEFAULT NULL COMMENT '商品描述',
  `inserttime` datetime DEFAULT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#栏目表相关



DROP TABLE IF EXISTS `word_resource`;
CREATE TABLE `word_resource` (
  `resource_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户id',
  `resource_type` enum('journalarticle','article','journal') DEFAULT NULL COMMENT '资源类型:期刊,期刊文档,文档',
  `buy_type` enum('after','before') DEFAULT 'before' COMMENT '添加方式:前台,后台',
  `journal_id` int(11) DEFAULT '0' COMMENT '期刊id',
  `article_id` int(11) DEFAULT '0' COMMENT '文档id',
  `price` double DEFAULT NULL COMMENT '购买金币',
  `cart_id` int(11) DEFAULT NULL COMMENT '购物车id',
  `do_id` int(11) DEFAULT NULL COMMENT '操作人员',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime DEFAULT NULL COMMENT '建立时间',
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `word_shopping_cart`;
CREATE TABLE `word_shopping_cart` (
  `cart_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户Id',
  `resoure_type` enum('joutnalarticle','article','journal') DEFAULT 'journal' COMMENT '资源类型',
  `journal_id` int(11) DEFAULT '0' COMMENT '期刊Id',
  `article_id` int(11) DEFAULT '0' COMMENT '文档Id',
  `pay_flag` int(11) DEFAULT '0' COMMENT '0:位购买,1已购买',
  `create_time` datetime DEFAULT NULL COMMENT '建立时间',
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `word_user_money`;
CREATE TABLE `word_user_money` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户Id',
  `money` double DEFAULT '0' COMMENT '金币',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '建立时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `word_user_money_log`;
CREATE TABLE `word_user_money_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户Id',
  `type` enum('del','add') DEFAULT NULL COMMENT '方式:充值,消费',
  `buy_type` enum('before','after') DEFAULT 'before' COMMENT '购买方式:前台,后台',
  `money_type` enum('pay','bank','other') DEFAULT 'other' COMMENT 'bank:银行,pay:支付宝,other:其他',
  `money` double DEFAULT '0' COMMENT '充值金币',
  `add_money` double(10,0) DEFAULT '0' COMMENT '充值金额',
  `out_trade_no` varchar(100) DEFAULT NULL COMMENT '订单号',
  `resource_type` enum('journalarticle','article','journal') DEFAULT NULL COMMENT '资源类型:期刊,期刊文档,文档',
  `resource_id` int(11) DEFAULT '0' COMMENT '资源的Id',
  `do_id` int(11) DEFAULT '0' COMMENT '操作用户id',
  `create_time` datetime DEFAULT NULL COMMENT '建立时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_outtrade_no` (`out_trade_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;