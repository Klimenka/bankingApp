package nl.inholland.model;

import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;

/**
 * Customer
 */
@Entity
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-02T11:27:08.122Z[GMT]")
public class Customer extends User {

    public Customer(String officialName, String preferedName, SexEnum sex, String dateOfBirth, Address primaryAddress, Address postAddress, String mobileNumber, String emailAddress, CommercialMessagesEnum commrcialMessages, PreferredLanguageEnum preferedLanguage, String userType) {
        super(officialName, preferedName, sex, dateOfBirth, primaryAddress, postAddress, mobileNumber, emailAddress, commrcialMessages, preferedLanguage, userType);
    }

    public Customer() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Customer {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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
