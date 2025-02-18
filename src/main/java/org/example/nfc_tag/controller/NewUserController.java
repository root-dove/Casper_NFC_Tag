package org.example.nfc_tag.controller;

import org.example.nfc_tag.entity.NewUser;
import org.example.nfc_tag.service.NewUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://192.168.45.39:3000")  // 특정 도메인 허용
@RequestMapping("/newusers")
public class NewUserController {

    private final NewUserService newUserService;

    public NewUserController(NewUserService newUserService) {
        this.newUserService = newUserService;
    }

    // 🔹 new_user 테이블의 개수를 반환하는 API
    @GetMapping("/count")
    public long getUserCount() {
        return newUserService.getUserCount();
    }

    // 🔹 new_user 테이블의 모든 데이터를 반환하는 API
    @GetMapping
    public ResponseEntity<List<NewUser>> getAllNewUsers() {
        List<NewUser> newUsers = newUserService.getAllNewUsers();
        return ResponseEntity.ok(newUsers);
    }

    // 🔹 new_user 테이블에서 특정 ID의 데이터를 삭제하는 API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNewUser(@PathVariable Long id) {
        boolean deleted = newUserService.deleteNewUserById(id);
        if (deleted) {
            return ResponseEntity.ok("User with ID " + id + " deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("User with ID " + id + " not found.");
        }
    }

    // 🔹 new_user 테이블의 모든 데이터를 삭제하는 API
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllNewUsers() {
        newUserService.deleteAllNewUsers();
        return ResponseEntity.ok("All users deleted from new_user table.");
    }
}
