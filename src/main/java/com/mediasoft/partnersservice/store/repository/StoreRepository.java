package com.mediasoft.partnersservice.store.repository;

import com.mediasoft.partnersservice.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    boolean existsByName(String storeName);

    @Query("SELECT s.name FROM Store s WHERE s.partner.id = :partnerId")
    List<String> getStoreNamesByPartnerId(Long partnerId);

    boolean existsByIdAndPartnerId(Long storeId, Long partnerId);
}