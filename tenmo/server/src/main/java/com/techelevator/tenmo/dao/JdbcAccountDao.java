package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
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
        String sql = "SELECT account_id, user_id FROM account;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            Account account = mapRowToAccountNoBalance(results);
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
    public double getBalanceByAccountId(int id) {
        String sql = "SELECT balance FROM account WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,id);
        if (results.next()){
        return results.getDouble("balance");}
        System.out.println("Account ID not found"); //EXCEPTION??
        return 0;
    }

@Override
public Account getAccountsByUserId(int userId){
        Account account = new Account();
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id =?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userId);
        while(results.next()){
            account = mapRowToAccount(results);
            return account;
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

    private Account mapRowToAccountNoBalance(SqlRowSet rowSet){
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        return account;

    }
}
