package nl.inholland.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

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

  @JsonProperty("preferedName")
  private String preferedName;

  @JsonProperty("sex")
  private SexEnum sex;

  @JsonProperty("dateOfBirth")
  private String dateOfBirth;

  @ManyToOne(cascade = CascadeType.REMOVE)
  @JsonProperty("primaryAddress")
  private Address primaryAddress;

  @ManyToOne
  @JsonProperty("postAddress")
  private Address postAddress;

  @JsonProperty("mobileNumber")
  private String mobileNumber;

  @JsonProperty("emailAddress")
  private String emailAddress;

  @JsonProperty("commrcialMessages")
  private CommrcialMessagesEnum commrcialMessages;

  @JsonProperty("preferedLanguage")
  private PreferedLanguageEnum preferedLanguage;

  @JsonProperty("userType")
  private UserTypeEnum userType;

  /**
   * Constructor with parameters
   **/
  public User(String officialName, String preferedName, SexEnum sex, String dateOfBirth, Address primaryAddress, Address postAddress, String mobileNumber, String emailAddress, CommrcialMessagesEnum commrcialMessages, PreferedLanguageEnum preferedLanguage, UserTypeEnum userType) {

    this.officialName = officialName;
    this.preferedName = preferedName;
    this.sex = sex;
    this.dateOfBirth = dateOfBirth;
    this.primaryAddress = primaryAddress;
    this.postAddress = postAddress;
    this.mobileNumber = mobileNumber;
    this.emailAddress = emailAddress;
    this.commrcialMessages = commrcialMessages;
    this.preferedLanguage = preferedLanguage;
    this.userType = userType;
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

  public enum CommrcialMessagesEnum {
    POST("by post"),

    BANKMAIL("by bankmail"),

    TELEPHONE("by telephone"),

    EMAIL("by email");

    private String value;

    CommrcialMessagesEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static CommrcialMessagesEnum fromValue(String text) {
      for (CommrcialMessagesEnum b : CommrcialMessagesEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  public enum PreferedLanguageEnum {
    DUTCH("Dutch"),

    ENGLISH("English");

    private String value;

    PreferedLanguageEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static PreferedLanguageEnum fromValue(String text) {
      for (PreferedLanguageEnum b : PreferedLanguageEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  public enum UserTypeEnum {
    CUSTOMER("Customer"),
    EMPLOYEE("Employee");
    private String value;

    UserTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static UserTypeEnum fromValue(String text) {
      for (UserTypeEnum b : UserTypeEnum.values()) {
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
  @ApiModelProperty(required = true, value = "")
 // @NotNull
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
   * Get preferedName
   *
   * @return preferedName
   **/
  @ApiModelProperty(value = "")
  public String getPreferedName() {
    return preferedName;
  }

  public void setPreferedName(String preferedName) {
    this.preferedName = preferedName;
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
  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
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
    this.mobileNumber = mobileNumber;
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
   * Get commrcialMessages
   *
   * @return commrcialMessages
   **/
  @ApiModelProperty(value = "")

  public CommrcialMessagesEnum getCommrcialMessages() {
    return commrcialMessages;
  }

  public void setCommrcialMessages(CommrcialMessagesEnum commrcialMessages) {
    this.commrcialMessages = commrcialMessages;
  }


  /**
   * Get preferedLanguage
   *
   * @return preferedLanguage
   **/
  @ApiModelProperty(value = "")

  public PreferedLanguageEnum getPreferedLanguage() {
    return preferedLanguage;
  }

  public void setPreferedLanguage(PreferedLanguageEnum preferedLanguage) {
    this.preferedLanguage = preferedLanguage;
  }

  public UserTypeEnum getUserType() {
    return userType;
  }

  public void setUserType(UserTypeEnum userType) {
    this.userType = userType;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    officialName: ").append(toIndentedString(officialName)).append("\n");
    sb.append("    preferedName: ").append(toIndentedString(preferedName)).append("\n");
    sb.append("    sex: ").append(toIndentedString(sex)).append("\n");
    sb.append("    dateOfBirth: ").append(toIndentedString(dateOfBirth)).append("\n");
    sb.append("    primaryAddress: ").append(toIndentedString(primaryAddress)).append("\n");
    sb.append("    postAddress: ").append(toIndentedString(postAddress)).append("\n");
    sb.append("    mobileNumber: ").append(toIndentedString(mobileNumber)).append("\n");
    sb.append("    emailAddress: ").append(toIndentedString(emailAddress)).append("\n");
    sb.append("    commrcialMessages: ").append(toIndentedString(commrcialMessages)).append("\n");
    sb.append("    preferedLanguage: ").append(toIndentedString(preferedLanguage)).append("\n");
    sb.append("    userType: ").append(toIndentedString(userType)).append("\n");
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
