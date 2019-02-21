package com.ccwsz.server.dao.dock.course.checking;

import com.ccwsz.server.dao.entity.CourseCheckingInInfoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.util.List;

public interface CourseCheckingInInfoRepository extends CrudRepository<CourseCheckingInInfoEntity, Integer> {
    List<CourseCheckingInInfoEntity> findByCourseIdOrderByBeginningTimeDesc(long courseId);

    @Nullable
    CourseCheckingInInfoEntity findByBeginningTime(Timestamp time);
}
