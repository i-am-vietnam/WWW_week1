package com.example.www_week1.Model;

import java.sql.Date;
import java.util.Objects;

public class Log {
    private Long logId;
    private Long accountId;
    private Date loginDate;
    private Date logoutDate;
    private String description;

    public Log() {
    }

    public Log(Long logId, Long accountId, Date loginDate, Date logoutDate, String description) {
        this.logId = logId;
        this.accountId = accountId;
        this.loginDate = loginDate;
        this.logoutDate = logoutDate;
        this.description = description;
    }

    public Log(Long accountId, Date loginDate, Date logoutDate, String description) {
        this.accountId = accountId;
        this.loginDate = loginDate;
        this.logoutDate = logoutDate;
        this.description = description;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(Date logoutDate) {
        this.logoutDate = logoutDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Log{" +
                "logId=" + logId +
                ", accountId=" + accountId +
                ", loginDate=" + loginDate +
                ", logoutDate=" + logoutDate +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return Objects.equals(logId, log.logId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logId);
    }
}
