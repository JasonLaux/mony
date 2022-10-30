package com.fdmgroup.mony.controller;
import com.fdmgroup.mony.dto.*;
import com.fdmgroup.mony.exception.BankAccountNotFoundException;
import com.fdmgroup.mony.exception.LedgerNotFoundException;
import com.fdmgroup.mony.exception.UserAlreadyExistsException;
import com.fdmgroup.mony.model.BankAccount;
import com.fdmgroup.mony.model.Bill;
import com.fdmgroup.mony.model.Ledger;
import com.fdmgroup.mony.model.User;
import com.fdmgroup.mony.repository.BankAccountRepository;
import com.fdmgroup.mony.service.BankAccountService;
import com.fdmgroup.mony.service.BillService;
import com.fdmgroup.mony.service.LedgerService;
import com.fdmgroup.mony.service.UserService;
import com.fdmgroup.mony.util.ModelToDTO;
import com.fdmgroup.mony.util.ObjectFactory;
import com.fdmgroup.mony.util.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private AuthController authController;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private ObjectFactory objectFactory;

    @BeforeEach
    void setup(){
        authController = new AuthController(passwordEncoder, authenticationManager, userService, tokenProvider, objectFactory);
    }

    @Test
    void test_Login(){
        String username = "test";
        String password = "123";
        LoginRequest loginRequest = mock(LoginRequest.class);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = mock(UsernamePasswordAuthenticationToken.class);
        Authentication authentication = mock(Authentication.class);
        when(objectFactory.createAuthToken(username, password)).thenReturn(usernamePasswordAuthenticationToken);
        when(authenticationManager.authenticate(usernamePasswordAuthenticationToken)).thenReturn(authentication);
        when(loginRequest.getUsername()).thenReturn(username);
        when(loginRequest.getPassword()).thenReturn(password);

        authController.login(loginRequest);

        verify(authenticationManager).authenticate(usernamePasswordAuthenticationToken);
        verify(tokenProvider).generate(authentication);
        verify(loginRequest).getUsername();
        verify(loginRequest).getPassword();
    }

    @Test
    void test_SignUp_ThrowUserAlreadyExistsException_Username(){
        String username = "test";
        SignUpRequest signUpRequest = mock(SignUpRequest.class);
        when(signUpRequest.getUsername()).thenReturn(username);
        when(userService.existsByUsername(username)).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, ()-> authController.signUp(signUpRequest));
    }

    @Test
    void test_SignUp_ThrowUserAlreadyExistsException_Email(){
        String email = "test";
        SignUpRequest signUpRequest = mock(SignUpRequest.class);
        when(signUpRequest.getEmail()).thenReturn(email);
        when(userService.existsByEmail(email)).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, ()-> authController.signUp(signUpRequest));
    }

    @Test
    void test_SignUp(){
        String username = "test";
        String password = "123";
        SignUpRequest signUpRequest = mock(SignUpRequest.class);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = mock(UsernamePasswordAuthenticationToken.class);
        Authentication authentication = mock(Authentication.class);
        when(objectFactory.createAuthToken(username, password)).thenReturn(usernamePasswordAuthenticationToken);
        when(authenticationManager.authenticate(usernamePasswordAuthenticationToken)).thenReturn(authentication);
        when(signUpRequest.getUsername()).thenReturn(username);
        when(signUpRequest.getPassword()).thenReturn(password);

        authController.signUp(signUpRequest);

        verify(authenticationManager).authenticate(usernamePasswordAuthenticationToken);
        verify(tokenProvider).generate(authentication);
        verify(signUpRequest,times(4)).getUsername();
        verify(signUpRequest, times(3)).getPassword();
    }




}
