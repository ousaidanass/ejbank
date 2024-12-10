package com.ejbank.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "ejbank_account_type")
public class EjbankAccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 58)
    private String name;

    @Column(name = "rate", precision = 10, scale = 0)
    private BigDecimal rate;

    @Column(name = "overdraft")
    private Integer overdraft;

    public EjbankAccountType() {
    }

}
