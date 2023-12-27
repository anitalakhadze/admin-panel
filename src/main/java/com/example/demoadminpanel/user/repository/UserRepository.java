package com.example.demoadminpanel.user.repository;

import com.example.demoadminpanel.user.entity.User;
import com.example.demoadminpanel.user.model.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query(value = "select u from User u where u.status in (:statuses)")
    List<User> findUsersByStatusIn(@Param("statuses") List<UserStatus> statuses);

}
