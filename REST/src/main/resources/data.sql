insert INTO DEVICE(id, devicename, devicetype) values (12345, 'device1', 'phone');
insert INTO DEVICE(id, devicename, devicetype) values (12412, 'device2', 'phone');
insert INTO DEVICE(id, devicename, devicetype) values (52353, 'device3', 'GPS');
insert INTO DEVICE(id, devicename, devicetype) values (23423, 'device4', 'phone');
insert INTO DEVICE(id, devicename, devicetype) values (42342, 'device5', 'laptop');

insert INTO POSITION(deviceid, latitude, longitude) values (12345, -85.0000, 142.3412);
insert INTO POSITION(deviceid, latitude, longitude) values (12345, 76.0000, -42.3423);
insert INTO POSITION(deviceid, latitude, longitude) values (12412, 56.1231, 41.2422);
insert INTO POSITION(deviceid, latitude, longitude) values (52353, 13.2300, 52.3434);
insert INTO POSITION(deviceid, latitude, longitude) values (23423, -52.3342, -6.4434);
insert INTO POSITION(deviceid, latitude, longitude) values (42342, -8.3423, 63.5353);