package com.ccwsz.server.dao.dock;

import com.ccwsz.server.dao.entity.UserHomeworkAnswerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserHomeworkAnswerRepository extends CrudRepository<UserHomeworkAnswerEntity, Integer> {
    List<UserHomeworkAnswerEntity> findByUserId(long userId);
}
