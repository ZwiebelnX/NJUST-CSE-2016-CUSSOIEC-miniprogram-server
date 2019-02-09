package com.ccwsz.server.dao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class CourseChooseEntityPK implements Serializable {
    private long id;
    private long courseId;
    private long teacherId;

    @Column(name = "id", nullable = false)
    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "course_id", nullable = false)
    @Id
    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    @Column(name = "teacher_id", nullable = false)
    @Id
    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseChooseEntityPK that = (CourseChooseEntityPK) o;
        return id == that.id &&
                courseId == that.courseId &&
                teacherId == that.teacherId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, teacherId);
    }
}
