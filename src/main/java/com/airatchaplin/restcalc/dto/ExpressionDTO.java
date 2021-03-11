package com.airatchaplin.restcalc.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Data
public class ExpressionDTO {
    String expression;
    String precision;
    LocalTime time;
    LocalDate date;

    @Override
    public String toString() {
        return "ExpressionDTO : " +
                "expression = '" + expression + '\'' +
                ", precision = '" + precision + '\'' +
                ", time = " + time +
                ", date = " + date;
    }
}
