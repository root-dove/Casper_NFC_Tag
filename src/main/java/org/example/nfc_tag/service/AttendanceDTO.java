package org.example.nfc_tag.service;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceDTO {
    private LocalDate attendanceDate;
    private String status;
    private String comment;
    private LocalTime time;

    // 기본 생성자
    public AttendanceDTO() {
    }

    // 모든 필드를 포함하는 생성자
    public AttendanceDTO(LocalDate attendanceDate, String status, String comment, LocalTime time) {
        this.attendanceDate = attendanceDate;
        this.status = status;
        this.comment = comment;
        this.time = time;
    }

    // Getter 및 Setter
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

    // toString() 메서드 (디버깅 시 유용)
    @Override
    public String toString() {
        return "AttendanceDTO{" +
                "attendanceDate=" + attendanceDate +
                ", status='" + status + '\'' +
                ", comment='" + comment + '\'' +
                ", time=" + time +
                '}';
    }
}
