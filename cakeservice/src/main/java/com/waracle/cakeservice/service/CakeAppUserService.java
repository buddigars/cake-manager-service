package com.waracle.cakeservice.service;

import com.waracle.cakeservice.model.CakeAppUser;

import java.util.List;

public interface CakeAppUserService {
    CakeAppUser saveUser(CakeAppUser cakeAppUser);

    void assignRoleToUser(String username, String roleName);

    List<CakeAppUser> getUsers();
}
