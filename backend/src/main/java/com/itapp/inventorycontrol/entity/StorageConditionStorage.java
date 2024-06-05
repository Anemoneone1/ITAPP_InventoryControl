package com.itapp.inventorycontrol.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "storage_condition_storage")
public class StorageConditionStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_condition_id", nullable = false)
    private StorageCondition storageCondition;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", nullable = false)
    private Storage storage;
}
