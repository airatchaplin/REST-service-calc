package com.airatchaplin.restcalc.rest;

import com.airatchaplin.restcalc.dto.ExpressionDTO;
import com.airatchaplin.restcalc.dto.UserDTO;
import com.airatchaplin.restcalc.model.Expression;
import com.airatchaplin.restcalc.service.CalcService;
import com.airatchaplin.restcalc.service.ExpressionService;
import com.airatchaplin.restcalc.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RestContr {

    @Autowired
    ExpressionService expressionService;

    @Autowired
    CalcService calcService;

    @Autowired
    UserService userService;

    ArrayList<String> list = new ArrayList<>(10);

    @PostMapping("/add")
    public List<String> add(@RequestBody Expression expression) throws Exception {
        double result = 0;
        if (calcService.isValid(expression.getExpression())) {
            result = calcService.getAnswer(calcService.ReversePolishNotation(expression.getExpression()));
            result = calcService.roundAvoid(result, Integer.parseInt(expression.getPrecision()));
        }

        ExpressionDTO expressionDTO = expressionService.setExpressionDTO(expression);
        String allAnswer = expressionDTO.toString() + " Ответ = " + result;
        if (list.size()<10){
            list.add(allAnswer);
        }else {
            for (String s : list){
                list.remove(s);
                list.add(allAnswer);
                break;
            }
        }

        expressionService.addExpressionForUser(expression);
        return list;
    }

    @GetMapping("/{username}")
    public UserDTO show(@PathVariable String username) throws NotFoundException {
        return userService.getUserDTO(username);
    }

}
