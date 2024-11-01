package com.rubaks.timetrackerapp.service.impl;

import com.rubaks.timetrackerapp.entity.User;
import com.rubaks.timetrackerapp.repository.UserRepository;
import com.rubaks.timetrackerapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }


}
