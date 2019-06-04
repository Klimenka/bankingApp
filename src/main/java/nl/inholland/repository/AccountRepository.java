package nl.inholland.repository;

import nl.inholland.model.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Iterable<Account> getAccountByUserIdEquals(long userId);

    Iterable<Account> getAccountByUserIdEqualsAndAccountTypeEquals
            (long userId, Account.AccountTypeEnum accountType);

    Iterable<Account> getAccountByDateOfOpeningGreaterThanEqualOrderByDateOfOpeningDesc
            (LocalDate dateOfOpening);

    Iterable<Account> getAccountByAccountTypeEquals(Account.AccountTypeEnum accountType);

    Iterable<Account> getAccountByDateOfOpeningGreaterThanEqualAndAccountTypeEquals
            (LocalDate dateOfOpening, Account.AccountTypeEnum accountType);
}
