package com.yashodeep.vmprovisioningservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserDetails, Long> {
    Optional<UserDetails> findByEmailAddressAndMobileNumber(String emailAddress, String mobileNumber);
    Optional<UserDetails> findByUsername(String username);
}
