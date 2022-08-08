package com.example.rest.getTests.status;

import com.example.rest.model.Device;
import com.example.rest.repository.DeviceRepositoryI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetDeviceHttpStatusTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepositoryI deviceRepository;

    Device testDevice;

    @BeforeEach
    void init() {
        testDevice = new Device();
        testDevice.setDevicename("Test device name");
        testDevice.setDevicetype("Test device type");
        testDevice.setPositions(null);
        deviceRepository.save(testDevice);
    }

    @AfterEach
    void end() {
        deviceRepository.deleteAll();
    }

    @Test
    @DisplayName("Test if GET on /devices returns code 200")
    void devicesHttpStatusTest() throws Exception {
        mockMvc.perform(get("/devices")).andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("Test if GET on /devices with page specified returns code 200")
    void devicesWithPageHttpStatusTest() throws Exception {
        mockMvc.perform(get("/devices?pageIndex=0")).andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("Test if GET on /devices/index returns code 200 when device exists")
    void singleDeviceCorrectHttpStatusTest() throws Exception {
        mockMvc.perform(get("/devices/" + testDevice.getId())).andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("Test if GET on /devices/index returns code 404 when device does not exist")
    void singleDeviceIncorrectHttpStatusTest() throws Exception {
        mockMvc.perform(get("/devices/-500")).andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    @DisplayName("Test if GET on /devices/positions returns code 200")
    void fullDevicesHttpStatusTest() throws Exception {
        mockMvc.perform(get("/devices/positions")).andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("Test if GET on /devices/positions with page specified returns code 200")
    void fullDevicesWithPageHttpStatusTest() throws Exception {
        mockMvc.perform(get("/devices/positions?pageIndex=0")).andDo(print())
                .andExpect(status().is(200));
    }
}
