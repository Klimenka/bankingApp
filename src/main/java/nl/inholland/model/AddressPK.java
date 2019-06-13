package nl.inholland.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * AddressPK is a way to map a Composite Primary Key with JPA and Hibernate
 */
public class AddressPK implements Serializable {

    protected String postCode;
    protected Integer houseNumber;

    public AddressPK(String postCode, Integer houseNumber) {
        this.postCode = postCode;
        this.houseNumber = houseNumber;
    }

    public AddressPK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressPK)) return false;
        AddressPK addressPK = (AddressPK) o;
        return postCode.equals(addressPK.postCode) &&
                houseNumber.equals(addressPK.houseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postCode, houseNumber);
    }
}