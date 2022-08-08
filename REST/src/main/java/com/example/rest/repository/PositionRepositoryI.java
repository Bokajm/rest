package com.example.rest.repository;

import com.example.rest.model.Position;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepositoryI extends JpaRepository<Position, Long> {

    @Query("SELECT p FROM Position p")
    List<Position> findAllPositions(Pageable page);

    List<Position> findAllByDeviceidIn(List<Long> ids);
}
