package org.example.nfc_tag.controller;

import java.time.LocalDate;
import java.util.List;

import org.example.nfc_tag.service.AttendanceDTO;
import org.example.nfc_tag.service.AttendanceService;
import org.example.nfc_tag.service.AttendanceUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // ✅ 출석 기록 추가 API
    @PostMapping("/mark")
    public ResponseEntity<String> markAttendance(@RequestParam Long userId) {
        try {
            String response = attendanceService.markAttendance(userId);
            return ResponseEntity.ok(response); // 출석 기록 성공
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    // ✅ 출석 기록 조회 API (날짜 범위)
    @GetMapping("/records")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceRecords(
        @RequestParam Long userId,
        @RequestParam LocalDate startDate,
        @RequestParam LocalDate endDate) 
        {
        try {
            List<AttendanceDTO> records = attendanceService.getAttendanceRecords(userId, startDate, endDate);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateAttendance(@RequestBody AttendanceUpdateDTO dto) {
        String response = attendanceService.updateAttendance(dto);
        if (response.contains("successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{userId}/vacation-days")
    public ResponseEntity<String> updateVacationDays(@PathVariable Long userId, @RequestBody VacationChangeDTO request) {
        String response = attendanceService.updateVacationDays(userId, request.getChange());

        if (response.contains("Updated")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
