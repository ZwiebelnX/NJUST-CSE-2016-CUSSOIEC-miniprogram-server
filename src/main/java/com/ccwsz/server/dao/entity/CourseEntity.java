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

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "gmt_create")
    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Basic
    @Column(name = "gmt_modified")
    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Basic
    @Column(name = "course_number")
    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    @Basic
    @Column(name = "course_name")
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Basic
    @Column(name = "teacher_name")
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Basic
    @Column(name = "classroom_location")
    public String getClassroomLocation() {
        return classroomLocation;
    }

    public void setClassroomLocation(String classroomLocation) {
        this.classroomLocation = classroomLocation;
    }

    @Basic
    @Column(name = "class_time")
    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "class_week")
    public String getClassWeek() {
        return classWeek;
    }

    public void setClassWeek(String classWeek) {
        this.classWeek = classWeek;
    }

    @Basic
    @Column(name = "live_url")
    public String getLiveUrl() {
        return liveUrl;
    }

    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }

    @Basic
    @Column(name = "is_live")
    public Byte getIsLive() {
        return isLive;
    }

    public void setIsLive(Byte isLive) {
        this.isLive = isLive;
    }

    @Basic
    @Column(name = "is_checking_in")
    public Byte getIsCheckingIn() {
        return isCheckingIn;
    }

    public void setIsCheckingIn(Byte isCheckingIn) {
        this.isCheckingIn = isCheckingIn;
    }

    @Basic
    @Column(name = "is_evaluated")
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
        return Objects.hash(id, gmtCreate, gmtModified, courseNumber, courseName, teacherName, classroomLocation, classTime, description, classWeek, liveUrl, isLive, isCheckingIn, isEvaluated);
    }
}
