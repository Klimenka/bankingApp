package nl.inholland.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import java.text.DecimalFormat;
import java.time.LocalDate;


@Entity
@Validated
@javax.annotation.Generated(value = "io.inholland.codegen.v3.generators.java.SpringCodegen", date = "2019-05-23T16:34:19.518Z[GMT]")
public class SavingAccount extends Account {

    @JsonProperty("interestRate")
    private float interestRate;

    public SavingAccount(float balance, LocalDate dateOfOpening, String currency, User user) {
        super(balance, dateOfOpening, currency, user);
        setAccountType(AccountTypeEnum.SAVING);
    }

    public SavingAccount() {
    }

    @ApiModelProperty(value = "")
    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SavingAccount {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    interestRate: ").append(toIndentedString(interestRate)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

   /* @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }*/
}
