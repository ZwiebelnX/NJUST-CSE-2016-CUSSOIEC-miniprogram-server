package com.ccwsz.server.dao.dock;

import com.ccwsz.server.dao.entity.CourseChooseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface CourseChooseRepository extends CrudRepository<CourseChooseEntity,Integer> {
    //通过学生id返回所有选课信息
    @Nullable
    List<CourseChooseEntity> findByStudentId(long studentId);

    @Nullable
    CourseChooseEntity findByStudentIdAndCourseId(long studentId,long courseId);
}
