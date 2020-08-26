package com.mediasoft.partnersservice.partner.repository;

import com.mediasoft.partnersservice.partner.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

    boolean existsByName(String partnerName);

    @Query("SELECT p.name FROM Partner p")
    List<String> getPartnerNames();
}