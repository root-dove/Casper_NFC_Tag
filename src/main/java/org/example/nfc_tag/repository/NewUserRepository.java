package org.example.nfc_tag.repository;

import org.example.nfc_tag.entity.NewUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewUserRepository extends JpaRepository<NewUser, Long> {
}
