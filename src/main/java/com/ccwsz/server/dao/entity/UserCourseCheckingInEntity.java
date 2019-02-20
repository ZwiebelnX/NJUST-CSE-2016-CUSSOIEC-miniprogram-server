package com.ccwsz.server.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "user_course_checking_in", schema = "online_cussoiec", catalog = "")
public class UserCourseCheckingInEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private long userId;
    private byte checkingStatus;
    private long checkingInfoId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "gmt_create", nullable = false)
    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Basic
    @Column(name = "gmt_modified", nullable = false)
    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "checking_status", nullable = false, length = 10)
    public byte getCheckingStatus() {
        return checkingStatus;
    }

    public void setCheckingStatus(byte checkingStatus) {
        this.checkingStatus = checkingStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCourseCheckingInEntity that = (UserCourseCheckingInEntity) o;
        return id == that.id &&
                userId == that.userId &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(checkingStatus, that.checkingStatus) &&
                Objects.equals(checkingInfoId, that.checkingInfoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gmtCreate, gmtModified, userId, checkingStatus, checkingInfoId);
    }

    @Basic
    @Column(name = "checking_info_id", nullable = false)
    public long getCheckingInfoId() {
        return checkingInfoId;
    }

    public void setCheckingInfoId(long checkingInfoId) {
        this.checkingInfoId = checkingInfoId;
    }
}
