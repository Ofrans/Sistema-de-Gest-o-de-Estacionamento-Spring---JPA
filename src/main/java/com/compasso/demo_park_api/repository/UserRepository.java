package com.compasso.demo_park_api.repository;

import com.compasso.demo_park_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);

    @Query("select u.role from User u where u.username like :username")
    User.Role findRoleByUsername(String username);
}
