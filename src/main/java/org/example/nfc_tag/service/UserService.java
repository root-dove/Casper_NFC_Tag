package org.example.nfc_tag.service;

import org.example.nfc_tag.service.UserCardDTO;
import org.example.nfc_tag.entity.Card;
import org.example.nfc_tag.entity.User;
import org.example.nfc_tag.repository.CardRepository;
import org.example.nfc_tag.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    @Autowired
    public UserService(UserRepository userRepository, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    @Transactional
    public String registerUserAndCard(UserCardDTO dto) {
        // NFC ID 중복 체크
        if (cardRepository.existsByNfcId(dto.getNfcId())) {
            return "NFC ID already exists!";
        }

        // 1. User 테이블에 유저 추가
        User newUser = new User();
        newUser.setName(dto.getUserName());
        userRepository.save(newUser); // 저장 후 ID 자동 생성

        // 2. Card 테이블에 NFC 정보 추가
        Card newCard = new Card();
        newCard.setNfcId(dto.getNfcId());
        newCard.setUser(newUser);
        cardRepository.save(newCard);

        return "User and NFC card registered successfully!";
    }
}
