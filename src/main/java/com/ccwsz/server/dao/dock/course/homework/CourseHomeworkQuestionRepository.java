package com.ccwsz.server.dao.dock.course.homework;

import com.ccwsz.server.dao.entity.CourseHomeworkQuestionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseHomeworkQuestionRepository extends CrudRepository<CourseHomeworkQuestionEntity, Integer> {
    //通过homeworkID找到对应的问题
    List<CourseHomeworkQuestionEntity> findByHomeworkId(long homeworkId);

    //删除所有问题
    boolean deleteAllByHomeworkId(long homeworkId);
}
