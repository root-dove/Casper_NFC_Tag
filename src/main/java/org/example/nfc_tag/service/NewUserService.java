package org.example.nfc_tag.service;

import org.example.nfc_tag.entity.NewUser;
import org.example.nfc_tag.repository.NewUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewUserService {

    private final NewUserRepository newUserRepository;

    @Autowired
    public NewUserService(NewUserRepository newUserRepository) {
        this.newUserRepository = newUserRepository;
    }

    public long getUserCount() {
        return newUserRepository.countUsers();
    }

    public List<NewUser> getAllNewUsers() {
        return newUserRepository.findAll();
    }

    public boolean deleteNewUserById(Long id) {
        Optional<NewUser> newUserOptional = newUserRepository.findById(id);
        if (newUserOptional.isPresent()) {
            newUserRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteAllNewUsers() {
        newUserRepository.deleteAll();
    }
}
