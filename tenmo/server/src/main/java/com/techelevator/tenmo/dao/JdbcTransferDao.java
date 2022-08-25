package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {


    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(DataSource dataSource) {this.jdbcTemplate=new JdbcTemplate(dataSource);}


    @Override
    public List<Transfer> getAllTransfers() {
        List<Transfer> allTransfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_code, transfer_status_code, account_from, account_to, transfer_amount FROM transfer;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            Transfer transferResult = mapRowToTransfer(results);
            allTransfers.add(transferResult);

        }
        return allTransfers;
    }

//    @Override
//    public Transfer getTransferByTransferId(int transferId) {
//        return null;
//    }

    @Override
    public List<Transfer> getTransferHistoryFromId(int fromId) {
        List<Transfer> transfersByUserId = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_code, transfer_status_code, account_from, account_to, transfer_amount FROM transfer WHERE account_from = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, fromId);
        if(results.next()){
            Transfer transferResult = mapRowToTransfer(results);
            transfersByUserId.add(transferResult);
            return transfersByUserId;
        }
        System.out.println("No transfer exists with the id : " + fromId);
        return null;
    }

//    @Override
//    public List<Transfer> getAllTransferByUserId(int userId) {
//        List<Transfer> transfersByUserId = new ArrayList<>();
//        String sql = "SELECT transfer_id, transfer_type_code, transfer_status_code, account_from, account_to, transfer_amount FROM transfer WHERE user_id = ?;";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
//        if(results.next()){
//            Transfer transferResult = mapRowToTransfer(results);
//            transfersByUserId.add(transferResult);
//            return transfersByUserId;
//        }
//        System.out.println("No transfer exists with the id : " + userId);
//        return null;
//    }

//    @Override
//    public List<Transfer> getAllTransfersByAccountId(int accountId) {
//        List<Transfer> transfers = new ArrayList<>();
//        String sql = "SELECT transfer_id, transfer_type_code, transfer_status_code, account_from, account_to, transfer_amount FROM transfer WHERE account_from IN(?) OR account_to IN(?);";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,accountId,accountId);
//        while(results.next()){
//            Transfer transfer = mapRowToTransfer(results);
//            transfers.add(transfer);
//        }
//
//        return transfers;
//    }

//    @Override
//    public List<Transfer> getTransferByFromId(int accountId) {
//        List<Transfer> transfers = new ArrayList<>();
//        String sql = "SELECT transfer_id, transfer_type_code, transfer_status_code, account_from, account_to, transfer_amount FROM transfer WHERE account_from IN(?) OR account_to IN(?);";
//
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId,accountId);
//        while(results.next()) {
//            Transfer transfer = mapRowToTransfer(results);
//            transfers.add(transfer);
//        }
//        return transfers;
//    }

//    @Override
//    public List<Transfer> getTransferHistoryByToId(int toId) {
//        List<Transfer> transfers = new ArrayList<>();
//        String sql = "SELECT transfer_id, transfer_type_code, transfer_status_code, account_from, account_to, transfer_amount FROM transfer WHERE account_to = ?;";
//
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,toId);
//        while(results.next()) {
//            Transfer transfer = mapRowToTransfer(results);
//            transfers.add(transfer);
//        }
//        return transfers;
//    }


    public Transfer createTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfers (transfer_id, transfer_type_code, transfer_status_code, account_from, amount_to,transfer_amount)" //might need changed
                +  " VALUES (?,?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, transfer.getTransferId(), transfer.getTransferTypeId(),transfer.getTransferStatusId(),
                transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        return transfer;
    }

    public void updateTransferStatus(int accountId,int transferId, int transferStatusId){
        String sql = "UPDATE transfer SET transfer_status_code = ? WHERE transfer_id = ?;";   //might need changed
        jdbcTemplate.update(sql,transferStatusId,transferId);
    }


    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setAccountFrom(results.getInt("account_from"));
        transfer.setAccountTo(results.getInt("account_to"));
        transfer.setAmount(results.getDouble("transfer_amount"));
        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setTransferStatusId(results.getInt("transfer_status_code"));
        transfer.setTransferTypeId(results.getInt("transfer_type_code"));
        return transfer;
    }

}
