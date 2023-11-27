package com.example.www_week1.Model;

import java.util.Objects;

public class Grant_access {
    private Long accountId;
    private Long roleId;
    private boolean isGrant;
    private String note;

    public Grant_access() {
    }

    public Grant_access(Long accountId, Long roleId, boolean isGrant, String note) {
        this.accountId = accountId;
        this.roleId = roleId;
        this.isGrant = isGrant;
        this.note = note;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public boolean isGrant() {
        return isGrant;
    }

    public void setGrant(boolean isGrant) {
        this.isGrant = isGrant;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "GrantAccess{" +
                "accountId=" + accountId +
                ", roleId=" + roleId +
                ", isGrant=" + isGrant +
                ", note='" + note + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grant_access that = (Grant_access) o;
        return isGrant == that.isGrant &&
                Objects.equals(accountId, that.accountId) &&
                Objects.equals(roleId, that.roleId) &&
                Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, roleId, isGrant, note);
    }
}
