package com.waracle.cakeservice.controller;

import com.waracle.cakeservice.exceptions.CakeExceptionHandler;
import com.waracle.cakeservice.model.DataResponse;
import com.waracle.cakeservice.service.CakeAppUserService;
import com.waracle.cakeservice.service.CakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static com.waracle.cakeservice.utils.ResponseUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CakeControllerTest {
    @InjectMocks
    private CakeController controller;

    @Mock
    private CakeService service;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CakeExceptionHandler())
                .build();
    }

    @Test
    void addCake() throws Exception {
        lenient().when(service.addCake(any())).thenReturn(getCake(""));

        mockMvc.perform(post("/api/v1/cakes/")
                        .content(convertToJson(getCake("")))
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().is(CREATED.value()))
                .andExpect(content().json(convertToJson(getCake(""))));

        verify(service).addCake(any());
    }

    @Test
    void addCakeWithoutCakeNameThrowsExp() throws Exception {
        lenient().when(service.addCake(any())).thenReturn(getCake("cakeName"));

        mockMvc.perform(post("/api/v1/cakes/")
                        .content(convertToJson(getCake("cakeName")))
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().is(BAD_REQUEST.value()));
    }

    @Test
    void addCakeWithoutClientNameThrowsExp() throws Exception {
        lenient().when(service.addCake(any())).thenReturn(getCake("clientName"));

        mockMvc.perform(post("/api/v1/cakes/")
                        .content(convertToJson(getCake("clientName")))
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().is(BAD_REQUEST.value()));
    }

    @Test
    void getCakesForAllClients() throws Exception {
        lenient().when(service.getCakesForAllClients()).thenReturn(DataResponse.builder().status("Success").cakes(Arrays.asList(getCake(""))).records(1).build());

        mockMvc.perform(get("/api/v1/")).andExpect(status().is(OK.value()));


        verify(service).getCakesForAllClients();
    }

    @Test
    void getCakesByClientName() throws Exception {
        lenient().when(service.getCakesByClientName(anyString())).thenReturn(DataResponse.builder().status("Success").cakes(Arrays.asList(getCake(""))).records(1).build());

        mockMvc.perform(get(String.format("/api/v1/cakes/%s", "Ravi"))).andExpect(status().is(OK.value()));

        verify(service).getCakesByClientName(anyString());
    }

    @Test
    void getCakesByClientNameWithoutClientNameThrowsExp() throws Exception {
        lenient().when(service.getCakesByClientName(anyString())).thenReturn(DataResponse.builder().status("Success").cakes(Arrays.asList(getCake(""))).records(1).build());

        mockMvc.perform(get(String.format("/api/v1/cakes/%s", " "))).andExpect(status().is(BAD_REQUEST.value()));
    }
}