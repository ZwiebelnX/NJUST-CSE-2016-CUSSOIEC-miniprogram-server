package com.ccwsz.server.dao.dock.bbs;

import com.ccwsz.server.dao.entity.BbsTopicEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BbsTopicRepository extends CrudRepository<BbsTopicEntity,Integer> {
    boolean existsById(long topicId);
    List<BbsTopicEntity> findByCourseId(Pageable pageable,long courseId);
}
