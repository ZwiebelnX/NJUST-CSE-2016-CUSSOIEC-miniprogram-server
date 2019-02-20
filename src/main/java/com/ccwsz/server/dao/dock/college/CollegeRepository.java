package com.ccwsz.server.dao.dock.college;

import com.ccwsz.server.dao.entity.CollegeEntity;
import org.springframework.data.repository.CrudRepository;

public interface CollegeRepository extends CrudRepository<CollegeEntity,Integer> {
    CollegeEntity findByName(String collegeName);
}
