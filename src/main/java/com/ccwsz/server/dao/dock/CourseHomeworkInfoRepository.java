package com.ccwsz.server.dao.dock;

import com.ccwsz.server.dao.entity.CourseHomeworkInfoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface CourseHomeworkInfoRepository extends CrudRepository<CourseHomeworkInfoEntity, Integer> {
    @Nullable
    List<CourseHomeworkInfoEntity> findByCourseId(long courseId);
}
