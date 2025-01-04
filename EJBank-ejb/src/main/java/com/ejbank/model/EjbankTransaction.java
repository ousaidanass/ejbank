package com.ejbank.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "ejbank_transaction")
public class EjbankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id_to", nullable = false)
    private EjbankAccount accountTo;

    @ManyToOne
    @JoinColumn(name = "account_id_from", nullable = false)
    private EjbankAccount accountFrom;

    @ManyToOne
    @JoinColumn(name = "author")
    private EjbankUser author;

    @Column(name = "amount", precision = 10, scale = 0)
    private BigDecimal amount;

    @Column(name = "comment", length = 255)
    private String comment;

    @Column(name = "applied")
    private Boolean applied;

    @Column(name = "date")
    private Date date;

    public EjbankTransaction() {
    }

    public Long getId() {
        return id;
    }

    public EjbankAccount getAccountTo() {
        return accountTo;
    }

    public EjbankAccount getAccountFrom() {
        return accountFrom;
    }

    public EjbankUser getAuthor() {
        return author;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public Boolean getApplied() {
        return applied;
    }

    public Date getDate() {
        return date;
    }

    public void setApplied(Boolean applied) {
        this.applied = applied;
    }
}
