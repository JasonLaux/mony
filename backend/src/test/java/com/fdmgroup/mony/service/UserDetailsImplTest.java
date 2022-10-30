package com.fdmgroup.mony.service;
import com.fdmgroup.mony.dto.CustomUserDetails;
import com.fdmgroup.mony.model.User;
import com.fdmgroup.mony.util.ObjectFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsImplTest {

    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserService mockUserService;

    @Mock
    private CustomUserDetails mockCustomUserDetails;

    @Mock
    private ObjectFactory mockCustomUserDetailsFactory;

    @Mock
    private User mockUser;

    @BeforeEach
    void setup(){
        userDetailsServiceImpl = new UserDetailsServiceImpl(mockUserService, mockCustomUserDetailsFactory);
    }

    @Test
    void test_LoadUserByUsername(){
        String username = "test";
        when(mockUserService.getUserByUsername(username)).thenReturn(mockUser);
        when(mockCustomUserDetailsFactory.createCustomUserDetails()).thenReturn(mockCustomUserDetails);

        UserDetails result = userDetailsServiceImpl.loadUserByUsername(username);

        verify(mockUserService).getUserByUsername(username);
        verify(mockCustomUserDetails).setId(mockUser.getId());
        verify(mockCustomUserDetails).setUsername(mockUser.getUsername());
        verify(mockCustomUserDetails).setPassword(mockUser.getPassword());
        verify(mockCustomUserDetails).setEmail(mockUser.getEmail());
        assertEquals(mockCustomUserDetails, result);

    }


}
