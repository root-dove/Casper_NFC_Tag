package org.example.nfc_tag.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 🔥 ID 자동 증가 (자동 생성)
    private Long id; // ✅ DB에서 자동 생성됨

    @Column(unique = true, nullable = false)
    private Long nfcId; // ✅ nfcId는 기본 키가 아님 (별도 컬럼)

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();
}

