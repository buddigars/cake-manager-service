package com.waracle.cakeservice.repository;

import com.waracle.cakeservice.model.CakeAppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CakeUserRepo extends JpaRepository<CakeAppUser, Long> {
    CakeAppUser findByUsername(String username);

    @Query("SELECT c FROM Cake c WHERE c.name =:cakeName")
    List<CakeAppUser> findAllCakesByClientName(@Param("cakeName") String cakeName);
}
