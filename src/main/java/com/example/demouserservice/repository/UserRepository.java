package com.example.demouserservice.repository;

import com.example.demouserservice.domain.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u where u.birthDate between :from and :to")
    List<User> findAllByBirthDateLimits(LocalDate from,LocalDate to);
}
