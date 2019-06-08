package nl.inholland.repository;

import nl.inholland.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    //@Query("select a.street, a.houseNumber, a.postCode, a.city, a.country from Address a where a.postCode = ?1 and a.houseNumber = ?2")
    //public String getAddress(String postCode, Integer houseNumber);
    public Address getAddressByPostCodeAndHouseNumber(String postCode, Integer houseNumber);
}


