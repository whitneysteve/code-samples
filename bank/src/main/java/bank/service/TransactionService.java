package bank.service;

import bank.model.Account;
import bank.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction lodgement( Account account, Integer amount );

    Transaction withdrawl( Account account, Integer amount );

    List<Transaction> getTransactions( Account account, int pageStart, int pageSize );

}
