package com.rubaks.timetrackerapp.service;

import com.rubaks.timetrackerapp.dto.auth.AuthenticationRequest;
import com.rubaks.timetrackerapp.dto.auth.AuthenticationResponse;
import com.rubaks.timetrackerapp.dto.auth.RegisterRequest;
import com.rubaks.timetrackerapp.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
