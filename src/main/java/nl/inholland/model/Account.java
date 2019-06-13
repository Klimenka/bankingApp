package nl.inholland.model;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.text.DecimalFormat;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonDeserialize(as = CurrentAccount.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
@Generated(value = "io.inholland.codegen.v3.generators.java.SpringCodegen", date = "2019-05-23T16:34:19.518Z[GMT]")
public abstract class Account {

    @Id
    @SequenceGenerator(name = "bankAccSeq", initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bankAccSeq")
    @JsonProperty("accountNumber")
    private long accountNumber;

    private String iban;

    @JsonProperty("balance")
    private float balance;

    @JsonProperty("dateOfOpening")
    private LocalDate dateOfOpening = LocalDate.now();

    /**
     * I have added this property to only hold user Id when Employee wants
     * to create new Bank account. it is better than passing a User object
     * and it is not being saved in database.
     */
    @Transient
    @JsonProperty("userIdentification")
    private long userIdentification;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @JsonProperty("currency")
    private String currency = "euro";

    @JsonIgnore
    private float limitBalanace = 0f;

    @JsonProperty("accountStatus")
    private AccountStatusEnum accountStatus = AccountStatusEnum.OPENED;

    @JsonProperty("accountType")
    private AccountTypeEnum accountType;

    public Account(float balance, LocalDate dateOfOpening, String currency, User user) {
        this.balance = balance;
        this.dateOfOpening = dateOfOpening;
        this.user = user;
        this.currency = currency;
    }

    public Account() {
    }

    /**
     * Gets or Sets accountType
     */
    public enum AccountTypeEnum {
        CURRENT("current"),
        SAVING("saving");
        private String value;

        AccountTypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static AccountTypeEnum fromValue(String text) {
            for (AccountTypeEnum b : AccountTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    /**
     * Gets or Sets accountStatus
     */
    public enum AccountStatusEnum {
        OPENED("opened"),
        CLOSED("closed");
        private String value;

        AccountStatusEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static AccountStatusEnum fromValue(String text) {
            for (AccountStatusEnum b : AccountStatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    /**
     * I am using the auto generated account number and from it
     * I generate an IBAN using iban library.
     */
    public void buildIBAN(long accountNumber) {

        DecimalFormat df = new DecimalFormat("0000000000");
        if (accountNumber < 0) {
            throw new IllegalArgumentException("Account number cannot be negative");
        }
        String accountNumberFormatted = df.format(accountNumber);
        this.setIBAN(new Iban.Builder()
                .countryCode(CountryCode.NL)
                .bankCode("INHO")
                .accountNumber(accountNumberFormatted)
                .build().toString());
    }

    /**
     * getters and
     * setters
     */
    @ApiModelProperty(value = "")
    public long getAccountNumber() {
        return accountNumber;
    }

    @ApiModelProperty(value = "")
    public String getIBAN() {
        return iban;
    }

    public void setIBAN(String IBAN) {
        this.iban = IBAN;
    }

    @ApiModelProperty(value = "")
    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        if (balance < limitBalanace) {
            throw new IllegalArgumentException("Balance " + balance + " is below zero.");
        }
        this.balance = balance;
    }

    @ApiModelProperty(required = true, value = "")
    @NotNull
    public LocalDate getDateOfOpening() {
        return dateOfOpening;
    }

    public void setDateOfOpening(LocalDate dateOfOpening) {
        this.dateOfOpening = dateOfOpening;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getUserIdentification() {
        return userIdentification;
    }

    public void setUserIdentification(long userIdentification) {
        this.userIdentification = userIdentification;
    }

    public float getLimitBalanace() {
        return limitBalanace;
    }

    public void setLimitBalanace(float limitBalanace) {
        this.limitBalanace = limitBalanace;
    }

    @ApiModelProperty(value = "")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @ApiModelProperty(required = true, value = "")
    @NotNull
    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountTypeEnum accountType) {
        this.accountType = accountType;
    }

    @ApiModelProperty(value = "")
    public AccountStatusEnum getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatusEnum accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Account {\n");
        sb.append("    accountNumber: ").append(toIndentedString(accountNumber)).append("\n");
        sb.append("    IBAN: ").append(toIndentedString(iban)).append("\n");
        sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
        sb.append("    dateOfOpening: ").append(toIndentedString(dateOfOpening)).append("\n");
        sb.append("    userId: ").append(toIndentedString(user.getId())).append("\n");
        sb.append("    accountType: ").append(toIndentedString(accountType)).append("\n");
        sb.append("    accountStatus: ").append(toIndentedString(accountStatus)).append("\n");
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
