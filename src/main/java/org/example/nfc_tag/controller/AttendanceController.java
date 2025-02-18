package org.example.nfc_tag.controller;

import java.time.LocalDate;
import java.util.List;

import org.example.nfc_tag.service.AttendanceDTO;
import org.example.nfc_tag.service.AttendanceService;
import org.example.nfc_tag.service.AttendanceUpdateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://192.168.45.39:3000")  // 특정 도메인 허용
@RequestMapping("/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;
    private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // ✅ 출석 기록 추가 API
    @PostMapping("/mark")
    public ResponseEntity<String> markAttendance(@RequestParam String userIdHex) {
        try {
            Long userId = Long.parseUnsignedLong(userIdHex, 16);

            // 출석 처리 서비스 호출
            String response = attendanceService.markAttendance(userId);
            return ResponseEntity.ok(response); // 출석 기록 성공

        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("Invalid userId: must be a valid hexadecimal string.");
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
            return ResponseEntity.status(403).body(response);
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
