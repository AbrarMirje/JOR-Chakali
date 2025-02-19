package com.jor.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jor.entity.Shop;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.IdGeneratorType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bucketId;

    @OneToOne(mappedBy = "bucket", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private Shop shop;

    private Integer quantity;
}
