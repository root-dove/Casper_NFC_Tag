package org.example.nfc_tag.service;

import org.example.nfc_tag.model.Attendance;
import org.example.nfc_tag.model.AttendanceStatus;
import org.example.nfc_tag.model.User;
import org.example.nfc_tag.repository.AttendanceRepository;
import org.example.nfc_tag.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

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

        // Check if attendance is already marked
        if (attendanceRepository.findByUserIdAndAttendanceDate(userId, today).isPresent()) {
            return "Attendance already marked for today.";
        }

        // Determine attendance status
        AttendanceStatus status = now.isAfter(LocalTime.of(16, 0)) ? AttendanceStatus.LATE : AttendanceStatus.PRESENT;

        // Add penalty if late
        if (status == AttendanceStatus.LATE) {
            user.addLatePenalty();
            userRepository.save(user);
        }

        // Save attendance
        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setDate(today);
        attendance.setStatus(status);

        attendanceRepository.save(attendance);
        return "Attendance marked as " + status + "!";
    }
}
