package com.shamrock.reework.common;

public class MedicalConditionData {
    private String medicalconditionName;
    private boolean isSelect;

    public MedicalConditionData(String medicalconditionName, boolean isSelect) {
        this.medicalconditionName = medicalconditionName;
        this.isSelect = isSelect;
    }

    public String getMedicalconditionName() {
        return medicalconditionName;
    }

    public void setMedicalconditionName(String medicalconditionName) {
        this.medicalconditionName = medicalconditionName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
