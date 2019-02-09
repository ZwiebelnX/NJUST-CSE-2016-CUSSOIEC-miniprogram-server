package com.ccwsz.server.dao.repository;

import com.ccwsz.server.dao.entity.CourseChooseEntity;
import com.ccwsz.server.dao.entity.CourseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CourseChooseDB extends CrudRepository<CourseChooseEntity,Integer> {
    //通过学生id返回所有选课信息
    @Query("select c from CourseChooseEntity c where c.studentId=:id")
    List<CourseChooseEntity> getCourseChooseEntitiesByStudentId(@Param("id") long studentid);
    //通过课程id返回课程信息
    @Query("select  n from  CourseEntity n where n.id=:id")
    CourseEntity getCourseEntityById(@Param("id") long courseId);
}
