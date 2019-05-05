package com.yashodeep.vmprovisioningservice.services;


import com.yashodeep.vmprovisioningservice.exception.ProvisionedVmNotFoundException;
import com.yashodeep.vmprovisioningservice.exception.UserNotFoundException;
import com.yashodeep.vmprovisioningservice.exception.VMConfigMissingException;
import com.yashodeep.vmprovisioningservice.jpa.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VmConfigService {

    private final VmConfigRepo vmConfigRepo;
    private final ProvisionedVmRepo provisionedVmRepo;
    private final UserRepo userRepo;

    public VmConfigService(VmConfigRepo vmConfigRepo, ProvisionedVmRepo provisionedVmRepo, UserRepo userRepo) {
        this.vmConfigRepo = vmConfigRepo;
        this.provisionedVmRepo = provisionedVmRepo;
        this.userRepo = userRepo;
    }

    public ProvisionedVm provisionVm(VmConfig vmConfig, String username) {
        if(vmConfig == null) {
            throw new VMConfigMissingException();
        }
        VmConfig config = vmConfigRepo.saveAndFlush(vmConfig);
        UserDetails user = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException());
        ProvisionedVm provisionedVm = provisionedVmRepo.saveAndFlush(ProvisionedVm.builder()
                .requestedUserDetails(user)
                .vmConfig(config)
                .allocatedSpace(Math.random()*1000+"gb")
                .build());
        return stripProtectedData(provisionedVm);
    }

    public ProvisionedVm vmconfigUpgrade(Long provisionedVmId, VmConfig newConfig) {
        ProvisionedVm provisionedVm = provisionedVmRepo.findById(provisionedVmId)
                .orElseThrow(ProvisionedVmNotFoundException::new);
        Long vmConfigId = provisionedVm.getVmConfig().getId();
        newConfig.setId(vmConfigId);
        vmConfigRepo.saveAndFlush(newConfig);
        return stripProtectedData(provisionedVmRepo.findById(provisionedVmId)
                .orElseThrow(ProvisionedVmNotFoundException::new));
    }

    public List<ProvisionedVm> findAllUserVms(String username) {
        UserDetails user = userRepo.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        return provisionedVmRepo.findByRequestedUserDetails(user).stream()
                .map(this::stripProtectedData)
                .collect(Collectors.toList());
    }

    public ProvisionedVm stripProtectedData(ProvisionedVm provisionedVm) {
        provisionedVm.getRequestedUserDetails().setPassword("");
        return provisionedVm;
    }

}
