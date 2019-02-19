package com.ccwsz.server.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "course_homework_question", schema = "online_cussoiec", catalog = "")
public class CourseHomeworkQuestionEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private long homeworkId;
    private byte questionType;
    private String questionText;
    private String imageUrls;
    private String chooseText;
    private byte correctAnswer;

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
    @Column(name = "homework_id", nullable = false)
    public long getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(long homeworkId) {
        this.homeworkId = homeworkId;
    }

    @Basic
    @Column(name = "question_type", nullable = false)
    public byte getQuestionType() {
        return questionType;
    }

    public void setQuestionType(byte questionType) {
        this.questionType = questionType;
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
    @Column(name = "image_urls", nullable = true, length = 1000)
    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
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
        CourseHomeworkQuestionEntity that = (CourseHomeworkQuestionEntity) o;
        return id == that.id &&
                homeworkId == that.homeworkId &&
                questionType == that.questionType &&
                correctAnswer == that.correctAnswer &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(questionText, that.questionText) &&
                Objects.equals(imageUrls, that.imageUrls) &&
                Objects.equals(chooseText, that.chooseText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gmtCreate, gmtModified, homeworkId, questionType, questionText, imageUrls, chooseText, correctAnswer);
    }
}