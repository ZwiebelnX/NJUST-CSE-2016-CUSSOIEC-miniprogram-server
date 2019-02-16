package com.ccwsz.server.dao.dock;

import com.ccwsz.server.dao.entity.CourseVideoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseVideoRepository extends CrudRepository<CourseVideoEntity, Integer> {
    //返回一个课程的所有视频信息
    List<CourseVideoEntity> findByCourseId(long courseId);
}
