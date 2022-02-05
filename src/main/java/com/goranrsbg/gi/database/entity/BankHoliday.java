package com.goranrsbg.gi.database.entity;

import com.goranrsbg.gi.etc.Formats;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

/**
 *
 * @author goranrsbg
 */
@Entity
@NamedQuery(name = "BankHoliday.all", query = "SELECT b FROM BankHoliday b ORDER BY date DESC")
public class BankHoliday {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private long id;

    @Version
    private int version;

    @Column(name = "date", nullable = false, unique = true)
    private LocalDate date;

    @Column(name = "name", length = 171, nullable = false)
    private String name;

    public BankHoliday() {
        name = "";
    }

    public BankHoliday(BankHoliday bankHoliday) {
        date = bankHoliday.getDate();
        name = bankHoliday.getName();
    }

    public long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        final BankHoliday other = (BankHoliday) obj;
        return id == other.getId()
                && date.equals(other.getDate())
                && name.equals(other.getName());
    }

    public String toShortString() {
        return String.format("%s (%s)", name, date.format(Formats.DAY));
    }

    @Override
    public String toString() {
        return String.format("[id = %d, v = %d -> %s (%s)]", id, version, name, date);
    }

}
