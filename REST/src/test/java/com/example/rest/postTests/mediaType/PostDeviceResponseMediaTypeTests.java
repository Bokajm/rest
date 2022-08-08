package com.example.rest.postTests.mediaType;

import com.example.rest.repository.DeviceRepositoryI;
import com.example.rest.repository.PositionRepositoryI;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
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
public class PostDeviceResponseMediaTypeTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepositoryI deviceRepository;

    @Autowired
    private PositionRepositoryI positionRepository;

    @AfterEach
    void end() {
        positionRepository.deleteAll();
        deviceRepository.deleteAll();
    }

    @Test
    @DisplayName("Test if correct POST on /devices returns application/json media type")
    void deviceCorrectPostHttpStatusTest() throws Exception {
        var values = new HashMap<String, String>() {{
            put("devicename", "testname");
            put("devicetype", "testtype");
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        mockMvc.perform(post("/devices").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Test if POST on /devices with non-existing id specified returns application/json media type")
    void deviceWithNonExistingIdPostHttpStatusTest() throws Exception {
        var values = new HashMap<String, Object>() {{
            put("id", 88888);
            put("devicename", "testname");
            put("devicetype", "testtype");
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        mockMvc.perform(post("/devices").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}