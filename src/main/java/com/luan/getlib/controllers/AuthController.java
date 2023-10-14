package com.luan.getlib.controllers;

import com.luan.getlib.models.AuthenticationDTO;
import com.luan.getlib.models.LoginResponseDTO;
import com.luan.getlib.models.RegisterDTO;
import com.luan.getlib.models.User;
import com.luan.getlib.repositories.UserRepository;
import com.luan.getlib.security.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final UserRepository repository;
    private final TokenService tokenService;


    @Autowired
    public AuthController(AuthenticationManager authManager, UserRepository repository, TokenService tokenService) {
        this.authManager = authManager;
        this.repository = repository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO data) {
        if (repository.findByUsername(data.username()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.fullName(), data.birthDate(), data.address(), data.email(), data.phone(), data.username(), encryptedPassword, data.role());

        repository.save(user);
        return ResponseEntity.ok().build();
    }
}
