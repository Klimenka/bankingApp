package nl.inholland.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AddressTest {

    private Address address;

    @Before
    public void setup() {
        address = new Address("ExampleStreet", 77, "2345NM",
                "Nijken", "The Netherlands");
    }

    @Test
    public void CreateNewAddressShouldNotBeNull() {
        assertNotNull(address);
    }

}
