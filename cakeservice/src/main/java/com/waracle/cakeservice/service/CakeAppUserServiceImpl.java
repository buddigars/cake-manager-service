package com.waracle.cakeservice.service;

import com.waracle.cakeservice.exceptions.CakeAlreadyExistsException;
import com.waracle.cakeservice.exceptions.ResourceNotFoundException;
import com.waracle.cakeservice.model.CakeAppUser;
import com.waracle.cakeservice.model.Role;
import com.waracle.cakeservice.repository.CakeUserRepo;
import com.waracle.cakeservice.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CakeAppUserServiceImpl implements CakeAppUserService, UserDetailsService {
    private final CakeUserRepo cakeUserRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    @Override
    public CakeAppUser saveUser(CakeAppUser cakeAppUser) {
        log.info("Saving new cake user {} to DB", cakeAppUser.getFirstName() + " " + cakeAppUser.getLastName());
        CakeAppUser user = cakeUserRepo.findByUsername(cakeAppUser.getUsername());
        if (Objects.nonNull(user)) {
            log.info("User already exists {}", cakeAppUser.getUsername());
            throw new CakeAlreadyExistsException("User {} already exists" + cakeAppUser.getUsername());
        }
        cakeAppUser.setPassword(passwordEncoder.encode(cakeAppUser.getPassword()));
        return cakeUserRepo.save(cakeAppUser);
    }

    @Override
    public void assignRoleToUser(String username, String roleName) {
        log.info("assigning role {} to user {} ...", roleName, username);
        CakeAppUser cakeAppUser = cakeUserRepo.findByUsername(username);
        if (Objects.isNull(cakeAppUser)) {
            log.info("username is not found in DB ={} ", username);
            throw new ResourceNotFoundException(username + " username in DB ");
        }

        Role role = roleRepo.findByName(roleName);
        if (Objects.isNull(role)) {
            log.info("Role not found in DB ={} ", roleName);
            throw new ResourceNotFoundException(roleName + " role in DB ");
        }
        List<Role> rolesList = (List<Role>) cakeAppUser.getRoles();
        LinkedList<Role> list = new LinkedList<>(rolesList);
        list.add(role);
        cakeAppUser.setRoles(list);
    }

    @Override
    public List<CakeAppUser> getUsers() {
        return cakeUserRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CakeAppUser user = cakeUserRepo.findByUsername(username);
        if (user == null) {
            log.info("User not found in DB ={} ", username);
            throw new UsernameNotFoundException("User not found in DB ");

        }
        log.info("User found in DB ={} ", username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}