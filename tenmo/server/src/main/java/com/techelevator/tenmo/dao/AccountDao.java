package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    List<Account> getAccounts();

   Account findAccountInfoByAccountId(int accountId);

    List<Account> getAccountByUserId(int userId);

    double getBalanceByAccountId(int accountId);

    //create account???



}
