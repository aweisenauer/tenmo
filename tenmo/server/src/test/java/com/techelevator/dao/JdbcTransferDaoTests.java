package com.techelevator.dao;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class JdbcTransferDaoTests extends BaseDaoTests {
    private JdbcTransferDao sut;
private AccountDao accountDao;
    private static final Transfer TEST_Transfer_1 = new Transfer(3001, 1, 2002, 2001, 20.00);
    private static final Transfer TEST_Transfer_2 = new Transfer(3002, 1, 2001, 2002, 20.00);
    private static final Transfer TEST_Transfer_3 = new Transfer(3003, 1, 2001, 2002, 20.00);

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        accountDao = new JdbcAccountDao(jdbcTemplate);
        sut = new JdbcTransferDao(dataSource,accountDao);


    }

    @Test
    public void list_all_transfers() {
        List<Transfer> transferList = sut.getAllTransfers();
        Assert.assertEquals(2, transferList.size());
    }

    @Test
    public void get_transfer_by_transfer_id() {
        Transfer transfer = sut.getTransferByTransferId(3001);
        Assert.assertEquals(transfer.getTransferId(), TEST_Transfer_1.getTransferId());

    }

    @Test
    public void create_transfer(){
        boolean transferCreated = sut.createTransfer(TEST_Transfer_2);
        Assert.assertTrue(transferCreated);
        Transfer transfer = sut.getTransferByTransferId(TEST_Transfer_2.getTransferId());
        Assert.assertEquals(3002,transfer.getTransferId());


    }
}