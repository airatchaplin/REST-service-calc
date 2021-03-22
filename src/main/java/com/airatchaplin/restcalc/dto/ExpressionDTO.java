package com.airatchaplin.restcalc.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ExpressionDTO {
    String expression;
    String precision;
    LocalTime time;
    LocalDate date;
    BigDecimal answer;

    @Override
    public String toString() {
        return "ExpressionDTO{" +
                "expression='" + expression + '\'' +
                ", precision='" + precision + '\'' +
                ", time=" + time +
                ", date=" + date +
                ", answer=" + answer +
                '}';
    }
}
