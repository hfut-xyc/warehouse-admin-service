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
    `user_id`               INT(11)      NOT NULL AUTO_INCREMENT,
    `username`         VARCHAR(255) NOT NULL,
    `password`         VARCHAR(255) NOT NULL,
    `role`             TINYINT(1)   NOT NULL COMMENT '0表示普通用户，1表示超级管理员',
    `create_time`      TIMESTAMP    NOT NULL DEFAULT current_timestamp(),
    `update_time`      TIMESTAMP    NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (user_id),
    UNIQUE KEY idx (username)
) ENGINE = InnoDB
  CHARSET = utf8;

# 只有超级管理员才能添加、修改、删除仓库
CREATE TABLE `tb_warehouse`
(
    `warehouse_id`               INT(11)      NOT NULL AUTO_INCREMENT,
    `warehouse_name`             VARCHAR(255) NOT NULL,
    `create_time`      TIMESTAMP DEFAULT current_timestamp(),
    `update_time`      TIMESTAMP DEFAULT current_timestamp(),
    PRIMARY KEY (warehouse_id)
) ENGINE = InnoDB
  CHARSET = utf8
  AUTO_INCREMENT = 10000000;

CREATE TABLE `tb_product`
(
    `product_id`               INT(11)      NOT NULL AUTO_INCREMENT,
    `product_name`             VARCHAR(255) NOT NULL,
    `create_time`      TIMESTAMP DEFAULT current_timestamp(),
    `update_time`      TIMESTAMP DEFAULT current_timestamp(),
    PRIMARY KEY (product_id)
) ENGINE = InnoDB
  CHARSET = utf8
  AUTO_INCREMENT = 20000000;

CREATE TABLE `tb_warehouse_product`
(
    `id`               INT(11) NOT NULL AUTO_INCREMENT,
    `warehouse_id`     INT(11)      NOT NULL COMMENT '',
    `warehouse_name`   VARCHAR(255) NOT NULL COMMENT '',
    `product_id`       INT(11)      NOT NULL COMMENT '',
    `product_name`     VARCHAR(255) NOT NULL COMMENT '',
    `stock`            INT     NOT NULL,
    `create_user_id`   INT(11),
    `create_username` VARCHAR(255),
    `create_time`      TIMESTAMP DEFAULT current_timestamp(),
    `update_user_id`   INT(11),
    `update_username` VARCHAR(255),
    `update_time`      TIMESTAMP DEFAULT current_timestamp(),
    PRIMARY KEY (warehouse_id),
    KEY idx_warehouse_id (warehouse_id),
    KEY idx_product_id (product_id)
) ENGINE = InnoDB
  CHARSET = utf8;

CREATE TABLE `tb_order`
(
    `order_id`               INT(11)      NOT NULL AUTO_INCREMENT,
    `warehouse_id`     INT(11)      NOT NULL COMMENT '',
    `warehouse_name`   VARCHAR(255) NOT NULL COMMENT '',
    `product_id`       INT(11)      NOT NULL COMMENT '',
    `product_name`     VARCHAR(255) NOT NULL COMMENT '',
    `count`            INT          NOT NULL COMMENT '订单数量，大于0表示入库，小于0表示出库',
    `create_user_id`   INT(11),
    `create_username` VARCHAR(255),
    `create_time`      TIMESTAMP DEFAULT current_timestamp(),
    `update_user_id`   INT(11),
    `update_username` VARCHAR(255),
    `update_time`      TIMESTAMP DEFAULT current_timestamp(),
    PRIMARY KEY (order_id),
    KEY idx_create_time (create_time)
) ENGINE = InnoDB
  CHARSET = utf8;


INSERT INTO `tb_user`(username, password, role)
VALUES ('admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 1),
       ('xyc', 'ad6b60c1ac2e3d95828b88673e6d22c311c921f9606f08bc2967bfbd117a9a43', 0);

INSERT INTO `tb_warehouse`(warehouse_name)
VALUES ('1号仓库'),
       ('2号仓库'),
       ('3号仓库'),
       ('4号仓库');

INSERT INTO `tb_product`(product_name)
VALUES ('华为p60'),
       ('小米10'),
       ('iPhone X');


INSERT INTO `tb_warehouse_product`(warehouse_id, warehouse_name, product_id, product_name, count)
VALUES (10000001, '1号仓库', 20000000, '华为p60',  100),
       (10000001, '1号仓库',  20000001, '小米10',  200),
       (10000002, '2号仓库',  20000001, '小米10',  300),
       (10000002, '2号仓库',  20000002, 'iPhone X',  400),
       (10000003, '3号仓库',  20000000, '华为p60',  200),
       (10000003, '3号仓库',  20000001, '小米10',  500);


# INSERT INTO `tb_order`(warehouse_id, product_id, count)
# VALUES (10000000, 20000000, 100),
#        (10000000, 20000001, -200),
#        (10000002, 20000001, 300),
#        (10000002, 20000002, -400),
#        (10000003, 20000000, 200);
# 扣库存有并发安全问题
