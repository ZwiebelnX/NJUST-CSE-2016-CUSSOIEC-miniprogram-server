package com.ccwsz.server.dao.entity;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GeneratorType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class) //启动自动生成时间
@Table(name = "user_homework_answer", schema = "online_cussoiec", catalog = "")
public class UserHomeworkAnswerEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private long homeworkId;
    private String userAnswer;
    private long userId;
    private long questionId;
    private byte isCorrect;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
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
    @Column(name = "homework_id", nullable = false)
    public long getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(long homeworkId) {
        this.homeworkId = homeworkId;
    }

    @Basic
    @Column(name = "user_answer", nullable = false, length = 100)
    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserHomeworkAnswerEntity that = (UserHomeworkAnswerEntity) o;
        return id == that.id &&
                homeworkId == that.homeworkId &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(userAnswer, that.userAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gmtCreate, gmtModified, homeworkId, userAnswer);
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "question_id", nullable = false)
    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    @Basic
    @Column(name = "is_correct", nullable = false)
    public byte getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(byte isCorrect) {
        this.isCorrect = isCorrect;
    }
}
