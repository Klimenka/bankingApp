package nl.inholland.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * User
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-02T11:27:08.122Z[GMT]")
public class User   {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("officialName")
  private String officialName = null;

  @JsonProperty("preferedName")
  private String preferedName = null;

  /**
   * Gets or Sets sex
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
  @JsonProperty("sex")
  private SexEnum sex = null;

  @JsonProperty("dateOfBirth")
  private String dateOfBirth = null;

  @JsonProperty("primaryAddress")
  private Address primaryAddress = null;

  @JsonProperty("postAddress")
  private Address postAddress = null;

  @JsonProperty("mobileNumber")
  private String mobileNumber = null;

  @JsonProperty("emailAddress")
  private String emailAddress = null;

  /**
   * Gets or Sets commrcialMessages
   */
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
  @JsonProperty("commrcialMessages")
  private CommrcialMessagesEnum commrcialMessages = null;

  /**
   * Gets or Sets preferedLanguage
   */
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
  @JsonProperty("preferedLanguage")
  private PreferedLanguageEnum preferedLanguage = null;

  public User id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public User officialName(String officialName) {
    this.officialName = officialName;
    return this;
  }

  /**
   * Get officialName
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

  public User preferedName(String preferedName) {
    this.preferedName = preferedName;
    return this;
  }

  /**
   * Get preferedName
   * @return preferedName
  **/
  @ApiModelProperty(value = "")

  public String getPreferedName() {
    return preferedName;
  }

  public void setPreferedName(String preferedName) {
    this.preferedName = preferedName;
  }

  public User sex(SexEnum sex) {
    this.sex = sex;
    return this;
  }

  /**
   * Get sex
   * @return sex
  **/
  @ApiModelProperty(value = "")

  public SexEnum getSex() {
    return sex;
  }

  public void setSex(SexEnum sex) {
    this.sex = sex;
  }

  public User dateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

  /**
   * Get dateOfBirth
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

  public User primaryAddress(Address primaryAddress) {
    this.primaryAddress = primaryAddress;
    return this;
  }

  /**
   * Get primaryAddress
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

  public User postAddress(Address postAddress) {
    this.postAddress = postAddress;
    return this;
  }

  /**
   * Get postAddress
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

  public User mobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
    return this;
  }

  /**
   * Get mobileNumber
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

  public User emailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

  /**
   * Get emailAddress
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

  public User commrcialMessages(CommrcialMessagesEnum commrcialMessages) {
    this.commrcialMessages = commrcialMessages;
    return this;
  }

  /**
   * Get commrcialMessages
   * @return commrcialMessages
  **/
  @ApiModelProperty(value = "")

  public CommrcialMessagesEnum getCommrcialMessages() {
    return commrcialMessages;
  }

  public void setCommrcialMessages(CommrcialMessagesEnum commrcialMessages) {
    this.commrcialMessages = commrcialMessages;
  }

  public User preferedLanguage(PreferedLanguageEnum preferedLanguage) {
    this.preferedLanguage = preferedLanguage;
    return this;
  }

  /**
   * Get preferedLanguage
   * @return preferedLanguage
  **/
  @ApiModelProperty(value = "")

  public PreferedLanguageEnum getPreferedLanguage() {
    return preferedLanguage;
  }

  public void setPreferedLanguage(PreferedLanguageEnum preferedLanguage) {
    this.preferedLanguage = preferedLanguage;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(this.id, user.id) &&
        Objects.equals(this.officialName, user.officialName) &&
        Objects.equals(this.preferedName, user.preferedName) &&
        Objects.equals(this.sex, user.sex) &&
        Objects.equals(this.dateOfBirth, user.dateOfBirth) &&
        Objects.equals(this.primaryAddress, user.primaryAddress) &&
        Objects.equals(this.postAddress, user.postAddress) &&
        Objects.equals(this.mobileNumber, user.mobileNumber) &&
        Objects.equals(this.emailAddress, user.emailAddress) &&
        Objects.equals(this.commrcialMessages, user.commrcialMessages) &&
        Objects.equals(this.preferedLanguage, user.preferedLanguage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, officialName, preferedName, sex, dateOfBirth, primaryAddress, postAddress, mobileNumber, emailAddress, commrcialMessages, preferedLanguage);
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
