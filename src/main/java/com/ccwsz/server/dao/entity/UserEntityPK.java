package com.ccwsz.server.dao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class UserEntityPK implements Serializable {
    private long id;
    private String openid;
    private String personNumber;
    private String college;

    @Column(name = "id", nullable = false)
    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "openid", nullable = false, length = 50)
    @Id
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Column(name = "person_number", nullable = false, length = 20)
    @Id
    public String getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    @Column(name = "college", nullable = false, length = 30)
    @Id
    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntityPK that = (UserEntityPK) o;
        return id == that.id &&
                Objects.equals(openid, that.openid) &&
                Objects.equals(personNumber, that.personNumber) &&
                Objects.equals(college, that.college);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openid, personNumber, college);
    }
}
