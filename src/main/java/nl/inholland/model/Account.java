package nl.inholland.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.text.DecimalFormat;
import java.text.DecimalFormat;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonDeserialize(as = CurrentAccount.class)
@Validated
@javax.annotation.Generated(value = "io.inholland.codegen.v3.generators.java.SpringCodegen", date = "2019-05-23T16:34:19.518Z[GMT]")
public abstract class Account {

  @Id
  @SequenceGenerator(name = "bankAccSeq", initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bankAccSeq")
  @JsonProperty("accountNumber")
  private long accountNumber;

  //@JsonProperty("IBAN")
  private String IBAN;

  @JsonProperty("balance")
  private float balance;

  @JsonProperty("dateOfOpening")
  private LocalDate dateOfOpening = LocalDate.now();

  @JsonProperty("userId")
  private long userId;

  @JsonProperty("currency")
  private String currency = "euro";

  @JsonProperty("accountStatus")
  private AccountStatusEnum accountStatus = AccountStatusEnum.OPENED;

  @JsonProperty("accountType")
  private AccountTypeEnum accountType;

  //  String accountNumber
  //  this.accountNumber = accountNumber;
  public Account(float balance, LocalDate dateOfOpening, long userId,
                 String currency) {
    this.balance = balance;
    this.dateOfOpening = dateOfOpening;
    this.userId = userId;
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

  public void buildIBAN() {
    DecimalFormat df = new DecimalFormat("0000000000");
    String accountNumberFormatted = df.format(this.accountNumber);
    this.IBAN = new Iban.Builder()
            .countryCode(CountryCode.NL)
            .bankCode("INHO")
            .accountNumber(accountNumberFormatted)
            .build().toString();
  }

  @ApiModelProperty(value = "")
  public long getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
  }

  @ApiModelProperty(value = "")
  public String getIBAN() {
    return IBAN;
  }

  public void setIBAN(String IBAN) {
    this.IBAN = IBAN;
  }

  @ApiModelProperty(value = "")
  public float getBalance() {
    return balance;
  }

  public void setBalance(float balance) {
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

  @ApiModelProperty(required = true, value = "")
  @NotNull
  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
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
    sb.append("    IBAN: ").append(toIndentedString(IBAN)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    dateOfOpening: ").append(toIndentedString(dateOfOpening)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
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



      /*public Account accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }*/

    /*public Account balance(Float balance) {
        this.balance = balance;
        return this;
    }

    public Account dateOfOpening(String dateOfOpening) {
        this.dateOfOpening = dateOfOpening;
        return this;
    }

    public Account userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Account accountType(AccountTypeEnum accountType) {
        this.accountType = accountType;
        return this;
    }

    public Account accountStatus(AccountStatusEnum accountStatus) {
        this.accountStatus = accountStatus;
        return this;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(this.accountNumber, account.accountNumber) &&
                Objects.equals(this.balance, account.balance) &&
                Objects.equals(this.dateOfOpening, account.dateOfOpening) &&
                Objects.equals(this.userId, account.userId) &&
                Objects.equals(this.accountType, account.accountType) &&
                Objects.equals(this.accountStatus, account.accountStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, balance, dateOfOpening, userId, accountType, accountStatus);
    }*/


}
