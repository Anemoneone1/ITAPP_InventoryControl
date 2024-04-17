package com.itapp.inventorycontrol.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "storage_space_item")
public class StorageSpaceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_space_id", nullable = false)
    private StorageSpace storageSpace;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @Column(name = "batch_number", nullable = false)
    private String batchNumber;
}
