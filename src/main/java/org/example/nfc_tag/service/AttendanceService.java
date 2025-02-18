package org.example.nfc_tag.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.nfc_tag.entity.Card;
import org.example.nfc_tag.entity.NewUser;
import org.example.nfc_tag.model.Attendance;
import org.example.nfc_tag.model.AttendanceStatus;
import org.example.nfc_tag.entity.User;
import org.example.nfc_tag.repository.AttendanceRepository;
import org.example.nfc_tag.repository.CardRepository;
import org.example.nfc_tag.repository.NewUserRepository;
import org.example.nfc_tag.repository.UserRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final NewUserRepository newUserRepository;
    private final CardRepository cardRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, UserRepository userRepository, NewUserRepository newUserRepository, CardRepository cardRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
        this.newUserRepository = newUserRepository;
        this.cardRepository = cardRepository;
    }
    @Transactional
    public String markAttendance(Long nfcId) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        // NFC 카드가 등록되어 있는지 확인
        Optional<Card> cardOptional = cardRepository.findByNfcIdWithLock(nfcId);
        if (cardOptional.isEmpty()) {
            // 카드가 등록되지 않았다면 new_user 테이블에 추가
            if (newUserRepository.findByUserId(nfcId).isPresent()) {
                return "User already exists in new_user table!";
            }

            NewUser newUser = new NewUser();
            newUser.setUserId(nfcId);
            newUser.setDate(today);
            newUserRepository.save(newUser);
            return "Card ID not found! User ID added to new_user table.";
        }

        // Card 테이블에서 User ID 가져오기
        Card card = cardOptional.get();
        Long userId = card.getUser().getId(); // 카드가 매핑된 userId 가져오기

        // 유저가 존재하는지 확인
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return "User not found in user table!";
        }

        User user = userOptional.get();

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
        Optional<User> userOptional = userRepository.findById(dto.getUserId());
        if (userOptional.isEmpty()) {
            return "User not found!";
        }

        User user = userOptional.get();
        LocalDate date = dto.getAttendanceDate();
        Optional<Attendance> optionalAttendance = attendanceRepository.findByUserIdAndAttendanceDate(user.getId(), date);

        Attendance attendance;
        if (optionalAttendance.isPresent()) {
            attendance = optionalAttendance.get();
            attendance.setStatus(AttendanceStatus.valueOf(dto.getStatus().toUpperCase())); // Enum 변환
            attendance.setComment(dto.getComment());
            attendance.setTime(dto.getTime());

            attendanceRepository.save(attendance);
            return "Attendance record updated successfully.";
        } else {
            // 새로운 데이터 생성
            attendance = new Attendance();
            attendance.setUser(user);
            attendance.setAttendanceDate(date);
            attendance.setStatus(AttendanceStatus.valueOf(dto.getStatus().toUpperCase()));
            attendance.setComment(dto.getComment());
            attendance.setTime(dto.getTime() != null ? dto.getTime() : LocalTime.now());
            attendance.setCreatedAt(LocalDateTime.now());
            attendance.setUpdatedAt(LocalDateTime.now());
        }

        attendanceRepository.save(attendance);
        return "Attendance updated successfully!";
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


