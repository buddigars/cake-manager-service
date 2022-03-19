package com.waracle.cakeservice;

import com.waracle.cakeservice.model.Cake;
import com.waracle.cakeservice.model.CakeAppUser;
import com.waracle.cakeservice.model.Role;
import com.waracle.cakeservice.service.CakeAppUserService;
import com.waracle.cakeservice.service.CakeService;
import com.waracle.cakeservice.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class CakeserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CakeserviceApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(CakeAppUserService cakeAppUserService, CakeService cakeService, RoleService roleService) {
        return args -> {
            roleService.saveRole(Role.builder().id(null).name("ADMIN").build());
            roleService.saveRole(Role.builder().id(null).name("VIEW").build());

            cakeAppUserService.saveUser(CakeAppUser.builder().id(null).firstName("John").lastName("Brant").username("admin").password("1234")
                    .email("john.brant@waracle.com").roles(new ArrayList<>()).build());

            cakeAppUserService.saveUser(CakeAppUser.builder().id(null).firstName("Ravi").lastName("Buddiga").username("user")
                    .password("1234")
                    .email("ravi.buddiga@waracle.com").roles(new ArrayList<>()).build());

            cakeAppUserService.assignRoleToUser("user", "VIEW");
            cakeAppUserService.assignRoleToUser("admin", "ADMIN");

            cakeService.addCake(Cake.builder().id(null).name("Red Velvet").clientName("Ravi").build());
            cakeService.addCake(Cake.builder().id(null).name("Black Forest").clientName("Ravi").build());
            cakeService.addCake(Cake.builder().id(null).name("Chocolate").clientName("Ravi").build());
            cakeService.addCake(Cake.builder().id(null).name("Belgium Chocolate").clientName("John").build());
            cakeService.addCake(Cake.builder().id(null).name("Red Velvet").clientName("John").build());
            cakeService.addCake(Cake.builder().id(null).name("Black Forest").clientName("John").build());

        };

    }
}