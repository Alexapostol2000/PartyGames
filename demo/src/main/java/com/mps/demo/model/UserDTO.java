package com.mps.demo.model;

import lombok.Data;

@Data
public class UserDTO {

    private String name;

    private UserRole userRole;

    private Long totalScore;

    private String jwt;
}
