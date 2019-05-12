package com.yashodeep.vmprovisioningservice.services;

import com.yashodeep.vmprovisioningservice.exception.UserAlreadyRegisteredException;
import com.yashodeep.vmprovisioningservice.jpa.UserDetails;
import com.yashodeep.vmprovisioningservice.jpa.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails userSignup(UserDetails userDetails) {
        Optional<UserDetails> user = userRepo.findByUsername(userDetails.getUsername());
        if(user.isPresent()) {
            throw new UserAlreadyRegisteredException();
        }
        UserDetails byUsername = user.orElse(userDetails);
        String password = userDetails.getPassword();
        userDetails.setId(byUsername.getId());
        userDetails.setPassword(passwordEncoder.encode(password));
        return userRepo.saveAndFlush(userDetails);
    }

}
