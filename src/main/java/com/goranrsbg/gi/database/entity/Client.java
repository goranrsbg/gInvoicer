package com.goranrsbg.gi.database.entity;

import com.goranrsbg.gi.database.entity.embedable.Address;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 * The client. Contract(Table), Contract Pricing
 *
 * @author Goran Cvijanovic
 */
@Entity
@NamedQuery(name = "Client.all", query = "SELECT c FROM Client c")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private long id;

    @Version
    private int version;

    @Column(name = "name", length = 171, nullable = false, unique = true)
    private String name;

    @Embedded
    private Address address;

    @Column(name = "company_number", length = 171, nullable = false)
    private String companyNumber;

    @OneToMany(mappedBy = "client")
    private final Set<CareHome> careHomes;

    @ManyToMany(mappedBy = "clients")
    private final Set<Company> companies;

    public Client() {
        careHomes = new HashSet<>();
        companies = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public Set<CareHome> getCareHomes() {
        return careHomes;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        return id == other.getId()
                && name.equals(other.getName())
                && companyNumber.equals(other.getCompanyNumber())
                && address.equals(other.getAddress());
    }

    @Override
    public String toString() {
        return String.format("[%d -> %s (%s) no:%s]", id, name, address, companyNumber);
    }

}
