package com.example.mynewfishshop.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="items")
@Data
public class ItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name="title")
    String title;

    @Column(name="short_disc")
    String disc;

    @Column(name="full_disc")
    String full_disc;

    @Column(name="price")
    String price;

    @Column(name="price_discount")
    String price_discount;

    @Column(name="photo_url")
    String url;

    @Column(name="category")
    String category;
}
