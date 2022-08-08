package com.example.rest.getTests.mediaType;

import com.example.rest.model.Device;
import com.example.rest.repository.DeviceRepositoryI;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class GetDeviceResponseMediaTypeTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepositoryI deviceRepository;

    Device testDevice;

    @BeforeEach
    void init() {
        deviceRepository.deleteAll();
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
    @DisplayName("Test if GET on /devices returns application/json media type")
    void devicesMediaTypeTest() throws Exception {
        mockMvc.perform(get("/devices")).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Test if GET on /devices with page specified returns application/json media type")
    void devicesWithPageMediaTypeTest() throws Exception {
        mockMvc.perform(get("/devices?pageIndex=0")).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Test if GET on /devices/index returns application/json media type")
    void singleDevicesMediaTypeTest() throws Exception {
        mockMvc.perform(get("/devices/" + testDevice.getId())).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Test if GET on /devices/positions returns application/json media type")
    void fullDevicesMediaTypeTest() throws Exception {
        mockMvc.perform(get("/devices/positions")).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Test if GET on /devices/index with page specified returns application/json media type")
    void fullDevicesWithPageMediaTypeTest() throws Exception {
        mockMvc.perform(get("/devices/positions?pageIndex=0")).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}