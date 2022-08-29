package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    List<Account> getAccounts();

    Account getAccountInfoByAccountId(int accountId);

    Account getAccountsByUserId(int userId);

    double getBalanceByAccountId(int accountId);

}
