package com.ccwsz.server.dao.dock.bbs;

import com.ccwsz.server.dao.entity.BbsTopicEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BbsTopicRepository extends CrudRepository<BbsTopicEntity,Integer> {
    boolean existsById(long topicId);
    @Query(value = "select * from bbs_topic where course_id=:an", nativeQuery = true)
    List<BbsTopicEntity> findTopics(Pageable pageable, @Param("an") long userId);
}
