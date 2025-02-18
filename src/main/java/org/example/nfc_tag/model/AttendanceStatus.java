package org.example.nfc_tag.model;

public enum AttendanceStatus {
    PRESENT,  // "Present" -> "PRESENT" (대문자로 변경)
    LATE,
    ABSENT,
    VACATION,
    OFFICIAL_LEAVE,// 혹시 OFFICIALLEAVE로 되어 있다면 언더스코어 추가
    NOT_YET
}
