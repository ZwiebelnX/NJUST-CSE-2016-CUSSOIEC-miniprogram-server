package com.ccwsz.server.dao.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class) //启动自动生成时间
@Table(name = "bbs_topic", schema = "online_cussoiec", catalog = "")
public class BbsTopicEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private long userId;
    private String title;
    private String topicText;
    private int replyCount;
    private int clickingRate;
    private long sectorId;
    private long courseId;

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
    @Column(name = "gmt_create")
    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Basic
    @LastModifiedDate
    @Column(name = "gmt_modified")
    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Basic
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "topic_text")
    public String getTopicText() {
        return topicText;
    }

    public void setTopicText(String topicText) {
        this.topicText = topicText;
    }

    @Basic
    @Column(name = "reply_count")
    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    @Basic
    @Column(name = "clicking_rate")
    public int getClickingRate() {
        return clickingRate;
    }

    public void setClickingRate(int clickingRate) {
        this.clickingRate = clickingRate;
    }

    @Basic
    @Column(name = "sector_id")
    public long getSectorId() {
        return sectorId;
    }

    public void setSectorId(long sectorId) {
        this.sectorId = sectorId;
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
        BbsTopicEntity that = (BbsTopicEntity) o;
        return id == that.id &&
                userId == that.userId &&
                replyCount == that.replyCount &&
                clickingRate == that.clickingRate &&
                sectorId == that.sectorId &&
                courseId == that.courseId &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(title, that.title) &&
                Objects.equals(topicText, that.topicText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gmtCreate, gmtModified, userId, title, topicText, replyCount, clickingRate, sectorId, courseId);
    }
}
