package com.goranrsbg.gi.database.entity.embedable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Goran Cvijanovic
 */
@Embeddable
public class PersonName {

    @Column(name = "first_name", length = 171, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 171, nullable = false)
    private String lastName;

    public PersonName() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String surname) {
        this.lastName = surname;
    }

    /**
     * First name plus first letter from last name with dot.
     * <code>Goran Cvijanovic -> Goran C.</code>
     *
     * @return name surname string
     */
    public String toShortString() {
        return String.format("%s %c.", firstName, lastName.charAt(0));
    }

    /**
     * First name Last name.
     *
     * @return name surname string
     */
    public String toReverseString() {
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.firstName.hashCode();
        hash = 23 * hash + this.lastName.hashCode();
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
        final PersonName other = (PersonName) obj;
        return firstName.equals(other.getFirstName()) && lastName.equals(other.getLastName());
    }

    @Override
    public String toString() {
        return String.format("%s %s", lastName, firstName);
    }

}
