package com.luan.getlib.controllers;

import com.luan.getlib.models.DeleteRequestDTO;
import com.luan.getlib.models.PasswordDTO;
import com.luan.getlib.models.User;
import com.luan.getlib.models.UserInfoDTO;
import com.luan.getlib.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/")
    public ResponseEntity<UserInfoDTO> getUserInfo() {
        return service.getUserInfo();
    }

    @PutMapping("/alter-registration")
    public ResponseEntity<UserInfoDTO> alterRegistration(@RequestBody User user) {
        return service.alterRegistration(user);
    }

    @PatchMapping("/change-password")
    public ResponseEntity changePassword(@RequestBody PasswordDTO passwordDTO) {
        return service.changePassword(passwordDTO);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteAccount(@RequestBody DeleteRequestDTO deleteRequestDTO) {
        return service.deleteAccount(deleteRequestDTO.password());
    }

}
