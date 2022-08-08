package com.example.rest.getTests.payload;

import com.example.rest.model.Device;
import com.example.rest.repository.DeviceRepositoryI;
import com.example.rest.repository.PositionRepositoryI;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetDeviceResponseTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepositoryI deviceRepository;

    @Autowired
    private PositionRepositoryI positionRepository;

    Device testDevice;

    @BeforeEach
    void init() {
        positionRepository.deleteAll();
        deviceRepository.deleteAll();
        testDevice = new Device();
        testDevice.setDevicename("Test device name");
        testDevice.setDevicetype("Test device type");
        testDevice.setPositions(null);
        deviceRepository.save(testDevice);
    }

    @AfterEach
    void end() {
        positionRepository.deleteAll();
        deviceRepository.deleteAll();
    }

    @Test
    @DisplayName("Test if GET on /devices/index returns desired body")
    void deviceResponseTest() throws Exception {
        mockMvc.perform(get("/devices/" + testDevice.getId())).andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Matchers.is(testDevice.getId()), Long.class))
                .andExpect(jsonPath("$.devicename", Matchers.is(testDevice.getDevicename())))
                .andExpect(jsonPath("$.devicetype", Matchers.is(testDevice.getDevicetype())));
    }
}
