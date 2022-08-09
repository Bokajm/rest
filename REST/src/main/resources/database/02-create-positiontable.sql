--liquibase formatted sql
--changeset jmargol:2
CREATE TABLE POSITION (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    deviceid BIGINT NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    CHECK (latitude BETWEEN -90 AND 90),
    CHECK (longitude BETWEEN -180 AND 180)
);

--changeset jmargol:3
ALTER TABLE POSITION
    ADD CONSTRAINT position_deviceid
        FOREIGN KEY (deviceid) REFERENCES device(id)