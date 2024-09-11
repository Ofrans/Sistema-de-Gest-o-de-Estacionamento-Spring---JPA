package com.compasso.demo_park_api.web.controller;

import com.compasso.demo_park_api.entity.User;
import com.compasso.demo_park_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user){
        User user1 =  userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        User user1 =  userService.searchById(id);
        return ResponseEntity.ok(user1);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user){
        User user1 =  userService.editPassword(id,user.getPassword());
        return ResponseEntity.ok(user1);
    }

    // Lista todos os users
    @GetMapping
    public ResponseEntity <List<User>> getAll(){
        List<User> user1 =  userService.searchAll();
        return ResponseEntity.ok(user1);
    }
}
