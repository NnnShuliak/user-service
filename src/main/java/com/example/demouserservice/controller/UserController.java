package com.example.demouserservice.controller;

import com.example.demouserservice.domain.User;
import com.example.demouserservice.dto.UserDTO;
import com.example.demouserservice.exceptions.DateException;
import com.example.demouserservice.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(name = "from",required = false) LocalDate from,
            @RequestParam(name = "to",required = false) LocalDate to
    ) {
        if (from == null && to == null) return ResponseEntity.ok(userService.findAll());

        from = Optional.ofNullable(from).orElse(LocalDate.of(1,1,1));
        to = Optional.ofNullable(to).orElse(LocalDate.now());

        if (from.isAfter(to)) throw new DateException(from,to);

        List<User> users = userService.findAll(from, to);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> findById(@PathVariable(name = "userId") Long userId) {
        User foundUser = userService.findById(userId);
        return ResponseEntity.ok(foundUser);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user, HttpServletResponse response) {
        User createdUser = userService.create(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        response.setHeader(HttpHeaders.LOCATION, location.toString());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> partialUpdate(@PathVariable("userId") Long userId,
                                           @RequestBody UserDTO user) {
        userService.partialUpdate(userId,user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> fullyUpdate(@PathVariable("userId") Long userId,
                                           @RequestBody User user) {
        userService.fullyUpdate(userId,user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> delete(@PathVariable("userId") Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}
