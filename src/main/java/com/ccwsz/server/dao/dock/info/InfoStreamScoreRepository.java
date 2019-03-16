package com.ccwsz.server.dao.dock.info;

import com.ccwsz.server.dao.entity.InfoStreamScoreEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InfoStreamScoreRepository extends CrudRepository<InfoStreamScoreEntity, Long> {
    List<InfoStreamScoreEntity> findAllByTargetUserId(long userId);

}
