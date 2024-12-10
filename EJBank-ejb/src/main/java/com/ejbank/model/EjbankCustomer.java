package com.ejbank.model;


import javax.persistence.*;

@Entity
@Table(name = "ejbank_customer")
public class EjbankCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "advisor_id", nullable = false)
    private EjbankAdvisor ejbankAdvisor;

    public EjbankCustomer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EjbankAdvisor getEjbankAdvisor() {
        return ejbankAdvisor;
    }

    public void setEjbankAdvisor(EjbankAdvisor ejbankAdvisor) {
        this.ejbankAdvisor = ejbankAdvisor;
    }

    @Override
    public String toString() {
        return "EjbankCustomer{" +
                "id=" + id +
                ", ejbankAdvisor=" + ejbankAdvisor +
                '}';
    }
}
