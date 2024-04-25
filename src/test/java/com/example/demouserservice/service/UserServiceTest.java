package com.example.demouserservice.service;

import com.example.demouserservice.domain.User;
import com.example.demouserservice.dto.UserDTO;
import com.example.demouserservice.exceptions.NoSuchUserException;
import com.example.demouserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RequiredArgsConstructor
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testFindAll() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));

        // Act
        List<User> result = userService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllByBirthDateLimits() {
        // Arrange
        LocalDate from = LocalDate.now().minusYears(30);
        LocalDate to = LocalDate.now();
        when(userRepository.findAllByBirthDateLimits(from, to)).thenReturn(List.of(new User(), new User()));

        // Act
        List<User> result = userService.findAll(from, to);

        // Assert
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAllByBirthDateLimits(from, to);
    }

    @Test
    public void testFindById() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User result = userService.findById(userId);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testFindById_UserNotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchUserException.class, () -> userService.findById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testCreate() {
        // Arrange
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.create(user);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testPartialUpdate() {
        // Arrange
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        User existingUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        // Act
        User result = userService.partialUpdate(userId, userDTO);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testFullyUpdate() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.fullyUpdate(userId, user);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteById() {
        // Arrange
        Long userId = 1L;

        // Act
        userService.deleteById(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }
}
