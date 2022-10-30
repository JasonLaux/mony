package com.fdmgroup.mony.service;
import com.fdmgroup.mony.exception.UserNotFoundException;
import com.fdmgroup.mony.model.User;
import com.fdmgroup.mony.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private User mockUser;

    @Mock
    private User mockModifiedUser;

    @BeforeEach
    void setup(){
        userService = new UserService(mockUserRepository);
    }

    @Test
    void test_FindUser_ById(){

        long id = 1;

        when(mockUserRepository.findById(id)).thenReturn(Optional.of(mockUser));

        assertEquals(mockUser, userService.getUserById(id));
    }

    @Test
    void test_FindUser_ById_ThrowUserNotFoundException(){

        long id = 1;

        when(mockUserRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(id));

    }

    @Test
    void test_FindUser_ByUsername(){

        String username = "test";

        when(mockUserRepository.findUserByUsername(username)).thenReturn(Optional.of(mockUser));

        assertEquals(mockUser, userService.getUserByUsername(username));
    }

    @Test
    void test_FindUser_ByUsername_ThrowUserNotFoundException(){

        String username = "test";

        when(mockUserRepository.findUserByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername(username));

    }

    @Test
    void test_DeletedUser_ById(){
        long id = 1;
        when(mockUserRepository.existsById(id)).thenReturn(true);

        userService.deleteUserById(id);

        verify(mockUserRepository).deleteById(id);
    }

    @Test
    void test_DeletedUser_ById_ThrowUserNotFoundException(){
        long id = 1;
        when(mockUserRepository.existsById(id)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(id));
    }

    @Test
    void test_updateUser(){
        long id = 1;
        when(mockUserRepository.findById(id)).thenReturn(Optional.of(mockUser));

        User result = userService.updateUser(id, mockModifiedUser);

        verify(mockUser).setUsername(mockModifiedUser.getUsername());
        verify(mockUser).setPassword(mockModifiedUser.getPassword());
        verify(mockUser).setEmail(mockModifiedUser.getEmail());
        verify(mockUserRepository).save(mockUser);
        assertEquals(mockUserRepository.save(mockUser), result);
    }

    @Test
    void test_updateUser_ThrowUserNotFoundException(){
        long id = 1;
        when(mockUserRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,() -> userService.updateUser(id, mockModifiedUser));
    }

    @Test
    void test_ExistsByEmail(){
        String email = "test";

        userService.existsByEmail(email);

        verify(mockUserRepository).existsByEmail(email);
    }

    @Test
    void test_ExistsByUsername(){
        String username = "test";

        userService.existsByUsername(username);

        verify(mockUserRepository).existsByUsername(username);
    }

    @Test
    void test_saveUser(){

        userService.save(mockUser);

        verify(mockUserRepository).save(mockUser);
    }

}
