package com.example.rest.postTests.mediaType;

import com.example.rest.model.Device;
import com.example.rest.model.Position;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class PostPositionResponseMediaTypeTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepositoryI deviceRepository;

    @Autowired
    private PositionRepositoryI positionRepository;

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
        positionRepository.deleteAll();
        deviceRepository.deleteAll();
    }

    @Test
    @DisplayName("Test if correct POST on /positions returns application/json media type")
    void positionCorrectPostMediaTypeTest() throws Exception {

        var values = new HashMap<String, Object>() {{
            put("deviceid", testDevice.getId());
            put ("latitude", 12.3123);
            put("longitude", 12.3321);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        mockMvc.perform(post("/positions").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Test if POST on /position with non-existing id specified returns returns application/json media type")
    void positionWithNonExistingIdPostMediaTypeTest() throws Exception {

        var values = new HashMap<String, Object>() {{
            put("id", 88888);
            put("deviceid", testDevice.getId());
            put ("latitude", 12.3123);
            put("longitude", 12.3321);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        mockMvc.perform(post("/positions").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
