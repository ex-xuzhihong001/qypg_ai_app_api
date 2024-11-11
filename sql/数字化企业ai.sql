

/*用户表：图像、微信号*/
CREATE TABLE `user` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `nickname` varchar(256) NOT NULL DEFAULT '' COMMENT '姓名',
   `gender` int(11) NOT NULL  COMMENT '性别',
   `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `mtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间'
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=10036 DEFAULT CHARSET=utf8 COMMENT='用户表'



/*配置表：  行业表、 职位表、部门表*/
CREATE TABLE `config_industry` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `parent_id` int(11) NOT NULL DEFAULT '' COMMENT '父类id',
   `name` varchar(256) NOT NULL DEFAULT '' COMMENT '行业名称',
   `status` int(11) NOT NULL DEFAULT '' COMMENT '状态',
   `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `mtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间'
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=10036 DEFAULT CHARSET=utf8 COMMENT='行业配置表'

CREATE TABLE `config_question_category` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `name` varchar(256) NOT NULL DEFAULT '' COMMENT '维度名称',
   `weight` int(11) NOT NULL DEFAULT '0' COMMENT '权重_系数',
   `status` int(11) NOT NULL DEFAULT '' COMMENT '状态',
   `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `mtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间'
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=10036 DEFAULT CHARSET=utf8 COMMENT='问题维度表'

 CREATE TABLE `config_question` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `industry_id` int(11) NOT NULL  COMMENT '所属行业id',
   `question_name` varchar(256) NOT NULL DEFAULT '' COMMENT '问题名称',
   `trigger_type` int(11) NOT NULL DEFAULT '0' COMMENT '问题类型：0 分数触发、1 必答题',
   `trigger_condition` varchar(4000) NOT NULL DEFAULT '' COMMENT 'json字段：分数、其他',
   `sort` int(11) NOT NULL DEFAULT '0' COMMENT '出现顺序',
   `score` int(11) NOT NULL DEFAULT '0' COMMENT '分数',
   `weight` int(11) NOT NULL DEFAULT '0' COMMENT '权重_系数',
   `title` varchar(4000) NOT NULL DEFAULT '' COMMENT '问题标题',
   `type` int(11) NOT NULL DEFAULT '0' COMMENT '问题类型：0 问答题、1 单选题【判断题】、2 多选题',
   `options` varchar(4000) NOT NULL DEFAULT '' COMMENT '选项',
   `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态 0 未发布、1 发布',
   `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `mtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间'
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=10036 DEFAULT CHARSET=utf8 COMMENT='问题配置表'





/*数据表：*/

 CREATE TABLE `user_question_history` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `user_id` int(11) NOT NULL  COMMENT '用户id',
   `batch_no` varchar(256) NOT NULL  COMMENT '问题回答批次编号',
   `question_id` int(11) NOT NULL COMMENT '问题id',
   `answer` varchar(1000) NOT NULL DEFAULT '' COMMENT '答案',
   `title` varchar(4000) NOT NULL DEFAULT '' COMMENT '问题标题',
   `type` int(11) NOT NULL DEFAULT '0' COMMENT '问题类型：0 问答题、1 单选题【判断题】、2 多选题',
   `options` varchar(4000) NOT NULL DEFAULT '' COMMENT '选项',
   `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
   `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `mtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间'
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=10036 DEFAULT CHARSET=utf8 COMMENT='用户答题历史表'



 CREATE TABLE `user_score` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `user_id` int(11) NOT NULL  COMMENT '用户id',
   `industry_id` varchar(16) NOT NULL DEFAULT '' COMMENT '所属行业id',
   `batch_no` varchar(256) NOT NULL  COMMENT '问题回答批次编号',
   `score` int(11) NOT NULL DEFAULT '0' COMMENT '分数',
   `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `mtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间'
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=10036 DEFAULT CHARSET=utf8 COMMENT='用户得分表'


 CREATE TABLE `user_evaluation_history` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `order_no` varchar(225) NOT NULL COMMENT '订单编号',
   `industry_id` int(11) NOT NULL DEFAULT '' COMMENT '所属行业id',
   `level` int(11) NOT NULL DEFAULT '0' COMMENT '评估等级 0 普通、1 vip 、 2 定制',
   `content` text NOT NULL DEFAULT '' COMMENT '评估内容',
   `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
   `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `mtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间'
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=10036 DEFAULT CHARSET=utf8 COMMENT='用户评估历史记录'


  CREATE TABLE `user_business_info` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `user_id` int(11) NOT NULL  COMMENT '用户id',
   `industry_id` int(11) NOT NULL DEFAULT '' COMMENT '所属行业id',
   `department` varchar(225) NOT NULL  DEFAULT '' COMMENT '部门',
   `position` varchar(225) NOT NULL  DEFAULT '' COMMENT '职位',
   `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `mtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间'
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=10036 DEFAULT CHARSET=utf8 COMMENT='用户企业信息表'


  CREATE TABLE `user_order` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `order_no` varchar(225) NOT NULL COMMENT '订单编号',
   `industry_id` int(11) NOT NULL DEFAULT '' COMMENT '金额',
   `pay_status` int(11) NOT NULL DEFAULT '0' COMMENT '支付状态',
   `status` int(11) NOT NULL DEFAULT '0' COMMENT '订单状态',
   `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `mtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间'
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=10036 DEFAULT CHARSET=utf8 COMMENT='用户订单表'


