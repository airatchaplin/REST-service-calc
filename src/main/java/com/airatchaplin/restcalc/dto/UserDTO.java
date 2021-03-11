package com.airatchaplin.restcalc.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    String username;
    List<ExpressionDTO> expressions;

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", expressions=" + expressions +
                '}';
    }
}
