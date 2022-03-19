package com.waracle.cakeservice.controller;

import com.waracle.cakeservice.exceptions.CakeExceptionHandler;
import com.waracle.cakeservice.model.Role;
import com.waracle.cakeservice.service.RoleService;
import com.waracle.cakeservice.utils.ResponseUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.waracle.cakeservice.utils.ResponseUtils.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {
    @InjectMocks
    private RoleController controller;

    @Mock
    private RoleService service;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CakeExceptionHandler())
                .build();
    }
    @Test
    void createCakeAppUser() throws Exception{
        lenient().when(service.saveRole(any())).thenReturn(getRoleObj());

        mockMvc.perform(post("/api/v1/role/save")
                        .content(convertToJson(getRoleObj()))
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().is(CREATED.value()))
                .andExpect(content().json(convertToJson(getRoleObj())));

        assertNotNull(getRoleObj());

    }

    public static Role getRoleObj(){
        return Role.builder().id(22L).name("ROLE_1").build();
    }
}