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
public class ContractWorkerPriceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private long id;

    @Version
    private int version;

    @ManyToOne
    @JoinColumn(name = "fk_contractWorker")
    private ContractCompanyWorker contractWorker;

    @ManyToOne
    @JoinColumn(name = "fk_title")
    private Title title;

    @Column(name = "price_per_hour", nullable = false)
    private double pricePerHour;

    public ContractWorkerPriceDetails() {
    }

    public long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public ContractCompanyWorker getContractWorker() {
        return contractWorker;
    }

    public void setContractWorker(ContractCompanyWorker contractWorker) {
        this.contractWorker = contractWorker;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
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
        final ContractWorkerPriceDetails other = (ContractWorkerPriceDetails) obj;
        return id == other.getId()
                && contractWorker.equals(other.getContractWorker())
                && title.equals(other.getTitle())
                && pricePerHour == other.getPricePerHour();
    }

    @Override
    public String toString() {
        return String.format("[%d, %s |%s| (.2f)", id, contractWorker, title, pricePerHour);
    }
}
