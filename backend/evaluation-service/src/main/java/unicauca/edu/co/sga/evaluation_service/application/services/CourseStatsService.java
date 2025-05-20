package unicauca.edu.co.sga.evaluation_service.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CourseStatsDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.SubjectRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.utils.StatsCalculator;

import java.util.List;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CourseStatsService {

    private final EvaluationRepository evaluationRepo;
    private final SubjectRepository subjectRepo;

    public CourseStatsDTO calculateCourseStats(Long courseId, Long rubricId, String semester) {
        String subjectName = subjectRepo.findSubjectNameByCourseId(courseId);
        List<BigDecimal> scores = evaluationRepo.findScoresByCourseRubricAndSemester(
                courseId, rubricId, semester);

        if (scores.isEmpty()) {
            throw new IllegalArgumentException("No hay evaluaciones para los criterios");
        }

        BigDecimal average = StatsCalculator.calculateAverage(scores);
        BigDecimal median = StatsCalculator.calculateMedian(scores);
        BigDecimal stdDev = StatsCalculator.calculateStandardDeviation(scores, average);
        BigDecimal min = scores.stream().min(BigDecimal::compareTo).get();
        BigDecimal max = scores.stream().max(BigDecimal::compareTo).get();

        return CourseStatsDTO.builder()
                .courseId(courseId)
                .subjectName(subjectName)
                .semester(semester)
                .average(average)
                .median(median)
                .standardDeviation(stdDev)
                .minScore(min)
                .maxScore(max)
                .studentsCount(scores.size())
                .build();
    }
}
