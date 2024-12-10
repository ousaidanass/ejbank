package com.ejbank.model;


import javax.persistence.*;

@Entity
@Table(name = "ejbank_customer")
@DiscriminatorValue("customer")
public class EjbankCustomer extends EjbankUser {

    @ManyToOne
    @JoinColumn(name = "advisor_id", nullable = false)
    private EjbankAdvisor ejbankAdvisor;

    public EjbankCustomer() {
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
                "ejbankAdvisor=" + ejbankAdvisor +
                '}';
    }
}
