package com.waracle.cakeservice.service;

import com.waracle.cakeservice.model.CakeAppUser;

public interface CakeAppUserService {
    CakeAppUser saveUser(CakeAppUser cakeAppUser);

    void assignRoleToUser(String username, String roleName);
}
