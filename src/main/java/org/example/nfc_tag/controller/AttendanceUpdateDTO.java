package org.example.nfc_tag.controller;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceUpdateDTO {
    private Long userId;
    private LocalDate attendanceDate;
    private String status;
    private String comment;
    private LocalTime time;

    // 생성자
    public AttendanceUpdateDTO() {}

    public AttendanceUpdateDTO(Long userId, LocalDate attendanceDate, String status, String comment, LocalTime time) {
        this.userId = userId;
        this.attendanceDate = attendanceDate;
        this.status = status;
        this.comment = comment;
        this.time = time;
    }

    // Getter & Setter
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
