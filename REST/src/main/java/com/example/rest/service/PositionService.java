package com.example.rest.service;

import com.example.rest.model.Device;
import com.example.rest.model.Position;
import com.example.rest.repository.PositionRepositoryI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {

    private static final int pageSize = 10;
    private final PositionRepositoryI positionRepository;

    public List<Position> getPositions(int pageIndex) {
        return positionRepository.findAllPositions(PageRequest.of(pageIndex, pageSize));
    }

    public Position getPosition(long id) {
        return positionRepository.findById(id).orElse(null);
    }

    public Position addPosition(Position position) {
        return positionRepository.save(position);
    }
}
