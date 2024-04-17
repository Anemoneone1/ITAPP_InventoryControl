package com.itapp.inventorycontrol.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "storage_space")
public class StorageSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "square_space", nullable = false)
    private Double squareSpace;

    @Column(name = "free_space", nullable = false)
    private Double freeSpace;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "stationary", nullable = false)
    private boolean stationary;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_storage_space")
    private StorageSpace parentStorageSpace;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "storageSpace")
    private Set<StorageSpaceItem> items;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "storageSpace")
    private Set<StorageConditionsStorageSpace> storageConditions;
}