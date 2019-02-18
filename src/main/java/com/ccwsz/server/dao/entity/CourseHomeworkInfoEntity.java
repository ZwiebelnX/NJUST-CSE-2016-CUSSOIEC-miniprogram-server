package com.ccwsz.server.dao.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "course_homework_info", schema = "online_cussoiec", catalog = "")
public class CourseHomeworkInfoEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private long courseId;
    private Date deadLine;
    private String name;

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
    @Column(name = "course_id", nullable = false)
    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "dead\u001Fline", nullable = true)
    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseHomeworkInfoEntity that = (CourseHomeworkInfoEntity) o;
        return id == that.id &&
                courseId == that.courseId &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(deadLine, that.deadLine) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gmtCreate, gmtModified, courseId, deadLine, name);
    }
}
