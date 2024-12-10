package com.ejbank.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ejbank_advisor")
@DiscriminatorValue("advisor")
public class EjbankAdvisor extends EjbankUser{
    @OneToMany(mappedBy = "ejbankAdvisor")
    private Set<EjbankCustomer> ejbankCustomers;

    public EjbankAdvisor() {
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
                "ejbankCustomers=" + ejbankCustomers +
                '}';
    }
}
