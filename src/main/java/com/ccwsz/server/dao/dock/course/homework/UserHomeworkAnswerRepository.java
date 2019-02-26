package com.ccwsz.server.dao.dock.course.homework;

import com.ccwsz.server.dao.entity.UserHomeworkAnswerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface UserHomeworkAnswerRepository extends CrudRepository<UserHomeworkAnswerEntity, Integer> {
    List<UserHomeworkAnswerEntity> findByUserId(long userId);

    List<UserHomeworkAnswerEntity> findByHomeworkIdAndUserId(long homeworkId, long userId);

    boolean existsByUserIdAndHomeworkId(long userId);
}
