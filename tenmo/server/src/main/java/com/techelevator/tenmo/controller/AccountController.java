package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {
    private AccountDao accountDao;
    private UserDao userDao;

    public AccountController(JdbcAccountDao accountDao, JdbcUserDao userDao){
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/accounts", method = RequestMethod.GET)
    public List<Account> getAllAccounts(){
        List<Account> accountList = accountDao.getAccounts();
        return accountList;
    }

    @RequestMapping(path="/accounts/info/{accountId}", method = RequestMethod.GET)
    public Account getAccountInfoByAccountId(@PathVariable int accountId){
        Account accountById = accountDao.getAccountInfoByAccountId(accountId);
        return accountById;

    }
    @RequestMapping(path = "/accounts/{userId}", method = RequestMethod.GET)
    public List<Account> getAccountsByUserId(@PathVariable int userId){
        List<Account> accountByUserIdList = accountDao.getAccountsByUserId(userId);
        return accountByUserIdList;
    }
    @RequestMapping(path = "/accounts/balance/{id}",method = RequestMethod.GET)
    public double getBalanceByAccountId(@PathVariable int id){
        double accountBalance = accountDao.getBalanceByAccountId(id);
        return accountBalance;
    }
}
