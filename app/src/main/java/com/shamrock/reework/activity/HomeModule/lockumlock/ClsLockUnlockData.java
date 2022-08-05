package com.shamrock.reework.activity.HomeModule.lockumlock;

public class ClsLockUnlockData {
    private String StaticName;

    private String ServiceName;

    private String IsLocked;

    private int Id;

    private String ProcedureText;

    public String getStaticName() {
        return StaticName;
    }

    public void setStaticName(String staticName) {
        StaticName = staticName;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getIsLocked() {
        return IsLocked;
    }

    public void setIsLocked(String isLocked) {
        IsLocked = isLocked;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getProcedureText() {
        return ProcedureText;
    }

    public void setProcedureText(String procedureText) {
        ProcedureText = procedureText;
    }
}
