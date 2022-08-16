package com.cy.store.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class BaseEntity implements Serializable {
    private String createdUser;        //日志-创建人
    private Date createdTime;          //日志-创建时间',
    private Date modifiedTime;         //日志-最后修改时间',
    private String modifiedUser;       //日志-最后修改执行人',

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(createdUser, that.createdUser) && Objects.equals(createdTime, that.createdTime) && Objects.equals(modifiedTime, that.modifiedTime) && Objects.equals(modifiedUser, that.modifiedUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdUser, createdTime, modifiedTime, modifiedUser);
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "createdUser='" + createdUser + '\'' +
                ", createdTime=" + createdTime +
                ", modifiedTime=" + modifiedTime +
                ", modifiedUser='" + modifiedUser + '\'' +
                '}';
    }
}
