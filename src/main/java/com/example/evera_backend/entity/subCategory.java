package com.example.evera_backend.entity;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sub_category")
public class subCategory implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",length = 45,nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "main_category_id")
    private mainCategory mainCategory;

    public subCategory() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the mainCategory
     */
    public mainCategory getMainCategory() {
        return mainCategory;
    }

    /**
     * @param mainCategory the mainCategory to set
     */
    public void setMainCategory(mainCategory mainCategory) {
        this.mainCategory = mainCategory;
    }

}