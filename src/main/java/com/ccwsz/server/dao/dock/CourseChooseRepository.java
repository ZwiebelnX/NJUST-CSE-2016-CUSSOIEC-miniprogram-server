package com.ccwsz.server.dao.dock;

import com.ccwsz.server.dao.entity.CourseChooseEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CourseChooseRepository extends CrudRepository<CourseChooseEntity,Integer> {
    //通过学生id返回所有选课信息
    List<CourseChooseEntity> findByStudentId(long studentId);

}
