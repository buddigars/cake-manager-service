package com.waracle.cakeservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakeservice.model.Cake;
import com.waracle.cakeservice.model.CakeAppUser;
import com.waracle.cakeservice.model.Role;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class ResponseUtils {
    public static String convertToJson(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return obj == null ? "" : mapper.writeValueAsString(obj);

        } catch (JsonProcessingException j) {
            throw new IllegalArgumentException(j);
        }
    }

    public static CakeAppUser getCakeAppUser(String dontPopulate) {
        if(StringUtils.pathEquals(dontPopulate, "username")){
            return CakeAppUser.builder().id(1L).firstName("John").lastName("Brant").password("1234")
                    .email("john.brant@waracle.com").roles(new ArrayList<>()).build();
        }
        if(StringUtils.pathEquals(dontPopulate, "password")){
            return CakeAppUser.builder().id(1L).firstName("John").lastName("Brant").username("admin")
                    .email("john.brant@waracle.com").roles(new ArrayList<>()).build();
        }
        return CakeAppUser.builder().id(1L).firstName("John").lastName("Brant").username("admin").password("1234")
                .email("john.brant@waracle.com").roles(Arrays.asList(Role.builder().name("role").build())).build();
    }

    public static Cake getCake(String dontPopulate) {
        if(StringUtils.pathEquals(dontPopulate, "cakeName")){
            return Cake.builder().id(1L).clientName("Brant").build();
        }
        if(StringUtils.pathEquals(dontPopulate, "clientName")){
            return Cake.builder().id(1L).name("Cake1").build();
        }
        return Cake.builder().id(1L).name("Cake1").clientName("Brant").build();
    }

    public static Role getRoleObj(String roleName) {
        return Role.builder().id(1L).name(roleName).build();
    }
}
