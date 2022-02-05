package com.goranrsbg.gi.database.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

/**
 * SendEmail entity class.
 *
 * @author Goran Cvijanovic
 */
@Entity
@NamedQuery(name = "Email.all", query = "SELECT e FROM Email e ORDER BY description")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private long id;

    @Version
    private int version;

    @Column(name = "description", length = 171, nullable = false)
    private String description;

    @Column(name = "out_server", length = 171, nullable = false)
    private String outServer;

    @Column(name = "smtp_port", nullable = false)
    private int smtpPort;

    @Column(name = "e_mail", length = 171, nullable = false)
    private String email;

    @Column(name = "password", length = 171, nullable = false)
    private String password;

    @ManyToMany(mappedBy = "sendingEmails")
    private final Set<Company> companies;

    public Email() {
        description = "";
        outServer = "";
        email = "";
        password = "";
        companies = new HashSet<>();
    }

    public Email(Email email) {
        description = email.getDescription();
        outServer = email.getOutServer();
        this.email = email.getEmail();
        password = email.getPassword();
        companies = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOutServer() {
        return outServer;
    }

    public void setOutServer(String outServer) {
        this.outServer = outServer;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smptPort) {
        this.smtpPort = smptPort;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public String toShortString() {
        return String.format("%s (%s)", description, email);
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
        final Email e = (Email) obj;
        return id == e.getId()
                && smtpPort == e.getSmtpPort()
                && description.equals(e.getDescription())
                && outServer.equals(e.getOutServer())
                && email.equals(e.getEmail())
                && password.equals(e.getPassword());
    }

    @Override
    public String toString() {
        return String.format("[%d -> %s (%s) |%d| u:%s p:%s]", id, description, outServer, smtpPort, email, password);
    }

}
