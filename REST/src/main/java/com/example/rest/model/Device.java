package com.example.rest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String devicename;
    private String devicetype;

    @OneToMany
    @JoinColumn(name = "deviceid")
    private List<Position> positions;
}
