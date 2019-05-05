package com.yashodeep.vmprovisioningservice.services;

import com.yashodeep.vmprovisioningservice.jpa.UserDetails;
import com.yashodeep.vmprovisioningservice.jpa.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDetails userSignup(UserDetails userDetails) {
        return userRepo.saveAndFlush(userDetails);
    }

}
