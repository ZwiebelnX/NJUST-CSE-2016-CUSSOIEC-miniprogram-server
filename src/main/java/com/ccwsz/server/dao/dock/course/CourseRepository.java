package com.ccwsz.server.dao.dock.course;

import com.ccwsz.server.dao.entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

public interface CourseRepository extends CrudRepository<CourseEntity,Integer> {
    //通过课程id返回课程信息
    @Nullable
    CourseEntity findById(long courseId);

    @Nullable
    CourseEntity findByCourseNumber(String CourseNumber);

    boolean existsById(long courseId);

}
