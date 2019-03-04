package com.ccwsz.server.dao.dock.bbs;

import com.ccwsz.server.dao.entity.BbsSectorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

public interface BbsSectorRepository extends CrudRepository<BbsSectorEntity,Integer> {
    @Nullable
    BbsSectorEntity findByCourseId(long courseId);
}
