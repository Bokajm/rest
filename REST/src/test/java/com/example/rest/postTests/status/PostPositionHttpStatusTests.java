package com.example.rest.postTests.status;


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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostPositionHttpStatusTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepositoryI deviceRepository;

    @Autowired
    private PositionRepositoryI positionRepository;

    Device testDevice;
    Position testPosition;

    @BeforeEach
    void init() {
        positionRepository.deleteAll();
        deviceRepository.deleteAll();
        testDevice = new Device();
        testDevice.setDevicename("Test device name");
        testDevice.setDevicetype("Test device type");
        testDevice.setPositions(null);
        deviceRepository.save(testDevice);

        testPosition = new Position();
        testPosition.setDeviceid(testDevice.getId());
        testPosition.setLatitude(10);
        testPosition.setLongitude(10);
        positionRepository.save(testPosition);
    }

    @AfterEach
    void end() {
        positionRepository.deleteAll();
        deviceRepository.deleteAll();
    }

    @Test
    @DisplayName("Test if correct POST on /positions returns code 201")
    void positionCorrectPostHttpStatusTest() throws Exception {

        var values = new HashMap<String, Object>() {{
            put("deviceid", testDevice.getId());
            put ("latitude", 12.3123);
            put("longitude", 12.3321);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        mockMvc.perform(post("/positions").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is(201));
    }

    @Test
    @DisplayName("Test if POST on /positions with non-existing id specified returns code 201")
    void positionWithNonExistingIdPostHttpStatusTest() throws Exception {

        var values = new HashMap<String, Object>() {{
            put("id", 88888);
            put("deviceid", testDevice.getId());
            put ("latitude", 12.3123);
            put("longitude", 12.3321);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        mockMvc.perform(post("/positions").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is(201));
    }

    @Test
    @DisplayName("Test if POST on /positions with existing id specified returns code 403")
    void positionWithExistingIdPostHttpStatusTest() throws Exception {

        var values = new HashMap<String, Object>() {{
            put("id", testPosition.getId());
            put("deviceid", testDevice.getId());
            put ("latitude", 12.3123);
            put("longitude", 12.3321);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        mockMvc.perform(post("/positions").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is(403));
    }

    @Test
    @DisplayName("Test if POST on /positions for non-existing device returns code 404")
    void positionIncorrectDevicePostHttpStatusTest() throws Exception {

        var values = new HashMap<String, Object>() {{
            put("deviceid", 99999);
            put ("latitude", 12.3123);
            put("longitude", 12.3321);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        mockMvc.perform(post("/positions").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    @DisplayName("Test if POST on /positions with incorrect latitude returns code 400")
    void positionIncorrectLatitudePostHttpStatusTest() throws Exception {

        var values = new HashMap<String, Object>() {{
            put("deviceid", testDevice.getId());
            put ("latitude", 123123);
            put("longitude", 12.3321);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        mockMvc.perform(post("/positions").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    @DisplayName("Test if POST on /positions with incorrect longitude returns code 400")
    void positionIncorrectLongitudePostHttpStatusTest() throws Exception {

        var values = new HashMap<String, Object>() {{
            put("deviceid", testDevice.getId());
            put ("latitude", 12.3123);
            put("longitude", 123321);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        mockMvc.perform(post("/positions").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is(400));
    }

}
