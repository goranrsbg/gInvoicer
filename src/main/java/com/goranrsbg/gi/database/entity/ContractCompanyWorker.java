package com.goranrsbg.gi.database.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 *
 * @author goranrsbg
 */
@Entity
@NamedQuery(name = "ContractWorker.all", query = "SELECT c FROM ContractCompanyWorker c")
public class ContractCompanyWorker {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private long id;

    @Version
    private int version;

    @ManyToOne
    @JoinColumn(name = "fk_company")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "fk_worker")
    private Worker worker;

    @Column(name = "start_date", nullable = false, unique = true)
    private LocalDate startDate;

    @OneToMany(mappedBy = "contractWorker")
    private final Set<ContractWorkerPriceDetails> priceDetails;

    public ContractCompanyWorker() {
        priceDetails = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Set<ContractWorkerPriceDetails> getPriceDetails() {
        return priceDetails;
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
        final ContractCompanyWorker other = (ContractCompanyWorker) obj;
        return id == other.getId()
                && company.equals(other.getCompany())
                && worker.equals(other.getWorker())
                && startDate.equals(other.getStartDate());
    }

    @Override
    public String toString() {
        return String.format("[%d, between %s and %s on %4$td/%4$tm/%4$tY", id, company, worker, startDate);
    }

}
