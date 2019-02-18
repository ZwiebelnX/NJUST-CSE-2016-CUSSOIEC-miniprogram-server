package com.ccwsz.server.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "course_homework_detail", schema = "online_cussoiec", catalog = "")
public class CourseHomeworkDetailEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private String questionText;
    private String chooseText;
    private byte correctAnswer;
    private long homeworkId;

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
    @Column(name = "gmt_modified", nullable = false)
    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Basic
    @Column(name = "question_text", nullable = false, length = 250)
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Basic
    @Column(name = "choose_text", nullable = false, length = 255)
    public String getChooseText() {
        return chooseText;
    }

    public void setChooseText(String chooseText) {
        this.chooseText = chooseText;
    }

    @Basic
    @Column(name = "correct_answer", nullable = false)
    public byte getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(byte correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseHomeworkDetailEntity that = (CourseHomeworkDetailEntity) o;
        return id == that.id &&
                correctAnswer == that.correctAnswer &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(questionText, that.questionText) &&
                Objects.equals(chooseText, that.chooseText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gmtCreate, gmtModified, questionText, chooseText, correctAnswer);
    }

    @Basic
    @Column(name = "homework_id", nullable = false)
    public long getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(long homeworkId) {
        this.homeworkId = homeworkId;
    }
}
