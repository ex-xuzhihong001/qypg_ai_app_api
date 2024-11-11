CREATE DATABASE IF NOT EXISTS rxy_data;

/*用户表：图像、微信号*/
CREATE TABLE `user` (
   `id`             int(11) NOT NULL AUTO_INCREMENT,
   `nickname`       varchar(256) NOT NULL DEFAULT ''        COMMENT '昵称',
   `open_id`        varchar(256) NOT NULL DEFAULT ''        comment '微信open_id',
   `country_code`   varchar(32) NOT NULL DEFAULT ''         comment '区号',
   `phone_number`   varchar(100) NOT NULL DEFAULT ''        comment '手机号',
   `imgUrl`         varchar(256) NOT NULL DEFAULT ''        COMMENT '图像地址',
   `name`           varchar(256) NOT NULL DEFAULT ''        COMMENT '姓名',
   `gender`         int(11) NOT NULL DEFAULT '0'            COMMENT '性别：0 未填 1 女 2 男',
   `industry_id`    int(11) NOT NULL DEFAULT '0'            COMMENT '所属行业id',
   `industry_name`  varchar(256) NOT NULL DEFAULT ''        COMMENT '所属行业名称',
   `qy_name`        varchar(225) NOT NULL  DEFAULT ''       COMMENT '企业名称：用户填写',
   `department`     varchar(225) NOT NULL  DEFAULT ''       COMMENT '部门名称：选择',
   `position`       varchar(225) NOT NULL  DEFAULT ''       COMMENT '职位：选择',
   `status`         int(11) NOT NULL DEFAULT '0'            COMMENT '状态 0 未生效 1 生效',
   `ctime`           datetime NOT NULL DEFAULT NOW()  COMMENT '创建时间',
   `mtime`           datetime NOT NULL DEFAULT NOW()  COMMENT '修改时间',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表';


/*配置表*/
CREATE TABLE `config_kv` (
   `id`             int(11) NOT NULL AUTO_INCREMENT,
   `config_key`     varchar(256) NOT NULL                COMMENT 'key',
   `config_value`   varchar(1000) NOT NULL DEFAULT ''    COMMENT '值',
   `info`           varchar(256) NOT NULL DEFAULT ''     COMMENT '描述',
   `status`         int(11) NOT NULL DEFAULT '0'         COMMENT '状态 0 未生效 1 生效',
   `ctime`           datetime NOT NULL DEFAULT NOW()  COMMENT '创建时间',
   `mtime`           datetime NOT NULL DEFAULT NOW()  COMMENT '修改时间',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='kv配置表';


CREATE TABLE `config_industry` (
   `id`             int(11) NOT NULL AUTO_INCREMENT,
   `parent_id`      int(11) NOT NULL DEFAULT '0'        COMMENT '父类id',
   `name`           varchar(256) NOT NULL               COMMENT '名称',
   `type`           int(11) NOT NULL                    COMMENT '类型：1 ->行业、2 ->部门、3 ->职位',
   `qy_type`        int(11) NOT NULL                    COMMENT '0 标准、1 无仓储物流企业、2 无生产有设计企业、3 有生产无设计企业、 4 无生产无设计企业',
   `status`         int(11) NOT NULL DEFAULT '0'        COMMENT '状态 0 未生效 1 生效',
   `ctime`           datetime NOT NULL DEFAULT NOW()  COMMENT '创建时间',
   `mtime`           datetime NOT NULL DEFAULT NOW()  COMMENT '修改时间',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='职能配置表';


CREATE TABLE `config_question_category` (
   `id`              int(11) NOT NULL AUTO_INCREMENT,
   `name`            varchar(256) NOT NULL DEFAULT ''    COMMENT '维度名称',
   `category_type`   int(11) NOT NULL DEFAULT '0'        COMMENT '维度类型 0 组织战略 1 技术基础 2 仓储物流 3 采购销售 4 生产生产 5 设计设计 6 职能支持',
   `qy_type`         int(11) NOT NULL DEFAULT '0'        COMMENT '0 标准、1 无仓储物流企业、2 无生产有设计企业、3 无设计企业、 4 无生产无设计企业',
   `qy_type_name`    varchar(256) NOT NULL DEFAULT ''    COMMENT '类型名称',
   `weight`          int(11) NOT NULL DEFAULT '0'        COMMENT '权重_系数',
   `sort`            int(11) NOT NULL DEFAULT '0'        COMMENT '排序',
   `status`          int(11) NOT NULL DEFAULT '0'        COMMENT '状态 0 未生效 1 生效',
   `ctime`           datetime NOT NULL DEFAULT NOW()  COMMENT '创建时间',
   `mtime`           datetime NOT NULL DEFAULT NOW()  COMMENT '修改时间',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='问题维度表';



 CREATE TABLE `config_question` (
   `id`                 int(11) NOT NULL AUTO_INCREMENT,
   `parent_id`           int(11) NOT NULL DEFAULT '0'        COMMENT '父类id',
   `category_type`       int(11) NOT NULL DEFAULT '0'        COMMENT '维度类型',
   `category_name`      int(11) NOT NULL DEFAULT '0'        COMMENT '维度名称',
   `question_name`      varchar(256) NOT NULL DEFAULT ''    COMMENT '问题名称',
   `trigger_type`       int(11) NOT NULL DEFAULT '0'        COMMENT '问题触发类型：0 分数触发、1 必答题',
   `trigger_condition`  varchar(4000) NOT NULL DEFAULT ''   COMMENT 'json字段：分数、其他',
   `sort`               int(11) NOT NULL DEFAULT '0'        COMMENT '出现顺序',
   `score`              int(11) NOT NULL DEFAULT '0'        COMMENT '分数',
   `weight`             int(11) NOT NULL                    COMMENT '权重_系数',
   `title`              varchar(4000)                       COMMENT '问题标题',
   `type`               int(11) NOT NULL                    COMMENT '问题类型：0 问答题、1 单选题【判断题】、2 多选题 目前只支持单选',
   `options`            varchar(4000) NOT NULL DEFAULT ''   COMMENT '选项内容，目前前端写死5档',
   `status`             int(11) NOT NULL DEFAULT '0'        COMMENT '状态 0 未发布、1 发布',
   `ctime`           datetime NOT NULL DEFAULT NOW()  COMMENT '创建时间',
   `mtime`           datetime NOT NULL DEFAULT NOW()  COMMENT '修改时间',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='问题配置表';

 CREATE TABLE `config_gpt_call_content` (
   `id`             int(11) NOT NULL AUTO_INCREMENT,
   `level`          varchar(16) NOT NULL                COMMENT '评估等级：0 普通、1 vip 、2 定制',
   `type`           varchar(16) NOT NULL                COMMENT '问题类型：0 现状描述 、1 提升空间、 2 发展建议、 3 风险收益 4 推荐技术及工具 ',
   `title`          varchar(16) NOT NULL                COMMENT '问题标题',
   `call_content`   varchar(4000) NOT NULL              COMMENT '问题固定内容',
   `limit_content`  varchar(4000) NOT NULL DEFAULT ''   COMMENT '限制内容：如返回数量、默认语言等',
   `dynamic_params` varchar(4000) NOT NULL DEFAULT ''   COMMENT '动态参数：行业、总分',
   `sort`           int(11) NOT NULL DEFAULT '0'        COMMENT '请求顺序',
   `status`         int(11) NOT NULL DEFAULT '0'        COMMENT '状态 0 无效 1 有效',
   `ctime`           datetime NOT NULL DEFAULT NOW()  COMMENT '创建时间',
   `mtime`           datetime NOT NULL DEFAULT NOW()  COMMENT '修改时间',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='chat_gpt调用内容配置表';

CREATE TABLE `sms_pg_goods` (
   `id`             int(11) NOT NULL AUTO_INCREMENT,
   `level`          varchar(16) NOT NULL                COMMENT '评估等级：0 普通、1 vip 、2 定制',
   `amount`         decimal(10,2) NOT NULL              COMMENT '商品金额，0的话就直接生成无需支付',
   `title`          varchar(256) NOT NULL DEFAULT ''    COMMENT '商品标题',
   `content`        varchar(500) NOT NULL DEFAULT ''    COMMENT '内容',
   `img_url`        varchar(256) NOT NULL DEFAULT ''    COMMENT '图片',
   `sort`           int(11) NOT NULL DEFAULT '0'        COMMENT '展示顺序',
   `status`         int(11) NOT NULL DEFAULT '0'        COMMENT '状态 0 无效 1 有效',
   `ctime`           datetime NOT NULL DEFAULT NOW()  COMMENT '创建时间',
   `mtime`           datetime NOT NULL DEFAULT NOW()  COMMENT '修改时间',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='评估报告商品表';


/*数据表：*/
 CREATE TABLE `user_question_history` (
   `id`             int(11) NOT NULL AUTO_INCREMENT,
   `user_id`        int(11) NOT NULL                    COMMENT '用户id',
   `batch_no`       varchar(256) NOT NULL               COMMENT '问题回答批次编号',
   `category_id`    int(11) NOT NULL DEFAULT '0'        COMMENT '问题维度id',
   `question_id`    int(11) NOT NULL                    COMMENT '问题id',
   `answer`         varchar(1000) NOT NULL DEFAULT ''   COMMENT '答案',
   `title`          varchar(4000) NOT NULL DEFAULT ''   COMMENT '问题标题',
   `type`           int(11) NOT NULL DEFAULT '0'        COMMENT '问题类型：0 问答题、1 单选题【判断题】、2 多选题',
   `score`          int(11) NOT NULL DEFAULT '0'        COMMENT '得分',
   `options`        varchar(4000) NOT NULL DEFAULT ''   COMMENT '选项',
   `status`         int(11) NOT NULL DEFAULT '0'        COMMENT '状态 0 无效 1 有效',
   `uk`             varchar(256) NOT NULL               COMMENT 'uk:批次id+问题id',
   `ctime`           datetime NOT NULL DEFAULT NOW()  COMMENT '创建时间',
   `mtime`           datetime NOT NULL DEFAULT NOW()  COMMENT '修改时间',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户答题历史表';

 CREATE TABLE `user_score` (
   `id`             int(11) NOT NULL AUTO_INCREMENT,
   `user_id`        int(11) NOT NULL                    COMMENT '用户id',
   `category_id`    varchar(16) NOT NULL                COMMENT '所属维度id',
   `batch_no`       varchar(256) NOT NULL               COMMENT '问题回答批次编号',
   `score`          int(11) NOT NULL DEFAULT '0'        COMMENT '分数',
   `uk`             varchar(256) NOT NULL               COMMENT 'uk:批次id+维度id',
   `ctime`           datetime NOT NULL DEFAULT NOW()  COMMENT '创建时间',
   `mtime`           datetime NOT NULL DEFAULT NOW()  COMMENT '修改时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `unx_uk` (`uk`),
   KEY `idx_batch_no` (`batch_no`),
   KEY `idx_user_id` (`user_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户得分表';


 CREATE TABLE `user_evaluation_history` (
   `id`             int(11) NOT NULL AUTO_INCREMENT,
   `user_id`        int(11) NOT NULL                   comment '用户id',
   `order_no`       varchar(225) NOT NULL               COMMENT '订单编号',
   `batch_no`       varchar(256) NOT NULL               COMMENT '问题回答批次编号',
   `level`          int(11) NOT NULL DEFAULT '0'        COMMENT '评估等级 0 普通、1 vip 、2 定制',
   `type`           varchar(16) NOT NULL                COMMENT '问题类型：0 现状描述 、1 提升空间、 2 发展建议、 3 风险收益 4 推荐技术及工具 ',
   `title`          varchar(16) NOT NULL                COMMENT '问题标题',
   `call_content`   text NOT NULL                       COMMENT '问题固定内容',
   `back_content`   text NOT NULL DEFAULT ''            COMMENT '返回评估内容',
   `status`         int(11) NOT NULL DEFAULT '0'        COMMENT '状态:0 无效 1 有效 ',
   `info`           varchar(500) NOT NULL               COMMENT '描述',
   `uk`             varchar(256) NOT NULL               COMMENT 'uk:订单编号+问题类型',
   `ctime`           datetime NOT NULL DEFAULT NOW()  COMMENT '创建时间',
   `mtime`           datetime NOT NULL DEFAULT NOW()  COMMENT '修改时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `unx_uk` (`uk`),
   KEY `idx_order_no` (`order_no`),
   KEY `idx_batch_no` (`batch_no`),
   KEY `idx_user_id` (`user_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户评估历史记录';



CREATE TABLE `oms_order` (
  `id`              bigint(20) NOT NULL AUTO_INCREMENT  COMMENT '订单id',
  `order_no`        varchar(128) NOT NULL               COMMENT '订单编号',
  `batch_no`        varchar(128) NOT NULL               COMMENT '问题批次编号',
  `prepay_id`       varchar(128) NOT NULL               COMMENT '支付预编号',
  `user_id`         int(11) NOT NULL                    comment '用户id',
  `total_amount`    decimal(10,2) NOT NULL              COMMENT '订单总金额',
  `pay_amount`      decimal(10,2) NOT NULL              COMMENT '应付金额（实际支付金额）',
  `pay_type`        int(1) NOT NULL                     COMMENT '支付方式：0->未支付；1->支付宝；2->微信',
  `pay_status`      int(1) NOT NULL                     COMMENT '订单支付状态：0->待付款；1->已支付；2->支付超时已关闭; 3->已取消；4->退款受理中； 5->退款中；6->已退款 7->退款异常',
  `order_status`    int(1) NOT NULL  DEFAULT '0'        COMMENT '订单状态：0->评估报告生成中；1->生成成功 2->生成失败',
  `note`            varchar(500) NOT NULL DEFAULT ''    COMMENT '订单备注',
  `delete_status`   int(1) NOT NULL DEFAULT '0'         COMMENT '删除状态：0->未删除；1->已删除',
  `payment_time`    datetime DEFAULT NULL               COMMENT '支付时间',
  `info`            varchar(500) NOT NULL DEFAULT ''    COMMENT '描述',
  `ctime`           datetime NOT NULL DEFAULT NOW()  COMMENT '创建时间',
  `mtime`           datetime NOT NULL DEFAULT NOW()  COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unx_order_no` (`order_no`),
  KEY `idx_batch_no` (`batch_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_ctime` (`ctime`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单表';


CREATE TABLE `oms_pay_history`  (
  `id`              bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '支付记录id',
  `order_no`        varchar(50) NOT NULL DEFAULT ''         COMMENT '商户订单编号',
  `transaction_id`  varchar(50) NOT NULL DEFAULT ''         COMMENT '支付系统交易编号',
  `pay_type`        int(1) NOT NULL                         COMMENT '支付方式：0->未支付；1->支付宝；2->微信',
  `trade_type`      varchar(20) NOT NULL DEFAULT ''         COMMENT '交易类型：小程序支付',
  `trade_state`     varchar(50) NOT NULL DEFAULT ''         COMMENT '交易状态：待支付、支付成功、支付失败、超时未支付、订单关闭',
  `payer_total`     int(11) NOT NULL DEFAULT '0'            COMMENT '支付金额(分)',
  `content`         text NOT NULL DEFAULT ''                COMMENT '通知参数',
  `ctime`           datetime NOT NULL DEFAULT NOW()  COMMENT '创建时间',
  `mtime`           datetime NOT NULL DEFAULT NOW()  COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unx_order_no` (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET=utf8 COMMENT '支付记录表';


CREATE TABLE `oms_refund_history`  (
  `id`              bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '款单id',
  `order_no`        varchar(50) NOT NULL DEFAULT ''      COMMENT '订单编号',
  `refund_no`       varchar(50) NOT NULL DEFAULT ''      COMMENT '商户退款单编号',
  `refund_id`       varchar(50) NOT NULL DEFAULT ''      COMMENT '支付系统退款单号',
  `total_fee`       int(11) NOT NULL DEFAULT '0'         COMMENT '原订单金额(分)',
  `refund`          int(11) NOT NULL DEFAULT '0'         COMMENT '退款金额(分)',
  `reason`          varchar(50) NOT NULL DEFAULT ''      COMMENT '退款原因',
  `refund_status`   varchar(10) NOT NULL DEFAULT ''      COMMENT '退款状态：待退款、退款成功、退款异常',
  `content_return`  text NOT NULL DEFAULT ''             COMMENT '申请退款返回参数',
  `content_notify`  text NOT NULL DEFAULT ''             COMMENT '退款结果通知参数',
  `ctime`           datetime NOT NULL DEFAULT NOW()      COMMENT '创建时间',
  `mtime`           datetime NOT NULL DEFAULT NOW()      COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unx_order_no` (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET=utf8 COMMENT '退款记录表'

