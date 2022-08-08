package com.example.rest.service;

import com.example.rest.model.Device;
import com.example.rest.model.Position;
import com.example.rest.repository.DeviceRepositoryI;
import com.example.rest.repository.PositionRepositoryI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private static final int pageSize = 3;
    private final DeviceRepositoryI deviceRepository;
    private final PositionRepositoryI positionRepositoryI;

    public List<Device> getDevices(int pageIndex) {
        return deviceRepository.findAllDevices(PageRequest.of(pageIndex, pageSize));
    }

    public Device getDevice(long id) {
        return deviceRepository.findById(id).orElse(null);
    }

    public Device addDevice(Device device) {
        return deviceRepository.save(device);
    }

    public List<Device> getFullDevices(int pageIndex) {
        List<Device> devices = deviceRepository.findAllDevices(PageRequest.of(pageIndex, pageSize));
        List<Long> ids = devices.stream()
                .map(device -> device.getId())
                .collect(Collectors.toList());
        List<Position> positions = positionRepositoryI.findAllByDeviceidIn(ids);
        devices.forEach(device -> device.setPositions(findPositions(positions, device.getId())));
        return devices;
    }

    private List<Position> findPositions(List<Position> positions, long id) {
        return positions.stream()
                .filter(position -> position.getDeviceid() == id)
                .collect(Collectors.toList());
    }
}
