package com.goranrsbg.gi.database.entity;

import com.goranrsbg.gi.database.entity.embedable.Address;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

/**
 *
 * @author Goran Cvijanovic
 */
@Entity
@NamedQuery(name = "CareHome.all", query = "SELECT c FROM CareHome c")
public class CareHome {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private long id;

    @Version
    private int version;

    @Column(name = "name", length = 171, nullable = false, unique = true)
    private String name;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "fk_client")
    private Client client;

    @ManyToMany
    @JoinTable(name = "carehome_worker",
            joinColumns = {
                @JoinColumn(name = "fk_carehome")},
            inverseJoinColumns = {
                @JoinColumn(name = "fk_worker")})
    private final Set<Worker> employed;

    public CareHome() {
        employed = new HashSet<>();
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Worker> getEmployed() {
        return employed;
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
        final CareHome other = (CareHome) obj;
        return id == other.getId()
                && name.equals(other.getName())
                && address.equals(other.getAddress());
    }

    @Override
    public String toString() {
        return String.format("[%d -> %s (%s)]", id, name, address);
    }

}
