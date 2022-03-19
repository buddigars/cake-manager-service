package com.waracle.cakeservice.controller;

import com.waracle.cakeservice.exceptions.CakeExceptionHandler;
import com.waracle.cakeservice.model.AssignRoleToUser;
import com.waracle.cakeservice.service.CakeAppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.waracle.cakeservice.utils.ResponseUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CakeAppUserControllerTest {
    @InjectMocks
    private CakeAppUserController controller;

    @Mock
    private CakeAppUserService service;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CakeExceptionHandler())
                .build();
    }

    @Test
    void addCakeAppUser() throws Exception {
        lenient().when(service.saveUser(any())).thenReturn(getCakeAppUser(""));

        mockMvc.perform(post("/api/v1/cake-app-user/save")
                        .content(convertToJson(getCakeAppUser("")))
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().is(CREATED.value()))
                .andExpect(content().json(convertToJson(getCakeAppUser(""))));

        verify(service).saveUser(any());
    }

    @Test
    void addCakeAppUserWithoutUsername() throws Exception {
        lenient().when(service.saveUser(any())).thenReturn(getCakeAppUser(""));

        mockMvc.perform(post("/api/v1/cake-app-user/save")
                        .content(convertToJson(getCakeAppUser("username")))
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().is(BAD_REQUEST.value()));
    }

    @Test
    void addCakeAppUserWithoutPwd() throws Exception {
        lenient().when(service.saveUser(any())).thenReturn(getCakeAppUser(""));

        mockMvc.perform(post("/api/v1/cake-app-user/save")
                        .content(convertToJson(getCakeAppUser("password")))
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().is(BAD_REQUEST.value()));
    }

    @Test
    void assignRoleToUser() throws Exception {
        lenient().when(service.saveUser(any())).thenReturn(getCakeAppUser(""));
        mockMvc.perform(post("/api/v1/role/assign-to-user")
                        .content(convertToJson(getAssignRoleToUserReq()))
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().is(OK.value()));

        verify(service).assignRoleToUser(anyString(), anyString());

    }


    public static AssignRoleToUser getAssignRoleToUserReq() {
        return AssignRoleToUser.builder().username("buddigars").roleName("ROLE_1").build();
    }
}