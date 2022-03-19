package com.waracle.cakeservice.controller;

import com.waracle.cakeservice.model.Role;
import com.waracle.cakeservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/save")
    public ResponseEntity<Role> createCakeAppUser(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());

        return ResponseEntity.created(uri).body(roleService.saveRole(role));
    }

    @GetMapping("/")
    public ResponseEntity<List<Role>> getRoles() {

        return ResponseEntity.ok().body(roleService.getAllRoles());
    }
}
