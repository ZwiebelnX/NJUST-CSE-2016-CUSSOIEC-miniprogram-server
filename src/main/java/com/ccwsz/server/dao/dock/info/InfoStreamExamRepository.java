package com.ccwsz.server.dao.dock.info;

import com.ccwsz.server.dao.entity.InfoStreamExamEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface InfoStreamExamRepository extends CrudRepository<InfoStreamExamEntity, Long> {

    List<InfoStreamExamEntity> findAllByTargetUserIdOrderByGmtModified(long userId);
}
