package com.yashodeep.vmprovisioningservice;

import com.yashodeep.vmprovisioningservice.jpa.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class VmProvisioningServiceApplication {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    CommandLineRunner demo(UserRepo userRepo, ProvisionedVmRepo provisionedVmRepo, VmConfigRepo vmConfigRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            // Clear
            provisionedVmRepo.deleteAll();
            userRepo.deleteAll();
            vmConfigRepo.deleteAll();

            userRepo.saveAndFlush(
                    UserDetails.builder()
                            .name("Yashodeep")
                            .emailAddress("yashodeepv@gmail.com")
                            .mobileNumber("8108514851")
                            .active(true)
                            .roles(Arrays.asList("USER"))
                            .username("yashodeepv")
                            .password(passwordEncoder.encode("yash"))
                            .build());
            userRepo.saveAndFlush(
                    UserDetails.builder()
                            .name("Rashmi")
                            .emailAddress("rashmiv@gmail.com")
                            .mobileNumber("8169267616")
                            .active(true)
                            .roles(Arrays.asList("ADMIN"))
                            .username("rashmiv")
                            .password(passwordEncoder.encode("rash"))
                            .build());
            vmConfigRepo.saveAndFlush(
                    VmConfig.builder()
                    .os("MacOS")
                    .cpuCores(4)
                    .ram("16GB")
                    .hdd("250GB")
                    .build()
            );
            vmConfigRepo.saveAndFlush(
                    VmConfig.builder()
                            .os("Win")
                            .cpuCores(4)
                            .ram("16GB")
                            .hdd("250GB")
                            .build()
            );
            UserDetails userDetails = userRepo.findByEmailAddressAndMobileNumber("yashodeepv@gmail.com", "8108514851")
                 .orElseThrow(() -> new RuntimeException("UserDetails not found"));
            provisionedVmRepo.saveAndFlush(
                    ProvisionedVm.builder()
                    .requestedUserDetails(userDetails)
                    .allocatedSpace("100GB")
                    .vmConfig(vmConfigRepo.findByOs("Win").orElseThrow(() -> new RuntimeException("VM not found")).get(0))
                    .build()
            );

        };
    }


    public static void main(String[] args) {
        SpringApplication.run(VmProvisioningServiceApplication.class, args);
    }

}