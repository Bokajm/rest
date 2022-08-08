package com.example.rest.repository;

import com.example.rest.model.Device;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class DeviceRepositoryTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepositoryI deviceRepository;

    @Test
    @DisplayName("Test of adding a device to device repository works properly")
    void addDeviceToRepoTest() {
        Device toAdd = new Device();
        toAdd.setDevicename("Added Device Name");
        toAdd.setDevicetype("Added Device Type");
        Device returned = deviceRepository.save(toAdd);
        Device foundEntity = deviceRepository.findById(returned.getId()).orElse(null);

        assertNotNull(foundEntity);
        assertEquals(toAdd.getDevicename(), foundEntity.getDevicename());
        assertEquals(toAdd.getDevicetype(), foundEntity.getDevicetype());
    }
}
