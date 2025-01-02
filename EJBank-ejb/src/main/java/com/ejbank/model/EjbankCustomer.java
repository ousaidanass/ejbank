package com.ejbank.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ejbank_customer")
@DiscriminatorValue("customer")
public class EjbankCustomer extends EjbankUser {

    @ManyToOne
    @JoinColumn(name = "advisor_id", nullable = false)
    private EjbankAdvisor ejbankAdvisor;

    @OneToMany(mappedBy = "ejbankCustomer")
    private Set<EjbankAccount> accounts;

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

    public Set<EjbankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<EjbankAccount> accounts) {
        this.accounts = accounts;
    }
}
