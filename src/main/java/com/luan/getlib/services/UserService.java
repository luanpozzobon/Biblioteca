package com.luan.getlib.services;

import com.luan.getlib.models.Operation;
import com.luan.getlib.models.PasswordDTO;
import com.luan.getlib.models.User;
import com.luan.getlib.models.UserInfoDTO;
import com.luan.getlib.repositories.OperationRepository;
import com.luan.getlib.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final OperationRepository operationRepository;

    @Autowired
    public UserService(UserRepository userRepository, OperationRepository operationRepository) {
        this.userRepository = userRepository;
        this.operationRepository = operationRepository;
    }

    public ResponseEntity<UserInfoDTO> getUserInfo() {
        User user = getAuthenticatedUser();
        UserInfoDTO userInfo = new UserInfoDTO(user);
        return ResponseEntity.ok(userInfo);
    }

    public ResponseEntity<UserInfoDTO> alterRegistration(User alteredUser) {
        User authUser = getAuthenticatedUser();
        if (alteredUser.getFullName() != null && !alteredUser.getFullName().isBlank())
            authUser.setFullName(alteredUser.getFullName());
        if (alteredUser.getBirthDate() != null)
            authUser.setBirthDate(alteredUser.getBirthDate());
        if (alteredUser.getAddress() != null)
            authUser.setAddress(alteredUser.getAddress());
        if (alteredUser.getEmail() != null && !alteredUser.getEmail().isBlank())
            authUser.setEmail(alteredUser.getEmail());
        if (alteredUser.getPhone() != null && !alteredUser.getPhone().isBlank())
            authUser.setPhone(alteredUser.getPhone());
        if (alteredUser.getUsername() != null && !alteredUser.getUsername().isBlank())
            authUser.setUsername(alteredUser.getUsername());

        userRepository.save(authUser);
        UserInfoDTO userInfoDTO = new UserInfoDTO(authUser);
        return ResponseEntity.ok(userInfoDTO);
    }

    public ResponseEntity changePassword(PasswordDTO passwordDTO) {
        User user = getAuthenticatedUser();
        if (!new BCryptPasswordEncoder().matches(passwordDTO.oldPassword(), user.getPassword()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("A senha atual não corresponde a senha cadastrada!");
        if (!passwordDTO.arePasswordsEqual())
            return ResponseEntity.badRequest().body("Senhas não correspondem!");

        String encryptedPassword = new BCryptPasswordEncoder().encode(passwordDTO.newPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity deleteAccount(String password) {
        User user = getAuthenticatedUser();
        if (!new BCryptPasswordEncoder().matches(password, user.getPassword()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("A senha não corresponde a senha cadastrada!");

        List<Operation> operations = operationRepository.findAllByUser(user);
        if (!operations.isEmpty())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Não é possível encerrar a conta, pois há empréstimos em aberto!");

        userRepository.delete(user);
        return ResponseEntity.ok().body("Conta excluída com sucesso!");
    }

    protected User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return userRepository.findUserByUsername(userDetails.getUsername());
    }
}
