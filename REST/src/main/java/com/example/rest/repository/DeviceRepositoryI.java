package com.example.rest.repository;

import com.example.rest.model.Device;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepositoryI extends JpaRepository<Device, Long> {

    @Query("SELECT d FROM Device d")
    List<Device> findAllDevices(Pageable page);
}
