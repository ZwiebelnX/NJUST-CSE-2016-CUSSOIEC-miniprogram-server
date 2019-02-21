package com.ccwsz.server.dao.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class) //启动自动生成时间
@Table(name = "course_checking_in_info", schema = "online_cussoiec", catalog = "")
public class CourseCheckingInInfoEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private long courseId;
    private Timestamp beginningTime;

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
    @Column(name = "course_id", nullable = false)
    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "beginning_time", nullable = false)
    public Timestamp getBeginningTime() {
        return beginningTime;
    }

    public void setBeginningTime(Timestamp beginningTime) {
        this.beginningTime = beginningTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseCheckingInInfoEntity that = (CourseCheckingInInfoEntity) o;
        return id == that.id &&
                courseId == that.courseId &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(beginningTime, that.beginningTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gmtCreate, gmtModified, courseId, beginningTime);
    }
}
