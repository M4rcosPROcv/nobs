package com.example.nobs.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity // maps a java class to mysql
@Data
@Table(name="product")
public class Product {

    @Id // all tables in mysql need a primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto generates id
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "Name is required")
    @Column(name = "name")
    private String name;

    @Size(min = 20, message = "Description must be at least 20 characters")
    @Column(name = "description")
    private String description;

    @PositiveOrZero(message = "Price cannot be negative")
    @Column(name = "price")
    private Double price;

}
