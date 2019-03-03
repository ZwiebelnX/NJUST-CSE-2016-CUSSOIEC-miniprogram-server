package com.ccwsz.server.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
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
<<<<<<< HEAD
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "title")
=======
    @Column(name = "title", nullable = false, length = 45)
>>>>>>> origin/master
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
<<<<<<< HEAD
    @Column(name = "topic_text")
=======
    @Column(name = "topic_text", nullable = false, length = 1000)
>>>>>>> origin/master
    public String getTopicText() {
        return topicText;
    }

    public void setTopicText(String topicText) {
        this.topicText = topicText;
    }

    @Basic
<<<<<<< HEAD
    @Column(name = "reply_count")
=======
    @Column(name = "reply_count", nullable = false)
>>>>>>> origin/master
    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    @Basic
<<<<<<< HEAD
    @Column(name = "clicking_rate")
=======
    @Column(name = "clicking_rate", nullable = false)
>>>>>>> origin/master
    public int getClickingRate() {
        return clickingRate;
    }

    public void setClickingRate(int clickingRate) {
        this.clickingRate = clickingRate;
    }

    @Basic
<<<<<<< HEAD
    @Column(name = "sector_id")
=======
    @Column(name = "sector_id", nullable = false)
>>>>>>> origin/master
    public long getSectorId() {
        return sectorId;
    }

    public void setSectorId(long sectorId) {
        this.sectorId = sectorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BbsTopicEntity that = (BbsTopicEntity) o;
        return id == that.id &&
<<<<<<< HEAD
                userId == that.userId &&
=======
>>>>>>> origin/master
                replyCount == that.replyCount &&
                clickingRate == that.clickingRate &&
                sectorId == that.sectorId &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(title, that.title) &&
                Objects.equals(topicText, that.topicText);
    }

    @Override
    public int hashCode() {
<<<<<<< HEAD
        return Objects.hash(id, gmtCreate, gmtModified, userId, title, topicText, replyCount, clickingRate, sectorId);
=======
        return Objects.hash(id, gmtCreate, gmtModified, title, topicText, replyCount, clickingRate, sectorId);
>>>>>>> origin/master
    }
}
