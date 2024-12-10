package com.ejbank.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ejbank_transaction")
public class EjbankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private EjbankCustomer ejbankCustomer;

    @Column(name = "author")
    private Integer author;

    @Column(name = "amount", precision = 10, scale = 0)
    private BigDecimal amount;

    @Column(name = "comment", length = 255)
    private String comment;

    @Column(name = "applied")
    private Boolean applied;

    @Column(name = "date")
    private LocalDateTime date;




}
