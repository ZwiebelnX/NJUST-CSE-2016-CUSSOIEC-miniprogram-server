package com.ccwsz.server.dao.dock.course.checking;

import com.ccwsz.server.dao.entity.CourseCheckingInInfoEntity;
import org.omg.CORBA.TIMEOUT;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.util.List;

public interface CourseCheckingInInfoRepository extends CrudRepository<CourseCheckingInInfoEntity, Integer> {
    CourseCheckingInInfoEntity findById(long id);

    List<CourseCheckingInInfoEntity> findByCourseIdOrderByBeginningTimeDesc(long courseId);

    @Nullable
    CourseCheckingInInfoEntity findByCourseIdAndBeginningTime(long courseId, Timestamp time);

    //删除指定签到
    void deleteByCourseIdAndBeginningTime(long courseId, Timestamp time);

    boolean existsByCourseIdAndBeginningTime(long courseId, Timestamp time);
}
