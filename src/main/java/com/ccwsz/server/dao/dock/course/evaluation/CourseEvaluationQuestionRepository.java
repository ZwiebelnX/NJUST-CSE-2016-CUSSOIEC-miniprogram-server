package com.ccwsz.server.dao.dock.course.evaluation;

import com.ccwsz.server.dao.entity.CourseEvaluationQuestionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface CourseEvaluationQuestionRepository extends CrudRepository<CourseEvaluationQuestionEntity,Integer> {
    List<CourseEvaluationQuestionEntity> findByCourseId(long courseId);
}
