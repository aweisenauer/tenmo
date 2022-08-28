package com.techelevator.dao;

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

    private static final Transfer TEST_Transfer_1 = new Transfer(3001, 1, 2001, 2002, 20.00);
    private static final Transfer TEST_Transfer_2 = new Transfer(3002, 1, 2001, 2002, 20.00);

    @Before
    public void setup() {
        sut = new JdbcTransferDao(dataSource);
    }

}