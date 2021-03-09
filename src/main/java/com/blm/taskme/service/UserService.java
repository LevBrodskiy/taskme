package com.blm.taskme.service;

import com.blm.taskme.api.v1.request.RegistrationRequest;
import com.blm.taskme.api.v1.response.UserProfileResponse;
import com.blm.taskme.domain.User;
import com.blm.taskme.service.exception.RegistrationException;
import com.blm.taskme.service.exception.UserNotFoundException;
import com.blm.taskme.service.exception.ValidationException;

import java.security.Principal;
import java.util.Optional;

public interface UserService {
    void register(RegistrationRequest request)
            throws RegistrationException, ValidationException;

     UserProfileResponse getProfile(String username)
            throws UserNotFoundException;

    Optional<User> getUser(Principal principal);

    Optional<User> getUserByEmail(String email);
}
