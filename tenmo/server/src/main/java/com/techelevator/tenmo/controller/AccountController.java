package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {
    private AccountDao accountDao;
    private UserDao userDao;

    public void accountController(JdbcAccountDao dao, JdbcUserDao userDao){
        this.accountDao = dao;
        this.userDao = userDao;

    }

    @RequestMapping(path = "/accounts", method = RequestMethod.GET)
    public List<Account> getAllAccounts(){
        List<Account> accountList = accountDao.getAccounts();
        return accountList;
    }

    @RequestMapping(path="/accounts/{id}", method = RequestMethod.GET)
    public Account getAccountInfoByAccountId(int accountId){
        Account accountById = accountDao.findAccountInfoByAccountId(accountId);
        return accountById;

    }
    @RequestMapping(path = "????", method = RequestMethod.GET) //PATH
    public List<Account> getAccountByUserId(int userId){
        List<Account> accountList = accountDao.getAccountByUserId(userId);
        return accountList;
    }
    @RequestMapping(path = "/accounts/balance",method = RequestMethod.GET)
    public double getBalanceByAccountId(int accountId){
        double accountBalance = accountDao.getBalanceByAccountId(accountId);
        return accountBalance;
    }
}
