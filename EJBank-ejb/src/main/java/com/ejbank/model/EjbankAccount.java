package com.ejbank.model;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ejbank_account")
public class EjbankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private EjbankCustomer ejbankCustomer;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType. EAGER)
    @JoinColumn(name = "account_type_id", nullable = false)
    private EjbankAccountType accountType;

    @Column(name = "balance", precision = 10, scale = 0)
    private BigDecimal balance;

    public EjbankAccount() {
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public EjbankAccountType getAccountType() {
        return accountType;
    }

    public EjbankUser getCustomer() {
        return this.ejbankCustomer;
    }
}
