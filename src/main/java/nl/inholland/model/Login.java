package nl.inholland.model;

import java.security.SecureRandom;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-02T11:27:08.122Z[GMT]")
public class Login {

    @Id
    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @Transient
    private static final Random RANDOM = new SecureRandom();
    @Transient
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public Login(Login login) {
        this.password = login.getPassword();
        this.user = login.getUser();
        this.userName = login.user.getEmailAddress();
    }

    /*
        this constructor is for when creating a login with random password
     */
    public Login(String userName, User user) {
        this.userName = userName;
        this.user = user;
        this.password = generatePassword();
    }
    /*
        this constructor is for when creating a login with fixed password
     */
    public Login(String userName, String password, User user) {
        this.userName = userName;
        this.user = user;
        this.password = password;
    }

    /*
      default empy constructor
   */
    public Login() {
    }

    private String generatePassword() {
        StringBuilder returnValue = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public Boolean checkPassword(String password) {
        if (password.equals(this.password)) {
            return true;
        } else {
            return false;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getUserName() {
        return userName;
    }

    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return user.getId();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class Login {\n");
        sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
        sb.append("    password: ").append(toIndentedString(password)).append("\n");
        sb.append("    userId: ").append(toIndentedString(user.getId())).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
