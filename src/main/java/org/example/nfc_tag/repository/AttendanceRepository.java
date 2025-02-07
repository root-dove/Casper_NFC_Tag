package org.example.nfc_tag.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.example.nfc_tag.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByUserIdAndAttendanceDate(Long userId, LocalDate attendanceDate);
    List<Attendance> findByUserIdAndAttendanceDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
