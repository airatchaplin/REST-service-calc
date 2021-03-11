package com.airatchaplin.restcalc.repository;

import com.airatchaplin.restcalc.model.Expression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpressionRepository extends JpaRepository<Expression,Long> {
}
