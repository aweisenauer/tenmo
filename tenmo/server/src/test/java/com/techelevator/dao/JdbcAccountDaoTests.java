package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.util.List;

public class JdbcAccountDaoTests extends BaseDaoTests{
    private JdbcAccountDao sut;

    protected static final Account TEST_ACCOUNT_1 = new Account(2001, 1001, 1000);
    protected static final Account TEST_ACCOUNT_2 = new Account(2002, 1002, 20);

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void get_all_accounts(){
        List<Account> allAccounts = sut.getAccounts();
        Assert.assertEquals(2, allAccounts.size());
    }

    @Test
    public void get_account_by_account_id(){
      Account account = sut.getAccountInfoByAccountId(2001);
      Assert.assertEquals(TEST_ACCOUNT_1.getAccountId(),account.getAccountId());
    }

    @Test
    public void get_account_balance_by_account_id(){
        double accountBalance = sut.getBalanceByAccountId(2001);
        Assert.assertEquals(TEST_ACCOUNT_1.getBalance(),accountBalance,0.1);

    }
//    @Test
//    public void get_account_by_user_is(){
//        Account account = sut.getAccountsByUserId(1001);
//        Assert.assertEquals(1,);

    }

//    private void assertAccountsMatch(Account expected, Account actual) {
//        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
//        Assert.assertEquals(expected.getUserId(), actual.getUserId());
//        Assert.assertEquals(expected.getBalance(), actual.getBalance(),0.1);
//    }




