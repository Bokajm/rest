package com.example.rest.getTests.status;

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
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetPositionHttpStatusTests {

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
    @DisplayName("Test if GET on /positions returns code 200")
    void positionsHttpStatusTest() throws Exception {
        mockMvc.perform(get("/positions")).andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("Test if GET on /positions with page specified returns code 200")
    void positionsWithPageHttpStatusTest() throws Exception {
        mockMvc.perform(get("/positions?pageIndex=0")).andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("Test if GET on /positions/index returns code 200 when device exists")
    void singlePositionCorrectHttpStatusTest() throws Exception {
        mockMvc.perform(get("/positions/" + testPosition.getId())).andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("Test if GET on /positions/index returns code 404 when device does not exist")
    void singlePositionIncorrectHttpStatusTest() throws Exception {
        mockMvc.perform(get("/positions/-500")).andDo(print())
                .andExpect(status().is(404));
    }

}
