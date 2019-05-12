package com.yashodeep.vmprovisioningservice.web;

import com.yashodeep.vmprovisioningservice.jpa.ProvisionedVm;
import com.yashodeep.vmprovisioningservice.jpa.VmConfig;
import com.yashodeep.vmprovisioningservice.services.VmConfigService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/vm-provisioning-service/provision-vm")
public class VmConfigContoller {

    private final VmConfigService vmConfigService;


    public VmConfigContoller(VmConfigService vmConfigService) {
        this.vmConfigService = vmConfigService;
    }

    @PostMapping
    public ProvisionedVm provisionVm(@RequestBody VmConfig vmConfig, @AuthenticationPrincipal UserDetails userDetails) {
        return vmConfigService.provisionVm(vmConfig, userDetails.getUsername());
    }

    @GetMapping
    public List<ProvisionedVm> getAllVms(@AuthenticationPrincipal UserDetails userDetails) {
        return vmConfigService.findAllUserVms(userDetails.getUsername());
    }

    @PutMapping
    public ProvisionedVm updateVm(@RequestBody ProvisionedVm provisionedVm) {
        return vmConfigService.vmconfigUpgrade(provisionedVm.getId(), provisionedVm.getVmConfig());
    }

}
