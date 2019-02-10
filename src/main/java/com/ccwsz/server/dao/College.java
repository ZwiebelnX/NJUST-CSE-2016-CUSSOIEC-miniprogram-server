package com.ccwsz.server.dao;

import com.ccwsz.server.dao.entity.CollegeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface College extends CrudRepository<CollegeEntity,Integer> {
    @Query("select c from CollegeEntity c where c.name=:name")
    List<CollegeEntity> getByName(@Param("name") String college);
}
