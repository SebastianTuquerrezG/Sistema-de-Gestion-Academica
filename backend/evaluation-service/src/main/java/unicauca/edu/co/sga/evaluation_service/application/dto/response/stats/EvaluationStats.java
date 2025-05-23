package unicauca.edu.co.sga.evaluation_service.application.dto.response.stats;

import lombok.Getter;

import java.math.BigDecimal;

public record EvaluationStats(BigDecimal score, String raName) {
}