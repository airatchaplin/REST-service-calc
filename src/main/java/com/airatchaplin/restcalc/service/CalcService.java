package com.airatchaplin.restcalc.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

@Service
public class CalcService {

    public BigDecimal roundAvoid(BigDecimal value, int places) {
        String result =  value.setScale(places, RoundingMode.FLOOR).toString();
        double d = Double.parseDouble(result);
        result = String.valueOf(d);
        return new BigDecimal(result);
    }

    public String ReversePolishNotation(String s) {
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int priority = getPriority(s.charAt(i));
            if (priority == 0) {
                result.append(s.charAt(i));
            } else if (priority == 1) {
                stack.push((s.charAt(i)));
            } else if (priority > 1) {
                result.append(" ");
                while (!stack.empty()) {
                    if (getPriority(stack.peek()) >= priority) {
                        result.append(stack.pop());
                    } else {
                        break;
                    }
                }
                stack.push(s.charAt(i));
            } else if (priority == -1) {
                result.append(" ");
                while (getPriority(stack.peek()) != 1) {
                    result.append(stack.pop());
                }
                stack.pop();
            }
        }
        while (!stack.empty()) {
            result.append(stack.pop());
        }
        return result.toString();
    }

    public BigDecimal getAnswer(String s) {
        Stack<BigDecimal> stack = new Stack<>();
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                continue;
            }
            if (getPriority(s.charAt(i)) == 0) {
                while (s.charAt(i) != ' ' && getPriority(s.charAt(i)) == 0) {

                    string.append(s.charAt(i++));

                }
                stack.push(new BigDecimal(String.valueOf(string)));
                string = new StringBuilder("");
            }
            if (getPriority(s.charAt(i)) > 1) {
                BigDecimal a = stack.pop();
                BigDecimal b = stack.pop();

                if (s.charAt(i) == '+') {
                    stack.push(a.add(b));
                }
                if (s.charAt(i) == '-') {
                    stack.push(b.subtract(a));
                }
                if (s.charAt(i) == '/') {
                    if (a.toString().equals("0") || a.toString().equals("0.0")) {
                        throw new ArithmeticException("Ошибка, деление на 0!");
                    } else {
                        BigDecimal divideResult = (b).divide(a,100,RoundingMode.FLOOR);
                        stack.push(divideResult);
                    }
                }
                if (s.charAt(i) == '*') {

                    stack.push(b.multiply(a));
                }
                if (s.charAt(i) == '^') {
                    stack.push(b.pow(Integer.parseInt(a.toString())));
                }
            }

        }
        return stack.pop();
    }

    public boolean isValid(String s) {

        String regexNum = "[\\d\\s\\(\\)]";
        String regexZnak = "[\\-\\/\\*\\+]";
        int countNum = 0;
        int countZnak = 0;
        int countSkobka = 0;


        for (String c : s.split(regexNum)) {
            if (c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/") || c.equals("^")) {
                countZnak++;
            } else if (c.equals("")) {
                continue;
            } else {
                for (char z : c.toCharArray()) {
                    if (getPriority(z) > 0) {
                        countZnak++;
                    }
                }

            }
        }
        for (char c : s.toCharArray()) {
            if (c == '(' || c == ')') {
                countSkobka++;
            }
        }
        for (String c : s.split(regexZnak)) {
            if (c.equals("")) {
                continue;
            } else {
                countNum++;
            }
        }
        if (s.isEmpty()) {
            throw new IllegalArgumentException("Некорректный ввод выражения! Пустая строка!");
        }
        if (countNum <= countZnak) {
            throw new IllegalArgumentException("Некорректный ввод выражения! Провертье правильность расположения операторов!");
        }
        if (countNum == 1) {
            throw new IllegalArgumentException("Некорректный ввод выражения!");
        }
        if (countSkobka % 2 != 0) {
            throw new IllegalArgumentException("Некорректный ввод выражения! Проверьте правильность расположения скобок!");
        }

        return true;
    }

    public static int getPriority(char c) {
        if (c == '^') {
            return 4;
        } else if (c == '*' || c == '/') {
            return 3;
        } else if (c == '+' || c == '-') {
            return 2;
        } else if (c == '(') {
            return 1;
        } else if (c == ')') {
            return -1;
        } else
            return 0;
    }
}
