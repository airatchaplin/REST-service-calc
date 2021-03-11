package com.airatchaplin.restcalc.service;

import com.airatchaplin.restcalc.dto.ExpressionDTO;
import com.airatchaplin.restcalc.model.Expression;
import com.airatchaplin.restcalc.model.User;
import com.airatchaplin.restcalc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

@Service
public class ExpressionService {

    @Autowired
    UserRepository userRepository;

    public void addExpressionForUser(Expression expression) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setExpressions(Collections.singleton(expression));
        expression.setUser(user);
        expression.setTime(LocalTime.now());
        expression.setDate(LocalDate.now());
        userRepository.save(user);
    }

    public ExpressionDTO setExpressionDTO(Expression expression){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ExpressionDTO expressionDTO = new ExpressionDTO();
        expressionDTO.setExpression(expression.getExpression());
        expressionDTO.setPrecision(expression.getPrecision());
        expressionDTO.setDate(LocalDate.now());
        expressionDTO.setTime(LocalTime.now());
        return expressionDTO;
    }

}
