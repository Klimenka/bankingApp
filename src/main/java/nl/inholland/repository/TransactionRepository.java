package nl.inholland.repository;

import nl.inholland.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.threeten.bp.OffsetDateTime;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long>
{
    Iterable<Transaction> findById(long transactionId);
    Iterable<Transaction> findByStatus(TransactionStatus transactionStatus);
    Iterable<Transaction> findByType(Transaction.TransactionTypeEnum transactionTypeEnum);
    Iterable<Transaction> getAllByAccount(String account);
    Iterable<Transaction> getAll(String userAccount, java.time.OffsetDateTime dateFrom, java.time.OffsetDateTime dateTo);
    Transaction findByIdAndAccount(long transactionId, String userAccount);
}
