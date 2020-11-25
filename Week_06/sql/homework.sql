CREATE TABLE `customer` (
  `customer_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT comment '用户ID' ,
  `mobile` VARCHAR(16) NOT NULL comment '手机号',
  `customer_name` VARCHAR(32) NOT NULL comment '用户名',
  `password` VARCHAR(32) NOT NULL comment '密码',
  `email` VARCHAR(32) NOT NULL comment '邮箱',
  `address` VARCHAR(64) NOT NULL comment '地址',
  `create_time` BIGINT UNSIGNED NOT NULL comment '创建时间',
  `update_time` BIGINT UNSIGNED NOT NULL comment '更新时间',
  UNIQUE INDEX `customer_id_UNIQUE` (`customer_id` ASC) VISIBLE,
  UNIQUE INDEX `mobile_UNIQUE` (`mobile` ASC) VISIBLE,
  PRIMARY KEY (`mobile`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE `commodity_info` (
  `commodity_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT comment '商品ID' ,
  `commodity_name` VARCHAR(16) NOT NULL comment '商品名称',
  `commodity_type` VARCHAR(16) NOT NULL comment '商品类型',
  `price` BIGINT NOT NULL comment '价格',
  `description` VARCHAR(32) NOT NULL comment '描述',
  `create_time` BIGINT UNSIGNED NOT NULL comment '创建时间',
  `update_time` BIGINT UNSIGNED NOT NULL comment '更新时间',
  PRIMARY KEY (`commodity_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE `order_info` (
  `order_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT comment '订单ID' ,
  `order_time` BIGINT UNSIGNED NOT NULL comment '订单时间',
  `order_user` BIGINT NOT NULL comment '下单用户',
  `commodity_id` BIGINT UNSIGNED NOT NULL comment '商品ID' ,
  `price` BIGINT UNSIGNED NOT NULL comment '价格',
  `create_time` BIGINT UNSIGNED NOT NULL comment '创建时间',
  `update_time` BIGINT UNSIGNED NOT NULL comment '更新时间',
  PRIMARY KEY (`order_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;