package org.example.nfc_tag.repository;


import org.example.nfc_tag.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
