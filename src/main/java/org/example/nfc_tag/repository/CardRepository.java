package org.example.nfc_tag.repository;

import org.example.nfc_tag.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByNfcId(String nfcId);
}
