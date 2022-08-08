package com.example.rest.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeviceDto {
    private long id;
    private String devicename;
    private String devicetype;
}
