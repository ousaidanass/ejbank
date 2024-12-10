package com.ejbank.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "ejbank_account")
public class EjbankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private EjbankCustomer ejbankCustomer;

    @Column(name = "balance", precision = 10, scale = 0)
    private BigDecimal balance;


}
