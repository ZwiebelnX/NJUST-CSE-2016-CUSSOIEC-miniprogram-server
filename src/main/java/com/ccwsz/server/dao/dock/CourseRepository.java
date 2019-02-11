package com.ccwsz.server.dao.dock;

import com.ccwsz.server.dao.entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<CourseEntity,Integer> {
    //通过课程id返回课程信息
    CourseEntity findById(long courseId);
}