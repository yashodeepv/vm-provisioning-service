package com.yashodeep.vmprovisioningservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VmConfigRepo extends JpaRepository<VmConfig, Long> {
    Optional<List<VmConfig>> findByOs(String os);
}
