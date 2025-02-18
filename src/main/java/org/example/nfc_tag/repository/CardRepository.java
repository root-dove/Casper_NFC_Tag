package org.example.nfc_tag.repository;

import jakarta.persistence.LockModeType;
import org.example.nfc_tag.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByNfcId(Long nfcId);

    // ✅ 단순 조회 (비관적 락 없음)
    Optional<Card> findByNfcId(Long nfcId);

    // ✅ 비관적 락을 사용한 조회
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Card c WHERE c.nfcId = :nfcId")
    Optional<Card> findByNfcIdWithLock(@Param("nfcId") Long nfcId);
}

