package com.waracle.cakeservice.service;

import com.waracle.cakeservice.exceptions.CakeAlreadyExistsException;
import com.waracle.cakeservice.exceptions.DatabaseOperationException;
import com.waracle.cakeservice.model.Cake;
import com.waracle.cakeservice.model.CakeAppUser;
import com.waracle.cakeservice.model.DataResponse;
import com.waracle.cakeservice.repository.CakeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CakeServiceImpl implements CakeService {
    private final CakeRepo cakeRepo;

    @Override
    public DataResponse getCakesByClientName(String clientName) {
        List<Cake> cakes;
        log.info("fetching all cakes who belong to {}", clientName);
        try {
            if (!StringUtils.hasText(clientName)) {
                cakes = cakeRepo.findAll();
                return DataResponse.builder().cakes(cakes).records(cakes.size()).status("Success").build();
            }
            cakes = cakeRepo.findAllCakesByClientName(clientName.toUpperCase());
            if (CollectionUtils.isEmpty(cakes)) {
                log.info("No cakes for client= {}", clientName);
                return DataResponse.builder().cakes(new ArrayList<>()).status("failed")
                        .errorMessage("Cakes does not exist for provided client name " + clientName).build();
            }
            return DataResponse.builder().cakes(cakes).records(cakes.size()).status("Success").build();
        } catch (PersistenceException pe) {
            log.error("Something went wrong while fetching getCakesByClientName={}", pe.getMessage());
            throw new DatabaseOperationException("Something went wrong while fetching getCakesByClientName" + pe.getMessage());
        }
    }

    @Override
    public DataResponse getCakesForAllClients() {
        return getCakesByClientName(null);
    }

    @Override
    public Cake addCake(Cake cake) {
        Cake saveCake = Cake.builder().id(cake.getId()).name(cake.getName().toUpperCase()).clientName(cake.getClientName().toUpperCase()).build();
        log.info("Saving new cake {} to DB", saveCake.getName());
        Cake cakeFoundInDB = cakeRepo.findCakeByClientName(saveCake.getName(), saveCake.getClientName());
        if (Objects.nonNull(cakeFoundInDB)) {
            log.info("Cake already exists {}", cake.getName());
            throw new CakeAlreadyExistsException("Cake {} already exists" + cake.getName() + " for Client {} " + cake.getClientName());
        }
        log.info("New cake {} saved successfully to DB", cake.getName());
        return cakeRepo.save(saveCake);
    }


}