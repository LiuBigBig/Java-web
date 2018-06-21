package com.icss.hr.perm.pojo;

public class Permission {
    private Integer permId;

    private String permName;

    private String permInfo;

    public Integer getPermId() {
        return permId;
    }

    public void setPermId(Integer permId) {
        this.permId = permId;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName == null ? null : permName.trim();
    }

    public String getPermInfo() {
        return permInfo;
    }

    public void setPermInfo(String permInfo) {
        this.permInfo = permInfo == null ? null : permInfo.trim();
    }
}