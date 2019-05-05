package com.yashodeep.vmprovisioningservice.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProvisionedVm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String allocatedSpace;

    @OneToOne
    @JoinColumn(name = "vm_id")
    private VmConfig vmConfig;

    @OneToOne
    @JoinColumn(name="requested_user_id")
    private UserDetails requestedUserDetails;
}
