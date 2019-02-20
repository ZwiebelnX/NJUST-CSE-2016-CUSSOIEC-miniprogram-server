package com.ccwsz.server.dao.dock.course.checking;

import com.ccwsz.server.dao.entity.UserCourseCheckingInEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface UserCourseCheckingInRepository extends CrudRepository<UserCourseCheckingInEntity,Integer> {
    @Nullable
    UserCourseCheckingInEntity findByCheckingInfoIdAndUserId(long checkingInfoId, long userId);

}
