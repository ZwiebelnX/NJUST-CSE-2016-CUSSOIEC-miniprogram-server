package com.ccwsz.server.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "course_evaluation_question", schema = "online_cussoiec", catalog = "")
public class CourseEvaluationQuestionEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private long courseId;
    private byte questionType;
    private String questionText;
    private String chooseText;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "course_id")
    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "question_type")
    public byte getQuestionType() {
        return questionType;
    }

    public void setQuestionType(byte questionType) {
        this.questionType = questionType;
    }

    @Basic
    @Column(name = "question_text")
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Basic
    @Column(name = "choose_text")
    public String getChooseText() {
        return chooseText;
    }

    public void setChooseText(String chooseText) {
        this.chooseText = chooseText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseEvaluationQuestionEntity that = (CourseEvaluationQuestionEntity) o;
        return id == that.id &&
                courseId == that.courseId &&
                questionType == that.questionType &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(questionText, that.questionText) &&
                Objects.equals(chooseText, that.chooseText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gmtCreate, gmtModified, courseId, questionType, questionText, chooseText);
    }
}
