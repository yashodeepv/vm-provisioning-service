package com.yashodeep.vmprovisioningservice;

import com.yashodeep.vmprovisioningservice.jpa.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Arrays;

@SpringBootApplication
public class VmProvisioningServiceApplication {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT");
    }

    @Bean
    CommandLineRunner demo(UserRepo userRepo, ProvisionedVmRepo provisionedVmRepo, VmConfigRepo vmConfigRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            // test user for Integration test
            // Clear
            provisionedVmRepo.deleteAll();
            userRepo.deleteAll();
            vmConfigRepo.deleteAll();

            userRepo.saveAndFlush(
                    UserDetails.builder()
                            .name("Yashodeep")
                            .emailAddress("yv@gmail.com")
                            .mobileNumber("12213123")
                            .active(true)
                            .roles(Arrays.asList("USER"))
                            .username("yashodeepv")
                            .password(passwordEncoder.encode("yash"))
                            .build());
            userRepo.findAll().stream().forEach(System.out::println);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VmProvisioningServiceApplication.class, args);
    }

}