package com.ejbank.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ejbank_user")
public class EjbankUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "login", nullable = false, length = 8)
    private String login;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "firstname", nullable = false, length = 50)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 50)
    private String lastname;

    @Column(name = "type", nullable = false, length = 50)
    private String type;


    @OneToMany(mappedBy = "ejbankUser")
    private Set<EjbankCustomer> ejbankCustomer;

    @OneToMany(mappedBy = "ejbankUser")
    private Set<EjbankAdvisor> ejbankAdvisor;

    public EjbankUser() {
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getType() {
        return type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<EjbankCustomer> getEjbankCustomer() {
        return ejbankCustomer;
    }

    public void setEjbankCustomer(Set<EjbankCustomer> ejbankCustomer) {
        this.ejbankCustomer = ejbankCustomer;
    }

    public Set<EjbankAdvisor> getEjbankAdvisor() {
        return ejbankAdvisor;
    }

    public void setEjbankAdvisor(Set<EjbankAdvisor> ejbankAdvisor) {
        this.ejbankAdvisor = ejbankAdvisor;
    }

    @Override
    public String toString() {
        return "EjbankUser{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", type='" + type + '\'' +
                ", ejbankCustomer=" + ejbankCustomer +
                ", ejbankAdvisor=" + ejbankAdvisor +
                '}';
    }
}
