package com.waracle.cakeservice.controller;

import com.waracle.cakeservice.model.AssignRoleToUser;
import com.waracle.cakeservice.model.CakeAppUser;
import com.waracle.cakeservice.service.CakeAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CakeAppUserController {
    private final CakeAppUserService cakeAppUserService;

    @PostMapping("/cake-app-user/save")
    public ResponseEntity<CakeAppUser> addCakeAppUser(@RequestBody CakeAppUser cakeAppUser) {
        if (!StringUtils.hasText(cakeAppUser.getUsername())
                || !StringUtils.hasText(cakeAppUser.getPassword())) {
            throw new IllegalArgumentException("Insufficient details to create user");
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/cake-app-user/save").toUriString());

        return ResponseEntity.created(uri).body(cakeAppUserService.saveUser(cakeAppUser));
    }

    @PostMapping("/role/assign-to-user")
    public ResponseEntity<?> assignRoleToUser(@RequestBody AssignRoleToUser roleToUser) {
        cakeAppUserService.assignRoleToUser(roleToUser.getUsername(), roleToUser.getRoleName());
        return ResponseEntity.ok().build();
    }
}


