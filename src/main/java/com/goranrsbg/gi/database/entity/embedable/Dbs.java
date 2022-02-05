package com.goranrsbg.gi.database.entity.embedable;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Goran Cvijanovic
 */
@Embeddable
public class Dbs {

    @Column(name = "dbs_number", nullable = true, length = 31)
    private String number;

    @Column(name = "dbs_date", nullable = true)
    private LocalDate date;

    public Dbs() {
    }

    public String getNumber() {
        return number;
    }

    public Dbs setNumber(String number) {
        this.number = number;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Dbs setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.number);
        hash = 23 * hash + Objects.hashCode(this.date);
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
        final Dbs other = (Dbs) obj;
        return number.equals(other.getNumber()) && date.equals(other.getDate());
    }

}
