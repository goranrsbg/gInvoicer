package com.goranrsbg.gi.database.entity;

import com.goranrsbg.gi.database.entity.embedable.Address;
import com.goranrsbg.gi.database.entity.embedable.Dbs;
import com.goranrsbg.gi.database.entity.embedable.LimitedCompany;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

/**
 *
 * @author Goran Cvijanovic
 */
@Entity
@SequenceGenerator(name = "id_generator", sequenceName = "id_seqence", allocationSize = 17)
@NamedQuery(name = "Worker.all", query = "SELECT w FROM Worker w ORDER BY first_name")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private long id;

    @Version
    private int version;

    @Embedded
    private PersonName personName;

    @Embedded
    private Address address;

    @Embedded
    private LimitedCompany limitedCompany;

    @Embedded
    private Dbs dbs;

    @Column(name = "email", nullable = false, length = 171)
    private String email;

    @Column(name = "utr", nullable = false, length = 31)
    private String utr;

    @Column(name = "nino", nullable = false, length = 31)
    private String nino;

    @Column(name = "phone", nullable = false, length = 31)
    private String phone;

    @Column(name = "hourly_wage", nullable = false)
    private double hourlyWage;

    @ManyToOne
    @JoinColumn(name = "fk_title")
    private Title title;

    @ManyToMany(mappedBy = "employed")
    private final Set<CareHome> careHomes;

    @OneToMany(mappedBy = "worker")
    private final Set<WorkerTraining> trainings;

    @ManyToMany(mappedBy = "workers")
    private final Set<Company> companies;

    public Worker() {
        companies = new HashSet<>();
        trainings = new HashSet<>();
        careHomes = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public PersonName getPersonName() {
        return personName;
    }

    public void setPersonName(PersonName personName) {
        this.personName = personName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LimitedCompany getLimitedCompany() {
        return limitedCompany;
    }

    public void setLimitedCompany(LimitedCompany limitedCompany) {
        this.limitedCompany = limitedCompany;
    }

    public Dbs getDbs() {
        return dbs;
    }

    public void setDbs(Dbs dbs) {
        this.dbs = dbs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUtr() {
        return utr;
    }

    public void setUtr(String utr) {
        this.utr = utr;
    }

    public String getNino() {
        return nino;
    }

    public void setNino(String nino) {
        this.nino = nino;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public Set<CareHome> getCareHomes() {
        return careHomes;
    }

    public Set<WorkerTraining> getTrainings() {
        return trainings;
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
        final Worker other = (Worker) obj;
        return id == other.getId()
                && personName.equals(other.getPersonName())
                && address.equals(other.getAddress())
                && hourlyWage == other.getHourlyWage()
                && dbs.equals(other.getDbs())
                && limitedCompany.equals(other.getLimitedCompany())
                && nino.equals(other.getNino())
                && phone.equals(other.getPhone())
                && title.equals(other.getTitle())
                && utr.equals(other.getUtr());
    }

}
