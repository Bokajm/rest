package com.example.rest.getTests.mediaType;

import com.example.rest.model.Device;
import com.example.rest.model.Position;
import com.example.rest.repository.DeviceRepositoryI;
import com.example.rest.repository.PositionRepositoryI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
public class GetPositionResponseMediaTypeTests {

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
    @DisplayName("Test if GET on /positions returns application/json media type")
    void positionsMediaTypeTest() throws Exception {
        mockMvc.perform(get("/positions")).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Test if GET on /positions with page specified returns application/json media type")
    void positionsWithPageMediaTypeTest() throws Exception {
        mockMvc.perform(get("/positions?pageIndex=0")).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Test if GET on /positions/index returns application/json media type")
    void singlePositionMediaTypeTest() throws Exception {
        mockMvc.perform(get("/positions/" + testPosition.getId())).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
