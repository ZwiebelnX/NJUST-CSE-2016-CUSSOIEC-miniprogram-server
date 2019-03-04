package com.ccwsz.server.dao.dock.info;

import com.ccwsz.server.dao.entity.InfoStreamBbsReplyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InfoStreamBbsReplyRepository extends PagingAndSortingRepository<InfoStreamBbsReplyEntity, Integer> {
}
