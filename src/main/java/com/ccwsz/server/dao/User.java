package com.ccwsz.server.dao;

import com.ccwsz.server.dao.entity.CourseChooseEntity;
import com.ccwsz.server.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface User extends CrudRepository<UserEntity,Integer> {
    @Query("select u from UserEntity u where u.personNumber=:id and u.college=:collegeName")
    UserEntity getUserEntitiesByStudentNumber(@Param("id") String personId,@Param("collegeName")String collegeName);

    @Query("select u from UserEntity u where u.openid=:id")
    UserEntity getStudentIdByOpenId(@Param("id") String openId);
}
