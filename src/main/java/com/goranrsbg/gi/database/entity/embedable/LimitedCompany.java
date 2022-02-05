package com.goranrsbg.gi.database.entity.embedable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Goran Cvijanovic
 */
@Embeddable
public class LimitedCompany {

    @Column(name = "ltd_name", length = 171, nullable = true)
    private String name;

    @Column(name = "ltd_number", length = 171, nullable = true)
    private String number;

    public LimitedCompany() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.name.hashCode();
        hash = 23 * hash + this.number.hashCode();
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
        final LimitedCompany other = (LimitedCompany) obj;
        return this.name.equals(other.getName()) && this.number.equals(other.getNumber());
    }

}
