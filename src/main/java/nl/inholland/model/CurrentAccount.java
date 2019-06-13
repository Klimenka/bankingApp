package nl.inholland.model;


import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Validated
@Generated(value = "io.inholland.codegen.v3.generators.java.SpringCodegen", date = "2019-05-23T16:34:19.518Z[GMT]")
public class CurrentAccount extends Account {

    public CurrentAccount(float balance, LocalDate dateOfOpening, String currency, User user) {
        super(balance, dateOfOpening, currency, user);
        this.setAccountType(AccountTypeEnum.CURRENT);
    }

    public CurrentAccount() {
        this.setAccountType(AccountTypeEnum.CURRENT);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CurrentAccount {\n");
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
