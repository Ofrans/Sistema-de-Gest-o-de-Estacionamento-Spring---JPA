package com.compasso.demo_park_api.service;

import com.compasso.demo_park_api.entity.User;
import com.compasso.demo_park_api.exception.EntityNotFoundException;
import com.compasso.demo_park_api.exception.PasswordInvalidException;
import com.compasso.demo_park_api.exception.UsernameUniqueViolationException;
import com.compasso.demo_park_api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }catch(org.springframework.dao.DataIntegrityViolationException e) {
            throw new UsernameUniqueViolationException(String.format("Username {%s} already exists", user.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public User searchById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User id={%s} not found.", id))
        );
    }

    @Transactional
    public User editPassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)){
            throw new PasswordInvalidException("Passwords do not match.");
        }

        User user = searchById(id);
        if(!passwordEncoder.matches(currentPassword, user.getPassword())){
            throw new PasswordInvalidException("New password do not match");
        }

        user.setPassword(newPassword);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> searchAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with '%s' not found.", username))
        );
    }

    public User.Role findRoleByUsername(String username) {
        return userRepository.findRoleByUsername(username);
    }
}
