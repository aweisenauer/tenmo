package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    AccountDao accountDao;
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(DataSource dataSource, AccountDao accountDao) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.accountDao   = accountDao;
    }


    @Override
    public List<Transfer> getAllTransfers() {
        List<Transfer> allTransfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_status_code, account_from, account_to, transfer_amount FROM transfer;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Transfer transferResult = mapRowToTransfer(results);
            allTransfers.add(transferResult);

        }
        return allTransfers;
    }

    @Override
    public Transfer getTransferByTransferId(int transferId) {
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            Transfer transferDetails = mapRowToTransfer(results);
            return transferDetails;

        }
        return null;
    }

    @Override
    public List<Transfer> getTransferHistoryFromId(int fromId) {
        List<Transfer> transfersByUserId = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_status_code, account_from, account_to, transfer_amount FROM transfer WHERE account_from = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, fromId);
        if (results.next()) {
            Transfer transferResult = mapRowToTransfer(results);
            transfersByUserId.add(transferResult);
            return transfersByUserId;
        }
        System.out.println("No transfer exists with the id : " + fromId);
        return null;
    }


    public boolean createTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfer (transfer_status_code, account_from, account_to, transfer_amount)"
                + " VALUES (?, ?, ?, ?)";
        String sqlUpdateSender = "UPDATE account SET balance = balance - ?" + "WHERE account_id = ?";
        String sqlUpdateReceiver = "UPDATE account SET balance = balance + ?" + "WHERE account_id = ?";
        String sqlRejected = "INSERT INTO transfer (transfer_status_code, account_from, account_to, transfer_amount)"
                + " VALUES (2, ?, ?, ?)";
        if (transfer.getAmount()>accountDao.getBalanceByAccountId(transfer.getAccountFrom())){
            jdbcTemplate.update(sqlRejected,transfer.getAccountFrom(),transfer.getAccountTo(),transfer.getAmount());
            System.out.println("CODE 2: Transfer Rejected, not enough funds to send.");
            return false;


        }

       else try {
            jdbcTemplate.update(sql,transfer.getTransferStatusId(),transfer.getAccountFrom(),transfer.getAccountTo(),transfer.getAmount());
        } catch (DataAccessException e) {
            System.out.println("error");
        }
        try {
                jdbcTemplate.update(sqlUpdateSender, transfer.getAmount(), transfer.getAccountFrom());


        } catch (DataAccessException e) {
            System.out.println("error");
        }
        try {
            jdbcTemplate.update(sqlUpdateReceiver, transfer.getAmount(), transfer.getAccountTo());
        } catch (DataAccessException e) {
            System.out.println("error");
        }
        System.out.println("Transaction Successful!");
        return true;
    }

    public void updateTransferStatus(int accountId, int transferId, int transferStatusId) {
        String sql = "UPDATE transfer SET transfer_status_code = ? WHERE transfer_id = ?;";   //might need changed
        jdbcTemplate.update(sql, transferStatusId, transferId);
    }

    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setAccountFrom(results.getInt("account_from"));
        transfer.setAccountTo(results.getInt("account_to"));
        transfer.setAmount(results.getDouble("transfer_amount"));
        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setTransferStatusId(results.getInt("transfer_status_code"));
        return transfer;
    }
}
