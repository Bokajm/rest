package com.example.rest.controller;

import com.example.rest.model.Position;
import com.example.rest.service.DeviceService;
import com.example.rest.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
public class PositionController {

    private static final Logger logger = Logger.getLogger(PositionController.class.getName());

    private final DeviceService deviceService;
    private final PositionService positionService;

    @GetMapping("/positions")
    public List<Position> getPositions(@RequestParam(required = false) Integer pageIndex) {
        logger.info("Received GET request for positions, page: " + pageIndex);

        int page = 0;
        if(pageIndex != null && pageIndex > 0) {
            page = pageIndex;
        } else {
            logger.info("Invalid page index received, defaulting to 0");
        }

        logger.info("Sending response with positions, page: " + page);
        return positionService.getPositions(page);
    }

    @GetMapping("/positions/{id}")
    public ResponseEntity<Position> getPosition(@PathVariable long id) {
        logger.info("Received GET request for a position, position id: " + id);
        Position position = positionService.getPosition(id);
        if(position == null) {
            logger.info("Could not find position with id " + id + ", sending response");
            return new ResponseEntity<Position>(HttpStatus.NOT_FOUND);
        }

        logger.info("Sending response with position, id: " + id);
        return new ResponseEntity<Position>(position, HttpStatus.OK);
    }

    @PostMapping(value = "/positions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Position> addPosition(@RequestBody Position position) {
        logger.info("Received POST request with a position, position id: " + position.getId());
        if((deviceService.getDevice(position.getDeviceid())) == null) {
            logger.info("Device does not exist, sending response");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if((positionService.getPosition(position.getId())) != null) {
            logger.info("Position with this id already exists, editing not allowed, sending response");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(position.getLatitude() < -90 || position.getLatitude() > 90) {
            logger.info("Latitude is not a number between -90 and 90, sending response.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(position.getLongitude() < -180 || position.getLongitude() > 180) {
            logger.info("Longitude is not a number between -180 and 180, sending response.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Position resultPosition = positionService.addPosition(position);
        if(resultPosition == null) {
            logger.info("Could not process entity, sending response");
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            logger.info("Position added, position id: " + resultPosition.getId() + ", sending response");
            return new ResponseEntity<>(resultPosition, HttpStatus.CREATED);
        }
    }

}
