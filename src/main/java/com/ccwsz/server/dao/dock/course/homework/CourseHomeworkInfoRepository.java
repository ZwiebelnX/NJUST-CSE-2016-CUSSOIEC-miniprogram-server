package com.ccwsz.server.dao.dock.course.homework;

import com.ccwsz.server.dao.entity.CourseHomeworkInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseHomeworkInfoRepository extends CrudRepository<CourseHomeworkInfoEntity, Integer> {
    List<CourseHomeworkInfoEntity> findByCourseId(long courseId);

    boolean existsById(long homeworkId);

    boolean deleteAllById(long Id);
}
