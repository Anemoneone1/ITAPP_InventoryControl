package com.itapp.inventorycontrol.repository;

import com.itapp.inventorycontrol.entity.BoxItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxItemRepository extends JpaRepository<BoxItem, Long> {
    List<BoxItem> findAllByItemCompanyId(Long id);
}
