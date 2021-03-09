package com.blm.taskme.service.implementation;

import com.blm.taskme.api.v1.request.RegistrationRequest;
import com.blm.taskme.api.v1.response.UserProfileResponse;
import com.blm.taskme.domain.User;
import com.blm.taskme.repository.UserRepository;
import com.blm.taskme.security.DefaultUserDetails;
import com.blm.taskme.service.exception.RegistrationException;
import com.blm.taskme.service.exception.UserNotFoundException;
import com.blm.taskme.service.exception.ValidationException;
import com.blm.taskme.service.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DefaultUserServiceTest {
    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;

    @InjectMocks
    private DefaultUserService userService;


    @Test
    void register_Should_Throw_RegistrationException() {
        when(repository.existsByEmail(anyString()))
                .thenReturn(true);

        when(repository.save(any(User.class)))
                .thenReturn(new User());

        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("");
        request.setNickname("");
        request.setPassword("");

        assertThrows(RegistrationException.class, () -> userService.register(request));
    }

    @Test
    void register_Should_Register_New_User() throws RegistrationException, ValidationException {
        when(repository.existsByEmail(anyString()))
                .thenReturn(true);

        when(repository.save(any(User.class)))
                .thenReturn(new User());

        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("");
        request.setNickname("");
        request.setPassword("");

        userService.register(request);
    }

    @Test
    void getProfile_Should_Return_Profile() throws UserNotFoundException {
        UserProfileResponse expected = new UserProfileResponse();
        when(repository.findByEmail(anyString()))
                .thenReturn(Optional.of(new User()));

        when(mapper.toProfileResponse(any(User.class)))
                .thenReturn(expected);

        UserProfileResponse actual = userService.getProfile("");

        assertEquals(expected, actual);
    }

    @Test
    void getProfile_Should_Throw_UserNotFoundException() {
        when(repository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.getProfile(""));
    }

    @Test
    void getUser_Should_Return_User() {
        User expected = new User();
        DefaultUserDetails details = new DefaultUserDetails(expected);
        Principal principal = new UsernamePasswordAuthenticationToken(details, "");

        User actual = userService.getUser(principal).get();

        assertEquals(expected, actual);
    }

    @Test
    void getUser_Should_Return_Empty() {
        Principal principal = new UsernamePasswordAuthenticationToken(null, null);

        Optional<User> user = userService.getUser(principal);

        boolean empty = !user.isPresent();

        assertTrue(empty);
    }
}