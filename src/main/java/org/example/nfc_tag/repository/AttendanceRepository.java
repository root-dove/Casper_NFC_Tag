package org.example.nfc_tag.repository;

import org.example.nfc_tag.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByUserIdAndAttendanceDate(Long userId, LocalDate attendanceDate);
}
