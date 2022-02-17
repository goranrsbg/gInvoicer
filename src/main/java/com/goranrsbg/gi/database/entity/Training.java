package com.goranrsbg.gi.database.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 * Training entity class.
 *
 * @author Goran Cvijanovic
 */
@Entity
@NamedQuery(name = "Training.all", query = "SELECT t FROM Training t ORDER BY name")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private long id;

    @Version
    private int version;

    @Column(name = "name", length = 171, nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 171, nullable = false)
    private String description;

    @OneToMany(mappedBy = "training")
    private final Set<WorkerTraining> workers;

    public Training() {
        name = "";
        description = "";
        workers = new HashSet<>();
    }

    public Training(Training training) {
        workers = new HashSet<>();
        this.name = training.getName();
        this.description = training.getDescription();
    }

    public long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<WorkerTraining> getWorkers() {
        return workers;
    }

    public String toShortString() {
        return name;
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
        final Training t = (Training) obj;
        return id == t.getId()
                && name.equals(t.getName())
                && description.equals(t.getDescription());
    }

    @Override
    public String toString() {
        return String.format("[%d -> %s /%s/]", id, name, description);
    }

}
