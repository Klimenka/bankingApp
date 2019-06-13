package nl.inholland.repository;

import nl.inholland.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

    public Address getAddressByPostCodeAndHouseNumber(String postCode, Integer houseNumber);
}


