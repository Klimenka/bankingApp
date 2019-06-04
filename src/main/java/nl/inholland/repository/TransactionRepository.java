package nl.inholland.repository;

import nl.inholland.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.threeten.bp.OffsetDateTime;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long>
{
    Iterable<Transaction> findAllByAccountFrom(String account);
    Iterable<Transaction> findAll();
    Iterable<Transaction> findAllByTransactionId(long Id);
}
