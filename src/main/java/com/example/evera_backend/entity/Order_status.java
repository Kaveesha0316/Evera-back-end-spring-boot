package com.example.evera_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_status")
public class Order_status {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",length = 10,nullable = false)
    private String name;

    public Order_status() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
