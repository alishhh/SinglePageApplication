package com.example.single_page_application.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "details")
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Integer count;
    private Double quantityCost;
    private Long parentId;

    public void setPrice(Double price) {
        this.price = price;
    }
}
