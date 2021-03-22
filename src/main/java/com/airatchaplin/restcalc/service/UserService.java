package com.airatchaplin.restcalc.service;

import com.airatchaplin.restcalc.dto.ExpressionDTO;
import com.airatchaplin.restcalc.dto.UserDTO;
import com.airatchaplin.restcalc.exceptions.NotFoundException;
import com.airatchaplin.restcalc.model.Expression;
import com.airatchaplin.restcalc.model.User;
import com.airatchaplin.restcalc.repository.ExpressionRepository;
import com.airatchaplin.restcalc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExpressionRepository expressionRepository;

    public UserDTO getUserDTO(String username) {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("There is no user with this name in the database!");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        List<Expression> list = expressionRepository.findAll();
        List<ExpressionDTO> expressionDTOList = new ArrayList<>();

        for (Expression expression: list){
            ExpressionDTO expressionDTO = new ExpressionDTO();
            Set<User> userSet =  expression.getUser();
            for (User us : userSet) {
                if (us.getUsername().equals(username)) {
                    expressionDTO.setExpression(expression.getExpression());
                    expressionDTO.setPrecision(expression.getPrecision());
                    expressionDTO.setDate(expression.getDate());
                    expressionDTO.setTime(expression.getTime());
                    expressionDTOList.add(expressionDTO);
                }
            }
        }
        userDTO.setExpressions(expressionDTOList);
        return userDTO;
    }

}
