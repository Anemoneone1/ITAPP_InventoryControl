package com.itapp.inventorycontrol.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "box")
public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", nullable = false)
    private Storage storage;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "box")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<BoxItem> items;
}
