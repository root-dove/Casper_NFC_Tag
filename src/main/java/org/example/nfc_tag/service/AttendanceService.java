package org.example.nfc_tag.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.nfc_tag.model.Attendance;
import org.example.nfc_tag.model.AttendanceStatus;
import org.example.nfc_tag.model.User;
import org.example.nfc_tag.repository.AttendanceRepository;
import org.example.nfc_tag.repository.UserRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
    }

    public String markAttendance(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return "User not found!";
        }

        User user = userOptional.get();
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        // 이미 출석이 기록되어 있는지 확인
        if (attendanceRepository.findByUserIdAndAttendanceDate(userId, today).isPresent()) {
            return "Attendance already marked for today.";
        }

        // 출석 상태 결정
        AttendanceStatus status = now.isAfter(LocalTime.of(10, 0)) ? AttendanceStatus.LATE : AttendanceStatus.PRESENT;

        // 출석 정보 저장
        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setAttendanceDate(today);
        attendance.setTime(now);
        attendance.setStatus(status);
        attendance.setCreatedAt(LocalDateTime.now());
        attendance.setUpdatedAt(LocalDateTime.now());

        attendanceRepository.save(attendance);
        return "Attendance marked as " + status + "!";
    }
    public List<AttendanceDTO> getAttendanceRecords(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Attendance> attendances = attendanceRepository.findByUserIdAndAttendanceDateBetween(userId, startDate, endDate);
        
        return attendances.stream()
            .map(attendance -> new AttendanceDTO(
                attendance.getAttendanceDate(),
                attendance.getStatus().name(),
                attendance.getComment(),
                attendance.getTime()
            ))
            .collect(Collectors.toList());
    }
    
    // 출근 상황 조절
    public String updateAttendance(AttendanceUpdateDTO dto) {
        Optional<Attendance> optionalAttendance = attendanceRepository.findByUserIdAndAttendanceDate(dto.getUserId(), dto.getAttendanceDate());

        if (optionalAttendance.isPresent()) {
            Attendance attendance = optionalAttendance.get();
            attendance.setStatus(AttendanceStatus.valueOf(dto.getStatus().toUpperCase())); // Enum 변환
            attendance.setComment(dto.getComment());
            attendance.setTime(dto.getTime());

            attendanceRepository.save(attendance);
            return "Attendance record updated successfully.";
        } else {
            return "No attendance record found for the given user and date.";
        }
    }

    // 휴가 일수 조정정
    @Transactional
    public String updateVacationDays(Long userId, int change) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return "User not found";
        }

        User user = userOptional.get();
        int newVacationDays = user.getRemainingLeaveDays() + change;

        if (newVacationDays < 0) {
            return "Insufficient vacation days";
        }

        user.setRemainingLeaveDays(newVacationDays);
        userRepository.save(user);

        return "Updated vacation days: " + newVacationDays;
    }
}


