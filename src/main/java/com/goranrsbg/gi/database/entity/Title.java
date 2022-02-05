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
 *
 * @author goranrsbg
 */
@Entity
@NamedQuery(name = "Title.all", query = "SELECT t FROM Title t ORDER BY shortName")
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private long id;

    @Version
    private int version;

    @Column(name = "name", length = 171, nullable = false, unique = true)
    private String name;

    @Column(name = "short_name", length = 31, nullable = false)
    private String shortName;

    @OneToMany(mappedBy = "title")
    private final Set<Worker> workers;

    public Title() {
        name = "";
        shortName = "";
        workers = new HashSet<>();
    }

    public Title(Title title) {
        name = title.getName();
        shortName = title.getShortName();
        workers = new HashSet<>();
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Set<Worker> getWorkers() {
        return workers;
    }

    public String toShortString() {
        return shortName;
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
        final Title other = (Title) obj;
        return id == other.getId()
                && name.equals(other.getName())
                && shortName.equals(other.getShortName());
    }

    @Override
    public String toString() {
        return String.format("%s | %s and workers.", shortName, name);
    }

}
