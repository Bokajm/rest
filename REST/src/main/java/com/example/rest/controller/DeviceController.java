package com.example.rest.controller;

import com.example.rest.dto.DeviceDto;
import com.example.rest.dto.DeviceDtoMapper;
import com.example.rest.model.Device;
import com.example.rest.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
public class DeviceController {

    private static final Logger logger = Logger.getLogger(PositionController.class.getName());

    private final DeviceService deviceService;

    @GetMapping("/devices")
    public List<DeviceDto> getDevices(@RequestParam(required = false) Integer pageIndex) {
        logger.info("Received GET request for devices, page: " + pageIndex);

        int page = 0;
        if(pageIndex != null && pageIndex > 0) {
            page = pageIndex;
        } else {
            logger.info("Invalid page index received, defaulting to 0");
        }

        logger.info("Sending response with devices, page: " + page);
        return DeviceDtoMapper.mapDevicesToDto(deviceService.getDevices(page));
    }

    @GetMapping("/devices/positions")
    public List<Device> getFullDevices(@RequestParam(required = false) Integer pageIndex) {
        logger.info("Received GET request for devices with positions, page: " + pageIndex);

        int page = 0;
        if(pageIndex != null && pageIndex > 0) {
            page = pageIndex;
        } else {
            logger.info("Invalid page index received, defaulting to 0");
        }

        logger.info("Sending response with devices, page: " + page);
        return deviceService.getFullDevices(page);
    }

    @GetMapping("/devices/{id}")
    public ResponseEntity<Device> getDevice(@PathVariable long id) {
        logger.info("Received GET request for a device, device id: " + id);
        Device device = deviceService.getDevice(id);
        if(device == null) {
            logger.info("Could not find device with id " + id + ", sending response");
            return new ResponseEntity<Device>(HttpStatus.NOT_FOUND);
        }

        logger.info("Sending response with device, id: " + id);
        return new ResponseEntity<Device>(device, HttpStatus.OK);
    }

    @PostMapping(value = "/devices", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Device> addDevice(@RequestBody Device device) {
        logger.info("Received POST request with a device, device id: " + device.getId());
        if((deviceService.getDevice(device.getId())) != null) {
            logger.info("Device with this id already exists, editing not allowed, sending response");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(device.getPositions() != null) {
            logger.info("Received device with positions, operation not allowed, sending response");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Device resultDevice = deviceService.addDevice(device);
        if(resultDevice == null) {
            logger.info("Could not process entity, sending response");
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            logger.info("Device added, device id: " + resultDevice.getId() + ", sending response");
            return new ResponseEntity<>(resultDevice, HttpStatus.CREATED);
        }
    }

}
