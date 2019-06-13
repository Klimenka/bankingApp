package nl.inholland.model;

import java.time.LocalDate;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Transaction
 */
@Validated
@Entity
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-02T11:27:08.122Z[GMT]")
public class Transaction   {
    @JsonProperty("transaction_id")
    @Id
    @SequenceGenerator(name = "transactionSequence", initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactionSequence")
    private long transactionId;

    @JsonProperty("accountFrom")
    private String accountFrom;

    @JsonProperty("accountTo")
    private String accountTo;

    @JsonProperty("amount")
    private float amount;

    @ManyToOne
    @JsonProperty("userPerforming")
    private User userPerforming;

    @JsonProperty("date")
    private LocalDate date;

    private int DAYLIMIT = 20;

    private float TRANSACTIONLIMIT = 10000;

    private float ABSOLUTELIMIT = 10;

    public Transaction() {

    }

    public Transaction(String accountFrom, String accountTo, Float amount, User userPerforming, LocalDate date, TransactionTypeEnum transactionType)
    {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.userPerforming = userPerforming;
        this.date = date;
        this.transactionType = transactionType;
    }

    /**
     * Gets or Sets transactionStatus
     */
    public enum TransactionStatusEnum {
        PENDING("pending"),

        SUCCESSFUL("successful"),

        FAILED("failed");

        private String value;

        TransactionStatusEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static TransactionStatusEnum fromValue(String text) {
            for (TransactionStatusEnum b : TransactionStatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("transactionStatus")
    private TransactionStatusEnum transactionStatus = TransactionStatusEnum.PENDING;

    /**
     * Gets or Sets transactionType
     */
    public enum TransactionTypeEnum {
        TRANSACTION("transaction"),

        WITHDRAW("withdraw"),

        DEPOSIT("deposit");

        private String value;

        TransactionTypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static TransactionTypeEnum fromValue(String text) {
            for (TransactionTypeEnum b : TransactionTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("transactionType")
    private TransactionTypeEnum transactionType;

    public Transaction transactionId(long transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    /**
     * Get transactionId
     *
     * @return transactionId
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public Transaction accountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
        return this;
    }

    /**
     * Get accountFrom
     *
     * @return accountFrom
     **/
    @ApiModelProperty(value = "")

    public String getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(String accountFrom) {

        if(accountFrom == null)
            throw new IllegalArgumentException("Please fill in your account");
        this.accountFrom = accountFrom;
    }

    public Transaction accountTo(String accountTo) {
        this.accountTo = accountTo;
        return this;
    }

    /**
     * Get accountTo
     *
     * @return accountTo
     **/
    @ApiModelProperty(value = "")

    public String getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(String accountTo) {
        this.accountTo = accountTo;
    }

    public Transaction amount(Float amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Get amount
     * minimum: 1
     * maximum: 10000
     *
     * @return amount
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @DecimalMin(value = "1", message = "The balance must be above 1")
    @DecimalMax(value = "10000", message = "Balance must be below 10000")
    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount)
    {
        if(amount < 0)
        {
            throw new IllegalArgumentException("the amount must not be below zero");
        } else if (amount == null) {
            throw new NullPointerException("the amount should have a value");
        }

        this.amount = amount;
    }

    public Transaction userPerforming(User userPerforming) {
        this.userPerforming = userPerforming;
        return this;
    }

    /**
     * Get userPerforming
     *
     * @return userPerforming
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    public User getUserPerforming()
    {
        return userPerforming;
    }

    public void setUserPerforming(User userPerforming) {
        if(userPerforming == null)
            throw new IllegalArgumentException("userPerforming should not be null");

        this.userPerforming = userPerforming;
    }

    public Transaction date (LocalDate date) {
        this.date = date;
        return this;
    }

    /**
     * Get date
     *
     * @return date
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public Transaction transactionStatus(TransactionStatusEnum transactionStatus) {
        this.transactionStatus = transactionStatus;
        return this;
    }

    /**
     * Get transactionStatus
     *
     * @return transactionStatus
     **/
    @ApiModelProperty(value = "")

    public TransactionStatusEnum getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatusEnum transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Transaction transactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    @Valid
    public int getDayLimit()
    {
        return DAYLIMIT;
    }

    @Valid
    public float getTransactionLimit()
    {
        return TRANSACTIONLIMIT;
    }

    @Valid
    public float getAbsoluteLimit()
    {
        return ABSOLUTELIMIT;
    }

    /**
     * Get transactionType
     *
     * @return transactionType
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction transaction = (Transaction) o;
        return Objects.equals(this.transactionId, transaction.transactionId) &&
                Objects.equals(this.accountFrom, transaction.accountFrom) &&
                Objects.equals(this.accountTo, transaction.accountTo) &&
                Objects.equals(this.amount, transaction.amount) &&
                Objects.equals(this.userPerforming, transaction.userPerforming) &&
                Objects.equals(this.date, transaction.date) &&
                Objects.equals(this.transactionStatus, transaction.transactionStatus) &&
                Objects.equals(this.transactionType, transaction.transactionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, accountFrom, accountTo, amount, userPerforming, date, transactionStatus, transactionType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Transaction {\n");

        sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
        sb.append("    accountFrom: ").append(toIndentedString(accountFrom)).append("\n");
        sb.append("    accountTo: ").append(toIndentedString(accountTo)).append("\n");
        sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
        sb.append("    userPerforming: ").append(toIndentedString(userPerforming)).append("\n");
        sb.append("    date: ").append(toIndentedString(date)).append("\n");
        sb.append("    transactionStatus: ").append(toIndentedString(transactionStatus)).append("\n");
        sb.append("    transactionType: ").append(toIndentedString(transactionType)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
