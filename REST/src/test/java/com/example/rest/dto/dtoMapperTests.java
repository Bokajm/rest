package com.example.rest.dto;

import com.example.rest.model.Device;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class dtoMapperTests {

    @Test
    @DisplayName("Test if mapping a single device to dto with DeviceDtoMapper works properly")
    void mapDeviceToDtoTest() {
        Device device = new Device();
        device.setId(1);
        device.setDevicename("name");
        device.setDevicetype("type");
        device.setPositions(null);
        DeviceDto dto = DeviceDtoMapper.mapDeviceToDto(device);

        assertEquals(device.getId(), dto.getId());
        assertEquals(device.getDevicename(), dto.getDevicename());
        assertEquals(device.getDevicetype(), dto.getDevicetype());
    }

    @Test
    @DisplayName("Test if mapping a list of devices to dto with DeviceDtoMapper works properly")
    void mapDevicesToDtoTest() {
        List<Device> devices = new ArrayList<>();
        Device device = new Device();

        for(int i=0;i<10;i++) {
            device.setId(i+1);
            device.setDevicename("name" + i);
            device.setDevicetype("type" + i);
            device.setPositions(null);

            devices.add(device);
        }

        List<DeviceDto> dtos = DeviceDtoMapper.mapDevicesToDto(devices);

        assertEquals(dtos.size(), 10);
        for(int i=0;i<10;i++) {
            assertEquals(dtos.get(i).getId(), devices.get(i).getId());
            assertEquals(dtos.get(i).getDevicename(), devices.get(i).getDevicename());
            assertEquals(dtos.get(i).getDevicetype(), devices.get(i).getDevicetype());
        }
    }
}
