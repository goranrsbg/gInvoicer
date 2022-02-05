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
public class ContractClientPriceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private long id;

    @Version
    private int version;

    @ManyToOne
    @JoinColumn(name = "fk_contractClient")
    private ContractCompanyClient contractClient;

    @ManyToOne
    @JoinColumn(name = "fk_title")
    private Title title;

    @Column(name = "price_per_hour", nullable = false)
    private double pricePerHour;

    public ContractClientPriceDetails() {
    }

    public long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public ContractCompanyClient getContractClient() {
        return contractClient;
    }

    public void setContractClient(ContractCompanyClient contractClient) {
        this.contractClient = contractClient;
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
        final ContractClientPriceDetails other = (ContractClientPriceDetails) obj;
        return id == other.getId()
                && contractClient.equals(other.getContractClient())
                && title.equals(other.getTitle())
                && pricePerHour == other.getPricePerHour();
    }

    @Override
    public String toString() {
        return String.format("[%d, %s |%s| (.2f)", id, contractClient, title, pricePerHour);
    }

}
