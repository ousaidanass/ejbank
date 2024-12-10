package com.ejbank.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ejbank_advisor")
public class EjbankAdvisor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "ejbankAdvisor")
    private Set<EjbankCustomer> ejbankCustomers;

    public EjbankAdvisor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<EjbankCustomer> getEjbankCustomers() {
        return ejbankCustomers;
    }

    public void setEjbankCustomers(Set<EjbankCustomer> ejbankCustomers) {
        this.ejbankCustomers = ejbankCustomers;
    }

    @Override
    public String toString() {
        return "EjbankAdvisor{" +
                "id=" + id +
                ", ejbankCustomers=" + ejbankCustomers +
                '}';
    }
}
