package com.jor.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;

    private String shopName;

    @ManyToOne
    @JoinColumn(name = "location_id")
    @JsonBackReference
    private Locations location;

    private String contact;


    @OneToMany(mappedBy = "shop", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference
    private List<Product> products;


    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "bucket_id")
    @JsonManagedReference
    private Bucket bucket;


    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<UserProductPrices> userProductPrices;
}
