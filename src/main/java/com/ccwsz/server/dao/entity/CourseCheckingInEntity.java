package com.ccwsz.server.dao.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class) //启动自动生成时间
@Table(name = "course_checking_in", schema = "online_cussoiec", catalog = "")
public class CourseCheckingInEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private String checkingTime;
    private String checkingStatus;
    private long courseId;
    private long userId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @CreatedDate
    @Column(name = "gmt_create", nullable = false)
    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Basic
    @LastModifiedDate
    @Column(name = "gmt_modified", nullable = false)
    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Basic
    @Column(name = "checking_time", nullable = false, length = 10)
    public String getCheckingTime() {
        return checkingTime;
    }

    public void setCheckingTime(String checkingTime) {
        this.checkingTime = checkingTime;
    }

    @Basic
    @Column(name = "checking_status", nullable = false, length = 10)
    public String getCheckingStatus() {
        return checkingStatus;
    }

    public void setCheckingStatus(String checkingStatus) {
        this.checkingStatus = checkingStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseCheckingInEntity that = (CourseCheckingInEntity) o;
        return id == that.id &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(checkingTime, that.checkingTime) &&
                Objects.equals(checkingStatus, that.checkingStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gmtCreate, gmtModified, checkingTime, checkingStatus);
    }

    @Basic
    @Column(name = "course_id", nullable = false)
    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
