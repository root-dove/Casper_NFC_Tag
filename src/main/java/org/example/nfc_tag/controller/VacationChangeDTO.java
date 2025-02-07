package org.example.nfc_tag.controller;

public class VacationChangeDTO {
    private int change; // 휴가 일수 변경값 (양수: 증가, 음수: 감소)

    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }
}
