package com.ccwsz.server.dao.dock.info;

import com.ccwsz.server.dao.entity.InfoStreamScoreEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InfoStreamScoreRepository extends CrudRepository<InfoStreamScoreEntity, Long> {
}
