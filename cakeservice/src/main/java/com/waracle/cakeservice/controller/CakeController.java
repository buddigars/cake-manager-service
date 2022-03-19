package com.waracle.cakeservice.controller;

import com.waracle.cakeservice.model.Cake;
import com.waracle.cakeservice.model.DataResponse;
import com.waracle.cakeservice.service.CakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/cakes")
@RequiredArgsConstructor
public class CakeController {
    private final CakeService cakeService;

    @PostMapping("/")
    public ResponseEntity<Cake> addCake(@RequestBody Cake cake) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/cakes").toUriString());
        if (!StringUtils.hasText(cake.getName())
                || !StringUtils.hasText(cake.getClientName())) {
            throw new IllegalArgumentException("Insufficient details to create cake order");
        }
        return ResponseEntity.created(uri).body(cakeService.addCake(cake));
    }

    @GetMapping("/")
    public ResponseEntity<DataResponse> getCakesForAllClients() {
        return ResponseEntity.ok().body(cakeService.getCakesForAllClients());
    }

    @GetMapping("/{clientName}")
    public ResponseEntity<DataResponse> getCakesByClientName(@PathVariable String clientName) {
        if (!StringUtils.hasText(clientName)) {
            throw new IllegalArgumentException("Client name is empty");
        }
        return ResponseEntity.ok().body(cakeService.getCakesByClientName(clientName));
    }


}


