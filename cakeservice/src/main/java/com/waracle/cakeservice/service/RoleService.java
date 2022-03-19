package com.waracle.cakeservice.service;

import com.waracle.cakeservice.model.Role;

import java.util.List;

public interface RoleService {
    Role saveRole(Role role);
    List<Role> getAllRoles();
}
