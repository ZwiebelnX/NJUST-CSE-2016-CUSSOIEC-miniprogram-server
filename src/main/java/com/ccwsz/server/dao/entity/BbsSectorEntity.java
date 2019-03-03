package com.ccwsz.server.dao.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class) //启动自动生成时间
@Table(name = "bbs_sector", schema = "online_cussoiec", catalog = "")
public class BbsSectorEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private String sectorName;
    private long clickingRate;
    private long topicCount;

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
    @Column(name = "sector_name", nullable = false, length = 45)
    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    @Basic
    @Column(name = "clicking_rate", nullable = false)
    public long getClickingRate() {
        return clickingRate;
    }

    public void setClickingRate(long clickingRate) {
        this.clickingRate = clickingRate;
    }

    @Basic
    @Column(name = "topic_count", nullable = false)
    public long getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(long topicCount) {
        this.topicCount = topicCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BbsSectorEntity that = (BbsSectorEntity) o;
        return id == that.id &&
                clickingRate == that.clickingRate &&
                topicCount == that.topicCount &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(sectorName, that.sectorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gmtCreate, gmtModified, sectorName, clickingRate, topicCount);
    }
}
