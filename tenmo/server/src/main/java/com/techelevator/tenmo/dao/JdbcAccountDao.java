package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}

    @Override
    public List<Account> getAccounts(){
        List<Account> accountsList = new ArrayList<>();
        String sql = "SELECT account_id, user_id, balance FROM account;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            Account account = mapRowToAccount(results);
            accountsList.add(account);
        }
return accountsList;
    }

    @Override
    public Account getAccountInfoByAccountId(int accountId) {
        Account account = new Account();
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id =?;"; //returning account ID??
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
    if (results.next()){
        account = mapRowToAccount(results);
        return account;
    }
        System.out.println("No Account exists"); //EXCEPTION??
        return null;

    }

    @Override
    public Double getBalance(int accountId) {
        String sql = "SELECT balance FROM account WHERE account_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
        if (result.next()) {
            return mapRowToAccount(result).getBalance();
        }
        return null;
    }

@Override
public List<Account> getAccountsByUserId(int userId){
        List<Account> accountList = new ArrayList<>();
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id =?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while(results.next()){
            Account account = mapRowToAccount(results);
            accountList.add(account);
            return accountList;
        }
    System.out.println("User ID not Found");
        return null;

}

    private Account mapRowToAccount(SqlRowSet rowSet){
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getDouble("balance"));
        return account;

    }

}
