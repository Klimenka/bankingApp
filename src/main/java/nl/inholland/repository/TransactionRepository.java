package nl.inholland.repository;

import nl.inholland.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.threeten.bp.OffsetDateTime;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long>
{
    List<Transaction> findAll();
    List<Transaction> findAllByAccountFromEqualsAndTimestampEquals(String accountFrom, OffsetDateTime today);
    List<Transaction> findAllByAccountFromEqualsAndTimestampGreaterThanEqualAndTimestampLessThanEqual(String userAccount, OffsetDateTime dateFrom, OffsetDateTime dateTo);
}