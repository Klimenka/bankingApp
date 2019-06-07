package nl.inholland.model;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

/**
 * Address
 */
@Entity
@IdClass(AddressPK.class)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"postCode", "houseNumber"})})
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-02T11:27:08.122Z[GMT]")
public class Address implements Serializable {
    @JsonProperty("street")
    private String street;
    @Id
    @JsonProperty("houseNumber")
    private Integer houseNumber;
    @Id
    @JsonProperty("postCode")
    private String postCode;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country")
    private String country;

    public Address(String street, Integer houseNumber, String postCode, String city, String country) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
    }

    public Address() {
    }

    /**
     * Get street
     *
     * @return street
     **/
    @ApiModelProperty(value = "")

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }


    /**
     * Get houseNumber
     *
     * @return houseNumber
     **/
    @ApiModelProperty(value = "")

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }


    /**
     * Get postCode
     *
     * @return postCode
     **/
    @ApiModelProperty(value = "")

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }


    /**
     * Get city
     *
     * @return city
     **/
    @ApiModelProperty(value = "")

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get country
     *
     * @return country
     **/
    @ApiModelProperty(value = "")

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(this.street, address.street) &&
                Objects.equals(this.houseNumber, address.houseNumber) &&
                Objects.equals(this.postCode, address.postCode) &&
                Objects.equals(this.city, address.city) &&
                Objects.equals(this.country, address.country);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Address {\n");

        sb.append("    street: ").append(toIndentedString(street)).append("\n");
        sb.append("    houseNumber: ").append(toIndentedString(houseNumber)).append("\n");
        sb.append("    postCode: ").append(toIndentedString(postCode)).append("\n");
        sb.append("    city: ").append(toIndentedString(city)).append("\n");
        sb.append("    country: ").append(toIndentedString(country)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
