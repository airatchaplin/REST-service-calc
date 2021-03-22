package com.airatchaplin.restcalc.service;

import com.airatchaplin.restcalc.dto.ExpressionDTO;
import com.airatchaplin.restcalc.model.Expression;
import com.airatchaplin.restcalc.repository.ExpressionRepository;
import com.airatchaplin.restcalc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

@Service
public class ExpressionService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExpressionRepository expressionRepository;

    public void addExpressionForUser(Expression expression,BigDecimal answer) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.airatchaplin.restcalc.model.User userInDatabase = userRepository.findByUsername(user.getUsername());
        userInDatabase.setExpressions(Collections.singleton(expression));
        expression.setUser(Collections.singleton(userInDatabase));
        expression.setTime(LocalTime.now());
        expression.setDate(LocalDate.now());
        expression.setAnswer(answer);
        expressionRepository.save(expression);
    }

    public ExpressionDTO setExpressionDTO(Expression expression, BigDecimal answer){
        ExpressionDTO expressionDTO = new ExpressionDTO();
        expressionDTO.setExpression(expression.getExpression());
        expressionDTO.setPrecision(expression.getPrecision());
        expressionDTO.setDate(LocalDate.now());
        expressionDTO.setTime(LocalTime.now());
        expressionDTO.setAnswer(answer);
        return expressionDTO;
    }

    public BigDecimal chekingExpressionInDataBase(String expression){
        return expressionRepository.findByAnswer_ExpressionLike(expression);
    }
}
