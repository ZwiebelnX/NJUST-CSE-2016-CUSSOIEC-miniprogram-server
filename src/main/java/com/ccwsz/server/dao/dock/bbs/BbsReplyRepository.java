package com.ccwsz.server.dao.dock.bbs;

import com.ccwsz.server.dao.entity.BbsReplyEntity;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BbsReplyRepository extends CrudRepository<BbsReplyEntity,Integer> {
    //
    List<BbsReplyEntity> findByTopicId(Pageable pageable,long topicId);
}
