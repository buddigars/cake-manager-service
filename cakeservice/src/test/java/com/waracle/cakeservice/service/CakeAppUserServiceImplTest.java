package com.waracle.cakeservice.service;

import com.waracle.cakeservice.exceptions.CakeAlreadyExistsException;
import com.waracle.cakeservice.exceptions.ResourceNotFoundException;
import com.waracle.cakeservice.model.CakeAppUser;
import com.waracle.cakeservice.repository.CakeUserRepo;
import com.waracle.cakeservice.repository.RoleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static com.waracle.cakeservice.utils.ResponseUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CakeAppUserServiceImplTest {
    @InjectMocks
    private CakeAppUserServiceImpl service;

    @Mock
    private CakeUserRepo repo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepo roleRepo;

    @Mock
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void saveUser() {
        when(repo.save(any())).thenReturn(getCakeAppUser(""));
        CakeAppUser actual = service.saveUser(getCakeAppUser(""));

        assertNotNull(actual);
        assertEquals("John", actual.getFirstName());
    }

    @Test
    void saveUserWhenUserExistsThrowsExp() {
        when(repo.findByUsername(anyString())).thenReturn(getCakeAppUser(""));

        assertThrows(CakeAlreadyExistsException.class, () -> service.saveUser(getCakeAppUser(" ")));


    }

    @Test
    void assignRoleToUser() {
        when(repo.findByUsername(anyString())).thenReturn(getCakeAppUser("VIEW"));
        when(roleRepo.findByName(anyString())).thenReturn(getRoleObj("VIEW"));

        service.assignRoleToUser("test", "VIEW");

        assertEquals(1, getCakeAppUser(" ").getRoles().size());
    }

    @Test
    void addRoleToEmpException() {
        when(repo.findByUsername(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.assignRoleToUser("test","VIEW"));
    }
    @Test
    void addRoleToEmpNullRoleException() {
        when(repo.findByUsername(anyString())).thenReturn(getCakeAppUser("VIEW"));
        when(roleRepo.findByName(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.assignRoleToUser("test","VIEW"));
    }

    @Test
    void loadUserByUsername() {
        when(repo.findByUsername(anyString())).thenReturn(getCakeAppUser(" "));

        UserDetails user = service.loadUserByUsername("test");

        assertNotNull(user);
        assertEquals("admin", user.getUsername());
    }

    @Test
    void loadUserByUsernameUserNotFound() {
        when(repo.findByUsername(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("test"));
    }

    @Test
    void getUsers() {
        when(repo.findAll()).thenReturn(Arrays.asList(getCakeAppUser(" ")));

        List<CakeAppUser> actual = service.getUsers();

        assertNotNull(actual);
        assertEquals(1, actual.size());
    }
}