package com.ccwsz.server.dao.dock;

import com.ccwsz.server.dao.entity.CourseChooseEntity;
import com.ccwsz.server.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Null;
import java.sql.Timestamp;
import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity,Integer> {
//    @Query("select u from UserEntity u where u.personNumber=:id and u.college=:collegeName")
//    UserEntity findByCollegeAndPersonNumber(@Param("collegeName")String collegeName,@Param("id") String personId);
    @Nullable
    UserEntity findByCollegeAndPersonNumber(String college, String personNumber);

//    @Query("select u from UserEntity u where u.openid=:id")
//    UserEntity findByOpenid(@Param("id") String openId);
    @Nullable
    UserEntity findByOpenid(String openId);
    @Modifying
    @Query("update UserEntity u set u.college=:ai where u.openid=:id")
    void updateUserPersonNumber(@Param("id")String id, @Param("ai")String personID);
    @Modifying
    @Query("update UserEntity u set u.college=:ai where u.openid=:id")
    void updateUserCollege(@Param("id")String id, @Param("ai")String college);

    @Modifying
    @Query("update UserEntity u set u.college=:ai where u.openid=:id")
    void updateUserRealName(@Param("id")String id, @Param("ai")String realName);

    @Modifying
    @Query("update UserEntity u set u.college=:ai where u.openid=:id")
    void updateUserNickName(@Param("id")String id, @Param("ai")String nickName);

    @Modifying
    @Query("update UserEntity u set u.getModified=:ai where u.openid=:id")
    void  updateUserGetModified(@Param("id")String id, @Param("ai")Timestamp nowTimestamp);

    @Modifying
    @Query("update UserEntity u set u.gmtCreate=:ai where u.openid=:id")
    void  updateUserGmtCreate(@Param("id")String id, @Param("ai")Timestamp nowTimestamp);
}
