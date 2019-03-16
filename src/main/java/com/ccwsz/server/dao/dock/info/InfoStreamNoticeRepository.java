package com.ccwsz.server.dao.dock.info;

import com.ccwsz.server.dao.entity.InfoStreamNoticeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface InfoStreamNoticeRepository extends CrudRepository<InfoStreamNoticeEntity, Long> {
    List<InfoStreamNoticeEntity> findAllByTargetCourseIdInOrTargetCourseIdIsNullOrderByGmtModified(Collection courseIds);

    List<InfoStreamNoticeEntity> findAllByType(String type);
}
