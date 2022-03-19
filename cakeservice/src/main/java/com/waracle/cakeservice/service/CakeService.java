package com.waracle.cakeservice.service;

import com.waracle.cakeservice.model.Cake;
import com.waracle.cakeservice.model.DataResponse;

public interface CakeService {
    DataResponse getCakesByClientName(String clientName);

    DataResponse getCakesForAllClients();

    Cake addCake(Cake cake);
}
