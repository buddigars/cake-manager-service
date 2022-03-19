package com.waracle.cakeservice.repository;

import com.waracle.cakeservice.model.Cake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CakeRepo extends JpaRepository<Cake, Long> {
    @Query("SELECT c FROM Cake c WHERE c.clientName =:clientName and c.name=:cakeName")
    Cake findCakeByClientName(@Param("cakeName") String cakeName, @Param("clientName") String clientName);

    @Query("SELECT c FROM Cake c WHERE c.clientName =:clientName")
    List<Cake> findAllCakesByClientName(@Param("clientName") String clientName);
}
