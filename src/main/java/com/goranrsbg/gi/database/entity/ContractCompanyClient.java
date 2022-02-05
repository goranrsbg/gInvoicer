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
@NamedQuery(name = "ContractCompany.all", query = "SELECT c FROM ContractCompanyClient c")
public class ContractCompanyClient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private long id;

    @Version
    private int version;

    @ManyToOne
    @JoinColumn(name = "fk_company")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "fk_client")
    private Client client;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "last_date", nullable = false)
    private LocalDate lastDate;

    @OneToMany(mappedBy = "contractClient")
    private final Set<ContractClientPriceDetails> priceDetails;

    public ContractCompanyClient() {
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    public Set<ContractClientPriceDetails> getPriceDetails() {
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
        final ContractCompanyClient other = (ContractCompanyClient) obj;
        return id == other.getId()
                && company.equals(other.getCompany())
                && client.equals(other.getClient())
                && startDate.equals(other.getStartDate())
                && lastDate.equals(other.getLastDate());
    }

    @Override
    public String toString() {
        return String.format("[%d, between %s and %s on %4$td/%4$tm/%4$tY to %5$td/%5$tm/%5$tY", id, company, client, startDate, lastDate);
    }

}
