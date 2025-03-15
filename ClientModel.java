package com.example.mynewfishshop.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="client")
public class ClientModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name="name")
    String name;

    @Column(name="email")
    String email;

    @Column(name="wanted_item")
    String item;

    @Column(name="contact")
    String contact;

    @Column(name="message")
    String message;

    @Column(name="actual")
    boolean actual;

}
