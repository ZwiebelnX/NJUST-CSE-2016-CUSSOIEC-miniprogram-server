package com.ccwsz.server.dao.dock.course.homework;

import com.ccwsz.server.dao.entity.CourseHomeworkQuestionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseHomeworkQuestionRepository extends CrudRepository<CourseHomeworkQuestionEntity, Integer> {
    List<CourseHomeworkQuestionEntity> findByHomeworkId(long homeworkId);
}
