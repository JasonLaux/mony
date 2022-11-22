package com.fdmgroup.mony.controller;
import com.fdmgroup.mony.dto.AuthResponse;
import com.fdmgroup.mony.dto.LoginRequest;
import com.fdmgroup.mony.dto.SignUpRequest;
import com.fdmgroup.mony.exception.UserAlreadyExistsException;
import com.fdmgroup.mony.model.User;
import com.fdmgroup.mony.service.UserService;
import com.fdmgroup.mony.util.ObjectFactory;
import com.fdmgroup.mony.util.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * Authentication controller for handling login and signup request. AuthenticationManager will use UserDetail service
 * to examine whether the credentials are matched or not. JWT will be generated based on the authentication token.
 * @author Jason Liu
 * @version 1.0
 */
@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final ObjectFactory objectFactory;

    /**
     * Handle login request and return the jwt token.
     * @param loginRequest User input object containing username and password
     * @return             JWT token
     */

    @Operation(summary = "log in using username and password")
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest){
        String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
        return objectFactory.createAuthResponse(token);
    }

    /**
     * Handle sign up request return the token to the frontend.
     * @param signUpRequest Object contains username, email and password.
     * @return              Token String.
     */
    @Operation(summary = "sign up using username, email and password")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public AuthResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        log.info("user is currently signing up");
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            throw new UserAlreadyExistsException(String.format("Username %s already been used", signUpRequest.getUsername()));
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistsException(String.format("Email %s already been used", signUpRequest.getEmail()));
        }

        userService.save(mapSignUpRequestToUser(signUpRequest));
        log.info("user with username {} and email {} and password {} is signed up successfully", signUpRequest.getUsername(), signUpRequest.getPassword(), signUpRequest.getEmail() );
        String token = authenticateAndGetToken(signUpRequest.getUsername(), signUpRequest.getPassword());
        return objectFactory.createAuthResponse(token);
    }

    /**
     * Pass UsernamePasswordAuthenticationToken to the default AuthenticationProvider which will use the userDetails
     * service to get the user based on username and compare the encrypted password
     * @param username Username
     * @param password Password
     * @return         JWT String
     */
    @Operation(summary = "authenticate user using username and password, returns token")
    protected String authenticateAndGetToken(String username, String password) {
        // Pass UsernamePasswordAuthenticationToken to the default AuthenticationProvider which will use the userDetails
        // service to get the user based on username and compare the encrypted password
        Authentication authentication = authenticationManager.authenticate(objectFactory.createAuthToken(username, password));
        return tokenProvider.generate(authentication);
    }

    /**
     * Map the user input request to the User object.
     * @param signUpRequest Signup request object
     * @return              User object
     */
    public User mapSignUpRequestToUser(SignUpRequest signUpRequest) {

//        user.setRole(WebSecurityConfig.USER);
        return User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();
    }

}
