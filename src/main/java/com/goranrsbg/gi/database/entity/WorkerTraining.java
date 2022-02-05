package com.goranrsbg.gi.database.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

/**
 *
 * @author Goran Cvijanovic
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"fk_worker", "fk_training"}))
public class WorkerTraining {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private long id;

    @Version
    private int version;

    @ManyToOne
    @JoinColumn(name = "fk_worker")
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "fk_training")
    private Training training;

    @Column(name = "expire_date", nullable = false)
    private LocalDate expireDate;

    public WorkerTraining() {
    }

    public long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
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
        final WorkerTraining other = (WorkerTraining) obj;
        return id == other.getId()
                && worker.equals(other.getWorker())
                && training.equals(other.getTraining())
                && expireDate.equals(other.getExpireDate());
    }

    @Override
    public String toString() {
        return String.format("[%d -> %s | %s | expire on %4$td/%4$tm/%4$tY]", id, worker, training, expireDate);
    }

}
