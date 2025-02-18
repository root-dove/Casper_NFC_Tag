package org.example.nfc_tag.controller;
import org.example.nfc_tag.entity.User;
import org.example.nfc_tag.repository.UserRepository;
import org.example.nfc_tag.service.UserCardDTO;
import org.example.nfc_tag.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://192.168.45.39:3000")  // 특정 도메인 허용
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);
    @Autowired
    private UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUserAndCard(@RequestBody UserCardDTO dto) {
        String response = userService.registerUserAndCard(dto);
        if (response.contains("successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

}
