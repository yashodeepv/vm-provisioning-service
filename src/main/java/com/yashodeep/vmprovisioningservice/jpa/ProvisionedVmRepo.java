package com.yashodeep.vmprovisioningservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvisionedVmRepo extends JpaRepository<ProvisionedVm, Long> {
    List<ProvisionedVm> findByRequestedUserDetails(UserDetails user);
}
