package com.goranrsbg.gi.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

/**
 *
 * @author goranrsbg
 */
@Entity
public class InvoiceNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private long id;

    @Version
    private int version;

    @ManyToOne
    @JoinColumn(name = "fk_company")
    private Company company;

    @Column(name = "number_worker", nullable = false)
    private long numberWorker;

    @Column(name = "number_client", nullable = false)
    private long numberClient;

    public InvoiceNumber() {
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

    public long getNumberWorker() {
        return numberWorker;
    }

    public void setNumberWorker(long numberWorker) {
        this.numberWorker = numberWorker;
    }

    public long getNumberClient() {
        return numberClient;
    }

    public void setNumberClient(long numberClient) {
        this.numberClient = numberClient;
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
        final InvoiceNumber other = (InvoiceNumber) obj;
        return id == other.getId()
                && company.equals(other.getCompany())
                && numberClient == other.getNumberClient()
                && numberWorker == other.getNumberWorker();
    }

    @Override
    public String toString() {
        return String.format("[%d, %s [Client: %d] [Worker: %d]", id, company, numberClient, numberWorker);
    }

}
