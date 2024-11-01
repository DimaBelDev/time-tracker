package com.rubaks.timetrackerapp.service;


import com.rubaks.timetrackerapp.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserById(Long id);

}
