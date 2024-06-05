package com.itapp.inventorycontrol.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "company_agreements")
public class ComplianceAgreements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compliance_id", nullable = false)
    private Compliance compliance;

    @Column(name = "lease_expired", nullable = false)
    private boolean leaseExpired = false;

    @Column(name = "lease_start", nullable = false)
    private Date leaseStart;

    @Column(name = "lease_end", nullable = false)
    private Date leaseEnd;
}
