package org.example.nfc_tag.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCardDTO {
    private String userName; // 유저 이름
    private String nfcId;    // NFC ID
}
