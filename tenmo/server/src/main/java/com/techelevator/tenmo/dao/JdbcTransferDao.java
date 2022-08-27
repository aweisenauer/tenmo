package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
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
    Account account = new Account();
JdbcAccountDao accountDao;
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Transfer> getAllTransfersByUsername(String username) {
        List<Transfer> allTransfers = new ArrayList<>();
        String sql = "SELECT t.transfer_id,t.transfer_status_code,t.account_from,t.account_to,t.transfer_amount \n" +
                "FROM transfer t \n" +
                "JOIN account s ON t.account_from = s.account_id OR t.account_to = s.account_id\n" +
                "JOIN tenmo_user tu ON tu.user_id = s.user_id\n" +
                "WHERE tu.username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while (results.next()) {
            Transfer transferResult = mapRowToTransfer(results);
            allTransfers.add(transferResult);

        }
        return allTransfers;
    }

    @Override
    public Transfer getTransferByTransferId(int transferId) {
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?"; //fix sql
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            Transfer transferDetails = mapRowToTransfer(results);
            return transferDetails;

        }
        return null;
    }

    @Override
    public List<Transfer> getTransferHistoryFromId(int accountId) {
        List<Transfer> transfersByUserId = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_status_code, account_from, account_to, transfer_amount FROM transfer WHERE account_from = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,accountId);
        while (results.next()); {
            Transfer transferResult = mapRowToTransfer(results);
            transfersByUserId.add(transferResult);
            }


        return transfersByUserId;
        }



    public String createTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfer (transfer_status_code, account_from, account_to, transfer_amount)"
                + " VALUES (?, ?, ?, ?)";
        String sqlUpdateSender = "UPDATE account SET balance = balance - ?" + "WHERE account_id = ?";
        String sqlUpdateReceiver = "UPDATE account SET balance = balance + ?" + "WHERE account_id = ?";
        String sqlRejected = "INSERT INTO transfer (transfer_status_code, account_from, account_to, transfer_amount)"
                + " VALUES (2, ?, ?, ?)";
        try {
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
        return "Transaction Successful!";
    }

    public void requestTransfer(Transfer transfer){
        String sqlPending = "INSERT INTO transfer (transfer_status_code, account_from, account_to, transfer_amount)"
                + " VALUES (3, ?, ?, ?)";
        try {
            jdbcTemplate.update(sqlPending,transfer.getAccountFrom(),transfer.getAccountTo(),transfer.getAmount());
            System.out.println("Transaction Created: Request is Pending");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//    public void updateTransferStatus(int accountId, int transferId, int transferStatusId) {
//        String sql = "UPDATE transfer SET transfer_status_code = ? WHERE transfer_id = ?;";//might need changed
//        try {
//            jdbcTemplate.update(sql, transferStatusId, transferId);
//            if (transferStatusId==1){
//
//            }
//        }
//
//    }

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
