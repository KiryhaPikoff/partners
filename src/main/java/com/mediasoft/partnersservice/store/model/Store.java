package com.mediasoft.partnersservice.store.model;

import com.mediasoft.partnersservice.partner.model.Partner;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer employeesNumber;

    @ManyToOne(optional = false)
    private Partner partner;
}