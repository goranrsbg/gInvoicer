package com.goranrsbg.gi.database.entity;

import com.goranrsbg.gi.database.entity.embedable.Address;
import com.goranrsbg.gi.database.entity.embedable.PersonName;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

/**
 * Company entity class.
 *
 * @author Goran Cvijanovic
 */
@Entity
@NamedQuery(name = "Company.all", query = "SELECT c FROM Company c")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private long id;

    @Version
    private int version;

    @Column(name = "company_name", length = 171, nullable = false, unique = true)
    private String name;

    @Embedded
    private PersonName director;

    @Embedded
    private Address address;

    @Column(name = "company_number", length = 171, nullable = false, unique = true)
    private String companyNumber;

    @Column(name = "emails", length = 171, nullable = false)
    private String emails;

    @Column(name = "website", length = 171, nullable = false)
    private String website;

    @Column(name = "phone", length = 171, nullable = false)
    private String phone;

    @Column(name = "logo", length = 171, nullable = false)
    private String logo;

    @ManyToMany
    @JoinTable(name = "company_client",
            joinColumns = {
                @JoinColumn(name = "fk_company")},
            inverseJoinColumns = {
                @JoinColumn(name = "fk_client")})
    private final Set<Client> clients;

    @ManyToMany
    @JoinTable(name = "company_worker",
            joinColumns = {
                @JoinColumn(name = "fk_company")},
            inverseJoinColumns = {
                @JoinColumn(name = "fk_worker")})
    private final Set<Worker> workers;

    @ManyToMany
    @JoinTable(name = "company_email",
            joinColumns = {
                @JoinColumn(name = "fk_company")},
            inverseJoinColumns = {
                @JoinColumn(name = "fk_email")})
    private final Set<Email> sendingEmails;

    public Company() {
        clients = new HashSet<>();
        workers = new HashSet<>();
        sendingEmails = new HashSet<>();
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

    public PersonName getDirector() {
        return director;
    }

    public void setDirector(PersonName director) {
        this.director = director;
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

    public String getWebsite() {
        return website;
    }

    public Company setWebsite(String website) {
        this.website = website;
        return this;
    }

    public String getTelNumber() {
        return phone;
    }

    public void setTelNumber(String telNumber) {
        this.phone = telNumber;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String imgPath) {
        this.logo = imgPath;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public Set<Worker> getWorkers() {
        return workers;
    }

    public Set<Email> getSendingEmails() {
        return sendingEmails;
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
        final Company other = (Company) obj;
        if (this.id != other.getId()) {
            return false;
        }
        return id == other.getId()
                && name.equals(other.getName())
                && companyNumber.equals(other.getCompanyNumber())
                && website.equals(other.getWebsite())
                && phone.equals(other.getTelNumber())
                && logo.equals(other.getLogo())
                && director.equals(other.getDirector())
                && address.equals(other.getAddress());
    }

    @Override
    public String toString() {
        return String.format("[%d -> %s (%s) /%s/ no:%s (%s ph:%s logo:%s)]", id, name, director, address, companyNumber, website, phone, logo);
    }

}
