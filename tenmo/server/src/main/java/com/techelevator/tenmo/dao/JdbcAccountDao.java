package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

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
    public Account findAccountInfoByAccountId(int accountId) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id =?;"; //returning account ID??
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,accountId);
if (results.next()){

    return mapRowToAccount(results);
}
        System.out.println("No Account exists"); //EXCEPTION??
return null;

    }

    @Override
    public double getBalanceByAccountId(int accountId) {
        String sql = "SELECT balance FROM account WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,accountId);
        if (results.next()){
        return results.getDouble("balance");}
        System.out.println("Account ID not found"); //EXCEPTION??
        return 0;
    }



//find account by user id
@Override
public List<Account> getAccountByUserId(int userId){
        List<Account> accountList = new ArrayList<>();
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id =?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userId);
        while(results.next()){
            Account account = mapRowToAccount(results);
            accountList.add(account);
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
