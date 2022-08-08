DROP TABLE POSITION;
DROP TABLE DEVICE;

CREATE TABLE DEVICE (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    devicename varchar(200) NULL,
    devicetype varchar(200) NULL
);

CREATE TABLE POSITION (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    deviceid BIGINT NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    CHECK (latitude BETWEEN -90 AND 90),
    CHECK (longitude BETWEEN -180 AND 180)
);

ALTER TABLE POSITION
    ADD CONSTRAINT position_deviceid
    FOREIGN KEY (deviceid) REFERENCES device(id)