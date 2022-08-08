package com.example.rest.postTests.payload;

import com.example.rest.model.Device;
import com.example.rest.repository.DeviceRepositoryI;
import com.example.rest.repository.PositionRepositoryI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class PostPositionResponseTests {

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
    @DisplayName("Test if correct POST on /positions returns desired body")
    void deviceCorrectPostResponseBodyTest() throws Exception {

        var values = new HashMap<String, Object>() {{
            put("deviceid", testDevice.getId());
            put("latitude", 13.3213);
            put("longitude", 43.2423);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        mockMvc.perform(post("/positions").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(jsonPath("$.deviceid", Matchers.is(values.get("deviceid")), Long.class))
                .andExpect(jsonPath("$.latitude", Matchers.is(values.get("latitude"))))
                .andExpect(jsonPath("$.longitude", Matchers.is(values.get("longitude"))));
    }

    @Test
    @DisplayName("Test if POST on /positions with non-existing id returns desired body")
    void deviceWithNonExistingIdPostHttpStatusTest() throws Exception {

        var values = new HashMap<String, Object>() {{
            put("id", 5050);
            put("deviceid", testDevice.getId());
            put("latitude", 13.3213);
            put("longitude", 43.2423);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        mockMvc.perform(post("/positions").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(jsonPath("$.deviceid", Matchers.is(values.get("deviceid")), Long.class))
                .andExpect(jsonPath("$.latitude", Matchers.is(values.get("latitude"))))
                .andExpect(jsonPath("$.longitude", Matchers.is(values.get("longitude"))));
    }
}
