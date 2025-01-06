package org.example.nfc_tag.controller;

import org.example.nfc_tag.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/mark")
    public ResponseEntity<String> markAttendance(@RequestParam Long userId) {
        try {
            String response = attendanceService.markAttendance(userId);
            return ResponseEntity.ok(response); // 출석 기록 성공
        } catch (Exception e) {
            // 예외가 발생하면 404 또는 400과 같은 상태 코드 반환
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }
}
