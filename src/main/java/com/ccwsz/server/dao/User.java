package com.ccwsz.server.dao;

import com.ccwsz.server.dao.entity.CourseChooseEntity;
import com.ccwsz.server.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface User extends CrudRepository<UserEntity,Integer> {
    @Query("select u from UserEntity u where u.personNumber=:id and u.college=:collegeName")
    UserEntity getUserEntitiesByStudentNumber(@Param("id") String personId,@Param("collegeName")String collegeName);

    @Query("select u from UserEntity u where u.openid=:id")
    UserEntity getUserByOpenId(@Param("id") String openId);

    @Modifying
    @Query("update UserEntity u set u.college=:ai where u.openid=:id")
    public void updateUserPersonNumber(@Param("id")String id, @Param("ai")String personID);

    @Modifying
    @Query("update UserEntity u set u.college=:ai where u.openid=:id")
    public void updateUserCollege(@Param("id")String id, @Param("ai")String college);

    @Modifying
    @Query("update UserEntity u set u.college=:ai where u.openid=:id")
    public void updateUserRealName(@Param("id")String id, @Param("ai")String realName);

    @Modifying
    @Query("update UserEntity u set u.college=:ai where u.openid=:id")
    public void updateUserNickName(@Param("id")String id, @Param("ai")String nickName);

    @Modifying
    @Query("update UserEntity u set u.getModified=:ai where u.openid=:id")
    public void  updateUserGetModified(@Param("id")String id, @Param("ai")Timestamp nowTimestamp);

    @Modifying
    @Query("update UserEntity u set u.gmtCreate=:ai where u.openid=:id")
    public void  updateUserGmtCreate(@Param("id")String id, @Param("ai")Timestamp nowTimestamp);
}
