CREATE DATABASE IF NOT EXISTS db_warehouse;
USE db_warehouse;

DROP TABLE IF EXISTS `tb_user`;
DROP TABLE IF EXISTS `tb_warehouse`;
DROP TABLE IF EXISTS `tb_product`;
DROP TABLE IF EXISTS `tb_order`;
DROP TABLE IF EXISTS `tb_warehouse_product`;

# 只有超级管理员才能添加、修改、删除用户
CREATE TABLE `tb_user`
(
    `id`          INT(11)      NOT NULL AUTO_INCREMENT,
    `user_id`     VARCHAR(255) NOT NULL,
    `username`    VARCHAR(255) NOT NULL,
    `password`    VARCHAR(255) NOT NULL,
    `role`        TINYINT(1)   NOT NULL COMMENT '0表示普通用户，1表示超级管理员',
    `create_time` TIMESTAMP    NOT NULL DEFAULT current_timestamp(),
    `update_time` TIMESTAMP    NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (id),
    KEY idx (username)
) ENGINE = InnoDB
  CHARSET = utf8;

# 只有超级管理员才能添加、修改、删除仓库
CREATE TABLE `tb_warehouse`
(
    `id`             INT(11)      NOT NULL AUTO_INCREMENT,
    `warehouse_id`   VARCHAR(255) NOT NULL,
    `warehouse_name` VARCHAR(255) NOT NULL,
    `create_time`    TIMESTAMP DEFAULT current_timestamp(),
    `update_time`    TIMESTAMP DEFAULT current_timestamp(),
    PRIMARY KEY (id),
    KEY idx (warehouse_name)
) ENGINE = InnoDB
  CHARSET = utf8;

CREATE TABLE `tb_product`
(
    `id`           INT(11)      NOT NULL AUTO_INCREMENT,
    `product_id`   VARCHAR(255) NOT NULL,
    `product_name` VARCHAR(255) NOT NULL,
    `create_time`  TIMESTAMP DEFAULT current_timestamp(),
    `update_time`  TIMESTAMP DEFAULT current_timestamp(),
    PRIMARY KEY (id),
    KEY idx (product_name)
) ENGINE = InnoDB
  CHARSET = utf8;

CREATE TABLE `tb_warehouse_product`
(
    `id`             INT(11)      NOT NULL AUTO_INCREMENT,
    `warehouse_id`   VARCHAR(255) NOT NULL COMMENT '',
    `warehouse_name` VARCHAR(255) NOT NULL COMMENT '',
    `product_id`     VARCHAR(255) NOT NULL COMMENT '',
    `product_name`   VARCHAR(255) NOT NULL COMMENT '',
    `stock`          INT          NOT NULL,
    `create_time`    TIMESTAMP DEFAULT current_timestamp(),
    `update_time`    TIMESTAMP DEFAULT current_timestamp(),
    PRIMARY KEY (id),
    KEY idx_warehouse_id (warehouse_id),
    KEY idx_product_id (product_id)
) ENGINE = InnoDB
  CHARSET = utf8;

CREATE TABLE `tb_order`
(
    `id`             INT(11)      NOT NULL AUTO_INCREMENT,
    `order_id`       VARCHAR(255) NOT NULL COMMENT '订单id，17位时间戳+5位随机数',
    `warehouse_id`   VARCHAR(255) NOT NULL COMMENT '仓库id',
    `warehouse_name` VARCHAR(255) NOT NULL COMMENT '仓库名',
    `product_id`     VARCHAR(255) NOT NULL COMMENT '产品id',
    `product_name`   VARCHAR(255) NOT NULL COMMENT '产品名',
    `user_id`        VARCHAR(255) NOT NULL COMMENT '创建订单的用户id',
    `username`       VARCHAR(255) NOT NULL COMMENT '创建订单的用户名',
    `count`          INT          NOT NULL COMMENT '订单数量，大于0表示入库，小于0表示出库',
    `stock`          INT          NOT NULL COMMENT '订单完成后的剩余库存',
    `create_time`    TIMESTAMP DEFAULT current_timestamp(),
    `update_time`    TIMESTAMP DEFAULT current_timestamp(),
    PRIMARY KEY (id),
    KEY (order_id)
) ENGINE = InnoDB
  CHARSET = utf8;


INSERT INTO `tb_user`(user_id, username, password, role)
VALUES ('A00001', 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 1),
       ('A00002', 'xyc', 'ad6b60c1ac2e3d95828b88673e6d22c311c921f9606f08bc2967bfbd117a9a43', 0);

INSERT INTO `tb_warehouse`(warehouse_id, warehouse_name)
VALUES ('10000001', '1号仓库'),
       ('10000002', '2号仓库'),
       ('10000003', '3号仓库'),
       ('10000004', '4号仓库');

INSERT INTO `tb_product`(product_id, product_name)
VALUES ('20000001', 'Huawei'),
       ('20000002', 'Xiaomi'),
       ('20000003', 'iPhone');


INSERT INTO `tb_warehouse_product`(warehouse_id, warehouse_name, product_id, product_name, stock)
VALUES ('10000001', '1号仓库', '20000001', '华为p60', 1000),
       ('10000001', '1号仓库', '20000002', '小米10', 200),
       ('10000002', '2号仓库', '20000002', '小米10', 300),
       ('10000002', '2号仓库', '20000003', 'iPhone X', 400),
       ('10000003', '3号仓库', '20000001', '华为p60', 200),
       ('10000003', '3号仓库', '20000002', '小米10', 500);
