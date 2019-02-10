package com.ccwsz.server.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "online_cussoiec", catalog = "")
@IdClass(UserEntityPK.class)
public class UserEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp getModified;
    private String openid;
    private Object userType;
    private String personNumber;
    private String realName;
    private String nickName;
    private Object gender;
    private String college;
    private Object grade;
    private String academy;
    private String major;
    private String phone;
    private String email;

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
    @Column(name = "get_modified", nullable = false)
    public Timestamp getGetModified() {
        return getModified;
    }

    public void setGetModified(Timestamp getModified) {
        this.getModified = getModified;
    }

    @Id
    @Column(name = "openid", nullable = false, length = 50)
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Basic
    @Column(name = "user_type", nullable = false)
    public Object getUserType() {
        return userType;
    }

    public void setUserType(Object userType) {
        this.userType = userType;
    }

    @Id
    @Column(name = "person_number", nullable = false, length = 20)
    public String getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    @Basic
    @Column(name = "real_name", nullable = false, length = 15)
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Basic
    @Column(name = "nick_name", nullable = true, length = 20)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Basic
    @Column(name = "gender", nullable = false)
    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    @Id
    @Column(name = "college", nullable = false, length = 30)
    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    @Basic
    @Column(name = "grade", nullable = false)
    public Object getGrade() {
        return grade;
    }

    public void setGrade(Object grade) {
        this.grade = grade;
    }

    @Basic
    @Column(name = "academy", nullable = false, length = 20)
    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    @Basic
    @Column(name = "major", nullable = false, length = 20)
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 11)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 30)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(getModified, that.getModified) &&
                Objects.equals(openid, that.openid) &&
                Objects.equals(userType, that.userType) &&
                Objects.equals(personNumber, that.personNumber) &&
                Objects.equals(realName, that.realName) &&
                Objects.equals(nickName, that.nickName) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(college, that.college) &&
                Objects.equals(grade, that.grade) &&
                Objects.equals(academy, that.academy) &&
                Objects.equals(major, that.major) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gmtCreate, getModified, openid, userType, personNumber, realName, nickName, gender, college, grade, academy, major, phone, email);
    }
}
