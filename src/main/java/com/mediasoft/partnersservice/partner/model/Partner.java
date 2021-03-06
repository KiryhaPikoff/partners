package com.mediasoft.partnersservice.partner.model;

import com.mediasoft.partnersservice.store.model.Store;
import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "partner")
    private List<Store> stores;

    @Formula("(SELECT COUNT(*) FROM Store s WHERE s.partner_id = id)")
    private Integer storesCount;
}