package com.ccwsz.server.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "bbs_sector", schema = "online_cussoiec", catalog = "")
public class BbsSectorEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private String sectorName;
    private long clickingRate;
    private long topicCount;
    private long courseId;

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
    @Column(name = "sector_name")
    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    @Basic
    @Column(name = "clicking_rate")
    public long getClickingRate() {
        return clickingRate;
    }

    public void setClickingRate(long clickingRate) {
        this.clickingRate = clickingRate;
    }

    @Basic
    @Column(name = "topic_count")
    public long getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(long topicCount) {
        this.topicCount = topicCount;
    }

    @Basic
    @Column(name = "course_id")
    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BbsSectorEntity that = (BbsSectorEntity) o;
        return id == that.id &&
                clickingRate == that.clickingRate &&
                topicCount == that.topicCount &&
                courseId == that.courseId &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(sectorName, that.sectorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gmtCreate, gmtModified, sectorName, clickingRate, topicCount, courseId);
    }
}
