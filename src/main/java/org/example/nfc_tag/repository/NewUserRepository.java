package org.example.nfc_tag.repository;

import org.example.nfc_tag.entity.NewUser;
import org.example.nfc_tag.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NewUserRepository extends JpaRepository<NewUser, Long> {
    // new_user 테이블의 개수를 반환하는 쿼리
    @Query("SELECT COUNT(n) FROM NewUser n")
    long countUsers();

    // 🔹 특정 userId가 new_user 테이블에 존재하는지 확인
    Optional<NewUser> findByUserId(Long userId);
}
