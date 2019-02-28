package com.ccwsz.server.dao.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class) //启动自动生成时间
@Table(name = "college", schema = "online_cussoiec", catalog = "")
public class CollegeEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private String name;
    private String englishName;
    private String collegeNumber;
    private String location;
    private Date openingDate1;
    private Date openingDate2;
    private Date openingDate3;
    private Date openingDate4;
    private Date endingDate;

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
    @Column(name = "name", nullable = false, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "english_name", nullable = true, length = 40)
    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    @Basic
    @Column(name = "college_number", nullable = true, length = 10)
    public String getCollegeNumber() {
        return collegeNumber;
    }

    public void setCollegeNumber(String collegeNumber) {
        this.collegeNumber = collegeNumber;
    }

    @Basic
    @Column(name = "location", nullable = true, length = 100)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "opening_date_1", nullable = false)
    public Date getOpeningDate1() {
        return openingDate1;
    }

    public void setOpeningDate1(Date openingDate1) {
        this.openingDate1 = openingDate1;
    }

    @Basic
    @Column(name = "opening_date_2", nullable = false)
    public Date getOpeningDate2() {
        return openingDate2;
    }

    public void setOpeningDate2(Date openingDate2) {
        this.openingDate2 = openingDate2;
    }

    @Basic
    @Column(name = "opening_date_3", nullable = false)
    public Date getOpeningDate3() {
        return openingDate3;
    }

    public void setOpeningDate3(Date openingDate3) {
        this.openingDate3 = openingDate3;
    }

    @Basic
    @Column(name = "opening_date_4", nullable = false)
    public Date getOpeningDate4() {
        return openingDate4;
    }

    public void setOpeningDate4(Date openingDate4) {
        this.openingDate4 = openingDate4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollegeEntity that = (CollegeEntity) o;
        return id == that.id &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(name, that.name) &&
                Objects.equals(englishName, that.englishName) &&
                Objects.equals(collegeNumber, that.collegeNumber) &&
                Objects.equals(location, that.location) &&
                Objects.equals(openingDate1, that.openingDate1) &&
                Objects.equals(openingDate2, that.openingDate2) &&
                Objects.equals(openingDate3, that.openingDate3) &&
                Objects.equals(openingDate4, that.openingDate4);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gmtCreate, gmtModified, name, englishName, collegeNumber, location, openingDate1, openingDate2, openingDate3, openingDate4);
    }

    @Basic
    @Column(name = "ending_date", nullable = false)
    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }
}
