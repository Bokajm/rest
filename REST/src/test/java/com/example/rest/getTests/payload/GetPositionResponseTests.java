package com.example.rest.getTests.payload;

import com.example.rest.model.Device;
import com.example.rest.model.Position;
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
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class GetPositionResponseTests {

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
    @DisplayName("Test if GET on /positions/index returns desired body")
    void deviceResponseTest() throws Exception {
        mockMvc.perform(get("/positions/" + testPosition.getId())).andDo(print())
                .andExpect(jsonPath("$.id", Matchers.is(testPosition.getId()), Long.class))
                .andExpect(jsonPath("$.deviceid", Matchers.is(testDevice.getId()), Long.class))
                .andExpect(jsonPath("$.latitude", Matchers.is(testPosition.getLatitude())))
                .andExpect(jsonPath("$.longitude", Matchers.is(testPosition.getLongitude())));
    }
}
