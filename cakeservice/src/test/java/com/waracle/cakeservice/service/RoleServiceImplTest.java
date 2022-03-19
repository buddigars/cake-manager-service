package com.waracle.cakeservice.service;

import com.waracle.cakeservice.model.Role;
import com.waracle.cakeservice.repository.RoleRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.waracle.cakeservice.utils.ResponseUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @InjectMocks
    private RoleServiceImpl service;

    @Mock
    private RoleRepo repo;
    @Test
    void saveRole() {
        when(repo.save(any())).thenReturn(getRoleObj("ROLE_1"));

        Role actual = service.saveRole(getRoleObj("ROLE_1"));

        assertNotNull(actual);
        assertEquals("ROLE_1", actual.getName());
    }
}