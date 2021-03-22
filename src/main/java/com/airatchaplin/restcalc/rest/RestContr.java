package com.airatchaplin.restcalc.rest;

import com.airatchaplin.restcalc.dto.ExpressionDTO;
import com.airatchaplin.restcalc.dto.UserDTO;
import com.airatchaplin.restcalc.model.Expression;
import com.airatchaplin.restcalc.service.CalcService;
import com.airatchaplin.restcalc.service.ExpressionService;
import com.airatchaplin.restcalc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
public class RestContr {

    @Autowired
    ExpressionService expressionService;

    @Autowired
    CalcService calcService;

    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    UserDetailsService userDetailsService;

    @PostMapping("/login")
    void login(@RequestParam("username") String username){
        userDetailsService.loadUserByUsername(username);
    }

    @GetMapping("/home")
    public String homePage(){
        User user;
        String username ="";
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            username = user.getUsername();
        } catch (Exception e) {
            e.printStackTrace();
        }
       if (username.equals("")) {
           return "Hello unauthorized";
       }else {
           return "Hello " + username;
       }
    }

    @PostMapping("/api/addExpression")
    public ExpressionDTO add(@RequestBody Expression expression){
        BigDecimal result;
        result =expressionService.chekingExpressionInDataBase(expression.getExpression());
        if (result == null) {
            if (calcService.isValid(expression.getExpression())) {
                result = calcService.getAnswer(calcService.ReversePolishNotation(expression.getExpression()));
                result = calcService.roundAvoid(result, Integer.parseInt(expression.getPrecision()));
            }
        }
        ExpressionDTO expressionDTO = expressionService.setExpressionDTO(expression, result);
        expressionService.addExpressionForUser(expression, result);
        return expressionDTO;
    }

    @GetMapping("/api/show/{username}")
    public UserDTO show(@PathVariable String username){
            return userService.getUserDTO(username);
    }

}
