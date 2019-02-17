package com.ccwsz.server.dao.dock;

import com.ccwsz.server.dao.entity.CourseCheckingInEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

public interface CourseCheckingInRepository extends CrudRepository<CourseCheckingInEntity,Integer> {
    @Nullable
    CourseCheckingInEntity findByUserIdAndCourseId(Long userId,Long courseId);
}
