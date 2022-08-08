package com.example.rest.postTests.status;

import com.example.rest.model.Device;
import com.example.rest.repository.DeviceRepositoryI;
import com.example.rest.repository.PositionRepositoryI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostDeviceHttpStatusTests {

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
    @DisplayName("Test if correct POST on /devices returns code 201")
    void deviceCorrectPostHttpStatusTest() throws Exception {

        var values = new HashMap<String, String>() {{
            put("devicename", "testname");
            put("devicetype", "testtype");
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        mockMvc.perform(post("/devices").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is(201));
    }

    @Test
    @DisplayName("Test if POST on /devices with non-existing id specified returns code 201")
    void deviceWithNonExistingIdPostHttpStatusTest() throws Exception {

        var values = new HashMap<String, Object>() {{
            put("id", 88888);
            put("devicename", "testname");
            put("devicetype", "testtype");
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        mockMvc.perform(post("/devices").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is(201));
    }

    @Test
    @DisplayName("Test if POST on /devices with existing id specified returns code 403")
    void deviceWithExistingIdPostHttpStatusTest() throws Exception {

        var values = new HashMap<String, Object>() {{
            put("id", testDevice.getId());
            put("devicename", "testname");
            put("devicetype", "testtype");
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        mockMvc.perform(post("/devices").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is(403));
    }
}
