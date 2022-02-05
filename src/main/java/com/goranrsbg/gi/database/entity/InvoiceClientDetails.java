package com.goranrsbg.gi.database.entity;

import java.time.LocalDate;
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
public class InvoiceClientDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private long id;

    @Version
    private int version;

    @ManyToOne
    @JoinColumn(name = "fk_company")
    private Company company;

    @Column(name = "week_ending", nullable = false, unique = true)
    private LocalDate weekEnding;

    @Column(name = "invoice_number", nullable = false, unique = true)
    private long invoiceNumber;

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
        final InvoiceWorkerDetails other = (InvoiceWorkerDetails) obj;
        return id == other.getId()
                && invoiceNumber == other.getInvoiceNumber()
                && weekEnding == other.getWeekEnding()
                && company.equals(other.getCompany());
    }

    @Override
    public String toString() {
        return String.format("[%d, %s Client %3&td/%3&tm/%3&tY | %d |", id, company, weekEnding, invoiceNumber);
    }
}
