package nl.inholland.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * User model
 */

@Entity
@Validated
@JsonDeserialize(as = Customer.class)
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-02T11:27:08.122Z[GMT]")
public abstract class User {

    @Id
    @SequenceGenerator(name = "userSeq", initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    @JsonProperty("id")
    private Long id;

    @JsonProperty("officialName")
    private String officialName;

    @JsonProperty("preferredName")
    private String preferredName;

    @JsonProperty("sex")
    private SexEnum sex;

    @JsonProperty("dateOfBirth")
    private LocalDate dateOfBirth;

    @ManyToOne
    @JsonProperty("primaryAddress")
    private Address primaryAddress;

    @ManyToOne
    @JsonProperty("postAddress")
    private Address postAddress;

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("emailAddress")
    private String emailAddress;

    @JsonProperty("commercialMessages")
    private CommercialMessagesEnum commercialMessages;

    @JsonProperty("preferredLanguage")
    private PreferredLanguageEnum preferredLanguage;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId")
    protected Set<Role> roles;

    /**
     * Constructor with parameters
     **/
    public User(String officialName, String preferredName, SexEnum sex, String dateOfBirth, Address primaryAddress,
                Address postAddress, String mobileNumber, String emailAddress, CommercialMessagesEnum commercialMessages,
                PreferredLanguageEnum preferredLanguage, String roleString) {

        this.officialName = officialName;
        this.preferredName = preferredName;
        this.sex = sex;
        setDateOfBirth(dateOfBirth);
        this.primaryAddress = primaryAddress;
        this.postAddress = postAddress;
        setMobileNumber(mobileNumber);
        this.emailAddress = emailAddress;
        this.commercialMessages = commercialMessages;
        this.preferredLanguage = preferredLanguage;
        this.roles = new HashSet<>();
        Role role = new Role();
        role.setRole(roleString);
        this.roles.add(role);
    }

    /**
     * Empty default constructor
     **/
    public User() {
    }

    /**
     * Enums
     */
    public enum SexEnum {
        MALE("male"),

        FEMALE("female");

        private String value;

        SexEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static SexEnum fromValue(String text) {
            for (SexEnum b : SexEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }

            }
            return null;
        }
    }


    public enum CommercialMessagesEnum {
        POST("by post"),

        BANKMAIL("by bankmail"),

        TELEPHONE("by telephone"),

        EMAIL("by email");

        private String value;

        CommercialMessagesEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static CommercialMessagesEnum fromValue(String text) {
            for (CommercialMessagesEnum b : CommercialMessagesEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    public enum PreferredLanguageEnum {
        DUTCH("Dutch"),

        ENGLISH("English");

        private String value;

        PreferredLanguageEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static PreferredLanguageEnum fromValue(String text) {
            for (PreferredLanguageEnum b : PreferredLanguageEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }


    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(value = "")

    public Long getId() {
        return id;
    }


    /**
     * Get officialName
     *
     * @return officialName
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    /**
     * Get preferredName
     *
     * @return preferredName
     **/
    @ApiModelProperty(value = "")
    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    /**
     * Get sex
     *
     * @return sex
     **/
    @ApiModelProperty(value = "")
    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    /**
     * Get dateOfBirth
     *
     * @return dateOfBirth
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);
        LocalDate date = null;

        try {
            date = LocalDate.parse(dateOfBirth, format);

        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
        if (date.compareTo(LocalDate.now()) > 0) {
            throw new IllegalArgumentException("DateOfBirth should be in the past");
        }
        this.dateOfBirth = date;
    }

    /**
     * Get primaryAddress
     *
     * @return primaryAddress
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull
    @Valid
    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }


    /**
     * Get postAddress
     *
     * @return postAddress
     **/
    @ApiModelProperty(value = "")

    @Valid
    public Address getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(Address postAddress) {
        this.postAddress = postAddress;
    }


    /**
     * Get mobileNumber
     *
     * @return mobileNumber
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        if (mobileNumber.matches("^\\(?([+]31|0031|0)-?6(\\s?|-)([0-9]\\s{0,3}){8}$")) {
            this.mobileNumber = mobileNumber;
        } else {
            throw new IllegalArgumentException("This is not Dutch phone number");
        }
    }


    /**
     * Get emailAddress
     *
     * @return emailAddress
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


    /**
     * Get commercialMessages
     *
     * @return commercialMessages
     **/
    @ApiModelProperty(value = "")

    public CommercialMessagesEnum getCommercialMessages() {
        return commercialMessages;
    }

    public void setCommercialMessages(CommercialMessagesEnum commercialMessages) {
        this.commercialMessages = commercialMessages;
    }


    /**
     * Get preferredLanguage
     *
     * @return preferredLanguage
     **/
    @ApiModelProperty(value = "")

    public PreferredLanguageEnum getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(PreferredLanguageEnum preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }


    public Set<Role> getRoles() {
        return roles;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class User {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    officialName: ").append(toIndentedString(officialName)).append("\n");
        sb.append("    preferredName: ").append(toIndentedString(preferredName)).append("\n");
        sb.append("    sex: ").append(toIndentedString(sex)).append("\n");
        sb.append("    dateOfBirth: ").append(toIndentedString(dateOfBirth)).append("\n");
        sb.append("    primaryAddress: ").append(toIndentedString(primaryAddress)).append("\n");
        sb.append("    postAddress: ").append(toIndentedString(postAddress)).append("\n");
        sb.append("    mobileNumber: ").append(toIndentedString(mobileNumber)).append("\n");
        sb.append("    emailAddress: ").append(toIndentedString(emailAddress)).append("\n");
        sb.append("    commercialMessages: ").append(toIndentedString(commercialMessages)).append("\n");
        sb.append("    preferredLanguage: ").append(toIndentedString(preferredLanguage)).append("\n");
        sb.append("    userRoles: ").append(toIndentedString(roles)).append("\n");
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
