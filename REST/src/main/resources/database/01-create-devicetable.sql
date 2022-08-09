--liquibase formatted sql
--changeset jmargol:1
CREATE TABLE DEVICE (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    devicename varchar(200) NULL,
    devicetype varchar(200) NULL
);
