package com.example.rest.postTests.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
public class PostDeviceResponseTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test if correct POST on /devices returns desired body")
    void deviceCorrectPostResponseBodyTest() throws Exception {

        var values = new HashMap<String, String>() {{
            put("devicename", "testname");
            put("devicetype", "testtype");
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        mockMvc.perform(post("/devices").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(jsonPath("$.devicename", Matchers.is(values.get("devicename"))))
                .andExpect(jsonPath("$.devicetype", Matchers.is(values.get("devicetype"))));
    }

    @Test
    @DisplayName("Test if POST on /devices with non-existing id returns desired body")
    void deviceWithNonExistingIdPostHttpStatusTest() throws Exception {
        var values = new HashMap<String, Object>() {{
            put("id", 8888);
            put("devicename", "testname");
            put("devicetype", "testtype");
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        mockMvc.perform(post("/devices").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(jsonPath("$.devicename", Matchers.is(values.get("devicename"))))
                .andExpect(jsonPath("$.devicetype", Matchers.is(values.get("devicetype"))));
    }

}
