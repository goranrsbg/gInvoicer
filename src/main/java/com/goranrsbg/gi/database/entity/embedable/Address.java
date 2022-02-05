package com.goranrsbg.gi.database.entity.embedable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Goran Cvijanovic
 */
@Embeddable
public class Address {

    @Column(name = "street", length = 171, nullable = false)
    private String street;

    @Column(name = "town", length = 171, nullable = false)
    private String town;

    @Column(name = "zipcode", length = 19, nullable = false)
    private String zipcode;

    public Address() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode.toUpperCase();
    }

    @Override
    public String toString() {
        return String.format("%s, %s %s", street, town, zipcode);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + street.hashCode();
        hash = 23 * hash + town.hashCode();
        hash = 23 * hash + zipcode.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        return street.equals(other.getStreet())
                && town.equals(other.getTown())
                && zipcode.equals(other.getZipcode());
    }

}
