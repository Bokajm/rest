package com.example.rest.dto;

import com.example.rest.model.Device;

import java.util.List;
import java.util.stream.Collectors;

public class DeviceDtoMapper {
    public static List<DeviceDto> mapDevicesToDto(List<Device> devices) {
        return devices.stream()
                .map(device -> mapDeviceToDto(device))
                .collect(Collectors.toList());
    }

    public static DeviceDto mapDeviceToDto(Device device) {
        return DeviceDto.builder()
                .id(device.getId())
                .devicename(device.getDevicename())
                .devicetype(device.getDevicetype())
                .build();
    }
}
