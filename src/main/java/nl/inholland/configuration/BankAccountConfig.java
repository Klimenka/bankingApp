package nl.inholland.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * set and get the bank own account
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "iban.number")
public class BankAccountConfig {

    private String IBAN;

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }
}
