package com.example.evera_backend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "brand")
public class brand implements Serializable{


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",length = 45,nullable = false)
    private String name;

    public brand() {
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
