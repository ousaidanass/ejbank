package com.ejbank.model;


import javax.persistence.*;

@Entity
@Table(name = "ejbank_user")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
