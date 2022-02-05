package com.goranrsbg.gi.database.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
public class TimeSheetData {

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

    @ManyToOne
    @JoinColumn(name = "fk_carehome")
    private CareHome careHome;

    @ManyToOne
    @JoinColumn(name = "fk_worker")
    private Worker worker;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "break_time", nullable = false)
    private LocalTime breakTime;

    public TimeSheetData() {
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

    public CareHome getCareHome() {
        return careHome;
    }

    public void setCareHome(CareHome careHome) {
        this.careHome = careHome;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(LocalTime breakTime) {
        this.breakTime = breakTime;
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
        final TimeSheetData other = (TimeSheetData) obj;
        return id == other.getId()
                && company.equals(other.getCompany())
                && client.equals(other.getClient())
                && careHome.equals(other.getCareHome())
                && worker.equals(other.getWorker())
                && startTime.equals(other.getStartTime())
                && endTime.equals(other.getEndTime())
                && breakTime.equals(other.getBreakTime());
    }

    @Override
    public String toString() {
        return String.format("[%d, %s |%s| /%s/ |%s| %6$td/%6$tm/%6$tY %6$tH:%6$tM - %7td/%7tm/%7tY %7$tH:%7$tM (%8$tH:%8$tM)", id, company, client, careHome, worker, startTime, endTime, breakTime);
    }

}
