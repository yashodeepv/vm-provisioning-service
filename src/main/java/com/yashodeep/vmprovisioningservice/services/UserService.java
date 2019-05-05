package com.yashodeep.vmprovisioningservice.services;

import com.yashodeep.vmprovisioningservice.jpa.UserDetails;
import com.yashodeep.vmprovisioningservice.jpa.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails userSignup(UserDetails userDetails) {
        UserDetails byUsername = userRepo.findByUsername(userDetails.getUsername()).orElse(userDetails);
        String password = userDetails.getPassword();
        userDetails.setId(byUsername.getId());
        userDetails.setPassword(passwordEncoder.encode(password));
        return userRepo.saveAndFlush(userDetails);
    }

}
