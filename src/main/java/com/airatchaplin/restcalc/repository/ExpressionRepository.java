package com.airatchaplin.restcalc.repository;

import com.airatchaplin.restcalc.model.Expression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;

public interface ExpressionRepository extends JpaRepository<Expression,Long> {

    @Query(value = "SELECT Expression.answer FROM Expression where expression = ?1",nativeQuery = true)
    BigDecimal findByAnswer_ExpressionLike(String answer);
}
