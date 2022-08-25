package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {


    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
        if (results.next()){
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


    public void createTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfer (transfer_status_code, account_from, account_to, transfer_amount)" //might need changed
                + " VALUES (?, ?, ?, ?) RETURNING transfer_id";
        Integer newTransferId;
        try{
            newTransferId = jdbcTemplate.queryForObject(sql,Integer.class, transfer);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

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
