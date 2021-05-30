package com.blm.taskme.service.implementation;

import com.blm.taskme.api.v1.request.RegistrationRequest;
import com.blm.taskme.api.v1.response.UserProfileResponse;
import com.blm.taskme.domain.Role;
import com.blm.taskme.domain.User;
import com.blm.taskme.domain.enums.RoleName;
import com.blm.taskme.domain.enums.UserStatus;
import com.blm.taskme.repository.RoleRepository;
import com.blm.taskme.repository.UserRepository;
import com.blm.taskme.security.DefaultUserDetails;
import com.blm.taskme.service.UserService;
import com.blm.taskme.service.exception.RegistrationException;
import com.blm.taskme.service.exception.UserNotFoundException;
import com.blm.taskme.service.exception.ValidationException;
import com.blm.taskme.service.mapper.UserMapper;
import com.blm.taskme.util.ValidationUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.security.Principal;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Validator validator;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultUserService(UserRepository userRepository, UserMapper userMapper, Validator validator, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.validator = validator;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void register(RegistrationRequest request) throws RegistrationException, ValidationException {
        System.out.println(validator);
        ValidationUtil.validate(validator, request);

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("User with this email already exists");
        }

        User user = new User();

        userMapper.mergeIntoUser(user, request);

        user.setId(null);
        user.setStatus(UserStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName(RoleName.DEFAULT_USER)
                .orElseThrow(() -> new RuntimeException("Role DEFAULT_USER not found"));

        user.addRole(role);

        userRepository.save(user);
    }

    @Transactional
    @Override
    public UserProfileResponse getProfile(@NonNull String username) throws UserNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return userMapper.toProfileResponse(user);
    }

    @Transactional
    @Override
    public Optional<User> getUser(@NonNull Principal principal) {
        if ( !(principal instanceof Authentication) ) {
            return Optional.empty();
        }

        Authentication authentication = (Authentication)principal;

        if ( !(authentication.getDetails() instanceof DefaultUserDetails) ) {
            return Optional.empty();
        }

        DefaultUserDetails userDetails = (DefaultUserDetails) authentication.getDetails();

        return Optional.ofNullable(userDetails.getUser());
    }

    @Transactional
    @Override
    public Optional<User> getUserByEmail(@NonNull String email) {
        return userRepository.findByEmail(email);
    }
}
