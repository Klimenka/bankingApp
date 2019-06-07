package nl.inholland.repository;

import nl.inholland.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> getAccountByUserIdEqualsAndAccountStatusEquals
            (long userId, Account.AccountStatusEnum status);

    List<Account> getAccountByUserIdEqualsAndAccountTypeEqualsAndAccountStatusEquals
            (long userId, Account.AccountTypeEnum accountType, Account.AccountStatusEnum status);

    List<Account> getAccountByDateOfOpeningEquals
            (LocalDate dateOfOpening);

    List<Account> getAccountByAccountTypeEquals(Account.AccountTypeEnum accountType);

    List<Account> getAccountByDateOfOpeningEqualsAndAccountTypeEquals
            (LocalDate dateOfOpening, Account.AccountTypeEnum accountType);

}
