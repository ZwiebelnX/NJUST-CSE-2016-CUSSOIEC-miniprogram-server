package com.ccwsz.server.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "course", schema = "online_cussoiec", catalog = "")
public class CourseEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private String courseNumber;
    private String courseName;
    private String teacherName;
    private String classroomLocation;
    private String classTime;
    private String description;
    private String classWeek;
    private String liveUrl;
    private Byte isLive;
    private Byte isCheckingIn;
    private Byte isEvaluated;
    private long teacherId;

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
    @Column(name = "course_number", nullable = false, length = 10)
    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    @Basic
    @Column(name = "course_name", nullable = false, length = 60)
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Basic
    @Column(name = "teacher_name", nullable = false, length = 20)
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Basic
    @Column(name = "classroom_location", nullable = true, length = 20)
    public String getClassroomLocation() {
        return classroomLocation;
    }

    public void setClassroomLocation(String classroomLocation) {
        this.classroomLocation = classroomLocation;
    }

    @Basic
    @Column(name = "class_time", nullable = true, length = 30)
    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "class_week", nullable = true, length = 10)
    public String getClassWeek() {
        return classWeek;
    }

    public void setClassWeek(String classWeek) {
        this.classWeek = classWeek;
    }

    @Basic
    @Column(name = "live_url", nullable = true, length = 255)
    public String getLiveUrl() {
        return liveUrl;
    }

    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }

    @Basic
    @Column(name = "is_live", nullable = true)
    public Byte getIsLive() {
        return isLive;
    }

    public void setIsLive(Byte isLive) {
        this.isLive = isLive;
    }

    @Basic
    @Column(name = "is_checking_in", nullable = true)
    public Byte getIsCheckingIn() {
        return isCheckingIn;
    }

    public void setIsCheckingIn(Byte isCheckingIn) {
        this.isCheckingIn = isCheckingIn;
    }

    @Basic
    @Column(name = "is_evaluated", nullable = true)
    public Byte getIsEvaluated() {
        return isEvaluated;
    }

    public void setIsEvaluated(Byte isEvaluated) {
        this.isEvaluated = isEvaluated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseEntity that = (CourseEntity) o;
        return id == that.id &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(courseNumber, that.courseNumber) &&
                Objects.equals(courseName, that.courseName) &&
                Objects.equals(teacherName, that.teacherName) &&
                Objects.equals(teacherId, that.teacherId) &&
                Objects.equals(classroomLocation, that.classroomLocation) &&
                Objects.equals(classTime, that.classTime) &&
                Objects.equals(description, that.description) &&
                Objects.equals(classWeek, that.classWeek) &&
                Objects.equals(liveUrl, that.liveUrl) &&
                Objects.equals(isLive, that.isLive) &&
                Objects.equals(isCheckingIn, that.isCheckingIn) &&
                Objects.equals(isEvaluated, that.isEvaluated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gmtCreate, gmtModified, courseNumber, courseName, teacherName, teacherId, classroomLocation, classTime, description, classWeek, liveUrl, isLive, isCheckingIn, isEvaluated);
    }

    @Basic
    @Column(name = "teacher_id", nullable = false)
    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }
}
