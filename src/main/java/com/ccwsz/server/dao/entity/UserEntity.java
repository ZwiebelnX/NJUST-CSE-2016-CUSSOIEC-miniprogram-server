package com.ccwsz.server.dao.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class) //启动自动生成时间
@Table(name = "user", schema = "online_cussoiec", catalog = "")
public class UserEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private String openid;
    private String userType;
    private String personNumber;
    private String realName;
    private String nickName;
    private String gender;
    private String grade;
    private String academy;
    private String major;
    private String phone;
    private String email;
    private String college;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "openid", nullable = false, length = 50)
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Basic
    @Column(name = "user_type", columnDefinition = "student", nullable = false, length = 7)
    public String getUserType() {
        if(userType == null){
            return "";
        }
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Basic
    @Column(name = "person_number", columnDefinition = "", nullable = true, length = 20)
    public String getPersonNumber() {
        if(personNumber == null){
            return "";
        }
        return personNumber;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    @Basic
    @Column(name = "real_name", nullable = true, columnDefinition = "", length = 15)
    public String getRealName() {
        if(realName == null){
            return "";
        }
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Basic
    @Column(name = "nick_name", nullable = true, columnDefinition = "", length = 20)
    public String getNickName() {
        if(nickName == null){
            return "";
        }
        return nickName;
    }

    public void setNickName(String nickName) {

        this.nickName = nickName;
    }

    @Basic
    @Column(name = "gender", nullable = true, columnDefinition = "male", length = 6)
    public String getGender() {
        if(gender == null){
            return "";
        }
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "grade", nullable = true, columnDefinition = "1" ,length = 5)
    public String getGrade() {
        if(grade == null){
            return "";
        }
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Basic
    @Column(name = "academy", nullable = true, columnDefinition = "", length = 20)
    public String getAcademy() {
        if(academy == null){
            return "";
        }
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    @Basic
    @Column(name = "major", nullable = true, columnDefinition = "", length = 20)
    public String getMajor() {
        if(major == null){
            return "";
        }
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Basic
    @Column(name = "phone", nullable = true, columnDefinition = "", length = 11)
    public String getPhone() {
        if(phone == null){
            return "";
        }
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email", nullable = true, columnDefinition = "", length = 30)
    public String getEmail() {
        if(email == null){
            return "";
        }
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
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(openid, that.openid) &&
                Objects.equals(userType, that.userType) &&
                Objects.equals(personNumber, that.personNumber) &&
                Objects.equals(realName, that.realName) &&
                Objects.equals(nickName, that.nickName) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(grade, that.grade) &&
                Objects.equals(academy, that.academy) &&
                Objects.equals(major, that.major) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gmtCreate, gmtModified, openid, userType, personNumber, realName, nickName, gender, grade, academy, major, phone, email);
    }

    @Basic
    @Column(name = "college", nullable = true, length = 30)
    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
