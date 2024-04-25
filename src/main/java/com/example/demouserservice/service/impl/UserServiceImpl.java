package com.example.demouserservice.service.impl;

import com.example.demouserservice.domain.User;
import com.example.demouserservice.dto.UserDTO;
import com.example.demouserservice.exceptions.NoSuchUserException;
import com.example.demouserservice.repository.UserRepository;
import com.example.demouserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository repository;


    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<User> findAll(LocalDate from, LocalDate to) {
        return repository.findAllByBirthDateLimits(from, to);
    }
    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchUserException(id));
    }

    @Override
    public User create(User user) {
        user.setId(null);
        return repository.save(user);
    }

    @Override
    public User partialUpdate(Long id, UserDTO userUpdateInfo) {
        User exictedUser = repository.findById(id).orElseThrow(() -> new NoSuchUserException(id));
        BeanUtils.copyProperties(userUpdateInfo, exictedUser, getNullPropertyNames(userUpdateInfo));
        return repository.save(exictedUser);
    }

    @Override
    public User fullyUpdate(Long id, User user) {
        user.setId(id);
        return repository.save(user);
    }

    @Override
    public void deleteById(Long userId) {
        repository.deleteById(userId);
    }

    private String[] getNullPropertyNames(UserDTO source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            Object srcValue = src.getPropertyValue(propertyDescriptor.getName());
            if (srcValue == null) {
                emptyNames.add(propertyDescriptor.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


}
